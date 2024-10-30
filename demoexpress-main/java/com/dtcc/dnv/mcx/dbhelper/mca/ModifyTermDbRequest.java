/*
 * Created on Sep 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.dbhelper.mca;

import com.dtcc.dnv.mcx.dbhelper.MCXAbstractDbRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ModifyTermDbRequest extends MCXAbstractDbRequest {
	
	private boolean isISDA 				= false;
	private String indicator 			= "";
	private String functionalIndicator 	= "";
	private int valueID 				= 0;  //Either DocId or TextID
	private String userInd 				= ""; //User Indicator 
	private String docDeleteInd 		= ""; //Document Delete Indicator
	private boolean tmpltLocked		= false;
	private String usrId				= "";
	private String cmpnyCd				= "";

	public ModifyTermDbRequest(String requestId, AuditInfo auditInfo)
	{
		super(requestId, auditInfo);
	}
	
	/**
	 * @return Returns the functionalIndicator.
	 */
	public String getFunctionalIndicator() {
		return functionalIndicator;
	}

	/**
	 * @param functionalIndicator The functionalIndicator to set.
	 */
	public void setFunctionalIndicator(String functionalIndicator) {
		this.functionalIndicator = functionalIndicator;
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
	 * @return Returns the valueID.
	 */
	public int getValueID() {
		return valueID;
	}
	/**
	 * @param valueID The valueID to set.
	 */
	public void setValueID(int valueID) {
		this.valueID = valueID;
	}
	/**
	 * @return Returns the txtIndicator.
	 */
	public String getIndicator() {
		return indicator;
	}
	/**
	 * @param txtIndicator The txtIndicator to set.
	 */
	public void setIndicator(String indicator) {
		this.indicator = indicator;
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
}
