package com.dtcc.dnv.mcx.beans;

import java.io.Serializable;

/**
 * This bean used to store the Mca deatils for the Select Mca Screen
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
 * @date Sep 4, 2007
 * @version 1.0
 * 
 *  
 */
public class MCAList implements Serializable
{
    private String productCode;
    private String subProductCode;
    private String regionCode;
    private String publishYear;
    private String mcaTemplateID;
    private String mcaTemplateName;
    private String mcaTemplateGrp;
    private String mcaTemplateStatus;
    private String mcaTemplateDate;
    private String mcaTemplateType;

    /**
     * @return Returns the mcaTemplateGrp.
     */
    public String getMcaTemplateGrp()
    {
        return mcaTemplateGrp;
    }

    /**
     * @param mcaTemplateGrp
     *            The mcaTemplateGrp to set.
     */
    public void setMcaTemplateGrp(String mcaTemplateGrp)
    {
        this.mcaTemplateGrp = mcaTemplateGrp;
    }

    /**
     * @return Returns the mcaTemplateID.
     */
    public String getMcaTemplateID()
    {
        return mcaTemplateID;
    }

    /**
     * @param mcaTemplateID
     *            The mcaTemplateID to set.
     */
    public void setMcaTemplateID(String mcaTemplateID)
    {
        this.mcaTemplateID = mcaTemplateID;
    }

    /**
     * @return Returns the mcaTemplateStatus.
     */
    public String getMcaTemplateStatus()
    {
        return mcaTemplateStatus;
    }

    /**
     * @param mcaTemplateStatus
     *            The mcaTemplateStatus to set.
     */
    public void setMcaTemplateStatus(String mcaTemplateStatus)
    {
        this.mcaTemplateStatus = mcaTemplateStatus;
    }


    /**
     * @return Returns the productCode.
     */
    public String getProductCode()
    {
        return productCode;
    }

    /**
     * @return Returns the mcaTemplateDate.
     */
    public String getMcaTemplateDate()
    {
        return mcaTemplateDate;
    }
    /**
     * @param mcaTemplateDate The mcaTemplateDate to set.
     */
    public void setMcaTemplateDate(String mcaTemplateDate)
    {
        this.mcaTemplateDate = mcaTemplateDate;
    }
    /**
     * @param productCode
     *            The productCode to set.
     */
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

 

    /**
     * @return Returns the publishYear.
     */
    public String getPublishYear()
    {
        return publishYear;
    }

    /**
     * @param publishYear
     *            The publishYear to set.
     */
    public void setPublishYear(String publishYear)
    {
        this.publishYear = publishYear;
    }

    /**
     * @return Returns the regionCode.
     */
    public String getRegionCode()
    {
        return regionCode;
    }

    /**
     * @param regionCode
     *            The regionCode to set.
     */
    public void setRegionCode(String regionCode)
    {
        this.regionCode = regionCode;
    }

 

    /**
     * @return Returns the subProductCode.
     */
    public String getSubProductCode()
    {
        return subProductCode;
    }

    /**
     * @param subProductCode
     *            The subProductCode to set.
     */
    public void setSubProductCode(String subProductCode)
    {
        this.subProductCode = subProductCode;
    }


    /**
     * @return Returns the mcaTemplateName.
     */
    public String getMcaTemplateName()
    {
        return mcaTemplateName;
    }
    /**
     * @param mcaTemplateName The mcaTemplateName to set.
     */
    public void setMcaTemplateName(String mcaTemplateName)
    {
        this.mcaTemplateName = mcaTemplateName;
    }
    /**
     * @return Returns the mcaTemplateType.
     */
    public String getMcaTemplateType()
    {
        return mcaTemplateType;
    }
    /**
     * @param mcaTemplateType The mcaTemplateType to set.
     */
    public void setMcaTemplateType(String mcaTemplateType)
    {
        this.mcaTemplateType = mcaTemplateType;
    }
}
