
package com.dtcc.dnv.mcx.delegate.enroll;

import java.util.ArrayList;
import java.util.HashMap;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceResponse;

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
 * @author Prabhu K
 * @date Sep 4, 2007
 * @version 1.0
 * 
 * 
 */
public class SelectMCAServiceResponse extends MCXAbstractServiceResponse
{
    ArrayList productList;
    ArrayList regionList;
    HashMap productRegionMap;
    

    /**
     * @return Returns the productList.
     */
    public ArrayList getProductList()
    {
        return productList;
    }
    /**
     * @param productList The productList to set.
     */
    public void setProductList(ArrayList productList)
    {
        this.productList = productList;
    }
    /**
     * @return Returns the productRegionMap.
     */
    public HashMap getProductRegionMap()
    {
        return productRegionMap;
    }
    /**
     * @param productRegionMap The productRegionMap to set.
     */
    public void setProductRegionMap(HashMap productRegionMap)
    {
        this.productRegionMap = productRegionMap;
    }
    /**
     * @return Returns the regionList.
     */
    public ArrayList getRegionList()
    {
        return regionList;
    }
    /**
     * @param regionList The regionList to set.
     */
    public void setRegionList(ArrayList regionList)
    {
        this.regionList = regionList;
    }

}
