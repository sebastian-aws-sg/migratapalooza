package com.dtcc.dnv.otc.legacy;

import java.sql.SQLException;
import java.util.Vector;

import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.security.model.SignonDbRequest;


/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.
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

 */

public class DERS71C0FamiliesByPartcDB extends CommonDB {
	//
	public DERS71C0FamiliesByPartcDB() {
		super();
		SPName = "DERS71C0-FamiliesByPartc";
	}
	//
	public void execute(IDbRequest dbRequest, IDbResponse dbResponse)
		throws SQLException {

		// Cast it to the db request class that I'm expecting and get the request parameters
		SignonDbRequest request = (SignonDbRequest) dbRequest;
		String participantId = request.getParticipantId();
		String prodyctType = request.getProductType();

		cstmt = con.prepareCall("{ call DER.DERS71C0 (?,?,?,?,?) }");
		// Register out params / First parameter is IO - SQLCA
		cstmt.registerOutParameter(1, java.sql.Types.CHAR);
		cstmt.setString(2, participantId);
		cstmt.setString(3, prodyctType); 	//'DPR' for pymnt rec, "DSV" for Derivatives      
		cstmt.registerOutParameter(4, java.sql.Types.CHAR); //used to recieve the originator
		cstmt.registerOutParameter(5, java.sql.Types.CHAR); //Error Log

		rs = cstmt.executeQuery();
		//
		sqlcaString = cstmt.getString(1);
		sqlca = new SQLCA(sqlcaString);

		String originatorId = cstmt.getString(4);
		String outString5 = cstmt.getString(5);		

		Vector partyList = new Vector();
		Vector orgList = new Vector();
		
		String sProdTestInd = "P";
		
		/** 
		 * added by shashi 10/04/2004
		 * added to get List of Orignators
		 */
		
		if (sqlca.getSqlCode() == 0)
		{
			while (rs != null && rs.next()) 
			{
				String origCode = rs.getString(1);
				String prodTestInd = rs.getString(2);
	
				if (origCode != null) {
					origCode = origCode.trim();
				}
				if (prodTestInd != null) {
					prodTestInd = prodTestInd.trim();
				}
	
				//Originator Code, test Indicator
				Originator oc = new Originator( origCode, prodTestInd);
				orgList.add(oc);
				
			} //&& rs != null && rs.next()
				
		} //end while(sqlca.getSqlCode() == 0)
//		/**
//		 * This else condition is for testing purpose only 
//		 * this procedure should atleast return one Originator Code. 
//		 * If None found then business delegate should handled no access condition.
//		 */
//		else
//		{
//			//For testing only			
//			Originator oc1 = new Originator(originatorId, sProdTestInd);
//			orgList.add(oc1);
//			Originator oc2 = new Originator("OTC2", "T");
//			orgList.add(oc2);			
//			Originator oc3 = new Originator("OTCD", "T");
//			orgList.add(oc3);			
//			Originator oc4 = new Originator("JPMD", "T");
//			orgList.add(oc4);			
//		}

		dbResponse.setContent(orgList);

	} //end execute

} //end class