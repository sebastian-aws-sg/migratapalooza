<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<head>
<title>Print View</title>
</head>
<body onload="notEmpty()">
<script type="text/javascript">
function notEmpty(){
var obj = window.dialogArguments;
var tmpltID = obj.tmpltID;
var randomnumber=Math.floor(Math.random()*100001)
document.getElementById("adminprintView").src = "/mcx/action/ViewAdminISDATemplate?tmpltId="+tmpltID+"&sltInd=PT&random="+randomnumber+"&viewInd=F&frmScr=ISDA&catgyId= ";
}
</script>
<form>
<iframe id="adminprintView" src="javascript:'';"
width="100%" height="100%" name="popup" leftmargin=0  framespacing=0  frameborder=no border=0  marginheight=0 marginwidth=0>


</iframe>
</form>
</body>
