package com.ai.indeed.email;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class MyMessage extends MimeMessage {
    /**
     * Default constructor. An empty message object is created.
     * The <code>headers</code> field is set to an empty InternetHeaders
     * object. The <code>flags</code> field is set to an empty Flags
     * object. The <code>modified</code> flag is set to true.
     *
     * @param    session    the Sesssion
     */
    public MyMessage(Session session) {
        super(session);
    }
    protected void updateMessageID() throws MessagingException {
        setHeader("Message-ID", "86242881.3312.1700441653484.JavaMail.\"mkt-ghq@sinosig.com\"@dubbo-service-provider");
    }
}
