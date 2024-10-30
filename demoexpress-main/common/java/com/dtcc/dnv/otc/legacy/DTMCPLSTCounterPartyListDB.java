package com.dtcc.dnv.otc.legacy;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.security.model.CounterPartyDbRequest;
import com.dtcc.dnv.otc.common.security.model.CounterPartyDbResponse;
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
 * @author     Robert Masri
 * 
 * 
 * DLV 9/16/2004
 * Removed unused import: import com.dtcc.dnv.otc.common.security.model.SignonDbResponse;
 * 
 *	Rev	1.4				October 13, 2005		sv
 * Updated to also retrieve the firm id and the firm name. 
 * 
 * ************************************************************
 * PROJECT MOVED TO A NEW REPOSITORY.
 * @version	1.1			October 18, 2007		sv
 * Removed unused imports.
 */

public class DTMCPLSTCounterPartyListDB extends CommonDB {

  public DTMCPLSTCounterPartyListDB()
  {
  	 super();
     SPName = "DTMCPLST-CounterParty Lookup";  
  }

/////////////////////////////////////////////////////////////////////////

  public void execute(IDbRequest dbRequest, IDbResponse dbResponse) throws SQLException
  {
	CounterPartyDbRequest request = (CounterPartyDbRequest) dbRequest;
	CounterPartyDbResponse response = (CounterPartyDbResponse) dbResponse;
	
    cstmt = con.prepareCall("{ call DTM.DTMCPLST (?, ?, ?, ?) }");

    cstmt.registerOutParameter(1, Types.CHAR); //OUTSQLCA
    cstmt.registerOutParameter(2, Types.CHAR); //SP_ERROR_AREA
    cstmt.setString( 3, "");
    cstmt.setString( 4, "");    
    //
    rs = cstmt.executeQuery();
    sqlca = new SQLCA((String)cstmt.getObject(1));
    sSpErrArea = (String) cstmt.getObject(2);
    response.setSqlcaErrorCode(sqlca.getSqlCodeAsString());
    response.setSpReturnCode("SP00");
    
	Vector list = new Vector();

    while(sqlca.getSqlCode() == 0 && rs != null && rs.next() )
    {

		String partyId = rs.getString(1);
		String partyFullLegalName = rs.getString(2);

		//resultset will contain 
		//8 char account ID as first column
		//20 char account ID in fifth column
		//"Y" value in sixth column indicates that it's Block Account
		//Firm Id in sixth column indicates an id for the firm
		//Firm name value in sixth column indicates the name of the firm(JP Morgan)

		String blockInd		= rs.getString(3);
		String externalId 	= rs.getString(4);
		String firmId		= rs.getString(5);
		String firmNm		= rs.getString(6);
		String prodTestInd = rs.getString(7);
		String wrhsPushTypcd = rs.getString(8);
		String wrhsGateInd = rs.getString(9);
 

		if (partyId != null) {
			partyId = partyId.trim();
		}
		if (partyFullLegalName != null) {
			partyFullLegalName = partyFullLegalName.trim();
		}

		if (externalId != null) {
			externalId = externalId.trim();
		}
		if (blockInd != null) {
			blockInd = blockInd.trim();
		}
		if (firmId != null) {
			firmId = firmId.trim();
		}
		if (firmNm != null) {
			firmNm = firmNm.trim();
		}
		
		if (wrhsPushTypcd != null) {
			wrhsPushTypcd = wrhsPushTypcd.trim();
		}													
		if (wrhsGateInd != null) {
			wrhsGateInd = wrhsGateInd.trim();
		}													

		//CounterpartyIdName cp = new CounterpartyIdName();
		
		CounterpartyIdName cp = new CounterpartyIdName( externalId 		//20 char Counterparty Partcipant Number
														,partyFullLegalName //Legal Name
														,prodTestInd					//test Indicator
														,""			 		//Originator Code
														,"" 				//Paricipant Member Type Indicator (S,F,I)
														,partyId 			//8 char Paricipant Number
														,blockInd 			//Block Account indicator (Y,N)																														
														,firmId				// Firm id (i.e. ID for firm aka family)
														,firmNm				// Firm name (name for family -- JP Morgan)
														,wrhsPushTypcd		// warehouse Push TypCd
														,wrhsGateInd
														);

		list.add( cp );

    }	
    dbResponse.setContent( list );
  }  // end function
}   // end class