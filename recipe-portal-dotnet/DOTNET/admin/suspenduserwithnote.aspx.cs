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
using XDRecipe.BL.Providers.FriendList;
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;

public partial class admin_suspenduserwithnote : BasePageAdmin
{
    protected void Page_Load(object sender, EventArgs e)
    {
        string UserName = Request.QueryString["uname"];

        //Get admin username from the sessioan variable and place it in the label.
        lblusername.Text = "Welcome Admin:&nbsp;" + UserIdentity.AdminUsername;

        lblusernamesuspend.Text = "Suspending: <b>" + UserName + "</b>";

        if (!string.IsNullOrEmpty(Request.QueryString["uid"]))
        {
            int UserID = int.Parse(Request.QueryString["uid"]);

            SuspenionLogNote.DataSource = Blogic.ActionProcedureDataProvider.GetUsersSuspenionNote(UserID);
            SuspenionLogNote.DataBind();
        }
    }

    public void Suspend_User(object s, EventArgs e)
    {
        if (!string.IsNullOrEmpty(Request.QueryString["uid"]))
        {
            int UserID = int.Parse(Request.QueryString["uid"]);

            //Cannot suspend the Administrator account.
            if (UserID != 1)
            {
                string Type = Request.Form["Type"];
                string Note = Request.Form["Note"];

                Blogic.SuspendUser(UserID, Type, Note);

                ProviderUserDetails user = new ProviderUserDetails();

                user.FillUp(UserID);

                EmailTemplate SendeMail = new EmailTemplate();

                //Flag = 1 = Suspension email notice.
                SendeMail.SendAccountSuspensionReinstateEmail(user.Email, user.Username, Type, 1);

                SendeMail = null;
                user = null;

                Response.Redirect("confirmusersuspenddeleteedit.aspx?mode=Suspend&uid=" + UserID);
            }
        }

    }
}
