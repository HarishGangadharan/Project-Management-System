package com.ideas2it.employeemanagementsystem.dao;

import java.util.Set;

import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.model.Employee;

/**
 * <p>
 * Helps to perform operations like adding, deleting, restoring, display all the
 * employee details to the databases. 
 * </p>
 *
 * @author Harish
 */
public interface EmployeeDao {

    /**
     * <p>
     * Add a new employee  with all their corresponding details.
     * </p>
     * 
     * @param     employee     details of the employee
     *
     * @return                 id of the employee who was added.
     */
    public String insertEmployee(Employee employee) 
        throws PMSApplicationException;

    /**
     * <p>
     * Retrieve all the employee details from the employees.
     * </p>
     *
     * @param   status    specifies whether the status is active or inactive
     *
     * @return          details of all the employee.
     */
    public Set<Employee> retrieveAllEmployeesDetailByStatus(String status) 
        throws PMSApplicationException;

     /**
     * <p>
     * Search a particular employee from the employees with the
     * help of the specified employeeId.
     * </p>
     *
     * @param   employeeId     id of the particular employee.
     *
     * @param   status    specifies whether the status is active or inactive
     *
     * @return                 Information of that particular employee.
     */
    public Employee searchEmployeeByIdAndStatus(int employeeId, String status) 
        throws PMSApplicationException;

    /**
     * <p>
     * Delete the particular employee from the employeelist by obtaining all the
     * details of that particular employee from the employees.
     * <p>
     *
     * @param   employee      details of the particular employee
     *                               to be deleted.
     *
     * @return                id of the employee who was deleted.
     */
    public int deleteEmployee(Employee employee) throws PMSApplicationException;

    /**
     * <p>
     * Replace the updated employee details with the existing employee.
     * </p>
     *
     * @param   employee      employee information with updated details.
     *
     * @return                employee information after updation.
     */ 
    public Employee updateEmpDetail(Employee employee) 
        throws PMSApplicationException;

    /**
     * <p>
     * Restore the specified employee.
     * </p>
     * 
     * @param    employee    employee information.
     *
     * @return               employee information after restoring.
     */
    public Employee restoreEmployee(Employee employee) 
        throws PMSApplicationException;
}
