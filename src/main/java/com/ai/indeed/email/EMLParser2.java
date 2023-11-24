package com.ai.indeed.email;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Properties;

public class EMLParser2 {

    public static void main(String[] args) {
        String emlFilePath = "C:\\Users\\wangjun51000\\Desktop/【外部邮件】光大证券数据发送，文件名_玄元科新65号20231114[柜台文件].RAR.eml";
        String outputDirectory = "C:\\Users\\wangjun51000\\Desktop/";

        try {
            Session session = Session.getDefaultInstance(new Properties());
            InputStream inputStream = new FileInputStream(emlFilePath);
            MimeMessage message = new MimeMessage(session, inputStream);
            processParts(message, outputDirectory,8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processParts(Part part, String outputDirectory,int retryCount) throws Exception {
        if(retryCount==0){
            return;
        }
        retryCount--;
        System.out.println(retryCount);
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int count = multipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                processParts(bodyPart, outputDirectory,retryCount);
            }
        } else if (part.isMimeType("application/octet-stream")) {
            String disposition = part.getDisposition();
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
