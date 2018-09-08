package com.ideas2it.employeemanagementsystem.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;


import com.ideas2it.employeemanagementsystem.commons.constants.ClientConstants;
import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.commons.constants.ProjectConstants;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.logger.PMSLogger; 
import com.ideas2it.employeemanagementsystem.model.Address;
import com.ideas2it.employeemanagementsystem.model.Client;
import com.ideas2it.employeemanagementsystem.model.Project;
import com.ideas2it.employeemanagementsystem.service.ClientService;
import com.ideas2it.employeemanagementsystem.service.impl.ClientServiceImpl;
import org.apache.commons.io.IOUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;  

/**
 * Obtain all the details about the client and use them for futher
 * manipulations.
 * <p>
 * Helps in creating, deleting, searching a particular client and
 * updating the client detail and displaying the clients detail 
 * and modified client detail.
 * </p>
 *
 * @author Harish
 */
@Controller
public class ClientController extends HttpServlet {

    private static ClientService clientService;


    public void init() throws ServletException { 
        ApplicationContext context = 
            new GenericXmlApplicationContext("classpath*:/Beans.xml");
    }

    public  void setClientService(ClientService clientService) {
        this.clientService = clientService;

    }


    /**
     * <p>
     * Helps to redirect to the client creation page.
     * </p>
     *
     * @return    ModelAndView   Display the client creation page.
     */
    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public ModelAndView showCreateForm() {
        ModelAndView mav = new ModelAndView(Constants.CLIENTLABEL);
        Client client = new Client();
        return new ModelAndView(Constants.CLIENT_JSP,Constants.CLIENTLABEL, client);
    }


    /**
     * <p>
     * Helps to add the client information.
     * </p>
     *
     * @param     client         client with all thier information.
     *
     * @return    ModelAndView   Display the client creation page.
     */
    @RequestMapping(value = "/submitClient", method = RequestMethod.POST)
    public ModelAndView addClient(@ModelAttribute(Constants.CLIENTLABEL)
        Client client)  {
        try {
            Set<Client> clients = null;
            Set<Project> projects = null;
            Set<Project> freeProjects = new HashSet<Project>();
            ModelAndView mav = new ModelAndView(ClientConstants.ASSIGNPROJECTSTOCLIENT);
            String responseMessage = clientService.createClient(client);
            clients = clientService.fetchAllClientsByStatus(Constants.ACTIVE);
            mav.addObject(Constants.CLIENTS, clients);
            mav.addObject(Constants.RESPONSEMESSAGE, responseMessage);
            projects = clientService.fetchAllProjects();
            for (Project project : projects) {
                if ( null == project.getClientId()) {
                     freeProjects.add(project);
                 }
            }
            mav.addObject(Constants.RESPONSE_MESSAGE, responseMessage);
            mav.addObject(ClientConstants.PROJECTS, freeProjects);
            mav.addObject(ClientConstants.LABELCLIENT,client);
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;

        }

    }



    /**
     * <p>
     * Helps to search a particular client with the specified id and status.
     * </p>
     *
     * @param     id             id of the client to be searched.
     *
     * @param     status         status of the client to be searched.
     *
     * @param     client         client object to store all the information.
     *
     * @return    ModelAndView   Display the  client jsp page.
     */
    @RequestMapping(value = "/client", method = RequestMethod.POST, params = "search")
    public ModelAndView searchClientDetail(@RequestParam(Constants.LABEL_ID) String id, 
            @RequestParam(Constants.LABEL_STATUS) String status, 
            @ModelAttribute(Constants.CLIENTLABEL) Client client) {
        try {
            StringBuilder errorMessage = new StringBuilder();
            client = clientService.searchClientByIdAndStatus(Integer.parseInt(id), 
                status);
            if (null != client ) {
                return new ModelAndView(Constants.CLIENT_JSP,Constants.CLIENTLABEL, client);
            } else {
                errorMessage.append(ClientConstants.ERRORMESSAGE1).append(id).
                    append(ClientConstants.ERRORMESSAGE2);
                ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
                mav.addObject(Constants.ERROR , errorMessage.toString());
                return mav;
            }
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }
    }

  
    /**
     * <p>
     * Helps to update a particular client.
     * </p>
     *
     * @param     client         client with all the information.
     *
     * @return    ModelAndView   Display the  client jsp page.
     */
    @RequestMapping(value = "/clientOperation", method = RequestMethod.POST, 
        params = "Update")
    public ModelAndView updateClient(@ModelAttribute(Constants.CLIENTLABEL)
            Client client)   {
        try {
            Set<Client> clients = null;
            ModelAndView mav = new ModelAndView(ClientConstants.CLIENTDETAILJSP);
            clients = clientService.fetchAllClientsByStatus(Constants.ACTIVE);
            mav.addObject(Constants.CLIENTS, clients);
            clientService.updateClientDetail(client);
            mav.addObject(Constants.UPDATEDID, client.getId());
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }
    }


