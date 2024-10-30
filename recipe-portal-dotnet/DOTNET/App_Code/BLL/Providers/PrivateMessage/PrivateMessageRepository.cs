#region XD World Recipe V 2.8
// FileName: PrivateMessageRepository.cs
// Author: Dexter Zafra
// Date Created: 2/30/2009
// Website: www.ex-designz.net
#endregion
using System;
using XDRecipe.Model;

namespace XDRecipe.BL
{
    /// <summary>
    /// Objects in this class manages Add, and Delete Private Message
    /// </summary>
    public class PrivateMessageRepository : BasePMObj
    {
        /// <summary>
        /// Default Constructor
        /// </summary>
        public PrivateMessageRepository()
        {
        }

        /// <summary>
        /// Perform Insert to Database
        /// </summary>
        /// <returns></returns>
        public override int Add(PrivateMessage pm)
        {
            return Blogic.InsertPrivateMessage(pm);
        }

        /// <summary>
        /// Perform Delete
        /// </summary>
        /// <returns></returns>
        public override int Delete(PrivateMessage pm)
        {
            return Blogic.DeletePrivateMessage(pm);
        }

        /// <summary>
        /// Perform Delete Sent by user PM
        /// </summary>
        /// <returns></returns>
        public override int DeleteSentPM(PrivateMessage pm)
        {
            return Blogic.DeleteSentPrivateMessage(pm);
        }
    }
}
