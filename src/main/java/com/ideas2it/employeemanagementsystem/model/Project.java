package com.ideas2it.employeemanagementsystem.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.commons.constants.EmployeeConstants;
import com.ideas2it.employeemanagementsystem.commons.constants.ProjectConstants;
import com.ideas2it.employeemanagementsystem.model.Employee;
import com.ideas2it.employeemanagementsystem.model.History;
import com.ideas2it.employeemanagementsystem.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * Assign the user defined project input details such as name, description,
 * members involved in the project and start date of the project to the 
 * corresponding fields using setters.
 * <p>
 * All the details will be stored in the form of object in which a single
 * object consist of single project information.
 * </p>
 *
 * @author   Harish
 */

public class Project {

    private Integer id;
    private String name;
    private String description;
    private Integer clientId;
    private String status;
    private Date startDate;
    private Set<Employee> projectMembers = new HashSet<Employee>();
    private Set<History> histories = new HashSet<History>();

    // Getters and setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setHistories(Set<History> histories) {
        this.histories.addAll(histories);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<History> getHistories() {
        return histories;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProjectMembers(Set<Employee> employees) {
        this.projectMembers.addAll(employees);
    }

    public void removeProjectMembers() {
        this.projectMembers.clear();
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Set<Employee> getProjectMembers() {
        return projectMembers;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getStartDate() {
        return startDate;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
    
    public Integer getClientId() {
        return clientId;
    }

    public Integer getId() {
        return id;
    }

 
    public boolean equals(Project project) {
        return (this.getId()).equals(project.getId());
    }
   
    public int hashCode() {
        return (this.getId().hashCode());
    } 

    /**
     * <p>
     * Used to display information about the particular project which includes 
     * the name,description, start date of the project and employees who are
     * working in the project.
     * </p>
     *
     * @return    displayMessage     all the information about the project 
     */    
    public String toString() {
        StringBuilder displayMessage = new StringBuilder();
        displayMessage.append(ProjectConstants.PROJECT_ID_VALUE).
            append(getId()).            
            append(ProjectConstants.NAME_VALUE).append(getName()).
            append(ProjectConstants.DESCRIPTION_VALUE).
            append(getDescription()).
            append(ProjectConstants.CLIENT_VALUE).append(getClientId()).
            append(ProjectConstants.START_DATE_VALUE).
            append((getStartDate())).
            append(Constants.NEW_LINE).
            append(ProjectConstants.LABEL_PROJ_MEMBERS);
        if (projectMembers.isEmpty()) {
            displayMessage.append(ProjectConstants.
                                  NO_PROJECT_MEMBER_AVAILABLE);
        } else {
            for (Employee employee : projectMembers) {
                displayMessage.append(employee).             
                    append(Constants.NEW_LINE);
            }
        }
        return displayMessage.toString();
    }

}
