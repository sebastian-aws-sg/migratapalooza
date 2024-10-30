<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<head>
<title>Apply to Counterparty</title>
</head>
<body onload="notEmpty()">
<script type="text/javascript">
function notEmpty(){
var obj = window.dialogArguments;
var tmpltID = obj.tmpltID;
var tmpltTyp = obj.tmpltTyp;
var ISDATmpltNm	= obj.ISDATmpltNm;
var randomnumber=Math.floor(Math.random()*100001)
document.getElementById("applyToCP").src = "/mcx/action/getApplyTemplateToCP?random="+randomnumber+"&tmpltID="+tmpltID+"&transaction.tmpltTyp="+tmpltTyp+"&transaction.ISDATmpltNm="+ISDATmpltNm;
}
</script>
<form>
<iframe id="applyToCP" src="javascript:'';"
width="100%" height="100%" name="popup" leftmargin=0  framespacing=0  frameborder=no border=0  marginheight=0 marginwidth=0>

</iframe>
</form>
</body>
