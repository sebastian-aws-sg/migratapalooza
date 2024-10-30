package com.dtcc.dnv.otc.common.signon.entitlement;

import java.util.ArrayList;

import com.dtcc.dnv.otc.common.layers.AbstractServiceResponse;
/**
 * @author vxsubram
 * @date Aug 13, 2007
 * @version 1.0
 * 
 *  This class contains the response of the entitlement process for non-branch user 
 */
public class EntitlementSignonResponse extends AbstractServiceResponse {
	
	private ArrayList ufoBeans = new ArrayList();
	
	public ArrayList getUFOBeans() {
		return ufoBeans;
	}
	
	void addUFOBeans(UFOBean ufoBean){
		ufoBeans.add(ufoBean);
	}
}
