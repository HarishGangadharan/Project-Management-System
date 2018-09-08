package com.ideas2it.employeemanagementsystem.service.impl;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.service.OtpService;
import com.ideas2it.employeemanagementsystem.commons.constants.Constants;

import com.ideas2it.employeemanagementsystem.utils.MailUtil;

/**
 * <p>
 * Helps to create a otp for the purpose of the registration.
 * </p>
 * 
 * @author  Harish.G
 */
public class OtpServiceImpl implements  OtpService {
    
    /**
     * {@inheritDoc}
     */
    public int generateOtp(HttpServletRequest request, String mail) 
            throws PMSApplicationException {
        Random rand = new Random();
        int otp = rand.nextInt((500000 - 100000) + 1) + 1000;
        MailUtil.sentMail(request, String.valueOf(otp), mail, 
            Constants.REGISTRATION_MESSAGE);
        return otp;
    }
}
