<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<html>
<head>
<title>MYTELCO HR - Project Activity Details</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<form name="form1" method="post" action="/submitProjectactivitydetail.do">
  <label>Activity No: </label>
  <input type="text" name="textfield"><br>
  <input type="submit" name="btnGetProjectActivityDetails" value="Get Project Activity Details">


  <input type="hidden" name = "logout" value="false"/>
  <input type="submit" value = "Logout" onclick="document.getElementById('logout').value='true';"/>
</form>
<p>&nbsp;</p>
</body>
</html>
