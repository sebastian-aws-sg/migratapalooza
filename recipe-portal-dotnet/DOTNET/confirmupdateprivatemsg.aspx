<%@ Page Language="C#" AutoEventWireup="true" CodeFile="confirmupdateprivatemsg.aspx.cs" Inherits="confirmupdateprivatemsg" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
<META HTTP-EQUIV="refresh" CONTENT="3; URL=pmview.aspx">
    <title>Confirmation Update Private Message</title>
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
<br />
<br />
<br />
<div style="text-align: center; margin-top: 35px;"><h1 style="font-family: verdana, arial, helvetica, sans-serif; font-weight: bold; color: #000; font-size: 16px; margin-bottom: 10px; padding-bottom: 1px;"><asp:Label ID="lblsuccess" runat="server" /></h1></div>
<div style="text-align: center; margin-top: 20px;">
<span class="content12">Please wait, you will be redirected back to you Private Message Inbox page.</span>
<br /><br />
<asp:HyperLink ID="HyperLink1" runat="server" NavigateUrl="pmview.aspx" cssClass="content12">Back to My PM Inbox</asp:HyperLink>
</div>   
    </form>
</body>
</html>
