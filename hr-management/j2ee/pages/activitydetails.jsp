<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<html>
<head>
<title>MYTELCO HR - Activity Details</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<form name="form1" method="post" action="/submitActivitydetail.do">
  <label>Activity No: </label>
  <input type="text" name="textfield"><br>
  <input type="submit" name="btnGetActivityDetails" value="Get Activity Details">


  <input type="hidden" name = "logout" value="false"/>
  <input type="submit" value = "Logout" onclick="document.getElementById('logout').value='true';"/>
</form>
<p>&nbsp;</p>
</body>
</html>
