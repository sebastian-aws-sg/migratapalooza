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
using XDRecipe.BL.Providers.FriendList;
using XDRecipe.UI;

public partial class admin_popupviewuserfriendslist : BasePageAdmin
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(Request.QueryString["uid"]) && !string.IsNullOrEmpty(Request.QueryString["uname"]))
        {
            int UserID = int.Parse(Request.QueryString["uid"]);
            string UserName = Request.QueryString["uname"];

            lblusernameheader.Text = UserName + "'s Friends";

            ProviderFriendsList MyFriends = new ProviderFriendsList(UserID, 50);

            MyFriendsList.DataSource = MyFriends.GetFriendsList();
            MyFriendsList.DataBind();

            lblcounter.Text = "<img src='../images/friendlisticon.gif' align='absmiddle'>&nbsp;&nbsp;" + MyFriends.TotalCount + " friends in Friends List.";

            MyFriends = null;
        }
    }
}
