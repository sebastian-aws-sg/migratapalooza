#region XD World Recipe V 3
// FileName: IDB.cs
// Author: Dexter Zafra
// Date Created: 6/29/2008
// Website: www.ex-designz.net
#endregion
using System;
using XDRecipe.Common;

namespace XDRecipe.Model
{
    /// <summary>
    /// CRUD Interface
    /// </summary>
    public interface IDB
    {
        int Add();
        int Update();
        int Delete();
        void fillup();
    }
}
