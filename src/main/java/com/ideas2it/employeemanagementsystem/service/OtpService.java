package com.ideas2it.employeemanagementsystem.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;

import com.ideas2it.employeemanagementsystem.commons.constants.Constants;

import com.ideas2it.employeemanagementsystem.utils.MailUtil;

/**
 * <p>
 * Helps to create a otp for the purpose of the registration.
 * </p>
 * 
 * @author  Harish.G
 */
public interface OtpService {
    
    /**
     * {@inheritDoc}
     */
    public int generateOtp(HttpServletRequest request, String mail) 
            throws PMSApplicationException;
}
