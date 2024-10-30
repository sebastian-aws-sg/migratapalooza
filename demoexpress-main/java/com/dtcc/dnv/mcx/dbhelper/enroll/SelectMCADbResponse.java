/*
 * Created on Aug 27, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.dbhelper.enroll;

import java.util.ArrayList;
import java.util.HashMap;

import com.dtcc.dnv.mcx.dbhelper.MCXAbstractDbResponse;

/**
 * @author pkarmega
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SelectMCADbResponse extends MCXAbstractDbResponse
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

