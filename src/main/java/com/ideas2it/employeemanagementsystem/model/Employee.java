package com.ideas2it.employeemanagementsystem.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;


import com.ideas2it.employeemanagementsystem.commons.constants.AddressConstants;
import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.commons.constants.EmployeeConstants;
import com.ideas2it.employeemanagementsystem.commons.constants.ProjectConstants;
import com.ideas2it.employeemanagementsystem.model.Address;
import com.ideas2it.employeemanagementsystem.model.Project;
import com.ideas2it.employeemanagementsystem.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * Assign the user defined input details such as name, designation, mailid,
 * mobile number and dob to the corresponding fields using setters.
 * <p>
 * All the details will be stored in the form of object in which a single
 * object consist of single employee information.
 * </p>
 * @author   Harish
 */
public class Employee {

    private  Integer id;
    private String name;
    private String designation;
    private String mailId;
    private String gender;
    private long mobileNumber;
    private Date dob;
    private String status;
    private Set<Project> projects = new HashSet<Project>();
    private List<Address> addresses = new ArrayList<Address>();

    // Getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setDob(Date dob) {
        this.dob = dob;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getDesignation() {
        return designation;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
  
    public Set<Project> getProjects() {
        return projects;
    }

    public String getMailId() {
        return mailId;
    }
  
    public Integer getId() {
        return id;
    }
    
  
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses.addAll(addresses);
    }
  
    public boolean equals(Employee employee) {
        return (this.getId()).equals(employee.getId());
    }


    /**
     * <p>
     * Used to display all the projects in which the particular employee is 
     * assinged.
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
                projectDetails.append(ProjectConstants.NAME_VALUE).
                    append(project.getName()).
                    append(ProjectConstants.PROJECT_ID_VALUE).
                    append(project.getId());
            }
        }
        return projectDetails;
    }

    /**
     * <p>
     * Used to display addresses of the particular employee.
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
     * Used to display information aboutthe particular employee.
     * </p>
     *
     * @return    displayMessage     all the information about the employee 
     */
    public String toString() {
        StringBuilder displayMessage = new StringBuilder();
        displayMessage.append(EmployeeConstants.EMPID_VALUE).
            append(getId()).
            append(EmployeeConstants.NAME_VALUE).append(getName()).
            append(EmployeeConstants.DESIGNATION_VALUE).
            append(getDesignation()).
            append(EmployeeConstants.MOBILE_NUMBER_VALUE).
            append(getMobileNumber()).
            append(EmployeeConstants.MAIL_ID_VALUE).append(getMailId()).
            append(EmployeeConstants.DOB_VALUE).
            append(getDob()).
            append(Constants.NEW_LINE).
            append(EmployeeConstants.PROJECTS_ALLOCATED_TO_EMP).
            append(displayProjects()).
            append(Constants.NEW_LINE).
            append(AddressConstants.ADDRESS_INFORMATION).
            append(displayAddress());;
      return displayMessage.toString();
    }

}
