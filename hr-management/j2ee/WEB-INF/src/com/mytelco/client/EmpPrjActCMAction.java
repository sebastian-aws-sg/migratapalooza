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

import com.mytelco.server.EmpPrjActCMLocalHome;
import com.mytelco.server.EmpPrjActCMLocalObject;
import com.mytelco.server.EmpPrjActPKey;
import com.oroad.stxx.action.Action;

/**
 * This class calls CM Beans for getting emp project activity details
 *
 */

public class EmpPrjActCMAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        EmpPrjActCMForm empPrjActCMform;
        EmpPrjActCMLocalObject empPrjActLocalObject; 
        if (form instanceof EmpDetailCMForm) {
        	empPrjActCMform = (EmpPrjActCMForm)form;
            if("true".equals(empPrjActCMform.getLogout())){
            	return mapping.findForward("logoutempprjactCMaction");
            }
            
            //call Container managed beans  
            try
            {
                InitialContext ic = new InitialContext();
                Object empPrjActCMObj = ic.lookup("java:EmployeeProjectActivityCMref");
                EmpPrjActCMLocalHome empPrjActLocalHome = (EmpPrjActCMLocalHome) empPrjActCMObj;
                EmpPrjActPKey epaPKey = new EmpPrjActPKey();
                epaPKey.employeeNo = empPrjActCMform.getEmpNo();
                epaPKey.activityNo = empPrjActCMform.getProjNo();
                epaPKey.projectNo = empPrjActCMform.getProjNo();
                
                empPrjActLocalObject = empPrjActLocalHome.findByPrimaryKey(epaPKey);

                //              create a new XML document for this Action with the root
                //element of "ProjectDetails" and Employee details are there in "Project" Node
                Document document = new Document(new Element("EmpPrjActDetails"));

                //add some data to the XML document so that the Action
                //will produce XML in the form
                Element EmpPrjAct = new Element("EmpPrjAct");
                EmpPrjAct.addContent(new Element("EmpNo")
                    .setText(empPrjActLocalObject.getEmpNo()));
                EmpPrjAct.addContent(new Element("ProjNo")
                    .setText(empPrjActLocalObject.getProjNo()));
                EmpPrjAct.addContent(new Element("ActNo")
                    .setText(empPrjActLocalObject.getActNo()));
                EmpPrjAct.addContent(new Element("EStartDate")
                        .setText(empPrjActLocalObject.getEStartDate().toString()));
                EmpPrjAct.addContent(new Element("EEndDate")
                        .setText(empPrjActLocalObject.getEEndDate().toString()));
                document.getRootElement().addContent(EmpPrjAct);
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
        return mapping.findForward("empprjactCMsuccess");
    } //end perform
} //end 

