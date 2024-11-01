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

public partial class admin_confirmusersuspenddeleteedit : BasePageAdmin
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(Request.QueryString["mode"]) && !string.IsNullOrEmpty(Request.QueryString["uid"]))
        {
            string mode = Request.QueryString["mode"];
            int UserID = int.Parse(Request.QueryString["uid"]);

            switch (mode)
            {
                case "Delete":
                    lblconfirmmsg.Text = "User ID #" + UserID + " has been successfully deleted.";
                    break;

                case "Edit":
                    lblconfirmmsg.Text = "User ID #" + UserID + " profile has been successfully updated.";
                    break;

                case "Suspend":
                    lblconfirmmsg.Text = "User ID #" + UserID + " has been successfully suspended. Email notification has been sent to the user.";
                    break;

                case "ReInstate":
                    lblconfirmmsg.Text = "User ID #" + UserID + " has been successfully reinstated. Email notification has been sent to the user.";
                    break;
            }
            
        }
    }
}
