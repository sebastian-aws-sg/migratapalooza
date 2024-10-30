using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Collections.Generic;
using System.Web;
using System.Globalization;
using System.Web.Security;
using System.Web.UI;
using System.Text;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using XDRecipe.BL.Providers.User;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Common.Utilities;
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;

//This page is a unit test for ExrendedCollection.
//You can remove/delete this page anytime you want.

/// <summary>
/// Object in this class manages email properties
/// </summary>
public class Car
{
    //Defaut constructor
    public Car()
    {
    }

    public Car(string personname, int speed)
    {
        this._PersonName = personname;
        this._Speed = speed;
    }

    protected string _PersonName;
    protected int _Speed;


    public string PersonName
    {
        get { return _PersonName; }
        set { _PersonName = value; }
    }
    public int Speed
    {
        get { return _Speed; }
        set { _Speed = value; }
    }
}

public partial class test : System.Web.UI.Page
{

    public string GetWeek(int weeknumber, int year)
    {
        DateTime dt = new DateTime(year, 1, 1).AddDays((weeknumber - 1) * 7);

        while (dt.DayOfWeek != CultureInfo.CurrentCulture.DateTimeFormat.FirstDayOfWeek)

            dt = dt.AddDays(-1);

        //return string.Format("{0:MM/dd/yyyy}-{1:MM/dd/yyyy}", dt, dt.AddDays(6));

        return dt.ToShortDateString() + " - " + dt.AddDays(6).ToShortDateString();
    }


    public string GetWeekStartDate(int year, int weeknumber)
    {
        DateTime dt = new DateTime(year, 1, 1).AddDays((weeknumber - 1) * 7);

        while (dt.DayOfWeek != CultureInfo.CurrentCulture.DateTimeFormat.FirstDayOfWeek)
            dt = dt.AddDays(-1);

        return dt.ToShortDateString();
    }

    public string GetWeekEndDate(int year, int weeknumber)
    {
        DateTime dt = new DateTime(year, 1, 1).AddDays((weeknumber - 1) * 7);

        while (dt.DayOfWeek != CultureInfo.CurrentCulture.DateTimeFormat.FirstDayOfWeek)
            dt = dt.AddDays(-1);

        return dt.AddDays(6).ToShortDateString();
    }

    public void PrintDay(int year, int month, DayOfWeek dayName)
    {
        CultureInfo ci = new CultureInfo("en-US");
        for (int i = 1; i <= ci.Calendar.GetDaysInMonth(year, month); i++)
        {
            if (new DateTime(year, month, i).DayOfWeek == dayName)
                Response.Write(i.ToString() + "<br/>");
        }
    }


