<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<html>
<head>
<title>MYTELCO HR - Project Details Using Container Managed Beans</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<form name="form1" method="post" action="/submitProjectCM.do">
  <label>Project No: </label>
  <input type="text" name="textfield"><br>
  <input type="submit" name="btnGetProjectDetails" value="Get Project Details">


  <input type="hidden" name = "logout" value="false"/>
  <input type="submit" value = "Logout" onclick="document.getElementById('logout').value='true';"/>
</form>
<p>&nbsp;</p>
</body>
</html>
