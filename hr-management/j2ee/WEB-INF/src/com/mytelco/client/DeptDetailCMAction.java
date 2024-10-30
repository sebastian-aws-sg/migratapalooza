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

public class DeptDetailCMAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        DeptDetailCMForm deptdetailsCMform;
        DepartmentCMLocalObject deptLocalObject; 
        if (form instanceof DeptDetailCMForm) {
        	deptdetailsCMform = (DeptDetailCMForm)form;
            if("true".equals(deptdetailsCMform.getLogout())){
            	return mapping.findForward("logoutdeptdetailsCMaction");
            }
            
            //call Container managed beans EmployeeDataCM for 
            //getting employee data by primary key employee number
            try
            {
                InitialContext ic = new InitialContext();
                Object deptCMObj = ic.lookup("java:DepartmentCMref");
                DepartmentCMLocalHome departmentCMLocalHome = (DepartmentCMLocalHome) deptCMObj;

                deptLocalObject = departmentCMLocalHome.findByPrimaryKey(deptdetailsCMform.getDeptNo());

                //create a new XML document for this Action 
                Document document = new Document(new Element("DepartmentDetails"));

                //add some data to the XML document so that the Action
                //will produce XML in the form
                Element Department = new Element("Department");
                Department.addContent(new Element("DeptNo")
                    .setText(deptLocalObject.getDeptNo()));
                Department.addContent(new Element("DepartmentName")
                    .setText(deptLocalObject.getDepartmentName()));
                Department.addContent(new Element("ManagerNo")
                    .setText(deptLocalObject.getManagerNo()));
                Department.addContent(new Element("ADMRDepartment")
                        .setText(deptLocalObject.getADMRDepartment()));
                Department.addContent(new Element("Location")
                        .setText(deptLocalObject.getLocation()));
                document.getRootElement().addContent(Department);
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
        return mapping.findForward("deptdetailCMsuccess");
    } //end perform
} //end 

