package com.ai.indeed.email;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailCilent {
    public static void main(String[] args) {
        // 邮件服务器配置
        String host = "imap.qq.com";
        int port = 993;
        String username = "804534813@qq.com";
        String password = "zdftnbufizbhbede";

        // 代理服务器配置
//        String proxyHost = "106.227.9.22";
//        int proxyPort = 16817;



//        String proxyUsername = "wangjun5582196";
//        String proxyPassword = "1pajyb3z";

        try {
            // 创建会话配置
            Properties props = new Properties();
            props.put("mail.store.protocol", "imap");
            props.put("mail.imap.host", host);
            props.put("mail.imap.port", port);
            props.put("mail.imap.ssl.enable", "true");
            props.setProperty("mail.debug", "true");

            // 创建代理配置

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
                MimeMessage cmsg = (MimeMessage) message;

//                String messageID = cmsg.getMessageID();
//                System.out.println(messageID);
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
