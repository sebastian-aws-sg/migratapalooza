
package com.dtcc.dnv.mcx.db.alert;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.dtcc.dnv.mcx.beans.AlertInfo;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.alert.AlertDbRequest;
import com.dtcc.dnv.mcx.dbhelper.alert.AlertDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

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
 * @author Peng Zhou
 * @date Oct 01, 2007
 * @version 1.0
 * 
 * This class is used as DAO to retrieve alert detail.
 *  
 */
public class DPMXAADLDAO extends MCXCommonDB {
    /* (non-Javadoc)
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        SPName = "DPMXAADL";
        
        AlertDbResponse dbResponse = (AlertDbResponse) response;
        AlertDbRequest dbRequest = (AlertDbRequest) request;       
        StringBuffer sb = new StringBuffer();
        sb.append("{ call ");
        sb.append(QUAL);
        sb.append(SPName);
        sb.append(" ( ?, ?, ?, ?) }");

        cstmt = con.prepareCall(sb.toString());
        cstmt.registerOutParameter(1, java.sql.Types.CHAR);
        cstmt.registerOutParameter(2, java.sql.Types.CHAR);
        cstmt.registerOutParameter(3, java.sql.Types.CHAR);
        AlertInfo thealertinfo = (AlertInfo)dbRequest.getTransaction();
        cstmt.setString(4, thealertinfo.getAlertid());

        rs = cstmt.executeQuery();

        sqlca = new SQLCA(cstmt.getString(1));
        sSpErrArea = (String) cstmt.getObject(2);
        String thisreturncode = (String) cstmt.getObject(3);
        dbResponse.setSpReturnCode(thisreturncode);
        dbResponse.setSpResponseMessage(sSpErrArea);  
        
        if (sqlca.getSqlCode() == 0 && dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
		{
			dbResponse.setAlertStatus("success");
			getAlertinfo(dbResponse);
		}
		
    }
    
    /**
     * @param dbResponse get alert detail from resultset
     * @throws SQLException
     */
    private void getAlertinfo(AlertDbResponse dbResponse) throws SQLException
    {
        while (rs.next())
        {
            AlertInfo alertinfo = new AlertInfo();
            alertinfo.setSubject(rs.getString(1));
            alertinfo.setMessage(rs.getString(2).trim());
            alertinfo.setUserid(rs.getString(3).trim());
            alertinfo.setUserName(rs.getString(4).trim());
            String s = rs.getString(5).trim();
            s = formattimestamp(s);
            alertinfo.setUpdatetimestamp(s);
            
            dbResponse.setTransaction(alertinfo);
        }
      	
    }

    private String formattimestamp(String s) throws SQLException
    {
		try
		{
			s = s.trim();
			String sdpart="";
			String timepart="";
			int ispace = s.indexOf(" ");
			if(ispace != -1)
			{
				sdpart=s.substring(0,ispace);
				timepart=s.substring(ispace+1,s.length());
			}
			
	   		SimpleDateFormat sdf = (SimpleDateFormat)SimpleDateFormat.getDateTimeInstance();
			sdf.applyPattern("yyyy-MM-dd");
			java.util.Date d = sdf.parse(sdpart);

			sdf.applyPattern("MM/dd/yyyy zzz");	
			String dpart = sdf.format(d);
			int izone = dpart.indexOf(" ");
			sdpart = dpart.substring(0,izone);
			String zonepart = dpart.substring(izone+1,dpart.length());
			int idot = timepart.indexOf(".");
			if(idot!=-1)
			{
				timepart = timepart.substring(0,idot);
			}
			StringBuffer sbtmp =  new StringBuffer();
			sbtmp.append(sdpart);
			sbtmp.append(" ");
			sbtmp.append(timepart);
			sbtmp.append(" ");
			sbtmp.append(zonepart);
			return sbtmp.toString();
		}
		catch(Exception ee)
		{
			//ee.printStackTrace();
			throw new SQLException("Error Date Parse " + s);
		}    	
    }
    
}
