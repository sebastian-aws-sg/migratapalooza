#region XD World Recipe V 2.8
// FileName: editprofile.cs
// Author: Dexter Zafra
// Date Created: 2/14/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.IO;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using XDRecipe.UI;
using XDRecipe.BL;
using XDRecipe.BL.Providers;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;

public partial class editprofile : BasePage
{
    private string usercurrentemail;
    private string usercurrentpassword;

    protected void Page_Load(object sender, EventArgs e)
    {

        HideFormIfLogin.Visible = false;
        lblWarningMessage.Text = "Sorry! You must login to edit your profile.";
        lbllegendheader.Text = "Edit Profile";

        if (Authentication.IsUserAuthenticated)
        {
            ProviderUserDetails user = new ProviderUserDetails();

            user.FillUp(UserIdentity.UserID);

            lbllegendheader.Text = "Editting " + UserIdentity.UserName + "'s Profile";
            HideFormIfLogin.Visible = true;
            lblWarningMessage.Visible = false;

            Password1.Value = Encryption.Decrypt(user.Password);
            Password2.Value = Encryption.Decrypt(user.Password);
            Email.Value = user.Email;
            Firstname.Value = user.FirstName;
            Lastname.Value = user.LastName;
            City.Value = user.City;
            State.Value = user.State;
            Date1.CalendarDateString = user.DOB.ToShortDateString();
            FavoriteFoods1.Value = user.FavoriteFoods1;
            FavoriteFoods2.Value = user.FavoriteFoods2;
            FavoriteFoods3.Value = user.FavoriteFoods3;
            Website.Value = user.Website;
            AboutMe.Value = user.AboutMe;

            usercurrentemail = user.Email;
            usercurrentpassword = Encryption.Decrypt(user.Password);

            userimageedit.ImageUrl = GetUserImage.GetImage(user.Photo).ToString();

            string[] Countries = Utility.CountriesList;

            Country.Items.Insert(0, new ListItem(user.Country.ToString(), user.Country.ToString()));

            foreach (string country in Countries)
            {
                Country.Items.Add(country);
            }

            user = null;
        }
    }

