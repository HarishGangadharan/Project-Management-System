package com.ideas2it.employeemanagementsystem.model;

import java.util.Date;
import com.ideas2it.employeemanagementsystem.utils.DateUtil;
import com.ideas2it.employeemanagementsystem.commons.constants.ProjectConstants;

/**
 * Assign the user defined input details such as name, requirements, mailid,
 * mobile number and projects to the corresponding fields using setters.
 * <p>
 * All the details will be stored in the form of object in which a single
 * object consist of single client information.
 * </p>
 * @author   Harish
 */

public class History {

    private Integer projectId;
    private Integer employeeId;
    private Integer historyId;
    private Date empJoiningDate;
    private Date empLeavingDate;

    // Getters and setters
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public void setEmpLeavingDate(Date empLeavingDate) {
        this.empLeavingDate = empLeavingDate;
    }

    public void setEmpJoiningDate(Date empJoiningDate) {
        this.empJoiningDate = empJoiningDate;
    }
 
    public Integer getProjectId() {
        return projectId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public Integer getHistoryId() {
        return historyId;
    }

    public Date getEmpJoiningDate() {
        return empJoiningDate;
    }

    public Date getEmpLeavingDate() {
        return empLeavingDate;
    }
}
