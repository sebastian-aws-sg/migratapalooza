package com.dtcc.dnv.mcx.db.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import com.dtcc.dnv.mcx.beans.CompanyBean;
import com.dtcc.dnv.mcx.beans.CompanyContactBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.company.CompanyListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.company.CompanyListDbResponse;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

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

public class DPMXHCMDDAO extends MCXCommonDB {
	
    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        SPName = "DPMXHCMD"; 
        
        CompanyListDbRequest dbRequest = (CompanyListDbRequest) request;

        StringBuffer sb = new StringBuffer();
        sb.append("{ call ");
        sb.append(QUAL);
        sb.append(SPName);
        sb.append("( ?, ?, ?) }");

        cstmt = con.prepareCall(sb.toString());
        cstmt.registerOutParameter(1, java.sql.Types.CHAR);
        cstmt.registerOutParameter(2, java.sql.Types.CHAR);
        cstmt.registerOutParameter(3, java.sql.Types.CHAR);
        
        rs = cstmt.executeQuery();

        sqlca = new SQLCA(cstmt.getString(1));
        sSpErrArea = (String) cstmt.getObject(2);
        sprc = (String) cstmt.getObject(3);
        
        if (sqlca.getSqlCode() == 0 )
        {
        	processCompanyList(response, rs);
        }
    }
    
    /**
     * @param response
     * @param rs
     * @return CompanyListDbResponse
     */
    private CompanyListDbResponse processCompanyList(IDbResponse response, ResultSet rs) 
      throws SQLException {
	    List companyList = new Vector();
		CompanyListDbResponse dbResponse = (CompanyListDbResponse) response;
		CompanyBean company = null;
		while(rs.next()) {
		  company = new CompanyBean();
		  company.setCompanyId(rs.getString(1).trim());
		  company.setCompanyTypeInd(rs.getString(2).trim());
		  company.setCompanyGroupInd(rs.getString(3).trim());
		  company.setCompanyName(rs.getString(4).trim());
		  company.setCompanyEnvInd(rs.getString(5).trim());
		  
		  // Set primary contact information
		  String pContact = rs.getString(6).trim();
		  if(pContact != null && pContact.length() > 0) {
		  	CompanyContactBean contact = new CompanyContactBean();
		  	contact.setContactName(pContact);
		  	contact.setContactPhone(rs.getString(8).trim());
		  	contact.setContactEmail(rs.getString(10).trim());
		  	contact.setPrimary(true);
		  	company.addCompanyContact(contact);		  	
		  }
		  
		  // Set secondary contact information
		  String sContact = rs.getString(7).trim();
		  if(sContact != null && sContact.length() > 0) {
		  	CompanyContactBean contact = new CompanyContactBean();
		  	contact.setContactName(sContact);
		  	contact.setContactPhone(rs.getString(9).trim());
		  	contact.setContactEmail(rs.getString(11).trim());
		  	contact.setPrimary(false);
		  	company.addCompanyContact(contact);		  	
		  }
		  
		  companyList.add(company);
		}
		dbResponse.setContent(companyList);
		return dbResponse;
    }
}
