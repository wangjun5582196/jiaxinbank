package com.ai.indeed.email;

import javax.mail.*;
import javax.mail.search.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


public class MailClient2 {
    public static void main(String[] args) {

//            {mail.imap.socketFactory.port=995,
//                mail.store.protocol=imap,
//                mail.mime.multipart.allowempty=true,
//                mail.imap.port=995,
//                mail.imap.timeout=25000,
//                mail.imap.connectiontimeout=25000,
//                mail.imap.writetimeout=25000,
//                mail.imap.proxy.host=proxy.trust.inner,
//                mail.imap.proxy.port=8080,
//                mail.imap.ssl.enable=true,
//                mail.imap.socketFactory.fallback=false,
//                mail.imap.host=pop.exmail.qq.com,
//                mail.mime.address.strict=false,
//                mail.imap.socketFactory.class=javax.net.ssl.SSLSocketFactory}
        // 邮件服务器配置
        String host = "pop3.mxhichina.com";
        int port = 995;
        String username = "zqyyzx@priv.ccbtrust.com.cn";
        String password = "Zqyy0099.";

        // 代理服务器配置
//        String proxyHost = "106.227.9.220";
//        int proxyPort = 16817;
//        String proxyUsername = "wangjun5582196";
//        String proxyPassword = "1pajyb3z";

        try {
            // 创建会话配置
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "pop3");
            props.setProperty("mail.pop3.host", host);
            props.setProperty("mail.pop3.port", String.valueOf(port));
            props.setProperty("mail.pop3.ssl.enable", "true");
            props.setProperty("mail.debug", "true");


//            // 创建代理配置
//            props.put("mail.imap.proxy.host", proxyHost);
//            props.put("mail.imap.proxy.port", proxyPort);
//            props.put("mail.imap.proxy.user", proxyUsername);
//            props.put("mail.imap.proxy.password", proxyPassword);

//            props.put("mail.mime.multipart.allowempty",true);
//
//            props.put("mail.imap.timeout",25000);
//            props.put("mail.imap.connectiontimeout",25000);
//            props.put("mail.imap.writetimeout",25000);
//
//            props.put("mail.imap.socketFactory.fallback",false);
//            props.put("mail.mime.address.strict",false);


            // 创建会话
            Session session = Session.getInstance(props, null);

            // 连接到邮件服务器
            Store store = session.getStore();
            store.connect(host, port, username, password);

            // 获取收件箱
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.FLAGGED), false);
            //	Message[] messages = folder.search(flagTerm);
            //收取2天内的
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, -8);
            //GT（>）, LE（<=）, LT（<）
            SearchTerm searchTerm = new ReceivedDateTerm(ComparisonTerm.GT, calendar.getTime());
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.add(Calendar.DATE, 1);
            SearchTerm andTerm = new AndTerm(searchTerm,
                    new ReceivedDateTerm(ComparisonTerm.LT, calendar1.getTime()));
            SearchTerm andTerm2 = new AndTerm( andTerm, flagTerm);
            // 接收读取邮箱中的未读邮件，对应邮件标志为 FLAGGED， false 并且 接收日期在 两天内，取到过滤后 邮件结果。
            // 做具体的邮件处理，读取完成后，将FLAGGED 标识 置为true。
            Message[] messages = inbox.search(andTerm2);
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

