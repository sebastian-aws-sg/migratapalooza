#region XD World Recipe V 3
// FileName: BaseArticleObj.cs
// Author: Dexter Zafra
// Date Created: 6/29/2008
// Website: www.ex-designz.net
#endregion
using System;
using XDRecipe.Model;

namespace XDRecipe.BL
{
    /// <summary>
    /// Object in this class manages Article CRUD database methods.
    /// </summary>
    public abstract class BaseArticleObj : article, IRepository
    {
        #region Class members
            public virtual int Add(article article) { return 0; } //Insert to database
            public virtual int Update(article article) { return 0; } //Update to database
            public virtual int Delete(article article) { return 0; } //Delete from database
            public virtual int AddCategory(article category) { return 0; } //Insert to database
            public virtual int UpdateCategory(article category) { return 0; } //Update to database
            public virtual int DeleteCategory(article category) { return 0; } //Delete from database
            public virtual void FillUp(int ID) { } //Fill up database fields
        #endregion

        #region Interface Contract Implementation - overload methods
            public virtual int Add() { return 0; } //Insert to database
            public virtual int Update() { return 0; } //Update to database
            public virtual int Delete() { return 0; } //Delete from database
            public virtual void FillUp() { } //Fill up database fields
        #endregion
    }
}

