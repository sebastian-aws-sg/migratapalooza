using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using XDRecipe.UI;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Security;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.CookBooks;
using XDRecipe.BL.Providers.FriendList;
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;
using System.Web.UI.HtmlControls;

public partial class admin_deleteduserlogview : BasePageAdmin
{
    protected void Page_Load(object sender, EventArgs e)
    {
        lblusername.Text = "Welcome Admin:&nbsp;" + UserIdentity.AdminUsername;

        DeletedUserLog.DataSource = Blogic.ActionProcedureDataProvider.ViewDeletedUsersLog;
        DeletedUserLog.DataBind();
    }
}
