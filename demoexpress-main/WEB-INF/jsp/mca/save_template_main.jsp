<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<head>
<title>Save Template</title>
</head>
<body onload="notEmpty()">
<script type="text/javascript">
function notEmpty()
	{
	var obj = window.dialogArguments;
	var tmpltId 		= obj.tmpltId;
	var ISDATmpltNm 	= obj.ISDATmpltNm;
	var tmpltNm			= obj.tmpltNm;
	var tmpltType 		= obj.tmpltType;
	var frmScr 			= obj.frmScr;
	var opnInd 			= obj.opnInd;
	var randomnumber=Math.floor(Math.random()*100001);
	document.getElementById("saveframe").src = "/mcx/action/generateTemplateName?frmScr="+frmScr+"&tmpltId="+tmpltId+"&transaction.tmpltTyp="+tmpltType+"&transaction.ISDATmpltNm="+ISDATmpltNm+"&transaction.tmpltNm="+tmpltNm+"&opnInd="+opnInd+"&random="+randomnumber;
}
</script>
<form>
<iframe id="saveframe" src="javascript:'';" 
width="100%" height="100%" name="popup" leftmargin=0  framespacing=0  frameborder=no border=0  marginheight=0 marginwidth=0>
</iframe>
</form>
</body>