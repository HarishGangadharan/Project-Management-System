package com.ideas2it.employeemanagementsystem.dao;

import java.util.Set;

import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.model.Client;

/**
 * <p>
 * Helps to perform operations like adding, deleting, restoring, display all the
 * client details to the databases. 
 * </p>
 *
 * @author Harish
 */
public interface ClientDao {

    /**
     * <p>
     * Add a new client to the clients with all their corresponding details.
     * </p>
     * 
     * @param     client     details of the client
     *
     * @return               id of the client who was added.
     */
    public String insertClient(Client client) throws PMSApplicationException;

    /**
     * <p>
     * Retrieve all the client details from the clients based on their status.
     * </p>
     *
     * @param   status  specifies whether the status is active or inactive
     *
     * @return          details of all the client.
     */
    public Set<Client> retrieveAllClientsDetailByStatus(String status) 
        throws PMSApplicationException;

     /**
     * <p>
     * Search a particular client from the clients with the
     * help of the specified clientId.
     * </p>
     *
     * @param   clientId  id of the particular client.
     *
     * @param   status    specifies whether the status is active or inactive
     *
     * @return            Information of that particular client.
     */
    public Client searchClientByIdAndStatus(int clientId, String status) 
        throws PMSApplicationException;
    /**
     * <p>
     * Delete the particular client from the clients by obtaining all the
     * details of that particular client from the clients.
     * <p>
     *
     * @param   client      details of the particular client
     *                               to be deleted.
     *
     * @return              id of the client who was deleted.
     */
    public int deleteClient(Client client) throws PMSApplicationException;

    /**
     * <p>
     * Update the client details with the existing one.
     * </p>
     *
     * @param   client      client information with updated details.
     *
     * @return              client information after updation.
     */ 
    public Client updateClientDetail(Client client) 
        throws PMSApplicationException;

    /**
     * <p>
     * Restore the specified client.
     * </p>
     * 
     * @param    client    client information to be restored.
     *
     * @return             client information after restoring.
     */
    public Client restoreClient(Client client) 
        throws PMSApplicationException;
}
