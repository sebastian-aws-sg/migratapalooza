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

import com.mytelco.server.EmpResumeCMLocalHome;
import com.mytelco.server.EmpResumeCMLocalObject;
import com.oroad.stxx.action.Action;

/**
 * This class calls CM Beans for getting department details
 * This class makes an xmldocument containing department details.
 *
 */

public class EmpResumeCMAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        EmpResumeCMForm empResumeCMform;
        EmpResumeCMLocalObject empResumeLocalObject; 
        if (form instanceof EmpResumeCMForm) {
        	empResumeCMform = (EmpResumeCMForm)form;
            if("true".equals(empResumeCMform.getLogout())){
            	return mapping.findForward("logoutempresumeCMaction");
            }
            
            //call Container managed beans EmpResumeCM for 
            try
            {
                InitialContext ic = new InitialContext();
                Object empResumeCMObj = ic.lookup("java:EmpResumeCMref");
                EmpResumeCMLocalHome empResumeCMLocalHome = (EmpResumeCMLocalHome) empResumeCMObj;

                empResumeLocalObject = empResumeCMLocalHome.findByPrimaryKey(empResumeCMform.getEmpNo());

                //create a new XML document for this Action 
                Document document = new Document(new Element("EmpResumeDetails"));

                //add some data to the XML document so that the Action
                //will produce XML in the form
                Element EmpResume = new Element("EmpResume");
                EmpResume.addContent(new Element("EmpNo")
                    .setText(empResumeLocalObject.getEmployeeNo()));
                EmpResume.addContent(new Element("EmployeeResume")
                    .setText(empResumeLocalObject.getEmployeeResume().toString()));
                EmpResume.addContent(new Element("ResumeFormat")
                    .setText(empResumeLocalObject.getResumeFormat()));
                document.getRootElement().addContent(EmpResume);
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
        return mapping.findForward("empresumeCMsuccess");
    } //end perform
} //end 

