package com.dtcc.dnv.otc.common.action;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.tiles.TilesRequestProcessor;

import com.dtcc.dnv.otc.common.security.model.IUser;
import com.dtcc.dnv.otc.common.security.model.UserFactory;

/**
 * @author Steve Velez
 * @date October 26, 2007
 *
 * Overrides default request processor to implement roel check.  This
 * class will validate against the IUser first, and then call
 * the parent processRoles().
 * 
 * @version	1.1			October 20, 2007			sv
 * Updated redirectURL logic.
 */
public class DSRequestProcessor extends TilesRequestProcessor {
	
	private String authorizationError = null;
	private static final String AUTH_ERROR_FORWARD = "authorizationError";
   
	/**
	 * @see org.apache.struts.action.RequestProcessor#processRoles(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.action.ActionMapping)
	 */	
	protected boolean processRoles(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping) 
		throws ServletException, IOException{
		// Initialize local variable.
		boolean hasRole =  false;
		boolean hasACL = false;
		IUser user = null;
		
		try{
			// Get the user object
			user = UserFactory.getUser(request);
		}
		catch(Throwable e){
			// If there is no user object, then set t ofalse, because there is no way to check ACL
		}
		
		// If the mapping is a SecureMapping, then check for ACL.
		if (mapping instanceof SecureMapping){
			// Cast mapping object
			SecureMapping aclMapping = (SecureMapping)mapping;
			// Get ACL.
			String acl = aclMapping.getPrivilege();
			// If there is no ACL, then set to true.
			if (acl ==  null || acl.trim().length()==0){
				hasACL = true;
			}
			else if (user == null){
				hasACL = false;
			}
			// Check the ACL.
			else{
				hasACL = user.hasModuleAccess(acl); 
			}
		}
		// There is no Secure mapping, so do not use ACL, set to true
		else{
			hasACL = true;
		}
		
		// Get the roles defined in the mapping.
		String [] roles = mapping.getRoleNames();
		
		// If there no roles defined then return true.
		if (roles == null || roles.length==0){
			hasRole = true;
		}
		else if (user == null){
			hasRole = false;
		}
		// Check role in user as primary step.
		else{
	   		// Loop through defined roles and validate in the user object
	   		for (int i = 0; i <roles.length && (!hasRole); i++){
	   			hasRole = user.hasRole(roles[i].trim());
	   		}
		}
				
		boolean boolToReturn = (hasRole && hasACL);
		// If value is false, then return FORBIDDEN
		if (!boolToReturn){
			// Send error.
        	redirectUrl(request,response,mapping,AUTH_ERROR_FORWARD,user, HttpServletResponse.SC_FORBIDDEN, "global.authorization.error");
		}
		// Return boolean.
		return boolToReturn;
	}
	
	/**
	 * @param request
	 * @param response
	 * @param mapping
	 * @param action
	 * @param user
	 * @param code
	 * @param message
	 */
	protected void redirectUrl( HttpServletRequest request, HttpServletResponse response, ActionMapping mapping, String action, IUser user, int code,String message){
		String redirectURL = null;
		try {
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message)); // message = global.authorization.error
            request.setAttribute(Globals.ERROR_KEY, messages);
			// get forward
			ForwardConfig forward = mapping.findForward(AUTH_ERROR_FORWARD);
			// Process forward
			processForwardConfig( request, response, forward);

	    } catch (IOException io) {
			log.info(errorMessage(user, redirectURL),io);
	    }
        catch (ServletException io) {
			log.info(errorMessage(user, redirectURL),io);
		}
	}
	
	/**
	 * Method used to generate generic error message to include user information.
     * 
	 * @param user IUser object of user issuing the request
	 * @param message Error message
	 * @return String Composed error message string
	 */
    private String errorMessage(IUser user, String message){
        String userInfo = "";
        StringBuffer sb = new StringBuffer(this.getClass().getName());

        if(user == null)
            userInfo = "No user indentified.  ";
        else
            userInfo = user.toString() + ".  ";

        sb.append("Error occurred while processing request.  ");        
        sb.append(userInfo);
        sb.append(message);
        return sb.toString();
    }
}