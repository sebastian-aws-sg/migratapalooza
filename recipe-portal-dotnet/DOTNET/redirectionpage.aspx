<%@ Page Language="C#" AutoEventWireup="true" CodeFile="redirectionpage.aspx.cs" Inherits="redirectionpage" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>Redirecttion Page</title>
    <link href="CSS/cssreciaspx.css" rel="stylesheet" type="text/css" />
       <style type="text/css">
      body
      {
         font-family: Verdana, Arial, Serif;
         font-size: 12px;
      }
   </style>
</head>
<body>
<!--Begin Header-->
<div class="headerwrap">
<table border="0" cellpadding="0" cellspacing="0" width="97%">
  <tr>
   <td width="50%" rowspan="2" valign="top"><a title="Myasp-net.com" href="default.aspx"><img src="images/recipelogo.gif" width="357" height="70" border="0" alt="Myasp-net.com" /></a></td>
    <td width="50%" align="right" valign="top">

</td>
  </tr>
  <tr>
    <td width="50%" align="right"><span class="chdate"><%=DateTime.Now.ToString("f")%></span></td>
  </tr>
</table>
</div>
<!--End Header-->
    <form id="form1" runat="server">
    <asp:Panel ID="PanelWelcomeBack" Visible="false" runat="server">
       <div style="margin-left: auto; margin-right: auto; width: 600px; border: solid 1px #ABD8FC; padding: 10px; margin-top: 100px;">
       <h1 style="font-family: verdana, arial, helvetica, sans-serif; font-weight: bold; color: #FF9900; font-size:x-large; margin-bottom: 10px; padding-bottom: 1px;"><asp:Label ID="lblwelcomeusername" runat="server" /></h1>
       Enjoy your stay.
       <br />
       <br />
       Please wait, you will be redirected in 3 seconds back to the previous page.
       <br />
       <br />
       <asp:HyperLink ID="HyperLink1" runat="server" NavigateUrl="default.aspx" cssClass="content12">Go to home page</asp:HyperLink>
       </div>
   </asp:Panel>
   <asp:Panel ID="PanelThankYouLogout" Visible="false" runat="server">
       <div style="margin-left: auto; margin-right: auto; width: 600px; border: solid 1px #ABD8FC; padding: 10px; margin-top: 100px;">
       <h1 style="font-family: verdana, arial, helvetica, sans-serif; font-weight: bold; color: #FF9900; font-size:x-large; margin-bottom: 10px; padding-bottom: 1px;">Thank you for your participation</h1>
       We hope you enjoy your stay.
       <br />
       <br />
       Please wait, you will be redirected in 3 seconds back to the previous page.
       <br />
       <br />
       <asp:HyperLink ID="HyperLink2" runat="server" NavigateUrl="default.aspx" cssClass="content12">Go to home page</asp:HyperLink>
       </div>
   </asp:Panel>
   <asp:Panel ID="PanelForJoining" Visible="false" runat="server">
        <div style="margin-left: auto; margin-right: auto; width: 600px; border: solid 1px #ABD8FC; padding: 10px; margin-top: 100px;">
        <h1 style="font-family: verdana, arial, helvetica, sans-serif; font-weight: bold; color: #FF9900; font-size:x-large; margin-bottom: 10px; padding-bottom: 1px;"><asp:Label ID="lblheader" runat="server" /></h1>
        <div style="margin-top: 10px;">
        <asp:Label cssClass="content12" ID="lblsuccess" runat="server" />
        <br />
        <br />
        <asp:Label cssClass="content12" ID="lblwait" runat="server" Visible="false" />
        <br />
        <asp:HyperLink ID="HyperLink3" runat="server" NavigateUrl="default.aspx" cssClass="content12">Go Back to Homepage</asp:HyperLink>
        </div>
        </div>
   </asp:Panel>
   <asp:Panel ID="PanelAccountSuspended" Visible="false" runat="server">
       <div style="margin-left: auto; margin-right: auto; width: 600px; border: solid 1px #ABD8FC; padding: 10px; margin-top: 100px;">
       <h1 style="font-family: verdana, arial, helvetica, sans-serif; font-weight: bold; color: #CC3300; font-size:x-large; margin-bottom: 8px; padding-bottom: 1px;">Login Failed - Account Suspended</h1>
       Your account has been suspended due to the violation of our terms and agreement.
       <br />
       Contact the site Administrator to reinstate your account.
       <br />
       <br />
       Please wait, you will be redirected in 12 seconds back to the homepage.
       <br />
       <br />
       <asp:HyperLink ID="HyperLink4" runat="server" NavigateUrl="default.aspx" cssClass="content12">Go to home page</asp:HyperLink>
       </div>
   </asp:Panel>
   <asp:Panel ID="PanelAccountNotActivated" Visible="false" runat="server">
       <div style="margin-left: auto; margin-right: auto; width: 600px; border: solid 1px #ABD8FC; padding: 10px; margin-top: 100px;">
       <h1 style="font-family: verdana, arial, helvetica, sans-serif; font-weight: bold; color: #CC3300; font-size:x-large; margin-bottom: 8px; padding-bottom: 1px;">Login Failed</h1>
       Your account has not been activated. We sent you an email containing the activation link after you register for a new account.
       <br />
       Please check your email or <a title="Resend my activation email." href="resendactivationemail.aspx">click here</a> to resend your activation email.
       <br />
       If you still not receive the activation email, please contact the site Administrator.
       <br />
       <br />
       Please wait, you will be redirected in 12 seconds back to the homepage.
       <br />
       <br />
       <asp:HyperLink ID="HyperLink5" runat="server" NavigateUrl="default.aspx" cssClass="content12">Go to home page</asp:HyperLink>
        </div>
   </asp:Panel>
   <asp:Panel ID="PanelProfileUpdateSuccess" Visible="false" runat="server">
        <div style="margin-left: auto; margin-right: auto; width: 600px; border: solid 1px #ABD8FC; padding: 10px; margin-top: 100px;">
        <h1 style="font-family: verdana, arial, helvetica, sans-serif; font-weight: bold; color: #FF9900; font-size:x-large; margin-bottom: 10px; padding-bottom: 1px;"><asp:Label ID="lblheaderupdateprofilesuccess" runat="server" /></h1>
        <div style="margin-top: 10px;">
        <asp:Label cssClass="content12" ID="lblupdateprofilemsg" runat="server" />
        <br />
        <br />
        <asp:Label cssClass="content12" ID="lblupdateprofilewait" runat="server" Visible="false" />
        <asp:HyperLink ID="HyperLink6" runat="server" NavigateUrl="default.aspx" cssClass="content12">Go Back to Homepage</asp:HyperLink>
        </div>
        </div> 
   </asp:Panel>
    </form>
</body>
</html>
