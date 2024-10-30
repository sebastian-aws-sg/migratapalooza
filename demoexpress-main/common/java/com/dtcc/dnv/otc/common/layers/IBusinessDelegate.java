package com.dtcc.dnv.otc.common.layers;

import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.UserException;

/**
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * IBusinessDelegate is the interface that must be implemented by the business delegate object.
 * The processRequest() method defines the interface contract between the presentation
 * tier and the business tier.
 * 
 * @see com.dtcc.dnv.otc.common.layers.IServiceRequest
 * @see com.dtcc.dnv.otc.common.layers.IServiceResponse
 * @see com.dtcc.dnv.otc.common.layers.AbstractBusinessDelegate
 * 
 * Rev  1.1     June 17, 2004        sav
 * Cleaned up imports and added throwing a UserException from the processRequest().
 * Updated comments.
 */

public interface IBusinessDelegate {

	/**
	 * @param IServiceRequest
	 * @return IServiceResponse
	 * @throws BusinessException
     * @throws UserException
	 * 
	 * This is the method that must be implemented by the class which acts as the
	 * delegate between the presentation tier and the business tier.
	 * All the information that the presentation has that the delegate needs
	 * must be passed through the IServiceRequest.  The delegate may not
	 * access presentation tier objects directly.
	 * 
	 * In most cases, the delegate should be stateless.
	 */
	public IServiceResponse processRequest ( IServiceRequest request ) throws BusinessException, UserException;
}
