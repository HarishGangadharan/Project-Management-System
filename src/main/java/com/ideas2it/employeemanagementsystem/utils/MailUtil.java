package com.ideas2it.employeemanagementsystem.utils;

import java.util.Properties;
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  
import javax.mail.Message; 
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
 
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.logger.PMSLogger;
import com.ideas2it.employeemanagementsystem.commons.constants.Constants;

/**
 * <p>
 * Helps to send mail with the corresponding details.
 * </p>
 *
 * @author Harish
 */
public class MailUtil {

    private static final String MAIL_EXCEPTION = "While sending mail to";

    /**
     * <p>
     * Send mail to the corresponding mail id with the specified subject and 
     * message.
     * </p>
     *
     * @param    HttpServletRequest  request for HttpServelt
     *
     * @param    message             Message to be sent in the body of the mail
     *
     * @param    mail                recipient mailid
     *
     * @param    subject             subject to be sent along with mail.
     */  
    public static void sentMail(HttpServletRequest request, 
            String message, String mail, String subject)
            throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Properties prop = new Properties();
        prop.put(Constants.MAIL_AUTH, Boolean.TRUE);
        prop.put(Constants.MAIL_STARTTLS, Boolean.TRUE);
        prop.put(Constants.MAIL_HOST, Constants.MAIL_HOST_VALUE);
        prop.put(Constants.MAIL_PORT, 587);

        Session ses = Session.getDefaultInstance(prop, 
            new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(Constants.SENDER, 
                Constants.SENDERPASSWORD); 
        }});

        try {
            MimeMessage mes = new MimeMessage(ses);
            mes.setFrom(new InternetAddress(Constants.SENDER));
            mes.addRecipient(Message.RecipientType.TO, 
                new InternetAddress(mail));
            mes.setSubject(subject);
            mes.setText(message);
            Transport.send(mes);
        } catch(MessagingException exception) {
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(MAIL_EXCEPTION).append(mail);
            PMSLogger.error(exception.getMessage());
            PMSLogger.info(String.valueOf(exception.getCause()));
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);       
        }
   }
}
