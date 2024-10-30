<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<html>
<head>
<title>MYTELCO HR - Msg In tray Using Container Managed Beans</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<form name="form1" method="post" action="/submitMsgInTrayCM.do">
  <label>Employee No: </label>
  <input type="text" name="textfield"><br>
  <input type="submit" name="btnGetMsgDetails" value="Get Employee Messages">


  <input type="hidden" name = "logout" value="false"/>
  <input type="submit" value = "Logout" onclick="document.getElementById('logout').value='true';"/>
</form>
<p>&nbsp;</p>
</body>
</html>
