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
import com.mytelco.server.HardwareCMLocalHome;
import com.mytelco.server.HardwareCMLocalObject;
import com.mytelco.server.ProjectCMLocalHome;
import com.mytelco.server.ProjectCMLocalObject;
import com.oroad.stxx.action.Action;

/**
 * This class calls CM Beans for getting employee details
 * This class makes an xmldocument containing employee details.
 *
 */

public class HardwareCMAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        HardwareCMForm hardwareCMform;
        HardwareCMLocalObject hardwareLocalObject; 
        if (form instanceof HardwareCMForm) {
        	hardwareCMform = (HardwareCMForm)form;
            if("true".equals(hardwareCMform.getLogout())){
            	return mapping.findForward("logouthardwareCMaction");
            }
            
            //call Container managed beans hardwareCM for 
            try
            {
                InitialContext ic = new InitialContext();
                Object hardwareCMObj = ic.lookup("java:HardwareCMref");
                HardwareCMLocalHome hardwareLocalHome = (HardwareCMLocalHome) hardwareCMObj;
                hardwareLocalObject = hardwareLocalHome.findByPrimaryKey(hardwareCMform.getHardwareId());

                //              create a new XML document for this Action with the root
                //element of "DeveloperDetails" and Developer details are there in "Developer" Node
                Document document = new Document(new Element("Hardware"));

                //add some data to the XML document so that the Action
                //will produce XML in the form
                Element Hardware = new Element("Hardware");
                Hardware.addContent(new Element("HardwareId")
                    .setText(hardwareLocalObject.getHardwareId()));
                Hardware.addContent(new Element("MouseId")
                    .setText(hardwareLocalObject.getMouseId()));
                Hardware.addContent(new Element("KeyboardId")
                    .setText(hardwareLocalObject.getKeyboardId()));
                Hardware.addContent(new Element("CPUId")
                        .setText(hardwareLocalObject.getCpuId()));
                Hardware.addContent(new Element("MonitorId")
                        .setText(hardwareLocalObject.getMonitorId()));
                document.getRootElement().addContent(Hardware);
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
        return mapping.findForward("hardwareCMsuccess");
    } //end perform
} //end 

