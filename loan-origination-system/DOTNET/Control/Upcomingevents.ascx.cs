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
using XDRecipe.BL.Providers.Events;

public partial class Upcomingevents : System.Web.UI.UserControl
{
    /// <summary>
    /// Format date to "Jan. 1, 2009"
    /// </summary>
    public string CustomDateFormat(object o)
    {
        string newdateformat = Utility.FormatDate(Convert.ToDateTime(o));
        return newdateformat;
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (IsListView)
        {
            PanelListViewSide.Visible = true;
            LoadUpComingEvents();
            LoadRecentEvents();
        }
    }

    private void LoadUpComingEvents()
    {
        ProviderGetUpComingPublicEvents.Param("All");

        int Count = ProviderGetUpComingPublicEvents.RecordCount;

        if (Count == 0)
        {
            lbcountupcomingevent.Text = "There are No Upcoming Events";
        }
        else
        {
            lbcountupcomingevent.Text = "There are (" + Count + ") Upcoming Events";
            UpComingEventsControl.DataSource = ProviderGetUpComingPublicEvents.UpComingPublicEvents();
            UpComingEventsControl.DataBind();
        }
    }

    private void LoadRecentEvents()
    {
        ProviderGetPast30DaysPublicEvents.Param("All");

        int Count = ProviderGetPast30DaysPublicEvents.RecordCount;

        if (Count == 0)
        {
            lblrecentevents.Text = "There are No Recent Events";
        }
        else
        {
            lblrecentevents.Text = "There are (" + Count + ") Recent Events";
            RecentEvents.DataSource = ProviderGetPast30DaysPublicEvents.Past30DaysPublicEvents();
            RecentEvents.DataBind();
        }
    }

    private bool IsListView
    {
        get
        {
            if (!string.IsNullOrEmpty(Request.QueryString["mode"]) && Request.QueryString["mode"] == "listview")
                return false;
            else
                return true;
        }
    }
}
