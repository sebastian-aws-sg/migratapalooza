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

import com.mytelco.common.ActivityData;
import com.mytelco.server.ActivityDetailJCICSConnector;
import com.oroad.stxx.action.Action;

/**
 * This class makes an xmldocument containing activity details.
 *
 *
 */

public class ActivityDetailAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        ActivityDetailForm activityDetailForm;
        ActivityData activityData = null;
        if (form instanceof ActivityDetailForm) {
            activityDetailForm = (ActivityDetailForm)form;
            if("true".equals(activityDetailForm.getLogout())){
            	return mapping.findForward("logoutactivitydetailaction");
            }
            ActivityDetailJCICSConnector activityDetailJCICSConnector = new ActivityDetailJCICSConnector();
            activityData = activityDetailJCICSConnector.GetRecord(activityDetailForm.getactNo());
            activityDetailForm.setActivityData(activityData);
        }

//      create a new XML document for this Action with the root
        //element of "ActivityDetails" and Activity details are there in "Activity" Node
        Document document = new Document(new Element("ActivityDetails"));

        //add some data to the XML document so that the Action
        //will produce XML in the form
        Element Activity = new Element("Activity");
        Activity.addContent(new Element("ActNo")
            .setText(activityData.getactNo().toString()));
        Activity.addContent(new Element("ActDesc")
            .setText(activityData.getactDesc()));

        document.getRootElement().addContent(Activity);
        saveDocument(request, document);

        //return a "success" since nothing could possibly go wrong
        return mapping.findForward("activitydetailsuccess");
    } //end perform
} //end TestSuiteAction

