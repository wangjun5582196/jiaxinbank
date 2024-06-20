package com.ai.indeed.email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            sendEmail();
        }
    }

    public static void sendEmail(){
        //        System.setProperty("socksProxyHost", "1.194.237.25");
//        System.setProperty("socksProxyPort", "3045");
//        System.setProperty("socksProxySet","true");


        // 配置SMTP服务器和认证信息
        String host = "smtp.163.com";
        String username = "wangjun5582196@163.com";
        String password = "YMUMUHJPWMDEYWAO";

        // 发件人和收件人信息
        String fromEmail = "wangjun5582196@163.com";
        String toEmail = "wangjun51000@hundsun.com";

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

            String htmlContent = "<br/>\n" +
                    "<table border=\"1\">\n" +
                    "    <tbody>\n" +
                    "    <tr>\n" +
                    "        <td style=\"text-align: center;\"><b>状态</b></td>\n" +
                    "        <td style=\"text-align: center;\"><b>产品</b></td>\n" +
                    "        <td style=\"text-align: center;\"><b>业务日期</b></td>\n" +
                    "        <td style=\"text-align: center;\"><b>文件类型</b></td>\n" +
                    "        <td style=\"text-align: center;\"><b>数据类型</b></td>\n" +
                    "        <td style=\"text-align: center;\"><b>资金账户/证券信息</b></td>\n" +
                    "        <td style=\"text-align: center;\"><b>催收机构</b></td>\n" +
                    "        <td style=\"text-align: center;\"><b>营业部</b></td>\n" +
                    "        <td style=\"text-align: center;\"><b>管理人</b></td>\n" +
                    "    </tr> <tr> <td>未催收未齐备</td> <td>2024_方正富邦</td> <td>20240417</td> <td>对账单</td> <td>期权结算资金账户</td> <td>134567837</td> <td>工商银行</td> <td></td> <td></td> </tr> <tr> <td>已催收未齐备</td> <td>2024_方正富邦</td> <td>20240417</td> <td>对账单</td> <td>券商普通资金账户</td> <td>236782378</td> <td>浙商证券</td> <td>中信证券</td> <td></td> </tr> <tr> <td>未催收未齐备</td> <td>2024_方正富邦</td> <td>20240417</td> <td>对账单</td> <td>券商信用资金账户</td> <td>567456723</td> <td>浙商证券</td> <td>广发证券</td> <td></td> </tr> </tbody> </table> <br/>";

            // 创建MimeMessage对象
            MimeMessage message = new MyMessage(session);

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
            System.out.println(message.getMessageID());
            transport.close();

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
