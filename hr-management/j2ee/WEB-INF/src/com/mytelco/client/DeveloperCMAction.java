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

import com.mytelco.server.DeveloperCMLocalHome;
import com.mytelco.server.DeveloperCMLocalObject;
import com.mytelco.server.EmployeeDataCMLocalHome;
import com.mytelco.server.EmployeeDataCMLocalObject;
import com.mytelco.server.EmployeeNO;
import com.mytelco.server.ProjectCMLocalHome;
import com.mytelco.server.ProjectCMLocalObject;
import com.oroad.stxx.action.Action;

/**
 * This class calls CM Beans for getting employee details
 * This class makes an xmldocument containing employee details.
 *
 */

public class DeveloperCMAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        DeveloperCMForm developerCMform;
        DeveloperCMLocalObject developerLocalObject; 
        if (form instanceof DeveloperCMForm) {
        	developerCMform = (DeveloperCMForm)form;
            if("true".equals(developerCMform.getLogout())){
            	return mapping.findForward("logoutdeveloperCMaction");
            }
            
            //call Container managed beans DeveloperCM for 
            try
            {
                InitialContext ic = new InitialContext();
                Object developerCMObj = ic.lookup("java:DeveloperCMref");
                DeveloperCMLocalHome developerLocalHome = (DeveloperCMLocalHome) developerCMObj;
                developerLocalObject = developerLocalHome.findByPrimaryKey(developerCMform.getDeveloperId());

                //              create a new XML document for this Action with the root
                //element of "DeveloperDetails" and Developer details are there in "Developer" Node
                Document document = new Document(new Element("ProjectDetails"));

                //add some data to the XML document so that the Action
                //will produce XML in the form
                Element Developer = new Element("Developer");
                Developer.addContent(new Element("DeveloperId")
                    .setText(developerLocalObject.getDeveloperId()));
                Developer.addContent(new Element("HardwareId")
                    .setText(developerLocalObject.getHardwareId()));
                Developer.addContent(new Element("SkillLevel")
                    .setText(developerLocalObject.getSkillLevel().toString()));
                Developer.addContent(new Element("Salary")
                        .setText(developerLocalObject.getSalary().toString()));
                Developer.addContent(new Element("Experience")
                        .setText(developerLocalObject.getExperience().toString()));
                document.getRootElement().addContent(Developer);
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
        return mapping.findForward("developerCMsuccess");
    } //end perform
} //end 

