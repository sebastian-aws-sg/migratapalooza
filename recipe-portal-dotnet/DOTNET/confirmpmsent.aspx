<%@ Page Language="C#" AutoEventWireup="true" CodeFile="confirmpmsent.aspx.cs" Inherits="confirmpmsent" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head id="Head1" runat="server">
    <META HTTP-EQUIV="refresh" CONTENT="3; URL=pmview.aspx">
    <link href="CSS/cssreciaspx.css" rel="stylesheet" type="text/css" />
    <title>PM Confirmation</title>
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
<div style="text-align: center; margin-top: 65px;">
<h3><asp:Label ID="lblconfirmmsg" runat="server" /></h3>
<br />
<span class="content2">Please wait, you will be redirected in 3 seconds back to your Private Message Inbox.</span>
<br />
<br />
<asp:HyperLink ID="HyperLink2" runat="server" NavigateUrl="default.aspx" cssClass="content2">Go to home page</asp:HyperLink>
</span></div>    
    </form>
</body>
</html>