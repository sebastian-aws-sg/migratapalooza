<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<head>
<title>View Industry Published Term</title>
</head>
<body onload="notEmpty()">
<script type="text/javascript">
function notEmpty(){
var obj = window.dialogArguments;
var termValueId = obj.termValueId;
var lockSt = obj.lockSt;
var tmpltType = obj.tmpltType;
var ISDATmpltNm = obj.ISDATmpltNm;
var lockUsrInd = obj.lockUsrInd;
var randomnumber=Math.floor(Math.random()*100001)
document.getElementById("viewISDA").src = "/mcx/action/viewISDATerm?termValId="+termValueId+"&tmpltType="+tmpltType+"&tmpltLocked="+lockSt+"&lockUsrInd="+lockUsrInd+"&ISDATmpltNm="+ISDATmpltNm+"&random="+randomnumber;
}
</script>
<form>
<iframe id="viewISDA" src="javascript:'';"
width="100%" height="100%" name="popup" leftmargin=0  framespacing=0  frameborder=no border=0  marginheight=0 marginwidth=0>


</iframe>
</form>
</body>
