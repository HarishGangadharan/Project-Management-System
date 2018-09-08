package com.ideas2it.employeemanagementsystem.dao.impl;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;

import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.commons.constants.EmployeeConstants;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.logger.PMSLogger;
import com.ideas2it.employeemanagementsystem.dao.GenericDao;
import com.ideas2it.employeemanagementsystem.dao.EmployeeDao;
import com.ideas2it.employeemanagementsystem.model.Employee;
import com.ideas2it.employeemanagementsystem.model.Project;

/**
 * <p>
 * Add the employee details to the employees and retrieves all the
 * employee information for updation process and performs deletion operation by
 * specifying an employeeId where id helps to uniquely identify a record in the
 * employees.
 * </p>
 * 
 * @author Harish
 */
public class EmployeeDaoImpl extends GenericDao implements EmployeeDao  {

    private static final String HIBERNATE_EXCEPTION = 
        "Error: Cannot able to access the database. Try again!";

    private static final String INSERT_EXCEPTION = 
        "'s details cant be inserted.Try again!";

    private static final String RETRIEVE_EXCEPTION = 
        "Error Occured : Employees detail cant be viewed.Try again!";

    private static final String SEARCH_EXCEPTION = 
        "'s detail cant be searched.Try again!";

    private static final String DELETE_EXCEPTION = 
        "'s details cant be deleted.Try again!";

    private static final String UPDATE_EXCEPTION = 
        "'s details cant be updated.Try again!";

    private static final String RESTORE_EXCEPTION = 
        "'s details cant be restored.Try again!";

    /**
     * {@inheritDoc}
     */
    public String insertEmployee(Employee employee) 
            throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session = getSession();
        employee.setStatus(Constants.ACTIVE);
        StringBuilder responseMessage = new StringBuilder();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            responseMessage.append(employee.getName()).
                append(Constants.INSERT_MESSAGE).append(employee.getId()).
                append(Constants.RESPECTIVE_ID);
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(employee.getName()).append(INSERT_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);          
        } finally {
            session.close();
        }
        return responseMessage.toString();
    }

    /**
     * {@inheritDoc}
     */
    public Set<Employee> retrieveAllEmployeesDetailByStatus(String status) 
            throws PMSApplicationException {
        Set<Employee> employees = new HashSet<Employee>();
        Session session =getSession();
        List employeesDetail;
        try {
            employeesDetail = session.createCriteria(Employee.class).
                add(Restrictions.eq(Constants.LABEL_STATUS, status)).list();
            employees.addAll(employeesDetail);
        } catch(HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            throw new PMSApplicationException(RETRIEVE_EXCEPTION, exception);
        } finally {
            session.close();
        }
        return employees;
   }

    /**
     * {@inheritDoc}
     */
    public Employee restoreEmployee(Employee employee) 
            throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session =getSession();
        employee.setStatus(Constants.ACTIVE);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();                       
            session.update(employee);
            transaction.commit();
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(employee.getName()).append(RESTORE_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);          
        } finally {
            session.close();
        }
         return employee;
    }

    /**
     * {@inheritDoc}
     */
    public int deleteEmployee(Employee employee) throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session =getSession();
        employee.setStatus(Constants.INACTIVE);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();                       
            session.update(employee);
            transaction.commit();
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(employee.getName()).append(DELETE_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);          
        } finally {
            session.close();
        }
         return employee.getId();
    }
 
    /**
     * {@inheritDoc}
     */
    public Employee searchEmployeeByIdAndStatus(int employeeId, String status) 
            throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session =getSession();
        Employee employee = new Employee();
        try {
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.add(Restrictions.eq(Constants.LABEL_ID, employeeId));
            criteria.add(Restrictions.eq(Constants.LABEL_STATUS, status));
	    employee = (Employee) criteria.uniqueResult();
        } catch(HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(employeeId).append(SEARCH_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                           exception);
        } finally {
            session.close();
        }
       return employee;
    }
 
    /**
     * {@inheritDoc}
     */
    public Employee updateEmpDetail(Employee employee) 
            throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session =getSession();
        employee.setStatus(Constants.ACTIVE);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();                 
            session.update(employee);
            transaction.commit();
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(employee.getName()).append(UPDATE_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);          
        } finally {
            session.close();
        }
        return employee; 
   }
}
