#region XD World Recipe V 3
// FileName: FriendsList.cs
// Author: Dexter Zafra
// Date Created: 2/30/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;

namespace XDRecipe.Model
{
    /// <summary>
    /// Object in this class manges Friends List
    /// </summary>
    public class FriendsList : DBObj
    {
        public FriendsList()
        {
        }

        protected int _ID;

        protected int _TotalCount;

        protected int _UID;

        protected int _FriendID;

        protected string _Username;

        protected string _Email;

        protected DateTime _Date;

        protected string _FirstName;

        protected string _LastName;

        protected string _Country;

        protected string _Photo;

        protected int _Hits;

        protected DateTime _LastVisit;

        protected DateTime _DateJoined;

        protected int _NumRecords;

        protected int _IsApproved;


        public int ID
        {
            get { return _ID; }
            set { _ID = value; }
        }
        public int TotalCount
        {
            get { return _TotalCount; }
            set { _TotalCount = value; }
        }
        public int UID
        {
            get { return _UID; }
            set { _UID = value; }
        }
        public int FriendID
        {
            get { return _FriendID; }
            set { _FriendID = value; }
        }
        public string Username
        {
            get { return _Username; }
            set { _Username = value; }
        }
        public string Email
        {
            get { return _Email; }
            set { _Email = value; }
        }
        public DateTime Date
        {
            get { return _Date; }
            set { _Date = value; }
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
        public string Country
        {
            get { return _Country; }
            set { _Country = value; }
        }
        public string Photo
        {
            get { return _Photo; }
            set { _Photo = value; }
        }
        public int Hits
        {
            get { return _Hits; }
            set { _Hits = value; }
        }
        public DateTime LastVisit
        {
            get { return _LastVisit; }
            set { _LastVisit = value; }
        }
        public DateTime DateJoined
        {
            get { return _DateJoined; }
            set { _DateJoined = value; }
        }
        public int NumRecords
        {
            get { return _NumRecords; }
            set { _NumRecords = value; }
        }
        public int IsApproved
        {
            get { return _IsApproved; }
            set { _IsApproved = value; }
        }
    }
}
