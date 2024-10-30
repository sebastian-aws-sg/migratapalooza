#region XD World Recipe V 2.8
// FileName: Emailrecipe.cs
// Author: Dexter Zafra
// Date Created: 5/24/2008
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
using XDRecipe.BL.Providers;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;

public partial class emailrecipe : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        string RecipeName;
        string Category;
        RecipeName = Request.QueryString["n"].ToString();
        Category = Request.QueryString["c"].ToString();

        Util.SecureQueryString(RecipeName);
        Util.SecureQueryString(Category);
    }

    public void SendingRecipe(Object s, EventArgs e)
    {
        if (!string.IsNullOrEmpty(Request.QueryString["id"]) 
            && !string.IsNullOrEmpty(Request.QueryString["n"]) 
            && !string.IsNullOrEmpty(Request.QueryString["c"]))
        {

            EmailTemplate SendeMail = new EmailTemplate();

            SendeMail.ItemID = (int)Util.Val(Request.QueryString["id"]);
            SendeMail.ItemName = Request.QueryString["n"].ToString();
            SendeMail.Category = Request.QueryString["c"].ToString();

            SendeMail.SenderName = Util.FormatTextForInput(Request.Form["txtFromName"]);
            SendeMail.SenderEmail = Util.FormatTextForInput(Request.Form["txtFromEmail"]);
            SendeMail.RecipientEmail = Util.FormatTextForInput(Request.Form["txtToEmail"]);
            SendeMail.RecipientName = Util.FormatTextForInput(Request.Form["toname"]);

            SendeMail.SendEmailRecipeToAFriend();

            Panel1.Visible = false;

            lblsentmsg.Text = "<div style='text-align: center; border: solid 1px #800000; padding: 8px; margin-left: 25px; margin-right: 25px;'><span class='content12'>Your message has been sent to (<b>" + SendeMail.RecipientEmail + "</b>).</span></div>";

            SendeMail = null;
        }

    }
}
