#region XD World Recipe V 2.8
// FileName: recipemanager.cs
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
using XDRecipe.BL.Providers.Recipes;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.User;

public partial class admin_recipemanager : BasePageAdmin
{
    //Declare page level variables
    private string strURLRedirect;
    private int ResultCount;
    private int CatId;
    private int Tab;
    private string Letter;
    private string Find;
    private int Top;
    private int Year;
    private int Month;
    private int RecipeImage;
    private int LastViewed;
    public int ArticleCount;
    public string lastviewedselectedvalue;
    public string lastviewedlabel;

    //Instantiate utility/common object
    Utility Util = new Utility();

    protected void Page_Load(object Sender, EventArgs E)
    {
        //Get total article count
        ArticleCount = Blogic.ActionProcedureDataProvider.ArticleCountAll;

        //Assign selected value to lastviewed dropdown menu
        int lv = (int)Util.Val(Request.QueryString["lv"]);
        GetLastViewedSelectedValue(lv);

        if (!IsPostBack)
        {
            //Populate year in the dropdwonlist - starting from 2000 to the current year.
            int tempDT = DateTime.Now.Year;
            for (int i = tempDT; (tempDT - i) < 9; i--)
                ddlyear.Items.Add(new ListItem(i.ToString(), i.ToString()));

            MonthSearch();

            //Populate dropdown category list
            GetDropdownCategoryList();

            //Display A-Z alhpabet letter recipe name 
            lblalphaletter.Text = AlphabetLink.BuildLink("recipemanager.aspx?l=", "dlet", "Browse all recipe starting with letter", "&nbsp;&nbsp;");

            //Get admin username from the sessioan variable and place it in the label.
            lblusername.Text = "Welcome Admin:&nbsp;" + UserIdentity.AdminUsername;

            lblletterlegend.Text = "Recipe A-Z:";
            lblunapproved2.Visible = false;
            lblmangermainpage.Text = "Default View";
            lblmangermainpagelink.ToolTip = "Back to Default View";
            lbCountRecipe.Text = "Total Approved Recipes:&nbsp;" + string.Format("{0:#,###}", Blogic.ActionProcedureDataProvider.GetHomepageTotalRecipeCount);
            lbCountCat.Text = "Total Category:&nbsp;" + Blogic.ActionProcedureDataProvider.GetHomepageTotalCategoryCount;
            lbCountComments.Text = "Total Comments: " + Blogic.ActionProcedureDataProvider.AdminRecipeManagerGetTotalComments;
            lblunapproved.Text = "Waiting For Approval:&nbsp;" + Blogic.ActionProcedureDataProvider.AdminRecipeManagerGetWaitingforApprovalCount;
            lblrecordperpage.Text = "Default 40 records per page";
            lblrecordperpageFooter.Text = "Showing default 40 records per page";
            lblrecordperpageTop.Text = "- 40 records per page";
            approvallink.ToolTip = "There are (" + Blogic.ActionProcedureDataProvider.AdminRecipeManagerGetWaitingforApprovalCount + ") recipes waiting for your approval. Click this link to approve the recipe";

            //Set the default pagesize.
            dgrd_recipe.PageSize = 40;

            //Data binding
            BindData();

            LastUpdatedRecipes.DataSource = Blogic.ActionProcedureDataProvider.GetLast10UpdatedRecipe;
            LastUpdatedRecipes.DataBind();

            //Release allocated memory
            Util = null;
        }
    }

