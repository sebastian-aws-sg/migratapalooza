package com.dtcc.dnv.mcx.db;

import com.dtcc.dnv.mcx.util.MessageResources;
import com.dtcc.dnv.otc.legacy.CommonDB;

/**
 * @author svijayak
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class MCXCommonDB extends CommonDB 
{
	private static final String MCX_QUAL;
	static{
		MCX_QUAL = MessageResources.getMessage("common.db.schema");
	}
	/**
	 * 
	 */
	public MCXCommonDB() 
	{
		super();
		this.QUAL = MCX_QUAL;
	}
}
