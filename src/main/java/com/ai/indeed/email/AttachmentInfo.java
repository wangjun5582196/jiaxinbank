package com.ai.indeed.email;



import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.ParseException;
import java.io.*;

public class AttachmentInfo {

    private static final String INLINE = "inline";

    private final Logger log = LoggerFactory.getLogger(AttachmentInfo.class);

    private Part part;
    private int num;

    public AttachmentInfo(Part part) {
        this.part = part;
    }

    public String getAttachmentType() throws MessagingException {
        String contentType;
        return (contentType = this.part.getContentType()) == null ? "invalid part" : contentType;
    }

    public String getContent() throws IOException, MessagingException {
        return this.hasMimeType("text/plain") ? (String)this.part.getContent() : "";
    }

    public String getDescription() throws MessagingException {
        String description;
        return (description = this.part.getDescription()) != null ? description : "";
    }

    public String getFilename() throws MessagingException {
        String filename;
        if ((filename = this.part.getFileName()) != null) {
            filename = MailUtil.deCode(filename);
            return filename;
        } else {
            return "";
        }
    }

    private void makeDir(String filePath) {
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.setWritable(true);
            dir.mkdirs();
        }

    }

    public void save(String filePath, String fileName) throws Exception{
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try{
            this.makeDir(filePath);
            File file = new File(filePath + File.separator + fileName);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bis = new BufferedInputStream(this.part.getInputStream());
            byte[] buffer = new byte[1024];
            boolean var7 = true;
            int length;
            while ((length = bis.read(buffer)) > 0) {
                bos.write(buffer,0,length);
            }
            bos.flush();

        }finally {
            closeIO(bos,bis);
        }
    }

    private void closeIO(BufferedOutputStream bos, BufferedInputStream bis){
        if (bos != null){
            try{
                bos.close();
            }catch (Exception ex){
                log.error(ex.getMessage(), ex);
            }
        }
        if (bis != null){
            try{
                bis.close();
            }catch (Exception ex){
                log.error(ex.getMessage(), ex);
            }
        }

    }

    public void save(String filePath) throws Exception {
        String fileName = this.getFilename();
        fileName = StringUtils.isBlank(fileName) ? String.valueOf(System.currentTimeMillis()) : fileName;
        this.save(filePath, fileName);
    }

    public String getNum() {
        return Integer.toString(this.num);
    }

    public boolean hasDescription() throws MessagingException {
        return this.part.getDescription() != null;
    }

    public boolean hasFilename() throws MessagingException {
        return this.part.getFileName() != null;
    }

    public InputStream getFileStream() throws IOException, MessagingException {
        return this.part.getInputStream();
    }

    public boolean hasMimeType(String mimeType) throws MessagingException {
        return this.part.isMimeType(mimeType);
    }

    public boolean isInline() throws MessagingException {
        return this.part.getDisposition() != null ? (INLINE).equals(this.part.getDisposition()) : true;
    }

    public void setPart(int num, Part part) throws MessagingException, ParseException {
        this.part = part;
        this.num = num;
    }
}
