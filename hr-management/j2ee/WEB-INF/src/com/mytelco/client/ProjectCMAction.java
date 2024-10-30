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

public class ProjectCMAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        ProjectCMForm projectCMform;
        ProjectCMLocalObject projLocalObject; 
        if (form instanceof EmpDetailCMForm) {
        	projectCMform = (ProjectCMForm)form;
            if("true".equals(projectCMform.getLogout())){
            	return mapping.findForward("logoutprojectCMaction");
            }
            
            //call Container managed beans ProjectCM for 
            try
            {
                InitialContext ic = new InitialContext();
                Object projCMObj = ic.lookup("java:ProjectCMref");
                ProjectCMLocalHome projLocalHome = (ProjectCMLocalHome) projCMObj;
                projLocalObject = projLocalHome.findByPrimaryKey(projectCMform.getProjNo());

                //              create a new XML document for this Action with the root
                //element of "ProjectDetails" and Employee details are there in "Project" Node
                Document document = new Document(new Element("ProjectDetails"));

                //add some data to the XML document so that the Action
                //will produce XML in the form
                Element Project = new Element("Project");
                Project.addContent(new Element("ProjNo")
                    .setText(projLocalObject.getProjNo()));
                Project.addContent(new Element("ProjName")
                    .setText(projLocalObject.getProjName()));
                Project.addContent(new Element("DeptNo")
                    .setText(projLocalObject.getDeptNo()));
                Project.addContent(new Element("ProjectStartDate")
                    .setText(projLocalObject.getProjStartDate().toString()));
                document.getRootElement().addContent(Project);
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
        return mapping.findForward("projectCMsuccess");
    } //end perform
} //end 

