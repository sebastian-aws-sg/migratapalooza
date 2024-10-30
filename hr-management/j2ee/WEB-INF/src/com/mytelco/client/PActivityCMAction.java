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

import com.mytelco.server.PActivityCMLocalHome;
import com.mytelco.server.PActivityCMLocalObject;
import com.oroad.stxx.action.Action;

/**
 * This class calls CM Beans for getting project activity details
 *
 */

public class PActivityCMAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        PActivityCMForm pActCMform;
        PActivityCMLocalObject pActLocalObject; 
        if (form instanceof PActivityCMForm) {
        	pActCMform = (PActivityCMForm)form;
            if("true".equals(pActCMform.getLogout())){
            	return mapping.findForward("logoutpactivityCMaction");
            }
            
            //call Container managed beans ProjectCM for 
            try
            {
                InitialContext ic = new InitialContext();
                Object pActCMObj = ic.lookup("java:pActivityCMref");
                PActivityCMLocalHome pActLocalHome = (PActivityCMLocalHome) pActCMObj;
                pActLocalObject = pActLocalHome.findByPrimaryKey(pActCMform.getProjNo());


                Document document = new Document(new Element("ProjectActivityDetails"));

                //add some data to the XML document so that the Action
                //will produce XML in the form
                Element ProjectActivity = new Element("ProjectActivity");
                ProjectActivity.addContent(new Element("ProjNo")
                    .setText(pActLocalObject.getProjNo()));
                ProjectActivity.addContent(new Element("ActNo")
                    .setText(pActLocalObject.getActNo()));
                ProjectActivity.addContent(new Element("ActStartDate")
                    .setText(pActLocalObject.getActStartDate().toString()));
                ProjectActivity.addContent(new Element("ActEndDate")
                    .setText(pActLocalObject.getActEndDate().toString()));
                document.getRootElement().addContent(ProjectActivity);
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
        return mapping.findForward("pactivityCMsuccess");
    } //end perform
} //end 

