#region XD World Recipe V 3
// FileName: ArticleComments.cs
// Author: Dexter Zafra
// Date Created: 5/29/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using System.Web;
using System.Web.UI.WebControls;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL.Providers.Comments
{
    /// <summary>
    /// Objects in this class manages get and show/hide recipe comments
    /// </summary>
    public sealed class ArticleComments : BaseCommentObj
    {
        public ArticleComments()
        {
        }

        public ArticleComments(int ID, Repeater RepeaterName, PlaceHolder Ph)
        {
            this._ID = ID;
            this._RepeaterName = RepeaterName;
            this._placeholder = Ph;
        }

        /// <summary>
        /// Show or hide recipe comment
        /// </summary>
        public override void FillUp()
        {
            //Find control
            Panel Panel1 = (Panel)(placeholder.FindControl("Panel1"));
            Image CommentImg = (Image)(placeholder.FindControl("CommentImg"));
            HyperLink CommentLink = (HyperLink)(placeholder.FindControl("CommentLink"));

            this._ShowHideComment = SiteConfiguration.GetConfiguration.ShowHideArticleComment;

            if (IsShowHideComment)
            {
                //If true, display article comments, else hide.
                //Get datasourse
                RepeaterName.DataSource = ProviderArticleComments.GetComments(ID);
                RepeaterName.DataBind();
                Panel1.Visible = true;
            }
            else
            {
                Panel1.Visible = false;
                CommentImg.Visible = false;
                CommentLink.Visible = false;
            }
        }
    }
}