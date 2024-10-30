package com.dtcc.dnv.mcx.company;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.dtcc.dnv.mcx.beans.CompanyBean;
import com.dtcc.dnv.mcx.dbhelper.company.CompanyListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.company.CompanyListDbResponse;
import com.dtcc.dnv.mcx.proxy.company.CompanyDbProxy;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;

/*
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 * 
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048 U.S.A All Rights Reserved.
 * 
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC.
 * 
 * @author Kevin Lake
 * 
 * @version 1.0 Date: September 05, 2007
 */
public class CompanyListHandler {

	/* Company List Handler Instance */
	private static CompanyListHandler _instance = null;

	/* Company Bean array */
	protected CompanyBean[] companyBeanList;

	/* Company List */
	protected Vector companyList;

	/* Wait flag */
	private boolean bLock = false;
	
	/* Timer object */
	private Timer timer;

	/* Message Logger */
	private final static MessageLogger log = MessageLogger.getMessageLogger(CompanyListHandler.class.getName());
	
	/* Static members */
	static {
		CompanyListHandler admin = CompanyListHandler.getInstance();
		admin.loadCompanyList();
		admin.resetTimer();
	}

	/**
	 * CompanyListHandler
	 */
	private CompanyListHandler() {
		super();
	}

	/**
	 * @return CompanyListHandler
	 */
	public static CompanyListHandler getInstance() {
		if (_instance == null)
			_instance = new CompanyListHandler();
		return _instance;
	}

	/**
	 * loadCompanyList
	 */
	private void loadCompanyList() {
		try {			
			log.info("Company List Loading...");
			
			bLock = true;
			
			/* Get company list from backend */
			CompanyDbProxy dbProxy = new CompanyDbProxy();			
			CompanyListDbRequest dbRequest = new CompanyListDbRequest("DPMXHCMD", null);
			CompanyListDbResponse dbResponse = (CompanyListDbResponse) dbProxy.processRequest(dbRequest);
			Vector list = (Vector) dbResponse.getContent();
			
			/* Set returned list */
			this.setCompanyList(list);

		} catch (DBException ex) {
			log.error(ex);						
		} finally {
			bLock = false;
		}
		log.info("Company List Loaded...");
	}

	/**
	 * @param testProdInd
	 * @return CompanyBean[]
	 */
	protected CompanyBean[] getCompanyList(String internalExternalInd) {

		/* Load list if empty */
		if (companyList == null || companyList.isEmpty() || companyList.size() == 0) {
			loadCompanyList();
		}

		/* Wait if blocked */
		while (bLock) {
			waitandCheck();
		}

		Vector cloneCompanyList = null;
		if(companyList != null)
			cloneCompanyList = (Vector)companyList.clone();
		else {
			log.error("Company list was not initialized");
			return null;
		}
		
		int size = cloneCompanyList.size();
		Iterator itrCpList = cloneCompanyList.iterator();

		/* Build Company List */
		Vector tmpCompanyList = new Vector();
		int companyCount = 0;
		for (int idx = 0; idx < size; idx++) {
			CompanyBean company = (CompanyBean) cloneCompanyList.get(idx);
			if (company.getCompanyEnvInd().equalsIgnoreCase(internalExternalInd)) {
				companyCount++;
				tmpCompanyList.add(company);
			}
		}
		
		Iterator iter = tmpCompanyList.iterator();
		CompanyBean[] companyList = new CompanyBean[companyCount];
		int count = 0;
		while (iter.hasNext())
			companyList[count++] = (CompanyBean) iter.next();

		return companyList;
	}
	
	/**
	 * @return CompanyBean[]
	 */
	protected CompanyBean[] getCompanyList() {

		/* Load list if empty */
		if (companyList == null || companyList.isEmpty() || companyList.size() == 0) {
			loadCompanyList();
		}

		/* Wait if blocked */
		while (bLock) {
			waitandCheck();
		}

		Vector cloneCompanyList = null;
		if(companyList != null)
			cloneCompanyList = (Vector)companyList.clone();
		else {
			log.error("Company list was not initialized");
			return null;
		}
		
		int size = cloneCompanyList.size();
		Iterator itrCpList = cloneCompanyList.iterator();

		/* Build Company List */
		Vector tmpCompanyList = new Vector();
		int companyCount = 0;
		for (int idx = 0; idx < size; idx++) {
			CompanyBean company = (CompanyBean) cloneCompanyList.get(idx);
			companyCount++;
			tmpCompanyList.add(company);			
		}
		
		Iterator iter = tmpCompanyList.iterator();
		CompanyBean[] companyList = new CompanyBean[companyCount];
		int count = 0;
		while (iter.hasNext())
			companyList[count++] = (CompanyBean) iter.next();

		return companyList;
	}

	/**
	 * Wait and check again
	 */
	private void waitandCheck() {
		try {
			Thread th = new Thread();
			th.start();
			Thread.sleep(1000);
			th = null;
		} catch (InterruptedException ex) {
          log.error(ex);
		}
	}

	/**
	 * @param counterPartyList
	 *            The counterPartyList to set.
	 */
	private void setCompanyList(CompanyBean[] companyBeanList) {
		this.companyBeanList = companyBeanList;
	}

	/**
	 * @param companyList	 
	 */
	private void setCompanyList(Vector companyList) {
		this.companyList = companyList;
	}

	/**
	 * Reset Timer
	 */
	private void resetTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		long nextTime = getNextReloadTime(0);
		
		log.info("Next Reload Time after :" + nextTime);

		timer = new Timer();
		timer.schedule(new CompanyReloadTask(), nextTime);
	}

	/**
	 * Get next reload time
	 * @param pTimeInterval
	 * @return long
	 */
	private static long getNextReloadTime(long pTimeInterval) {
		long nextExecuteTime = 0;
		long sleepTime = 0;

		if (pTimeInterval <= 0) {
			sleepTime = 1000 * 60 * 30;			
		} else {
			sleepTime = pTimeInterval;
		}
		nextExecuteTime = sleepTime;

		return nextExecuteTime;
	}

	// Inner class extending timerTask	
	class CompanyReloadTask extends TimerTask {
		public void run() {
			log.info("Started Reloading of Company List...");
			loadCompanyList();
			resetTimer();
		}
	}


}
