package com.ideas2it.employeemanagementsystem.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException; 
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.dao.AdminDao;
import com.ideas2it.employeemanagementsystem.dao.GenericDao;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.logger.PMSLogger;
import com.ideas2it.employeemanagementsystem.model.Admin;

public class AdminDaoImpl  extends GenericDao implements AdminDao  {

    private static final String INSERT_EXCEPTION = 
        "'s details cant be inserted.Try again!";

    private static final String SEARCH_EXCEPTION = 
        "'s detail cant be searched.Try again!";

    private static final String UPDATE_EXCEPTION = 
        "'s detail cant be updated with its pin.Try again!";


    /**
     * {@inheritDoc}
     */
    public String insertAdmin(Admin admin)  throws PMSApplicationException {
        Session session = getSession();
        StringBuilder exceptionMessage = new StringBuilder();
        Transaction transaction = null;
        StringBuilder responseMessage = new StringBuilder();
        try {         
            transaction = session.beginTransaction();
            session.save(admin); 
            transaction.commit();
            responseMessage.append(admin.getName()).
                append(Constants.INSERT_MESSAGE).append(admin.getId()).
                append(Constants.RESPECTIVE_ID);
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception.getCause()));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(admin.getName()).append(INSERT_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);       
        } finally {
            session.close();
        }
        return responseMessage.toString();
    }

     /**
     * {@inheritDoc}
     */
    public Admin searchAdmin(String adminMailId, String columnName) 
            throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session =getSession();
        Admin admin = new Admin();
        try {
            Criteria criteria = session.createCriteria(Admin.class);
            if(Constants.MAIL == columnName) {
                criteria.add(Restrictions.eq(columnName, adminMailId));
            } else if (Constants.PIN == columnName) {
                criteria.add(Restrictions.eq(columnName, adminMailId));
            } else {
                criteria.add(Restrictions.eq(columnName, 
                    Integer.parseInt(adminMailId)));
            }

	    admin = (Admin) criteria.uniqueResult();
        } catch(HibernateException exception) {
            PMSLogger.error(String.valueOf(exception.getCause()));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(admin.getMail()).append(SEARCH_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);       
        } finally {
            session.close();
        }
       return admin;
    }


    /**
     * {@inheritDoc}
     */
     public Admin updateAdminPin(Admin admin) throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session = getSession();
        Transaction transaction = null;
        try {           
            transaction = session.beginTransaction();
            session.update(admin); 
            transaction.commit();
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(admin.getId()).append(UPDATE_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);       
        } finally {
            session.close();
        }
        return admin; 
   }
}