    //Show data in the datagrid
    private void BindData()
    {
        CatId = (int)Util.Val(Request.QueryString["catid"]);
        Tab = (int)Util.Val(Request.QueryString["tab"]);
        Letter = Request.QueryString["l"];
        Find = Request.QueryString["find"];
        Top = (int)Util.Val(Request.QueryString["top"]);
        Month = (int)Util.Val(Request.QueryString["month"]);
        Year = (int)Util.Val(Request.QueryString["year"]);
        RecipeImage = (int)Util.Val(Request.QueryString["img"]);
        LastViewed = (int)Util.Val(Request.QueryString["lv"]);

        //Set default pageindex and pagesize to pass to the BLL
        int PageIndex = dgrd_recipe.CurrentPageIndex + 1;
        int PageSize = Convert.ToInt32(LstRecpage.SelectedItem.Value);

        //Get pagesize from the pagesize dropdown menu
        dgrd_recipe.PageSize = PageSize;

        //Return datatable with custom paging SQL Row_Number. 
        DataTable dt = Blogic.ActionProcedureDataProvider.AdminRecipeManagerWithCustomPaging(CatId, Letter, Find, Tab, Top, Year, Month, RecipeImage, LastViewed, PageIndex, PageSize);

        //Assign datatable to new DataView object
        DataView dv = new DataView(dt);

        try
        {
            DataRow dr = dt.Rows[0];

            if (Top == 100)
            {   //100 Most popular recipe by hits count
                dgrd_recipe.VirtualItemCount = 100;
            }
            else
            {
                //Assigned Total records count to grid virtual count
                dgrd_recipe.VirtualItemCount = (int)dr["RCount"];
            }

            //Initialize variable for total record count to get record count for filter
            int TotalRecordsCount = (int)dr["RCount"];

            //Release datarow object in memory
            dr = null;

            //Sort by recipe ID
            if (lbOrderBy.Text == "")
            {
                 dv.Sort = "ID";
            }
            else
            {
                try
                {
                    dv.Sort = lbOrderBy.Text + " " + lbDesc.Text;
                }
                catch
                {
                    dv.Sort = "ID";
                }
            }

            //Assign dataview to grid datasource
            dgrd_recipe.DataSource = dv;

            //Bind the data
            dgrd_recipe.DataBind();

            //For Alphabet Letter
            if (!string.IsNullOrEmpty(Letter))
            {
                lblSortedCat.Text = "There are&nbsp;" + TotalRecordsCount + "&nbsp;recipes starting with letter&nbsp;<b>" + Letter + "</b>";
                lblrcdalphaletterfooter.Text = TotalRecordsCount + "&nbsp;recipes starting with letter&nbsp;<b>" + Letter + "</b>&nbsp;-";
                lblrcdCatcount.Visible = false;
            }

            //For search
            if (!string.IsNullOrEmpty(Find))
            {
                lblSortedCat.Text = "Your search for recipe name&nbsp;" + "(<b>" + Find + "</b>) return:&nbsp;" + TotalRecordsCount + "&nbsp;records";
                lblrcdCatcount.Visible = false;
            }

            //For top 100 recipe
            if (Top == 100)
            {
                lblSortedCat.Text = "Top&nbsp;" + 100 + " popular&nbsp;recipes by hits";
                lblrcdCatcount.Visible = false;
            }

            //For year and month recipe submission
            if (!string.IsNullOrEmpty(Request.QueryString["year"]) && !string.IsNullOrEmpty(Request.QueryString["month"]))
            {
                lblSortedCat.Text = "There are&nbsp;" + TotalRecordsCount + " recipes submitted on &nbsp;" + Utility.GetMonthName(Month) + ", " + Year;
                lblrcdCatcount.Visible = false;
            }

            //Waiting for approval
            if (Tab == 1)
            {
                //Hide the footer total records count
                LblPageInfo.Visible = false;
                lblrecordperpageFooter.Visible = false;
                lblrecordperpageTop.Visible = false;
                approvallink.Enabled = false;
                lblSortedCat.Text = "How To? - To approve a recipe, click the Recipe Name link inside the grid.";
                lblthese.Text = "There are&nbsp;";
                lblthese2.Text = TotalRecordsCount + "&nbsp;recipe(s) waiting for approval.";
                lblmangermainpage.Text = "Recipe Manager Main";
                lblmangermainpagelink.ToolTip = "Back to Main Recipe Manager Page";
                lblunapproved2.Visible = true;
            }

            //Filter for images
            if (RecipeImage == 1)
            {
                lblSortedCat.Text = "There are&nbsp;" + TotalRecordsCount + "&nbsp;recipe with photo";
                lblrcdCatcount.Visible = false;
            }

            //Filter for lastviewed
            if (LastViewed > 0)
            {
                lblSortedCat.Text = "There are&nbsp;" + TotalRecordsCount + "&nbsp;recipes viewed " + lastviewedlabel;
                lblrcdCatcount.Visible = false;
            }

            //Filter top 100 recipes
            if (Top == 100)
            {
                LblPageInfoTop.Text = "Total Records: 100 - Showing Page: <b>" + PageIndex.ToString() + "</b> of <b>" + dgrd_recipe.PageCount.ToString() + "</b> - Displaying: <b>" + Convert.ToInt32(LstRecpage.SelectedItem.Value) + "</b> Records Per Page";
                lblrcdCatcountfooter.Text = "Total Records: 100";
            }
            else
            {
                LblPageInfoTop.Text = "Total Records: " + string.Format("{0:#,###}", TotalRecordsCount) + " - Showing Page: <b>" + PageIndex.ToString() + "</b> of <b>" + dgrd_recipe.PageCount.ToString() + "</b> - Displaying: <b>" + Convert.ToInt32(LstRecpage.SelectedItem.Value) + "</b> Records Per Page";
                lblrcdCatcountfooter.Text = "Total records: " + string.Format("{0:#,###}", TotalRecordsCount);
            }

            lblrcdCatcount.Text = "There are " + string.Format("{0:#,###}", TotalRecordsCount) + " recipes including unapproved recipes";

            //Display the pagecount in the top and footer
            LblPageInfo.Text = "Showing Page: <b>" + PageIndex.ToString() + "</b> of <b>" + dgrd_recipe.PageCount.ToString() + "</b> - Displaying: <b>" + Convert.ToInt32(LstRecpage.SelectedItem.Value) + "</b> Records Per Page" + "&nbsp;-&nbsp;";

        }
        catch
        {
        }

        //Lets hide some unneeded control
        lblrecordperpageFooter.Visible = false;
        lblrecordperpageTop.Visible = false;

        //Release datatable allocated memory
        dt = null;

        EnabledDisabled_PagerButtons();
    }

