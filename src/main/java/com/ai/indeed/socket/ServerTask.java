package com.ai.indeed.socket;

import cn.hutool.core.date.DateUtil;
import com.ai.indeed.httputil.HttpUtil;
import com.ai.indeed.xml.XmlParaser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * @author wangjunjun
 */
public class ServerTask implements Runnable {
    private Socket socket ;

    public ServerTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(DateUtil.now() + "客户端发来消息：" + inputLine);
                //解析Xml报文并发送http请求
                inputLine = inputLine.substring(8);
                Map<String, String> params = XmlParaser.readXml(inputLine);
                Map<String, String> headParams = XmlParaser.readHeadXml(inputLine);
                if (params.size() == 0) {
                    System.err.println(DateUtil.now() + "客户端发来请求参数为空：" + inputLine);
                }
                String jobUuid = "";
                String type = params.get("SysTp");
                String[] types = initParams("sysTps").split(",");
                String[] jobUuids = initParams("jobUuids").split(",");
                jobUuid = jobUuids[Arrays.asList(types).indexOf(type)];
                String response = HttpUtil.sendStartJob(jobUuid, params);
                out.write(initResponse(headParams, response));
                out.flush();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String initResponse(Map<String,String> headParams,String response){
        String successTemplate =initResponseTemplate("properties/success.xml");
        //String failTemplate =initResponseTemplate("src/main/resources/fail.xml");
        JSONObject jsonResponse = JSON.parseObject(response);
        Integer code = jsonResponse.getInteger("code");
        String msg = jsonResponse.getString("msg");
        successTemplate=successTemplate.replace("dataParam",String.valueOf(code));
        successTemplate= successTemplate.replace("msgParam",String.valueOf(msg));
        for (String key:headParams.keySet()) {
            successTemplate=successTemplate.replace(key+"Param",headParams.get(key));
        }
        successTemplate=successTemplate.replace("PvdrSysSrINoParam","CRPA"+System.currentTimeMillis());
        successTemplate=successTemplate.replace("TxnRetDtParam", DateUtil.format(new Date(),"YYYYMMDD"));
        successTemplate=successTemplate.replace("TxnRetTmParam",DateUtil.format(new Date(),"HHMMSS"));
        successTemplate=successTemplate.replace("dateParam", DateUtil.format(new Date(),"YYYYMMDD"));
        int count=String.valueOf(successTemplate.length()).length();
        String prefix="";
        for(int i=0;i<8-count;i++){
            prefix=prefix+"0";
        }
        successTemplate=prefix+(successTemplate.length())+successTemplate;
        System.out.println(DateUtil.now()+"服务器响应报文"+successTemplate);
        return successTemplate;
    }

    public static  String initResponseTemplate(String path){
        StringBuilder template =new StringBuilder();
        try {
            Stream<String> lines1 = Files.lines(Paths.get(path));
            lines1.forEach(line->{
                template.append(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template.toString().trim();
    }


    public static String  initParams(String type ) throws FileNotFoundException {
        InputStream in =new FileInputStream("properties/param.properties");
        Properties ps = new Properties();
        String jobUuid="";
        try {
            ps.load(in);
            jobUuid = String.valueOf(ps.getProperty(type));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jobUuid;
    }
}