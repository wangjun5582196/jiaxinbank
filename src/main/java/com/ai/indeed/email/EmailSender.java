package com.ai.indeed.email;

import com.ai.indeed.proxy.ClientProxyBasicHttp;

import java.net.Authenticator;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    public static void main(String[] args) {

//        System.setProperty("socksProxyHost", "1.194.237.25");
//        System.setProperty("socksProxyPort", "3045");
//        System.setProperty("socksProxySet","true");


        // 配置SMTP服务器和认证信息
        String host = "smtp.163.com";
        String username = "wangjun5582196@163.com";
        String password = "YMUMUHJPWMDEYWAO";

        // 发件人和收件人信息
        String fromEmail = "wangjun5582196@163.com";
        String toEmail = "804534813@qq.com";

        // 创建Properties对象，设置SMTP服务器和认证信息
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");

        // 设置代理服务器信息
//        props.put("mail.smtp.proxy.host", "115.201.98.215");
//        props.put("mail.smtp.proxy.port", "4225");



//        props.put("mail.smtp.proxy.host", "1.194.237.25");
//        props.put("mail.smtp.proxy.port", "3045");
//
        // 设置代理服务器的用户名和密码
//        props.put("mail.smtp.proxy.user", "wangjun5582196");
//        props.put("mail.smtp.proxy.password", "1pajyb3z");
//
//        //可以的配置
//        props.put("mail.smtp.proxy.host", "36.138.215.47");
//        props.put("mail.smtp.proxy.port", "16819");

//        props.put("mail.smtp.proxy.host", "10.106.251.6");
//        props.put("mail.smtp.proxy.port", "8080");


        // 创建Session对象
        Session session = Session.getInstance(props);

        try {

            String htmlContent = "<html><body><h1>邮件内容</h1>"
                    + "<table style='border-collapse: collapse;'>"
                    + "<tr><th style='border: 1px solid black;'>列1</th><th style='border: 1px solid black;'>列2</th></tr>"
                    + "<tr><td style='border: 1px solid black;'>值1</td><td style='border: 1px solid black;'>值2</td></tr>"
                    + "</table></body></html>";
            htmlContent=htmlContent+"<html><body><h1>邮件内容</h1>"
                    + "<table style='border-collapse: collapse;'>"
                    + "<tr><th style='border: 1px solid black;'>列1</th><th style='border: 1px solid black;'>列2</th></tr>"
                    + "<tr><td style='border: 1px solid black;'>值1</td><td style='border: 1px solid black;'>值2</td></tr>"
                    + "</table></body></html>";

            // 创建MimeMessage对象
            MimeMessage message = new MimeMessage(session);

            // 设置发件人
            message.setFrom(new InternetAddress(fromEmail));

            // 设置收件人
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            // 设置邮件主题和内容
            message.setSubject("Hello from JavaMailProxy");
            message.setText(htmlContent, "UTF-8", "html");


            // 发送邮件
            Transport transport = session.getTransport("smtp");
            transport.connect(host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
