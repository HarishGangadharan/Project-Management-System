package com.ideas2it.employeemanagementsystem.dao;

import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException; 
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.logger.PMSLogger;

/**
 * <p>
 * Used for getting a singleton session connection from the established session 
 * factory.
 * </p>
 *
 * @author   Harish
 */
public class GenericDao {

    private static SessionFactory sessionFactory = null;

    private static final String HIBERNATE_EXCEPTION = 
        "Error: Failed to create sessionFactory object. Try again!";

    private static final String HIBERNATE_FILE = 
        "hibernate.cfg.xml";
  
    /**
     * <p>
     * Helps to create a session factory.
     * </p>
     */
    private  void createSessionFactory() throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        try {
            sessionFactory = new Configuration().configure(HIBERNATE_FILE).
                buildSessionFactory();
        } catch (HibernateException exception) { 
            PMSLogger.error(exception.toString());
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(HIBERNATE_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                exception);       
        }
    }
    
    /**
     * <p>
     * Creates a session with the help of the session factory.
     * </p>
     *
     * @param                session.
     */
    protected Session getSession() throws PMSApplicationException {
        if (null == sessionFactory) {
            createSessionFactory(); 
        }
        return sessionFactory.openSession();
    }
}
