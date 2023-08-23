package com.ai.indeed.email;

import java.util.Properties;
import javax.mail.*;



public class MailClient {
    public static void main(String[] args) {
        // 邮件服务器配置
        String host = "pop.163.com";
        int port = 995;
        String username = "wangjun5582196@163.com";
        String password = "YMUMUHJPWMDEYWAO";

        // 代理服务器配置
        String proxyHost = "106.227.9.220";
        int proxyPort = 16817;
        String proxyUsername = "wangjun5582196";
        String proxyPassword = "1pajyb3z";

        try {
            // 创建会话配置
            Properties props = new Properties();
            props.put("mail.store.protocol", "pop3s");
            props.put("mail.pop3s.host", host);
            props.put("mail.pop3s.port", port);
            props.put("mail.pop3s.ssl.enable", "true");

            // 创建代理配置
            props.put("mail.pop3s.proxy.host", proxyHost);
            props.put("mail.pop3s.proxy.port", proxyPort);
            props.put("mail.pop3s.proxy.user", proxyUsername);
            props.put("mail.pop3s.proxy.password", proxyPassword);

            // 创建会话
            Session session = Session.getDefaultInstance(props, null);

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

