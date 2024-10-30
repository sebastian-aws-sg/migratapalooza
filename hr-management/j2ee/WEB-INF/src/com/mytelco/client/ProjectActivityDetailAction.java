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

import com.mytelco.common.PActivityData;
import com.mytelco.server.ProjectActivityDetailJCICSConnector;
import com.oroad.stxx.action.Action;

/**
 * This class makes an xmldocument containing activity details.
 * 
 * 
 */

public class ProjectActivityDetailAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ProjectActivityDetailForm projectActivityDetailForm;
		PActivityData pActivityData = null;
		if (form instanceof ActivityDetailForm) {
			projectActivityDetailForm = (ProjectActivityDetailForm) form;
			if ("true".equals(projectActivityDetailForm.getLogout())) {
				return mapping.findForward("logoutactivitydetailaction");
			}
			ProjectActivityDetailJCICSConnector pActivityDetailJCICSConnector = new ProjectActivityDetailJCICSConnector();
			pActivityData = pActivityDetailJCICSConnector
					.GetRecord(projectActivityDetailForm.getactNo());
			projectActivityDetailForm.setProjectActivityData(pActivityData);
		}

		// create a new XML document for this Action with the root
		// element of "ActivityDetails" and Activity details are there in
		// "Activity" Node
		Document document = new Document(new Element("ActivityDetails"));

		// add some data to the XML document so that the Action
		// will produce XML in the form
		Element Activity = new Element("ProjectActivity");
		
		Activity.addContent(new Element("PActNo").setText(pActivityData
				.getactNo().toString()));

		Activity.addContent(new Element("PStaffNo").setText(pActivityData
				.getactStaff().toString()));

		document.getRootElement().addContent(Activity);
		saveDocument(request, document);

		// return a "success" since nothing could possibly go wrong
		return mapping.findForward("projectactivitydetailsuccess");
	} // end perform
} // end TestSuiteAction

