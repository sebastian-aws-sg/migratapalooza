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
using XDRecipe.Common.Utilities;

public partial class confirmupdateprivatemsg : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(Request.QueryString["mode"]))
        {
            string Mode = Server.UrlEncode(Request.QueryString["mode"]);

            switch (Mode)
            {
                case "delete":
                    lblsuccess.Text = "Private Messages #: " + Server.UrlEncode(Request.QueryString["pid"]) + " Has Been Successfully Deleted";
                    break;

                case "send":
                    lblsuccess.Text = "Your Private Message to " + Server.UrlEncode(Request.QueryString["username"]) + " Has Been Sent Successfully";
                    break;

                case "reply":
                    lblsuccess.Text = "Your Private Message Reply to " + Server.UrlEncode(Request.QueryString["username"]) + " Has Been Sent Successfully";
                    break;

                case "read":
                    lblsuccess.Text = "Private Message #: " + Server.UrlEncode(Request.QueryString["pid"]) + " Has Been Successfully Marked as Read";
                    break;

                case "unread":
                    lblsuccess.Text = "Private Message #: " + Server.UrlEncode(Request.QueryString["pid"]) + " Has Been Successfully Marked as UnRead";
                    break;

                case "block":
                    lblsuccess.Text = Server.UrlEncode(Request.QueryString["username"]) + " Has Been Successfully Blocked.";
                    break;

                case "remove":
                    lblsuccess.Text = Server.UrlEncode(Request.QueryString["username"]) + " Has Been Successfully Removed From Your PM Block List.";
                    break;
            }
        }
    }
}
