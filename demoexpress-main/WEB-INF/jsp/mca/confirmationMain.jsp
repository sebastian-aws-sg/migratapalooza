<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<head>
<title>Submission to Counterparty</title>
</head>
<body onload="notEmpty()">
<script type="text/javascript">
function notEmpty()
{
	var obj = window.dialogArguments;
	var tmpltId 		= obj.tmpltId;
	var tmpltType 		= obj.tmpltType;
	var frmScr 			= obj.frmScr;
	var randomnumber=Math.floor(Math.random()*100001);
	document.getElementById("confirmationframe").src = "/mcx/action/sendTemplateToCP?opnInd=C&frmScr="+frmScr+"&tmpltId="+tmpltId+"&transaction.tmpltTyp="+tmpltType+"&random="+randomnumber;
}
</script>
<form>
<iframe id="confirmationframe" src="javascript:'';"
width="100%" height="100%" name="popup" leftmargin=0  framespacing=0  frameborder=no border=0  marginheight=0 marginwidth=0>
</iframe>
</form>
</body>