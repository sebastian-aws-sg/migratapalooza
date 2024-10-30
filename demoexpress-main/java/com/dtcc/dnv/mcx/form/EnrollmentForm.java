package com.dtcc.dnv.mcx.form;

import java.util.ArrayList;
import java.util.HashMap;

import com.dtcc.dnv.mcx.beans.Enrollment;

/**
 * @author pkarmega
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EnrollmentForm extends MCXActionForm
{
	private ArrayList fedDealerList;
	private ArrayList otherDealerList;
	private int fedDealerCount;
	private int otherDealerCount;
	private HashMap DealerMap;
	
    public EnrollmentForm()
    {
        Enrollment bean = new Enrollment();
        super.setTransaction(bean);
    }

	/**
	 * @return Returns the fedDealerList.
	 */
	public ArrayList getFedDealerList() {
		return fedDealerList;
	}
	/**
	 * @param fedDealerList The fedDealerList to set.
	 */
	public void setFedDealerList(ArrayList fedDealerList) {
		this.fedDealerList = fedDealerList;
	}
	/**
	 * @return Returns the otherdealerList.
	 */
	public ArrayList getOtherDealerList() {
		return otherDealerList;
	}
	/**
	 * @param otherdealerList The otherdealerList to set.
	 */
	public void setOtherDealerList(ArrayList otherDealerList) {
		this.otherDealerList = otherDealerList;
	}
	/**
	 * @return Returns the fedDealerCount.
	 */
	public int getFedDealerCount() {
		return fedDealerCount;
	}
	/**
	 * @param fedDealerCount The fedDealerCount to set.
	 */
	public void setFedDealerCount(int fedDealerCount) {
		this.fedDealerCount = fedDealerCount;
	}
	/**
	 * @return Returns the otherDealerCount.
	 */
	public int getOtherDealerCount() {
		return otherDealerCount;
	}
	/**
	 * @param otherDealerCount The otherDealerCount to set.
	 */
	public void setOtherDealerCount(int otherDealerCount) {
		this.otherDealerCount = otherDealerCount;
	}
	
	/**
	 * @return Returns the dealerMap.
	 */
	public HashMap getDealerMap() {
		return DealerMap;
	}
	/**
	 * @param dealerMap The dealerMap to set.
	 */
	public void setDealerMap(HashMap dealerMap) {
		DealerMap = dealerMap;
	}

}
