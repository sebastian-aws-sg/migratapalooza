<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">


<head>
<title><bean:message key="mcx.ManageDocs.deleteCP.header" /></title>
</head>
<body onload="deleteCmpny()">
<script type="text/javascript">
function deleteCmpny(){
var deleteCmpnyId = window.dialogArguments;
var randomnumber=Math.floor(Math.random()*10000001);
document.getElementById("deleteCmpny").src = "/mcx/action/deletecpWindow?deleteCmpnyId="+deleteCmpnyId + "&random="+randomnumber;
}
</script>
<form >
					
					
<iframe id = "deleteCmpny" src="javascript:'';"
width="100%" height="100%" name="popup" leftmargin=0  framespacing=0  frameborder=no border=0  marginheight=0 marginwidth=0>

</iframe>

</form>

</body>