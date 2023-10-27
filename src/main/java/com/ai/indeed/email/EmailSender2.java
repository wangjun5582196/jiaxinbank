package com.ai.indeed.email;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender2 {
    public static void main(String[] args) {
        // 配置SMTP服务器和认证信息
        String host = "smtp.163.com";
        String username = "wangjun5582196@163.com";
        //
        String password = "YMUMUHJPWMDEYWAO";

        // 发件人和收件人信息
        String fromEmail = "wangjun5582196@163.com";
        String toEmail = "804534813@qq.com";

        // 创建Properties对象，设置SMTP服务器和认证信息
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "false");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "d");

        // 创建Session对象
        Session session = Session.getInstance(props);

        try {
            // 创建MimeMessage对象
            MimeMessage message = new MimeMessage(session);

            // 设置发件人
            message.setFrom(new InternetAddress(fromEmail));

            // 设置收件人
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            // 设置邮件主题和内容
            message.setSubject("Hello from JavaMail");
            message.setText("This is a test email from JavaMail.");

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


    public boolean getXO(String str) {
        if("".equals(str)){
            return false;
        }
        Boolean flag=false;
        Integer length=str.length();
        Set<String> strSet = new HashSet<>();
        for(int i=0;i<length;i++){
            String temp=str.substring(i,i+1);
            if(strSet.contains(temp)){
                flag=true;
                break;
            }else{
                strSet.add(temp);
            }
        }
        // 在这⾥写代码
        return flag ;

    }
}
