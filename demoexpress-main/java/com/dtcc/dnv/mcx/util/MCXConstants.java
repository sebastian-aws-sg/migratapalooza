package com.dtcc.dnv.mcx.util;

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
 */
public class MCXConstants {
	
	public static final String SUCCESS_FORWARD = "success";
	public static final String FAILURE_FORWARD = "failure";
	public static final String ERROR_FORWARD = "error";
	public static final String ERROR_POPUP_FORWARD = "errorPopup";
	
	public static final String COMPANY_TYPE_DEALER_CD = "D";
	public static final String COMPANY_TYPE_CLIENT_CD = "C";
	
	public static final String COMPANY_STAT_INT_IN_CD = "I";
	public static final String COMPANY_STAT_EXT_IN_CD = "E";
	
	public static final String MCA_STATUS_PENDING = "P";
	public static final String MCA_STATUS_EXECUTED = "E";	
	
	public static final String SUBMIT = "SUBMIT";
	public static final String ADDCP = "ADDCP";
	public static final String EDITCP = "EDITCP";
	public static final String ADDCPSUCCESS = "ADDCPSUCCESS";
	public static final String ADDCPFAILURE = "ADDCPFAILURE";
	public static final String ADDCPFLAG = "A";
	public static final String RENAMECPFLAG = "R";	

	
	public static final String PREEXISTING = "PreExisting";
	public static final String PREEXISTINGTAB = "P";
	public static final String OTHERS = "Others";
	public static final String OTHERTAB = "O";
	public static final String MANAGEDOCSTAB = "manageDocTab";
	public static final String DEFAULTVALUE = "default";
	
	public static final String ALL = "ALL";
    public static final String SELECTCOUNTERPARTY = MessageResources.getMessage("mcx.ManageDocs.label.SelectCounterPartys");
	public static final String ALLDEALERS = MessageResources.getMessage("mcx.ManageDocs.search.filterby.label.AllCounterPartys");
	
	public static final String ADDCOUNTERPARTYDISP = MessageResources.getMessage("mcx.ManageDocs.label.AddCounterPartys");
	public static final String ADDCOUNTERPARTY = "Add CounterParty";
	public static final String MCAFIRM = "R";
	public static final String NOTMCAFIRM = "U";
	
	public static final String SEARCHCONST = "SEARCH";
	public static final String CLEARCONST = "CLEAR";
	public static final String DELETECONST = "DELETE";
	
	public static final String MCAINDICATOR = "*";
	
    // Email Constants
	public static final String MIME_HTML = "text/html";
	public static final String MCX_FROM_ADDRESS_KEY = "mcx.mail.from.addr";
	
	public static final String MCX_EMAIL_APPROVALREQ_SUBJ_KEY = "mcx.approval.request.subj";
	public static final String MCX_EMAIL_APPROVALREQ_FILE_KEY = "mcx.approval.request.file";
	
	public static final String MCX_EMAIL_APPROVAL_SUBJ_KEY = "mcx.approval.notification.subj";
	public static final String MCX_EMAIL_APPROVAL_FILE_KEY = "mcx.approval.notification.file";
	
	public static final String MCX_EMAIL_DENIAL_SUBJ_KEY = "mcx.denial.notification.subj";
	public static final String MCX_EMAIL_DENIAL_FILE_KEY = "mcx.denial.notification.file";
	
	
	public static final String FUNCTION_INDICATOR_ADMIN = "A";
	public static final String FUNCTION_INDICATOR_MCA = "M";
	public static final String VIEW_INDICATOR_COMPLETE = "F";
	public static final String VIEW_INDICATOR_AMENDMENT = "A";
	public static final String VIEW_INDICATOR_COMMENTS = "C";
	
	public static final String FRM_SCR_ISDA = "ISDA";
	public static final String FRM_SCR_WIZARD_1 = "MW1";
	public static final String FRM_SCR_WIZARD_2 = "MW2";
	public static final String FRM_SCR_NEGOGIATION = "NEG";

	
	public static final String SLT_IND_MENU = "ME";	
	public static final String SLT_IND_PRODUCT = "PR";
	public static final String SLT_IND_REGION = "RG";
	public static final String SLT_IND_TEMPLATE = "TE";
	public static final String SLT_IND_CATEGORY = "CG";
	public static final String SLT_IND_AMD_CMNT = "AC";
	public static final String SLT_IND_VIEW = "VW";
	public static final String SLT_IND_ENR_APP = "EA";	
	public static final String SLT_IND_PRINT = "PT";
	
	public static final String FINDNEWALERT = "findnewalert";

	public static final String DOCUMENT_INDICATOR			= "D";
	public static final String TEXT_INDICATOR				= "T";
	public static final String COMMENT_INDICATOR			= "C";		
	public static final String AGREE_AMENDMENT_STATUS_CD	= "A";	
	public static final String REQ_ISDA_TERM				= "I";
	public static final String REQ_AMEND_TERM				= "C";
	public static final String PREEXISTINGDISP = "Pre-Exec";
	
