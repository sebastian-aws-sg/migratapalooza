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
using XDRecipe.BL.Providers;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;

public partial class alphaletter : System.Web.UI.UserControl
{
    protected void Page_Load(object sender, EventArgs e)
    {
        lblletter.Text = "Recipe A-Z:";
        lblalphaletter.Text = AlphabetLink.BuildLink("recipename.aspx?letter=", "letter", "Browse all recipe starting with letter", "&nbsp;&nbsp;");
    }
}
