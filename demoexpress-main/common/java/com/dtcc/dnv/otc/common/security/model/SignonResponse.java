package com.dtcc.dnv.otc.common.security.model;

import java.util.Vector;

import com.dtcc.dnv.otc.common.layers.AbstractServiceResponse;
import com.dtcc.dnv.otc.legacy.CounterpartyIdName;
import com.dtcc.dnv.otc.legacy.Originator;


/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 * Form bean for a Struts application.
 *
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * SignonResponse is the IServiceResponse that is returned from SignonDelegate
 * 
 * Date: 10/04/2004
 * Changes made by Shashi to include ProductType List based 
 * on Functional requirement for implementing Equities
 */
public final class SignonResponse extends AbstractServiceResponse {

	private CounterpartyIdName[] partyList;

	//aded to to transfer Products Type List
	private Vector prodtypList;
	//aded to to transfer Applicable Modules Type List
	private Vector moduleList;

	//aded to to transfer Products Type List
	private Originator[] orgList;
	
	public CounterpartyIdName[] getList() { return partyList;	}

	public void setList(CounterpartyIdName[] list) { partyList = list; }


	//aded to to transfer Originators List	
	public Originator[] getOriginatorList() { return orgList;	}
	
	public void setOriginatorList(Originator[] aOrgList) { orgList = aOrgList; }

	
	//aded to to transfer Products Type List	
	public Vector getProductTypeList() { return prodtypList;	}

	public void setProductTypeList(Vector vProdtypList) { prodtypList = vProdtypList; }
	
	//aded to to transfer Appicable Modules Type List	
	public Vector getModuleList() { return moduleList;	}

	public void setModuleList(Vector vModuleList) { moduleList = vModuleList; }
	
	
}
