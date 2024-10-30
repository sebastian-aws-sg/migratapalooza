package com.dtcc.dnv.mcx.tags;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.dtcc.dnv.mcx.beans.AttributeList;
import com.dtcc.dnv.mcx.beans.MCAList;

/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 * 
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048, U.S.A All Rights Reserved.
 * 
 * RTM Reference : 3.3.3.11 Actor chooses to a non executed common form, non
 * executed interdealer form and an executed dealer form from selection grid
 * 
 * RTM Reference : 3.3.3.20 MCAs already marked as ‘Pending’ or ‘Executed’ on
 * this screen should not be selectable for re-initiation
 * 
 * RTM Reference : 3.3.3.21 System should allow selection of only those cells
 * that are white and state “Published”
 * 
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC.
 * 
 * @author Prabhu K
 * @date Sep 5, 2007
 * @version 1.0
 * 
 *  
 */

public class ProductMCAGrid extends BodyTagSupport
{
    private ArrayList productList = new ArrayList();
    private ArrayList regionList = new ArrayList();
    private HashMap productRegionMap = new HashMap();

    /**
     * Overide parent method to perform start tag functionality for this tag.
     * This method creates a select tag if the user is in a family (more than
     * one member). Otherwise nothing is done in this method.
     * 
     * @throws JspException
     *             thrown if there is a JspException or there are issues
     *             attaining user information.
     * 
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException
    {
        try
        {
            final String PUBLISH_STAT = "PUBLISHED";
            final String EXEC_STAT = "EXECUTED";
            final String PEND_STAT = "PENDING";
            final String EXECUTION_DATE = " Execution Date: ";
            final String REGION_AEJ = "Asia Ex-Japan (AEJ)";
            boolean isAsiaExJapan = false;

            StringBuffer buffer = new StringBuffer();
            buffer.append("<table width='100%' class='bordertable' cellpadding='0' cellspacing='0'>");
            buffer.append("<tr> <td width='18%' height='20px' class='prodHeading'>Product</td>");
            int prodLengh = productList.size();

            int columnLength = regionList.size();

            // Region information in header
            for (int columnIndex = 0; columnIndex < columnLength; columnIndex++)
            {
                String regionDesc = ((AttributeList) regionList.get(columnIndex)).getAttributeDesc();
                if (REGION_AEJ.equals(regionDesc))
                {
                    regionDesc = regionDesc + "<font color='#CC0000'>*</font>";
                    isAsiaExJapan = true;
                }

                buffer.append("<td width='18%' height='20px' class='prodHeading'>" + regionDesc + "</td>");
            }
            buffer.append("</tr>");

            // for each product
            for (int prodIndex = 0; prodIndex < prodLengh; prodIndex++)
            {
                String productDesc = ((AttributeList) productList.get(prodIndex)).getAttributeDesc();
                String productName = ((AttributeList) productList.get(prodIndex)).getAttributeName();

                //Product information in first column
                buffer.append("<tr>");
                
                buffer.append("<td class='product_bg_center border4a'>" + productDesc + "</td>");

                for (int columnIndex = 0; columnIndex < columnLength; columnIndex++)
                {
                    String regionDesc = ((AttributeList) regionList.get(columnIndex)).getAttributeDesc();
                    String regionName = ((AttributeList) regionList.get(columnIndex)).getAttributeName();

                    String Mapping = productName + regionName;

                    int mappingLength = 0;
                    ArrayList mappingList = null;
                    if (productRegionMap.get(Mapping) != null)
                    {
                        mappingList = (ArrayList) productRegionMap.get(Mapping);
                        mappingLength = mappingList.size();
                    }                    
                    if (mappingLength == 0)
                    {
                        if(columnIndex == columnLength-1){
                            buffer.append("<td class='normal border3aend' align='center'>No Form</td>");
                        }else{
                        // If no record is availble
                        buffer.append("<td class='normal border3a' align='center'>No Form</td>");
                        }
                    } else
                    {
                        // If atleast one record is availble
                        buffer.append("<td height='100%'><table  cellpadding='0'  cellspacing='0'  width='100%' height='100%'>");
                        for (int mappingIndex = 0; mappingIndex < mappingLength; mappingIndex++)
                        {
                            if (mappingList != null)
                            {
                                MCAList mcaList = (MCAList) mappingList.get(mappingIndex);

                                String styleClass = "";
                                String onClickEvent = "";
                                String execContent = "";
                                String content = "";
                                // For background color
                                if (mcaList.getMcaTemplateStatus().equals(PUBLISH_STAT))
                                {                                                                                 
                                    if (columnIndex == columnLength - 1)
                                    {
                                        styleClass = "innerTableCellWhite1";
                                        onClickEvent = "style='cursor:hand' onclick=\"selectGrid(this)\"";
                                    } else
                                    {
                                        styleClass = "innerTableCellWhite";
                                        onClickEvent = "style='cursor:hand' onclick=\"selectGridMore(this)\"";
                                    }
                                                                        
                                    content = "<input style='display:none' type='checkbox'  name='mcaList' id='" + mcaList.getMcaTemplateID() + "' value='" + mcaList.getMcaTemplateName() + "' >";

                                } else if (mcaList.getMcaTemplateStatus().equals(EXEC_STAT))
                                {
                                    if (columnIndex == columnLength - 1)
                                    {
                                        styleClass = "innerTableCellGreen1";
                                    } else
                                    {
                                        styleClass = "innerTableCellGreen";
                                    }
                                    execContent = "<br/>" + EXECUTION_DATE + mcaList.getMcaTemplateDate();

                                } else if (mcaList.getMcaTemplateStatus().equals(PEND_STAT))
                                {
                                    if (columnIndex == columnLength - 1)
                                    {
                                        styleClass = "innerTableCellYellow1";
                                    } else
                                    {
                                        styleClass = "innerTableCellYellow";
                                    }
                                    if(mcaList.getMcaTemplateType().equals("R"))
		                            {
		                                execContent = "<br/>" +  EXECUTION_DATE + mcaList.getMcaTemplateDate();
		                            }
                                }

                                content = content + mcaList.getMcaTemplateName();

                                buffer.append("<tr height='100%' " + onClickEvent + "><td height='100%' class='" + styleClass + "' formType='" + mcaList.getMcaTemplateGrp() + "'>" + content
                                        + execContent + "</td></tr>");
                            }
                        }
                        buffer.append("</table></td>");
                    }
                }
                buffer.append("</tr>");
            }

            buffer.append("</table>");
            if (isAsiaExJapan)
            {
                buffer
                        .append("<div class='counter_pop_txt'><font color='#CC0000'>*</font> Australia, Hong Kong, India, Indonesia, Korea, Malaysia, New Zealand, Singapore, Taiwan and Thailand.</div>");
            }
            pageContext.getOut().write(buffer.toString());

        } catch (Exception e)
        {
            throw new JspException(e.getMessage());
        }
        return EVAL_PAGE;
    }

    /**
     * @param productList
     *            The productList to set.
     */
    public void setProductList(ArrayList productList)
    {
        this.productList = productList;
    }

    /**
     * @param productRegionMap
     *            The productRegionMap to set.
     */
    public void setProductRegionMap(HashMap productRegionMap)
    {
        this.productRegionMap = productRegionMap;
    }

    /**
     * @param regionList
     *            The regionList to set.
     */
    public void setRegionList(ArrayList regionList)
    {
        this.regionList = regionList;
    }


}
