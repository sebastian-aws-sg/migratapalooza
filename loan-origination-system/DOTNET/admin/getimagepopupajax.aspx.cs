#region XD World Recipe V 2.8
// FileName: getimagepopupajax.cs
// Author: Dexter Zafra
// Date Created: 9/11/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.IO;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using XDRecipe.UI;
using XDRecipe.BL;
using XDRecipe.BL.Providers;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;

public partial class admin_getimagepopupajax : BasePageAdmin
{
    protected void Page_Load(object sender, EventArgs e)
    {
        //Get image path, filename, extension, width and height.
        string Directory = Server.MapPath("~/RecipeImageUpload/"); // Recipe images directory
        string Getimagename = Request.QueryString["imgname"];
        string FileName = "";

        if (File.Exists(Server.MapPath("~/RecipeImageUpload/" + Path.GetFileName(Getimagename))))
        {
            FileName = Request.QueryString["imgname"];
        }
        else
        {
            FileName = "imagenotfound.gif";
        }

        string FullFileNamePath = string.Concat(Directory, FileName);

        //We used using here to make prevent file locking when updating photo.
        //This ensure that object is dispose properly.
        using (System.Drawing.Image GetUploadedImage = System.Drawing.Image.FromFile(FullFileNamePath))
        {
            float imgWidth = GetUploadedImage.PhysicalDimension.Width; //Get img width
            float imgHeight = GetUploadedImage.PhysicalDimension.Height; //Get img height
            lblimgsize.Text = "Size: " + imgWidth + " x " + imgHeight;
        }

        recipeimage.ImageUrl = "~/RecipeImageUpload/" + FileName.ToString();
        recipeimage.AlternateText = FileName.ToString();

        lblimgname.Text = "Name: " + Getimagename.ToString();
            
    }
}
