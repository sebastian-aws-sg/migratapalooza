#region XD World Recipe V 3
// FileName: ArticleCommentsRepository.cs
// Author: Dexter Zafra
// Date Created: 6/30/2008
// Website: www.ex-designz.net
#endregion
using System;
using XDRecipe.BL;

namespace XDRecipe.Model
{
    /// <summary>
    /// Objects in this class manage Add, update and delete article comments
    /// </summary>
    public class ArticleCommentsRepository : BaseCommentObj
    {
        /// <summary>
        /// Default Constructor
        /// </summary>
        public ArticleCommentsRepository()
        {
        }

        /// <summary>
        /// Perform Insert to Database
        /// </summary>
        /// <returns></returns>
        public override int Add(Comment comment)
        {
            return Blogic.ActionProcedureDataProvider.InsertArticleComment(comment);
        }

        /// <summary>
        /// Perform Update
        /// </summary>
        /// <returns></returns>
        public override int Update(Comment comment)
        {
            return Blogic.ActionProcedureDataProvider.UpdateArticleComment(comment);
        }

        /// <summary>
        /// Perform Delete
        /// </summary>
        /// <returns></returns>
        public override int Delete(Comment comment)
        {
            return Blogic.ActionProcedureDataProvider.DeleteArticleComment(comment);
        }
    }
}

