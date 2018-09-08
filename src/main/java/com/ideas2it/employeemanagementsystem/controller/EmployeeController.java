package com.ideas2it.employeemanagementsystem.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.commons.constants.EmployeeConstants;
import com.ideas2it.employeemanagementsystem.commons.constants.ProjectConstants;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.logger.PMSLogger; 
import com.ideas2it.employeemanagementsystem.model.Address;
import com.ideas2it.employeemanagementsystem.model.Employee;
import com.ideas2it.employeemanagementsystem.model.Project;
import com.ideas2it.employeemanagementsystem.service.EmployeeService;
import com.ideas2it.employeemanagementsystem.service.impl.EmployeeServiceImpl;
import com.ideas2it.employeemanagementsystem.utils.DateUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;  

/**
 * Obtain all the details from the employee and use them for futher
 * manipulations.
 * <p>
 * Helps in creating, deleting, searching a particular employee, updating 
 * the employee detail and displaying all the  employee detail. 
 * and modified employee detail.
 * </p>
 *
 * @author Harish
 */
@Controller
public class EmployeeController  extends HttpServlet {

    private static EmployeeService employeeService;

    public void init() throws ServletException { 

         ApplicationContext context = 
            new GenericXmlApplicationContext("classpath*:/Beans.xml");
    }

