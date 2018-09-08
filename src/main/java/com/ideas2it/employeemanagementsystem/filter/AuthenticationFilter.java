package com.ideas2it.employeemanagementsystem.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.logger.PMSLogger; 
import com.ideas2it.employeemanagementsystem.utils.MailUtil;
/**
 * <p>
 * Helps to allow only authorized user only and all the operations will be
 * happened via this authenticationfiler.
 * </p>
 *
 *
 */ 
public class AuthenticationFilter implements Filter {

    private ServletContext context;
    
    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
    }
 
    /**
     * <p>
     * Helps to allow only authorized user and prevents from external access.
     * </p>
     *
     * @param   request     ServletRequest
     *
     * @param   response    ServletResponse
     *
     * @param   chain       FilterChain
     */ 
    public void doFilter(ServletRequest request, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(Boolean.FALSE);
        String path= req.getRequestURI();
        StringBuilder message = new StringBuilder();
        message.append(Constants.WARNING).append(req.getRemoteAddr()).
            append(Constants.ACCESS_ERROR).append(path);   

        if (!req.getRemoteAddr().equals(Constants.IP)) {  
            try {  
            MailUtil.sentMail(req, message.toString(), 
                Constants.MAILIDADDRESS, Constants.UNAUTHORIZED_ACCESS_ERROR);
            } catch(PMSApplicationException exception) {
                PMSLogger.error(exception.getMessage());
                PMSLogger.info(String.valueOf(exception.getCause()));
               request.getRequestDispatcher(Constants.ERRORJSP).
                   forward(request, response);
            }
        }

        if (path.endsWith(Constants.CSS) || path.endsWith(Constants.JPEG)) {
            chain.doFilter(request,response);
            return;
        }     

        if ( null != session && null == session.getAttribute(Constants.MAILID) 
               && (path).equals(Constants.ADMINCONTROLLERPATH)) {
            chain.doFilter(request, response);
        } else  if (null != session && null != session.getAttribute(
                    Constants.MAILID)){
            if ((path).equals(Constants.PROJECTPATH)) {
                req.getRequestDispatcher(Constants.HOME_JSP).
                    forward(request,response);
            }

            if ((path).equals(Constants.REGISTERPATH)) {
                req.getRequestDispatcher(Constants.HOME_JSP).forward(
                    request,response);
            }

            if (path.endsWith(Constants.EMPLOYEECONTROLLER) && 
                   (req.getMethod().endsWith(Constants.GET))) {
                req.getRequestDispatcher(Constants.EMPLOYEE_JSP).forward(
                    request,response);
            }

            if (path.endsWith(Constants.CLIENTCONTROLLER) && 
                    (req.getMethod().endsWith(Constants.GET))) {
                req.getRequestDispatcher(Constants.CLIENT_JSP).forward(
                    request,response);
            }

            if (path.endsWith(Constants.PROJECTCONTROLLER) && 
                    (req.getMethod().endsWith(Constants.GET))) {
                req.getRequestDispatcher(Constants.PROJECT_JSP).forward(
                    request,response);
            }

            if (path.endsWith(Constants.ADMINCONTROLLER) && 
                    (req.getMethod().endsWith(Constants.GET))) {
                req.getRequestDispatcher(Constants.HOME_JSP).forward(
                    request,response);
            }

            if (!(path).equals(Constants.LOGINPATH)) {
                chain.doFilter(request, response);
           
            } else {
                req.getRequestDispatcher(Constants.HOME_JSP).forward(
                    request,response);
            }
        } else if ((path).equals(Constants.REGISTERPATH)) {
            req.getRequestDispatcher(Constants.REGISTERJSP).forward(
                request,response);
        } else if ((path).equals(Constants.LOGINPATH)) {
            req.getRequestDispatcher(Constants.LOGIN_JSP).forward(
                request,response);
        } else if ((path).equals(Constants.ACCESSPATH)) {
            req.getRequestDispatcher(Constants.ACCESS_JSP).forward(
                request,response);
        } else {
            req.getRequestDispatcher(Constants.LOGIN_JSP).forward(
                request,response);
        }
    }

    /**
     * <p>
     * Destory method
     * </p>
     */
    public void destroy() {

    }
}
