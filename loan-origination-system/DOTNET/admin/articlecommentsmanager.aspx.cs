#region XD World Recipe V 2.8
// FileName: articlecommentsmanager.cs
// Author: Dexter Zafra
// Date Created: 7/3/2008
// Website: www.ex-designz.net
#endregion
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
using System.Data.SqlClient;
using XDRecipe.UI;
using XDRecipe.BL;
using XDRecipe.BL.Providers;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.User;

public partial class admin_articlecommentmanager : BasePageAdmin
{
    private const string ASCENDING = " ASC";
    private const string DESCENDING = " DESC";

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            //Get admin username from the sessioan variable and place it in the label.
            lblusername.Text = "Welcome Admin:&nbsp;" + UserIdentity.AdminUsername;

            lbCountArticle.Text = "Total Approved Article: " + Blogic.ActionProcedureDataProvider.ArticleCountAll;

            //Hide comment editing form
            Panel1.Visible = false;
            countcommentlink.Enabled = false;

            lblCurrentSort.Text = "Desc";
            lblSortColName.Text = "Sorted by default: Date -";

            GetTop25UsersWhoCommentedARecipe();

            BindData();

        }
        else
        {
            //Show comment editing form
            Panel1.Visible = true;
            countcommentlink.Enabled = true;
        }
    }

    private DataTable dt
    {
        get
        {
            string strAuthor = Request.QueryString["find"];
            return Blogic.ActionProcedureDataProvider.AdminGetArticleComments(strAuthor);
        }
    }

    //Data binding
    private void BindData()
    {
        DataView dv = new DataView(dt);

        lbCountComments.Text = "Total Comments: " + dv.Count.ToString();

        ArticleComments.DataSource = dv;
        ArticleComments.DataBind();

        dv = null;
    }

    private void GetTop25UsersWhoCommentedARecipe()
    {
        Top25UsersWhoCommentAnArticle.DataSource = Blogic.ActionProcedureDataProvider.GetTop25UsersWhoCommentAnArticle;
        Top25UsersWhoCommentAnArticle.DataBind();
    }

    public void SearchUser_Click(object sender, EventArgs e)
    {
        Response.Redirect("articlecommentsmanager.aspx?find=" + Request.Form["searcinput"]);
    }

    //Handles update comment
    public void Update_Comments(Object s, EventArgs e)
    {
        ArticleCommentsRepository comment = new ArticleCommentsRepository();

        comment.ID = int.Parse(Request.Form["KeyIDs"]);
        comment.Comments = Request.Form["Comments"];

        if (comment.Update(comment) != 0)
        {
            JSLiteral.Text = "Error occured while processing your submit.";
            return;
        }

        comment = null;

        Response.Redirect("articlecommentconfirmation.aspx?mode=update&id=" + Request.Form["KeyIDs"]);
    }

    //Handle the delete button click event
    public void ArticleComments_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if ((e.CommandName == "DeleteComment"))
        {
            //Passing multiple command argument separated by comma so we can split it.
            string[] commandArgsDelete = e.CommandArgument.ToString().Split(new char[] { ',' });
            int COMID = int.Parse(commandArgsDelete[0].ToString()); // Get the Comment ID
            int RecID = int.Parse(commandArgsDelete[1].ToString()); // Get the Recipe ID

            ArticleCommentsRepository comment = new ArticleCommentsRepository();

            comment.ID = COMID;
            comment.RECID = RecID;

            comment.Delete(comment);

            comment = null;

            Response.Redirect("articlecommentconfirmation.aspx?mode=del&id=" + COMID);
        }

        if ((e.CommandName == "EditComment"))
        {
            //Passing multiple command argument separated by comma so we can split it.
            string[] commandArgsEdit = e.CommandArgument.ToString().Split(new char[] { ',' });
            int CID = int.Parse(commandArgsEdit[0].ToString()); // Get the Comment ID
            string strComment = commandArgsEdit[1].ToString(); // Get the Comment

            Panel1.Visible = true;

            KeyIDs.Value = CID.ToString();
            Comments.Text = strComment;
            lblheaderform.Text = "Updating Comment #:&nbsp;" + CID;

        }
        else
        {
            Panel1.Visible = false;
        }
    }

    public void DeleteAllSelectedItems_Click(object sender, EventArgs e)
    {
        Panel1.Visible = false;

        for (int i = 0; i < ArticleComments.Rows.Count; i++)
        {
            CheckBox chkID = ArticleComments.Rows[i].FindControl("chkDelete") as CheckBox;

            if (chkID.Checked)
            {
                int ComID = int.Parse(ArticleComments.Rows[i].Cells[3].Text.ToString());
                int RecID = int.Parse(ArticleComments.Rows[i].Cells[4].Text.ToString());

                ArticleCommentsRepository comment = new ArticleCommentsRepository();

                comment.ID = ComID;
                comment.RECID = RecID;

                comment.Delete(comment);
            }
        }

        Response.Redirect("articlecommentconfirmation.aspx?mode=DeleteAll");
    }

    //Handles page change links - paging system
    public void NewPage(object sender, GridViewPageEventArgs e)
    {
        Panel1.Visible = false;
        ArticleComments.PageIndex = e.NewPageIndex;
        BindData();
    }

    public void ArticleComments_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        //First, make sure we're not dealing with a Header or Footer row
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            string UserName = (string)DataBinder.Eval(e.Row.DataItem, "Author");

            e.Row.Cells[5].Text = "<img src='../images/user-icon.gif'>&nbsp;<a title='View profile in membership manager.' href=membershipmanagers.aspx?search=" + UserName.Trim() + ">" + DataBinder.Eval(e.Row.DataItem, "Author") + "</a>";

            //Data row mouseover changecolor
            e.Row.Attributes.Add("onmouseover", "this.style.backgroundColor='#F4F9FF'");
            e.Row.Attributes.Add("onmouseout", "this.style.backgroundColor='#ffffff'");
        }
    }

    public SortDirection GridViewSortDirection
    {
        get
        {
            if (ViewState["sortDirection"] == null)
                ViewState["sortDirection"] = SortDirection.Ascending;

            return (SortDirection)ViewState["sortDirection"];
        }

        set { ViewState["sortDirection"] = value; }
    }

    public void ArticleComments_Sorting(object sender, GridViewSortEventArgs e)
    {
        string sortExpression = e.SortExpression;

        if (GridViewSortDirection == SortDirection.Ascending)
        {
            GridViewSortDirection = SortDirection.Descending;
            SortGridView(sortExpression, DESCENDING);
            lblCurrentSort.Text = "Desc";
            lblSortColName.Text = "Sorted by: " + e.SortExpression.ToString() + " -";
        }
        else
        {
            GridViewSortDirection = SortDirection.Ascending;
            SortGridView(sortExpression, ASCENDING);
            lblCurrentSort.Text = "Asc";
            lblSortColName.Text = "Sorted by: " + e.SortExpression.ToString() + " -";
        }
    }

    private void SortGridView(string sortExpression, string direction)
    {
        DataView dv = new DataView(dt);

        dv.Sort = sortExpression + direction;

        ArticleComments.DataSource = dv;
        ArticleComments.DataBind();

        dv = null;
    }
}