    //Assign last viewed dropdown menu selected value and label
    private void GetLastViewedSelectedValue(int LastViewed)
    {
        switch (LastViewed)
        {
            case 1:
                lastviewedselectedvalue = "Today";
                lastviewedlabel = "Today";
                break;
            case 2:
                lastviewedselectedvalue = "2 days";
                lastviewedlabel = "in the last 2 days";
                break;
            case 3:
                lastviewedselectedvalue = "3 days";
                lastviewedlabel = "in the last 3 days";
                break;
            case 4:
                lastviewedselectedvalue = "4 days";
                lastviewedlabel = "in the last 4 days";
                break;
            case 5:
                lastviewedselectedvalue = "5 days";
                lastviewedlabel = "in the last 5 days";
                break;
            case 6:
                lastviewedselectedvalue = "6 days";
                lastviewedlabel = "in the last 6 days";
                break;
            case 7:
                lastviewedselectedvalue = "1 week";
                lastviewedlabel = "in 1 week";
                break;
            case 14:
                lastviewedselectedvalue = "2 weeks";
                lastviewedlabel = "in 2 weeks";
                break;
            case 30:
                lastviewedselectedvalue = "1 month";
                lastviewedlabel = "in a month";
                break;
            default:
                lastviewedselectedvalue = "Last Viewed Recipes";
                break;
        }

    }

    //Handle enabled and disabled pager buttons
    private void EnabledDisabled_PagerButtons()
    {
        //Disabled and enabled footer pager button
        if (dgrd_recipe.CurrentPageIndex != 0)
        {
            Prev_Buttons();
            Firstbutton.Enabled = true;
            Prevbutton.Enabled = true;

            //Assign tooltip
            Firstbutton.ToolTip = "Jump back to page: 1";
            Prevbutton.ToolTip = "Back to previous page: " + dgrd_recipe.CurrentPageIndex.ToString();
        }
        else
        {
            Firstbutton.Enabled = false;
            Prevbutton.Enabled = false;

            //Clear tooltip
            Firstbutton.ToolTip = "";
            Prevbutton.ToolTip = "";
        }

        if (dgrd_recipe.CurrentPageIndex != (dgrd_recipe.PageCount - 1))
        {
            Next_Buttons();
            Lastbutton.Enabled = true;

            //Assign tooltip
            Nextbutton.ToolTip = "Go to next page: " + (dgrd_recipe.CurrentPageIndex + 2).ToString();
            Lastbutton.ToolTip = "Jump to last page: " + dgrd_recipe.PageCount.ToString();
        }
        else
        {
            //if there are less than 20 records, we're not going to allow page size change, so disabled the dropdownlist          
            LstRecpage.Enabled = false;

            //Disable next and last button if pagecount not equal to currentpageindex.
            Lastbutton.Enabled = false;
            Nextbutton.Enabled = false;

            //Clear tooltip
            Nextbutton.ToolTip = "";
            Lastbutton.ToolTip = "";
        }
    }

    //Handle PageSize Option dropdown menu
    public void DisplayPagerecord_Click(object sender, EventArgs e)
    {
        int lstRecPerPage = Convert.ToInt32(LstRecpage.SelectedItem.Value);

        //Check how many records per page to display
        switch (lstRecPerPage)
        {
            //lstRecPerpage is the ID of the Dropdownlist control

            case 80:
                dgrd_recipe.PageSize = 80;
                lblrecordperpage.Text = "Showing 80 records per page";
                lblrecordperpageFooter.Text = "Showing 80 records per page";
                lblrecordperpageTop.Text = "- 80 records per page";
                break;

            case 120:
                dgrd_recipe.PageSize = 120;
                lblrecordperpage.Text = "Default 120 records per page";
                lblrecordperpageFooter.Text = "Showing 120 records per page";
                lblrecordperpageTop.Text = "- 120 records per page";
                break;

            case 160:
                dgrd_recipe.PageSize = 160;
                lblrecordperpage.Text = "Showing 160 records per page";
                lblrecordperpageFooter.Text = "Showing 160 records per page";
                lblrecordperpageTop.Text = "- 160 records per page";
                break;

            case 200:
                dgrd_recipe.PageSize = 200;
                lblrecordperpage.Text = "Showing 200 records per page";
                lblrecordperpageFooter.Text = "Showing 200 records per page";
                lblrecordperpageTop.Text = "- 200 records per page";
                break;

            case 250:
                dgrd_recipe.PageSize = 250;
                lblrecordperpage.Text = "Showing 250 records per page";
                lblrecordperpageFooter.Text = "Showing 250 records per page";
                lblrecordperpageTop.Text = "- 250 records per page";
                break;

            case 300:
                dgrd_recipe.PageSize = 300;
                lblrecordperpage.Text = "Showing 300 records per page";
                lblrecordperpageFooter.Text = "Showing 300 records per page";
                lblrecordperpageTop.Text = "- 300 records per page";
                break;

            default:
                dgrd_recipe.PageSize = 40;
                lblrecordperpage.Text = "Default 40 records per page";
                lblrecordperpageFooter.Text = "Showing default 40 records per page";
                lblrecordperpageTop.Text = "- 40 records per page";
                break;
        }

        //Rebind the data and get the page size from the dropdown menu
        BindData();
    }

