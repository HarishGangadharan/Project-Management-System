package com.ideas2it.employeemanagementsystem.controller;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.commons.constants.ClientConstants;
import com.ideas2it.employeemanagementsystem.commons.constants.EmployeeConstants;
import com.ideas2it.employeemanagementsystem.model.Admin;
import com.ideas2it.employeemanagementsystem.service.AdminService;
import com.ideas2it.employeemanagementsystem.service.impl.AdminServiceImpl;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.logger.PMSLogger;
import com.ideas2it.employeemanagementsystem.utils.MailUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
/**
 * <p>
 * Obtain  the username and password from the user and allows further operations
 * only if they are valid and allows to add a new user.
 * </p> 
 * @author Harish
 */

public class AdminController extends HttpServlet {

    private static AdminService adminService;

    public void init() throws ServletException { 
         ApplicationContext context = 
             new GenericXmlApplicationContext("classpath*:/Beans.xml");
    }

    public  void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }
 
    /**
     * <p>
     * Helps to perform operation related to the admin controller.
     * </p>
     *
     * @param  request   HttpServletRequest
     *
     * @param  response  HttpServletResponse
     */  
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        try {
            switch (request.getParameter(Constants.CHOICE)) {
                case Constants.LOGIN:
                    searchAdmin(request, response);
                    break; 
                case Constants.REGISTER:
                    String enteredOtp =
                        request.getParameter(Constants.ENTERED_OTP);
                    String orginalOtp = 
                        request.getParameter(Constants.ORIGINAL_OTP);
                    addAdmin(request, response, enteredOtp, orginalOtp);
                    break;
                case Constants.LOGOUT:
                    logoutAdmin(request, response); 
                    break;
                case Constants.LABEL_OTP:
                    generateOtp(request, response);
                    break;
                case Constants.SUBMIT:
                    addAccessPin(request, response);
                    break;
                case Constants.LABEL_PIN:
                    searchAdminByPin(request, response);
                default :
            }
        } catch(PMSApplicationException exception) {
            PMSLogger.info(exception.getMessage());
           request.getRequestDispatcher(Constants.ERRORJSP).
               forward(request, response);
        }
    }

    /**
     * <p>
     * Helps to generate an otp for the registration purpose.
     * </p>
     *
     * @param  request   HttpServletRequest
     *
     * @param  response  HttpServletResponse
     */
    private void generateOtp(HttpServletRequest request,
             HttpServletResponse response)throws ServletException,IOException {
        try {
            String mail = request.getParameter(Constants.EMAILSIGNUP);
            String name = request.getParameter(Constants.USERNAMESIGNUP);
            String password = request.getParameter(Constants.PASSWORDSIGNUP);
            String phone = request.getParameter(Constants.MOBILESIGNUP);

            int otp = adminService.generateOtp(request, mail);
            request.setAttribute(Constants.NAME, name);
            request.setAttribute(EmployeeConstants.LABELPASSWORD, 
                password);
            request.setAttribute(Constants.MAIL, mail);
            request.setAttribute(EmployeeConstants.PHONE, phone);
            request.setAttribute(Constants.ENTERED_OTP, otp);
            request.getRequestDispatcher(Constants.LOGIN_JSP).
                forward(request, response);
        } catch(PMSApplicationException exception) {
            PMSLogger.error(exception.getMessage());
            PMSLogger.info(String.valueOf(exception.getCause()));
            request.getRequestDispatcher(Constants.LOGIN_JSP).
                forward(request, response);
        }
    }

    /**
     * <p>
     * Add a new admin along with their corresponding details.
     * </p>
     * @param  request   HttpServletRequest
     *
     * @param  response  HttpServletResponse
     */
    private void addAdmin(HttpServletRequest request,
             HttpServletResponse response, String enteredOtp, String orginalOtp) 
                 throws PMSApplicationException, ServletException, IOException {
        Admin admin = new Admin();
        if (orginalOtp.equals(enteredOtp)) {
            String mail = request.getParameter(Constants.EMAILSIGNUP);
            String name = request.getParameter(Constants.USERNAMESIGNUP);
            String password = request.getParameter(Constants.PASSWORDSIGNUP);
            String phone = request.getParameter(Constants.MOBILESIGNUP);
            admin.setName(name);
            admin.setPassword(password);
            admin.setMail(mail);
            admin.setMobileNumber(Long.valueOf(phone));
            String id = adminService.createAdmin(admin);
            request.setAttribute(Constants.ERROR_MESSAGE, id);
            request.getRequestDispatcher(Constants.LOGIN_JSP).
                forward(request, response);
        } else {
            request.setAttribute(Constants.ERROR_MESSAGE, Constants.WRONG_OTP);
            request.getRequestDispatcher(Constants.LOGIN_JSP).
                forward(request, response);
        }
    }



    /**
     * <p>
     * Add a new admin along with their corresponding details.
     * </p>
     * @param  request   HttpServletRequest
     *
     * @param  response  HttpServletResponse
     */
    private void addAccessPin(HttpServletRequest request,
            HttpServletResponse response) 
                throws PMSApplicationException, ServletException, IOException {
        Admin admin = new Admin();
        String id = request.getParameter(Constants.LABEL_ID);
        String pin = request.getParameter(Constants.ACCESSPINLABEL);
        admin = adminService.searchAdmin(id, Constants.LABEL_ID);
        if (null != admin) {
            StringBuilder pinMessage = new StringBuilder();
            pinMessage.append(Constants.A).append(id).append(Constants.P).
                append(pin);

           admin.setPin(pinMessage.toString());
           MailUtil.sentMail(request, pinMessage.toString(), admin.getMail(), 
               Constants.QUICKACCESPINSUBJECT);
            adminService.updateAdminPin(admin);
            request.setAttribute(Constants.ACCESSPINLABEL, Constants.ACCESSPINLABEL);
            request.getRequestDispatcher(Constants.ACCESS_JSP).
                forward(request, response);
        } else {
            request.setAttribute(Constants.ERROR_MESSAGE, Constants.WRONGADMIN);
            request.getRequestDispatcher(Constants.ACCESS_JSP).
                forward(request, response);
        }
    }
    
    /**
     * <p>
     * Search whether the given  admin is valid or not with the help of the 
     * given pin.
     * </p>
     * 
     * @param  request   HttpServletRequest
     *
     * @param  response  HttpServletResponse
     */
    private void searchAdminByPin(HttpServletRequest request,
             HttpServletResponse response) 
                 throws PMSApplicationException, ServletException, IOException {
        Admin admin = new Admin();
        RequestDispatcher rd = null;
        String password0 = request.getParameter(Constants.PASSWORD0);
        String password1 = request.getParameter(Constants.PASSWORD1);
        String password2 = request.getParameter(Constants.PASSWORD2);
        String password3 = request.getParameter(Constants.PASSWORD3);
        String password4 = request.getParameter(Constants.PASSWORD4);
        String password5 = request.getParameter(Constants.PASSWORD5);
        String password6 = request.getParameter(Constants.PASSWORD6);
        StringBuilder pinString = new StringBuilder();
        pinString.append(password0).append(password1).append(password2).
            append(password3).append(password4).append(password5).
            append(password6); 
        admin = adminService.searchAdmin(pinString.toString(), Constants.PIN);
       if (null !=  admin)  {
           HttpSession oldSession = request.getSession(Boolean.FALSE);
           if (null != oldSession) {
                oldSession.invalidate();
           }
           HttpSession newSession = request.getSession(Boolean.TRUE);
           newSession.setAttribute(Constants.MAILID, admin.getMail());
           Cookie message = new Cookie(Constants.MESSAGE, admin.getMail());
           response.addCookie(message);
           request.setAttribute("admin" , admin);
           request.getRequestDispatcher(Constants.HOME_JSP).
                forward(request, response);
       } else {
           request.setAttribute(Constants.ERROR_MESSAGE, 
               Constants.INCORRECT_USER);
           rd = getServletContext().getRequestDispatcher(Constants.ACCESS_PATH);
           rd.include(request, response);
       }   
   }

    /**
     * <p>
     * Helps to logout the registered user.
     * </p>
     *
     * @param  request   HttpServletRequest
     *
     * @param  response  HttpServletResponse
     *
     */
    private void logoutAdmin(HttpServletRequest request,
             HttpServletResponse response) throws PMSApplicationException, 
             ServletException, IOException {
        HttpSession session = request.getSession(Boolean.FALSE);
        StringBuilder path = new StringBuilder();
        Cookie[] cookies = request.getCookies();
        if (null != session) {
            session.invalidate();
            for(int i = 0; i < cookies.length; i++) {
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
            }
        path.append(request.getContextPath()).append(Constants.LOGIN_PATH);
        response.sendRedirect(path.toString());
        }
    }

    /**
     * <p>
     * Search a particular admin with the help of mail.
     * </p>
     *
     * @param  request   HttpServletRequest
     *
     * @param  response  HttpServletResponse
     *
     */
    private void searchAdmin(HttpServletRequest request,
             HttpServletResponse response) throws PMSApplicationException, 
             ServletException, IOException {
        RequestDispatcher rd = null;
        Admin admin = new Admin();
        String password = request.getParameter(Constants.PASSWORD);
        String mail = request.getParameter(Constants.USERNAME);
        admin.setName(password);
        admin = adminService.searchAdmin(mail, Constants.MAIL);
        if (null == admin) {       
            request.setAttribute(Constants.ERROR_MESSAGE, 
                Constants.INCORRECT_USER);
            rd = getServletContext().getRequestDispatcher(Constants.LOGIN_PATH);
            rd.include(request, response);
        } else {
            if (admin.getPassword().equals(password)) {
                HttpSession oldSession = request.getSession(Boolean.FALSE);
                if (oldSession != null) {
                    oldSession.invalidate();
                }
                HttpSession newSession = request.getSession(Boolean.TRUE);
                newSession.setAttribute(Constants.MAILID, mail);
                Cookie message = new Cookie(Constants.MESSAGE, mail);
                response.addCookie(message);
                request.getRequestDispatcher(Constants.HOME_JSP).
                    forward(request, response);
            } else {
                request.setAttribute(Constants.ERROR_MESSAGE, 
                    Constants.INCORRECT_PASSWORD);
                rd = getServletContext().getRequestDispatcher(Constants.LOGIN_PATH);
                         rd.include(request, response);
            }
        }
    }
}
