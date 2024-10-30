#region XD World Recipe V 2.8
// FileName: popupviewuserssavedrecipepublishedbyme.cs
// Author: Dexter Zafra
// Date Created: 3/29/2009
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
using XDRecipe.Common;
using XDRecipe.Security;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.CookBooks;
using XDRecipe.BL.Providers.User;

public partial class popupviewuserssavedrecipepublishedbyme : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            HideContentIfNotLogin.Visible = true;

            if (!string.IsNullOrEmpty(Request.QueryString["uid"]) && Utility.IsNumeric(Request.QueryString["uid"]))
            {
                int UserID = (int)Util.Val(Request.QueryString["uid"]);
                LoadData(UserID);
            }
        }
        else
        {
            HideContentIfNotLogin.Visible = false;
            lblyouarenotlogin.Visible = true;
            lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to My Account page. Please login to view your Account Page.</div>";
        }
    }

    private void LoadData(int UserID)
    {
        UserSavedRecipeCookBookPublishedByme.DataSource = Blogic.ActionProcedureDataProvider.GetViewUsersRecipeSavedPublishedByMe(UserIdentity.UserID, UserID);
        UserSavedRecipeCookBookPublishedByme.DataBind();
    }
}
