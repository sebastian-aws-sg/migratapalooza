<%@ Page Language="C#" AutoEventWireup="true" CodeFile="commentpostconfirmation.aspx.cs" Inherits="commentpostconfirmation" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <link href="CSS/cssreciaspx.css" rel="stylesheet" type="text/css" />
    <title>Recipe Comment Confirmation</title>
</head>
<body>
    <form id="form1" runat="server">
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
<div style="text-align: center; margin-top: 35px;">
<h3>Thank you! Your comment  has been successfully posted.</h3>
<br />
<span class="content12">Please wait, you will be redirected in 3 seconds back to the previous page.</span>
<br />
<a class="dt" href="default.aspx">Back to homepage</a>
</div>
    </form>
</body>
</html>
