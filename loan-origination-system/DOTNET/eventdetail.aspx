<%@ Page Language="C#" AutoEventWireup="true" EnableViewState="false" CodeFile="eventdetail.aspx.cs" Inherits="eventdetail" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>Event Detail</title>
    <link href="CSS/cssreciaspx.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <form id="form1" runat="server">
    <div>
         <div style="padding-bottom: 5px;">
             <asp:Label ID="lbldate" CssClass="content12" style="font-size: 14px; color: #000; font-weight: bold;" runat="server" />
         </div>
         <asp:Label ID="lbleventdetail" CssClass="content12" runat="server" />
   </div>
    </form>
</body>
</html>
