<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<head>
<title>Post Amendment</title>
</head>
<body onload="notEmpty()">
<script type="text/javascript">
function notEmpty(){
var obj = window.dialogArguments;
var dealerCode = obj.dealerCode;
var username = obj.username;
var uploadTime = obj.uploadTime;
var approve = obj.approve;
var termValueId = obj.termValueId;
var lockSt = obj.lockSt;
var tmpltType = obj.tmpltType;
var tmpltID = obj.tmpltID;
var isDealer = obj.isDealer;
var ISDATmpltNm = obj.ISDATmpltNm;
var lockUsrInd = obj.lockUsrInd;
var fromScreen = obj.fromScreen;
var displayNm = obj.displayNm;
var randomnumber=Math.floor(Math.random()*100001)
document.getElementById("postAmend").src = "/mcx/action/viewAmendment?termValId="+termValueId+"&tmpltLocked="+lockSt+"&tmpltType="+tmpltType+"&tmpltID="+tmpltID+"&ISDATmpltNm="+ISDATmpltNm+"&lockUsrInd="+lockUsrInd+"&frmScr="+fromScreen+"&userCompany="+displayNm+"&actingDealer="+isDealer+"&random="+randomnumber;
}
</script>
<form>
<iframe id="postAmend" src="javascript:'';"
width="100%" height="100%" name="popup" leftmargin=0  framespacing=0  frameborder=no border=0  marginheight=0 marginwidth=0>
</iframe>
</form>
</body>
