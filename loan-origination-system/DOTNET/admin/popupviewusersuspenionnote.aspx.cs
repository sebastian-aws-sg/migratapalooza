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
using XDRecipe.BL;
using XDRecipe.UI;

public partial class admin_popupviewusersuspenionnote : BasePageAdmin
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(Request.QueryString["uid"]))
        {
            int UserID = int.Parse(Request.QueryString["uid"]);

            SuspenionLogNote.DataSource = Blogic.ActionProcedureDataProvider.GetUsersSuspenionNote(UserID);
            SuspenionLogNote.DataBind();
        }
    }
}
