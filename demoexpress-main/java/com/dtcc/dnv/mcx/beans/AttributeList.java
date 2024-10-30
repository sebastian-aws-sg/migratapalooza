package com.dtcc.dnv.mcx.beans;

import java.io.Serializable;

/**
 * This bean is used for attribute details
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
 * @date Sep 5, 2007
 * @version 1.0
 * 
 *  
 */
public class AttributeList implements Serializable
{
    private String attributeName = null;
    private String attributeType = null;
    private String attributeDesc = null;

    /**
     * @return Returns the attributeDesc.
     */
    public String getAttributeDesc()
    {
        return attributeDesc;
    }

    /**
     * @param attributeDesc
     *            The attributeDesc to set.
     */
    public void setAttributeDesc(String attributeDesc)
    {
        this.attributeDesc = attributeDesc;
    }

    /**
     * @return Returns the attributeName.
     */
    public String getAttributeName()
    {
        return attributeName;
    }

    /**
     * @param attributeName
     *            The attributeName to set.
     */
    public void setAttributeName(String attributeName)
    {
        this.attributeName = attributeName;
    }

    /**
     * @return Returns the attributeType.
     */
    public String getAttributeType()
    {
        return attributeType;
    }

    /**
     * @param attributeType
     *            The attributeType to set.
     */
    public void setAttributeType(String attributeType)
    {
        this.attributeType = attributeType;
    }
}
