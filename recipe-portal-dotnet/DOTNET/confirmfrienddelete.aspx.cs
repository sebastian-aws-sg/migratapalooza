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

public partial class confirmfrienddelete : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(Request.QueryString["un"]))
        {
            string UserName = Request.QueryString["un"];

            lblheader.Text = "Removed Success";
            lblsuccess.Text = "<b>" + UserName + "</b> friend had been removed from your Friends List.";
            lblwait.Text = "Please wait, you will be redirected in 3 seconds back to your Friends List.";
        }
    }
}
