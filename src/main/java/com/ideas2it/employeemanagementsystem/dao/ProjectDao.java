package com.ideas2it.employeemanagementsystem.dao;

import java.util.Set;

import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;
import com.ideas2it.employeemanagementsystem.model.Employee;
import com.ideas2it.employeemanagementsystem.model.Project;

/**
 * <p>
 * Helps to perform operations like adding, deleting, restoring, display all the
 * project details to the databases. 
 * </p>
 *
 * @author Harish
 */
public interface ProjectDao {

    /**
     * <p>
     * Add a new project with all their corresponding details.
     * </p>
     * 
     * @param     project     details of the project
     *
     * @return                id of the project which was added.
     */
    public String insertProject(Project project) throws PMSApplicationException;

    /**
     * <p>
     * Retrieve all the project details from the projects.
     * </p>
     *
     * @param   status    specifies whether the status is active or inactive
     *
     * @return          details of all the project.
     */
    public Set<Project> retrieveAllProjectsByStatus(String status) 
        throws PMSApplicationException;

     /**
     * <p>
     * Search a particular project from the projects with the
     * help of the specified projectId.
     * </p>
     *
     * @param   projectId     id of the particular project.
     *
     * @return                Information of that particular project.
     */
    public Project searchProjectByIdAndStatus(int projectId, String status) 
        throws PMSApplicationException;

    /**
     * <p>
     * Delete the particular project from the projects.
     * <p>
     *
     * @param   project      details of the particular project
     *                               to be deleted.
     *
     * @return                id of the project who was deleted.
     */
    public int deleteProject(Project project) throws PMSApplicationException;

    /**
     * <p>
     * Replace the updated project details with the existing projects.
     * </p>
     *
     * @param   project      recently modified project information
     * 
     *
     * @return               project information after updation.
     */ 
    public Project updateProjectDetail(Project project) 
        throws PMSApplicationException;

    /**
     * <p>
     * Restore the specified project.
     * </p>
     * 
     * @param    project    project information.
     *
     * @return               project information after restoring.
     */
    public Project restoreProject(Project project) 
        throws PMSApplicationException;
}
