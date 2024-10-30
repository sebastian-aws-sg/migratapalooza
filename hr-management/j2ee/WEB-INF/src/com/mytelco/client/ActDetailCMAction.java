/*
 * Created on Aug 18, 2005
 *
 *
 */
package com.mytelco.client;

import java.io.IOException;

import javax.ejb.FinderException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.Element;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;

import com.mytelco.server.ActivityCMLocalHome;
import com.mytelco.server.ActivityCMLocalObject;
import com.mytelco.server.DepartmentCMLocalHome;
import com.mytelco.server.DepartmentCMLocalObject;
import com.mytelco.server.EmployeeDataCMLocalHome;
import com.mytelco.server.EmployeeDataCMLocalObject;
import com.mytelco.server.EmployeeNO;
import com.oroad.stxx.action.Action;

/**
 * This class calls CM Beans for getting department details
 * This class makes an xmldocument containing department details.
 *
 */

public class ActDetailCMAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        ActDetailCMForm actdetailsCMform;
        ActivityCMLocalObject actLocalObject; 
        if (form instanceof ActDetailCMForm) {
        	actdetailsCMform = (ActDetailCMForm)form;
            if("true".equals(actdetailsCMform.getLogout())){
            	return mapping.findForward("logoutactdetailsCMaction");
            }
            
            //call Container managed beans ActivityCM for 
            try
            {
                InitialContext ic = new InitialContext();
                Object actCMObj = ic.lookup("java:ActivityCMref");
                ActivityCMLocalHome activityCMLocalHome = (ActivityCMLocalHome) actCMObj;

                actLocalObject  = activityCMLocalHome.findByPrimaryKey(actdetailsCMform.getActNo());

                //create a new XML document for this Action 
                Document document = new Document(new Element("ActivityDetails"));

                //add some data to the XML document so that the Action
                //will produce XML in the form
                Element Activity = new Element("Activity");
                Activity.addContent(new Element("ActNo")
                    .setText(actLocalObject.getActivityNo()));
                Activity.addContent(new Element("ActKeyword")
                    .setText(actLocalObject.getActivityKeyword()));
                Activity.addContent(new Element("ActivityDescription")
                    .setText(actLocalObject.getActivityDescription()));
                document.getRootElement().addContent(Activity);
                saveDocument(request, document);

            }
            catch(NamingException ne)
            {
            	System.out.println(ne.getExplanation());
            }
            catch(FinderException fe)
            {
            	System.out.println(fe.getMessage());
            }

        }

        //return a "success" since nothing could possibly go wrong
        return mapping.findForward("actdetailCMsuccess");
    } //end perform
} //end 

