package com.ideas2it.employeemanagementsystem.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagementsystem.commons.constants.AddressConstants;
import com.ideas2it.employeemanagementsystem.commons.constants.ClientConstants;
import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.commons.constants.ProjectConstants;
import com.ideas2it.employeemanagementsystem.model.Address;
import com.ideas2it.employeemanagementsystem.model.Project;

/**
 * Assign the user defined input details such as name, requirements, mailid,
 * mobile number and projects to the corresponding fields using setters.
 * <p>
 * All the details will be stored in the form of object in which a single
 * object consist of single client information.
 * </p>
 * @author   Harish
 */

public class Client {

    private  Integer id;
    private String name;
    private String mailId;
    private String requirements;
    private long mobileNumber;
    private String status;
    private Set<Project> projects = new HashSet<Project>();
    private List<Address> addresses = new ArrayList<Address>();

    // Getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public void setProjects(Set<Project> projects) {
        this.projects.addAll(projects);
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses.addAll(addresses);
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getName() {
        return name;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public String getMailId() {
        return mailId;
    }
  

    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }

    public boolean equals(Client client) {
        return (this.getId()).equals(client.getId());
    }
   
    /**
     * <p>
     * Used to display all the projects in which the particular client is 
     * assocaited.
     * </p>
     *
     * @return    projectDetails     all the information about the projects 
     */
    public StringBuilder displayProjects() {
        StringBuilder projectDetails = new StringBuilder();
        if (getProjects().isEmpty()) {
             projectDetails.append(ProjectConstants.NO_PROJECT_FOUND);
        } else {
            for (Project project : getProjects()) {
                projectDetails.append(project).append(Constants.NEW_LINE);
            }
        }
        return projectDetails;
    }


    /**
     * <p>
     * Used to display addresses of the particular client.
     * </p>
     *
     * @return    addressDetails     all the information about the address 
     */
    public StringBuilder displayAddress() {
        StringBuilder addressDetails = new StringBuilder();
        if (getAddresses().isEmpty()) {
             addressDetails.append(AddressConstants.NO_ADDRESS_FOUND);
        } else {
            for (Address address : getAddresses()) {
                addressDetails.append(address).append(Constants.NEW_LINE);
            }
        }
        return addressDetails;
    }

    /**
     * <p>
     * Used to display information aboutthe particular client.
     * </p>
     *
     * @return    displayMessage     all the information about the client 
     */
    public String toString() {
        StringBuilder displayMessage = new StringBuilder();
        displayMessage.append(ClientConstants.CLIENTID_VALUE).
            append(getId()).
            append(ClientConstants.NAME_VALUE).append(getName()).
            append(ClientConstants.REQUIREMENT_VALUE).
            append(getRequirements()).
            append(ClientConstants.MOBILE_NUMBER_VALUE).
            append(getMobileNumber()).
            append(ClientConstants.MAIL_ID_VALUE).append(getMailId()).
            append(Constants.NEW_LINE).append(ClientConstants.CLIENT_PROJECTS).
            append(displayProjects()).append(Constants.NEW_LINE).
            append(AddressConstants.ADDRESS_INFORMATION).
            append(displayAddress());
        return displayMessage.toString();
    }


}
