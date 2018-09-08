package com.ideas2it.employeemanagementsystem.service;

import java.util.Set;

import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.model.Client;
import com.ideas2it.employeemanagementsystem.model.Project;

/**
 * <p>
 * Here we can do service related to client like adding, deleting, restoring
 * retrieving all the client details.
 * </p>
 *
 * @author   Harish
 */
public interface ClientService {

    /**
     * <p>
     * Add the client to the clients along with the details obtained
     * and it can be used for futher manipulation operations. 
     * </p>
     *
     * @param       client      Details of the particular client
     *
     * @return                  id of the client added.
     */
    public String createClient(Client client) throws PMSApplicationException;

    /**
     * <p>
     * Update the modified informations of the client.
     * </p>
     *
     * @param   client      modified informations of the client.
     *
     * @return              client information after updation.
     */
    public Client updateClientDetail(Client client) 
        throws PMSApplicationException;

    /**
     * <p>
     * Retrieve all the existing client which are present in the clients based 
     * on the specified status.
     * </p>
     *
     * @param    status  specified whether the status is active or inactive
     *
     * @return           information of all the clients          
     */
    public Set<Client> fetchAllClientsByStatus(String status) 
        throws PMSApplicationException;

    /**
     * <p>
     * Search the particular client from the clients with the help of the 
     * specified id.
     * </p>
     *
     * @param    clientId    id of the client to be searched.
     *
     * @param    status      specified whether the status is active or inactive
     *
     * @return               details of the particular client.
     */
    public Client searchClientByIdAndStatus(int clientId, String status) 
        throws PMSApplicationException;

    /**
     * <p>
     * Restore the specified client from the existing clients.
     * </p>
     * 
     * @param    client    client information.
     *
     * @return               client information after restoring.
     */
    public Client restoreClient(Client client) 
        throws PMSApplicationException;

    /**
     * <p>
     * removes the specified client from the clients.
     * </p>
     * 
     * @param    client    client information.
     *
     * @return             id of the client which was deleted.
     */
    public int removeClient(Client client) throws PMSApplicationException;

    /**
     * <p>
     * Retreieve all the existing projects for the purpose of assigning to the
     * client.
     * </p>
     *
     * @return               Projects information
     */
    public Set<Project> fetchAllProjects() throws PMSApplicationException;

    /**
     * <p>
     * Search a particular project from the projects with the help of the 
     * specified projectId.
     * </p>
     *
     * @return   project        project information.
     */
    public Project searchProjectById(int projectId) throws PMSApplicationException;


    /**
     * <p>
     * Search all the unassigned projects where the project consist of no clients.
     * </p>
     *
     * @return   project        project information.
     */
    public Set<Project> fetchUnassignedProjects(Set<Project> projects, Client client)
            throws PMSApplicationException;
}
