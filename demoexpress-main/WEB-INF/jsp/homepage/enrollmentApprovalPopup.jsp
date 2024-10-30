<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<head>
<title>Print View</title>
</head>
<body onload="notEmpty()">
<script type="text/javascript">
function notEmpty(){
var obj = window.dialogArguments;
var param = obj.param;
var selectedTab = obj.selectedTab;
var randomnumber=Math.floor(Math.random()*100001)
document.getElementById("printEnroll").src = "/mcx/action/dealerPendingApproval?printParam="+param+"&selectedTab="+selectedTab+"&random="+randomnumber;
}
</script>
<form>
<iframe id="printEnroll" src="javascript:'';"
width="100%" height="100%" name="popup" leftmargin=0  framespacing=0  frameborder=no border=0  marginheight=0 marginwidth=0>
</iframe>
</form>
</body>