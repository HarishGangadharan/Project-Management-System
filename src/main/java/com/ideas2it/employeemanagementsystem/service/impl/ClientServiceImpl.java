package com.ideas2it.employeemanagementsystem.service.impl;

import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;

import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.dao.ClientDao;
import com.ideas2it.employeemanagementsystem.dao.impl.ClientDaoImpl;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.model.Address;
import com.ideas2it.employeemanagementsystem.model.Client;
import com.ideas2it.employeemanagementsystem.model.Project;
import com.ideas2it.employeemanagementsystem.service.ClientService;
import com.ideas2it.employeemanagementsystem.service.ProjectService;
import com.ideas2it.employeemanagementsystem.service.impl.ProjectServiceImpl;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 *<p>
 * Consist of business logics for performing crud operations with the
 * help of the given client's details and retrieve information from
 * dao for performing those operations.
 * </p>
 *
 * @author Harish
 */
public class ClientServiceImpl implements ClientService {

    private static ClientDao clientDao;

    private static ProjectService projectService;

    public void init() throws ServletException { 
         ApplicationContext context = new GenericXmlApplicationContext("classpath*:/Beans.xml");
    }

    public  void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public  void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }


    /**
     * {@inheritDoc}
     */
    public String createClient(Client client) throws PMSApplicationException {
        return clientDao.insertClient(client);
    }

    /**
     * {@inheritDoc}
     */
    public Client updateClientDetail(Client client) 
            throws PMSApplicationException {
        return clientDao.updateClientDetail(client);
    }

    /**
     * {@inheritDoc}
     */
    public Set<Client> fetchAllClientsByStatus(String status) 
            throws PMSApplicationException  {
        return (clientDao.retrieveAllClientsDetailByStatus(status));
    }

    /**
     * {@inheritDoc}
     */
    public Client searchClientByIdAndStatus(int clientId, String status) 
            throws PMSApplicationException {
        return clientDao.searchClientByIdAndStatus(clientId, status);
    }
 
  
    /**
     * {@inheritDoc}
     */
    public int removeClient(Client client)  throws PMSApplicationException {

        client.getProjects().clear();
        return clientDao.deleteClient(client);
    }

    /**
     * {@inheritDoc}
     */
    public Client restoreClient(Client client) throws PMSApplicationException {
        return clientDao.restoreClient(client);
    }

    /**
     * {@inheritDoc}
     */
    public Set<Project> fetchUnassignedProjects(Set<Project> projects, Client client)
            throws PMSApplicationException {
        Set<Project> clientProjects = new HashSet<Project>();    
        for (Project project : projects) {
            if (null == project.getClientId()) {
                for (Project projectDetail : client.getProjects()) {
                    if (projectDetail.getId() == project.getId()) {
                        clientProjects.add(project);
                    }
                }
            } else {
                clientProjects.add(project);
            }
        }
        return clientProjects;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Project> fetchAllProjects() throws PMSApplicationException {
        return projectService.fetchAllProjectsByStatus(Constants.ACTIVE);
    }
    
    /**
     * {@inheritDoc}
     */
    public Project searchProjectById(int projectId) 
            throws PMSApplicationException  {
        return projectService.searchProjectByIdAndStatus(projectId, 
                                                         Constants.ACTIVE);
    }
}