    public  void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * <p>
     * Helps to redirect to the employee creation page.
     * </p>
     *
     * @return    ModelAndView   Display the employee creation page.
     */
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public ModelAndView showEmployeeForm() {
        try {
            ModelAndView mav = new ModelAndView("employee.jsp");
            Employee employee = new Employee();
            mav.addObject(Constants.EMPLOYEE, employee);
            mav.addObject(Constants.MINDATE, DateUtil.convertDateToString(
                DateUtil.getDateReducedByYears(60)));
            mav.addObject("maxDate", DateUtil.convertDateToString(
                DateUtil.getDateReducedByYears(18)));
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

    /**
     * <p>
     * Helps to add the address details to a particular employee.
     * </p>
     *
     * @param     id             id of the employee to be searched.
     *
     * @param     employee         employee object to store all the information.
     *
     * @return    ModelAndView   Display the  employee jsp page.
     */
    @RequestMapping(value="/employeeOperation", method=RequestMethod.POST, 
        params="addAddress")
    public ModelAndView addAddress(@ModelAttribute(Constants.EMPLOYEE) 
            Employee employee) {
        List<Address> addresses = new ArrayList<Address>();
        Address permanentAddress = new Address();
        permanentAddress.setType(Constants.PERMANENT);
        addresses.add(permanentAddress);
        Address temporaryAddress = new Address();
        temporaryAddress.setType(Constants.TEMPORARY);
        addresses.add(temporaryAddress);
        employee.setAddresses(addresses);
        return new ModelAndView("address.jsp",Constants.EMPLOYEE, employee);
    }
	
    /**
     * <p>
     * Helps to update a particular employee.
     * </p>
     *
     * @param     employee         employee with all the information.
     *
     * @return    ModelAndView   Display the  employee jsp page.
     */
     @RequestMapping(value="/employeeOperation", method=RequestMethod.POST, params="Update")
    public ModelAndView updateEmployee(@ModelAttribute(Constants.EMPLOYEE) Employee
            employee)  {
        try {
            Set<Employee> employees = null;
            ModelAndView mav = new ModelAndView(Constants.EMPLOYEESDETAILJSP);
            employeeService.updateEmpDetail(employee);
            employees = employeeService.fetchEmployeesDetailByStatus(Constants.ACTIVE);
            mav.addObject(Constants.EMPLOYEES, employees);
            mav.addObject(Constants.UPDATEDID, employee.getId());
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

    /**
     * <p>
     * Helps to delete a particular employee.
     * </p>
     *
     * @param     employee         employee with all the information.
     *
     * @return    ModelAndView   Display the  employee jsp page.
     */
    @RequestMapping(value="/employeeOperation", method=RequestMethod.POST, params="Delete")
    public ModelAndView deleteEmployee(@ModelAttribute(Constants.EMPLOYEE) Employee 
            employee)   {
    	try {
            Set<Employee> employees = null;
            ModelAndView mav = new ModelAndView(Constants.EMPLOYEESDETAILJSP);
            employeeService.removeEmployee(employee);
            employees = employeeService.fetchEmployeesDetailByStatus(Constants.ACTIVE);
            mav.addObject(Constants.EMPLOYEES, employees);
            mav.addObject(Constants.DELETEDID, employee.getId());
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }


    }

    /**
     * <p>
     * Helps to cancel a the jsp.
     * </p>
     *
     * @return    ModelAndView   Display the  employee jsp page.
     */
   @RequestMapping(value="/employeeOperation", method=RequestMethod.POST, params="Cancel")
    public ModelAndView Cancel() {
    	try {
            ModelAndView mav = new ModelAndView("employee.jsp");
            Employee employee = new Employee();
            mav.addObject(Constants.EMPLOYEE, employee);
            mav.addObject(Constants.MINDATE, DateUtil.convertDateToString(
                DateUtil.getDateReducedByYears(60)));
            mav.addObject("maxDate", DateUtil.convertDateToString(
                DateUtil.getDateReducedByYears(18)));
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }


    }

    /**
     * <p>
     * Helps to restore a particular employee.
     * </p>
     *
     * @param     employee         employee with all the information.
     *
     * @return    ModelAndView   Display the  employee jsp page.
     */
    @RequestMapping(value="/employeeOperation", method=RequestMethod.POST, params="Restore")
    public ModelAndView restoreEmployee(@ModelAttribute(Constants.EMPLOYEE) 
            Employee employee)   {
    	try {
            employeeService.restoreEmployee(employee);
            Set<Employee> employees = null;
            ModelAndView mav = new ModelAndView(Constants.EMPLOYEESDETAILJSP);
            employees = employeeService.fetchEmployeesDetailByStatus(Constants.INACTIVE);
            mav.addObject(Constants.EMPLOYEES, employees);
            mav.addObject(Constants.RESTOREDID, employee.getId());
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

   /**
     * <p>
     * Helps to add the employee information.
     * </p>
     *
     * @param     employee         employee with all thier information.
     *
     * @return    ModelAndView   Display the employee creation page.
     */
    @RequestMapping(value="/submitEmployee", method=RequestMethod.POST)
    public ModelAndView addEmployee(@ModelAttribute(Constants.EMPLOYEE) Employee employee) {
    	try {
            Set<Employee> employees = null;
            ModelAndView mav = new ModelAndView(Constants.EMPLOYEESDETAILJSP);
            String responseMessage = employeeService.createEmployee(employee);
            employees = employeeService.fetchEmployeesDetailByStatus(Constants.ACTIVE);
            mav.addObject(Constants.EMPLOYEES, employees);
            mav.addObject(Constants.RESPONSEMESSAGE,responseMessage);
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

    /**
     * <p>
     * Helps to search a particular employee with the specified id and status.
     * </p>
     *
     * @param     id             id of the employee to be searched.
     *
     * @param     status         status of the employee to be searched.
     *
     * @param     employee         employee object to store all the information.
     *
     * @return    ModelAndView   Display the  employee jsp page.
     */
    @RequestMapping(value="/employee", method=RequestMethod.POST, params="search")
    public ModelAndView searchmployee(@RequestParam(Constants.LABEL_ID) String id, 
            @RequestParam(Constants.LABEL_STATUS) String status, 
            @ModelAttribute(Constants.EMPLOYEE) Employee employee) {
    	try {
            StringBuilder errorMessage = new StringBuilder();
            employee = employeeService.searchEmployeeByIdAndStatus(
                       Integer.parseInt(id), status);
            if ( null != employee) {
                return new ModelAndView("employee.jsp",Constants.EMPLOYEE, employee);
            } else {
                errorMessage.append(EmployeeConstants.ERRORMESSAGE1).append(id).
                   append(EmployeeConstants.ERRORMESSAGE2);

                ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
                mav.addObject(Constants.ERROR , errorMessage.toString());
                return mav;
            }
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }




    /**
     * <p>
     * Helps to search a particular employee with the specified id and status.
     * </p>
     *
     * @param     id             id of the employee to be deleted.
     *
     * @param     status         status of the employee to be searched.
     *
     * @param     employee         employee object to store all the information.
     *
     * @return    ModelAndView   Display the  employee jsp page.
     */
     @RequestMapping(value="/searchEmployee", method=RequestMethod.POST)
    public ModelAndView searchEmployee(@RequestParam(Constants.LABEL_ID) String id, 
            @RequestParam(Constants.LABEL_STATUS) String status,
            @ModelAttribute(Constants.EMPLOYEE) Employee employee)  {
    	try {
            employee = employeeService.searchEmployeeByIdAndStatus(
                       Integer.parseInt(id), status);
            return new ModelAndView("employee.jsp",Constants.EMPLOYEE, employee);
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }


    /**
     * <p>
     * Helps to restore a particular employee with the specified id and status.
     * </p>
     *
     * @param     id             id of the employee to be restored.
     *
     * @param     status         status of the employee to be searched.
     *
     * @param     employee         employee object to store all the information.
     *
     * @return    ModelAndView   Display the  employee jsp page.
     */
    @RequestMapping(value="/restoreEmployee", method=RequestMethod.POST)
    public ModelAndView restoreEmployee(@RequestParam(Constants.LABEL_ID) String id,
            @ModelAttribute(Constants.EMPLOYEE) Employee employee)  {
    	try {
            ModelAndView mav = new ModelAndView(Constants.EMPLOYEESDETAILJSP);
            employee = employeeService.searchEmployeeByIdAndStatus(
                       Integer.parseInt(id), Constants.INACTIVE);
            employeeService.restoreEmployee(employee);
            Set<Employee> employees = null;
            employees = employeeService.fetchEmployeesDetailByStatus(Constants.INACTIVE);
            mav.addObject(Constants.EMPLOYEES, employees);
            mav.addObject(Constants.RESTOREDID, employee.getId());
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

    /**
     * <p>
     * Helps to display all the employees with the specified  status.
     * </p>
     *
     *
     * @param     status         status of the employee to be searched.
     *
     * @param     employee         employee object to store all the information.
     *
     * @return    ModelAndView   Display the  employee jsp page.
     */
    @RequestMapping(value="/employee", method=RequestMethod.POST,  params="display")
    public ModelAndView displayAllEmployees(@RequestParam(Constants.LABEL_STATUS)
            String status, @ModelAttribute(Constants.EMPLOYEE) Employee employee) {
    	try {
            Set<Employee> employees = null;
            employees = employeeService.fetchEmployeesDetailByStatus(status);
            return new ModelAndView("employeesDetail.jsp","employees", employees);
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

    /**
     * <p>
     * Helps to delete a particular employee with the specified id and status.
     * </p>
     *
     * @param     id             id of the employee to be deleted.
     *
     * @param     status         status of the employee to be searched.
     *
     * @param     employee         employee object to store all the information.
     *
     * @return    ModelAndView   Display the  employee jsp page.
     */
    @RequestMapping(value="/deleteEmployee", method=RequestMethod.POST)
    public ModelAndView deleteEmployee(@RequestParam(Constants.LABEL_ID) String id,
            @ModelAttribute(Constants.EMPLOYEE) Employee employee) {
    	try {
            employee = employeeService.searchEmployeeByIdAndStatus(
                Integer.parseInt(id), Constants.ACTIVE);
            ModelAndView mav = new ModelAndView(Constants.EMPLOYEESDETAILJSP);
            Set<Employee> employees = null;
            employeeService.removeEmployee(employee);
            employees = employeeService.fetchEmployeesDetailByStatus(Constants.ACTIVE);
            mav.addObject(Constants.EMPLOYEES, employees);
            mav.addObject(Constants.DELETEDID, employee.getId());
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }


    }


    /**
     * <p>
     * Helps to create a  text file with all the employee  detail.
     * </p>
     *
     * @param     status         status of the employee to be searched.
     *
     * @param     employee         employee object to store all the information.
     *
     * @return    ModelAndView   Display the  employee jsp page.
     */
    @RequestMapping(value="/createFile", method=RequestMethod.POST)
    public ModelAndView createFile(@RequestParam(Constants.LABEL_STATUS) String status, 
        @RequestParam("filename") String name, HttpServletRequest request,
        @ModelAttribute(Constants.EMPLOYEE) 
        Employee employee)  throws PMSApplicationException , ServletException,
        IOException {
        ModelAndView mav = new ModelAndView(Constants.EMPLOYEESDETAILJSP);
        InputStream inputStream = null;
        Set<Employee> employees = null;
        OutputStream outputStream = null;
	int end;
        final int EOF = -1;
        StringBuilder builder = new StringBuilder();
        StringBuilder pathName = new StringBuilder();
        StringBuilder exceptionMessage = new StringBuilder();
        StringBuilder destinationPath = new StringBuilder();
    	try {
            employees = employeeService.fetchEmployeesDetailByStatus(status);
            if (employees.isEmpty()) {
                builder.append(EmployeeConstants.ERROR_NO_EMPLOYEE);
            } else {
                for (Employee employeeInfo : employees) { 
                    builder.append(employeeInfo.toString());
                }
            }
            inputStream =IOUtils.toInputStream(builder.toString(),
                Constants.UTF);
            pathName.append(Constants.FILEPATH).append(name);  
            File outFile = new File(pathName.toString()); 
            outputStream = new FileOutputStream(outFile);
            while ((end = inputStream.read()) != EOF) {
                outputStream.write(end);
            }
            exceptionMessage.append(Constants.ERRORFILENOTCREATED);
            destinationPath.append(Constants.DESTINATIONPATH).append(name);
            mav.addObject("employees", employees);
            mav.addObject(Constants.FILEPRESENT,Constants.TRUE);
            mav.addObject(Constants.FILE, destinationPath.toString());
            return mav;
        } catch (IOException exception) {
            ModelAndView mav1 = new ModelAndView(Constants.ERRORJSP);
            mav1.addObject(Constants.ERROR, exception.getMessage());
            return mav1;
        } catch(PMSApplicationException exception) {
            ModelAndView mav2 = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav2.addObject(Constants.ERROR, exception.getMessage());
            return mav2;
        }

    }
}
