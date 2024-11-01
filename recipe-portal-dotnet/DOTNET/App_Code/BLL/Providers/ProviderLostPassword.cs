#region XD World Recipe V 3
// FileName: LostPassword.cs
// Author: Dexter Zafra
// Date Created: 2/15/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;

namespace XDRecipe.BL
{
    /// <summary>
    /// Object in this class retrieve username and password and email it to the user.
    /// </summary>
    public static class lostpassword
    {
        private static string _Firstname;

        private static string _Uname;

        private static string _UPass;


        private static string Firstname
        {
            get { return _Firstname; }
            set { _Firstname = value; }
        }

        private static string Uname
        {
            get { return _Uname; }
            set { _Uname = value; }
        }

        private static string UPass
        {
            get { return _UPass; }
            set { _UPass = value; }
        }

        public static void GetUserCredential(string Email)
        {
            IDataReader dr = Blogic.ActionProcedureDataProvider.RecoverLostPassword(Email);

            while (dr.Read())
            {
                if (dr["FirstName"] != DBNull.Value)
                {
                    _Firstname = (string)dr["FirstName"];
                }
                if (dr["UserName"] != DBNull.Value)
                {
                    _Uname = (string)dr["UserName"];
                }
                if (dr["Password"] != DBNull.Value)
                {
                    _UPass = (string)dr["Password"];
                }
            }
            //Release allocated memory
            dr.Close();
        }

        public static string GetFirstname
        {
            get
            {
                return Firstname;
            }
        }

        public static string GetUserName
        {
            get
            {
                return Uname;
            }
        }

        public static string GetUserPass
        {
            get
            {
                return UPass;
            }
        }
    }
}