    public static final String MCA_STATUS_PENDING_ENROLLMENTS = "P";
    public static final String MCA_STATUS_APPROVEDFIRMS = "A";
    public static final String MCA_STATUS_APPROVE_ACTION = "APPROVE";
    public static final String MCA_STATUS_DENY_ACTION = "DENY";
    public static final String MCA_STATUS_DENYFIRMS = "D";
    public static final String MCA_STATUS_APPROVE = "Approve";
    public static final String MCA_ACTION = "action";
    public static final String MCA_PENDING_STATUS = "PendingEnrollment";
    public static final String MCA_APPROVE_STATUS = "Approved Firms";

	public static final String VIEW_COMMENT	= "V";	
	public static final String CONST_YES	= "Y";	
	public static final String LOCKED_INDICATOR = "L";
	public static final String ISDA_TEMPLATE_TYPE = "I";
	public static final String GENERIC_TEMPLATE_TYPE = "D";
	public static final String UNLOCK					= "U";
	public static final String WORKING_TEMPLATE_TYPE 	= "W";
	public static final String EXECUTED_TEMPLATE_TYPE 	= "E";
	public static final String REEXECUTED_TEMPLATE_TYPE	= "R";
	public static final String CP_FINAL_TEMPLATE_TYPE 	= "C";
	public static final String ENROLLEDCPs				= "ENROLLEDCPs";
	public static final String OPERATION_IND_S			= "S";
	public static final String EXECUTE_POPUP			= "execute";
	public static final String DEALER_FWD				= "dealer";
	public static final String CLIENT_FWD				= "client";
	public static final String DEFAULT_TIMESTAMP 		= "-00.00.00.000000";
	public static final String DEFAULT_DATETIMESTAMP 		= "9999-12-31-00.00.00.000000";
	public static final String PRODUCTS					= "Products";
	
//	Admin Template Listing
    public static final String MCA_ADMIN_ACTION = "A";
    public static final String MCA_ADMIN_STATUSCD = "New";
    public static final String MCA_ADMIN_STATCD = "In-Progress";
    public static final String MCA_ADMIN_STATUSCODE = "Pending approval";


	public static final String MCA_USER_TYPE_DEALER = "dealer";
	public static final String MCA_USER_TYPE_CLIENT = "client";
	public static final String MCA_USER_TYPE_TEMPLATE_ADMIN = "templateAdmin";

	public static final String GENERIC_TEMPLATE_NAME		= "GENERIC";	
	public static final String CHECK_SAVE					= "C";	
	public static final String DELETE_INDICATOR				= "D";
	public final static String GENERAL_INTERNAL_ERROR = "general.internal.error";
	public final static String GENERAL_BUSINESS_ERROR = "general.business.error";

    public static final String EMPTY						= "";
	public static final String APPLY = "APPLY";
	public static final String EXECUTE = "EXECUTE";
	public static final String TILD							= "~";
	public static final String ORG_USR						= "O";

    public static final String MANAGE_DOC_ORG_DELETE = "D";
    public static final String MANAGE_DOC_ORG_REASSIGN = "R";
    public static final String MANAGE_DOC_ACTIVITY_LIST = " ";
    public static final String MANAGE_DOC_ACTIVITY_DELETE = "D";
    public static final String MANAGE_DOC_TYPE_DOC = "doc";
    public static final String MANAGE_DOC_TYPE_PDF = "pdf";
    public static final String MANAGE_DOC_TYPE_ZIP = "zip";
    public static final String MANAGE_DOC_TYPE_XLS = "xls";
	
    public static final String IMAGE_TYPE_GIF 		= "gif";
    public static final String IMAGE_TYPE_JPG 		= "jpg";
	
    public static final String MANAGE_DOC_CONTENTTYPE_DOC = "application/msword";
    public static final String MANAGE_DOC_CONTENTTYPE_PDF = "application/pdf";
    public static final String MANAGE_DOC_CONTENTTYPE_ZIP = "application/zip";
    public static final String MANAGE_DOC_CONTENTTYPE_XLS = "application/vnd.ms-excel";
    public static final String MANAGE_DOC_REASSIGN = "Y";
    public static final String EMPTY_SPACE = "";
    public static final String MANAGE_DOC_MCA_DEFAULT = "default";
    public static final String MANAGE_DOC_UPLOAD_YES = "Y";
    public static final String MANAGE_DOC_UPLOAD_NO = "N";
    public static final int FILE_UPLOAD_BYTE_SIZE = 2097152;
    
    public static final String PROPREITARY_CATEGORY_STATUS = "P";
    public static final String LOGIN_TIME = "Login Time";
    public static final String LOGIN_TIME_FORMAT = "MM/dd/yyyy hh:mm:ss zzz";

    public static final String MCA_STATUS_PUBLISHED = "P";
    
    public static final String MCA_SIDE_MENU 			= "MCASideMenu";
    public static final String TERMFORM 				= "TERMFORM";
    public static final String PROPREITARY_TERM = "PROPREITARY_TERM";

    public static final String BASE_TMPLT_IS_FI_EX = "BASE_TMPLT_IS_FI_EX";

}
