package com.ai.indeed.email;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Properties;

public class EMLParser2 {

    private static final String TEXT_PLAIN = "text/plain";
    private static final String TEXT_HTML = "text/html";
    private static final String MULTIPART = "multipart/*";
    /**
     * mimeType excel 格式
     */
    private static final String MSEXCEL = "application/vnd.ms-excel";
    /**
     * mimeType zip格式
     */
    public static final String X_ZIP_COMPRESSED = "application/x-zip-compressed";
    private static final String KEYWORD_NAME = "name=";
    public static final String MIME_TYPE_APPLICATION_OCTET_STREAM = "application/octet-stream";


    public static void main(String[] args) {
        String emlFilePath = "D:\\360MoveData\\Users\\wangjun51000\\Desktop\\问题邮件/浙商证券_1800052027_古曲祥辰3号_20240709-20240709普通对账单.eml";
        String outputDirectory = "C:\\Users\\wangjun51000\\Desktop/";

        try {
            Session session = Session.getDefaultInstance(new Properties());
            InputStream inputStream = new FileInputStream(emlFilePath);
            MimeMessage message = new MimeMessage(session, inputStream);
            System.out.println(message.getSubject());

            System.out.println(Md5CalculateUtil.MD5(message.getMessageID()));

            processParts(message, outputDirectory,8);


            StringBuffer sb = new StringBuffer();
            Object content = message.getContent();
            if (message.isMimeType(TEXT_PLAIN) || message.isMimeType(TEXT_HTML)) {
                sb.append((String) content);
            } else if (message.isMimeType(MULTIPART)) {
                Multipart mp = (Multipart) message.getContent();
                int numParts = mp.getCount();
                boolean hasTextHtml = false;
                for (int i = 0; i < numParts; ++i) {
                    BodyPart bodyPart = mp.getBodyPart(i);
                    if (bodyPart.isMimeType(TEXT_HTML)) {
                        hasTextHtml = true;
                    }
                }
                for (int i = 0; i < numParts; ++i) {
                    Part bodyPart = mp.getBodyPart(i);
                    if (bodyPart.isMimeType(TEXT_PLAIN) && hasTextHtml) {
                        // 优先取text/html，防止同时取text/plain、text/html造成正文重复
                        continue;
                    }
                    sb.append(getMailContent(bodyPart));
                }
            } else {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String getMailContent(Part part) throws MessagingException, IOException {
        StringBuffer sb = new StringBuffer();
        if (part.isMimeType(TEXT_PLAIN) || part.isMimeType(TEXT_HTML)) {
            if (!part.getContentType().contains(KEYWORD_NAME)) {
                sb.append((String) part.getContent());
            }
        } else if (part.isMimeType(MULTIPART)) {
            Multipart mp = (Multipart) part.getContent();
            int numParts = mp.getCount();
            boolean hasTextHtml = false;
            for (int i = 0; i < numParts; ++i) {
                if (mp.getBodyPart(i).isMimeType(TEXT_HTML)) {
                    hasTextHtml = true;
                }
            }
            for (int i = 0; i < numParts; ++i) {
                Part bodyPart = mp.getBodyPart(i);
                if (bodyPart.isMimeType(TEXT_PLAIN) && hasTextHtml) {
                    // 优先取text/html，防止同时取text/plain、text/html造成正文重复
                    continue;
                }
                sb.append(getMailContent(bodyPart));
            }
        } else {
            System.out.println("未定义具体传输数据类型");
        }
        return sb.toString();
    }


    private static void processParts(Part part, String outputDirectory,int retryCount) throws Exception {
        String disposition = part.getDisposition();
        System.out.println("Disposition: " + disposition);
        if(retryCount==0){
            return;
        }
        retryCount--;
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int count = multipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                processParts(bodyPart, outputDirectory,retryCount);
            }
        }else if(Part.ATTACHMENT.equalsIgnoreCase(disposition) || Part.INLINE.equalsIgnoreCase(disposition)){
            String fileName = MailUtil.deCode(part.getFileName());
            String filePath = outputDirectory + File.separator + fileName;
            System.out.println("Saved attachment: " + filePath);
            saveAttachment(part, filePath);
        } else if (part.isMimeType("application/octet-stream")) {
            if (disposition != null && (disposition.equalsIgnoreCase(Part.ATTACHMENT) || disposition.equalsIgnoreCase(Part.INLINE))) {
                String fileName = MailUtil.deCode(part.getFileName());
                String filePath = outputDirectory + File.separator + fileName;
                saveAttachment(part, filePath);
                System.out.println("Saved attachment: " + filePath);
            }
        }
    }

//    private static void saveAttachment(Part part, String filePath) throws Exception {
//        InputStream inputStream = part.getInputStream();
//        OutputStream outputStream = new FileOutputStream(new File(filePath));
//
//        byte[] buffer = new byte[4096];
//        int bytesRead;
//        while ((bytesRead = inputStream.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, bytesRead);
//        }
//
//        outputStream.close();
//        inputStream.close();
//    }
        private static void saveAttachment(Part part, String filePath) throws Exception {
            BufferedOutputStream bos = null;
            BufferedInputStream bis = null;
            try{

                bos = new BufferedOutputStream(new FileOutputStream(filePath));
                bis = new BufferedInputStream(part.getInputStream());
                byte[] buffer = new byte[1024];
                int length;
                while ((length = bis.read(buffer)) > 0) {
                    bos.write(buffer,0,length);
                }
                bos.flush();

            }finally {
                closeIO(bos,bis);
            }

        }

    private static void closeIO(BufferedOutputStream bos, BufferedInputStream bis){
        if (bos != null){
            try{
                bos.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        if (bis != null){
            try{
                bis.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }



}
