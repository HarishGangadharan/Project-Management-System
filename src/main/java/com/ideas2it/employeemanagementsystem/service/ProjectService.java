package com.ideas2it.employeemanagementsystem.service;

import java.util.Set;

import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.model.Employee;
import com.ideas2it.employeemanagementsystem.model.Project;

/**
 * <p>
 * Here we can do service related to project like adding, deleting, restoring
 * retrieving all the project details. 
 * </p>
 *
 * @author    Harish
 */
public interface ProjectService {

    /**
     * <p>
     * Add the project to the projects along with the details obtained
     * and it can be used for futher manipulation operations. 
     * </p>
     *
     * @param       project      Details of the particular project
     *
     * @return                   id of the project added.
     */
    public String createProject(Project project) throws PMSApplicationException;

    /**
     * <p>
     * Retrieve all the existing project which are present in the projects
     * for the purpose of displaying and searching a particular project based on 
     * their status;
     * </p>
     *
     * @param    status      specifies whether it is active or inactive
     *
     * @return               information of all the project based on the given 
     *                       status.        
     */
    public Set<Project> fetchAllProjectsByStatus(String status) 
        throws PMSApplicationException;

    /**
     * <p>
     * Search the particular project from the projects
     * with the help of the specified id.
     * </p>
     *
     * @param     projectId  id of the project to be searched.
     *
     * @param    status      specifies whether it is active or inactive
     *
     * @return               details of the particular project.
     */
    public Project searchProjectByIdAndStatus(int projectId, String status)
        throws PMSApplicationException;

    /**
     * <p>
     * remove the specified project which is available in the existing projects.
     * </p>
     * 
     * @param    project   project which is to be deleted.
     *
     * @return             id of the project which was deleted.
     */
    public int removeProject(Project project) throws PMSApplicationException;

    /**
     * <p>
     * Restore the specified project from the existing projects.
     * </p>
     * 
     * @param    project    project information to be restored.
     *
     * @return              project information after restoring.
     */
    public Project restoreProject(Project project) 
        throws PMSApplicationException;

    /**
     * <p>
     * Update the project detail to the projects by obtaining
     * all the modified information of the project.
     * </p>
     *
     * @param   project      modified informations of the project.
     *
     *
     * @return               project information after updation.
     */
    public Project updateProjectDetail(Project project) 
        throws PMSApplicationException;

    /**
     * <p>
     * Fetch all the details of the employee by obtaining the 
     * employees for the purpose of assigning them to the project.
     * </p>
     *
     * @return             all the existing employees.
     */
    public Set<Employee> fetchAllEmployees() throws PMSApplicationException;

    /**
     * <p>
     * Fetch the details of the particular employee from the 
     * employees with the help of employeeId.
     * </p>
     *
     * @param   employeeId    id of that particular employee.
     *
     * @return  Employee      employee information
     */
    public Employee fetchEmployeeDetailById(int employeeId) 
        throws PMSApplicationException;

    /**
     * <p>
     * Checks whether the employee is engaged with any other 
     * project.If so he can't be assigned because an employee
     * can work only in one project.
     * </p>
     *
     * @param   employee    employee details 
     *
     * @return  true        when employee is not engaged with
     *                      any other projects.
     *
     *          false       when employee is working with some
     *                      other projects.
     */
    public boolean hasEmployeeAssigned(Employee employee) 
        throws PMSApplicationException;
}
