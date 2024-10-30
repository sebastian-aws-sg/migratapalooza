package com.dtcc.dnv.otc.common.signon.modules;


/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jul 24, 2007
 * @version 1.0
 *
 * Interface class for all signon modules.
 * 
 * @version	1.1				August 30, 2007			sv
 * Updated process() interface method to throw SignonModuleException
 * 
 * @version	1.2				September 12, 2007		sv
 * Added postProcess() interface method.
 */
public interface ISignonModule {
	// Interface method to process the signon workflow step.
	public ISignonModuleResponse process(ISignonModuleRequest request) throws SignonModuleException;
	//	 Interface method to process the signon workflow step.
	public void postProcess(ISignonModuleRequest request) throws SignonModuleException;
}

