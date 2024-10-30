/*
 * Created on Dec 21, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.mca;

import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbResponse;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.mca.LockUnlockProxy;
import com.dtcc.dnv.mcx.proxy.mca.ModifyTermProxy;
import com.dtcc.dnv.mcx.proxy.mca.TemplateInfoProxy;
import com.dtcc.dnv.mcx.util.MCXConstants;
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
public abstract class MCXMCABusinessDelegate extends MCXAbstractBusinessDelegate {

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public abstract void processDbRequest(IServiceResponse serviceResponse,
			IDbResponse dbResponse) throws BusinessException;

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
	 */
	public abstract IServiceResponse processRequest(IServiceRequest request)
			throws BusinessException, UserException;
	
	/**
	 * Lock the given Template
	 * @param modifyTermServiceRequest
	 * @param modifyTermServiceResponse
	 * @param tmpltId
	 * @throws BusinessException
	 */
	public boolean isEligibleForLock(
			ModifyTermServiceRequest modifyTermServiceRequest,
			ModifyTermServiceResponse modifyTermServiceResponse, int tmpltId)
			throws BusinessException {
		TemplateInfoProxy templateProxy = new TemplateInfoProxy();
		TemplateDbRequest templateDbRequest = new TemplateDbRequest("",
				modifyTermServiceRequest.getAuditInfo());
		boolean eligForLock = false;
		try {

			templateDbRequest.setUserId(modifyTermServiceRequest.getUsrId());
			templateDbRequest.setCmpnyId(modifyTermServiceRequest.getCmpnyCd());

			TermBean oldTmplt = (TermBean) modifyTermServiceRequest.getTransaction();
			TemplateBean tmpltBean = new TemplateBean();
			tmpltBean.setTmpltId(tmpltId);
			templateDbRequest.setTransaction(tmpltBean);

			TemplateDbResponse tmplDbResponse = (TemplateDbResponse) templateProxy
					.processRequest(templateDbRequest);
			processSPReturnCode(modifyTermServiceResponse, tmplDbResponse);
			
			if(!modifyTermServiceResponse.hasError())	
			{
				tmpltBean = (TemplateBean) tmplDbResponse.getTransaction();
				boolean isActingDealer = modifyTermServiceRequest.isActingDealer();
				if(tmpltBean.getOrgDlrCd().equals(tmpltBean.getOrgCltCd()) 
						&& tmpltBean.getDlrStCd().equalsIgnoreCase(MCXConstants.MCA_STATUS_PENDING))
				{
					isActingDealer = true;
				}

				if(isActingDealer && tmpltBean.getDlrStCd().equalsIgnoreCase(MCXConstants.MCA_STATUS_PENDING) 
						&& oldTmplt.getTmpltTyp().equalsIgnoreCase(tmpltBean.getTmpltTyp()))
				{
					eligForLock = true;
				}
				else if( ! isActingDealer && tmpltBean.getCltStCd().equalsIgnoreCase(MCXConstants.MCA_STATUS_PENDING) 
						&& oldTmplt.getTmpltTyp().equalsIgnoreCase(tmpltBean.getTmpltTyp()))
				{
					eligForLock = true;
				}
				else
				{
					modifyTermServiceResponse.setSpReturnMessage("Currently the Template is being worked by another user.");
					modifyTermServiceResponse.setHasError(true);
				}
			}			
			
		} catch (Exception dbe) {

			throw new BusinessException("BE03", "Unable to Save Amendment");
		}
		return eligForLock;
	}	

	public void lockTemplate(ModifyTermServiceRequest modifyTermServiceRequest,
			ModifyTermServiceResponse modifyTermServiceResponse, int tmpltId) throws BusinessException
	{
			LockUnlockProxy lockProxy = new LockUnlockProxy();
		TemplateDbRequest templateDbRequest = new TemplateDbRequest("",	modifyTermServiceRequest.getAuditInfo());

		try
		{
			templateDbRequest.setOpnInd(MCXConstants.LOCKED_INDICATOR);
			templateDbRequest.setUserId(modifyTermServiceRequest.getUsrId());
			templateDbRequest.setCmpnyId(modifyTermServiceRequest.getCmpnyCd());

			TemplateBean tmplBean = new TemplateBean();
			tmplBean.setTmpltId(tmpltId);
			templateDbRequest.setTransaction(tmplBean);

			TemplateDbResponse tmplDbResponse = (TemplateDbResponse) lockProxy
					.processRequest(templateDbRequest);
			processSPReturnCode(modifyTermServiceResponse, tmplDbResponse);
		}
		catch (Exception dbe)
		{
			throw new BusinessException("BE06", "Unable to Post Comments");
		}
	}
	
	/**
	 * Delete the Inserted Image Document
	 * @param serviceRequest
	 * @param serviceResponse
	 * @throws BusinessException
	 */
	public void deleteDoc(ModifyTermServiceRequest serviceRequest, ModifyTermServiceResponse serviceResponse) throws BusinessException 
	{
		ModifyTermProxy postAmendProxy = new ModifyTermProxy();
		ModifyTermDbRequest dbRequest = new ModifyTermDbRequest("", serviceRequest.getAuditInfo());
		
		try
		{
			DbRequestBuilder.copyObject(serviceRequest, dbRequest);			

			TermBean termBean = (TermBean) dbRequest.getTransaction();
			if(termBean.getDocId() != 0 && ! termBean.isImagePresent())
			{
				dbRequest.setDocDeleteInd(MCXConstants.DOCUMENT_INDICATOR);
				dbRequest.setIndicator(MCXConstants.DOCUMENT_INDICATOR);
				dbRequest.setValueID(termBean.getDocId());
				ModifyTermDbResponse dbResponse = (ModifyTermDbResponse) postAmendProxy.processRequest(dbRequest);
			}
		}
		catch (Exception dbe) 
		{
			throw new BusinessException("BE03", "Unable to Save Amendment");
		}
	}

	public void unLockTemplate(TemplateServiceRequest serviceRequest,
			TemplateServiceResponse serviceResponse, int tmpltId) throws BusinessException
	{
			LockUnlockProxy lockProxy = new LockUnlockProxy();
			TemplateDbRequest templateDbRequest = new TemplateDbRequest("",	serviceRequest.getAuditInfo());

		try
		{
			templateDbRequest.setOpnInd(MCXConstants.UNLOCK);
			templateDbRequest.setUserId(serviceRequest.getUserId());
			templateDbRequest.setCmpnyId(serviceRequest.getCmpnyId());

			TemplateBean tmplBean = new TemplateBean();
			tmplBean.setTmpltId(tmpltId);
			templateDbRequest.setTransaction(tmplBean);

			TemplateDbResponse tmplDbResponse = (TemplateDbResponse) lockProxy
					.processRequest(templateDbRequest);
			processSPReturnCode(serviceResponse, tmplDbResponse);
		}
		catch (Exception dbe)
		{
			throw new BusinessException("BE06", "Unable to Post Comments");
		}
	}


}
