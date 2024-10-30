package com.dtcc.dnv.otc.legacy;

import java.sql.SQLException;
import java.util.Vector;

import com.dtcc.dnv.otc.legacy.SQLCA;

import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;

import com.dtcc.dnv.otc.common.security.model.SignonDbRequest;


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
 * Rev 1.0	May 11, 2004	TK
 * Moved from otcd project to legacy
 * Rev 1.1	May 24, 2004	TK
 * Migrated to using the DBProxy framework
 * 
 * Rev 1.2	Oct 10, 2004	Shashi
 * included retreive Available Product List for the selected Originator
 * ProductList will be retreived as second resultset.
 * 
 * Rev 1.3	March 24, 2005	Shashi
 * Particpant Type is now added to indicate 
 * type of member that the user has logged on as.
 * 
 * Rev 1.4	June 14, 2005	Shashi
 * DTCCpartyId will hold 8 byte account Id and partyId will have 20 byte account id
 * blockIndicator will indicates whether the account is a block Account or not. "Y" or "N"
 * 
 * Rev	1.5			October 14, 2005			sv
 * Changed result set to retrieve the firm id and firm name.
 * 
 * 07/13/2006
 * Changed result set to retrieve the warehouse Push Type code for each account.
 * A - Auto
 * M - Manual
 * C - Compare to Imputed.
 * X - No Push Allowed 
 */


 public class DERS66C0PartyListDB extends CommonDB
 {

  public DERS66C0PartyListDB()
  {
     super(); 
     SPName = "'DERS66C0 - Family Lookup'";
  }
  
/////////////////////////////////////////////////////////////////////////
  

  public void execute( IDbRequest dbRequest, IDbResponse dbResponse ) throws SQLException
  {
	SignonDbRequest request = (SignonDbRequest) dbRequest;

	String sProdTestInd = request.getProdFlag();
	String originatorCode = request.getOriginatorCode();
	String productType = request.getProductType();

     cstmt = con.prepareCall("{ call DER.DERS66C0 (?,?,?,?) }");
     
     cstmt.registerOutParameter( 1,java.sql.Types.CHAR  );
     cstmt.setString( 2, sProdTestInd);     
     cstmt.setString( 3, originatorCode );     
     cstmt.setString( 4, productType ); //'DerivativeNetting' for pymnt rec, "CreditDefaultSwapShort" for OTCD       

     rs = cstmt.executeQuery();
     sqlca = new SQLCA((String)cstmt.getObject(1));


	Vector cplist = new Vector();
	Vector prodList = new Vector();
	Vector appModuleList = new Vector();
	
    if (sqlca.getSqlCode() == 0)
    {

    	while  (rs != null && rs.next() )
    	{
    		
				String partyId = rs.getString(1);
				String partyFullLegalName = rs.getString(2);
				String prodTestInd = rs.getString(3);
				String partyTypeInd = rs.getString(4);

				//resultset will contain 
				//8 char account ID as first column
				//20 char account ID in fifth column
				//"Y" value in sixth column indicates that it's Block Account

				String blockInd		= rs.getString(5);				 
				String externalId 	= rs.getString(6);
				String firmId		= rs.getString(7);
				String firmNm		= rs.getString(8);
				String wrhsPushTypcd = rs.getString(9);
				String wrhsGateInd = rs.getString(10);
	
				if (partyId != null) {
					partyId = partyId.trim();
				}
				if (partyFullLegalName != null) {
					partyFullLegalName = partyFullLegalName.trim();
				}
				if (prodTestInd != null) {
					prodTestInd = prodTestInd.trim();
				}
				if (partyTypeInd != null) {
					partyTypeInd = partyTypeInd.trim();
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

			CounterpartyIdName cp = new CounterpartyIdName( externalId //20 char Partcipant Number
															,partyFullLegalName //Legal Name
															,prodTestInd  //test Indicator
															,originatorCode //Originator Code
															,partyTypeInd //Paricipant Member Type Indicator (S,F,I,C)
															,partyId //8 char Paricipant Number
															,blockInd //Block Account indicator (Y,N)																														
															,firmId				// Firm id (i.e. ID for firm aka family)
															,firmNm				// Firm name (name for family -- JP Morgan)
															,wrhsPushTypcd		// warehouse Push TypCd
															,wrhsGateInd
															);

			cplist.add( cp );
			
    	}

		//getTestPartyData(cplist);


			/** 
			 * added by shashi 10/04/2004
			 * added to get applicable Product List
			 */
			int rsCount = 0;
    		while (cstmt.getMoreResults())
			{
				rs = cstmt.getResultSet();
				rsCount++;
				if (rsCount == 1) {
					while (rs != null && rs.next()) 
					{
						String prodCode = rs.getString(1);
			
						if (prodCode != null) {
							prodCode = prodCode.trim();
						}
			
						prodList.add(prodCode);
		
					} //&& rs != null && rs.next()
				}
				else if (rsCount == 2) {
					while (rs != null && rs.next()) 
					{
						String appCode = rs.getString(1);
			
						if (appCode != null) {
							appCode = appCode.trim();
						}
			
						appModuleList.add(appCode);
		
					} //&& rs != null && rs.next()
				}
			}
    		if (productType.equalsIgnoreCase("DPR")) {
    			appModuleList.add("PAYR");
    		}
			/**
			 * If no prodcut are available, we need handle no product error in the Business delegate 
			 */
    }
    
	Vector list = new Vector();
	list.add(0,cplist);
	list.add(1, prodList);			
	list.add(2, appModuleList);			
    
    dbResponse.setContent( list );
  }//end function
}//end class
  