    public void Update_User(object s, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            Utility Util = new Utility();

            if (Page.IsValid)
            {
                UserRepository User = new UserRepository();

                //Filters harmful scripts from input string.
                User.UID = UserIdentity.UserID;
                User.Username = UserIdentity.UserName;
                User.Password = Encryption.Encrypt(Util.FormatTextForInput(Request.Form[Password1.UniqueID]));
                User.FirstName = Util.FormatTextForInput(Request.Form[Firstname.UniqueID]);
                User.LastName = Util.FormatTextForInput(Request.Form[Lastname.UniqueID]);
                User.City = Util.FormatTextForInput(Request.Form[City.UniqueID]);
                User.State = Util.FormatTextForInput(Request.Form[State.UniqueID]);
                User.Country = Util.FormatTextForInput(Request.Form[Country.UniqueID]);
                User.DOB = DateTime.Parse(Date1.CalendarDateString);
                User.FavoriteFoods1 = Util.FormatTextForInput(Request.Form[FavoriteFoods1.UniqueID]);
                User.FavoriteFoods2 = Util.FormatTextForInput(Request.Form[FavoriteFoods2.UniqueID]);
                User.FavoriteFoods3 = Util.FormatTextForInput(Request.Form[FavoriteFoods3.UniqueID]);
                User.NewsLetter = Int32.Parse(Util.FormatTextForInput(Request.Form[Newsletter.UniqueID]));
                User.ContactMe = Int32.Parse(Util.FormatTextForInput(Request.Form[ContactMe.UniqueID]));
                User.Website = Util.FormatTextForInput(Request.Form[Website.UniqueID]);
                User.AboutMe = Util.FormatTextForInput(Request.Form[AboutMe.UniqueID]);

                #region Form Input Validation

                //Check if the user update the email by comparing the current user email against the email
                //input value.If the email changed, go to the next validation.
                if (Util.FormatTextForInput(Request.Form[Email.UniqueID]) != usercurrentemail)
                {
                    //Validate new email against database record. This prevent duplication.
                    if (Blogic.IsEmailExists(Util.FormatTextForInput(Request.Form[Email.UniqueID])))
                    {
                        lbvalenght.Text = "Error: Unable to update account. An account with the specified email already exists. Please choose another email.";
                        lbvalenght.Visible = true;
                        return;
                    }
                    else
                    {   //If the new email does not exists, update it.
                        User.Email = Util.FormatTextForInput(Request.Form[Email.UniqueID]);
                    }
                }
                else
                {
                    //If the email is the same, this means it has not been change, then assign an empty string.
                    //The stored procedure will do the rest of the magic.
                    User.Email = "";
                }

                //Let's decrypt the password for validation.
                if (!Validator.IsAlphaNumericOnly(Encryption.Decrypt(User.Password)))
                {
                    lbvalenght.Text = "<br>Error: Password must be at least 6 characters long and 12 characters maximun, and should only contain AlphaNumeric.";
                    lbvalenght.Visible = true;
                    return;
                }

                if (Util.FormatTextForInput(Request.Form[Password1.UniqueID]) != Util.FormatTextForInput(Request.Form[Password2.UniqueID]))
                {
                    lbvalenght.Text = "<br>Error: Password did not matach. Please re-enter a password and make sure they both match.";
                    lbvalenght.Visible = true;
                    return;
                }

                if (!Validator.IsValidEmail(Util.FormatTextForInput(Request.Form[Email.UniqueID])))
                {
                    lbvalenght.Text = "<br>Error: Invalid email address. Email address must be a valid format.";
                    lbvalenght.Visible = true;
                    return;
                }

                if (!Validator.IsValidName(User.FirstName))
                {
                    lbvalenght.Text = "<br>Error: Firstname should be alphabet and not contain illegal characters.";
                    lbvalenght.Visible = true;
                    return;
                }

                if (!Validator.IsValidName(User.LastName))
                {
                    lbvalenght.Text = "<br>Error: Firstname should be alphabet and not contain illegal characters.";
                    lbvalenght.Visible = true;
                    return;
                }

                if (User.FavoriteFoods1.Length > 25)
                {
                    lbvalenght.Text = "<br>Error: Favorite food 1 is too long. Maximum of 25 characters.";
                    lbvalenght.Visible = true;
                    return;
                }

                if (User.FavoriteFoods2.Length > 25)
                {
                    lbvalenght.Text = "<br>Error: Favorite food 2 is too long. Maximum of 25 characters.";
                    lbvalenght.Visible = true;
                    return;
                }

                if (User.FavoriteFoods3.Length > 25)
                {
                    lbvalenght.Text = "<br>Error: Favorite food 3 is too long. Maximum of 25 characters.";
                    lbvalenght.Visible = true;
                    return;
                }

                if (User.AboutMe.Length > 500)
                {
                    lbvalenght.Text = "<br>Error: About me text is too long. Maximum of 500 characters.";
                    lbvalenght.Visible = true;
                    return;
                }

                if (User.Website.Length > 75)
                {
                    lbvalenght.Text = "<br>Error: Website URL is too long. Maximum of 75 characters.";
                    lbvalenght.Visible = true;
                    return;
                }

                #endregion

                if (UserImageFileUpload.HasFile)
                {
                    int FileSize = UserImageFileUpload.PostedFile.ContentLength;
                    string contentType = UserImageFileUpload.PostedFile.ContentType;

                    //File type validation
                    if (!contentType.Equals("image/gif") &&
                        !contentType.Equals("image/jpeg") &&
                        !contentType.Equals("image/jpg") &&
                        !contentType.Equals("image/png"))
                    {
                        lbvalenght.Text = "<br>File format is invalid. Only gif, jpg, jpeg or png files are allowed.";
                        lbvalenght.Visible = true;
                        return;
                    }
                    // File size validation
                    if (FileSize > constant.UserImageMaxSize)
                    {
                        lbvalenght.Text = "<br>File size exceed the maximun allowed 60000 bytes";
                        lbvalenght.Visible = true;
                        return;

                    }
                }

                ImageUploadManager.UploadUserImage(User, PlaceHolder1, GetUserImage.ImagePathForUserPhoto, constant.UserImageMaxSize);

                if (User.Update(User) != 0)
                {
                    JSLiteral.Text = "Error occured while processing your submit.";
                    return;
                }

                string LogoutYesNo = "No";

                //Here we check if the password has been change. If the password was changed,
                //Let's logout the user.
                if (Request.Form[Password1.UniqueID] != usercurrentpassword)
                {
                    //Check if the cookies with name XDWRUserInfo exist on user's machine
                    if ((Request.Cookies["XDWRUserInfo"] != null))
                    {
                        //Expire the cookie
                        HttpCookie UserInfo = new HttpCookie("XDWRUserInfo");
                        UserInfo.Expires = DateTime.Now.AddDays(-31d);
                        Response.Cookies.Add(UserInfo);
                    }

                    //Use for session
                    if ((HttpContext.Current.Session["XDUsername"] != null) && (HttpContext.Current.Session["XDUpass"] != null))
                    {
                        HttpContext.Current.Session.Abandon();
                    }

                    LogoutYesNo = "Yes";
                }

                Response.Redirect("redirectionpage.aspx?uname=" + UserIdentity.UserName + "&uid=" + UserIdentity.UserID + "&logout=" + LogoutYesNo);

                User = null;
            }

            Util = null;
        }
    }
}