    private void GetDropdownCategoryList()
    {
        LoadDropDownListCategory.LoadDropDownCategory("Recipe Category", CategoryName, "Choose Category");
    }

    //Handles month search dropdownlist populate and set selected index. 
    private void MonthSearch()
    {
        int tempDT2 = DateTime.Now.Month;

        ddlmonth.Items.Insert(0, new ListItem(Utility.GetMonthName((int)tempDT2), tempDT2.ToString()));

        //Check whether search month was perform. If not, set the current month as selected index,
        //else get the month number from the querystring paramter and convert it to month name.
        if (!string.IsNullOrEmpty(Request.QueryString["year"]))
        {
            ddlmonth.Items.Insert(0, new ListItem(Utility.GetMonthName(int.Parse(Request.QueryString["month"])), tempDT2.ToString()));
        }

        //Populate dropdwonlist with month name or abbrevation. 
        //If it is true, then display month abbrevation, else display month name.
        DateTime month = Convert.ToDateTime("1/1/2000");
        for (int i = 0; i < 12; i++)
        {
            DateTime NextMonth = month.AddMonths(i);
            ddlmonth.Items.Add(new ListItem(Utility.GetMonthNameOrAbbrev((int)NextMonth.Month, false), NextMonth.Month.ToString()));
        }
    }

    //Handles admin sort by category
    public void AdminSortCat_Click(object sender, EventArgs e)
    {
        strURLRedirect = "recipemanager.aspx?category=" + Request.Form["category"];
        Response.Redirect(strURLRedirect);
    }

    //Handles search admin
    public void AdminSearch_Click(object sender, EventArgs e)
    {
        strURLRedirect = "recipemanager.aspx?find=" + Request.Form["find"];
        Response.Redirect(strURLRedirect);
    }

    //Handles search admin by month
    public void AdminSearchByMonth_Click(object sender, EventArgs e)
    {
        strURLRedirect = "recipemanager.aspx?year=" + ddlyear.SelectedValue + "&month=" + ddlmonth.SelectedValue;
        Response.Redirect(strURLRedirect);
    }

    //Handles dropdown list property
    public string SelectedValue
    {
        get
        {
            return CategoryName.SelectedValue;
        }
        set
        {
            CategoryName.SelectedValue = value;
        }
    }

    //Handle sort category selection redirect
    public void GetCatName_Click(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(this.SelectedValue))
        {
            CategoryName.SelectedValue = this.SelectedValue;
        }

