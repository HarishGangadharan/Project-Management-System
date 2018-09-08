package com.ideas2it.employeemanagementsystem.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;

import com.ideas2it.employeemanagementsystem.dao.AdminDao;
import com.ideas2it.employeemanagementsystem.dao.impl.AdminDaoImpl;
import com.ideas2it.employeemanagementsystem.model.Admin;
import com.ideas2it.employeemanagementsystem.service.AdminService;
import com.ideas2it.employeemanagementsystem.service.OtpService;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * <p>
 *  Here we can do service related to admin like creating a new admin and allow
 * only valid user.
 * </p>
 *
 * @author   Harish
 */
public class AdminServiceImpl implements AdminService {

    private static AdminDao adminDao;
    private  static  OtpService otpService;

    public void init() throws ServletException { 
         ApplicationContext context = new GenericXmlApplicationContext("classpath*:/Beans.xml");
    }

    public  void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;

    }

    public  void setOtpService(OtpService otpService) {
        this.otpService = otpService;
    }


    /**
     * {@inheritDoc}
     */
    public String createAdmin(Admin admin) throws PMSApplicationException {
        return adminDao.insertAdmin(admin);
    }

    /**
     * {@inheritDoc}
     */
    public Admin searchAdmin(String adminMail, String columnName) throws PMSApplicationException {
        return adminDao.searchAdmin(adminMail, columnName);
    }

    /**
     * {@inheritDoc}
     */
     public Admin updateAdminPin(Admin admin) throws PMSApplicationException {
        return adminDao.updateAdminPin(admin);
    }

    /**
     * {@inheritDoc}
     */
    public int generateOtp(HttpServletRequest request, String mail) 
            throws PMSApplicationException {
        return otpService.generateOtp(request, mail);
    }
}
