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

public partial class usersearchtab : System.Web.UI.UserControl
{
    public void SearchUserbasic_Click(object sender, EventArgs e)
    {
        Response.Redirect("searchuser.aspx?input=" + Request.Form[usersearcbasicinput.UniqueID] + "&condition=" + Int32.Parse(Request.Form[ddlSearchUser.UniqueID]));
    }
}
