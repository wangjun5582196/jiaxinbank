package com.ai.indeed.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author: huzhang
 * @Date: 2023/2/17 10:43 上午
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("127.0.0.1",10290);
        //获取用户输入
        String input ="00000000<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<service>" +
                "<SYS_HEAD>" +
                "<SvcCd>30220002</SvcCd>" +
                "<ScnCd>01</ScnCd>" +
                "<BsnSrINo>GMID20200618000000000001</BsnSrINo>" +
                "<TxnlnttDt>20200618</TxnlnttDt>" +
                "<TxnlnttTm>080000</TxnlnttTm>" +
                "<CnsmrSysNo>MiD</CnsmrSysNo>" +
                "<CnsmrSysSrINo>CMID20200618000000000001</CnsmrSysSrINo>" +
                "</SYS_HEAD>" +
                "<APP_HEAD>" +
                "<LglPrsnCd>666</LglPrsnCd>" +
                "</APP_HEAD>" +
                "<BODY>" +
                "<CstdySrlNo>系统类型</CstdySrlNo>" +
                "<SbmtTm>调用时间</SbmtTm>" +
                "<SysTp>GD</SysTp>" +
                "</BODY>" +
                "</service>";

        //将用户输入的数据写入到服务器，socket中
        OutputStream stream=socket.getOutputStream();
        PrintWriter writer=new PrintWriter(stream);
        writer.println(input);
        writer.flush();//关闭writer写入流。

    }
}
