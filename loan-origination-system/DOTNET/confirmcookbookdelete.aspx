<%@ Page Language="C#" AutoEventWireup="true" CodeFile="confirmcookbookdelete.aspx.cs" Inherits="confirmcookbookdelete" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>Confirm Delete Recipe CookBook</title>
    <META HTTP-EQUIV="refresh" CONTENT="3; URL=mycookbook.aspx">
    <link href="CSS/cssreciaspx.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <form id="form1" runat="server">
<br />
<br />
<br />
<div style="text-align: center; margin-top: 35px;"><h1><asp:Label ID="lblheader" runat="server" /></h1></div>
<div style="text-align: center; margin-top: 20px;">
<asp:Label cssClass="content12" ID="lblsuccess" runat="server" />
<br />
<br />
<asp:Label cssClass="content12" ID="lblwait" runat="server" Visible="false" />
<br />
<asp:HyperLink ID="HyperLink1" runat="server" NavigateUrl="mycookbook.aspx" cssClass="content12">Back to My CookBook</asp:HyperLink>
</div>   
    </form>
</body>
</html>
