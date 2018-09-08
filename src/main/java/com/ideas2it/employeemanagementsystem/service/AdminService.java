package com.ideas2it.employeemanagementsystem.service;

import javax.servlet.http.HttpServletRequest;

import com.ideas2it.employeemanagementsystem.model.Admin;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;

/**
 * <p>
 *  Here we can do service related to admin like creating a new admin and allow
 * only valid user.
 * </p>
 *
 * @author   Harish
 */
public interface AdminService {

    /**
     * <p>
     * Add the admin along with the details obtained
     * and it can be used for futher manipulation operations. 
     * </p>
     *
     * @param       admin      Details of the particular admin
     *
     * @return                  id of the admin added.
     */
    public String createAdmin(Admin admin) throws PMSApplicationException;

    /**
     * <p>
     * Update the admin quick access pin. 
     * </p>
     *
     * @param       admin      Details of the particular admin
     *
     * @return                 Updated Admin.
     */
    public Admin updateAdminPin(Admin admin) throws PMSApplicationException;

    /**
     * <p>
     * Search the particular admin with the help of the specified adminMail.
     * </p>
     *
     * @param    adminMail    mailid of the admin to be searched.
     *
     * @param    columnName   column value to be searched
     *
     * @return                details of the particular admin.
     */
    public Admin searchAdmin(String adminMail, String columnName) throws PMSApplicationException;

    /**
     * <p>
     * Helps to generate the otp for the registration purpose.
     * </p>
     *
     * @param    request    HttpServletRequest.
     *
     * @param    mail       Mail to which the otp is to sent.
     *
     * @return              generated otp.
     */
    public int generateOtp(HttpServletRequest request, String mail) 
        throws PMSApplicationException;
}
