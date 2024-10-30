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
import com.oroad.stxx.action.Action;

/**
 * This class calls CM Beans for getting employee details
 * This class makes an xmldocument containing employee details.
 *
 */

public class EmpDetailCMAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        EmpDetailCMForm empdetailsCMform;
        EmployeeDataCMLocalObject empLocalObject; 
        if (form instanceof EmpDetailCMForm) {
        	empdetailsCMform = (EmpDetailCMForm)form;
            if("true".equals(empdetailsCMform.getLogout())){
            	return mapping.findForward("logoutempdetailCMaction");
            }
            
            //call Container managed beans EmployeeDataCM for 
            //getting employee data by primary key employee number
            try
            {
                InitialContext ic = new InitialContext();
                Object empCMObj = ic.lookup("java:EmployeeDataCMref");
                EmployeeDataCMLocalHome empDataLocalHome = (EmployeeDataCMLocalHome) empCMObj;
                EmployeeNO empNoObj = new EmployeeNO();
                empNoObj.empNo = empdetailsCMform.getEmpNo();
                empLocalObject = empDataLocalHome.findByPrimaryKey(empNoObj);

                //              create a new XML document for this Action with the root
                //element of "EmployeeDetails" and Employee details are there in "Employee" Node
                Document document = new Document(new Element("EmployeeDetails"));

                //add some data to the XML document so that the Action
                //will produce XML in the form
                Element Employee = new Element("Employee");
                Employee.addContent(new Element("EmpNo")
                    .setText(empLocalObject.getEmpNo()));
                Employee.addContent(new Element("FirstName")
                    .setText(empLocalObject.getFirstName()));
                Employee.addContent(new Element("Job")
                    .setText(empLocalObject.getJob()));
                Employee.addContent(new Element("Salary")
                    .setText(String.valueOf(empLocalObject.getSalary())));
                document.getRootElement().addContent(Employee);
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
        return mapping.findForward("empdetailCMsuccess");
    } //end perform
} //end 

