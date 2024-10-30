using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Text;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using XDRecipe.Common.Utilities;
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;
using XDRecipe.BL.Providers.Events;

public partial class eventdetail : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(Request.QueryString["id"]) && Utility.IsNumeric(Request.QueryString["id"]))
        {
            int ID = int.Parse(Request.QueryString["id"]);

            ProviderGetPublicEventDetail PubEventDetail = new ProviderGetPublicEventDetail();
            PubEventDetail.FillUp(ID);

            StringBuilder mySB = new StringBuilder();
            mySB.Append("<div style='margin: 6px; line-height: 16px;'>");
            mySB.Append("<span style='font-size: 16px;'>");
            mySB.Append("<b>" + PubEventDetail.EventTitle + "</b>");
            mySB.Append("</span><br>");
            mySB.Append("<b>Type:</b>&nbsp;");
            mySB.Append(PubEventDetail.EventType);

            if (PubEventDetail.EventType == "Appointment" || PubEventDetail.EventType == "Meeting")
            {
                mySB.Append("<br>");
                mySB.Append("<b>Time:</b>&nbsp;");
                mySB.Append(PubEventDetail.AppMeetingStartTime);
                mySB.Append(" - ");
                mySB.Append(PubEventDetail.AppMeetingEndTime);
            }

            mySB.Append("</div>");
            mySB.Append("<div style='margin: 6px'>");
            mySB.Append(PubEventDetail.EventDetails.ToString());
            mySB.Append("<br><br>");
            mySB.Append("<b>By:</b>&nbsp;");
            mySB.Append("<a title='View profile' style='color: #007AF4;' href=userprofile.aspx?uid=" + PubEventDetail.UID + ">" + PubEventDetail.UserName + "</a>");
            mySB.Append("</div>");

            if (Authentication.IsUserAuthenticated && (PubEventDetail.UID == UserIdentity.UserID))
            {
                mySB.Append("<div style='margin: 8px; text-align: left;'>");
                mySB.Append("<a title='Edit my event' href='editevent.aspx' style='color: #007AF4;'>");
                mySB.Append("Edit");
                mySB.Append("</a>&nbsp;&nbsp;&nbsp;&nbsp;");
                mySB.Append("<a title='Delete my event' href='deleteevent.aspx' style='color: #007AF4;'>");
                mySB.Append("Delete");
                mySB.Append("</a>");
                mySB.Append("</div");
            }

            int NumDaysDiff = Utility.DateDiff(PubEventDetail.StartDate, PubEventDetail.EndDate);

            if (NumDaysDiff > 1)
            {
                lbldate.Text += "&nbsp;" + Utility.GetDayNameAbbrev(PubEventDetail.StartDate.DayOfWeek.ToString());
                lbldate.Text += ", " + Utility.GetMonthNameOrAbbrev(int.Parse(PubEventDetail.StartDate.Month.ToString()), true);
                lbldate.Text += " " + int.Parse(PubEventDetail.StartDate.Day.ToString());
                lbldate.Text += " - " + Utility.GetDayNameAbbrev(PubEventDetail.EndDate.DayOfWeek.ToString());
                lbldate.Text += ", " + Utility.GetMonthNameOrAbbrev(int.Parse(PubEventDetail.EndDate.Month.ToString()), true);
                lbldate.Text += "" + int.Parse(PubEventDetail.EndDate.Day.ToString());
                lbleventdetail.Text = mySB.ToString();
            }
            else
            {
                lbldate.Text += "&nbsp;" + Utility.GetDayNameAbbrev(PubEventDetail.StartDate.DayOfWeek.ToString());
                lbldate.Text += ", " + Utility.GetMonthNameOrAbbrev(int.Parse(PubEventDetail.StartDate.Month.ToString()), true);
                lbldate.Text += " " + int.Parse(PubEventDetail.StartDate.Day.ToString());
                lbleventdetail.Text = mySB.ToString();
            }

            mySB = null;
            PubEventDetail = null;
        }
    }
}
