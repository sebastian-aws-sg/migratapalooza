package com.dtcc.dnv.otc.common.layers;

/**
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jun 7, 2004
 * @version 1.0
 *
 * Serves as a marker class for all Transaction beans that represent different 
 * transacitons or deals (i.e Trade, Termination, etc).
 * 
 * Changes :
 *     7/18/2004 DLV
 *     Added method to make sure that we are formatting incoming numeric values based
 * on DerivSERV specification consistently across a project.
 * 
 * Rev  1.2     July 28, 2004       sav
 * Added clone interface method to enforce and allow the capability for all 
 * ITransactionBeans to perform cloning.
 * 
 * Rev  1.3     August 3, 2004      sav
 * Modified getClone() to return ITransactionBean instead of Object.
 * Updated javadoc.
 */
public interface ITransactionBean extends Cloneable{

    /**
     * 			  7/18/2004 added method to enforce implmentation of 
     * consistent currency formatting rules across DerivSERV project.
     */
	public void formatCurrencies();
    
    
	/**
	 * Interface method to create a clone of this object.
     * @return ITransactionBean cloned ITransactionBean object
	 * @throws CloneNotSupportedException Cloning exception
	 */
    public ITransactionBean getClone() throws CloneNotSupportedException;
}
