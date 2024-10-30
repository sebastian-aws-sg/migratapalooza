package com.dtcc.dnv.mcx.dbhelper.admin;

import java.util.ArrayList;

import com.dtcc.dnv.mcx.beans.MCATypeList;
import com.dtcc.dnv.mcx.dbhelper.MCXAbstractDbResponse;

/**
 * This class is used as a DBResponse helper to retrive MCA Type List
 * RTM Reference : 1.21.2
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
 * @author Seeni Mohammad Azharudin S
 * @date Sep 17, 2007
 * @version 1.0
 * 
 *  
 */

public class MCATypeListDbResponse extends MCXAbstractDbResponse
{
	private ArrayList mcaTypeList = new ArrayList();
	
	public ArrayList getMcaTypeList() {
		return mcaTypeList;
	}
	
	public void addMcaTypeList(MCATypeList mcaTypeList) {
		this.mcaTypeList.add(mcaTypeList);
	}
}

