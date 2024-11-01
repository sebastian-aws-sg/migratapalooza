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

public partial class exceptionlogmanager : BasePageAdmin
{
    protected void Page_Load(object sender, EventArgs e)
    {
        lblusername.Text = "Welcome Admin:&nbsp;" + UserIdentity.AdminUsername;

        ExceptionLog.DataSource = Blogic.ActionProcedureDataProvider.GetLast50ExceptionErrorLog;
        ExceptionLog.DataBind();
    }

    public void Delete_Exception(Object sender, CommandEventArgs e)
    {
        if (e.CommandName == "Delete")
        {
            inputCsv.Value = "";
            string CsvID = string.Empty;

            foreach (RepeaterItem ri in ExceptionLog.Items)
            {
                CheckBox chkID = (CheckBox)ri.FindControl("chkID");
                Label ExID = (Label)ri.FindControl("ExID");

                if (chkID != null)
                {
                    if (chkID.Checked)
                    {
                        inputCsv.Value += ExID.Text.Trim() + ",";
                    }
                }
            }

            inputCsv.Value = inputCsv.Value.TrimEnd(',');

            if (inputCsv.Value.Length > 0)
            {
                Blogic.DeleteException(inputCsv.Value);
                Response.Redirect("confirmexceptiondelete.aspx?id=" + inputCsv.Value);
            }
        }
    }
}
