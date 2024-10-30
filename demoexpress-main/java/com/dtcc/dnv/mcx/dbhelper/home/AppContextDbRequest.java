/*
 * Created on Oct 9, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.dbhelper.home;

import com.dtcc.dnv.otc.common.layers.AbstractDbRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AppContextDbRequest extends AbstractDbRequest {
	
	public AppContextDbRequest(String requestID, AuditInfo ai)
	{
		super(requestID, ai);
	}

}
