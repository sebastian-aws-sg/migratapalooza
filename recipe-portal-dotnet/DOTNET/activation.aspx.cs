#region XD World Recipe V 2.8
// FileName: activation.cs
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
using XDRecipe.UI;
using XDRecipe.BL;
using XDRecipe.Common.Utilities;
using XDRecipe.Security;

public partial class activation : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(Request.QueryString["guid"]))
        {
            string strGUID = Request.QueryString["guid"].ToString().Trim();

            if (Blogic.IsAccountActivattionCodeValid(strGUID))
            {
                if (!Blogic.IsAccountActivated(strGUID))
                {
                    HideContentIfAlreadyActivated.Visible = true;

                    activatebutton.Text = "Activate My Account";
                    activatebutton.Attributes.Add("onmouseover", "Tip('Click here to activate your account.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                    activatebutton.Attributes.Add("onmouseout", "UnTip()");

                    lblusernameactivation.Text = "Hello, " + Blogic.GetUserNameForActivationPage(strGUID).ToString();
                }
                else
                {
                    lblaccountisalreadyactivated.Visible = true;
                    lblaccountisalreadyactivated.Text = "Your account had already been activated.";
                }

                if (!string.IsNullOrEmpty(Request.QueryString["guid"]) && !string.IsNullOrEmpty(Request.QueryString["mode"]))
                {
                    if (Request.QueryString["mode"] == "success")
                    {
                        lblaccountisalreadyactivated.Visible = true;
                        lblaccountisalreadyactivated.Text = "Your account has been successfully activated. Please proceed to login.";
                    }
                }
            }
            else
            {
                lblaccountisalreadyactivated.Visible = true;
                lblaccountisalreadyactivated.Text = "The activation code is not valid.";
            }
        }
    }

    public void Activate_Click(object sender, EventArgs e)
    {
        string strGUID = Request.QueryString["guid"].ToString().Trim();

        if (!string.IsNullOrEmpty(strGUID))
        {
            Blogic.ActivateAccount(strGUID);
        }

        Response.Redirect("activation.aspx?guid=" + strGUID + "&mode=success");
    }
}
