#region XD World Recipe V 3
// FileName: ImageUploadManager.cs
// Author: Dexter Zafra
// Date Created: 2/15/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using System.Configuration;
using System.IO;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.BL.Providers.Recipes;
using XDRecipe.Common.Utilities;
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;

namespace XDRecipe.BL
{
    /// <summary>
    /// Object in this class manages Image Upload.
    /// </summary>
    public static class ImageUploadManager
    {
        public static void UploadRecipeImage(RecipeRepository Recipe, PlaceHolder ph, string directory, int maxsize, bool IsEdit)
        {
            FileUpload ImageUpload = (FileUpload)(ph.FindControl("RecipeImageFileUpload"));

            if (ImageUpload.HasFile) //Check if there is a file
            {
                //Constant variables
                string Directory = directory; 
                int maxFileSize = maxsize;

                int FileSize = ImageUpload.PostedFile.ContentLength; //Get th file lenght
                string contentType = ImageUpload.PostedFile.ContentType; // Get the file type
                string FileExist = Directory + ImageUpload.PostedFile.FileName; // Get the filename from the directory and compare
                string FileName = Path.GetFileNameWithoutExtension(ImageUpload.PostedFile.FileName); //Get the posted filename
                string FileExtention = Path.GetExtension(ImageUpload.PostedFile.FileName); //Get the posted file extension
                string FilePath;
                string FileNameWithExtension;

                //File type validation
                if (!contentType.Equals("image/gif") &&
                    !contentType.Equals("image/jpeg") &&
                    !contentType.Equals("image/jpg") &&
                    !contentType.Equals("image/png"))
                {
                    return;
                }
                // File size validation
                else if (FileSize > maxFileSize)
                {
                    return;
                }
                else
                {
                    //Check wether the image name already exist. 
                    //If the image name already exist, append a random 
                    //numeric and letter to ensure the image name is always unqiue.
                    if (File.Exists(HttpContext.Current.Server.MapPath(FileExist)))
                    {
                        //Create a random alpha numeric to make sure the updated image name is unique.
                        Random rand = new Random((int)DateTime.Now.Ticks);
                        int randnum = rand.Next(1, 10);
                        int CharCode = rand.Next(Convert.ToInt32('a'), Convert.ToInt32('z'));
                        char RandomChar = Convert.ToChar(CharCode);

                        //Get directory, the file name and the extension.
                        FilePath = string.Concat(Directory, FileName + randnum + RandomChar, "", FileExtention);

                        //Joined the filename and extension to insert into the database.
                        FileNameWithExtension = FileName + randnum + RandomChar + FileExtention;

                        //Initialize Add recipe object property to get the full image name
                        Recipe.RecipeImage = FileNameWithExtension;

                        try
                        {
                            //Save the recipe image to the specified directory
                            //Make sure the "RecipeImage" folder has write permission to upload image
                            ImageUpload.SaveAs(HttpContext.Current.Server.MapPath(FilePath));

                        }
                        catch
                        {

                        }
                    }
                    else
                    {
                        //Get directory, the file name and the extension.
                        FilePath = string.Concat(Directory, FileName, "", FileExtention);

                        //Joined the filename and extension to insert into the database.
                        FileNameWithExtension = FileName + FileExtention;

                        //Initialize Add recipe object property to get the full image name
                        Recipe.RecipeImage = FileNameWithExtension;

                        try
                        {
                            //Save the recipe image to the specified directory
                            //Make sure the "RecipeImage" folder has write permission to upload image
                            ImageUpload.SaveAs(HttpContext.Current.Server.MapPath(FilePath));

                        }
                        catch
                        {

                        }
                    }
                }
            }
            else
            {
                if (!IsEdit)
                {
                    //If there is no image to be uploaded, then assign an empty string to the property
                    Recipe.RecipeImage = string.Empty;
                }
                else
                {
                    //This section is executed if the input file is empty.
                    //Then it check if an image filename exist in the database.
                    //If it exist, just update it with the same value, else update it with an empty string.
                    IDataReader dr = Blogic.ActionProcedureDataProvider.GetRecipeImageFileNameForUpdate(Recipe.ID);

                    dr.Read();

                    if (dr["RecipeImage"] != DBNull.Value)
                    {
                        Recipe.RecipeImage = (string)dr["RecipeImage"];
                    }
                    else
                    {
                        Recipe.RecipeImage = string.Empty;
                    }

                    dr.Close();
                }
            }

        }

        public static void UploadUserImage(UserRepository User, PlaceHolder ph, string directory, int maxsize)
        {
            FileUpload ImageUpload = (FileUpload)(ph.FindControl("UserImageFileUpload"));

            if (ImageUpload.HasFile) //Check if there is a file
            {
                //Constant variables
                string Directory = directory;
                int maxFileSize = maxsize;

                int FileSize = ImageUpload.PostedFile.ContentLength; //Get th file lenght
                string contentType = ImageUpload.PostedFile.ContentType; // Get the file type
                string FileExist = Directory + ImageUpload.PostedFile.FileName; // Get the filename from the directory and compare
                string FileName = User.Username; //Use username as the image name. Username is unique so no problem with this.
                string FileExtention = Path.GetExtension(ImageUpload.PostedFile.FileName); //Get the posted file extension
                string FilePath;
                string FileNameWithExtension;

                //File type validation
                if (!contentType.Equals("image/gif") &&
                    !contentType.Equals("image/jpeg") &&
                    !contentType.Equals("image/jpg") &&
                    !contentType.Equals("image/png"))
                {
                    return;
                }
                // File size validation
                else if (FileSize > maxFileSize)
                {
                    return;
                }
                else
                {
                    //If the user updated the photo, just overwrite the old one.
                    //Get directory, the file name and the extension.
                    FilePath = string.Concat(Directory, FileName, "", FileExtention);

                    //Joined the filename and extension to insert into the database.
                    FileNameWithExtension = FileName + FileExtention;

                    //Initialize Add recipe object property to get the full image name
                    User.Photo = FileNameWithExtension;

                    try
                    {
                        //Save the recipe image to the specified directory
                        //Make sure the "RecipeImage" folder has write permission to upload image
                        ImageUpload.SaveAs(HttpContext.Current.Server.MapPath(FilePath));

                    }
                    catch
                    {
 
                    }
                }
            }
            else
            {
                //If there is no image to be uploaded, then assign an empty string to the property
                User.Photo = string.Empty;
            }

        }
    }
}
