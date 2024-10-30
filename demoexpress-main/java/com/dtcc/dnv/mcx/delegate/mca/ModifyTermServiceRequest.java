/*
 * Created on Sep 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.mca;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ModifyTermServiceRequest extends MCXAbstractServiceRequest {
	
	private boolean lockIt 			= false;
	private boolean isISDA 			= false;
	private String userInd 			= "";	 	//User Indicator 
	private String docDeleteInd     = ""; 		//Document Delete Indicator
	private boolean tmpltLocked		= false;
	private boolean dbCallReq = false;
	private String usrId			= "";
	private String cmpnyCd			= "";
	private boolean actingDealer 		= false;	//Is current user acting as Dealer		

	public ModifyTermServiceRequest(AuditInfo auditInfo)
	{
		super(auditInfo);
	}
	
	/**
	 * @return Returns the cmpnyCd.
	 */
	public String getCmpnyCd() {
		return cmpnyCd;
	}
	
	/**
	 * @param cmpnyCd The cmpnyCd to set.
	 */
	public void setCmpnyCd(String cmpnyCd) {
		this.cmpnyCd = cmpnyCd;
	}
	
	/**
	 * @return Returns the usrId.
	 */
	public String getUsrId() {
		return usrId;
	}
	
	/**
	 * @param usrId The usrId to set.
	 */
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	
	/**
	 * @return Returns the lockIt.
	 */
	public boolean isLockIt() {
		return lockIt;
	}
	/**
	 * @param lockIt The lockIt to set.
	 */
	public void setLockIt(boolean lockIt) {
		this.lockIt = lockIt;
	}
	/**
	 * @return Returns the isISDA.
	 */
	public boolean isISDA() {
		return isISDA;
	}
	/**
	 * @param isISDA The isISDA to set.
	 */
	public void setISDA(boolean isISDA) {
		this.isISDA = isISDA;
	}
	/**
	 * @return Returns the userInd.
	 */
	public String getUserInd() {
		return userInd;
	}
	/**
	 * @param userInd The userInd to set.
	 */
	public void setUserInd(String userInd) {
		this.userInd = userInd;
	}

	/**
	 * @return Returns the tmpltLocked.
	 */
	public boolean isTmpltLocked() {
		return tmpltLocked;
	}
	/**
	 * @param tmpltLocked The tmpltLocked to set.
	 */
	public void setTmpltLocked(boolean tmpltLocked) {
		this.tmpltLocked = tmpltLocked;
	}

	/**
	 * @return Returns the docDeleteInd.
	 */
	public String getDocDeleteInd() {
		return docDeleteInd;
	}
	/**
	 * @param docDeleteInd The docDeleteInd to set.
	 */
	public void setDocDeleteInd(String docDeleteInd) {
		this.docDeleteInd = docDeleteInd;
	}
	/**
	 * @return Returns the dbCallReq.
	 */
	public boolean isDbCallReq() {
		return dbCallReq;
	}
	/**
	 * @param dbCallReq The dbCallReq to set.
	 */
	public void setDbCallReq(boolean dbCallReq) {
		this.dbCallReq = dbCallReq;
	}
	/**
	 * @return Returns the actingDealer.
	 */
	public boolean isActingDealer() {
		return actingDealer;
	}
	/**
	 * @param actingDealer The actingDealer to set.
	 */
	public void setActingDealer(boolean actingDealer) {
		this.actingDealer = actingDealer;
	}
}
