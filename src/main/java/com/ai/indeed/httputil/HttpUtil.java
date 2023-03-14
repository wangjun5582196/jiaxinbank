package com.ai.indeed.httputil;

import com.alibaba.fastjson.JSON;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @Author: huzhang
 * @Date: 2023/2/20 5:06 下午
 */
public class HttpUtil {
    private static final OkHttpClient okHttpClient;
    private static String requestUrl;
    // private static final String requestUrl="http://z-commander-fe.qa.ii-ai.tech/commander-manager-api/openAPI/v1/job/jobUuid/1";
    private static String appKey;
    private static String appSecret;

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static String feVesion = "1.7.6.6";

    static {
        initParams();
        okHttpClient = okHttpClient();
    }

    public static String sendStartJob(String uuid, String jobUUID, Map<String, String> param) {
        MediaType mediaType = MediaType.parse("application/json");
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("inputParam", param);
        String data = JSON.toJSONString(requestParam);
        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder().url(requestUrl.replace("jobUuid", jobUUID)).method("PUT", body).
                addHeader("Content-Type", "application/json")
                .addHeader("APPKey", appKey)
                .addHeader("APPSecret", appSecret)
                .addHeader("commander-fe-version", feVesion)
                .build();
        logger.info("交易流水号{},请求控制器,请求报文为{}", uuid, JSON.toJSONString(param));
        String response = null;
        try {
            response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public static OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                //设置读取超时时间
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();//设置连接超时时间
    }


    public static void  initParams()  {
        InputStream in = null;
        Properties ps = new Properties();
        try {
            in = new FileInputStream("properties/param.properties");
            ps.load(in);
            appKey = String.valueOf(ps.getProperty("appKey"));
            appSecret = String.valueOf(ps.getProperty("appSecret"));
            requestUrl = String.valueOf(ps.getProperty("requestUrl"));
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


    }

    public static void main(String[] args) {
        Map<String,String> map=  new HashMap<>();
        map.put("CstdySrlNo","");
        map.put("Sbm111tTm","");
        map.put("SysTp","");

        sendStartJob("112233", "d516908c26438a0350e70eb9264ec6c1", map);
    }

}
