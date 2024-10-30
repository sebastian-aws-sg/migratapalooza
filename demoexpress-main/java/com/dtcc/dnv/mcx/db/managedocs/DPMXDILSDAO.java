package com.dtcc.dnv.mcx.db.managedocs;

import java.sql.SQLException;

import com.dtcc.dnv.mcx.beans.TemplateDocsBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * 
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
 * @author Elango TR
 * @date Sep 6, 2007
 * @version 1.0
 *  
 */
public class DPMXDILSDAO extends MCXCommonDB
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXDILSDAO.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        log.info("In DPMXDILSDAO to fetch list of ISDA templates");
        String tmpltName = "";
        SPName = "DPMXDILS";
        final String DELIMITER = "|"; 

        DealerClientListDbResponse dbResponse = (DealerClientListDbResponse) response;
        DealerClientListDbRequest dbRequest = (DealerClientListDbRequest) request;

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
        sSpErrArea = cstmt.getString(2).trim();
        dbResponse.setSpReturnCode(cstmt.getString(3).trim());
        dbResponse.setSpResponseMessage(sSpErrArea); 
        
        if (sqlca.getSqlCode() == 0 && dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
        {
            while (rs != null && rs.next())
            {
                TemplateDocsBean isdaTemplateList = new TemplateDocsBean();
                isdaTemplateList.setTmpltId(rs.getInt(1));
                isdaTemplateList.setTmpltNm(rs.getString(2));
                isdaTemplateList.setTmpltShrtNm(rs.getString(3));
                isdaTemplateList.setRgnCd(rs.getString(4));
                isdaTemplateList.setRgnNm(rs.getString(5));
                isdaTemplateList.setDerPrdCd(rs.getString(6));
                isdaTemplateList.setDerPrdNm(rs.getString(7));
                isdaTemplateList.setDerSubPrdCd(rs.getString(8));
                isdaTemplateList.setDerSubPrdNm(rs.getString(9));
                isdaTemplateList.setTmpltIdDetails(isdaTemplateList.getTmpltId()+DELIMITER+isdaTemplateList.getTmpltNm().trim()+DELIMITER+isdaTemplateList.getTmpltShrtNm().trim()+DELIMITER+isdaTemplateList.getDerPrdCd()+DELIMITER+isdaTemplateList.getDerSubPrdCd()+DELIMITER+isdaTemplateList.getRgnCd());
                
                dbResponse.addIsdaTemplateList(isdaTemplateList);
            }
        }
        
        log.info("Returning from DPMXDILSDAO");
    }
}