    /**
     * <p>
     * Helps to assign the projects to client.
     * </p>
     *
     * @param     client         client with all thier information.
     *
     * @return    ModelAndView   Display the client creation page.
     */
    @RequestMapping(value="/clientOperation", method=RequestMethod.POST, params="assign")
    public ModelAndView assignProjects(@ModelAttribute(Constants.CLIENTLABEL)
            Client client, 
            HttpServletRequest request) throws ServletException, IOException  {
        try {
            Set<Project> projects = new HashSet<Project>();
            Project project;
            StringBuilder errorMessage = new StringBuilder();
            String[] projectsId = request.getParameterValues(
                                  ProjectConstants.CHECKBOX);
            String id = request.getParameter("id");
            client = clientService.searchClientByIdAndStatus(
                            Integer.parseInt(id), Constants.ACTIVE);
            ModelAndView mav = new ModelAndView(ClientConstants.ASSIGNPROJECTSTOCLIENT);
            if (null != projectsId) {
                for (String projectId :projectsId) {
                    project =  clientService.searchProjectById(
                        Integer.parseInt(projectId));
                    projects.add(project);
                }
                client.setProjects(projects);
                client = clientService.updateClientDetail(client);
                mav.addObject(ClientConstants.LABELCLIENT, client);
                mav.addObject(ClientConstants.PROJECTS, client.getProjects());
                mav.addObject(ProjectConstants.PROJECTMEMBERS, 
                                 ProjectConstants.PROJECTMEMBERS);
        
            } else {
                mav.addObject(ClientConstants.LABELCLIENT, client);
                mav.addObject(ClientConstants.PROJECTS, client.getProjects());
                mav.addObject(ProjectConstants.PROJECTMEMBERS, 
                                 ProjectConstants.PROJECTMEMBERS);
            }
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }
    }

    /**
     * <p>
     * Display the project details for adding the projects to the 
     * client.
     * </p>
     *
     * @param     client         client with all thier information.
     *
     * @return    ModelAndView   Display the client creation page.
     */
    @RequestMapping(value="/clientOperation", method=RequestMethod.POST,
        params = "AddProject")
    public ModelAndView displayProjects(@ModelAttribute(Constants.CLIENTLABEL) 
            Client client, 
            HttpServletRequest request) 
            throws ServletException, IOException  {
        try {
            String id = request.getParameter("clientId");
            client = clientService.searchClientByIdAndStatus(
                            Integer.parseInt(id), Constants.ACTIVE);
            ModelAndView mav = new ModelAndView(ClientConstants.ASSIGNPROJECTSTOCLIENT);
            Set<Project> projects = clientService.fetchAllProjects();
            projects.removeAll(clientService.fetchUnassignedProjects(projects, client));
            mav.addObject(ClientConstants.LABELCLIENT, client);
            mav.addObject(ClientConstants.PROJECTS, projects);
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }
    }


