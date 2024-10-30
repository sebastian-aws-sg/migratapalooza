#region XD World Recipe V 3
// FileName: users.cs
// Author: Dexter Zafra
// Date Created: 2/15/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;

namespace XDRecipe.Model
{
    /// <summary>
    /// Object in this class manages user entity and properties
    /// </summary>
    public class Users
    {
        //Defaut constructor
        public Users()
        {
        }

        #region Class Variables
        /// <summary>User ID</summary>
        protected int _UID;

        /// <summary>User Level</summary>
        protected int _UserLevel;

        /// <summary>User name</summary>
        protected string _Username;

        /// <summary>User Password</summary>
        protected string _Password;

        /// <summary>User Firstname</summary>
        protected string _FirstName;

        /// <summary>User Lasttname</summary>
        protected string _LastName;

        /// <summary>User City</summary>
        protected string _City;

        /// <summary>User State</summary>
        protected string _State;

        /// <summary>User Country</summary>
        protected string _Country;

        /// <summary>User Email</summary>
        protected string _Email;

        /// <summary>User Date of Birth</summary>
        protected DateTime _DOB;

        /// <summary>User AboutMe</summary>
        protected string _AboutMe;

        /// <summary>User FavoriteFoods1</summary>
        protected string _FavoriteFoods1;

        /// <summary>User FavoriteFoods2</summary>
        protected string _FavoriteFoods2;

        /// <summary>User FavoriteFoods3</summary>
        protected string _FavoriteFoods3;

        /// <summary>User Receive NewsLetter</summary>
        protected int _NewsLetter;

        /// <summary>User date joined</summary>
        protected DateTime _DateJoined;

        /// <summary>User Photo</summary>
        protected string _Photo;

        /// <summary>User Website</summary>
        protected string _Website;

        /// <summary>User profile hits</summary>
        protected int _Hits;

        /// <summary>User Posted Recipe Counter</summary>
        protected int _PostedRecipeCount;

        /// <summary>User Posted Article Counter</summary>
        protected int _PostedArticleCount;

        /// <summary>User saved recipe to cookbook counter</summary>
        protected int _SavedrecipeCount;

        /// <summary>User comment recipe counter</summary>
        protected int _CommentRecipeCount;

        /// <summary>User comment article counter</summary>
        protected int _CommentArticleCount;

        /// <summary>User friends list counter</summary>
        protected int _FriendsCount;

        /// <summary>User last login date</summary>
        protected DateTime _LastLogin;

        /// <summary>User last visit date</summary>
        protected DateTime _LastVisit;

        /// <summary>User last update date</summary>
        protected DateTime _LastUpdated;

        /// <summary>User allow other user contact</summary>
        protected int _ContactMe;

        /// <summary>User status</summary>
        protected int _isActive;

        protected string _GUID;

        protected int _Activation;

        protected string _UserRole;

        protected int _ShowFriendsListinProfile;

        protected int _ShowCookBookinProfile;

        protected int _NumRecordsCookBookQuickView;

        protected int _NumRecordsFriendsList;

        protected int _PreferredLayout;

        protected int _PreferredPageSize;

        protected int _IsUserChoosePreferredLayout;

        protected int _ReceivePM;

        protected int _PMEmailNotification;

        #endregion


        #region Properties
        public int UID
        {
            get { return _UID; }
            set { _UID = value; }
        }

        public int UserLevel
        {
            get { return _UserLevel; }
            set { _UserLevel = value; }
        }

        public string Username
        {
            get { return _Username; }
            set { _Username = value; }
        }

        public string Password
        {
            get { return _Password; }
            set { _Password = value; }
        }

        public string FirstName
        {
            get { return _FirstName; }
            set { _FirstName = value; }
        }

        public string LastName
        {
            get { return _LastName; }
            set { _LastName = value; }
        }

        public string City
        {
            get { return _City; }
            set { _City = value; }
        }

        public string State
        {
            get { return _State; }
            set { _State = value; }
        }

        public string Country
        {
            get { return _Country; }
            set { _Country = value; }
        }

