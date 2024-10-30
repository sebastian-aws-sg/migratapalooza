/*
 * Created on Aug 18, 2005
 *
 *
 */
package com.mytelco.client;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mytelco.common.CommonUtility;
import com.mytelco.common.LoginInfo;
import com.mytelco.server.LogonSession;
import com.mytelco.server.LogonSessionHome;

/**
 * This class initializes our login logic.
 *
 * @Author myTelco
 */
public class LogonAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        if (form instanceof LogonForm) {
            String action = "logonfail";
            LogonForm logonData = (LogonForm) form;

            /* Gather the user id */
            String userID = logonData.getUserId();
            String password = logonData.getPassword();

            LoginInfo loginInfo = new LoginInfo(userID, password);

            /* Place the user business object into the session
             * for future use in our project.
             */
            HttpSession the_session = request.getSession();

            the_session.setAttribute("LoginInfo", loginInfo);

            //code to authenticate
            boolean isValiduser = false;
            LogonSessionHome logonSessionHome = (LogonSessionHome)CommonUtility.getHomeInterface("LogonSessionEJB", LogonSessionHome.class);

            try {
                LogonSession logonSession = (LogonSession)logonSessionHome.create();
                isValiduser = logonSession.authenticate(loginInfo);
                System.out.println(" authenticate result = " + isValiduser);
            } catch (RemoteException e) {
                e.printStackTrace();
                action = "fatalerror";
            } catch (CreateException e) {
                e.printStackTrace();
                action = "fatalerror";
            }


            /* Determine where to send the user */
            if (isValiduser) {
                action = "logonsuccess";
                System.out.println("it is a valid user loggin in");
            }

            System.out.println("action = " + action);
            return mapping.findForward(action);
        }
        return null;
    }

}

