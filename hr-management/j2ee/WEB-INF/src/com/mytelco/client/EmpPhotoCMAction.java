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
import com.mytelco.server.EmpPhotoCMHome;
import com.mytelco.server.EmpPhotoCMLocalHome;
import com.mytelco.server.EmpPhotoCMLocalObject;
import com.mytelco.server.EmployeeDataCMLocalHome;
import com.mytelco.server.EmployeeDataCMLocalObject;
import com.mytelco.server.EmployeeNO;
import com.oroad.stxx.action.Action;

/**
 * This class calls CM Beans for getting department details
 * This class makes an xmldocument containing department details.
 *
 */

public class EmpPhotoCMAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        EmpPhotoCMForm empPhotoCMform;
        EmpPhotoCMLocalObject empPhotoLocalObject; 
        if (form instanceof EmpPhotoCMForm) {
        	empPhotoCMform = (EmpPhotoCMForm)form;
            if("true".equals(empPhotoCMform.getLogout())){
            	return mapping.findForward("logoutempphotoCMaction");
            }
            
            //call Container managed beans EmpPhotoCM for 
            try
            {
                InitialContext ic = new InitialContext();
                Object empPhotoCMObj = ic.lookup("java:EmpPhotoCMref");
                EmpPhotoCMLocalHome empPhotoCMLocalHome = (EmpPhotoCMLocalHome) empPhotoCMObj;

                empPhotoLocalObject = empPhotoCMLocalHome.findByPrimaryKey(empPhotoCMform.getEmpNo());

                //create a new XML document for this Action 
                Document document = new Document(new Element("EmpPhotoDetails"));

                //add some data to the XML document so that the Action
                //will produce XML in the form
                Element EmpPhoto = new Element("EmpPhoto");
                EmpPhoto.addContent(new Element("EmpNo")
                    .setText(empPhotoLocalObject.getEmployeeNo()));
                EmpPhoto.addContent(new Element("EmployeePhoto")
                    .setText(empPhotoLocalObject.getEmployeePhoto().toString()));
                EmpPhoto.addContent(new Element("PhotoFormat")
                    .setText(empPhotoLocalObject.getPhotoFormat()));
                document.getRootElement().addContent(EmpPhoto);
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
        return mapping.findForward("empphotoCMsuccess");
    } //end perform
} //end 

