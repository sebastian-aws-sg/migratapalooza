package com.dtcc.dnv.mcx.db.managedocs;

import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;
import com.dtcc.dnv.mcx.beans.UploadBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.mcx.util.MessageResources;
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
public class DPMXDADCDAO extends MCXCommonDB
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXDADCDAO.class.getName());

    /**
     * DPMXDADCDAO
     *  
     */
    public DPMXDADCDAO()
    {
        super();
        this.poolName = MessageResources.getMessage("common.db2.pool");
        SPName = "DPMXDADC";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {

        int tmpltId = 0;
        int fileSize = 0;
        String tId = "";
        String fileName = "";
        String tmpltName = "";
        String tmpltShortName = "";
        StringTokenizer tmpltTokenizer = null;
        StringTokenizer cpTokenizer = null;
        InputStream inputStream = null;
        String exeDate = "";
        String selectedCPId = "";
        byte fileBytes[] = null;
        String toTimeStamp = "-00.00.00.000000";
        String executedFormatedDate = "";
        boolean canUpdate = false;
        int updateCnt = 0;
        final String DELIMITER = "|";
        
        String productId = "";
        String subProductId = "";
        String regionId = "";

        log.info("In DPMXDADCDAO to upload files");
        
        String manageDocsTab = "";
        DealerClientListDbResponse dbResponse = (DealerClientListDbResponse) response;
        DealerClientListDbRequest dbRequest = (DealerClientListDbRequest) request;
        ManageDocsTransactionBean manageDocsTransactionBean = (ManageDocsTransactionBean) dbRequest.getTransaction();
        UploadBean uploadBean = dbRequest.getUploadBean();

        String mcaNames = uploadBean.getMcaNames();
        String cpViewable = uploadBean.getCpViewable();
        String executedDate = uploadBean.getExecutedDate();

        SimpleDateFormat inFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd");

        manageDocsTab = dbRequest.getManageDocs();

        /*cpTokenizer = new StringTokenizer(manageDocsTransactionBean.getSelectedDealerClient(), "-");
        // need to check for has more tokens
        selectedCPId = cpTokenizer.nextToken();*/
        selectedCPId = manageDocsTransactionBean.getSelectedDealerClient();

        StringBuffer sb = new StringBuffer();
        sb.append("{ call ");
        sb.append(QUAL);
        sb.append(SPName);
        sb.append("( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");

        if (MCXConstants.PREEXISTINGTAB.equals(manageDocsTab) && !MCXConstants.MANAGE_DOC_MCA_DEFAULT.equals(mcaNames))
        {

            if (mcaNames != null)
            {

                tmpltTokenizer = new StringTokenizer(mcaNames, DELIMITER);
                tId = tmpltTokenizer.nextToken();

                if (tId != null && tId.trim().length() > 0 && !MCXConstants.MANAGE_DOC_MCA_DEFAULT.equals(tId))
                {
                    try
                    {
                        tmpltId = Integer.parseInt(tId);

                    } catch (NumberFormatException e)
                    {
                        tmpltId = 0;
                    }
                    tmpltName = tmpltTokenizer.nextToken();
                    tmpltShortName = tmpltTokenizer.nextToken();
                    productId = tmpltTokenizer.nextToken();
                    subProductId = tmpltTokenizer.nextToken();
                    regionId = tmpltTokenizer.nextToken();

                    try
                    {
                        executedFormatedDate = outFormat.format(inFormat.parse(executedDate)) + "-00.00.00.000000";

                    } catch (ParseException pe)
                    {
                        tmpltId = 0;

                    }
                } else
                {
                    tmpltId = 0;

                }
            }
            if (manageDocsTab.equals(MCXConstants.PREEXISTINGTAB))
            {
                cpViewable = MCXConstants.MANAGE_DOC_UPLOAD_NO;
            }
        } else if (MCXConstants.OTHERTAB.equals(manageDocsTab))
        {

            tmpltId = 0;
            tmpltName = "";
            tmpltShortName = "";
            executedFormatedDate = "";
            if (cpViewable == null)
            {
                cpViewable = MCXConstants.MANAGE_DOC_UPLOAD_NO;
            }
        }

        if ((MCXConstants.PREEXISTINGTAB.equals(manageDocsTab) && tmpltId > 0)
                || ((MCXConstants.OTHERTAB.equals(manageDocsTab)) && (MCXConstants.MANAGE_DOC_UPLOAD_YES.equals(cpViewable) || MCXConstants.MANAGE_DOC_UPLOAD_NO.equals(cpViewable))))
        {

            if (uploadBean != null)
            {

                fileName = uploadBean.getFileName();
                inputStream = uploadBean.getInputStream();
                fileSize = uploadBean.getFileSize();

            }

            if ((MCXConstants.OTHERTAB.equals(manageDocsTab) && fileSize > 0) || MCXConstants.PREEXISTINGTAB.equals(manageDocsTab))
            {

                canUpdate = true;
            }
        }
        

        if (canUpdate)
        {
            cstmt = con.prepareCall(sb.toString());
            cstmt.registerOutParameter(1, java.sql.Types.CHAR);
            cstmt.registerOutParameter(2, java.sql.Types.CHAR);
            cstmt.registerOutParameter(3, java.sql.Types.CHAR);
            cstmt.setString(4, dbRequest.getCmpnyId());
            cstmt.setString(5, selectedCPId);
            cstmt.setInt(6, tmpltId);
            cstmt.setString(7, tmpltName);
            cstmt.setString(8, tmpltShortName);
            cstmt.setString(9, productId);//sub product regions
            cstmt.setString(10, subProductId);
            cstmt.setString(11, regionId);
            cstmt.setString(12, fileName);
            cstmt.setString(13, manageDocsTab);
            cstmt.setString(14, cpViewable); // CP Viewable Y/N is currently
            // hardcode - Always N for
            // Preexisting
            cstmt.setString(15, executedFormatedDate);
            cstmt.setString(16, dbRequest.getUserId());
            cstmt.setBinaryStream(17, inputStream, fileSize);

            updateCnt = cstmt.executeUpdate();

            sqlca = new SQLCA(cstmt.getString(1));
            sSpErrArea = cstmt.getString(2).trim();
            dbResponse.setSpReturnCode(cstmt.getString(3).trim());
            dbResponse.setSpResponseMessage(sSpErrArea);
        }

        log.info("Exisitng DPMXDADCDAO in >>>>");
        tId = "";
        tmpltId = 0;
        tmpltName = "";
        tmpltShortName = "";
        fileName = "";
        executedFormatedDate = "";
        inputStream = null;
        fileBytes = null;
        canUpdate = false;
        updateCnt = 0;

        log.info("Returning from DPMXDADCDAO");
    }
}
