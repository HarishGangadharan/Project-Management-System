package com.ideas2it.employeemanagementsystem.service;

import java.util.Set;

import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.model.Employee;

/**
 * <p>
 * Here we can do service related to employee like adding, deleting, restoring
 * retrieving all the employee details.
 * </p>
 *
 * @author   Harish
 */
public interface EmployeeService {

    /**
     * <p>
     * Add the employee to the employees along with the obtained details
     * and it can be used for futher manipulation operations. 
     * </p>
     *
     * @param       employee      Details of the particular employee
     *
     * @return                    id of the employee added.
     */
    public String createEmployee(Employee employee) 
        throws PMSApplicationException;

    /**
     * <p>
     * Update the modified informations of the employee to employees.
     * </p>
     *

     * @param   employee      modified informations of the employee.
     *
     * @return                Employee information after updation.
     */
    public Employee updateEmpDetail(Employee employee) 
        throws PMSApplicationException;

    /**
     * <p>
     * Retrieve all the existing employee which are present in the employees
     * for the purpose of displaying and searching a particular employee based 
     * on their status.
     * </p>
     *
     * @param    status   specifies whether it is active or inactive
     *
     * @return            information of all the employee based on their status.        
     */
    public Set<Employee> fetchEmployeesDetailByStatus(String status) 
        throws PMSApplicationException;

    /**
     * <p>
     * Search a particular employee the help of the specified id and its 
     * corresponding status.
     * </p>
     *
     * @param    employeeId    id of the employee to be searched.
     *
     * @param    status        employee status 
     *
     * @return                 details of that particular employee.
     */
    public Employee searchEmployeeByIdAndStatus(int employeeId, String status) 
        throws PMSApplicationException;

    /**
     * <p>
     * Checks whether the specified employee is assigned with some other 
     * projects.
     * </p>
     * 
     * @param    employeeId   employee which is to be deleted.
     *
     * @return   true         if employee is working in a projects
     *  
     *                        when employee is not assigned to any projects
     */
    public boolean isEmployeeAlreadyAssigned(Employee employee) 
        throws PMSApplicationException;

    /**
     * <p>
     * removes the specified employee from the employees.
     * </p>
     * 
     * @param    employee    employee information.
     *
     * @return               id of the employee which was deleted.
     */
    public int removeEmployee(Employee employee) throws PMSApplicationException;


    /**
     * <p>
     * Restore the specified employee.
     * </p>
     * 
     * @param    employee    employee information to be restored.
     *
     * @return               employee information after restoring.
     */
    public Employee restoreEmployee(Employee employee) 
        throws PMSApplicationException;
}
