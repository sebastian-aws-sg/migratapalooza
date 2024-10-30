#region XD World Recipe V 2.8
// FileName: RecipeRepository.cs
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
    public class RecipeRepository : BaseRecipeObj
    {
        /// <summary>
        /// Default Constructor
        /// </summary>
        public RecipeRepository()
        {
        }

        /// <summary>
        /// Perform Insert to Database
        /// </summary>
        /// <returns></returns>
        public override int Add(Recipe recipe)
        {
            return Blogic.ActionProcedureDataProvider.AddRecipe(recipe);
        }

        /// <summary>
        /// Perform Update
        /// </summary>
        /// <returns></returns>
        public override int Update(Recipe recipe)
        {
            return Blogic.ActionProcedureDataProvider.UpdateRecipe(recipe);
        }

        /// <summary>
        /// Perform Delete
        /// </summary>
        /// <returns></returns>
        public override int Delete(Recipe recipe)
        {
            return Blogic.ActionProcedureDataProvider.AdminRecipeManagerDelete(recipe);
        }

        /// <summary>
        /// Perform Insert to Database
        /// </summary>
        /// <returns></returns>
        public override int AddCategory(Recipe category)
        {
            return Blogic.ActionProcedureDataProvider.AdminAddNewRecipeCategory(category);
        }

        /// <summary>
        /// Perform Update
        /// </summary>
        /// <returns></returns>
        public override int UpdateCategory(Recipe category)
        {
            return Blogic.ActionProcedureDataProvider.UpdateRecipeCategory(category);
        }

        /// <summary>
        /// Perform Delete
        /// </summary>
        /// <returns></returns>
        public override int DeleteCategory(Recipe category)
        {
            return Blogic.ActionProcedureDataProvider.AdminDeleteRecipeCategory(category);
        }
    }
}


