package com.ai.indeed.email;

import java.util.Properties;
import javax.mail.*;



public class MailClient {
    public static void main(String[] args) {
        // 邮件服务器配置
        String host = "pop.exmail.qq.com";
        int port = 995;
        String username = "gzcs@sdic-wealth.com";
        String password = "P6Dnq3LcSuFT3GH5";

        // 代理服务器配置
//        String proxyHost = "106.227.9.22";
//        int proxyPort = 16817;


        String proxyHost = "proxy.trust.inner";
        int proxyPort = 8080;
//        String proxyUsername = "wangjun5582196";
//        String proxyPassword = "1pajyb3z";

        try {
            // 创建会话配置
            Properties props = new Properties();
            props.put("mail.store.protocol", "pop3");
            props.put("mail.pop3.host", host);
            props.put("mail.pop3.port", port);
            props.put("mail.pop3.ssl.enable", "true");

            // 创建代理配置
            props.put("mail.pop3.proxy.host", proxyHost);
            props.put("mail.pop3.proxy.port", proxyPort);
//            props.put("mail.pop3.proxy.user", proxyUsername);
//            props.put("mail.pop3.proxy.password", proxyPassword);

            // 创建会话
            Session session = Session.getInstance(props, null);

            // 连接到邮件服务器
            Store store = session.getStore();
            store.connect(host, port, username, password);

            // 获取收件箱
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // 打印邮件信息
            Message[] messages = inbox.getMessages();
            for (Message message : messages) {
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());
            }

            // 关闭连接
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

