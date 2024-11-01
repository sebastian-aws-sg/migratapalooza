#region XD World Recipe V 2.8
// FileName: articlemanager.cs
// Author: Dexter Zafra
// Date Created: 5/29/2008
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
using XDRecipe.BL.Providers.Article;

public partial class admin_articlemanager : BasePageAdmin
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            //Get admin username from the sessioan variable and place it in the label.
            lblusername.Text = "Welcome Admin:&nbsp;" + UserIdentity.AdminUsername;

            lbCountArticle.Text = "Total Approved Article: " + Blogic.ActionProcedureDataProvider.ArticleCountAll;

            //Return Update Category List
            AdminUpdateArtCatList.DataSource = Blogic.ActionProcedureDataProvider.GetArticleCategoryList;
            AdminUpdateArtCatList.DataBind();

            //Return Add New Category List
            ArtCategoryList.DataSource = Blogic.ActionProcedureDataProvider.GetArticleCategoryList;
            ArtCategoryList.DataBind();

            UnApprovedArticleList.DataSource = ProviderUnapprovedArticles.GetUnApprovedArticles();
            UnApprovedArticleList.DataBind();

            lblcountunapproverecipe.Text = "There are (" + Blogic.GetUnApprovedArticleCount +") articles waiting for your review and approval.";

            ShowEditArticleListing();

            //Hide comment editing form
            Panel2.Visible = false;

            if (Request.QueryString["editcatid"] != null)
            {
                CategoryName.Text = Request.QueryString["catname"];
                CategoryID.Value = Request.QueryString["editcatid"];
                Panel2.Visible = true;
                Panel3.Visible = false;
                AddNewCat.Visible = false;
                lblheaderform.Text = "Updating Article Category ID# " + Request.QueryString["editcatid"];
            }

            LastUpdatedArticles.DataSource = Blogic.ActionProcedureDataProvider.GetLast10UpdatedArticle;
            LastUpdatedArticles.DataBind();
        }
        else
        {
            //Show comment editing form
            Panel2.Visible = true;
        }
    }

    //Handles show edit article listing
    private void ShowEditArticleListing()
    {
        //Instantiate validation
        Utility Util = new Utility();

        int CatId;
        CatId = (int)Util.Val(Request.QueryString["catid"]);

        if (string.IsNullOrEmpty(this.Request.QueryString["catid"]))
        {
            Panel1.Visible = false;
        }
        else
        {
            Panel1.Visible = true;

            try
            {
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetArticleCategory(CatId, 3); //3 = Category OrderBy Name.

                dr.Read();
                lbcatname.Text = dr["CAT_NAME"].ToString();
                dr.Close();

                ArticleCat.DataSource = Blogic.ActionProcedureDataProvider.GetArticleCategory(CatId, 3); //3 = Category OrderBy Name.
                ArticleCat.DataBind();
            }
            catch
            {
                Error.Text = "No Record Found.";
                return;
            }

            //Release allocated memory
            Util = null;
        }
    }

    public void UnApprovedArticleList_ItemDataBound(Object s, RepeaterItemEventArgs e)
    {
        // This event is raised for the header, the footer, separators, and items.
        // Execute the following logic for Items and Alternating Items.
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            LinkButton delbuttonunapprove = (LinkButton)(e.Item.FindControl("delbuttonunapprove"));
            LinkButton approvebutton = (LinkButton)(e.Item.FindControl("approvebutton"));
            HyperLink editunapprovebutton = (HyperLink)(e.Item.FindControl("editunapprovebutton"));

            delbuttonunapprove.Attributes["onclick"] = "javascript:return confirm('Are you sure you want to delete this articles. " + DataBinder.Eval(e.Item.DataItem, "Title") + " Article, ID#  " + DataBinder.Eval(e.Item.DataItem, "ID") + "?')";
            delbuttonunapprove.Text = "<img border='0' src='../images/icon_delete.gif'>";
            editunapprovebutton.Text = "<img src='../images/icon_pencil.gif' alt='Edit' border='0'>";
            editunapprovebutton.NavigateUrl = "updatearticle.aspx?aid=" + DataBinder.Eval(e.Item.DataItem, "ID");
            approvebutton.Text = "<img src='../images/adminapproval_icon_smll.gif' alt='Edit' border='0'>";
            approvebutton.Attributes.Add("onmouseover", "Tip('Approve <b>" + DataBinder.Eval(e.Item.DataItem, "Title") + "</b> article.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            approvebutton.Attributes.Add("onmouseout", "UnTip()");
            delbuttonunapprove.Attributes.Add("onmouseover", "Tip('Delete (<b>" + DataBinder.Eval(e.Item.DataItem, "Title") + "</b>) article.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            delbuttonunapprove.Attributes.Add("onmouseout", "UnTip()");
            editunapprovebutton.Attributes.Add("onmouseover", "Tip('Edit (<b>" + DataBinder.Eval(e.Item.DataItem, "Title") + "</b>) article.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            editunapprovebutton.Attributes.Add("onmouseout", "UnTip()");
        }
    }

    //Handle delete button click event
    public void ApprovedDelete_Article(Object sender, RepeaterCommandEventArgs e)
    {
        if ((e.CommandName == "Delete"))
        {
            ArticleRepository Article = new ArticleRepository();

            string[] commandArgsDelete = e.CommandArgument.ToString().Split(new char[] { ',' });
            Article.ID = int.Parse(commandArgsDelete[0].ToString()); // Article ID
            int User_ID = int.Parse(commandArgsDelete[1].ToString()); // Get the UserID

            Caching.PurgeCacheItems("Newest_Articles");
            Caching.PurgeCacheItems("ArticleCategory_SideMenu");
            Caching.PurgeCacheItems("Last5_ArticlePublishedByUser_" + User_ID);

            //Perform delete
            Article.Delete(Article);

            //Release allocated memory
            Article = null;

            //Redirect to confirm delete page
            Response.Redirect("articlemanager.aspx");
        }

        if ((e.CommandName == "Approved"))
        {
            string[] commandArgsApproved = e.CommandArgument.ToString().Split(new char[] { ',' });
            int ArticleID = int.Parse(commandArgsApproved[0].ToString()); // Get the Article ID
            int UserID = int.Parse(commandArgsApproved[1].ToString()); // Get the UserID

            //Refresh cache
            Caching.PurgeCacheItems("Newest_Articles");
            Caching.PurgeCacheItems("ArticleCategory_SideMenu");
            Caching.PurgeCacheItems("Last5_ArticlePublishedByUser_" + UserID);

            int Err = Blogic.ActionProcedureDataProvider.FinalizeAddArticle(ArticleID);

            // If error occured, stop further processing and notify user.
            if (Err != 0)
            {
                JSLiteral.Text = "Error occured while processing your submit.";
                return;
            }

            //Redirect to confirm delete page
            Response.Redirect("articlemanager.aspx");
        }
    }

    public void ArtCategoryList_ItemDataBound(Object s, RepeaterItemEventArgs e)
    {
        // This event is raised for the header, the footer, separators, and items.
        // Execute the following logic for Items and Alternating Items.
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            LinkButton delbutton2 = (LinkButton)(e.Item.FindControl("delbutton2"));
            HyperLink editbutton2 = (HyperLink)(e.Item.FindControl("editbutton2"));

            delbutton2.Attributes["onclick"] = "javascript:return confirm('Are you sure you want to delete this category and all its associated articles. Note: you will loss all articles belong in this category. " + DataBinder.Eval(e.Item.DataItem, "CAT_NAME") + " Category, ID#  " + DataBinder.Eval(e.Item.DataItem, "CAT_ID") + "?')";
            delbutton2.Text = "<img border='0' src='../images/icon_delete.gif'>";
            editbutton2.Text = "<img src='../images/icon_pencil.gif' alt='Edit' border='0'>";
            editbutton2.Attributes.Add("onmouseover", "Tip('Edit <b>" + DataBinder.Eval(e.Item.DataItem, "CAT_NAME") + "</b> category.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            editbutton2.Attributes.Add("onmouseout", "UnTip()");
            delbutton2.Attributes.Add("onmouseover", "Tip('Delete (<b>" + DataBinder.Eval(e.Item.DataItem, "CAT_NAME") + "</b>) category.<br><b>Note: </b> deleting this category will delete<br>all articles belong to this category.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            delbutton2.Attributes.Add("onmouseout", "UnTip()");
            Panel3.Visible = false;
        }
    }

    //Handles update comment
    public void Update_Category(Object s, EventArgs e)
    {
        ArticleRepository Category = new ArticleRepository();

        Category.CatID = int.Parse(Request.Form["CategoryID"]);
        Category.Category = Request.Form["CategoryName"];

        Caching.PurgeCacheItems("ArticleCategory_SideMenu");

        //Notify user if error occured.
        if (Category.UpdateCategory(Category) != 0)
        {
            JSLiteral.Text = "Error occured while processing your submit.";
            return;
        }

        Category = null;

        Response.Redirect("confirmarticlecatedit.aspx?catname=" + Request.Form["CategoryName"] + "&mode=update");
    }

    //Handle the delete button click event
    public void Delete_Category(Object sender, RepeaterCommandEventArgs e)
    {
        if ((e.CommandName == "Delete"))
        {
            ArticleRepository Category = new ArticleRepository();

            Category.CatID = Convert.ToInt32(e.CommandArgument);

            Caching.PurgeCacheItems("ArticleCategory_SideMenu");

            //Perform delete
            Category.DeleteCategory(Category);

            //Redirect to confirm delete page
            Response.Redirect("confirmarticlecatedit.aspx?catname=ArticleCategoryID" + Category.CatID + "&mode=del");
        }
    }

    //Handle add new category
    public void Add_Category(Object s, EventArgs e)
    {
        ArticleRepository Category = new ArticleRepository();

        Category.Category = Request.Form["CategoryName"];

        Caching.PurgeCacheItems("ArticleCategory_SideMenu");

        //Notify user if error occured.
        if (Category.AddCategory(Category) != 0)
        {
            JSLiteral.Text = "Error occured while processing your submit.";
            return;
        }

        Category = null;

        Response.Redirect("confirmarticlecatedit.aspx?catname=" + Request.Form["CategoryName"] + "&mode=add");
    }

    public void ArticleCat_ItemDataBound(Object s, RepeaterItemEventArgs e)
    {
        // This event is raised for the header, the footer, separators, and items.
        // Execute the following logic for Items and Alternating Items.
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            LinkButton delbutton3 = (LinkButton)(e.Item.FindControl("delbutton3"));
            HyperLink editbutton3 = (HyperLink)(e.Item.FindControl("editbutton3"));
            delbutton3.Text = "<img border='0' src='../images/icon_delete.gif'>";
            editbutton3.Text = "<img src='../images/icon_pencil.gif' alt='Edit' border='0'>";
            editbutton3.NavigateUrl = "updatearticle.aspx?aid=" + DataBinder.Eval(e.Item.DataItem, "ID");
            delbutton3.Attributes["onclick"] = "javascript:return confirm('Are you sure you want to delete " + DataBinder.Eval(e.Item.DataItem, "Title") + " Article, ID#  " + DataBinder.Eval(e.Item.DataItem, "ID") + "?')";
            delbutton3.Attributes.Add("onmouseover", "Tip('Delete (<b>" + DataBinder.Eval(e.Item.DataItem, "Title") + "</b>) article.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            delbutton3.Attributes.Add("onmouseout", "UnTip()");
            editbutton3.Attributes.Add("onmouseover", "Tip('Edit (<b>" + DataBinder.Eval(e.Item.DataItem, "Title") + "</b>) article.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            editbutton3.Attributes.Add("onmouseout", "UnTip()");
        }
    }

    //Handle the delete button click event
    public void Delete_Article(Object sender, RepeaterCommandEventArgs e)
    {
        if ((e.CommandName == "Delete"))
        {
            ArticleRepository DeleteArticle = new ArticleRepository();

            DeleteArticle.ID = Convert.ToInt32(e.CommandArgument);

            Caching.PurgeCacheItems("Newest_Articles");
            Caching.PurgeCacheItems("ArticleCategory_SideMenu");

            //Perform delete
            DeleteArticle.Delete();

            //Release allocated memory
            DeleteArticle = null;

            //Redirect to confirm delete page
            Response.Redirect("articlemanager.aspx");
        }
    }

    //Switch to Add Category mode
    public void ChangeToAddCat(object s, EventArgs e)
    {
        DelCategory.Visible = false;
        CategoryName.Text = "";
        CategoryID.Visible = false;
        Panel2.Visible = true;
        Panel3.Visible = false;
        AddNewCat.Visible = true;
        updatebutton.Visible = false;
        lblheaderform.Text = "Adding New Article Category";
        lblnamedis2.Text = "Category Name:";
    }
}
