/*
 * Created on Aug 28, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.beans;

import java.io.Serializable;

/**
 * @author pkarmega
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealerList extends  ABaseTran
{
	private String dealerName = null;
	private String dealerCode = null;
	private String fed18Indicator = null;
	private String enrollStatus = null;
	private String mcaNames = "None";
	

	/**
	 * @return Returns the enrollStatus.
	 */
	public String getEnrollStatus() {
		return enrollStatus;
	}
	/**
	 * @param enrollStatus The enrollStatus to set.
	 */
	public void setEnrollStatus(String enrollStatus) {
		this.enrollStatus = enrollStatus;
	}
	/**
	 * @return Returns the fed18Indicator.
	 */
	public String getFed18Indicator() {
		return fed18Indicator;
	}
	/**
	 * @param fed18Indicator The fed18Indicator to set.
	 */
	public void setFed18Indicator(String fed18Indicator) {
		this.fed18Indicator = fed18Indicator;
	}
	/**
	 * @return Returns the dealerCode.
	 */
	public String getDealerCode() {
		return dealerCode;
	}
	/**
	 * @param dealerCode The dealerCode to set.
	 */
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	/**
	 * @return Returns the dealerName.
	 */
	public String getDealerName() {
		return dealerName;
	}
	/**
	 * @param dealerName The dealerName to set.
	 */
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
    /**
     * @return Returns the mcaNames.
     */
    public String getMcaNames()
    {
        return mcaNames;
    }
    /**
     * @param mcaNames The mcaNames to set.
     */
    public void setMcaNames(String mcaNames)
    {
        this.mcaNames = mcaNames;
    }
}
