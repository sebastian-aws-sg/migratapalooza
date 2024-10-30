/*
 * Created on Sep 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.admin;

import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbResponse;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceResponse;
import com.dtcc.dnv.mcx.formatters.FormatterDelegate;
import com.dtcc.dnv.mcx.formatters.IFormatter;
import com.dtcc.dnv.mcx.proxy.mca.LockUnlockProxy;
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
* This class is used as a Delegate to Post the ISDA Term Details by the admin
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
* @author Elango TR
* @date Sep 21, 2007
* @version 1.0
*/
public class PostISDATermDetailDelegate extends MCXAbstractBusinessDelegate {  
	
private final static MessageLogger log = MessageLogger.getMessageLogger(PostISDATermDetailDelegate.class.getName());

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
		
		ModifyTermProxy postAmendProxy = new ModifyTermProxy();
		try
		{
		    log.debug("1. Inside the PostISDATermDetailDelegate >>>");
			ModifyTermDbRequest dbRequest = new ModifyTermDbRequest("", modifyTermServiceRequest.getAuditInfo());
			DbRequestBuilder.copyObject(modifyTermServiceRequest, dbRequest);
			
			//log.debug("2. modifyTermServiceRequest.isTmpltLocked() the PostISDATermDetailDelegate >>>" +modifyTermServiceRequest.isTmpltLocked());
			dbRequest.setTmpltLocked(modifyTermServiceRequest.isTmpltLocked()); // Boolean not copied
			dbRequest.setFunctionalIndicator(MCXConstants.FUNCTION_INDICATOR_ADMIN);
			
			TermBean termBean = (TermBean) dbRequest.getTransaction();
			FormatterDelegate.format(termBean, null, IFormatter.FORMAT_TYPE_INPUT);
			
			if(!dbRequest.isTmpltLocked())
			{
				lockTemplate(modifyTermServiceRequest,modifyTermServiceResponse,termBean.getTmpltId());
				dbRequest.setTmpltLocked(true);
			}
			
				
			//log.debug("3. termBean.getImageObj() in PostISDATermDetailDelegate is >>>" + termBean.getImageObj());
			//log.debug("4. termBean.getImageObj().length in PostISDATermDetailDelegate is >>>" + termBean.getImageObj().length);
			if(termBean.getImageObj() != null && termBean.getImageObj().length != 0)
			{
				dbRequest.setIndicator(MCXConstants.DOCUMENT_INDICATOR);
				
				if(termBean.getDocId() != 0)
					dbRequest.setValueID(termBean.getDocId());
				//log.debug("5. termBean.getDocId()in PostISDATermDetailDelegate is >>>" + termBean.getDocId());
				//log.debug("6. modifyTermServiceResponse.hasError() in PostISDATermDetailDelegate is >>>" + modifyTermServiceResponse.hasError());
				if(!modifyTermServiceResponse.hasError())	{
				dbResponse = (ModifyTermDbResponse) postAmendProxy.processRequest(dbRequest);
					processSPReturnCode(modifyTermServiceResponse, dbResponse);
				}
				log.debug("7. after the modifyterm in PostISDATermDetailDelegate is >>>");
				//Get the Doc id and update the text
				TermBean tempBean = (TermBean) dbResponse.getTransaction();
				modifyImageSource(termBean, tempBean.getDocId());
				termBean.setTmpltId(tempBean.getTmpltId());
				termBean.setTmpltTyp(tempBean.getTmpltTyp());
				dbRequest.setIndicator(MCXConstants.TEXT_INDICATOR);
				if(termBean.getTermTextId() != 0)
					dbRequest.setValueID(termBean.getTermTextId());
				if(!modifyTermServiceResponse.hasError())	{
				dbResponse = (ModifyTermDbResponse) postAmendProxy.processRequest(dbRequest);
					processSPReturnCode(modifyTermServiceResponse, dbResponse);
			}
			else
			{
					delInsDoc = true;
				}
			}
			else
			{
				//If Image Deleted on Update
			    //log.debug("8. termBean.getDocId() in PostISDATermDetailDelegate is >>>"+termBean.getDocId());
			    
				if(termBean.getDocId() != 0 && ! isImagePresent(termBean))
				{
				    //log.debug("9. inside the loop if the Image is not Present(termBean) in PostISDATermDetailDelegate is >>>"+isImagePresent(termBean));
					dbRequest.setDocDeleteInd(MCXConstants.DOCUMENT_INDICATOR);
					dbRequest.setIndicator(MCXConstants.DOCUMENT_INDICATOR);
					dbRequest.setValueID(termBean.getDocId());
					//log.debug("10. if modifyTermServiceResponse does not have Error() in PostISDATermDetailDelegate is >>>"+modifyTermServiceResponse.hasError());
					if(!modifyTermServiceResponse.hasError())	{
					dbResponse = (ModifyTermDbResponse) postAmendProxy.processRequest(dbRequest);
						processSPReturnCode(modifyTermServiceResponse, dbResponse);
					}						
				}
					dbRequest.setIndicator(MCXConstants.TEXT_INDICATOR);
				if(isImagePresent(termBean))
					modifyImageSource(termBean, termBean.getDocId());
					if(termBean.getTermTextId() != 0)
						dbRequest.setValueID(termBean.getTermTextId());
				//log.debug("11. if modifyTermServiceResponse does not have Error() in PostISDATermDetailDelegate is >>>"+modifyTermServiceResponse.hasError());
				if(!modifyTermServiceResponse.hasError())	{				
					dbResponse = (ModifyTermDbResponse) postAmendProxy.processRequest(dbRequest);
					processSPReturnCode(modifyTermServiceResponse, dbResponse);
				}
			}			
			
			TermBean tempBean = null;
			
			if(dbResponse != null && dbResponse.getTransaction() != null)
				tempBean = (TermBean) dbResponse.getTransaction();

			modifyTermServiceResponse.setTransaction(tempBean);
		}
		catch (Exception dbe)
		{
		    //log.debug("12. The general error in PostISDATermDetailDelegate is >>>" + dbe);
			log.error(" DBException in PostISDATermDetailDelegate.processRequest() method ", dbe);

			throw new BusinessException("BE03", "Unable to Save Amendment");
		}	
		finally
		{
		    log.debug("13. Insid ethe finally block of PostISDATermDetailDelegate is >>>");
		    //log.debug("14. delInsDoc in PostISDATermDetailDelegate is >>>"+delInsDoc);
		    //log.debug("15. modifyTermServiceResponse.hasError() in PostISDATermDetailDelegate is >>>"+modifyTermServiceResponse.hasError());
			if(delInsDoc && modifyTermServiceResponse.hasError())
			{
			    log.debug("16. Before deleting the doc in PostISDATermDetailDelegate >>>");
				deleteDoc(modifyTermServiceRequest, modifyTermServiceResponse);
				log.debug("17. After deleting the doc in PostISDATermDetailDelegate >>>");
			}
		}
	
		
		return modifyTermServiceResponse;
	}
	
	/**
	 * Modify the Amendment Text for the Image Source with its docId
	 * @param termBean
	 * @param docId
	 */
	private void modifyImageSource(TermBean termBean, int docId)
	{
		String amendmentValue = termBean.getTermVal();		
		//log.debug("a. amendmentValue before PostISDATermDetailDelegate.modifyImageSource is >>>" + amendmentValue);
		
		int startloc = 0;
		int endloc = 0;
		String originalFilePath = null;
		String replaceFilePath = null;

		startloc = amendmentValue.indexOf("src=") + 4;
		if(amendmentValue.indexOf("/mcx/action/getImageDoc?docId=") > 0){			
			endloc = amendmentValue.indexOf(">", startloc);
		}else if(amendmentValue.indexOf(".", startloc) > 0){			
			endloc = amendmentValue.indexOf(".", startloc)+5;		
		}else 
			return;
		
		 originalFilePath = amendmentValue.substring(startloc, endloc);
		 replaceFilePath = "'/mcx/action/getImageDoc?docId=" + docId + "'";
		amendmentValue = amendmentValue.replaceFirst(originalFilePath, replaceFilePath);
		//log.debug("b. amendmentValue after PostISDATermDetailDelegate.modifyImageSource is >>>" + amendmentValue);
		termBean.setTermVal(amendmentValue);
		termBean.setDocName(originalFilePath);
	}
	
	/**
	 * Check if there is an Image Object uploaded
	 * @param termBean
	 * @return
	 */
	private boolean isImagePresent(TermBean termBean)
	{
		boolean isImgPrsnt = false;
		String amendmentValue = termBean.getTermVal();
		
		if(amendmentValue.indexOf("src") != -1)
		{
			isImgPrsnt = true;
		}
		
		return isImgPrsnt;
	}

	/* (non-Javadoc)
     * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException
    {
        processSPReturnCode(serviceResponse,dbResponse);
	}
	
	private void lockTemplate(
			ModifyTermServiceRequest modifyTermServiceRequest,
			ModifyTermServiceResponse modifyTermServiceResponse, int tmpltId)
			throws BusinessException {

		try {		    
		    log.debug("a. Inside the PostISDATermDetailDelegate.lockTemplate method >>>");
			LockUnlockProxy lockProxy = new LockUnlockProxy();

			TemplateDbRequest templateDbRequest = new TemplateDbRequest("",
					modifyTermServiceRequest.getAuditInfo());
			templateDbRequest.setOpnInd(MCXConstants.LOCKED_INDICATOR);
			templateDbRequest.setUserId(modifyTermServiceRequest.getUsrId());
			templateDbRequest.setCmpnyId(modifyTermServiceRequest.getCmpnyCd());
			//log.debug("b. modifyTermServiceRequest.getUsrId() in PostISDATermDetailDelegate.lockTemplate method is >>>"+modifyTermServiceRequest.getUsrId());
			//log.debug("c. modifyTermServiceRequest.getCmpnyCd() in PostISDATermDetailDelegate.lockTemplate method is >>>" + modifyTermServiceRequest.getCmpnyCd());

			TemplateBean tmplBean = new TemplateBean();
			tmplBean.setTmpltId(tmpltId);
			templateDbRequest.setTransaction(tmplBean);

			TemplateDbResponse tmplDbResponse = (TemplateDbResponse) lockProxy
					.processRequest(templateDbRequest);
			processSPReturnCode(modifyTermServiceResponse, tmplDbResponse);
		} catch (Exception dbe) {
		    //log.debug("d. General Exception in PostISDATermDetailAction.lockTemplate method is >>>"+ dbe);
			log
					.error(
							" DBException in PostISDATermDetailDelegate.processRequest() method ",
							dbe);

			throw new BusinessException("BE03", "Unable to Save Amendment");
		}
        
    }
	
	private void deleteDoc(ModifyTermServiceRequest serviceRequest, ModifyTermServiceResponse serviceResponse) throws BusinessException 
	{
		ModifyTermProxy postAmendProxy = new ModifyTermProxy();
		
		try
		{
			ModifyTermDbRequest dbRequest = new ModifyTermDbRequest("", serviceRequest.getAuditInfo());
			DbRequestBuilder.copyObject(serviceRequest, dbRequest);			

			TermBean termBean = (TermBean) dbRequest.getTransaction();
			if(termBean.getDocId() != 0 && ! isImagePresent(termBean))
			{
				dbRequest.setDocDeleteInd(MCXConstants.DOCUMENT_INDICATOR);
				dbRequest.setIndicator(MCXConstants.DOCUMENT_INDICATOR);
				dbRequest.setValueID(termBean.getDocId());
				ModifyTermDbResponse dbResponse = (ModifyTermDbResponse) postAmendProxy.processRequest(dbRequest);
			}
		}
		catch (Exception dbe) 
		{
			log.error("DBException in SaveAmendmentDelegate.processRequest() method ",	dbe);
			throw new BusinessException("BE03", "Unable to Save Amendment");
		}
	}
}
