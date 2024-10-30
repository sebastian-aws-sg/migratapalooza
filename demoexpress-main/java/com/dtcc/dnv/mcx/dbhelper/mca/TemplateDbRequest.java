package com.dtcc.dnv.mcx.dbhelper.mca;

import com.dtcc.dnv.mcx.dbhelper.MCXAbstractDbRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;


/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 * 
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048, U.S.A All Rights Reserved.
 * 
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC.
 * 
 * @author Narahari A
 * @date Sep 4, 2007
 * @version 1.0
 * 
*/

public class TemplateDbRequest extends MCXAbstractDbRequest {

	private String  catgyId   		= "";   // category Id
	private String  viewInd   		= "";   // view Indicator
	private String  dfltFlg   		= "";   // view Indicator
	private String 	userId 			= "";	// User Id
	private String  functInd        = "";   // Function Indicator
	private String  cmpnyId           = "";	// organization Id
	private String  rgnCd   		= "";   // region Id
	private String opnInd            = "";	// operation Ind	
	private String tmpltTypInd		="";	//template type indicator
	private int catgySeqId			= 0;	//Category Sequence Id

	/**
	 * Constructor for AccountUploadDbRequest.
	 * @param requestId		String
	 * @param servCall		String
	 * @param certId		String
	 * @param testProdInd	String
	 * @param auditInfo		AuditInfo
	 */
	public TemplateDbRequest(
		String requestId,
		AuditInfo auditInfo) {
		super(requestId, auditInfo);
	}
	
	/**
	 * @return Returns the catgyId.
	 */
	public String getCatgyId() {
		return catgyId;
	}
	/**
	 * @param catgyId The catgyId to set.
	 */
	public void setCatgyId(String catgyId) {
		this.catgyId = catgyId;
	}
	/**
	 * @return Returns the viewInd.
	 */
	public String getViewInd() {
		return viewInd;
	}
	/**
	 * @param viewInd The viewInd to set.
	 */
	public void setViewInd(String viewInd) {
		this.viewInd = viewInd;
	}
	/**
	 * @return Returns the dfltFlg.
	 */
	public String getDfltFlg() {
		return dfltFlg;
	}
	/**
	 * @param dfltFlg The dfltFlg to set.
	 */
	public void setDfltFlg(String dfltFlg) {
		this.dfltFlg = dfltFlg;
	}
	/**
	 * @return Returns the functInd.
	 */
	public String getFunctInd() {
		return functInd;
	}
	/**
	 * @param functInd The functInd to set.
	 */
	public void setFunctInd(String functInd) {
		this.functInd = functInd;
	}
	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return Returns the cmpnyId.
	 */
	public String getCmpnyId() {
		return cmpnyId;
	}
	/**
	 * @param cmpnyId The cmpnyId to set.
	 */
	public void setCmpnyId(String orgId) {
		this.cmpnyId = orgId;
	}
	/**
	 * @return Returns the rgnCd.
	 */
	public String getRgnCd() {
		return rgnCd;
	}
	/**
	 * @param rgnCd The rgnCd to set.
	 */
	public void setRgnCd(String rgnCd) {
		this.rgnCd = rgnCd;
	}
	/**
	 * @return Returns the opnInd.
	 */
	public String getOpnInd() {
		return opnInd;
	}
	/**
	 * @param opnInd The opnInd to set.
	 */
	public void setOpnInd(String opnInd) {
		this.opnInd = opnInd;
	}
	/**
	 * @return Returns the tmpltTypInd.
	 */
	public String getTmpltTypInd() {
		return tmpltTypInd;
	}
	/**
	 * @param tmpltTypInd The tmpltTypInd to set.
	 */
	public void setTmpltTypInd(String tmpltTypInd) {
		this.tmpltTypInd = tmpltTypInd;
	}

	/**
	 * @return Returns the catgySeqId.
	 */
	public int getCatgySeqId() {
		return catgySeqId;
	}
	/**
	 * @param catgySeqId The catgySeqId to set.
	 */
	public void setCatgySeqId(int catgySeqId) {
		this.catgySeqId = catgySeqId;
	}
	
}

