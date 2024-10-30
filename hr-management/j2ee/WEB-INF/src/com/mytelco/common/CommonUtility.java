/*
 * Created on Aug 19, 2005
 *
 *
 */
package com.mytelco.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;

/**
 * @author myTelco 
 *
 *
 */
public class CommonUtility {

	public static String CENTRAL_WEB_ROOT_PATH = "http://www.mytelco.com";

	private static String mStrCentralDBName;

	private static HashMap mDepartmentDBNames;

	public static String getCentralDBName() {

		if (mStrCentralDBName == null || "".equals(mStrCentralDBName)) {
			Properties propAccess = new Properties();
			try {

				System.out.println("About to load");
				propAccess.load(new CommonUtility().getClass()
						.getResourceAsStream("/com/mytelco/common/mytelco.properties"));
				System.out.println("Loaded!!!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			mStrCentralDBName = propAccess.getProperty("cast.centraldbname");
		}

		return mStrCentralDBName;
	}

	public static String getDepartmentDBName(String departmentId) {
		if (mDepartmentDBNames == null) {
			mDepartmentDBNames = new HashMap();
			Properties propAccess = new Properties();
			InputStream mobjInputStream = new CommonUtility().getClass()
					.getResourceAsStream("mytelco.properties");
			try {
				propAccess.load(mobjInputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}

			for (Enumeration enum1 = propAccess.keys(); enum1.hasMoreElements();) {
				String key = (String) enum1.nextElement();
				if (key.startsWith("dept")) {
					mDepartmentDBNames.put(key, propAccess.getProperty(key));
				}
			}
		}

		return (String) mDepartmentDBNames.get(departmentId);
	}

	public static Connection getConnection(String databaseName)
			throws NamingException, SQLException {
		System.out.println(" Checking Connection for " + databaseName);
		// Get a database connection
		// using the DataSource and return it
		Context namingContext = new InitialContext();
		DataSource ds = (DataSource) namingContext.lookup(databaseName);
		return ds.getConnection();
	}

	public static void closePreparedStatement(PreparedStatement pStmt) {
		if (pStmt != null) {
			try {
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the HomeInterface of any bean requested. It is written to avoid
	 * writing the same code all over again to get the Home interface. This
	 * function takes in the JNDI name and the HomeInterface.class of the bean.
	 * The return value is of type object. This would then need to be casted in
	 * the calling method.
	 *
	 * @param astrEJB -
	 *            The JNDI name of the deployed bean.
	 * @param aClass -
	 *            The Class of the home interface for the bean.
	 * @return Object - containing the reference to the Home Interface of the
	 *         required bean.
	 */
	public static Object getHomeInterface(String astrEJB, Class aClass) {
		
		String strDebug = "getHomeInterface" + ":getHomeInterface";
		Object obj = null;
		try {
			Context ic = new InitialContext();
			String strTemp = "";
			obj = PortableRemoteObject.narrow(ic.lookup(strTemp + astrEJB),
					aClass);
		} catch (NamingException e) {
			System.out.println(strDebug + " Naming Exception: " + e);
			System.out.println(strDebug + " Explanation " + e.getExplanation());
			System.out.println(strDebug + " Resolved name : "
					+ e.getResolvedName());
			System.out.println(strDebug + " RemainingName "
					+ e.getRemainingName());
			System.out.println(strDebug + " Root Cause: " + e.getRootCause());
		} catch (ClassCastException ex) {
			System.out.println(strDebug + " ClassCastException Exception "
					+ ex.getMessage());

			// ex.printStackTrace();
		} catch (Exception e) {
			System.out.println(strDebug + " Exception: " + e.getMessage());

			// e.printStackTrace();
		}

		return obj;
	}

}