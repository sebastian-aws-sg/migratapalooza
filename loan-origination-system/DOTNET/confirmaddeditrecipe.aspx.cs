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

public partial class confirmaddeditrecipe : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(Request.QueryString["mode"]))
        {
            if (Request.QueryString["mode"] == "Updated")
            {
                lblconfirmmsg.Text = "Recipe " + Server.HtmlEncode(Request.QueryString["recipename"] + " has been successfully " + Server.HtmlEncode(Request.QueryString["mode"]));
                Response.AppendHeader("Refresh", "3; url=recipedetail.aspx?id=" + int.Parse(Request.QueryString["id"]));
            }
            else if (Request.QueryString["mode"] == "Added")
            {
                lblconfirmmsg.Text = "Your Recipe " + Server.HtmlEncode(Request.QueryString["recipename"] + " has been successfully " + Server.HtmlEncode(Request.QueryString["mode"])) + "<br>and is waiting for reveiw, and if approve, it will be posted within 24 hrs.";
                Response.AppendHeader("Refresh", "3; url=default.aspx");
            }
        }
    }
}
