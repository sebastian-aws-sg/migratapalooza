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
using XDRecipe.BL.Providers.User;

public partial class popuplast25userswhoaddedrecipeincookbook : BasePageAdmin
{
    protected void Page_Load(object sender, EventArgs e)
    {
        UserCookBook.DataSource = Blogic.ActionProcedureDataProvider.GetLast25UsersWhoAddedRecipeInCookBook;
        UserCookBook.DataBind();
    }
}
