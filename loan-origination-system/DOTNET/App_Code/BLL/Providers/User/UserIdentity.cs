#region XD World Recipe V 3
// FileName: UserIdentity.cs
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
using XDRecipe.BL;
using XDRecipe.BL.Providers.User;
using XDRecipe.Security;

namespace XDRecipe.BL.Providers.User
{
    /// <summary>
    /// Object in this class manages users partial identity susch as the userid, username and email.
    /// </summary>
    public static class UserIdentity
    {
        /// <summary>
        /// Returns int UserID. If the user is not login, this return 0.
        /// </summary>
        public static int UserID
        {
            get
            {
                int UID = 0;

                //Check if the cookies with name XDWRUserInfo exist on user's machine
                if (CookieLoginHelper.IsLoginCookieExists)
                {
                    //Get int userid
                    //Decrypt the username stored in the cookie so it match to our database record.
                    UID = Blogic.GetUserID(Encryption.Decrypt(CookieLoginHelper.LoginCookie.Values[0].ToString()), CookieLoginHelper.LoginCookie.Values[1].ToString());
                }

                //Get the user credential in session if user did not check remember me.
                if (CookieLoginHelper.IsLoginSessionExists)
                {
                    //Get int userid
                    UID = Blogic.GetUserID(CookieLoginHelper.UserSessionUserName.ToString(), CookieLoginHelper.UserSessionPassword.ToString());
                }

                return UID;
            }
        }

        /// <summary>
        /// Returns logon username. This is only use after user is authenticated or have login.
        /// </summary>
        public static string UserName
        {
            get
            {
                string User_Name = "";

                //Check if the cookies with name XDWRUserInfo exist on user's machine
                if (CookieLoginHelper.IsLoginCookieExists)
                {
                    //Decrypt the username stored in the cookie so it will show the decrypted value.
                    User_Name = Encryption.Decrypt(CookieLoginHelper.LoginCookie.Values[0].ToString());
                }

                //Get the user credential in session if user did not check remember me.
                if (CookieLoginHelper.IsLoginSessionExists)
                {
                    User_Name = CookieLoginHelper.UserSessionUserName.ToString();
                }

                return User_Name;
            }
        }

        /// <summary>
        /// Returns logon user email address. This is only use after user is authenticated or have login.
        /// </summary>
        public static string UserEmail
        {
            get
            {
                ProviderUserDetails User = new ProviderUserDetails();

                User.FillUp(UserID);

                string UEmail = "";

                //Check if the cookies with name XDWRUserInfo exist on user's machine
                if (CookieLoginHelper.IsLoginCookieExists)
                {
                    UEmail = User.Email;
                }

                //Get the user credential in session if user did not check remember me.
                if (CookieLoginHelper.IsLoginSessionExists)
                {
                    UEmail = User.Email;
                }

                User = null;

                return UEmail;
            }
        }


        /// <summary>
        /// Returns user Role/Level.
        /// </summary>
        //The user level in the database is 3 and 1.
        //3 = Admin and 1 = Regular user. You can add another level i.e. 2 = Editor
        public static string UserRole
        {
            get
            {
                ProviderUserDetails User = new ProviderUserDetails();

                User.FillUp(UserID);

                int URole = 0;

                //Check if the cookies with name XDWRUserInfo exist on user's machine
                if (CookieLoginHelper.IsLoginCookieExists)
                {
                    URole = User.UserLevel;
                }

                //Get the user credential in session if user did not check remember me.
                if (CookieLoginHelper.IsLoginSessionExists)
                {
                    URole = User.UserLevel;
                }

                User = null;

                if (URole == 3)
                {
                    return "Administrator";
                }
                else
                {
                    return "Regular";
                }
            }
        }

        /// <summary>
        /// Returns username for the Admin section.
        /// </summary>
        public static string AdminUsername
        {
            get { return CookieLoginHelper.AdminLoginSessionUsername.ToString(); }
        }
    }
}
