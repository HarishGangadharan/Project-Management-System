package com.ideas2it.employeemanagementsystem.dao.impl;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.hibernate.Transaction;

import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.dao.GenericDao;
import com.ideas2it.employeemanagementsystem.dao.ProjectDao;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.logger.PMSLogger;
import com.ideas2it.employeemanagementsystem.model.Employee;
import com.ideas2it.employeemanagementsystem.model.Project;

/**
 * <p>
 * Add the employee details to the employees and retrieves all the
 * employee information for updation process and performs deletion operation by
 * specifying an employeeId where id helps to uniquely identify a record in the
 * employeeSet.
 * </p>
 * 
 * @author Harish
 */
public class ProjectDaoImpl extends GenericDao implements ProjectDao{

    private static final String INSERT_EXCEPTION = 
        "'s details cant be inserted.Try again!";

    private static final String RETRIEVE_EXCEPTION = 
        "Error Occured : projects detail cant be viewed.Try again!";

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
    public String insertProject(Project project) 
            throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session = getSession();
        StringBuilder responseMessage = new StringBuilder();
        Transaction transaction = null;
        project.setStatus(Constants.ACTIVE);
        try {
            transaction = session.beginTransaction();
            session.save(project);
            transaction.commit();
            responseMessage.append(project.getName()).
                append(Constants.INSERT_MESSAGE).append(project.getId());
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(project.getName()).append(INSERT_EXCEPTION);
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
    public Set<Project> retrieveAllProjectsByStatus(String status) 
            throws PMSApplicationException {
        Set<Project> projects = new HashSet<Project>();
        Session session =getSession();
        List projectsDetail;
        try {
            projectsDetail = session.createCriteria(Project.class).
                add(Restrictions.eq(Constants.LABEL_STATUS, status)).
                list();
            projects.addAll(projectsDetail);
        } catch(HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            throw new PMSApplicationException(RETRIEVE_EXCEPTION, exception);
        } finally {
            session.close();
        }
        return projects;
    }

    /**
     * {@inheritDoc}
     */
    public Project searchProjectByIdAndStatus(int projectId, String status) 
            throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session =getSession();
        Project project = new Project();
        try {
            Criteria criteria = session.createCriteria(Project.class);
            criteria.add(Restrictions.eq(Constants.LABEL_ID, projectId));
            criteria.add(Restrictions.eq(Constants.LABEL_STATUS, status));
	    project = (Project) criteria.uniqueResult();
        } catch(HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(projectId).append(SEARCH_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);
        } finally {
            session.close();
        }
       return project;
    }


    /**
     * {@inheritDoc}
     */
    public Project restoreProject(Project project) 
            throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session =getSession();
        project.setStatus(Constants.ACTIVE);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();                       
            session.update(project);
            transaction.commit();
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(project.getName()).append(RESTORE_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);          
        } finally {
            session.close();
        }
         return project;
    }


    /**
     * {@inheritDoc}
     */
    public int deleteProject(Project project) throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session =getSession();
        project.setStatus(Constants.INACTIVE);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();         
            session.saveOrUpdate(project);
            transaction.commit();
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(project.getName()).append(DELETE_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);       
        } finally {
            session.close();
        }
         return project.getId();
    }

    /**
     * {@inheritDoc}
     */
    public Project updateProjectDetail(Project project) 
            throws PMSApplicationException {
        StringBuilder exceptionMessage = new StringBuilder();
        Session session =getSession();
        project.setStatus(Constants.ACTIVE);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();                    
            session.update(project);
            transaction.commit();
        } catch (HibernateException exception) {
            PMSLogger.error(String.valueOf(exception));
            exceptionMessage.append(Constants.ERROR_OCCURED).
                append(project.getName()).append(UPDATE_EXCEPTION);
            throw new PMSApplicationException(exceptionMessage.toString(), 
                                              exception);       
        } finally {
            session.close();
        }
        return project; 
    }
}
