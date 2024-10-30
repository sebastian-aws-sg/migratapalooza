#region XD World Recipe V 3
// FileName: RecipeCommentsRepository.cs
// Author: Dexter Zafra
// Date Created: 6/30/2008
// Website: www.ex-designz.net
#endregion
using System;
using XDRecipe.BL;

namespace XDRecipe.Model
{
    /// <summary>
    /// Objects in this class manage Add, update and delete recipe comments
    /// </summary>
    public class RecipeCommentsRepository : BaseCommentObj
    {
        /// <summary>
        /// Default Constructor
        /// </summary>
        public RecipeCommentsRepository()
        {
        }

        /// <summary>
        /// Perform Insert to Database
        /// </summary>
        /// <returns></returns>
        public override int Add(Comment comment)
        {
            return Blogic.ActionProcedureDataProvider.AddComment(comment);
        }

        /// <summary>
        /// Perform Update
        /// </summary>
        /// <returns></returns>
        public override int Update(Comment comment)
        {
            return Blogic.ActionProcedureDataProvider.UpdateRecipeComments(comment);
        }

        /// <summary>
        /// Perform Delete
        /// </summary>
        /// <returns></returns>
        public override int Delete(Comment comment)
        {
            return Blogic.ActionProcedureDataProvider.AdminDeleteRecipeComments(comment);
        }
    }
}
