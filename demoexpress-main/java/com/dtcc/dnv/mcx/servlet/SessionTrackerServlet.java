package com.dtcc.dnv.mcx.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.security.model.UserFactory;

/*
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
 *
 * @author Kevin Lake
 * @version 1.0
 * Date: September 05, 2007
 * 
 * This servlet is used to get the session interval specifc to 
 * the server which the application is deployed on and return to
 * client for the session timer. 
 */
public class SessionTrackerServlet extends HttpServlet {
    
	// Logger
	private static MessageLogger log = MessageLogger.getMessageLogger(SessionTrackerServlet.class.getName());
	
	// Constants
	private static final String ACTION_ID_INTERVAL = "int";
	private static final String ACTION_ID_REFRESH = "ref";
		
	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		try {
			processRequest(req, resp);
		} catch (Throwable e) {
			log.error(e);
		}
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		try {
			processRequest(req, resp);
		} catch (Throwable e) {
			log.error(e);
		}
	}

	/**
	 * @param req
	 * @param resp
	 */
	public void processRequest(HttpServletRequest req, HttpServletResponse resp) {
		try {
			
			// Response
			String response = "";
			
			// Evaluate action request
			String aid = req.getParameter("aid");
			if(aid != null && aid.trim().length() > 0) {
				
				if(aid.equalsIgnoreCase(ACTION_ID_INTERVAL)) {
					
					// Return server setting for session expiration minus one minute
					int interval = req.getSession(false).getMaxInactiveInterval() - 60;
					
					// Set response value
					response = String.valueOf(interval * 1000);
					
				} else if (aid.equalsIgnoreCase(ACTION_ID_REFRESH)) {
					// Act on session to keep alive
					UserFactory.getUser(req);
				}		
				
			}			
			
			// Prepare response
			PrintWriter sw = resp.getWriter();
			
			// Write out response
			sw.write(response);
			
		} catch (UserException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}	
	}	

}
