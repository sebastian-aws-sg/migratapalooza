/*
 * Created on Oct 9, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.dbhelper.home.AppContextDbRequest;
import com.dtcc.dnv.mcx.dbhelper.home.AppContextDbResponse;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.delegate.home.DisplayMCAsDelegate;
import com.dtcc.dnv.mcx.delegate.home.DisplayMCAsServiceRequest;
import com.dtcc.dnv.mcx.delegate.home.DisplayMCAsServiceResponse;
import com.dtcc.dnv.mcx.proxy.home.LoadProductsProxy;
import com.dtcc.dnv.mcx.proxy.mca.LoadDocumentImagesProxy;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ApplicationContextHandler {
	
	/* Application Context Handler Instance */
	private static ApplicationContextHandler _instance = null;
	
	/* Products List */
	private List productList;
	
	private Map docImages;
	
	private Map pendingMCAs = new HashMap();
	
	/* Message Logger */
	private static MessageLogger log = MessageLogger.getMessageLogger(ApplicationContextHandler.class.getName());

	/* Static members */
	static {
		ApplicationContextHandler appCont;
		appCont = ApplicationContextHandler.getInstance();
	}
	
	/**
	 * ApplicationContextHandler
	 */
	private ApplicationContextHandler() {
		super();
		loadProductList();
		loadDocImages();
	}

	/**
	 * @return ApplicationContextHandler
	 */
	public static ApplicationContextHandler getInstance() {
		if (_instance == null)
			_instance = new ApplicationContextHandler();
		return _instance;
	}
	
	/**
	 * loadProductList
	 */
	private void loadProductList() {
		try {			
			log.info("Product List Loading...");

			/* Get Product list from backend */
			LoadProductsProxy dbProxy = new LoadProductsProxy();			
			AppContextDbRequest dbRequest = new AppContextDbRequest("DPMXMPSP", null);
			AppContextDbResponse dbResponse = (AppContextDbResponse) dbProxy.processRequest(dbRequest);
			
			/* Set returned list */
			this.productList = (List)dbResponse.getContent();

		} catch (Throwable ex) {
			log.error(ex);
		} finally {
			//bLock = false;
		}
		log.info("Product List Loaded...");
	}

	/**
	 * updateProductList
	 */
	public void updateProductList() 
	{
		loadProductList();
	}
	
	public void loadUpdatePendingMCAs(String companyId)
	{
		try
		{
	        DisplayMCAsServiceRequest serviceRequest = new DisplayMCAsServiceRequest(null);
	        DisplayMCAsServiceResponse serviceResponse = new DisplayMCAsServiceResponse();
	
	        serviceRequest.setCompanyID(companyId);
	        serviceRequest.setUserID("");
	        serviceRequest.setPendingExecutedIndicator(MCXConstants.MCA_STATUS_PENDING);
	
	        DisplayMCAsDelegate delegate = new DisplayMCAsDelegate();
	
	        serviceResponse = (DisplayMCAsServiceResponse) delegate.processRequest(serviceRequest);
	        List list = serviceResponse.getDealerDetailsList();
	        if(list != null && !list.isEmpty())
	        	pendingMCAs.put(companyId, getMCASideMenu(list));
	        else
	        	pendingMCAs.put(companyId, "");
		}
		catch(Exception e)
		{
			log.error(e);
		}
	}
	
	public String getPendingMCAs(String companyId)
	{
		if(pendingMCAs.get(companyId) == null)
		{
			loadUpdatePendingMCAs(companyId);
		}
		return (String)pendingMCAs.get(companyId);
	}

	
	/**
	 * loadDocImages
	 */
	private void loadDocImages() {
		try {			
			log.info("Document Images Loading...");

			/* Get Doc list from backend */
			LoadDocumentImagesProxy dbProxy = new LoadDocumentImagesProxy();			
			DealerClientListDbRequest dbRequest = new DealerClientListDbRequest("DPMXDDLD", null);
			dbRequest.setManageDocs("I");
			DealerClientListDbResponse dbResponse = (DealerClientListDbResponse) dbProxy.processRequest(dbRequest);
			
			/* Set returned list */
			this.docImages = dbResponse.getImages();
			log.info("Number of Images : " + this.docImages.size());
		} catch (Throwable ex) {
			log.error(ex);
		} finally {
			//bLock = false;
		}
		log.info("Document Images Loaded...");
	}
	
	public byte[] getDocImage(String docId) {
		
		byte[] data = (byte[])docImages.get(docId);
		
		if(data==null) {
			loadDocImages();
			data = (byte[])docImages.get(docId);
		}
		return data;
	}

	/**
	 * @return Returns the prods.
	 */
	public List getProductsList() {
		return productList;
	}
	
    /**
     * Get the MCA (Pending MCA) menu data from the Pending MCA Dealer details
     * @param dealerDetailsList
     * @return
     */
	private String getMCASideMenu(List dealerDetailsList)
    {
        String sideMenu = "<table  border='0' cellspacing='0' onmouseover='side_menu()' onmouseout='hidesidemenu()'>";
        String prevDlrCd = null;
        String dlrNm = null;
        Iterator iter = dealerDetailsList.iterator();
        int j = 0;
        while (iter.hasNext())
        {
            TemplateBean templateBean = (TemplateBean) iter.next();
            dlrNm = templateBean.getOrgDlrNm().trim();
            if (prevDlrCd == null || !prevDlrCd.equalsIgnoreCase(templateBean.getOrgDlrCd()))
            {
                sideMenu = sideMenu + "<tr onclick=javascript:window.location.href='#' onmouseover='showsubmenu1(";
                sideMenu = sideMenu + "DLR_" + templateBean.getOrgDlrCd();
                sideMenu = sideMenu + ", " + j + ")' onmouseout='hidesubmenu1()' ><td id='submenu"+j+"'>";
                sideMenu = sideMenu + "<div style='float: left; text-align: left ;cursor:hand'><a href='#' class='menu_side_col1'>" + dlrNm +"&nbsp;</div>";
                sideMenu = sideMenu + "<div style='float: right;cursor:hand'><img border='0' class='padL20a' align='absmiddle' src='/mcx/static/images/orange_arrow_side.gif' alt='arrow for drop down' /> </a>";
            }
            sideMenu = sideMenu + "<input type='hidden' name='DLR_" + templateBean.getOrgDlrCd() + "' id='" + templateBean.getTmpltId() + "' value='" + templateBean.getTmpltNm().trim() + "' />";

            prevDlrCd = templateBean.getOrgDlrCd();
            j = j + 1;
        }
        sideMenu = sideMenu + "</table>";
        return sideMenu;
    }

	
}