        public string Email
        {
            get { return _Email; }
            set { _Email = value; }
        }

        public DateTime DOB
        {
            get { return _DOB; }
            set { _DOB = value; }
        }

        public string AboutMe
        {
            get { return _AboutMe; }
            set { _AboutMe = value; }
        }

        public string FavoriteFoods1
        {
            get { return _FavoriteFoods1; }
            set { _FavoriteFoods1 = value; }
        }

        public string FavoriteFoods2
        {
            get { return _FavoriteFoods2; }
            set { _FavoriteFoods2 = value; }
        }

        public string FavoriteFoods3
        {
            get { return _FavoriteFoods3; }
            set { _FavoriteFoods3 = value; }
        }

        public int NewsLetter
        {
            get { return _NewsLetter; }
            set { _NewsLetter = value; }
        }

        public DateTime DateJoined
        {
            get { return _DateJoined; }
            set { _DateJoined = value; }
        }

        public string Photo
        {
            get { return _Photo; }
            set { _Photo = value; }
        }

        public string Website
        {
            get { return _Website; }
            set { _Website = value; }
        }

        public int Hits
        {
            get { return _Hits; }
            set { _Hits = value; }
        }

        public int PostedRecipeCount
        {
            get { return _PostedRecipeCount; }
            set { _PostedRecipeCount = value; }
        }

        public int PostedArticleCount
        {
            get { return _PostedArticleCount; }
            set { _PostedArticleCount = value; }
        }

        public int SavedrecipeCount
        {
            get { return _SavedrecipeCount; }
            set { _SavedrecipeCount = value; }
        }

        public int CommentRecipeCount
        {
            get { return _CommentRecipeCount; }
            set { _CommentRecipeCount = value; }
        }

        public int CommentArticleCount
        {
            get { return _CommentArticleCount; }
            set { _CommentArticleCount = value; }
        }

        public int FriendsCount
        {
            get { return _FriendsCount; }
            set { _FriendsCount = value; }
        }

        public DateTime LastLogin
        {
            get { return _LastLogin; }
            set { _LastLogin = value; }
        }

        public DateTime LastVisit
        {
            get { return _LastVisit; }
            set { _LastVisit = value; }
        }

        public DateTime LastUpdated
        {
            get { return _LastUpdated; }
            set { _LastUpdated = value; }
        }

        public int ContactMe
        {
            get { return _ContactMe; }
            set { _ContactMe = value; }
        }

        public int isActive
        {
            get { return _isActive; }
            set { _isActive = value; }
        }

        public string GUID
        {
            get { return _GUID; }
            set { _GUID = value; }
        }

        public int Activation
        {
            get { return _Activation; }
            set { _Activation = value; }
        }

        public string UserRole
        {
            get { return _UserRole; }
            set { _UserRole = value; }
        }

        public int ShowFriendsListinProfile
        {
            get { return _ShowFriendsListinProfile; }
            set { _ShowFriendsListinProfile = value; }
        }

        public int ShowCookBookinProfile
        {
            get { return _ShowCookBookinProfile; }
            set { _ShowCookBookinProfile = value; }
        }

        public int NumRecordsCookBookQuickView
        {
            get { return _NumRecordsCookBookQuickView; }
            set { _NumRecordsCookBookQuickView = value; }
        }

        public int NumRecordsFriendsList
        {
            get { return _NumRecordsFriendsList; }
            set { _NumRecordsFriendsList = value; }
        }

        public int PreferredLayout
        {
            get { return _PreferredLayout; }
            set { _PreferredLayout = value; }
        }

        public int PreferredPageSize
        {
            get { return _PreferredPageSize; }
            set { _PreferredPageSize = value; }
        }

        public int IsUserChoosePreferredLayout
        {
            get { return _IsUserChoosePreferredLayout; }
            set { _IsUserChoosePreferredLayout = value; }
        }

        public int ReceivePM
        {
            get { return _ReceivePM; }
            set { _ReceivePM = value; }
        }

        public int PMEmailNotification
        {
            get { return _PMEmailNotification; }
            set { _PMEmailNotification = value; }
        }

        #endregion
    }
}