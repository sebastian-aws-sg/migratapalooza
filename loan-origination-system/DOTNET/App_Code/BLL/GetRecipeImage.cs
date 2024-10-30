#region XD World Recipe V 3
// FileName: GetRecipeImage.cs
// Author: Dexter Zafra
// Date Created: 9/11/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using XDRecipe.BL;

namespace XDRecipe.BL
{
    /// <summary>
    /// Object in this class manages recipe image and path
    /// </summary>
    public static class GetRecipeImage
    {

        /// <summary>
        /// Return the recipe image
        /// </summary>
        public static string GetRecipeImageUserEdit(int ID)
        {
            string FileName;

            FileName = "RecipeImageUpload/noimageavailable.gif";

            IDataReader dr = Blogic.ActionProcedureDataProvider.GetRecipeImageFileNameForUpdate(ID);

            dr.Read();

            if (dr["RecipeImage"] != DBNull.Value)
            {
                FileName = "RecipeImageUpload/" + (string)dr["RecipeImage"];
            }

            dr.Close();

            return FileName;
        }

        /// <summary>
        /// Return the recipe image for admin section
        /// </summary>
        public static string GetImage(int ID) 
        {
            string FileName;

            FileName = "../RecipeImageUpload/noimageavailable.gif";

            IDataReader dr = Blogic.ActionProcedureDataProvider.GetRecipeImageFileNameForUpdate(ID);

            dr.Read();

            if (dr["RecipeImage"] != DBNull.Value)
            {
                FileName = "../RecipeImageUpload/" + (string)dr["RecipeImage"];
            }

            dr.Close();

            return FileName;          
        }

        /// <summary>
        /// Return the recipe image for the recipe details
        /// </summary>
        public static string GetImageForRecipeDetails(int ID)
        {
            string FileName;

            FileName = "RecipeImageUpload/noimageavailable.gif";

            IDataReader dr = Blogic.ActionProcedureDataProvider.GetRecipeImageFileNameForUpdate(ID);

            dr.Read();

            if (dr["RecipeImage"] != DBNull.Value)
            {
                FileName = "RecipeImageUpload/" + (string)dr["RecipeImage"];
            }

            dr.Close();

            if (FileName.IndexOf("noimageavailable.gif") != -1)
            {
                FileName = string.Empty;
            }

            return FileName;
        }

        /// <summary>
        /// Return recipe image path for Admin
        /// </summary>
        public static string ImagePath
        {
            get
            {
                return "../RecipeImageUpload/";
            }
        }

        /// <summary>
        /// Return recipe image path for recipedetail
        /// </summary>
        public static string ImagePathDetail
        {
            get
            {
                return "RecipeImageUpload/";
            }
        }
    }
}
