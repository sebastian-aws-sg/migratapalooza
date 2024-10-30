#region XD World Recipe V 3
// FileName: AddRating.cs
// Author: Dexter Zafra
// Date Created: 7/25/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Web;
using XDRecipe.BL;
using XDRecipe.BL.Providers;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL
{
    /// <summary>
    /// Object in this class manage add rating.
    /// </summary>
    public sealed class Addrating : Rating
    {
        /// <summary>
        /// Constructor
        /// </summary>
        public Addrating(int WhatPage, int ItemID, int RateValue)
        {
            this._WhatPageID = WhatPage;
            this._ID = ItemID;
            this._RateVal = RateValue;
        }

        public void Insert()
        {
            string strCookieName = "";

            switch (WhatPageID)
            {
                case constant.intRecipe:
                    strCookieName = "XDWorldRecipeRatingCookie"; //Cookie name for recipe user rating
                    break;

                case constant.intArticle:
                    strCookieName = "XDArticleRatingCookie"; //Cookie name for article user rating
                    break;
            }

            //Create cookie
            HttpCookie RateCookie = new HttpCookie(strCookieName);
            RateCookie.Value = ID.ToString(); //Assign the item ID to the cookie value
            RateCookie.Expires = DateTime.Now.AddDays(1); //Expire cookie in 1 day
            HttpContext.Current.Response.Cookies.Add(RateCookie);

            switch (WhatPageID)
            {
                case constant.intRecipe: //Insert recipe rating and redirect back to recipedetail.aspx
                    Blogic.AddRating(ID, RateVal);
                    HttpContext.Current.Response.Redirect("recipedetail.aspx?id=" + ID);
                    break;

                case constant.intArticle: //Insert article rating and redirect back to articledetail.aspx
                    Blogic.AddArticleRating(ID, RateVal);
                    HttpContext.Current.Response.Redirect("articledetail.aspx?&aid=" + ID);
                    break;
            }
        }
    }
}
