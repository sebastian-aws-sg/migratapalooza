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

public partial class popupgetuserswhohasnotactivatedaccount : BasePageAdmin
{
    protected void Page_Load(object sender, EventArgs e)
    {
        UserUnActivatedAccount.DataSource = Blogic.ActionProcedureDataProvider.GetTop50UsersWhoHasNotActivatedAnAccount;
        UserUnActivatedAccount.DataBind();
    }

    //Handle button click event
    public void SendActivation_Click(Object sender, CommandEventArgs e)
    {
        if (e.CommandName == "SendActivation")
        {
            foreach (RepeaterItem ri in UserUnActivatedAccount.Items)
            {
                CheckBox chkID = (CheckBox)ri.FindControl("chkID");
                Label User_name = (Label)ri.FindControl("User_name");
                Label User_email = (Label)ri.FindControl("User_email");
                Label User_guid = (Label)ri.FindControl("User_guid");
                if (chkID != null)
                {
                    if (chkID.Checked)
                    {
                        string UserName = User_name.Text.Trim();
                        string UserEmail = User_email.Text.Trim();
                        string UserGuid = User_guid.Text.Trim();

                        //Unfortunately, sending multiple emails to different recipients does not work in this code.
                        //If you can figure out other solution, just drop me an email.
                        //Instantiate emailtemplate object
                        EmailTemplate SendeMail = new EmailTemplate();

                        SendeMail.RecipientEmail = UserEmail;

                        //Send the activation link
                        SendeMail.SendActivationLink(UserName, UserGuid);

                        SendeMail = null;

                        Response.Redirect("confirmsendactivation.aspx");
                    }
                }
            }
        }
    }
}
