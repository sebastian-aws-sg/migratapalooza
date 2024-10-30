/*
 * Created on Jul 18, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.otc.common.security.model;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.dtcc.dnv.otc.legacy.CounterpartyIdName;

/**
 * @author slingaia
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * 
 * ************************************************************
 * PROJECT MOVED TO NEW REPOSITORY
 * @version	1.2				October 18, 2007		sv
 * Updated logger to be static final.
 */
public class CounterPartyListHandler {

	private static CounterPartyListHandler _instance = null;	
	//protected CounterpartyIdName[] counterPartyList;
	protected CounterpartyIdName[] testPartyList;
	protected CounterpartyIdName[] prodPartyList;
	protected Vector cpList;
	private boolean bLock = false;
	private static String PROD_IND = "P";
	private static String TEST_IND = "T";
  	private Timer timer;
  	private static final Logger log = Logger.getLogger(CounterPartyListHandler.class);
  	
	private CounterPartyListHandler() {
		super();
	}

	public static CounterPartyListHandler getInstance() {

		if(_instance == null)
			_instance = new CounterPartyListHandler();
			
		return _instance;
	}  
	
	private synchronized void loadCounterPartyList() {
		try {
			
			log.info("CounterParty loading...");
			CounterPartyDbProxy dbProxy = new CounterPartyDbProxy();
			AuditInfo auditInfo = new AuditInfo("DSV", "sessionId", "dsv", "dsv");
			CounterPartyDbRequest dbRequest = new CounterPartyDbRequest( "DTMCPLST", "ALL", auditInfo);
			CounterPartyDbResponse dbResponse = (CounterPartyDbResponse) dbProxy.processRequest(dbRequest);
			
			Vector list = (Vector) dbResponse.getContent();
			this.setCpList(list);
			
			/**
			 * load Production and Test List 
			 */
	    	Vector cloneList = (Vector) list.clone(); 
	    	
	    	int size = cloneList.size();

	    	int tCount = 0;
	    	int pCount = 0;
	    	for (int idx = 0; idx < size; idx++) {
	    		CounterpartyIdName cpName = (CounterpartyIdName) cloneList.get(idx);

	    		if (cpName.getProdTestInd().equalsIgnoreCase(TEST_IND)) {
	    			tCount++;
	    		}
	    		if (cpName.getProdTestInd().equalsIgnoreCase(PROD_IND)) {
	    			pCount++;
	    		}
	    	}

	    	int t = 0;
	    	int p = 0;
	    	
	    	CounterpartyIdName[] tPartyList = new CounterpartyIdName[tCount];
	    	CounterpartyIdName[] pPartyList = new CounterpartyIdName[pCount];

	    	for (int idx = 0; idx < size; idx++) {
	    		CounterpartyIdName cpName = (CounterpartyIdName) cloneList.get(idx);

	    		if (cpName.getProdTestInd().equalsIgnoreCase(PROD_IND)) {
	    			pPartyList[p++] = (CounterpartyIdName) cpName;
	    		}
	    		else if (cpName.getProdTestInd().equalsIgnoreCase(TEST_IND)) {
	    			tPartyList[t++] = (CounterpartyIdName) cpName;
	    		}
	    	}
	    	
			bLock = true;
	    	this.testPartyList = tPartyList;
	    	this.prodPartyList = pPartyList;
	    	
		} catch (Exception ex) {
			log.error("Exception from CounterPartyListHandler:loadCounterPartyList",ex);
			
		} finally {
			bLock = false;
		}
		log.info("CPList loaded...");
	}
	
	public synchronized CounterpartyIdName[] getCounterPartyList(String testProdInd) {
		
		if (cpList == null || cpList.isEmpty() || cpList.size() == 0){
			loadCounterPartyList();
		}
		
		while (bLock) {
			waitandCheck();
		}
		
		if (testProdInd.equalsIgnoreCase(PROD_IND)) {
			return (CounterpartyIdName[]) prodPartyList.clone();
		}
		else if (testProdInd.equalsIgnoreCase(TEST_IND)) {
			return (CounterpartyIdName[]) testPartyList.clone();
		}
		else {
			return null;
		}
	}
	
	private void waitandCheck() {
			try {
				int i = 0;
		    	for (int idx = 0; idx < 10000; idx++) {
		    		i = idx;
		    	}
		} catch (Exception ex) {
			log.error("Exception from CounterPartyListHandler:waitandCheck",ex);	
		}
	}

	/**
	 * @param cpList The cpList to set.
	 */
	private void setCpList(Vector cpList) {
		this.cpList = cpList;
	}
	
	public void resetTimer()
	{
		if (timer != null)
		{
			timer.cancel();
			timer = null;
		}
		long nextTime = getNextReloadTime(0);
		log.info("Next Reload Time after :" + nextTime);
		
		timer = new Timer();
		timer.schedule(new CPReloadTask(), nextTime);
	}


	private static long getNextReloadTime(long pTimeInterval)
	{
  		long lDifference = 0;
	 	long nextExecuteTime 	= System.currentTimeMillis();
  		long sleepTime = 0;

  		if (pTimeInterval <= 0) {
  			sleepTime = 1000 * 60 * 60;
  			/** test reload time with 5 minutes */ 
  			//sleepTime = 1000 * 60 * 5;
  		} else {
  			sleepTime = pTimeInterval;
  		}
  		
  		nextExecuteTime =sleepTime;  
  		
		return nextExecuteTime;
	}	
	//JobTask: a inner class extending timerTask
	class CPReloadTask extends TimerTask {
		public void run() {
			log.info("Started Reloading CP...");
			loadCounterPartyList();
			resetTimer();
		}
	}
	
	static {
		CounterPartyListHandler admin;
	
	     admin = CounterPartyListHandler.getInstance();
	     
	     admin.loadCounterPartyList();
	     admin.resetTimer();
}	
}
