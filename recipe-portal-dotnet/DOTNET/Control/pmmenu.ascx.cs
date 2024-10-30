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
using XDRecipe.BL;
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;
using XDRecipe.BL.Providers.PrivateMessages;

public partial class pmmenu : System.Web.UI.UserControl
{
    public int UserSentPMCount;
    public int CountAllPMInTheSystem;
    public int CountPMSentToday;
    public int CountUserUnreadMsg;
    public int CountBlockedUsers;
    public int CountPMinTrash;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            PanelShowPMMenuIfLogin.Visible = true;

            int UserID = UserIdentity.UserID;

            UserSentPMCount = Blogic.CountUserSentPM(UserID);
            CountUserUnreadMsg = Blogic.GetUserNewPrivateMessageCount(UserID);
            CountBlockedUsers = Blogic.CountPMBlocklistedUsers(UserID);
            CountPMinTrash = Blogic.GetPMinTrashByUserID(UserID);

            EmptyTrashLink(CountPMinTrash);

            ProviderPMStatistic PMStatistic = new ProviderPMStatistic();
            PMStatistic.FillUp();

            CountAllPMInTheSystem = PMStatistic.TotalPMCount;
            CountPMSentToday = PMStatistic.CountSentPMToday;

            PMStatistic = null;
        }
    }

    private void EmptyTrashLink(int Counter)
    {
        if (Counter != 0)
        {
            lbemptytrash.Text = "<a class='content01 thickbox' href='#TB_inline?height=75&width=350&inlineId=confirmModalEmptyTrash&modal=true'>empty</a>";
            lbemptytrash.Attributes.Add("onmouseover", "Tip('Delete all messages in trash permanently.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lbemptytrash.Attributes.Add("onmouseout", "UnTip()");
        }
        else
        {
            lbemptytrash.Text = "<a class='content01' href='javascript:void(0)'>empty</a>";
            lbemptytrash.Attributes.Add("onclick", "csscody.alert('There is no messages in your PM trash.');return false;");
            lbemptytrash.Attributes.Add("onmouseover", "Tip('Empty trash', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lbemptytrash.Attributes.Add("onmouseout", "UnTip()");
        }
    }
}
