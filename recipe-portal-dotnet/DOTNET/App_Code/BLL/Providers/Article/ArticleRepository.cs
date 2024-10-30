#region XD World Recipe V 3
// FileName: ArticleRepository.cs
// Author: Dexter Zafra
// Date Created: 6/30/2008
// Website: www.ex-designz.net
#endregion
using System;
using XDRecipe.Model;

namespace XDRecipe.BL
{
    /// <summary>
    /// Objects in this class manages Add, Update and Delete Recipe
    /// </summary>
    public class ArticleRepository : BaseArticleObj
    {
        /// <summary>
        /// Default Constructor
        /// </summary>
        public ArticleRepository()
        {
        }

        /// <summary>
        /// Perform Insert to Database
        /// </summary>
        /// <returns></returns>
        public override int Add(article article)
        {
            return Blogic.ActionProcedureDataProvider.AddArticle(article);
        }

        /// <summary>
        /// Perform Update
        /// </summary>
        /// <returns></returns>
        public override int Update(article article)
        {
            return Blogic.ActionProcedureDataProvider.UpdateArticle(article);
        }

        /// <summary>
        /// Perform Delete
        /// </summary>
        /// <returns></returns>
        public override int Delete(article article)
        {
            return Blogic.ActionProcedureDataProvider.AdminDeleteArticle(article);
        }

        /// <summary>
        /// Perform Insert to Database
        /// </summary>
        /// <returns></returns>
        public override int AddCategory(article category)
        {
            return Blogic.ActionProcedureDataProvider.AdminAddNewArticleCategory(category);
        }

        /// <summary>
        /// Perform Update
        /// </summary>
        /// <returns></returns>
        public override int UpdateCategory(article category)
        {
            return Blogic.ActionProcedureDataProvider.UpdateArticleCategory(category);
        }

        /// <summary>
        /// Perform Delete
        /// </summary>
        /// <returns></returns>
        public override int DeleteCategory(article category)
        {
            return Blogic.ActionProcedureDataProvider.AdminDeleteArticleCategory(category);
        }

    }
}
