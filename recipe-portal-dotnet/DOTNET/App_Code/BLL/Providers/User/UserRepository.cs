#region XD World Recipe V 3
// FileName: UserRepository.cs
// Author: Dexter Zafra
// Date Created: 2/15/2009
// Website: www.ex-designz.net
#endregion
using System;
using XDRecipe.Model;

namespace XDRecipe.BL
{
    /// <summary>
    /// Objects in this class manages Add, Update and Delete users
    /// </summary>
    public class UserRepository : BaseUserObj
    {
        /// <summary>
        /// Default Constructor
        /// </summary>
        public UserRepository()
        {
        }

        /// <summary>
        /// Perform Insert to Database
        /// </summary>
        /// <returns></returns>
        public override int Add(Users user)
        {
            return Blogic.AddNewUser(user);
        }

        /// <summary>
        /// Perform Update
        /// </summary>
        /// <returns></returns>
        public override int Update(Users user)
        {
            return Blogic.UpdateUser(user);
        }

        /// <summary>
        /// Perform Delete
        /// </summary>
        /// <returns></returns>
        public override int Delete(Users user)
        {
            return Blogic.DeleteUser(user);
        }

    }
}
