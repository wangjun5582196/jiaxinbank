package com.ai.indeed.email;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmlParser {


    private static final String MULTIPART = "multipart/*";

    public static final String MIME_TYPE_APPLICATION_OCTET_STREAM = "application/octet-stream";

    public static final String X_ZIP_COMPRESSED = "application/x-zip-compressed";

    private static final String MSEXCEL = "application/vnd.ms-excel";


    public static void main(String[] args) {
        // 读取.eml文件路径
        String emlFilePath = "D:\\360MoveData\\Users\\wangjun51000\\Desktop\\问题邮件/浙商证券_1800052027_古曲祥辰3号_20240709-20240709普通对账单.eml";

        // 配置JavaMail会话
        Properties properties = System.getProperties();
        Session session = Session.getDefaultInstance(properties, null);

        try {
            // 创建MimeMessage对象并加载.eml文件
            MimeMessage message = new MimeMessage(session, new FileInputStream(emlFilePath));
            System.out.println("message_id------"+message.getMessageID());

            // 解析邮件内容
            String subject = message.getSubject();
            String from = message.getFrom()[0].toString();
            String to = message.getRecipients(Message.RecipientType.TO)[0].toString();
            String content = message.getContent().toString();

            // 打印解析结果
            System.out.println("Subject: " + subject);
            System.out.println("From: " + from);
            System.out.println("To: " + to);
            System.out.println("Content: " + content);

            String[] tas = message.getHeader("Subject");
            System.out.println("原生获取主题---"+message.getSubject());
            String subject123 = "";
            if (tas != null) {
                StringBuilder r = new StringBuilder(tas[0]);
                for (int i = 1; i < tas.length; i++) {
                    r.append(tas[i]);
                }
                subject123 = r.toString();
            }
            System.out.println( MailUtil.deCode(subject123));

            List<AttachmentInfo> attachments = new ArrayList<>();
            if (message.isMimeType(MULTIPART)) {
                Multipart mp = (Multipart) message.getContent();
                for (int i = 0; i < mp.getCount(); i++) {
                    BodyPart part = mp.getBodyPart(i);
                    String disposition = part.getDisposition();
                    if (Part.ATTACHMENT.equalsIgnoreCase(disposition) || Part.INLINE.equalsIgnoreCase(disposition)) {
                        AttachmentInfo attachment = new AttachmentInfo(part);
                        System.out.println(attachment.getFilename());
                        attachments.add(attachment);
                        continue;
                    }
                    if (part.isMimeType(MIME_TYPE_APPLICATION_OCTET_STREAM)) {
                        AttachmentInfo attachment = new AttachmentInfo(part);
                        attachments.add(attachment);
                        continue;
                    }
                    if (part.isMimeType("NONE/NONE")) {
                        AttachmentInfo attachment = new AttachmentInfo(part);
                        attachments.add(attachment);
                    }
                }
            } else if(message.isMimeType(MSEXCEL) || message.isMimeType(X_ZIP_COMPRESSED)) {
                String disposition = message.getDisposition();
                if (Part.ATTACHMENT.equalsIgnoreCase(disposition) || Part.INLINE.equalsIgnoreCase(disposition)) {
                    AttachmentInfo attachment = new AttachmentInfo(message);
                    attachments.add(attachment);
                }
            } else if (message.isMimeType(MIME_TYPE_APPLICATION_OCTET_STREAM)) {
                String disposition = message.getDisposition();
                if (Part.ATTACHMENT.equalsIgnoreCase(disposition) || Part.INLINE.equalsIgnoreCase(disposition)) {
                    AttachmentInfo attachment = new AttachmentInfo(message);
                    attachments.add(attachment);
                }
            }
            System.out.println(attachments.size());
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
