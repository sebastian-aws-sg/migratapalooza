#region XD World Recipe V 3
// FileName: IRepository.cs
// Author: Dexter Zafra
// Date Created: 2/29/2009
// Website: www.ex-designz.net
#endregion
using System;
using XDRecipe.Model;

namespace XDRecipe.BL
{
    /// <summary>
    /// CRUD Interface
    /// </summary>
    public interface IRepository
    {
        int Add();
        int Update();
        int Delete();
        void FillUp();
    }
}
