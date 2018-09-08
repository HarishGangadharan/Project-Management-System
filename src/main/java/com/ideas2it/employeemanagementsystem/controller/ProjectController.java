package com.ideas2it.employeemanagementsystem.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
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
import com.ideas2it.employeemanagementsystem.model.Employee;
import com.ideas2it.employeemanagementsystem.model.History;
import com.ideas2it.employeemanagementsystem.model.Project;
import com.ideas2it.employeemanagementsystem.service.ProjectService;
import com.ideas2it.employeemanagementsystem.service.impl.ProjectServiceImpl;
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
 * Obtain all the details about the project and use them for futher
 * manipulations.
 * <p>
 * Helps in creating, deleting, searching a particular project and
 * updating the project detail and displaying the projects detail 
 * and modified project detail.
 * </p>
 *
 * @author Harish
 */
@Controller
public class ProjectController   extends HttpServlet {

    private  static ProjectService projectService;

    public void init() throws ServletException { 
        ApplicationContext context = 
            new GenericXmlApplicationContext("classpath*:/Beans.xml");
    }

    public  void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * <p>
     * Helps to redirect to the project creation page.
     * </p>
     *
     * @return    ModelAndView   Display the project creation page.
     */
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ModelAndView createProject() {
        ModelAndView mav = new ModelAndView(Constants.PROJECTLABEL);
        Project project = new Project();
        return new ModelAndView(Constants.PROJECT_JSP,Constants.PROJECTLABEL, project);
    }

