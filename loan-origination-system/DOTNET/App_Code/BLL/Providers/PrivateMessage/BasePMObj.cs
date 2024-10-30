#region XD World Recipe V 2.8
// FileName: BasePMObj.cs
// Author: Dexter Zafra
// Date Created: 6/29/2008
// Website: www.ex-designz.net
#endregion
using System;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL
{
    /// <summary>
    /// Object in this class manages Private Message CRUD database methods.
    /// </summary>
    public abstract class BasePMObj : PrivateMessage, IRepository
    {
        #region class members
        public virtual int Add(PrivateMessage pm) { return 0; } //Insert to database
        public virtual int Delete(PrivateMessage pm) { return 0; } //Delete from database
        public virtual int DeleteSentPM(PrivateMessage pm) { return 0; } //Delete from database
        public virtual void FillUp(int ID, int UserID) { } //Fill up database fields
        #endregion

        #region Interface Contract Implementation - overload methods
        public virtual int Add() { return 0; } //Insert to database
        public virtual int Update() { return 0; } //Update to database
        public virtual int Delete() { return 0; } //Delete from database
        public virtual void FillUp() { } //Fill up database fields
        #endregion
    }
}
