package com.ideas2it.employeemanagementsystem.dao;

import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.model.Admin;

/**
 * <p>
 * Helps to perform operations like adding, searching admin details from 
 * the databases. 
 * </p>
 *
 * @author Harish
 */
public interface AdminDao {

    /**
     * <p>
     * Add a new admin with all their corresponding details.
     * </p>
     * 
     * @param     admin     details of the admin
     *
     * @return               id of the admin who was added.
     */
    public String insertAdmin(Admin admin) throws PMSApplicationException;

 
    /**
     * <p>
     * Search a particular admin with the help of the specified admin mail id.
     * </p>
     *
     * @param   adminMail  mailid of the particular admin.
     *
     * @return            Information of that particular admin.
     */
    public Admin searchAdmin(String adminMail, String columnName) 
        throws PMSApplicationException;

     
    /**
     * <p>
     * Update the admin access pin.
     * </p>
     *
     * @param   admin     admin along with the access pin details
     *
     * @return            Information of that particular admin.
     */
    public Admin updateAdminPin(Admin admin) throws PMSApplicationException;
}
