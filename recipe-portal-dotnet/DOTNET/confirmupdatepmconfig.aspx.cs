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

public partial class confirmupdatepmconfig : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        //Coming from MyAccount.aspx
        if (!string.IsNullOrEmpty(Request.QueryString["mode"]) && !string.IsNullOrEmpty(Request.QueryString["val"]))
        {
            string mode = Request.QueryString["mode"];

            switch (mode)
            {
                case "receivepm":
                    Response.Redirect("myaccount.aspx?method=receivepm&setting=" + Request.QueryString["val"]);
                    break;

                case "pmemailalert":
                    Response.Redirect("myaccount.aspx?method=pmemailalert&setting=" + Request.QueryString["val"]);
                    break;
            }
        }
    }
}
