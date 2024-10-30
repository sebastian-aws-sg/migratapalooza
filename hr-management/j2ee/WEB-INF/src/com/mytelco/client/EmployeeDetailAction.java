/*
 * Created on Aug 18, 2005
 *
 *
 */
package com.mytelco.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.Element;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;

import com.mytelco.common.EmployeeData;
import com.mytelco.server.EmployeeDetailJCICSConnector;
import com.oroad.stxx.action.Action;

/**
 * This class makes an xmldocument containing employee details.
 *
 *
 */

public class EmployeeDetailAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        EmployeeDetailForm employeeDetailForm;
        EmployeeData empData = null;
        if (form instanceof EmployeeDetailForm) {
            employeeDetailForm = (EmployeeDetailForm)form;
            if("true".equals(employeeDetailForm.getLogout())){
            	return mapping.findForward("logoutemployeedetailaction");
            }
            EmployeeDetailJCICSConnector employeeDetailJCICSConnector = new EmployeeDetailJCICSConnector();
            empData = employeeDetailJCICSConnector.GetRecord(employeeDetailForm.getEmpNo());
            employeeDetailForm.setEmployeeData(empData);
        }

//      create a new XML document for this Action with the root
        //element of "EmployeeDetails" and Employee details are there in "Employee" Node
        Document document = new Document(new Element("EmployeeDetails"));

        //add some data to the XML document so that the Action
        //will produce XML in the form
        Element Employee = new Element("Employee");
        Employee.addContent(new Element("EmpNo")
            .setText(empData.getEmpNo()));
        Employee.addContent(new Element("FirstName")
            .setText(empData.getFirstName()));
        Employee.addContent(new Element("LastName")
            .setText(empData.getLastName()));
        Employee.addContent(new Element("MidInit")
            .setText(empData.getMidinit()));
        Employee.addContent(new Element("WorkDept")
            .setText(empData.getWorkDept()));
        Employee.addContent(new Element("PhoneNo")
            .setText(empData.getPhoneNo()));
        Employee.addContent(new Element("HireDate")
            .setText(String.valueOf(empData.getHireDate())));
        Employee.addContent(new Element("Job")
            .setText(empData.getJob()));
        Employee.addContent(new Element("EdLevel")
            .setText(String.valueOf(empData.getEdLevel())));
        Employee.addContent(new Element("Sex")
            .setText(empData.getSex()));
        Employee.addContent(new Element("BirthDate")
            .setText(String.valueOf(empData.getBirthDate())));
        Employee.addContent(new Element("Salary")
            .setText(String.valueOf(empData.getSalary())));
        Employee.addContent(new Element("Bonus")
            .setText(String.valueOf(empData.getBonus())));
        Employee.addContent(new Element("Comm")
            .setText(String.valueOf(empData.getComm())));
        Employee.addContent(new Element("CreateDate")
            .setText(String.valueOf(empData.getCreateDate())));

        document.getRootElement().addContent(Employee);
        saveDocument(request, document);

        //return a "success" since nothing could possibly go wrong
        return mapping.findForward("employeedetailsuccess");
    } //end perform
} //end TestSuiteAction

