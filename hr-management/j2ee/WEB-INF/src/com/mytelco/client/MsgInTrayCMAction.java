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

import com.mytelco.server.MsgInTrayCMLocalHome;
import com.mytelco.server.MsgInTrayCMLocalObject;
import com.oroad.stxx.action.Action;

/**
 * This class calls CM Beans for getting department details
 * This class makes an xmldocument containing department details.
 *
 */

public class MsgInTrayCMAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        MsgInTrayCMForm msgInTrayCMform;
        MsgInTrayCMLocalObject msgInTrayLocalObject; 
        if (form instanceof MsgInTrayCMForm) {
        	msgInTrayCMform = (MsgInTrayCMForm)form;
            if("true".equals(msgInTrayCMform.getLogout())){
            	return mapping.findForward("logoutmsgintrayCMaction");
            }
            
            //call Container managed beans MsgInTrayCM 
            try
            {
                InitialContext ic = new InitialContext();
                Object msgCMObj = ic.lookup("java:MsgInTrayCMref");
                MsgInTrayCMLocalHome msgInTrayCMLocalHome = (MsgInTrayCMLocalHome) msgCMObj;

                msgInTrayLocalObject = msgInTrayCMLocalHome.findByPrimaryKey(msgInTrayCMform.getEmpNo());

                //create a new XML document for this Action 
                Document document = new Document(new Element("MsgInTray"));

                //add some data to the XML document so that the Action
                //will produce XML in the form
                Element MsgBox = new Element("MsgInTray");
                MsgBox.addContent(new Element("EmpNo")
                    .setText(msgInTrayLocalObject.getEmpNo()));
                MsgBox.addContent(new Element("ReceivedDate")
                    .setText(msgInTrayLocalObject.getReceivedDate().toString()));
                MsgBox.addContent(new Element("Source")
                    .setText(msgInTrayLocalObject.getSource()));
                MsgBox.addContent(new Element("Subject")
                    .setText(msgInTrayLocalObject.getSubject()));
                MsgBox.addContent(new Element("NoteText")
                    .setText(msgInTrayLocalObject.getNoteText()));
                document.getRootElement().addContent(MsgBox);
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
        return mapping.findForward("msgintrayCMsuccess");
    } //end perform
} //end 