    public string BuildCalendar(int month, int year)
    {
        DateTime VisibleDate = DateTime.Now.Date;
        DateTime CalMonth = new DateTime(year, month, 1);

        string[] days = new string[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

        int numDays = DateTime.DaysInMonth(year, month);
        int offset = (Int32)Enum.Parse(typeof(DayOfWeek), CalMonth.DayOfWeek.ToString());
        int position = 0;

        StringBuilder HTMLOutput = new StringBuilder();

        DateTime PrevMonth = CalMonth.AddDays(-(offset + 1));
        DateTime NextMonth = CalMonth.AddMonths(1).AddDays(-1).Date;

        HTMLOutput.Append("<table border='0' cellpadding='0' cellspacing='0' style='border-left: 1px solid #E1EDFF; border-top: 1px solid #E1EDFF;'>");
        HTMLOutput.Append("<tr>");
        HTMLOutput.Append("<td colspan='7' align='center' style='border-right: 1px solid #E1EDFF; background-color: #84B3FF; height: 32px;'><b>" + Utility.GetMonthNameOrAbbrev(CalMonth.Month, true) + " " + CalMonth.Year + "</b></td>");
        HTMLOutput.Append("</tr>");

        foreach (string day in days)
        {
            HTMLOutput.Append("<td align='center' width='140' style='border-right: 1px solid #E1EDFF; border-top: 1px solid #E1EDFF; border-bottom: 1px solid #D9E7FF; background-color: #C4DAFF;'>" + day + "</td>");
        }

        HTMLOutput.Append("</tr>");

        for (int i = 1 - offset; i <= numDays; i++)
        {
            if (position % 7 == 0)
            {
                HTMLOutput.Append("<tr>");
            }
            if (i < 1)
            {
                HTMLOutput.Append("<td align='center'><div style='text-align: right; padding-right: 3px; border-right: 1px Solid #E1EDFF; background-color: #ECF3FF;'>" + PrevMonth.AddDays(i + offset).Day.ToString() + "</div>");
                HTMLOutput.Append("<div style='height: 100px; background-color: #f7f7f7; border-right: 1px Solid #E1EDFF; border-bottom: 1px Solid #E1EDFF;''>");
                HTMLOutput.Append(PrevMonth.AddDays(i + offset).ToShortDateString());
                HTMLOutput.Append("</div>");
                HTMLOutput.Append("</td>");
            }
            else if (i == VisibleDate.Day && year == VisibleDate.Year && month == VisibleDate.Month)
            {
                HTMLOutput.Append("<td align='center'><div style='text-align: right; padding-right: 3px; border-right: 1px Solid #E1EDFF; background-color: #ECF3FF;'><b>" + i.ToString() + "</b></div>");
                HTMLOutput.Append("<div style='height: 100px; border-right: 1px Solid #E1EDFF; border-bottom: 1px Solid #E1EDFF;'#E1EDFF;'>");
                HTMLOutput.Append(DateTime.Now.Date.ToShortDateString());
                HTMLOutput.Append("</div>");
                HTMLOutput.Append("</td>");
            }
            else
            {
                HTMLOutput.Append("<td align='center'><div style='text-align: right; padding-right: 3px; border-right: 1px Solid #E1EDFF; background-color: #ECF3FF;'>" + i.ToString() + "</div>");
                HTMLOutput.Append("<div style='height: 100px; border-right: 1px Solid #E1EDFF; border-bottom: 1px Solid #E1EDFF;''>");
                HTMLOutput.Append(PrevMonth.AddDays(i + offset).ToShortDateString());
                HTMLOutput.Append("</div>");
                HTMLOutput.Append("</td>");
            }

            position += 1;

            if (position % 7 == 0)
                HTMLOutput.Append("</tr>");
        }

        if (position % 7 != 0)
        {
            for (int j = 1; j <= 7 - (position % 7); j++)
            {
                HTMLOutput.Append("<td align='center'><div style='text-align: right; padding-right: 3px; border-right: 1px Solid #E1EDFF; background-color: #ECF3FF;'>" + NextMonth.AddDays(j).Day.ToString() + "</div>");
                HTMLOutput.Append("<div style='height: 100px; background-color: #f7f7f7; border-right: 1px Solid #E1EDFF; border-bottom: 1px Solid #E1EDFF;'>");
                HTMLOutput.Append(NextMonth.AddDays(j).ToShortDateString());
                HTMLOutput.Append("</div>");
                HTMLOutput.Append("</td>");
            }

            HTMLOutput.Append("</tr>");
        }

        HTMLOutput.Append("</table>");

        return HTMLOutput.ToString();
    }

    private int GetWeekNumber(DateTime date)
    {
        CultureInfo ciCurr = CultureInfo.CurrentCulture;
        int weekNum = ciCurr.Calendar.GetWeekOfYear(date, CalendarWeekRule.FirstFourDayWeek, DayOfWeek.Monday);
        return weekNum;
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        Response.Write("<br><br>");
        Response.Write(BuildCalendar(5, 2009));
        Response.Write("<br><br>");

        int userids = 1;
        //Refresh cached data
        //Caching.PurgeCacheItems("MyCookBook_SideMenu_" + userids);

        //This is the generic extended collection unit test page.
        //You can remove/delete this page anytime you want.

        //Caching.PurgeCacheItems("Newest_Articles");
        //Caching.PurgeCacheItems("ArticleCategory_SideMenu");
        //Caching.PurgeCacheItems("Last5_ArticlePublishedByUser_1");

        //Blogic.UpdateArticleCommentAdmin(ID, Comment);
        //int SenderUID = 2;
        //string ToUserName = "Administrator";
        //Response.Write("IsUserBlockedFomSendingPM = " + Blogic.IsUserBlockedByRecipient(ToUserName, SenderUID) + "<br>");
        //Response.Write("<br><br>");

        /*
        PrivateMessageRepository Message = new PrivateMessageRepository();

        string mode = Request.QueryString["method"];

        Message.Subject = "How are you Webmaster?";
        Message.RecipientUserName = "Webmaster";
        Message.SenderUserID = userids;
        Message.Message = "What'sbyte up dude";

        Message.Add(Message);

        Message = null;
         */

        //int UID = 2;
        /*
        int GetUserID = Blogic.GetUserIDByUsername("Webmaster");
        UserFeaturesConfiguration.Fetch(GetUserID);
        Response.Write("IsReceivePM = " + UserFeaturesConfiguration.IsUserChooseToReceivePM + "<br>");
        Response.Write("IsReceiveEmailPMAlert = " + UserFeaturesConfiguration.IsUserChooseToReceiveEmailAlertReceivePM + "<br>");
        Response.Write("<br><br>");
         */

        //string strText = "C# substring function";

        /*
        //Response.Write(strText.Substring(0, 1) + "<br><br>");
        string strText = "8:30 AM";
        string strText2 = "10:00 AM";
        Response.Write(strText.Substring(1, 1) + "<br><br>");
        Response.Write(strText2.Substring(0, 2) + "<br><br>");

        Response.Write("IsPM = " + IsPM(strText2) + "<br><br>");

        //AM
        if (IsIndex1AColon(strText) && !IsIndex1AColon(strText2) && !IsPM(strText) && !IsPM(strText2))
        {
            //string combined = strText2.Substring(0, 1) + "" + strText2.Substring(1, 1);
            int HrDiff = HoursDiff(int.Parse(strText.Substring(0, 1)), int.Parse(strText2.Substring(0, 2)));
            Response.Write("HrDiff = [ " + HrDiff + " ]<br><br>");
        }

        //AM to PM
        if (IsIndex1AColon(strText) && !IsIndex1AColon(strText2) && !IsPM(strText) && !IsPM(strText2))
        {
            //string combined = strText2.Substring(0, 1) + "" + strText2.Substring(1, 1);
            int HrDiff = HoursDiff(int.Parse(strText.Substring(0, 1)), int.Parse(strText2.Substring(0, 2)));
            Response.Write("HrDiff = [ " + HrDiff + " ]<br><br>");
        }
        */

        /*
        DateTime bd = DateTime.Parse("4/26/2009");
        DateTime ed = DateTime.Parse("5/2/2009");

        int beginDateNum = bd.Day;
        int endDateNum = ed.Day;

        Response.Write("beginDateNum = [ " + beginDateNum + " ]<br><br>");
        Response.Write("endDateNum = [ " + endDateNum + " ]<br><br>");

        Response.Write("<table cellpadding='0' cellspacing='0' width='100%'>");
        Response.Write("<tr>");
        for (int i = beginDateNum; i < endDateNum + 1; i++)
        {
            //Response.Write("sequentialDayNum = [ " + i + " ]<br><br>");
            Response.Write("<td width='15%'><div style='background-color: #ECF3FF;'><span class='content01'>" + i + "</span></div></td>");
        }
        Response.Write("</tr>");
        Response.Write("</table>");
         */

        /*
        DateTime bd = DateTime.Parse("4/26/2009");
        DateTime ed = DateTime.Parse("5/2/2009");
        TimeSpan ts = ed.Subtract(bd);
        Response.Write("countDays = " + ts.Days + "<br><br>");
        for (int i = 0; i < ts.Days; i++)
        {
            Response.Write("sequentialDayNum = [ " + i + " ]<br><br>");
        }
         */

        /*
        DateTime dt = DateTime.Parse("4/26/2009");
        int difference = -((int)dt.DayOfWeek);
        DateTime start = dt.AddDays(difference).Date;
        DateTime end = start.AddDays(7).Date.AddMilliseconds(-1);

        for (int i = 0; i < 7; i++)
        {
            Response.Write("sequentialDayNum = [ " + start.AddDays(i).Day + " ]<br><br>");
        }
         */
        /*
        int EventID = 1;
        string EventURL = "\"showEvent(" + EventID + ",'top','left')\"";
        Response.Write(EventURL);
         */

        //DateTime startDate;
        //DateTime endDate;

        //Response.Write("GetWeekStartDate = " + GetWeekStartDate(2009, 18) + "<br>");
        //PrintDay(2009, 5, DayOfWeek.Sunday);


        /*
        Response.Write("startDate = " + start + "<br>");
        Response.Write("endDate = " + end + "<br>");
        Response.Write("<br><br>");
         */

        //Response.Write("IsNumeric = " + Utility.IsNumeric(Request.QueryString["num"]) + "<br>");
        //Response.Write("<br><br>");

        DateTime testdate = Convert.ToDateTime("4/26/2009");

        Response.Write("Day Name = " + testdate.DayOfWeek.ToString() + "<br>");

        //Response.Write("Week Number = " + GetWeekNumber(DateTime.Now.AddDays(1).Date) + "<br>");
        //Response.Write("Week Number = " + GetWeekNumber(testdate.AddDays(1).Date) + "<br>");
        //Response.Write("Current Date = " + testdate.AddDays(1).Date + "<br>");
        Response.Write("<br><br>");

        ExtendedCollection<int> dinos = new ExtendedCollection<int>();
        ExtendedCollection<string> dinosaurs = new ExtendedCollection<string>();
        ExtendedCollection<string> MyList = dinosaurs;

        ExtendedCollection<string> mystring = new ExtendedCollection<string>();

        ExtendedCollection<string> mytest = new ExtendedCollection<string>();

        ExtendedCollection<string> mysublist = mystring.FindAll(EndsWithSaurus);

        ExtendedCollection<int> a = new ExtendedCollection<int>();

        ProviderUserDetails user = new ProviderUserDetails();

        user.UID = 1;

        user.FillUp(1);

        Response.Write("Encrypt Username = " + Encryption.Encrypt("zafra") + "<br>");
        //Response.Write("Encrypted Admin Password = " + HttpContext.Current.Session["adminpassword"].ToString() + "<br>");
        Response.Write("<br><br>");

        //Response.Write("Email = " + user.Email.ToString() + "<br>");
        Response.Write("UserRole = " + UserIdentity.UserRole + "<br>");

        Response.Write("<br><br>");

        //Response.Write("IsUserSessionExists = " + CookieLoginHelper.IsLoginSessionExists + "<br>");
        //Response.Write("IsAdminLoginSessionExists = " + CookieLoginHelper.IsLoginAdminSessionExists + "<br>");
        string date = "1/5/2009";
        Response.Write("CustomDateFormat = " + Utility.FormatDate(DateTime.Parse(date)) + "<br>");


        Response.Write("<br><br>");

        UserFeaturesConfiguration.Fetch(1);
        Response.Write("UserConfigFeatures = " + UserFeaturesConfiguration.GetNumRecordsCookBookShow.ToString() + "<br>");

        Response.Write("<br><br>");

        string unametest = "Teststststststststs";

        if (unametest.Length > 50)
        {
            Response.Write("About me lenght = Too long.");
        }
        else
        {
            Response.Write("About me lenght = OK");
        }

        Response.Write("<br>");
        DateTime Age;
        Age = DateTime.Parse("12/5/1997");
        Response.Write("IsValidAge = " + Validator.IsValidAge(Age, 10) + "<br>");
        Response.Write("IsAlphaNumericOnly = " + Validator.IsAlphaNumericOnly("") + "<br>");
        Response.Write("IsValidName = " + Validator.IsValidName("Jun jun") + "<br>");
        Response.Write("IsValidEmail = " + Validator.IsValidEmail("dexterzafra@quotit.com") + "<br>");
        Response.Write("IsUserAuthenticated = " + Authentication.IsUserAuthenticated);   
        Response.Write("<br><br>");

        Response.Write("<br><br>");

        UserNameAndEmailValidation.Param("DeZaf", "hey@mydomain.com");

        Response.Write("Username and Email = " + UserNameAndEmailValidation.ErrMsg);

        Response.Write("<br><br>");

        Response.Write("Username and Email Boolean Result = " + UserNameAndEmailValidation.IsValid);

        Response.Write("<br><br>");

        string Filename = "adminphotowafo.gif";

        string Exten = Filename.Substring(Filename.Length - 4);
        
        string GetName = Filename.Substring(0, Filename.Length - 4);

        Response.Write("Filanem = " + GetName.ToString() + " Exten = " + Exten);

        Response.Write("<br><br>");

        HttpCookie GetUserInfo = HttpContext.Current.Request.Cookies["XDWRUserInfo"];

        if (GetUserInfo != null)
        {
            Response.Write("UnEncrypted CookieUsername = " + GetUserInfo.Values[0].ToString() + "<br>UnEncrypted password = " + GetUserInfo.Values[1].ToString());
        }

        Response.Write("<br><br>");

        //Response.Write("NewGuid = " + Guid.NewGuid().ToString("N"));

        Response.Write("<br><br>");

        /*
        //Email test
        //Instantiate emailtemplate object
        EmailTemplate SendeMail = new EmailTemplate();

        SendeMail.RecipientEmail = "karlos25@yahoo.com";

        //Send the activation link
        SendeMail.SendActivationLink("karlos25", Guid.NewGuid().ToString("N"));

        SendeMail = null;

        user = null;

        EmailTemplate SendCredential = new EmailTemplate();

        lostpassword.GetUserCredential("dexter007@yahoo.com");

        SendCredential.SendPassword("dexter007@yahoo.com", lostpassword.GetFirstname, lostpassword.GetUserName, Encryption.Decrypt(lostpassword.GetUserPass));

        SendCredential = null;
         */

        /*
        EmailTemplate SendeMail = new EmailTemplate();

        SendeMail.SendAccountSuspensionReinstateEmail("malony25@yahoo.com", "dotnetdude", "Reinstate Account", 2);

        SendeMail = null;
         */

        a.Add(4);
        a.Add(2);
        a.Add(7);
        a.Add(95);
        a.Add(3);
        a.Add(45);
        a.Add(5);
        a.Add(6);
        a.Add(1);
        a.Add(9);
        a.Add(18);
        a.Add(29);
        a.SortAscending();
        //a.Reverse();
        //a.SortDescending();
        //a.ReplaceWithByItem(3, 10); 

        Response.Write("GetSumInt: " + a.GetSumInt(a.ToArray()).ToString() + "<br>");

        int[] myIntArray = a.ToArray();
        //int[] myArray = new int[] { 22, 10, 4, 6, 33, 7, 8, 9, 11, 35, 48, 64, 78 };

        Response.Write("GetMaxInt: " + a.GetMaxInt(myIntArray).ToString() + "<br>");
        Response.Write("GetMaxInt: " + a.GetMaxInt(a.ToArray()).ToString() + "<br>");
        Response.Write("GetMinInt: " + a.GetMinInt(a.ToArray()).ToString() + "<br>");

        ExtendedCollection<double> d = new ExtendedCollection<double>();

        d.Add(4.1);
        d.Add(6.2);
        d.Add(7.9);
        d.Add(15.8);
        d.Add(3.5);
        d.Add(45.6);
        d.Add(54.6);

        Response.Write("<br>");

        Response.Write("GetMinDouble: " + d.GetMinDouble(d.ToArray()).ToString() + "<br>");
        Response.Write("GetMaxDouble: " + d.GetMaxDouble(d.ToArray()).ToString() + "<br>");
        Response.Write("GetSumDouble: " + d.GetSumDouble(d.ToArray()).ToString() + "<br>");

        Response.Write("<br>");


        foreach (int s in a)
        {
            Response.Write(s.ToString() + "<br>");
        }

        Response.Write("<br>");

        ExtendedCollection<string> x = new ExtendedCollection<string>();
        x.Add("Hello");
        x.Add("There");
        x.Add("World");
        x.Add("Four");
        x.Add("Three");
        x.Add("Two");
        x.Add("One");

        ExtendedCollection<string> l = new ExtendedCollection<string>();
        l.Add("one");
        l.Add("two");
        l.Add("three");
        l.Add("four");
        l.Add("five");
        //l.TrimExcess();

        // B.
        string[] s1 = l.ToArray();

        Response.Write("ToArrayLenght = " + s1.Length.ToString() + "<br>");
        Response.Write("ToArrayGetValue = " + s1.GetValue(2).ToString() + "<br>");

        Response.Write("<br>");


        // Make a collection of Cars.
        ExtendedCollection<Car> myCars = new ExtendedCollection<Car>();
        myCars.Add(new Car("Rusty", 20));
        myCars.Add(new Car("Zippy", 90));
        myCars.Add(new Car("Rudy", 60));
        myCars.Add(new Car("Tom", 60));
        myCars.Add(new Car("Henry", 70));
        myCars.Add(new Car("Carl", 50));
        myCars.Add(new Car("Robert", 60));
        myCars.RemoveAt(1);
        myCars.RemoveAt(3);

        foreach (Car c in myCars)
        {
            Response.Write("PersonName: " + c.PersonName + " , Speed: " + c.Speed + "<br>");
        }

        Response.Write("<br>");

        Response.Write("<b>Removed Items History:</b>" + "<br>");
        foreach (Car cr in myCars.RemovedItemHistory)
        {
            Response.Write("PersonName: " + cr.PersonName + " , Speed: " + cr.Speed + "<br>");
        }

        Response.Write("<br>");
        Response.Write("RemoveItemCount: " + myCars.RemovedItemCounter.ToString() + "<br>");
        Response.Write("Exist: " + l.Exists(TestForLength5) + "<br>");
        Response.Write("Capacity: " + l.Capacity.ToString() + "<br>");
        Response.Write("TrueForAll = " + l.TrueForAll(TestForLength5) + "<br>");

        Response.Write("<br><br>");

        foreach (string s in x.FindAll(TestForLength5))
        {
            Response.Write(s.ToString() + "<br>");
        }

        Response.Write("<br>");

        /*
        mystring.Add("One");
        mystring.Add("Four");
        mystring.Add("Two");
        mystring.Add("Five");
        mystring.Add("Six");
        mystring.Add("Three");
        mystring.Sort();
         */

        mystring.Add("Compsognathus");
        mystring.Add("Testsaurus");
        mystring.Add("Amargasaurus");
        mystring.Add("Oviraptor");
        mystring.Add("Tsaurus");
        mystring.Add("Velociraptor");
        mystring.Add("Masasaurus");
        mystring.Add("Deinonychus");
        mystring.Add("Dilophosaurus");
        mystring.Add("Gallimimus");
        mystring.Add("Triceratops");
        mystring.Add("Sagasaurus");
        mystring.Add("Taurus");
        mystring.Add("Elephant");
        mystring.Add("Dragonsaurus");


        mytest.Add("One");
        mytest.Add("Two");
        mytest.Add("Three");
        mytest.Add("Four");
        mytest.Add("Five");
        mytest.Add("Six");
        mytest.Add("Seven");
        mytest.Add("Eight");
        mytest.Add("Nine");
        mytest.Add("Ten");

        mytest.ReplaceWithByIndex(1, "Test1");
        mytest.ReplaceWithByItem("Four", "Test2");

        Response.Write("<br>");
        foreach (string mt in mytest)
        {
            Response.Write(mt.ToString() + "<br>");
        }
        Response.Write("<br>");

        //mystring.RemoveRange(2, 2);

        //mystring.Reverse();
        //mystring.Reverse(1,4);

        string[] output = mystring.GetRange(2, 3).ToArray();
        foreach (string dinosaur in output)
        {
            Response.Write(dinosaur.ToString() + "<br>");
        }

        Response.Write("<br>");

        foreach (string st in mysublist)
        {
            Response.Write(st + "<br>");
        }

        Response.Write("<br>");

        foreach (string str in mystring)
        {
            Response.Write(str + "<br>");
        }

        Response.Write("<br>");
        Response.Write("GetValue mystring = " + mystring.GetValue(6).ToString() + "<br>");
        Response.Write("<br>");
        Response.Write("mystringcount = " + mystring.Count.ToString() + "<br>");
        Response.Write("GetValue = " + mystring[4] + "<br>");

        Response.Write("<br>");

        dinos.Add(1);
        dinos.Add(2);
        dinos.Add(3);
        dinos.Add(4);
        dinos.Add(5);
        dinos.Add(6);
        dinos.Remove(3);
        

        foreach (int i in dinos)
        {
            Response.Write(i + "<br>");
        }
    
        dinosaurs.Add("Compsognathus");
        dinosaurs.Add("Testsaurus");
        dinosaurs.Add("Amargasaurus");
        dinosaurs.Add("Oviraptor");
        dinosaurs.Add("Tsaurus");
        dinosaurs.Add("Velociraptor");
        dinosaurs.Add("Deinonychus");
        dinosaurs.Add("Dilophosaurus");
        dinosaurs.Add("Gallimimus");
        dinosaurs.Add("Triceratops");
        dinosaurs.Insert(2, "Arrarrathus");
        dinosaurs.ReplaceWithByIndex(2, "Test");
        dinosaurs.ReplaceWithByItem("Deinonychus", "Test2");
        dinosaurs.RemoveByItem("Velociraptor");
        dinosaurs.RemoveAt(5);

        Response.Write("<br>");
        for (int i = 0; i < dinosaurs.Count; i++)
        {
            //Response.Write(MyList[i].ToString() + "<br>");
            Response.Write(dinosaurs.GetValue(i).ToString() + "<br>");
        }

        Response.Write("<br>");
        foreach (string dinosaur in dinosaurs)
        {
            Response.Write(dinosaur.ToString() + "<br>");
        }
        
        Response.Write("<br>");
        Response.Write("Find = " + dinosaurs.Find(EndsWithSaurus) + "<br>");
        Response.Write("Dinosaurs count = " + dinosaurs.Count.ToString() + "<br>");
        Response.Write("FindIndexOf = " + dinosaurs.IndexOf("Amargasaurus").ToString() + "<br>");
        Response.Write("LastIndexOf = " + dinosaurs.FindLastIndexOf("Gallimimus") + "<br>");
        Response.Write("IndexOf = " + dinosaurs.IndexOf("Dilophosaurus") + "<br>");
        Response.Write("GetLastIndex = " + dinosaurs.GetLastIndex.ToString() + "<br>");
        Response.Write("GetValue = " + dinosaurs[7] + "<br>");
        Response.Write("GetFirstIndexValue = " + dinosaurs.GetFirstIndexValue.ToString() + "<br>");
        Response.Write("GetLastIndexValue = " + dinosaurs.GetLastIndexValue.ToString() + "<br>");
        Response.Write("GetIndexValue = " + dinosaurs.GetValue(6).ToString() + "<br>");
        Response.Write("TrueForAll = " + dinosaurs.TrueForAll(EndsWithSaurus) + "<br>");
        Response.Write("Contained Dilophosaurus = " + dinosaurs.Contains("Dilophosaurus"));

    }

    static bool TestForLength5(string x)
    {
        return x.Length == 5;
    }

    public static int ConvertFloatToInt(float x)
    {
        return Convert.ToInt32(x);
    }


    private static long IntToLongConverter(int i)
    {
        return i;
    }

    // Search predicate returns true if a string ends in "saurus".
    private static bool EndsWithSaurus(String s)
    {
        if ((s.Length > 5) &&
            (s.Substring(s.Length - 6).ToLower() == "saurus"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // Search predicate returns true if a string ends in "saurus".
    private static bool EndsWithCrazy(String s)
    {
        if ((s.Length > 5) &&
            (s.Substring(s.Length - 6).ToLower() == "crazy"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static int CompareDinosByLength(string x, string y)
    {
        if (x == null)
        {
            if (y == null)
            {
                // If x is null and y is null, they're
                // equal. 
                return 0;
            }
            else
            {
                // If x is null and y is not null, y
                // is greater. 
                return -1;
            }
        }
        else
        {
            // If x is not null...
            //
            if (y == null)
            // ...and y is null, x is greater.
            {
                return 1;
            }
            else
            {
                // ...and y is not null, compare the 
                // lengths of the two strings.
                //
                int retval = x.Length.CompareTo(y.Length);

                if (retval != 0)
                {
                    // If the strings are not of equal length,
                    // the longer string is greater.
                    //
                    return retval;
                }
                else
                {
                    // If the strings are of equal length,
                    // sort them with ordinary string comparison.
                    //
                    return x.CompareTo(y);
                }
            }
        }

    }

}
