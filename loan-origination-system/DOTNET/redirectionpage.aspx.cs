#region XD World Recipe V 2.8
// FileName: redirectionpage.cs
// Author: Dexter Zafra
// Date Created: 2/14/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using XDRecipe.BL;

public partial class redirectionpage : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        //Multi purpose redirection page.

        string strRedirectToHomePage = "default.aspx";

        if (!string.IsNullOrEmpty(Request.QueryString["mode"]) && !string.IsNullOrEmpty(Request.QueryString["ReturnURL"]))
        {
            string mode = Request.QueryString["mode"];
            string strURLRedirect = Request.QueryString["ReturnURL"];

            switch (mode)
            {
                //Greetings during login
                case "welcome":
                    PanelWelcomeBack.Visible = true;
                    lblwelcomeusername.Text = "Welcome Back " + Request.QueryString["username"];
                    Response.AppendHeader("Refresh", "3; url=" + strURLRedirect);
                    break;

                //Thank you after logout
                case "thankyoulogout":
                    PanelThankYouLogout.Visible = true;
                    Response.AppendHeader("Refresh", "3; url=" + strURLRedirect);
                    break;

                //Display suspended message
                case "suspended":
                    PanelAccountSuspended.Visible = true;
                    Response.AppendHeader("Refresh", "12; url=" + strURLRedirect);
                    break;

                //Display not active - account has not been activated yet.
                case "notactive":
                    PanelAccountNotActivated.Visible = true;
                    Response.AppendHeader("Refresh", "12; url=" + strURLRedirect);
                    break;
            }
        }

        //New user registration
        if (!string.IsNullOrEmpty(Request.QueryString["email"]))
        {
            PanelForJoining.Visible = true;

            lblheader.Text = "Thank You for Joining " + constant.DomainName;
            lblsuccess.Text = "The activation link was sent to ( " + Request.QueryString["email"] + " ).<br>You should receive an email in few seconds containing the activation link.<br>Go to your email and click the activation link to activate your account.";
            lblwait.Text = "Please wait, you will be redirected in 12 seconds back to the homepage.";

            Response.AppendHeader("Refresh", "12; url=" + strRedirectToHomePage);
        }

        //Profile update
        if (!string.IsNullOrEmpty(Request.QueryString["uid"]) && !string.IsNullOrEmpty(Request.QueryString["uname"]) && !string.IsNullOrEmpty(Request.QueryString["logout"]))
        {
            string strURLRedirectProfileUpdate = "";
            string strLogUser = Request.QueryString["logout"];

            PanelProfileUpdateSuccess.Visible = true;

            switch (strLogUser)
            {
                case "Yes":
                    lblheaderupdateprofilesuccess.Text = "Profile Update Success. You are now logged off";
                    lblupdateprofilemsg.Text = "<b>" + Request.QueryString["uname"] + "'s</b> information had been successfully updated. You are now logged off of the site.";
                    lblupdateprofilewait.Text = "Please wait, you will be redirected in 3 seconds into your profile page.";
                    strURLRedirectProfileUpdate = "default.aspx";
                    break;

                case "No":
                    lblheaderupdateprofilesuccess.Text = "Profile Update Success.";
                    lblupdateprofilemsg.Text = "<b>" + Request.QueryString["uname"] + "'s</b> information had been successfully updated.";
                    lblupdateprofilewait.Text = "Please wait, you will be redirected in 3 seconds back to the  homepage to login.";
                    strURLRedirectProfileUpdate = "userprofile.aspx?uid=" + Request.QueryString["uid"];
                    break;
            }

            Response.AppendHeader("Refresh", "3; url=" + strURLRedirectProfileUpdate);

        }
    }

}
