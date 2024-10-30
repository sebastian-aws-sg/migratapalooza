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

public partial class confirmcookbookdelete : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(Request.QueryString["rn"]))
        {
            string RecipeName = Request.QueryString["rn"];

            lblheader.Text = "Removed Success";
            lblsuccess.Text = "<b>" + RecipeName + "</b> recipe had been removed from your CookBook.";
            lblwait.Text = "Please wait, you will be redirected in 3 seconds back to your CookBook.";
        }
    }
}