   /**
     * <p>
     * Helps to add the project information.
     * </p>
     *
     * @param     project         project with all thier information.
     *
     * @return    ModelAndView   Display the project creation page.
     */
    @RequestMapping(value="/projectOperation", method=RequestMethod.POST, params="submit")
    public ModelAndView addUser(@ModelAttribute(Constants.PROJECTLABEL) Project project) {
    	try {
           Set<Project> projects = null;
           Set<Employee> employees = null;
           ModelAndView mav = new ModelAndView(Constants.PROJECTMEMBERSJSP);
           String responseMessage = projectService.createProject(project);
           projects = projectService.fetchAllProjectsByStatus(Constants.ACTIVE);
           employees = projectService.fetchAllEmployees();
           mav.addObject(Constants.PROJECTS, projects);
            mav.addObject(Constants.EMPLOYEES, employees);
           mav.addObject(Constants.PROJECTINFO,  projectService.searchProjectByIdAndStatus(
                (project.getId()),Constants.ACTIVE));
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
     * Helps to assign the project members to project.
     * </p>
     *
     * @param     project         project with all thier information.
     *
     * @return    ModelAndView   Display the project creation page.
     */
    @RequestMapping(value="/projectOperation", method=RequestMethod.POST, params="assign")
    public ModelAndView addUser(@ModelAttribute(Constants.PROJECTLABEL) Project project, 
            HttpServletRequest request) throws  
            ServletException, IOException  {
    	try {
            String[] employeesId = request.getParameterValues(ProjectConstants.CHECKBOX);
            Set<Employee> projectMembers = new HashSet<Employee>();
            StringBuilder errorMessage = new StringBuilder();
            Employee employee;
            String projectId = request.getParameter(Constants.PROJECTID);
            ModelAndView mav = new ModelAndView(Constants.PROJECTMEMBERSJSP);
            project = projectService.searchProjectByIdAndStatus(
                project.getId(), Constants.ACTIVE);
            if (null != employeesId ) {
                for (String employeeId :employeesId) {
                    employee = projectService.fetchEmployeeDetailById(Integer.
                               parseInt(employeeId));
                    setProjectHistory(project , employee);
                    projectMembers.add(employee); 
                }
                project.setProjectMembers(projectMembers);
                project = projectService.updateProjectDetail(project);
                mav.addObject(Constants.PROJECTINFO,  project);
                mav.addObject( EmployeeConstants.EMPLOYEES, 
                                     project.getProjectMembers());    
                mav.addObject(ProjectConstants.PROJECTMEMBERS, 
                                     ProjectConstants.PROJECTMEMBERS);
            } else {
                errorMessage.append(ProjectConstants.NO_EMPLOYEE_AVAILABLE);
                mav.addObject(Constants.ERROR, errorMessage.toString());
                mav.addObject(Constants.PROJECTINFO,  project);
                mav.addObject( EmployeeConstants.EMPLOYEES, 
                                     project.getProjectMembers());    
                mav.addObject(ProjectConstants.PROJECTMEMBERS, 
                                     ProjectConstants.PROJECTMEMBERS);
            }
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
     * Display the employees details for adding the project members to the 
     * project.
     * </p>
     *
     * @param     project         project with all thier information.
     *
     * @return    ModelAndView   Display the project creation page.
     */
    @RequestMapping(value="/projectOperation", method=RequestMethod.POST, params = "AddEmployee")
    public ModelAndView AddEmployee(@ModelAttribute(Constants.PROJECTLABEL) Project project, 
            HttpServletRequest request) 
            throws  ServletException, IOException  {
    	try {
            String projectId = request.getParameter(ProjectConstants.PROJECTID);
            project = projectService.searchProjectByIdAndStatus(
                 Integer.parseInt(projectId), Constants.ACTIVE);
            ModelAndView mav = new ModelAndView(Constants.PROJECTMEMBERSJSP);
            Set<Employee> employees = projectService.fetchAllEmployees();
            Set<Employee> projectMembers = new HashSet<Employee>();
            for (Employee employee : employees) {
                for (Employee teamMember : project.getProjectMembers()) {
                   if (employee.getId().equals(teamMember.getId())) {
                        projectMembers.add(employee);
                   }
                }
            }
            employees.removeAll(projectMembers);
            mav.addObject(Constants.PROJECTINFO,project);
            mav.addObject(EmployeeConstants.EMPLOYEES,employees);
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
     * Update the project information.
     * </p>
     *
     * @param     project         project with all thier information.
     *
     * @return    ModelAndView   Display the project creation page.
     */
    @RequestMapping(value="/projectOperation", method=RequestMethod.POST, params="UpdateProject")
    public ModelAndView updateProjectInformation(@ModelAttribute(Constants.PROJECTLABEL)
            Project project) {
    	try {
           Set<Project> projects = null;
           project =  projectService.searchProjectByIdAndStatus(
                (project.getId()),Constants.ACTIVE);
        	return new ModelAndView(Constants.PROJECT_JSP,Constants.PROJECTLABEL, project);
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

    /**
     * <p>
     * Set the project history details while assigning project members.
     * </p>
     * @param  request   HttpServletRequest
     *
     * @param  response  HttpServletResponse
     */
    private void setProjectHistory(Project project, Employee employee)  
            throws PMSApplicationException{
        Set<History> projectHistories = new HashSet<History>();
        History historyDetail = new History();
        historyDetail.setEmployeeId(employee.getId());
        historyDetail.setProjectId(project.getId());
        historyDetail.setEmpJoiningDate(DateUtil.getCurrentDate());
        projectHistories.add(historyDetail);
        project.setHistories(projectHistories);        
    }


    /**
     * <p>
     * remove the project members from the project.
     * </p>
     *
     * @param     project         project with all thier information.
     *
     * @return    ModelAndView   Display the project creation page.
     */
    @RequestMapping(value="/RemoveProjectMember", method=RequestMethod.POST)
    public ModelAndView RemoveProjectMembe(@ModelAttribute(Constants.PROJECTLABEL) 
            Project project, HttpServletRequest request) 
            throws  ServletException, IOException  {
    	try {
            String id = request.getParameter(Constants.EMPLOYEEID);
            ModelAndView mav = new ModelAndView(Constants.PROJECTMEMBERSJSP);
            String projectId = request.getParameter(ProjectConstants.PROJECTID);
            project = projectService.searchProjectByIdAndStatus(
            project.getId(), Constants.ACTIVE);
            Set<Employee> projectMembers = null;
            Employee employee = null;
            projectMembers =  project.getProjectMembers();
            for (Employee teamMember : projectMembers) {
                if (Integer.parseInt(id) == teamMember.getId()) {
                    employee = teamMember;
                    setEmployeeLeavingDate(employee, project);
                 }
            }
            project.getProjectMembers().remove(employee);
            project = projectService.updateProjectDetail(project);
            mav.addObject(EmployeeConstants.EMPLOYEES, project.getProjectMembers());
            mav.addObject(ProjectConstants.PROJECTMEMBERS, 
                ProjectConstants.PROJECTMEMBERS);
            mav.addObject(EmployeeConstants.EMPLOYEES, project.getProjectMembers());
            mav.addObject(Constants.PROJECTINFO,project );
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
     * Set the project member leaving date while the employee is removed.
     * </p>
     * @param  request   HttpServletRequest
     *
     * @param  response  HttpServletResponse
     */
    private void setEmployeeLeavingDate(Employee employee, Project project) 
            throws PMSApplicationException{
        for (History history : project.getHistories()) {
            if ((history.getProjectId()).equals(project.getId()) && 
                   (history.getEmployeeId()).equals(employee.getId())) {
                if (history.getEmpLeavingDate() == null) {
                    history.setEmpLeavingDate(DateUtil.getCurrentDate());
                }
            }  
        }
    }
 
    /**
     * <p>
     * Helps to display all the projects with the specified  status.
     * </p>
     *
     *
     * @param     status         status of the project to be searched.
     *
     * @param     project         project object to store all the information.
     *
     * @return    ModelAndView   Display the  project jsp page.
     */
    @RequestMapping(value="/project", method=RequestMethod.POST,  params="display")
    public ModelAndView display(  @RequestParam(Constants.LABEL_STATUS) String status,
            @ModelAttribute(Constants.PROJECTLABEL) Project project)  {
    	try {
            Set<Project> projects = null;
            projects = projectService.fetchAllProjectsByStatus(status);
            return new ModelAndView("projectsDetail.jsp","projects", projects);
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }



    /**
     * <p>
     * Helps to delete a particular project with the specified id and status.
     * </p>
     *
     * @param     id             id of the project to be deleted.
     *
     * @param     status         status of the project to be searched.
     *
     * @param     project         project object to store all the information.
     *
     * @return    ModelAndView   Display the  project jsp page.
     */
    @RequestMapping(value="/deleteProject", method=RequestMethod.POST)
    public ModelAndView deleteProject(@RequestParam(Constants.LABEL_ID) String id,
            @ModelAttribute(Constants.PROJECTLABEL) Project project)  {
    	try {
           project = projectService.searchProjectByIdAndStatus(
                Integer.parseInt(id), Constants.ACTIVE);
            ModelAndView mav = new ModelAndView(Constants.PROJECTSDETAILJSP);
            Set<Project> projects = null;
            int Deletedid = projectService.removeProject(project); 
            projects = projectService.fetchAllProjectsByStatus(Constants.ACTIVE);
            mav.addObject(Constants.PROJECTS, projects);
            mav.addObject(Constants.DELETEDID, Deletedid);
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
     * Helps to search a particular project with the specified id and status.
     * </p>
     *
     * @param     id             id of the project to be deleted.
     *
     * @param     status         status of the project to be searched.
     *
     * @param     project         project object to store all the information.
     *
     * @return    ModelAndView   Display the  project jsp page.
     */
    @RequestMapping(value="/searchProject", method=RequestMethod.POST)
    public ModelAndView searchProject(@RequestParam(Constants.LABEL_ID) String id,
            @RequestParam(Constants.LABEL_STATUS) String status,
            @ModelAttribute(Constants.PROJECTLABEL) Project project)  {
    	try {
            project = projectService.searchProjectByIdAndStatus(
                Integer.parseInt(id), status);  
	     return new ModelAndView(Constants.PROJECT_JSP,Constants.PROJECTLABEL, project);
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }

    /**
     * <p>
     * Helps to restore a particular project with the specified id and status.
     * </p>
     *
     * @param     id             id of the project to be deleted.
     *
     * @param     status         status of the project to be searched.
     *
     * @param     project         project object to store all the information.
     *
     * @return    ModelAndView   Display the  project jsp page.
     */
    @RequestMapping(value="/restoreProject", method=RequestMethod.POST)
    public ModelAndView restoreProject(@RequestParam(Constants.LABEL_ID) String id,
            @ModelAttribute(Constants.PROJECTLABEL) Project project)  {
    	try {
            project = projectService.searchProjectByIdAndStatus(
                Integer.parseInt(id), Constants.INACTIVE);
            Set<Project> projects = null;
            ModelAndView mav = new ModelAndView(Constants.PROJECTSDETAILJSP);
            projectService.restoreProject(project);
            projects = projectService.fetchAllProjectsByStatus(Constants.INACTIVE);
            mav.addObject(Constants.PROJECTS, projects);
            mav.addObject(Constants.RESTOREDID, project.getId());
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
     * Helps to search a particular project with the specified id and status.
     * </p>
     *
     * @param     id             id of the project to be searched.
     *
     * @param     status         status of the project to be searched.
     *
     * @param     project         project object to store all the information.
     *
     * @return    ModelAndView   Display the  project jsp page.
     */
     @RequestMapping(value="/project", method=RequestMethod.POST, params="search")
     public ModelAndView addsUser(@RequestParam(Constants.LABEL_ID) String id, 
             @RequestParam(Constants.LABEL_STATUS) String status,
             @ModelAttribute(Constants.PROJECTLABEL) Project project) {
    	try {
            StringBuilder errorMessage = new StringBuilder();
             project = projectService.searchProjectByIdAndStatus(
                 Integer.parseInt(id), status);
 
             if ( null != project) {
                 ModelAndView mav = new ModelAndView(Constants.PROJECTMEMBERSJSP);
                 mav.addObject(Constants.PROJECTINFO,  project);
                 mav.addObject( EmployeeConstants.EMPLOYEES, 
                                     project.getProjectMembers());    
                 mav.addObject(ProjectConstants.PROJECTMEMBERS, 
                                     ProjectConstants.PROJECTMEMBERS);
                 return mav;
             } else {
                 errorMessage.append(ProjectConstants.ERRORMESSAGE1).append(id).
                 append(ProjectConstants.ERRORMESSAGE2);
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
     * Helps to update a particular project with the specified id and status.
     * </p>
     *
     * @param     id             id of the project to be searched.
     *
     * @param     status         status of the project to be searched.
     *
     * @param     project         project object to store all the information.
     *
     * @return    ModelAndView   Display the  project jsp page.
     */
    @RequestMapping(value="/projectOperation", method=RequestMethod.POST, params="Update")
    public ModelAndView updateProject(@ModelAttribute(Constants.PROJECTLABEL) Project project) {
    	try {
            Set<Project> projects = null;
            ModelAndView mav = new ModelAndView(Constants.PROJECTSDETAILJSP);
            Set<Employee> projectMembers = new HashSet<Employee>();
            Project projectDetail = projectService.searchProjectByIdAndStatus(
                    project.getId(), Constants.ACTIVE);
                for (Employee employee:projectDetail.getProjectMembers()) { 
                    setProjectHistory(project , employee);
                    projectMembers.add(employee);    
                }
            project.setProjectMembers(projectMembers);
            project = projectService.updateProjectDetail(project);
            projects = projectService.fetchAllProjectsByStatus(Constants.ACTIVE);
            mav.addObject(Constants.PROJECTS, projects);
            mav.addObject(Constants.UPDATEDID, project.getId());
            return mav;
        } catch(PMSApplicationException exception) {
            ModelAndView mav = new ModelAndView(Constants.ERRORJSP);
            PMSLogger.info(exception.getMessage());
            mav.addObject(Constants.ERROR, exception.getMessage());
            return mav;
        }

    }


    @RequestMapping(value="/projectOperation", method=RequestMethod.POST, params="History")
    public ModelAndView showHistory(@ModelAttribute(Constants.PROJECTLABEL) Project project, 
            HttpServletRequest request)   {
    	try {
            ModelAndView mav = new ModelAndView(Constants.PROJECTMEMBERSJSP);
            String id = request.getParameter(Constants.LABEL_ID);
            project = projectService.searchProjectByIdAndStatus(
                Integer.parseInt(id), Constants.ACTIVE);
            mav.addObject(Constants.PROJECTINFO, project);
            mav.addObject(EmployeeConstants.EMPLOYEES, 
                project.getProjectMembers());
            mav.addObject(ProjectConstants.PROJECTMEMBERS, 
                ProjectConstants.PROJECTMEMBERS);
            mav.addObject(ProjectConstants.HISTORIES, project.getHistories());
            mav.addObject(ProjectConstants.HISTORIES, 
               ProjectConstants.HISTORIES);
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
     * Helps to delete a particular project with the specified id and status.
     * </p>
     *
     * @param     id             id of the project to be searched.
     *
     * @param     status         status of the project to be searched.
     *
     * @param     project         project object to store all the information.
     *
     * @return    ModelAndView   Display the  project jsp page.
     */
    @RequestMapping(value="/projectOperation", method=RequestMethod.POST, params="Delete")
    public ModelAndView deleteProject(@ModelAttribute(Constants.PROJECTLABEL) Project project) {
    	try {
            Set<Project> projects = null;
            ModelAndView mav = new ModelAndView(Constants.PROJECTSDETAILJSP);
            int Deletedid = projectService.removeProject(project);
            projects = projectService.fetchAllProjectsByStatus(Constants.ACTIVE);
            mav.addObject(Constants.PROJECTS, projects);
            mav.addObject(Constants.DELETEDID, project.getId());
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
     * Helps to cancel the page.
     * </p>
     *
     * @param     client         client with all the information.
     *
     * @return    ModelAndView   Display the  client jsp page.
     */
   @RequestMapping(value="/projectOperation", method=RequestMethod.POST, params="Cancel")
    public ModelAndView Cancel() {
        Project project = new Project();
        return new ModelAndView(Constants.PROJECT_JSP,Constants.PROJECTLABEL, project);
    }

    /**
     * <p>
     * Helps to restore a particular project with the specified id and status.
     * </p>
     *
     * @param     id             id of the project to be searched.
     *
     * @param     status         status of the project to be searched.
     *
     * @param     project         project object to store all the information.
     *
     * @return    ModelAndView   Display the  project jsp page.
     */
    @RequestMapping(value="/projectOperation", method=RequestMethod.POST, params="Restore")
    public ModelAndView restoreProject(@ModelAttribute(Constants.PROJECTLABEL) Project project) {
    	try {
            Set<Project> projects = null;
            ModelAndView mav = new ModelAndView(Constants.PROJECTSDETAILJSP);
            projectService.restoreProject(project);
            projects = projectService.fetchAllProjectsByStatus(Constants.ACTIVE);
            mav.addObject(Constants.PROJECTS, projects);
            mav.addObject(Constants.RESTOREDID, project.getId());
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
     * Helps to create a  text file with all the project  detail.
     * </p>
     *
     * @param     status         status of the project to be searched.
     *
     * @param     project         project object to store all the information.
     *
     * @return    ModelAndView   Display the  project jsp page.
     */
    @RequestMapping(value="/createProjectFile", method=RequestMethod.POST)
    public ModelAndView createFile(@RequestParam(Constants.LABEL_STATUS) String status, 
            @RequestParam("filename") String name, HttpServletRequest request,
            @ModelAttribute(Constants.PROJECTLABEL) Project project)
            throws PMSApplicationException , ServletException, IOException {
        ModelAndView mav = new ModelAndView(Constants.PROJECTSDETAILJSP);
        InputStream inputStream = null;
        Set<Project> projects = null;
        OutputStream outputStream = null;
	int end;
        final int EOF = -1;
        StringBuilder builder = new StringBuilder();
        StringBuilder pathName = new StringBuilder();
        StringBuilder exceptionMessage = new StringBuilder();
        StringBuilder destinationPath = new StringBuilder();

    	try {
            projects = projectService.fetchAllProjectsByStatus(status);
            if (projects.isEmpty()) {
                builder.append(Constants.NO_PROJECT);
            } else {
                for (Project projectInfo : projects) { 
                    builder.append(projectInfo.toString());
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
            mav.addObject(Constants.PROJECTS, projects);
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
            return mav;
        }

    }
}
