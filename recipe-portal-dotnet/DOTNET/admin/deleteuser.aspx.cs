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

public partial class admin_deleteuser : BasePageAdmin
{
    protected void Page_Load(object sender, EventArgs e)
    {
        lblusername.Text = "Welcome Admin:&nbsp;" + UserIdentity.AdminUsername;
        lblusernametodelete.Text = "You are about to delete user #: " + Request.QueryString["uid"];
    }

    public void DeleteUser_Click(object sender, EventArgs e)
    {
        int UserID = int.Parse(Request.QueryString["uid"]);
        string Reason = Request.Form["Reason"];

        //Cannot delete the Administrator account. You can update the information such as password, email and name, city and so on...
        if (UserID != 1)
        {
            ProviderUserDetails users = new ProviderUserDetails();
            users.FillUp(UserID);

            Blogic.DeleteUserLog(UserID, users.Username, Reason);

            try
            {
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetUserPhotoByUserID(UserID);

                dr.Read();

                if (dr["Photo"] != DBNull.Value)
                {
                    System.IO.File.Delete(Server.MapPath(GetUserImage.ImagePathForUserPhotoForAdmin + dr["Photo"].ToString()));
                }

                dr.Close();
            }
            catch
            {
            }

            UserRepository user = new UserRepository();
            user.UID = UserID;

            if (user.Delete(user) != 0)
            {
                JSLiteral.Text = "Error occured while processing.";
                return;
            }

            SendAnEmailNotificationToTheUser(users, Reason);

            user = null;
            users = null;

            Response.Redirect("confirmusersuspenddeleteedit.aspx?mode=Delete&uid=" + UserID);
        }
    }

    private void SendAnEmailNotificationToTheUser(ProviderUserDetails users, string Reason)
    {
        EmailTemplate SendeMail = new EmailTemplate();
        SendeMail.SendDeleteAccountNotification(users.Email, users.Username, Reason);
        SendeMail = null;
    }
}
