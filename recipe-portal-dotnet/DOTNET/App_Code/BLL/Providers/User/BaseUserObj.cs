#region XD World Recipe V 2.8
// FileName: BaseUserObj.cs
// Author: Dexter Zafra
// Date Created: 6/29/2008
// Website: www.ex-designz.net
#endregion
using System;
using XDRecipe.Model;

namespace XDRecipe.BL
{
    /// <summary>
    /// Object in this class manages users CRUD database methods.
    /// </summary>
    public abstract class BaseUserObj : Users, IRepository
    {

        #region class members
            public virtual int Add(Users user) { return 0; } //Insert to database
            public virtual int Update(Users user) { return 0; } //Update to database
            public virtual int Delete(Users user) { return 0; } //Delete from database
            public virtual void FillUp(int UID) { } //Fill up database fields
        #endregion

        #region Interface contract Implementation - overload methods
            public virtual int Add() { return 0; } //Insert to database
            public virtual int Update() { return 0; } //Update to database
            public virtual int Delete() { return 0; } //Delete from database
            public virtual void FillUp() { } //Fill up database fields
        #endregion
    }
}

