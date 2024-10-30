package com.dtcc.dnv.otc.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dtcc.dnv.otc.common.security.model.IUser;
import com.dtcc.sharedservices.cwf.web.struts.DtccBaseAction;
import com.dtcc.sharedservices.security.common.CUOUser;

/**
 * @author Steve Velez
 * @date Jun 23, 2007
 *
 * Abstract base action for all DerivSERV action classes.  This will serve as a layer
 * between DerivSERV framework and any other framework (i.e. Struts, CWF, etc).
 */
public abstract class DSBaseAction extends DtccBaseAction {

	/**
	 * CWF framework interface action execution method.
	 * 
	 * @see com.dtcc.sharedservices.cwf.web.struts.DtccBaseAction#returnForward(com.dtcc.sharedservices.security.common.CUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward returnForward(CUOUser user, ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception{
		// If user instance of DerivSERV fraework, then cast to IUser and call IUser method.
		if (user instanceof IUser){
			return this.returnForward((IUser)user, mapping, form, req, res);
		}
		// If user is not an IUser then use default CWF user instance.  Should really only be used by signon.
		else
			return this.returnForward(user, mapping, form, req, res);
	}
	
	/**
	 * Abstract methods used by core DerivSERV applications.  This method is used to 
	 * implement the DerivSERV fraework IUser object.
	 * 
	 * @param user IUser object
	 * @param mapping ActionMapping struts class
	 * @param form ActionForm struts form bean
	 * @param req HttpServletRequest object
	 * @param res HttpServletResponse object
	 * @return ActionForward struts forward object.
	 * @throws Exception
	 */
	public abstract ActionForward returnForward(IUser user, ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception;
}
