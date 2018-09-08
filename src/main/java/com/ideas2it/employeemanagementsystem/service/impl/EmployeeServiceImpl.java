package com.ideas2it.employeemanagementsystem.service.impl;

import java.util.Set;
import javax.servlet.ServletException;
import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.dao.EmployeeDao;
import com.ideas2it.employeemanagementsystem.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.model.Address;
import com.ideas2it.employeemanagementsystem.model.Employee;
import com.ideas2it.employeemanagementsystem.service.EmployeeService;
import com.ideas2it.employeemanagementsystem.service.ProjectService;
import com.ideas2it.employeemanagementsystem.service.impl.ProjectServiceImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 *<p>
 * Consist of business logics for performing crud operations with the
 * help of the given employee's details and retrieve information from
 * dao for performing those operations.
 * </p>
 *
 * @author Harish
 */
public class EmployeeServiceImpl implements EmployeeService {

    private static EmployeeDao employeeDao;

    private static ProjectService projectService;
  
    public void init() throws ServletException { 
         ApplicationContext context = new GenericXmlApplicationContext("classpath*:/Beans.xml");
    }

    public  void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public  void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * {@inheritDoc}
     */
    public String createEmployee(Employee employee) 
            throws PMSApplicationException {      
        return employeeDao.insertEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    public Employee updateEmpDetail(Employee employee)  
            throws PMSApplicationException {
        return employeeDao.updateEmpDetail(employee);
    }

    /**
     * {@inheritDoc}
     */
    public Employee restoreEmployee(Employee employee) 
            throws PMSApplicationException {
        return employeeDao.restoreEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    public Set<Employee> fetchEmployeesDetailByStatus(String status) 
            throws PMSApplicationException {
        return (employeeDao.retrieveAllEmployeesDetailByStatus(status));
    }


    /**
     * {@inheritDoc}
     */
    public Employee searchEmployeeByIdAndStatus(int employeeId, String status) 
            throws PMSApplicationException {
        return employeeDao.searchEmployeeByIdAndStatus(employeeId, status);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmployeeAlreadyAssigned(Employee employee) 
            throws PMSApplicationException {
        return  projectService.hasEmployeeAssigned(employee);
    }
  
    /**
     * {@inheritDoc}
     */
    public int removeEmployee(Employee employee) 
            throws PMSApplicationException {
        employee.getProjects().clear();
        return employeeDao.deleteEmployee(employee);
    }
}
