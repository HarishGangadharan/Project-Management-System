package com.ideas2it.employeemanagementsystem.dao.impl;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;

import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.dao.ClientDao;
import com.ideas2it.employeemanagementsystem.dao.GenericDao;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.logger.PMSLogger;
import com.ideas2it.employeemanagementsystem.model.Client;

/**
 * <p>
 * Add the employee details to the employees and retrieves all the
 * employee information for updation process and performs deletion operation by
 * specifying an employeeId where id helps to uniquely identify a record in the
 * employees.
 * </p>
 * 
 * @author Harish
 */
public class ClientDaoImpl  extends GenericDao implements ClientDao  {

    private static final String INSERT_EXCEPTION = 
        "'s details cant be inserted.Try again!";

    private static final String RETRIEVE_EXCEPTION = 
        "Error Occured : clients detail cant be viewed.Try again!";

    private static final String SEARCH_EXCEPTION = 
        "'s detail cant be searched.Try again!";

    private static final String DELETE_EXCEPTION = 
        "'s details cant be deleted.Try again!";

    private static final String UPDATE_EXCEPTION = 
        "'s details cant be updated.Try again!";

    private static final String RESTORE_EXCEPTION = 
        "'s details cant be restored.Try again!";

    /**
     * {@inheritDoc}
     */
    public String insertClient(Client client) throws PMSApplicationException {
        Session session = getSession();
        StringBuilder exceptionMessage = new StringBuilder();
        Transaction transaction = null;
        client.setStatus(Constants.ACTIVE);
        StringBuilder responseMessage = new StringBuilder();
        try {         
            transaction = session.beginTransaction();
            session.save(client); 
            transaction.commit();
            responseMessage.append(client.getName()).
                append(Constants.INSERT_MESSAGE).append(client.getId()).
                append(Constants.RESPECTIVE_ID);
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(client.getName()).append(INSERT_EXCEPTION);
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
    public Set<Client> retrieveAllClientsDetailByStatus(String status) 
            throws PMSApplicationException {
        Set<Client> clients = new HashSet<Client>();
        StringBuilder exceptionMessage = new StringBuilder();
        Session session = getSession();
        List clientsDetail;
        try {
            clientsDetail = session.createCriteria(Client.class).
                add(Restrictions.eq(Constants.LABEL_STATUS, status)).
                list();
            clients.addAll(clientsDetail);
        } catch(HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            throw new PMSApplicationException(RETRIEVE_EXCEPTION, exception);
        } finally {
            session.close();
        }
        return clients;
   }

     /**
     * {@inheritDoc}
     */
    public int deleteClient(Client client) throws PMSApplicationException {
        Session session = getSession();
        client.setStatus(Constants.INACTIVE);
        StringBuilder exceptionMessage = new StringBuilder();
        Transaction transaction = null;
        try {           
            transaction = session.beginTransaction();
            session.update(client);
            transaction.commit();
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(client.getName()).append(DELETE_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);       
        } finally {
            session.close();
        }
         return client.getId();
    }
 
    /**
     * {@inheritDoc}
     */
    public Client restoreClient(Client client) throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session = getSession();
        client.setStatus(Constants.ACTIVE);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();                       
            session.update(client);
            transaction.commit();
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(client.getName()).append(RESTORE_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);          
        } finally {
            session.close();
        }
         return client;
    }

     /**
     * {@inheritDoc}
     */
    public Client searchClientByIdAndStatus(int clientId, String status) 
            throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session = getSession();
        Client client = new Client();
        try {
            Criteria criteria = session.createCriteria(Client.class);

            criteria.add(Restrictions.eq(Constants.LABEL_ID, clientId));
            criteria.add(Restrictions.eq(Constants.LABEL_STATUS, status));
	    client = (Client) criteria.uniqueResult();
        } catch(HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(client.getId()).append(SEARCH_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);       
        } finally {
            session.close();
        }
       return client;
    }
 
    /**
     * {@inheritDoc}
     */
    public Client updateClientDetail(Client client) 
            throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session = getSession();
        client.setStatus(Constants.ACTIVE);
        Transaction transaction = null;
        try {           
            transaction = session.beginTransaction();
            session.update(client); 
            transaction.commit();
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(client.getId()).append(UPDATE_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);       
        } finally {
            session.close();
        }
        return client; 
   }
}
