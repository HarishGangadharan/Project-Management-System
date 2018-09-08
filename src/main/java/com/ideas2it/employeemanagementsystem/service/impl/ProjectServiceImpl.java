package com.ideas2it.employeemanagementsystem.service.impl;

import java.util.Set;
import java.util.HashSet;
import javax.servlet.ServletException;

import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.dao.ProjectDao;
import com.ideas2it.employeemanagementsystem.dao.impl.ProjectDaoImpl;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.model.Employee;
import com.ideas2it.employeemanagementsystem.model.History;
import com.ideas2it.employeemanagementsystem.model.Project;
import com.ideas2it.employeemanagementsystem.service.EmployeeService;
import com.ideas2it.employeemanagementsystem.service.ProjectService;
import com.ideas2it.employeemanagementsystem.service.impl.EmployeeServiceImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 *<p>
 * Consist of business logics for performing crud operations with the
 * help of the given project's details and retrieve information from
 * dao for performing those operations.
 * </p>
 *
 * @author Harish
 */
public class ProjectServiceImpl implements ProjectService {
   
    private static ProjectDao projectDao;
    private static EmployeeService employeeService;

    public void init() throws ServletException { 
         ApplicationContext context = new GenericXmlApplicationContext("classpath*:/Beans.xml");
    }

    public  void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

  public  void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * {@inheritDoc}
     */
    public String createProject(Project project) 
            throws PMSApplicationException {
         return projectDao.insertProject(project);
    }

    /**
     * {@inheritDoc}
     */
    public Project restoreProject(Project project) 
            throws PMSApplicationException {

        return projectDao.restoreProject(project);
    }

    /**
     * {@inheritDoc}
     */
    public Set<Project> fetchAllProjectsByStatus(String status) 
            throws PMSApplicationException {
        return (projectDao.retrieveAllProjectsByStatus(status));
    }

    /**
     * {@inheritDoc}
     */
    public Project searchProjectByIdAndStatus(int projectId, String status) 
            throws PMSApplicationException {
        return projectDao.searchProjectByIdAndStatus(projectId, status);
    }

    public int removeProject(Project project) throws PMSApplicationException {

        project.getProjectMembers().clear();
       
        return projectDao.deleteProject(project);
    }

    /**
     * {@inheritDoc}
     */
    public Project updateProjectDetail(Project project)
            throws PMSApplicationException {
        return projectDao.updateProjectDetail(project);
    }

    /**
     * {@inheritDoc}
     */
    public Set<Employee> fetchAllEmployees() throws PMSApplicationException {

        return employeeService.fetchEmployeesDetailByStatus(Constants.ACTIVE);
    }

    /**
     * {@inheritDoc}
     */
    public Employee fetchEmployeeDetailById(int employeeId) 
            throws PMSApplicationException {
        return employeeService.searchEmployeeByIdAndStatus(employeeId,
                                                           Constants.ACTIVE);
    }
   
    /**
     * {@inheritDoc}
     */
    public boolean hasEmployeeAssigned(Employee employee) 
            throws PMSApplicationException {
        boolean isEmployeeEngaged = Boolean.FALSE;
        Set<Project> projects = 
            projectDao.retrieveAllProjectsByStatus(Constants.ACTIVE);
        for (Project project : projects) {
            for ( Employee projectMember : project.getProjectMembers()) {
                if (projectMember.equals(employee)) {
                    isEmployeeEngaged = Boolean.TRUE;
                    break;
                }
            }
        }
        return isEmployeeEngaged; 
    }
}
