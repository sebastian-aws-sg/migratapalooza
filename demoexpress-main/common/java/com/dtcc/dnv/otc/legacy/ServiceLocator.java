package com.dtcc.dnv.otc.legacy;

import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Copyright © 2007 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 * Form bean for a Struts application.
 * 
 * @author Cognizant
 * @version 1.0 This class is used to lookup all resources through jndi names.
 * 
 * @version	1.1				July 23, 3007			sv
 * Updated with Cognizant changes.
 * 
 * @version 1.2				August 7, 2007			sv
 * Removed extra logger.  Removed lookup of environment variable for pool name.
 * Check in for Cognizant.
 * 
 * @version	1.3				October 2, 2007			sv
 * Updated comments and removed static references.  Removed static InitialContext
 * reference.
 * 
 * @vserion	1.4				October 18, 2007		sv
 * Updated logger to static final.
 */
public final class ServiceLocator {
    /** Instance of Log */
	private static final Logger logger = Logger.getLogger(ServiceLocator.class);

    /** Instance of ServiceLocator */
    private static ServiceLocator serviceLocator = new ServiceLocator();
	
    /** INITIAL CONTEXT FACTORY for JNDI Lookup */
    private static String initialContextFactory = null;
    
    /**
     * Gets the instance of Service Locator.
     * @return ServiceLocator
     */
    public static ServiceLocator getInstance() {
        return serviceLocator;
    }

    /**
     * Constructor for ServiceLocator
     */
    private ServiceLocator() {
    }

    /**
     * @param initialContextFactoryName
     */
    public void setInitialContextFactory(String initialContextFactoryName) {
        initialContextFactory = initialContextFactoryName;
    }

    /**
     * This method obtains the datasource itself for a caller
     * @param jndiName String
     * @return dataSource
     * @throws SQLException wraps the exception occurs while executing sql
     *             operations
     */
    public static DataSource getDataSource(String jndiName) throws SQLException {
        DataSource dataSource = null;
		String envBase = "java:comp/env/";
		InitialContext ctx = null;
        try {
            // If the factory has not already bee found, then look it up. 
        	if(initialContextFactory == null){
        		// Instantiate initial context.
    			ctx = new InitialContext();
    			try{
    				// Look up factoy in environment variable.
    				initialContextFactory = (String)ctx.lookup(envBase + "initialContextFactory");
				} catch (NameNotFoundException exp){
					// Set it to null so that default can be used.
					initialContextFactory = null;
				}
            }
        	// If factory has been found, then look create the correct InitialContext. 
        	if (initialContextFactory != null) {
                Hashtable cFactoryTable = new Hashtable();
                cFactoryTable.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
                ctx = new InitialContext(cFactoryTable);
            } else {
            	ctx = new InitialContext();
            }
        	// Lookup datasource
			dataSource = (DataSource)ctx.lookup(jndiName);
        } catch (NamingException ne) {
            throw new SQLException(ne.getMessage());
        } catch (Exception nex) {
            throw new SQLException("Unable to create data source");
        } finally {
        	// Close the context if it is not null.
            if (ctx != null) {
                try {
                	ctx.close();
                } catch (NamingException ignore) {
                }
            }
        }
        //  return the data source. 
        return dataSource;
    }
}