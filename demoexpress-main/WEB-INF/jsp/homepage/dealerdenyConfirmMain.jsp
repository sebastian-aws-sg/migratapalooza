<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<head>
<title><bean:message key="mcx.homePage.popup.head" /></title>
</head>
<body onload="notEmpty()">
<script type="text/javascript">
function notEmpty(){
var obj = window.dialogArguments;
var dealerCode = obj.dealerCode;
var username = obj.username;
var uploadTime = obj.uploadTime;
var approve = obj.approve;
document.getElementById("denyFrame").src = "/mcx/action/dealerPendingApproval?dealer="+ dealerCode +"&action=" + approve +"&user=" + username + "&uploadtime="+uploadTime;
}
</script>
<form>
<iframe id="denyFrame" src="javascript:'';"
width="100%" height="100%" name="popup" leftmargin=0  framespacing=0  frameborder=no border=0  marginheight=0 marginwidth=0>
</iframe>
</form>
</body>