    /**
     * <p>
     * remove the project from the client.
     * </p>
     *
     * @param     client         client with all thier information.
     *
     * @return    ModelAndView   Display the client creation page.
     */
    @RequestMapping(value="/RemoveProject", method=RequestMethod.POST)
    public ModelAndView RemoveProject(@ModelAttribute(Constants.CLIENTLABEL) 
            Client client, HttpServletRequest request) 
            throws  ServletException, IOException  {
        try {
            ModelAndView mav = new ModelAndView(ClientConstants.ASSIGNPROJECTSTOCLIENT);
            Set<Project> projects = null;
            String id = request.getParameter(ClientConstants.PROJECTID);
            String clientId = request.getParameter(ClientConstants.CLIENTID);
            Project projectDetail = null;
            client = clientService.searchClientByIdAndStatus(
                            Integer.parseInt(clientId), Constants.ACTIVE);
            projects =  client.getProjects();
            for (Project project : projects) {
                if (Integer.parseInt(id) == project.getId()) {
                    projectDetail = project;
                }
            }
            client.getProjects().remove(projectDetail);
            client = clientService.updateClientDetail(client);
            mav.addObject(ClientConstants.LABELCLIENT, client);
            mav.addObject(ClientConstants.PROJECTS, client.getProjects());
            mav.addObject(ProjectConstants.PROJECTMEMBERS, 
                                 ProjectConstants.PROJECTMEMBERS);
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

    /**
     * <p>
     * Update the client information.
     * </p>
     *
     * @param     client         client with all thier information.
     *
     * @return    ModelAndView   Display the client creation page.
     */
    @RequestMapping(value="/clientOperation", method=RequestMethod.POST,
        params="UpdateClient")
    public ModelAndView UpdateClientInformation(@ModelAttribute(Constants.CLIENTLABEL) 
           Client client)   {
        try {
            client = clientService.searchClientByIdAndStatus(
                        client.getId(), Constants.ACTIVE);
            return new ModelAndView(Constants.CLIENT_JSP,Constants.CLIENTLABEL, client);
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

    /**
     * <p>
     * Helps to search a particular client with the specified id and status.
     * </p>
     *
     * @param     id             id of the client to be searched.
     *
     * @param     status         status of the client to be searched.
     *
     * @param     client         client object to store all the information.
     *
     * @return    ModelAndView   Display the  client jsp page.
     */
    @RequestMapping(value = "/searchClient", method = RequestMethod.POST)
    public ModelAndView searchClient(@RequestParam("id") String id, 
            @RequestParam(Constants.LABEL_STATUS) String status, 
            @ModelAttribute(Constants.CLIENTLABEL) Client client) {
        try {
            client = clientService.searchClientByIdAndStatus(Integer.parseInt(id), 
                status);
            return new ModelAndView(Constants.CLIENT_JSP,Constants.CLIENTLABEL, client);
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

    /**
     * <p>
     * Helps to restore a particular client.
     * </p>
     *
     * @param     client         client with all the information.
     *
     * @return    ModelAndView   Display the  client jsp page.
     */
    @RequestMapping(value = "/restoreClient", method = RequestMethod.POST)
    public ModelAndView restoreClientDetail(
            @ModelAttribute(Constants.CLIENTLABEL) Client client)  {
        try {
            Set<Client> clients = null;
            ModelAndView mav = new ModelAndView(ClientConstants.CLIENTDETAILJSP);
            clientService.restoreClient(client);
            clients = clientService.fetchAllClientsByStatus(Constants.INACTIVE);
            mav.addObject(Constants.CLIENTS, clients);
            mav.addObject(Constants.RESTOREDID, client.getId());
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

    /**
     * <p>
     * Helps to delete a particular client.
     * </p>
     *
     * @param     client         client with all the information.
     *
     * @return    ModelAndView   Display the  client jsp page.
     */
    @RequestMapping(value = "/clientOperation", method = RequestMethod.POST, params = "Delete")
    public ModelAndView deleteClient(@ModelAttribute(Constants.CLIENTLABEL)
            Client client)   {
        try {
            Set<Client> clients = null;
            ModelAndView mav = new ModelAndView(ClientConstants.CLIENTDETAILJSP);
            clientService.removeClient(client);
            clients = clientService.fetchAllClientsByStatus(Constants.ACTIVE);
            mav.addObject(Constants.CLIENTS, clients);
            mav.addObject(Constants.DELETEDID, client.getId());
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    } 

    /**
     * <p>
     * Helps to cancel the page.
     * </p>
     *
     * @param     client         client with all the information.
     *
     * @return    ModelAndView   Display the  client jsp page.
     */
    @RequestMapping(value = "/clientOperation", method = RequestMethod.POST, 
        params = "Cancel")
    public ModelAndView Cancel() {
        ModelAndView mav = new ModelAndView(Constants.CLIENTLABEL);
        Client client = new Client();
        return new ModelAndView(Constants.CLIENT_JSP,Constants.CLIENTLABEL, client);
    }

    /**
     * <p>
     * Helps to search a particular client with the specified id and status.
     * </p>
     *
     * @param     id             id of the client to be searched.
     *
     * @param     status         status of the client to be searched.
     *
     * @param     client         client object to store all the information.
     *
     * @return    ModelAndView   Display the  client jsp page.
     */
    @RequestMapping(value = "/clientOperation", method = RequestMethod.POST,
        params = "Restore")
    public ModelAndView restoreClient(@ModelAttribute(Constants.CLIENTLABEL) 
            Client client)    {
        try {
            Set<Client> clients = null;
            ModelAndView mav = new ModelAndView(ClientConstants.CLIENTDETAILJSP);
            clientService.restoreClient(client);
            clients = clientService.fetchAllClientsByStatus(Constants.INACTIVE);
            mav.addObject(Constants.CLIENTS, clients);
            mav.addObject(Constants.RESTOREDID, client.getId());
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

    /**
     * <p>
     * Helps to display the clients.
     * </p>
     *
     * @return    ModelAndView   Display the  client jsp page.
     */
    @RequestMapping(value = "/client", method = RequestMethod.POST, 
        params = "display")
    public ModelAndView displayAllClients(@RequestParam(Constants.LABEL_STATUS) String status,
            @ModelAttribute(Constants.CLIENTLABEL) Client client) {
        try {
            Set<Client> clients = null;
            clients = clientService.fetchAllClientsByStatus(status);
            return new ModelAndView(ClientConstants.CLIENTDETAILJSP, Constants.CLIENTS, 
                 clients);
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    } 

    /**
     * <p>
     * Helps to delete a particular client with the specified id.
     * </p>
     *
     * @param     id             id of the client to be searched.
     *
     * @param     client         client object to store all the information.
     *
     * @return    ModelAndView   Display the  client jsp page.
     */
    @RequestMapping(value = "/deleteClient", method = RequestMethod.POST)
    public ModelAndView deleteClientDetails(@RequestParam("id") String id,
            @ModelAttribute(Constants.CLIENTLABEL) Client client) {
        try {
            client = clientService.searchClientByIdAndStatus(Integer.parseInt(id), 
                Constants.ACTIVE);
            ModelAndView mav = new ModelAndView(ClientConstants.CLIENTDETAILJSP);
            Set<Client> clients = null;
            clientService.removeClient(client);
            clients = clientService.fetchAllClientsByStatus(Constants.ACTIVE);
            mav.addObject(Constants.CLIENTS, clients);
            mav.addObject(Constants.DELETEDID, client.getId());
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

    /**
     * <p>
     * Helps to add the address details to a particular client.
     * </p>
     *
     * @param     id             id of the client to be searched.
     *
     * @param     client         client object to store all the information.
     *
     * @return    ModelAndView   Display the  client jsp page.
     */
    @RequestMapping(value = "/clientOperation", method = RequestMethod.POST, 
        params = "addAddress")
    public ModelAndView addAddress(@ModelAttribute(Constants.CLIENTLABEL) 
            Client client) {
        List<Address> addresses = new ArrayList<Address>();
        Address permanentAddress = new Address();
        permanentAddress.setType(Constants.PERMANENT);
        addresses.add(permanentAddress);
        Address temporaryAddress = new Address();
        temporaryAddress.setType(Constants.TEMPORARY);
        addresses.add(temporaryAddress);
        client.setAddresses(addresses);
        return new ModelAndView("address.jsp",Constants.CLIENTLABEL, client);
    }

    /**
     * <p>
     * Helps to create a  text file with all the client  detail.
     * </p>
     *
     * @param     status         status of the project to be searched.
     *
     * @param     client         client object to store all the information.
     *
     * @return    ModelAndView   Display the  client jsp page.
     */
    @RequestMapping(value="/createClientFile", method=RequestMethod.POST)
    public ModelAndView createFile(@RequestParam(Constants.LABEL_STATUS) String status, 
            @RequestParam("filename") String name, HttpServletRequest request,
            @ModelAttribute(Constants.CLIENTLABEL) Client client)
            throws  ServletException, IOException {
        ModelAndView mav = new ModelAndView(ClientConstants.CLIENTDETAILJSP);
        InputStream inputStream = null;
        Set<Client> clients = null;
        OutputStream outputStream = null;
	int end;
        final int EOF = -1;
        StringBuilder builder = new StringBuilder();
        StringBuilder pathName = new StringBuilder();
        StringBuilder exceptionMessage = new StringBuilder();
        StringBuilder destinationPath = new StringBuilder();

    	try {
            clients = clientService.fetchAllClientsByStatus(status);
            if (clients.isEmpty()) {
                builder.append(Constants.NO_CLIENT);
            } else {
                for (Client clientInfo : clients) { 
                    builder.append(clientInfo.toString());
                }
            }
            inputStream =IOUtils.toInputStream(builder.toString(),
                Constants.UTF);
            pathName.append(Constants.FILEPATH).append(name);  
            File outFile = new File(pathName.toString()); 
            outputStream = new FileOutputStream(outFile);
            while ((end = inputStream.read()) != EOF) {
                outputStream.write(end);
            }
            exceptionMessage.append(Constants.ERRORFILENOTCREATED);
            destinationPath.append(Constants.DESTINATIONPATH).append(name);
            mav.addObject(Constants.CLIENTS, clients);
            mav.addObject(Constants.FILEPRESENT,Constants.TRUE);
            mav.addObject(Constants.FILE, destinationPath.toString());
            return mav;
        } catch (IOException exception) {
            ModelAndView mav1 = new ModelAndView(Constants.ERRORJSP);
            mav1.addObject(Constants.ERROR, exception.getMessage());
            return mav1;

        } catch(PMSApplicationException exception) {
            ModelAndView mav2 = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav2.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }
}
