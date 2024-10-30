package com.dtcc.dnv.otc.common.signon.modules;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Sep 11, 2007
 * @version 1.0
 *
 * This class serves as the base abstract module for all signon modules.  The process()
 * is mandatory and the postProcess() is optional hence why it has an empty implemenation.
 * All Mandatory interface methods must be abstract here, and all optional interfact 
 * methods must have an empty implementation. 
 */
public abstract class AbstractSignonModule implements ISignonModule {

	/**
	 * @see com.dtcc.dnv.otc.common.signon.modules.ISignonModule#process(com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest)
	 */
	public abstract ISignonModuleResponse process(ISignonModuleRequest request) throws SignonModuleException;

	/**
	 * @see com.dtcc.dnv.otc.common.signon.modules.ISignonModule#postProcess(com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest)
	 */
	public void postProcess(ISignonModuleRequest request) throws SignonModuleException {
	}

}
