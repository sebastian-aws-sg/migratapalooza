package com.dtcc.dnv.mcx.db.enroll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.dtcc.dnv.mcx.beans.AttributeList;
import com.dtcc.dnv.mcx.beans.MCAList;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.enroll.SelectMCADbRequest;
import com.dtcc.dnv.mcx.dbhelper.enroll.SelectMCADbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * This class is used as a DAO to get the MCA deatails
 * 
 * RTM Reference : 3.3.3.1 To create an enrolment request, actor is directed to
 * the enrolment list of dealers, selection of MCAs throuhg enrolment grid and
 * the enrolment confirmation screen
 * 
 * RTM Reference : 3.3.3.2 Actor chooses to select a another MCA for enrolment
 * after the previous MCA is approved by dealer
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
 * @author Prabhu K
 * @date Sep 3, 2007
 * @version 1.0
 * 
 *  
 */
public class DPMXESELDAO extends MCXCommonDB
{

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        SPName = "DPMXESEL";
        SelectMCADbResponse dbResponse = (SelectMCADbResponse) response;
        SelectMCADbRequest dbRequest = (SelectMCADbRequest) request;

        StringBuffer sb = new StringBuffer();
        sb.append("{ call ");
        sb.append(QUAL);
        sb.append(SPName);
        sb.append("( ?, ?, ?, ?, ? ) }");

        cstmt = con.prepareCall(sb.toString());
        cstmt.registerOutParameter(1, java.sql.Types.CHAR);
        cstmt.registerOutParameter(2, java.sql.Types.CHAR);
        cstmt.registerOutParameter(3, java.sql.Types.CHAR);
        cstmt.setString(4, dbRequest.getCompanyID());
        cstmt.setString(5, dbRequest.getDealerCode());

        rs = cstmt.executeQuery();

        sqlca = new SQLCA(cstmt.getString(1));
        sSpErrArea = cstmt.getString(2).trim();
        dbResponse.setSpReturnCode(cstmt.getString(3).trim());
        dbResponse.setSpResponseMessage(sSpErrArea);  
        if (sqlca.getSqlCode() == 0 && dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
        {
            getMCADetails(dbResponse);
        }
        
    }

    /**
     * This method is used to get the Dealer list from the result set
     * 
     * @param dbResponse
     * @return
     */
    private void getMCADetails(SelectMCADbResponse dbResponse) throws SQLException
    {
        String TYPE_PROD = "P";
        String TYPE_REGION = "R";

        ArrayList productList = new ArrayList();
        ArrayList regionList = new ArrayList();
        HashMap productRegionMap = new HashMap();

        // First resultset will contain the attribute list of products & regions
        while (rs != null && rs.next())
        {
            String attributeName = rs.getString(1).trim();
            String attributeDesc = rs.getString(2).trim();
            String attributeType = rs.getString(3).trim();

            AttributeList attributeList = new AttributeList();
            attributeList.setAttributeName(attributeName);
            attributeList.setAttributeDesc(attributeDesc);
            attributeList.setAttributeType(attributeType);

            if (attributeType.equalsIgnoreCase(TYPE_PROD))
            {
                productList.add(attributeList);
            } else if (attributeType.equalsIgnoreCase(TYPE_REGION))
            {
                regionList.add(attributeList);
            }

        }

        // Second resultset will contain the products & regions & MCAsn mapping
        if (cstmt.getMoreResults())
        {
            String prevRow = null;
            ArrayList productRegionList = null;
            String currentRow = null;
            String currentGroup = null;
            String prevGroup = null;
            boolean isFirstTime = true;

            rs = cstmt.getResultSet();
            while (rs != null && rs.next())
            {
                MCAList mcaList = new MCAList();
                mcaList.setProductCode(rs.getString(1).trim());
                mcaList.setRegionCode(rs.getString(2).trim());
                mcaList.setMcaTemplateID(rs.getString(3).trim());
                mcaList.setMcaTemplateName(rs.getString(4).trim());
                mcaList.setMcaTemplateGrp(rs.getString(5).trim());
                mcaList.setMcaTemplateStatus(rs.getString(6).trim());
                mcaList.setMcaTemplateDate(rs.getString(7).trim());
                mcaList.setMcaTemplateType(rs.getString(8).trim());

                currentGroup = mcaList.getProductCode() + mcaList.getRegionCode() + mcaList.getMcaTemplateGrp();
                
                // This check is for taking the latest template from the Group
                // If the latest template is taken then we have to omit other records 
                if(currentGroup.equals(prevGroup))
                {
                    MCAList mcaPrevList = (MCAList)productRegionList.get(productRegionList.size()-1);
                    if(mcaPrevList.getMcaTemplateType().equals("R") && isFirstTime)
                    {
                        mcaPrevList.setMcaTemplateDate(mcaList.getMcaTemplateDate());
                        productRegionList.set(productRegionList.size()-1,mcaPrevList);
                    }
                    isFirstTime = false;
                    continue;
                }
                else
                {
                    isFirstTime = true;
                }
                
                currentRow = mcaList.getProductCode() + mcaList.getRegionCode();

                // Group the similar product,sub product & Region level MCAs and
                // put it in map
                if (currentRow.equals(prevRow) && productRegionList != null)
                {
                    productRegionList.add(mcaList);
                } else
                {
                    if (productRegionList != null)
                    {
                        productRegionMap.put(prevRow, productRegionList);
                    }
                    productRegionList = new ArrayList();
                    productRegionList.add(mcaList);
                }
                prevRow = currentRow;
                prevGroup = currentGroup;
            }
            productRegionMap.put(currentRow, productRegionList);
        }

        dbResponse.setProductList(productList);
        dbResponse.setRegionList(regionList);
        dbResponse.setProductRegionMap(productRegionMap);

    }

}
