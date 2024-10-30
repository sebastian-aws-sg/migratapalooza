#region XD World Recipe V 3
// FileName: ProviderRecipeDropdownList.cs
// Author: Dexter Zafra
// Date Created: 6/19/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using System.Web.UI.WebControls;
using System.Collections;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL.Providers.Recipes
{
    /// <summary>
    /// Object in this class populate recipe category dropdownlist
    /// </summary>
    public sealed class ProviderRecipeCategoryDropdown
    {
        public ProviderRecipeCategoryDropdown()
        {
        }

        private Hashtable _CategoryListRecipe;

        /// <summary>
        /// Return a hashtable of recipe category
        /// </summary>
        public Hashtable categoryListRecipe
        {
            get
            {
                if (_CategoryListRecipe == null)
                    createCategoryListRecipe();
                return _CategoryListRecipe;
            }
        }

        /// <summary>
        /// Create a hashtable for Recipe Category dropdownlist
        /// </summary>
        private void createCategoryListRecipe()
        {
            //Get data
            IDataReader dr = Blogic.ActionProcedureDataProvider.GetRecipeCategoryDropdownlist;

            try
            {
                Hashtable ht = new Hashtable();
                while (dr.Read())
                {
                    ht.Add(dr["CAT_ID"].ToString(), dr["CAT_TYPE"].ToString());

                    _CategoryListRecipe = ht;
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }

            //Release allocated memory
            dr.Close();
        }
    }
}