package com.ai.indeed.email;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Properties;

public class EMLParser2 {

    public static void main(String[] args) {
        String emlFilePath = "C:\\Users\\wangjun51000\\Desktop/11.eml";
        String outputDirectory = "C:\\Users\\wangjun51000\\Desktop/";

        try {
            Session session = Session.getDefaultInstance(new Properties());
            InputStream inputStream = new FileInputStream(emlFilePath);
            MimeMessage message = new MimeMessage(session, inputStream);

            processParts(message, outputDirectory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processParts(Part part, String outputDirectory) throws Exception {
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int count = multipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                processParts(bodyPart, outputDirectory);
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

    private static void saveAttachment(Part part, String filePath) throws Exception {
        InputStream inputStream = part.getInputStream();
        OutputStream outputStream = new FileOutputStream(new File(filePath));

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.close();
        inputStream.close();
    }
}
