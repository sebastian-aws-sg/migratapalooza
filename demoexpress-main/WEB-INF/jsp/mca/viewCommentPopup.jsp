<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<head>
<title>Post Comment</title>
</head>
<body onload="notEmpty()">
<script type="text/javascript">
function notEmpty(){
var obj = window.dialogArguments;
var termValueId = obj.termValueId;
var lockSt = obj.lockSt;
var tmpltType = obj.tmpltType;
var tmpltID = obj.tmpltID;
var ISDATmpltNm = obj.ISDATmpltNm;
var fromScreen = obj.fromScreen;
var displayNm = obj.displayNm;
var isDealer = obj.isDealer;
var lockUsrInd = obj.lockUsrInd;
var randomnumber=Math.floor(Math.random()*100001)
document.getElementById("viewComnt").src = "/mcx/action/view_comment?tmpltID="+tmpltID+"&termValId="+termValueId+"&tmpltType="+tmpltType+"&tmpltLocked="+lockSt+"&ISDATmpltNm="+ISDATmpltNm+"&lockUsrInd="+lockUsrInd+"&userInd=V&frmScr="+fromScreen+"&userCompany="+displayNm+"&actingDealer="+isDealer+"&random="+randomnumber;
}
</script>
<form>
<iframe id="viewComnt" src="javascript:'';"
width="100%" height="100%" name="popup" leftmargin=0  framespacing=0  frameborder=no border=0  marginheight=0 marginwidth=0>
</iframe>
</form>
</body>