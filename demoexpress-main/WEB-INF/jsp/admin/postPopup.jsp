<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<head>
<title>Confirm</title>
</head>
<body onload="notEmpty()">
<script type="text/javascript">
function notEmpty(){
var randomnumber=Math.floor(Math.random()*100001)
document.getElementById("post").src = "/mcx/action/ApprovalDate?random="+randomnumber;
}
</script>
<form>
<iframe id="post" src="javascript:'';"
width="645" height="470" name="popup" leftmargin=0  framespacing=0  frameborder=no border=0  marginheight=0 marginwidth=0>
</iframe>
</form>
</body>
