#region XD World Recipe V 3
// FileName: ProviderRecipeComments.cs
// Author: Dexter Zafra
// Date Created: 8/26/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Web;
using System.Data;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL.Providers.Comments
{
    /// <summary>
    /// object in this class manages recipe comments object List collection
    /// </summary>
    public static class RecipeCommentProvider
    {
        /// <summary>
        /// Return Recipe Comment
        /// </summary>
        public static ExtendedCollection<Comment> GetComments(int ID)
        {
            return ProviderDataFieldsComment.GetDataFields(Blogic.ActionProcedureDataProvider.GetCommentsRecipeDetail(ID));
        }
    }
}