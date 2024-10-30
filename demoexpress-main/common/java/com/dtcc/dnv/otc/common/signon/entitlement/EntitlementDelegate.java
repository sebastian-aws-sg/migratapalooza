package com.dtcc.dnv.otc.common.signon.entitlement;

import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.AbstractBusinessDelegate;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;
import com.dtcc.sharedservices.ufocodes.common.CUOUserUFOCode;
import com.dtcc.sharedservices.ufocodes.common.UserUFOCodesGenerator;


/**
 * @author vxsubram
 * @date Aug 13, 2007
 * @version 1.0
 * 
 * This class manages the entitlement worflow for Non-Branch user 
 * registered with the signon module.
 * 
 * @version 1.1		September 16, 2007		sv
 * Checked in for Cognizant.  UFOBean instance is created using static factory method.
 * The OCode-EntitlementCode format split up logic is abstracted in UFOBean.
 * 
 * @version 1.2		October 25, 2007		sv
 * The UFOCode and UFO Type are retrieved from CSF.
 * 
 * @version 1.3		November 08, 2007		sv
 * The UFOCode and UFO Type retrieved from CSF are made to uppercase.
 */
public class EntitlementDelegate  extends AbstractBusinessDelegate {

	/* Process request to get Entitlement for non-branch user
	 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
	 */
	public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException {
		EntitlementSignonResponse response = new EntitlementSignonResponse();
		
		getUFOBeans(request,response);
		return response;
	}
	/*
	 * Gets the UFOCode and UFO Type from CSF implementation
	 * @param request instance of IServiceRequest
	 * @param response instance of EntitlementSignonResponse
	 * @throws UserException
	 */
	void getUFOBeans(IServiceRequest request,EntitlementSignonResponse response) throws UserException{
		
		EntitlementSignonRequest entitlementRequest = (EntitlementSignonRequest)request;
		
		// Call CSF API to get the Originator Code and Entitlement combination.
		UserUFOCodesGenerator uufo = new UserUFOCodesGenerator(entitlementRequest.getUser());
		try{
			CUOUserUFOCode[] array=uufo.getUserUFOCodes(entitlementRequest.getProductId());
			for (int i=0;i<array.length;i++){
				String ocode_eCode_value = array[i].getUfoCode()+"_"+array[i].getUfoType();
				UFOBean oUFO = UFOBean.createInstance(ocode_eCode_value.toUpperCase());
				oUFO.setDescription(ocode_eCode_value);
				response.addUFOBeans(oUFO);
			}
		}catch(Exception csfEx){
			throw UserException.createInfo(UserException.SEC_ERROR_CODE_ENTITLEMENT,csfEx.getMessage());
		}
	}	
}
