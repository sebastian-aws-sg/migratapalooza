<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<head>
<title>Modify</title>
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
var tmpltID = obj.tmpltID;
var catgyId = obj.catgyId;
var catgySqId = obj.catgySqId;
var catgyNm = obj.catgyNm;
var termId = obj.termId;
var termSqId = obj.termSqId;
var termNm = obj.termNm;
var mcaStatusCd = obj.mcaStatusCd;
var randomnumber=Math.floor(Math.random()*100001)
document.getElementById("editTerm").src = "/mcx/action/ViewAdminISDATermDetail?termValId="+termValueId+"&tmpltType="+tmpltType+"&tmpltLocked="+lockSt		   
	   +"&transaction.catgyId="+catgyId+"&transaction.catgySqId="+catgySqId+"&transaction.catgyNm="+catgyNm
	   +"&transaction.termId="+termId+"&transaction.termSqId="+termSqId+"&transaction.termNm="+termNm+"&transaction.tmpltId="+tmpltID+"&transaction.ISDATmpltNm="+ISDATmpltNm+"&lockUsrInd="+lockUsrInd+"&mcaStatusCd="+mcaStatusCd+"&random="+randomnumber;	   
	   }
</script>
<form>
<iframe id="editTerm" src="javascript:'';"
width="100%" height="100%" name="popup" leftmargin=0  framespacing=0  frameborder=no border=0  marginheight=0 marginwidth=0>


</iframe>
</form>
</body>