        strURLRedirect = "recipemanager.aspx?catid=" + CategoryName.SelectedValue;
        Response.Redirect(strURLRedirect);
    }

    //Previous footer button
    private void Prev_Buttons()
    {
        int PrevSet;
        if (dgrd_recipe.CurrentPageIndex + 1 != 1 && ResultCount != -1)
        {
            PrevSet = dgrd_recipe.PageSize;
        }
    }

    //Next footer button
    private void Next_Buttons()
    {
        int NextSet;
        if (dgrd_recipe.CurrentPageIndex + 1 < dgrd_recipe.PageCount)
        {
            NextSet = dgrd_recipe.PageSize;
        }
    }

    //Handles footer button pager click event
    public void FooterPagerClick(object sender, CommandEventArgs e)
    {
        //Used by external paging
        string arg;
        arg = e.CommandArgument.ToString();

        switch (arg)
        {
            case "next":
                //The next Button was Clicked
                if ((dgrd_recipe.CurrentPageIndex < (dgrd_recipe.PageCount - 1)))
                {
                    dgrd_recipe.CurrentPageIndex += 1;
                }

                break;

            case "prev":
                //The prev button was clicked
                if ((dgrd_recipe.CurrentPageIndex > 0))
                {
                    dgrd_recipe.CurrentPageIndex -= 1;
                }

                break;

            case "last":
                //The Last Page button was clicked
                dgrd_recipe.CurrentPageIndex = (dgrd_recipe.PageCount - 1);
                break;

            default:
                //The First Page button was clicked
                dgrd_recipe.CurrentPageIndex = Convert.ToInt32(arg);
                break;
        }

        //Rebind the data
        BindData();
    }

    //Handle edit databound
    public void Edit_Handle(object sender, DataGridCommandEventArgs e)
    {
        if ((e.CommandName == "edit"))
        {
            TableCell iIdNumber = e.Item.Cells[0];
            strURLRedirect = "editing.aspx?id=" + iIdNumber.Text;
            Server.Transfer(strURLRedirect);
        }
    }

    //Handle the delete button click event
    public void Delete_Recipes(object sender, DataGridCommandEventArgs e)
    {
        if ((e.CommandName == "Delete"))
        {
            TableCell iIdNumber2 = e.Item.Cells[0];
            TableCell iIRecipename = e.Item.Cells[1];

            #region Delete Recipe Image
            //Delete the recipe image if the recipe has an image.

            try
            {
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetRecipeImageFileNameForUpdate(int.Parse(iIdNumber2.Text));

                dr.Read();

                if (dr["RecipeImage"] != DBNull.Value)
                {
                    System.IO.File.Delete(Server.MapPath(GetRecipeImage.ImagePath + dr["RecipeImage"].ToString()));
                }

                dr.Close();
            }
            catch
            {
            }
            #endregion

            //Refresh cached data
            Caching.PurgeCacheItems("MainCourse_RecipeCategory");
            Caching.PurgeCacheItems("Ethnic_RecipeCategory");
            Caching.PurgeCacheItems("RecipeCategory_SideMenu");
            Caching.PurgeCacheItems("Newest_RecipesSideMenu_");

            RecipeRepository Recipe = new RecipeRepository();

            Recipe.ID = int.Parse(iIdNumber2.Text);

            //Perform delete recipe
            Recipe.Delete(Recipe);

            Recipe = null;

            //Redirect to confirm delete page
            strURLRedirect = "confirmdel.aspx?catname=" + iIRecipename.Text + "&mode=del";
            Server.Transfer(strURLRedirect);
        }
    }

    public void dgRecipe_ItemDataBound(object sender, DataGridItemEventArgs e)
    {
        object strIDcell;
        strIDcell = DataBinder.Eval(e.Item.DataItem, "ID");

        //Disbabled viewstate in certain item cells to minimize viewstate size
        e.Item.Cells[2].EnableViewState = false;
        e.Item.Cells[3].EnableViewState = false;
        e.Item.Cells[4].EnableViewState = false;
        e.Item.Cells[5].EnableViewState = false;
        e.Item.Cells[6].EnableViewState = false;
        e.Item.Cells[7].EnableViewState = false;

        //First, make sure we're not dealing with a Header or Footer row
        if (e.Item.ItemType != ListItemType.Header && e.Item.ItemType != ListItemType.Footer)
        {
            LinkButton deleteButton = (LinkButton)(e.Item.Cells[8].Controls[0]);
            LinkButton editButton = (LinkButton)(e.Item.Cells[7].Controls[0]);

            //Check for null or empty string value
            if (Convert.IsDBNull(DataBinder.Eval(e.Item.DataItem, "RecipeImage")) || (string)DataBinder.Eval(e.Item.DataItem, "RecipeImage") == "")
            {
                e.Item.Cells[2].Text = "No";
            }
            else
            {
                e.Item.Cells[2].Text = "Yes";
                e.Item.Cells[2].ToolTip = "Click to view - Image Name: " + DataBinder.Eval(e.Item.DataItem, "RecipeImage");
                e.Item.Cells[2].Attributes.Add("OnMouseOver", "this.style.cursor='pointer';this.style.color='#ff3e3e';showimage('getimagepopupajax.aspx?id=" + strIDcell + "&imgname=" + DataBinder.Eval(e.Item.DataItem, "RecipeImage") + "',this);return false");
                e.Item.Cells[2].Attributes.Add("OnMouseOut", "this.style.cursor='pointer';this.style.color='#007AF4'");
                e.Item.Cells[2].ForeColor = System.Drawing.ColorTranslator.FromHtml("#007AF4");
            }

            //We can now add the onclick event handler
            deleteButton.Attributes["onclick"] = "javascript:return confirm('Are you sure you want to delete " + DataBinder.Eval(e.Item.DataItem, "Name") + " Recipe, ID#  " + DataBinder.Eval(e.Item.DataItem, "ID") + "?')";
            deleteButton.ToolTip = "Delete recipe (" + DataBinder.Eval(e.Item.DataItem, "Name") + ") ID :" + DataBinder.Eval(e.Item.DataItem, "ID");
            editButton.ToolTip = "Edit recipe (" + DataBinder.Eval(e.Item.DataItem, "Name") + ") ID :" + DataBinder.Eval(e.Item.DataItem, "ID");

            //Data row mouseover changecolor
            e.Item.Attributes.Add("OnMouseOver", "this.style.backgroundColor='#ECF5FF'");
            e.Item.Attributes.Add("OnMouseOut", "this.style.backgroundColor='#ffffff'");

            //handle cell 1 Recipe name change cell and font color
            e.Item.Cells[1].Attributes.Add("OnMouseOver", "this.style.backgroundColor='#F0E68C';this.style.cursor='pointer';this.style.color='#ff3e3e'");
            e.Item.Cells[1].Attributes.Add("OnMouseOut", "this.style.backgroundColor='#fff';this.style.cursor='pointer';this.style.color='#007AF4'");
            e.Item.Cells[1].ForeColor = System.Drawing.ColorTranslator.FromHtml("#007AF4");

            //Handle cell 1 - Recipe name click event
            e.Item.Cells[1].Attributes.Add("Onclick", "javascript:window.open('viewing.aspx?id=" + strIDcell + "'," + "'','height=815,width=700')");

            //Display cell tooltip in the grid
            e.Item.Cells[0].ToolTip = "Recipe  " + DataBinder.Eval(e.Item.DataItem, "ID");

            if (string.IsNullOrEmpty(Request.QueryString["lv"]))
            {
                e.Item.Cells[1].ToolTip = "Click to view: " + DataBinder.Eval(e.Item.DataItem, "Name") + " recipe";
            }
            else
            {
                e.Item.Cells[1].Text = DataBinder.Eval(e.Item.DataItem, "Name") + " - (Lastviewed on: " + DataBinder.Eval(e.Item.DataItem, "HIT_DATE") + ")";
                e.Item.Cells[1].ToolTip = "Click to view: " + DataBinder.Eval(e.Item.DataItem, "Name") + " recipe - Lastviewed on: " + DataBinder.Eval(e.Item.DataItem, "HIT_DATE");
            }

            //e.Item.Cells[1].ToolTip = "Click to view: " + DataBinder.Eval(e.Item.DataItem, "Name") + " recipe";
            e.Item.Cells[3].ToolTip = "Category: " + DataBinder.Eval(e.Item.DataItem, "Category");
            e.Item.Cells[4].ToolTip = "Recipe author: " + DataBinder.Eval(e.Item.DataItem, "Author");
            e.Item.Cells[5].ToolTip = "Submitted on: " + DataBinder.Eval(e.Item.DataItem, "Date");
            e.Item.Cells[6].ToolTip = "This recipe has been viewed: " + DataBinder.Eval(e.Item.DataItem, "Hits");

            //If we're in the approval tab, then we change the tooltip
            if (Request.QueryString["tab"] == "1")
            {
                e.Item.Cells[1].ToolTip = "Waiting for approval, click to review and approve: " + DataBinder.Eval(e.Item.DataItem, "Name") + " recipe";
            }

            //Display the sorted category name from the dropdownlist
            if (Request.QueryString["catid"] != null)
            {
                lblSortedCat.Text = "Sorted by Category: " + DataBinder.Eval(e.Item.DataItem, "Category");
            }
        }

        //Handles the header link tooltip
        //First, we make sure we're dealing with the Header
        if (e.Item.ItemType == ListItemType.Header)
        {
            #region Show Sort Image Arrow Up and Arrow Down

            string Sort = lbsort.Text;

            switch (Sort)
            {
                case "ID":
                    if (lblCurrentSort.Text == "ASC")
                    {
                        e.Item.Cells[0].Text = "ID" + " <img src='../images/arrow_up2.gif'>";
                    }
                    else
                    {
                        e.Item.Cells[0].Text = "ID" + " <img src='../images/arrow_down2.gif'>";
                    }
                    break;

                case "Name":
                    if (lblCurrentSort.Text == "ASC")
                    {
                        e.Item.Cells[1].Text = "Recipe Name" + " <img src='../images/arrow_up2.gif'>";
                    }
                    else
                    {
                        e.Item.Cells[1].Text = "Recipe Name" + " <img src='../images/arrow_down2.gif'>";
                    }
                    break;

                case "RecipeImage":
                    if (lblCurrentSort.Text == "ASC")
                    {
                        e.Item.Cells[2].Text = "Photo" + " <img src='../images/arrow_up2.gif'>";
                    }
                    else
                    {
                        e.Item.Cells[2].Text = "Photo" + " <img src='../images/arrow_down2.gif'>";
                    }
                    break;

                case "Category":
                    if (lblCurrentSort.Text == "ASC")
                    {
                        e.Item.Cells[3].Text = "Category" + " <img src='../images/arrow_up2.gif'>";
                    }
                    else
                    {
                        e.Item.Cells[3].Text = "Category" + " <img src='../images/arrow_down2.gif'>";
                    }
                    break;

                case "Author":
                    if (lblCurrentSort.Text == "ASC")
                    {
                        e.Item.Cells[4].Text = "Author" + " <img src='../images/arrow_up2.gif'>";
                    }
                    else
                    {
                        e.Item.Cells[4].Text = "Author" + " <img src='../images/arrow_down2.gif'>";
                    }
                    break;

                case "Date":
                    if (lblCurrentSort.Text == "ASC")
                    {
                        e.Item.Cells[5].Text = "Date" + " <img src='../images/arrow_up2.gif'>";
                    }
                    else
                    {
                        e.Item.Cells[5].Text = "Date" + " <img src='../images/arrow_down2.gif'>";
                    }
                    break;

                case "Hits":
                    if (lblCurrentSort.Text == "ASC")
                    {
                        e.Item.Cells[6].Text = "Hits" + " <img src='../images/arrow_up2.gif'>";
                    }
                    else
                    {
                        e.Item.Cells[6].Text = "Hits" + " <img src='../images/arrow_down2.gif'>";
                    }
                    break;
            }
            #endregion

            //Perform header style on recipe name column
            if (!string.IsNullOrEmpty(Request.QueryString["l"]) || !string.IsNullOrEmpty(Request.QueryString["find"]))
            {
                e.Item.Cells[1].BackColor = System.Drawing.ColorTranslator.FromHtml("#0057D9");
            }
            //Perform header style based on category column
            if (!string.IsNullOrEmpty(Request.QueryString["img"]))
            {
                e.Item.Cells[2].BackColor = System.Drawing.ColorTranslator.FromHtml("#0057D9");
            }
            //Perform header style based on category column
            if (!string.IsNullOrEmpty(Request.QueryString["catid"]))
            {
                e.Item.Cells[3].BackColor = System.Drawing.ColorTranslator.FromHtml("#0057D9");
            }
            //Perform header style on date column
            if (!string.IsNullOrEmpty(Request.QueryString["year"]))
            {
                e.Item.Cells[5].BackColor = System.Drawing.ColorTranslator.FromHtml("#0057D9");
            }
            //Perform header style on Hits column
            if (!string.IsNullOrEmpty(Request.QueryString["top"]))
            {
                e.Item.Cells[6].BackColor = System.Drawing.ColorTranslator.FromHtml("#0057D9");
            }

            //Display cell header tooltip
            e.Item.Cells[0].ToolTip = "Sort by ID - ASC or DESC";
            e.Item.Cells[1].ToolTip = "Sort by Recipe Name";
            e.Item.Cells[2].ToolTip = "Sort by Recipe Photo";
            e.Item.Cells[3].ToolTip = "Sort by Recipe Category";
            e.Item.Cells[4].ToolTip = "Sort by Author";
            e.Item.Cells[5].ToolTip = "Sort by Submitted date";
            e.Item.Cells[6].ToolTip = "Sort by Most Popular - Hits";

            //Handles clickable and change color cell header on mouseover
            //If you add/delete or made changes to the order of the columns, make sure you change
            //the static javascript postback to get the right unique control id.
            e.Item.Cells[0].Attributes.Add("OnMouseOver", "this.style.backgroundColor='#ACCE22';this.style.cursor='pointer'");
            e.Item.Cells[0].Attributes.Add("OnMouseOut", "this.style.backgroundColor='#CEE76D';this.style.cursor='pointer'");
            e.Item.Cells[0].Attributes.Add("Onclick", "javascript:__doPostBack('dgrd_recipe$ctl02$ctl00')");
            e.Item.Cells[1].Attributes.Add("OnMouseOver", "this.style.backgroundColor='#ACCE22';this.style.cursor='pointer'");
            e.Item.Cells[1].Attributes.Add("OnMouseOut", "this.style.backgroundColor='#CEE76D';this.style.cursor='pointer'");
            e.Item.Cells[1].Attributes.Add("Onclick", "javascript:__doPostBack('dgrd_recipe$ctl02$ctl01')");
            e.Item.Cells[2].Attributes.Add("OnMouseOver", "this.style.backgroundColor='#ACCE22';this.style.cursor='pointer'");
            e.Item.Cells[2].Attributes.Add("OnMouseOut", "this.style.backgroundColor='#CEE76D';this.style.cursor='pointer'");
            e.Item.Cells[2].Attributes.Add("Onclick", "javascript:__doPostBack('dgrd_recipe$ctl02$ctl02')");
            e.Item.Cells[3].Attributes.Add("OnMouseOver", "this.style.backgroundColor='#ACCE22';this.style.cursor='pointer'");
            e.Item.Cells[3].Attributes.Add("OnMouseOut", "this.style.backgroundColor='#CEE76D';this.style.cursor='pointer'");
            e.Item.Cells[3].Attributes.Add("Onclick", "javascript:__doPostBack('dgrd_recipe$ctl02$ctl03')");
            e.Item.Cells[4].Attributes.Add("OnMouseOver", "this.style.backgroundColor='#ACCE22';this.style.cursor='pointer'");
            e.Item.Cells[4].Attributes.Add("OnMouseOut", "this.style.backgroundColor='#CEE76D';this.style.cursor='pointer'");
            e.Item.Cells[4].Attributes.Add("Onclick", "javascript:__doPostBack('dgrd_recipe$ctl02$ctl04')");
            e.Item.Cells[5].Attributes.Add("OnMouseOver", "this.style.backgroundColor='#ACCE22';this.style.cursor='pointer'");
            e.Item.Cells[5].Attributes.Add("OnMouseOut", "this.style.backgroundColor='#CEE76D';this.style.cursor='pointer'");
            e.Item.Cells[5].Attributes.Add("Onclick", "javascript:__doPostBack('dgrd_recipe$ctl02$ctl05')");
            e.Item.Cells[6].Attributes.Add("OnMouseOver", "this.style.backgroundColor='#ACCE22';this.style.cursor='pointer'");
            e.Item.Cells[6].Attributes.Add("OnMouseOut", "this.style.backgroundColor='#CEE76D';this.style.cursor='pointer'");
            e.Item.Cells[6].Attributes.Add("Onclick", "javascript:__doPostBack('dgrd_recipe$ctl02$ctl06')");
        }

        //Make sure we're not dealing with a Header
        if (e.Item.ItemType != ListItemType.Header)
        {
            //Change the color of the recipe name column when sorted by alpha letter
            if (!string.IsNullOrEmpty(Request.QueryString["l"]))
            {
                e.Item.Cells[1].BackColor = System.Drawing.Color.Ivory;
                e.Item.Cells[1].Attributes.Add("OnMouseOver", "this.style.backgroundColor='#F0E68C';this.style.cursor='pointer';this.style.color='#ff3e3e'");
                e.Item.Cells[1].Attributes.Add("OnMouseOut", "this.style.backgroundColor='Ivory';this.style.cursor='pointer';this.style.color='#007AF4'");
            }

            //Change the color of the recipe name column when sorted by alpha letter
            if (!string.IsNullOrEmpty(Request.QueryString["find"]))
            {
                e.Item.Cells[1].BackColor = System.Drawing.Color.Ivory;
                e.Item.Cells[1].Attributes.Add("OnMouseOver", "this.style.backgroundColor='#F0E68C';this.style.cursor='pointer';this.style.color='#ff3e3e'");
                e.Item.Cells[1].Attributes.Add("OnMouseOut", "this.style.backgroundColor='Ivory';this.style.cursor='pointer';this.style.color='#007AF4'");
            }

            //Change the color of the category column when sorted category
            if (!string.IsNullOrEmpty(Request.QueryString["catid"]))
            {
                e.Item.Cells[3].BackColor = System.Drawing.Color.Ivory;
                e.Item.Cells[3].Attributes.Add("OnMouseOver", "this.style.backgroundColor='#F0E68C'");
                e.Item.Cells[3].Attributes.Add("OnMouseOut", "this.style.backgroundColor='Ivory'");
            }

            //Perform column style based on search criteria
            if (!string.IsNullOrEmpty(Request.QueryString["year"]))
            {
                e.Item.Cells[5].BackColor = System.Drawing.Color.Ivory;
                e.Item.Cells[5].Attributes.Add("OnMouseOver", "this.style.backgroundColor='#F0E68C'");
                e.Item.Cells[5].Attributes.Add("OnMouseOut", "this.style.backgroundColor='Ivory'");
            }
            if (!string.IsNullOrEmpty(Request.QueryString["top"]))
            {
                e.Item.Cells[6].BackColor = System.Drawing.Color.Ivory;
                e.Item.Cells[6].Attributes.Add("OnMouseOver", "this.style.backgroundColor='#F0E68C'");
                e.Item.Cells[6].Attributes.Add("OnMouseOut", "this.style.backgroundColor='Ivory'");
            }

            //Image filter
            if (!string.IsNullOrEmpty(Request.QueryString["img"]))
            {
                e.Item.Cells[2].ToolTip = "Click to view - Image Name: " + DataBinder.Eval(e.Item.DataItem, "RecipeImage");
                e.Item.Cells[2].Attributes.Add("OnMouseOver", "this.style.cursor='pointer';this.style.color='#ff3e3e';showimage('getimagepopupajax.aspx?id=" + strIDcell + "&imgname=" + DataBinder.Eval(e.Item.DataItem, "RecipeImage") + "',this);return false");
                e.Item.Cells[2].Attributes.Add("OnMouseOut", "this.style.cursor='pointer';this.style.color='#007AF4';");
                e.Item.Cells[2].ForeColor = System.Drawing.ColorTranslator.FromHtml("#007AF4");
                e.Item.Cells[2].BackColor = System.Drawing.Color.Ivory;
            }
        }
    }

    //The SortCommand event handler
    public void Recipes_SortCommand(object sender, DataGridSortCommandEventArgs e)
    {
        //Toggle SortAscending if the column that the data was sorted by has
        //been clicked again...
        if (lbOrderBy.Text == e.SortExpression)
        {
            if (lbDesc.Text == "desc")
            {
                lbDesc.Text = "";
                lblCurrentSort.Text = "ASC";
                lbsort.Text = e.SortExpression;
            }
            else
            {
                lbDesc.Text = "desc";
                lblCurrentSort.Text = "DESC";
                lbsort.Text = e.SortExpression;
            }
        }
        else
        {
            lbOrderBy.Text = e.SortExpression;
            lbDesc.Text = "";
            lblCurrentSort.Text = "ASC";
            lbsort.Text = e.SortExpression;
        }

        BindData();
        //rebind the DataGrid data
    }

    //Handles page change links - paging system
    public void New_Page(object sender, DataGridPageChangedEventArgs e)
    {
        dgrd_recipe.CurrentPageIndex = e.NewPageIndex;
        BindData();
    }
}
