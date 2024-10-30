/*
 * Created on Sep 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.mca;



import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbResponse;
import com.dtcc.dnv.mcx.formatters.FormatterDelegate;
import com.dtcc.dnv.mcx.formatters.IFormatter;
import com.dtcc.dnv.mcx.proxy.mca.ModifyTermProxy;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.DbRequestBuilder;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveAmendmentDelegate extends MCXMCABusinessDelegate {

	private final static MessageLogger log = MessageLogger.getMessageLogger(SaveAmendmentDelegate.class.getName());

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
	 */
	public IServiceResponse processRequest(IServiceRequest request)
			throws BusinessException, UserException 
	{
		ModifyTermServiceRequest modifyTermServiceRequest = (ModifyTermServiceRequest) request;

		ModifyTermServiceResponse modifyTermServiceResponse = new ModifyTermServiceResponse();
		ModifyTermDbResponse dbResponse =  null;
		boolean delInsDoc = false;
		boolean eligForLock = false;
		ModifyTermProxy postAmendProxy = new ModifyTermProxy();
		try
		{
			ModifyTermDbRequest dbRequest = new ModifyTermDbRequest("", modifyTermServiceRequest.getAuditInfo());
			DbRequestBuilder.copyObject(modifyTermServiceRequest, dbRequest);			
			
			dbRequest.setTmpltLocked(modifyTermServiceRequest.isTmpltLocked()); // Boolean not copied
			dbRequest.setFunctionalIndicator(MCXConstants.FUNCTION_INDICATOR_MCA);
			
			TermBean termBean = (TermBean) dbRequest.getTransaction();
			FormatterDelegate.format(termBean, null, IFormatter.FORMAT_TYPE_INPUT);
			if ((termBean.getTmpltTyp().equalsIgnoreCase(MCXConstants.WORKING_TEMPLATE_TYPE)
					|| termBean.getTmpltTyp().equalsIgnoreCase(MCXConstants.REEXECUTED_TEMPLATE_TYPE))
					&& !dbRequest.isTmpltLocked())
			{
				eligForLock = isEligibleForLock(modifyTermServiceRequest,modifyTermServiceResponse,termBean.getTmpltId());
				if(!eligForLock)
				{
					return modifyTermServiceResponse;
				}
			}
			
			//If the Template is Generic Template, lock the template prior to Save Amendment
			if ((termBean.getTmpltTyp().equalsIgnoreCase(MCXConstants.GENERIC_TEMPLATE_TYPE) || eligForLock )
					&& !dbRequest.isTmpltLocked())
			{
				lockTemplate(modifyTermServiceRequest,modifyTermServiceResponse,termBean.getTmpltId());
				dbRequest.setTmpltLocked(true);
			}
			
			//If the User inserted an Image
			if(termBean.getImageObj() != null && termBean.getImageObj().length != 0)
			{
				//Set the Document Indicator
				dbRequest.setIndicator(MCXConstants.DOCUMENT_INDICATOR);
				//If the doc Id is present (in case of image update) set the docId
				if(termBean.getDocId() != 0)
					dbRequest.setValueID(termBean.getDocId());
				//Insert the Image Document
				if(!modifyTermServiceResponse.hasError())	
				{
				dbResponse = (ModifyTermDbResponse) postAmendProxy.processRequest(dbRequest);
					processSPReturnCode(modifyTermServiceResponse, dbResponse);
				}
				else
				{
					return modifyTermServiceResponse;
				}
				
				if(!modifyTermServiceResponse.hasError())	
				{
					//Get the Doc id from the previous insert and update the text
				        TermBean tempBean = (TermBean) dbResponse.getTransaction();
				        termBean.setDocId(tempBean.getDocId());
					//Modify the Image Source with the current DocId
				        FormatterDelegate.format(termBean, null, IFormatter.FORMAT_TYPE_OUTPUT);
					
					//Prepare for the Text Insert, set Indicator as 'Text'
				        termBean.setTmpltId(tempBean.getTmpltId());
				        termBean.setTmpltTyp(tempBean.getTmpltTyp());
				        dbRequest.setIndicator(MCXConstants.TEXT_INDICATOR);
					//If the text Id is present, send the textId
				        if(termBean.getTermTextId() != 0)
					    dbRequest.setValueID(termBean.getTermTextId());
					
				dbResponse = (ModifyTermDbResponse) postAmendProxy.processRequest(dbRequest);
					processSPReturnCode(modifyTermServiceResponse, dbResponse);
					//If the Image Insert is Success and Text Insert failed
					if(!modifyTermServiceResponse.hasError())
					{
						delInsDoc = true;
					}
				}
				else
				{
					return modifyTermServiceResponse;
				}
			}
			else
			{
				//If Image Deleted on Update
				if(termBean.getDocId() != 0 && ! termBean.isImagePresent())
				{
					deleteDoc(modifyTermServiceRequest, modifyTermServiceResponse);
				}
				dbRequest.setIndicator(MCXConstants.TEXT_INDICATOR);
				
				//If there is no Current Image load, but the amendment already had Image
				if(termBean.isImagePresent())
					FormatterDelegate.format(termBean, null, IFormatter.FORMAT_TYPE_OUTPUT);
				
				//If the text Id is present, send the textId
				if(termBean.getTermTextId() != 0)
					dbRequest.setValueID(termBean.getTermTextId());
				if(!modifyTermServiceResponse.hasError())
				{				
				dbResponse = (ModifyTermDbResponse) postAmendProxy.processRequest(dbRequest);
					processSPReturnCode(modifyTermServiceResponse, dbResponse);
				}
				else
				{
					return modifyTermServiceResponse;
				}
			}
			
			TermBean tempBean = (TermBean) dbResponse.getTransaction();

			//If the Template is not Locked, lock the template for the User
			if(!modifyTermServiceResponse.hasError() && !dbRequest.isTmpltLocked())
			{
				lockTemplate(modifyTermServiceRequest,modifyTermServiceResponse,tempBean.getTmpltId());
			}
			modifyTermServiceResponse.setTransaction(tempBean);
		}
		catch (Exception dbe)
		{
			log.error(" DBException in SaveAmendmentDelegate.processRequest() method ", dbe);

			throw new BusinessException("BE03", "Unable to Save Amendment");
		}
		finally
		{
			//If the Image upload is success and text Insert failed
			if(delInsDoc && modifyTermServiceResponse.hasError())
			{
				deleteDoc(modifyTermServiceRequest, modifyTermServiceResponse);
			}
		}
			
		return modifyTermServiceResponse;
	}
	
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException 
	{
		processSPReturnCode(serviceResponse, dbResponse);
	}
	
}
