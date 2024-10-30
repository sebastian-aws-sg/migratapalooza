package com.dtcc.dnv.mcx.action.managedocs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.dtcc.dnv.mcx.action.IManagedSession;
import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;
import com.dtcc.dnv.mcx.beans.UploadBean;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageDocsServiceRequest;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageDocsServiceResponse;
import com.dtcc.dnv.mcx.delegate.managedocs.UploadManageDocDelegate;
import com.dtcc.dnv.mcx.form.ManagedDocumentForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
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
 * @date Sep 12, 2007
 * @version 1.0
 * 
 *  
 */

public class UploadManageDocAction extends MCXBaseAction implements IManagedSession
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(UploadManageDocAction.class.getName());
    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.mcx.action.DtccBaseAction#returnForward(com.dtcc.sharedservices.security.common.CUOUser,
     *      org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();

        InputStream inStream = null;
        ByteArrayInputStream inputStream = null;
        String mcaName = "";
        String fileName = "";
        final String NO = "N";
        final String YES = "Y";
        String docsUploaded[] = new String[5];

        log.info("In UploadManageDocAction");

        ManagedDocumentForm managedDocumentForm = (ManagedDocumentForm) form;

        ManageDocsTransactionBean transactionBean = new ManageDocsTransactionBean();
        ManageDocsServiceRequest serviceRequest = new ManageDocsServiceRequest(user.getAuditInfo());
        ManageDocsServiceResponse serviceResponse = new ManageDocsServiceResponse();
        transactionBean = (ManageDocsTransactionBean) managedDocumentForm.getTransaction();

        try
        {
            if (transactionBean != null)
            {
                //managedDocumentForm.setUploadFileSizeError(false);
                managedDocumentForm.setUploadDocs(false);
                managedDocumentForm.setDocsDeleted(false);
                serviceRequest.setTransaction(transactionBean);
                String cmpnyId = user.getCompanyId();

                serviceRequest.setCmpnyId(cmpnyId);
                // Client ID is available in transaction bean -
                // SelectedDealerClient
                
                FormFile[] formFiles = transactionBean.getUploads();
                FormFile uploadedFormFile = null;
                UploadBean uploadBean = null;
                String[] filePaths =  transactionBean.getFilesPath();
                String filePath = "";
                
                //Iterate through all the docs
                for (int index = 0; formFiles != null && index < formFiles.length && (uploadedFormFile = formFiles[index]) != null; index++)
                {
                    filePath = filePaths[index];
                    
                    //To check for invalid path using filesize and filename length
                    if ((uploadedFormFile.getFileSize() > 0 && MCXConstants.OTHERTAB.equals(managedDocumentForm.getManageDocTab()))
                            || (MCXConstants.PREEXISTINGTAB.equals(managedDocumentForm.getManageDocTab()) && ((uploadedFormFile.getFileSize() == 0 && uploadedFormFile.getFileName().trim().length() == 0) || (uploadedFormFile
                                    .getFileSize() > 0 && uploadedFormFile.getFileName().trim().length() > 0))))
                    {
                        //To check if file size is less than 2MB
                        if (MCXConstants.FILE_UPLOAD_BYTE_SIZE >= uploadedFormFile.getFileSize())
                        {
                            uploadBean = new UploadBean();
                            uploadBean.setFileName(uploadedFormFile.getFileName());
                            uploadBean.setFileSize(uploadedFormFile.getFileSize());
                            uploadBean.setContentType(uploadedFormFile.getContentType());
                            uploadBean.setExecutedDate(transactionBean.getExecutedDate(index));
                            uploadBean.setCpViewable(transactionBean.getSelectedCPView(index));
                            uploadBean.setMcaNames(transactionBean.getSelectedMCANames(index));

                            inStream = uploadedFormFile.getInputStream();

                            if (inStream == null || uploadedFormFile.getFileSize() == 0)
                            {
                                inputStream = new ByteArrayInputStream(MCXConstants.EMPTY.getBytes());
                                uploadBean.setInputStream(inputStream);
                            } else
                            {
                                uploadBean.setInputStream(inStream);
                            }

                            serviceRequest.setUploadBean(uploadBean);

                            serviceRequest.setManageDocs(managedDocumentForm.getManageDocTab());

                            serviceRequest.setUserId(user.getMCXUserGUID());

                            mcaName = uploadBean.getMcaNames();
                            fileName = uploadBean.getFileName();
                            
                            // To upload the document if the required details are present.
                            if ((mcaName != null && mcaName.length() > 0 && !MCXConstants.DEFAULTVALUE.equals(mcaName) && MCXConstants.PREEXISTINGTAB.equals(managedDocumentForm.getManageDocTab()))
                                    || (fileName != null && fileName.trim().length() > 0 && MCXConstants.OTHERTAB.equals(managedDocumentForm.getManageDocTab())))
                            {

                                UploadManageDocDelegate delegate = new UploadManageDocDelegate();
                                serviceResponse = (ManageDocsServiceResponse) delegate.processRequest(serviceRequest);
                                if (!serviceResponse.hasError())
                                {
                                    /*if (MCXConstants.PREEXISTINGTAB.equals(managedDocumentForm.getManageDocTab()))
                                    {
                                        forward = mapping.findForward(MCXConstants.PREEXISTING);
                                    } else if (MCXConstants.OTHERTAB.equals(managedDocumentForm.getManageDocTab()))
                                    {
                                        forward = mapping.findForward(MCXConstants.OTHERS);
                                    }*/
                                    //Upload  success
                                    docsUploaded[index] = YES;

                                } else
                                {
                                    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error.highpriority", serviceResponse.getSpReturnMessage()));
                                    docsUploaded[index] = NO;
                                }
                            } else
                            {
                                docsUploaded[index] = YES;
                            }
                        } else
                        {
                            log.info("In UploadManageDocAction the Uploaded file size is " + uploadedFormFile.getFileSize() + ". This is greater than the maximum limit of 2MB (2097152 bytes).");
                            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error.highpriority","File Size should not be greater than 2 MB for "+ uploadedFormFile.getFileName().trim()));
                            //errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error.highpriority","File Size should not be greater than 2 MB for "+ filePath.trim()));
                            //managedDocumentForm.setUploadFileSizeError(true);
                            docsUploaded[index] = NO;
                        }
                    } else if (uploadedFormFile.getFileSize() == 0 && uploadedFormFile.getFileName().trim().length() > 0)
                    {
                        log.info("In UploadManageDocAction the Uploaded file size is " + uploadedFormFile.getFileSize() + "This is invalid file.");
                        //errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error.highpriority", "System cannot find the specified file " + uploadedFormFile.getFileName().trim()));
                        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error.highpriority", "System cannot find the specified file " + filePath.trim()));
                        docsUploaded[index] = NO;
                    } else
                    {
                        docsUploaded[index] = YES;
                    }
                }
            }
            if (!errors.isEmpty() || managedDocumentForm.isUploadFileSizeError())
            {
                saveMessages(request, errors);
                managedDocumentForm.setUploadDocs(false);
                managedDocumentForm.setSelectedDealerClient(transactionBean.getSelectedDealerClient());
                //To redirect to the same screen when there is a error like file size>2MB or document allready available.
                /*if (MCXConstants.PREEXISTINGTAB.equals(managedDocumentForm.getManageDocTab()))
                {
                    forward = mapping.findForward(MCXConstants.PREEXISTING+MCXConstants.FAILURE_FORWARD);
                } else if (MCXConstants.OTHERTAB.equals(managedDocumentForm.getManageDocTab()))
                {
                    forward = mapping.findForward(MCXConstants.OTHERS+MCXConstants.FAILURE_FORWARD);
                }*/
                
            } else
            {
                //Checking if the upload success message has to be displayed or not.
                for (int index = 0; index < 5; index++)
                {
                    if (YES.equals(docsUploaded[index]))
                    {
                        managedDocumentForm.setUploadDocs(true);
                        managedDocumentForm.setDocsUploaded(true);
                    } else
                    {
                        managedDocumentForm.setUploadDocs(false);
                        managedDocumentForm.setDocsUploaded(false);
                        break;
                    }
                }
            }
            if(managedDocumentForm.isUploadDocs()){
                if (MCXConstants.PREEXISTINGTAB.equals(managedDocumentForm.getManageDocTab()))
                {
                    forward = mapping.findForward(MCXConstants.PREEXISTING);
                } else if (MCXConstants.OTHERTAB.equals(managedDocumentForm.getManageDocTab()))
                {
                    forward = mapping.findForward(MCXConstants.OTHERS);
                }
            }else{
//              To redirect to the same screen when there is a error like file size>2MB or document allready available.
                if (MCXConstants.PREEXISTINGTAB.equals(managedDocumentForm.getManageDocTab()))
                {
                    forward = mapping.findForward(MCXConstants.PREEXISTING+MCXConstants.FAILURE_FORWARD);
                } else if (MCXConstants.OTHERTAB.equals(managedDocumentForm.getManageDocTab()))
                {
                    forward = mapping.findForward(MCXConstants.OTHERS+MCXConstants.FAILURE_FORWARD);
                }
                request.setAttribute("selectedDealerClient",managedDocumentForm.getSelectedDealerClient());
            }
            
           
           

            return forward;
        } catch (BusinessException e)
        {
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", new Timestamp(System.currentTimeMillis())));

        } catch (Exception e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.internal.error", new Timestamp(System.currentTimeMillis())));
        } finally
        {
            if (inStream != null)
            {
                inStream.close();
            }
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
        if (!errors.isEmpty())
        {
            forward = mapping.findForward(MCXConstants.FAILURE_FORWARD);
            saveMessages(request, errors);
        }
        return forward;
    }
}