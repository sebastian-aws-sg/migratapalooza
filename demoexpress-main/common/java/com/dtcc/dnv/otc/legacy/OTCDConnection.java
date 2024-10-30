package com.dtcc.dnv.otc.legacy;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;


/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
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
 * @version 	1.0
 * @author    Dmitriy Larin
 * 
 * 
 * rev 1.5 sbrown 4/27/2004
 * Removed autocommit code.
 * Added rollback and commit methods
 *
 * rev 1.6 sbrown 4/27/2004
 * set autocommit to false
 * 
 * rev 1.7 sbrown 4/27/2004
 * changed an error message (Unable to set connection autocommit to false for OTCDPool)
 * 
 * rev 1.8 sbrown 4/28/2004
 * added a check to see if connection is null in commitConn().
 * changed error messages in commitConn() and rollbackConn()
 * 
 * LEGACY CODE MOVED TO NEW PROJECT
 * 
 * rev DSV1.1 dlv    5/15/2004
 * get rid of FormException reference
 * 
 * rev DSV1.2 TK 06/25/2004
 * Standardized getConnection() to throw SQLException
 * 
 * Rev  1.3     December 29, 2004   sav
 * Removed reference to OTCDConstants and places static constant in this file.
 * 
 * Rev	1.4		May 5, 2005			sv
 * Added getConnection(poolName) to take in the name of the connection 
 * pool.  Updated getConnection() to call the new getConnnection(poolName) 
 * method with the default poolName (via OTCDPool constant).
 * 
 * Rev	1.5		May 25, 2006		sv
 * Removed the JDBCConnectionPool class as a singleton.  Gloabls is already a singleton
 * and holds references to the JDBCConnectionPool object(s).  This was done to allow
 * different pools to be used while also using this framework otherwise classes would
 * override each other.  Updated closeAll() to take poolName so that the appropriate 
 * pool can be used to release the connection.
 * 
 * #### New versioning sequence used as this action class is modified for CRE.
 * 
 * @version 1.1
 * @author Cognizant
 * 07/04/2007 	Modified to obtain the connection using the environmental entry in web.xml
 * 
 * Changed the code to use ServiceLoacator class to look up the datasource name from
 * the environmental entry in the web.xml
 * 
 * Rev	1.2					July 21, 2007		sv
 * Updated to remove dependancy on pcweb and webplatform by using a new class 
 * 
 * @version	1.3				July 23, 2007			sv
 * Updated with Cognizant changes
 * 
 * @version	1.4				October 16, 2007		sv
 * Updated DataSource.getConnection() to use emprty parameter
 * signature rather than (null, null).  Removed printStackTrace().
 * 
 * @vserion	1.5				October 18, 2007		sv
 * Updated logger to static final.
 */

public class OTCDConnection{
	// Singleton instance
	public static OTCDConnection singleton = null;
	private static final Logger log = Logger.getLogger(OTCDConnection.class);

    // Constants
    public static final String OTCDPool = "OTCDPool";

	/**
	 * Private constructor to be created by local factory (getInstance()) method.
	 */
	private OTCDConnection(){
		super();
	}

	/**
	 * Factory method used to get instance of this class.
	 * 
	 * @return OTCDConnection object instance
	 */
	public static synchronized OTCDConnection getInstance(){
		// If an instance does not exist, then create one.
		if (singleton == null){
			singleton = new OTCDConnection();
		}
		return singleton;
	}

	/**
	 * This method returns a connection intended for OTCDPool.
	 * 
	 * @return
	 * @throws SQLException
	 * @deprecated
	 */
	public Connection getConnection() throws SQLException{
		// No parameter was passed, use the default.
		Connection con = this.getConnection(OTCDPool);
		return con;
	}
	
	/**
	 * This methods gets a connection from the pool name provided.
	 * 
	 * @param poolName String value of the poolName
	 * @return Connection object for the respective poolName
	 * @throws SQLException Exception thrown if there are errors.
	 */
	public Connection getConnection(String poolName) throws SQLException{
		Connection con = null;
		try
		{
			/*
			 * uses the ServiceLocator class to get the datasource name.
			 */
			DataSource ds = ServiceLocator.getDataSource(poolName);
			con = ds.getConnection();
		}
		catch (Throwable e)
		{
			log.error("Error occurred while trying to get connection: " + e);
			con = null;
			throw new SQLException ("Error occurred while trying to get connection:"+ poolName);
		}
		//New code for Shadow DS.
		//Validate connection type and set autocommit to true in case if it is not committed.
		if (con != null)
		{
			//set autocommit to false
			con.setAutoCommit(false);
		}
		return con;
	}

	/**
	 * closes all elements of database like the resultset, connection, CallableStatement
	 * @param rs resultSet
	 * @param cs CallableStatement
	 * @param con connection
	 * @param poolName
	 */
	public void closeAll(ResultSet rs, CallableStatement cs, Connection con, String poolName){
		try
		{
			try
			{
				if (rs != null)
					rs.close();
			}
			catch (SQLException e)
			{
				//Do nothing - no interest in Result Set!
				log.info("Error while closing resultset stmt "+ e);
			}
			catch (Exception e)
			{
				//Do nothing - no interest in Result Set!
				log.info("Error while closing resultset stmt "+ e);
			}
			try
			{
				if (cs != null)
					cs.close();
			}
			catch (SQLException e)
			{
				log.info("Error while closing callable stmt "+ e);
				//Do nothing - no interest in Callable Statement
			}
			catch (Exception e)
			{
				log.info("Error while closing callable stmt "+ e);
				//Do nothing - no interest in Callable Statement
			}
			log.info("Everything closed ");
			if (con != null)
				releaseConnection(con);
		}
		catch (Throwable e)
		{
			log.info("Error while closing  "+ e);
			// Drivers are C/C++ based and
			//   frequently throwing Runtime exception...
			// We need to catch Throwable to prevent system from
			//   stalling.
			// We do not need to report it, but we do need to handle it.
			// place log4j logging code here
		}
	}
	
	/**
	 * Releases the connection that is done with.
	 * @param con connection to be released 
	 * @return null.
	 */
	public final void releaseConnection(Connection con){
        
		try {
            con.close();
        } catch(SQLException sqle) {
            log.error(sqle);
        }
    }

	/**
	 * Commits this connection
	 *
	 * @throws Exception
	 */
	public void commitConn(Connection conn){
		try
		{
			if(conn != null)
				conn.commit();
		}
		catch (SQLException sqle)
		{
			log.error("SQLException while trying to commit connection!", sqle);
		}
		catch (Exception e)
		{
			log.error("Exception while trying to commit connection!", e);
		}
	}

	/**
	 * Rollsback the connection
	 */
	public void rollbackConn(Connection conn){
		try
		{
			if (conn != null)
			{
				conn.rollback();
			}
		}
		catch (SQLException sqle)
		{
			log.error("SQLException while trying to rollback connection!", sqle);
		}
		catch (Exception e)
		{
			log.error("Exception while trying to rollback connection!", e);
		}
	}
}
