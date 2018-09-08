package com.ideas2it.employeemanagementsystem.logger; 

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.Logger;

public class PMSLogger {

    private static final Logger logger;
    private static final String LOG_FILE = "log4j.xml";
    static {
        logger = Logger.getLogger(PMSLogger.class);
        DOMConfigurator.configure(LOG_FILE);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }
   
    public static void error(String message) {
        logger.error(message);
    }

    public static void fatal(String message) {
        logger.fatal(message);
    }
}
