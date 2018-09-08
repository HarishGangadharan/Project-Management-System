package com.ideas2it.employeemanagementsystem.exception;

import java.lang.Exception;

/**
 * <p>
 * User defined exception where we can able to throw our own exceptions and
 * we can able to customize the message which provides easy understanding.
 * </p>
 *
 *@author  Harish
 */
public class PMSApplicationException extends Exception {

    /**
     * <p>
     * Constructor of the customized exception in which we can display the user
     * defined exception.
     * </p>
     *
     * @param    message    customized exception message 
     *
     * @param    exception  exception obtained while catching. 
     */
    public PMSApplicationException(String message, Exception exception) {
        super(message, exception);
    }
}
