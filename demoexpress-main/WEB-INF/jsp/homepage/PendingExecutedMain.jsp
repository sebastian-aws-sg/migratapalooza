<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<body onload="notEmpty()">
<script type="text/javascript">
function notEmpty(){
var obj = window.dialogArguments;
var id = obj.selectedTab;
var param = obj.param;
var randomnumber=Math.floor(Math.random()*100001)
document.getElementById("mcaframe").src = '/mcx/action/getDealerDetails?mcaStatus='+id+'&printParam='+param+'&random='+randomnumber;
}
</script>
<form>
<iframe id="mcaframe" src="javascript:'';"
width="100%" height="100%" name="popup" leftmargin=0  framespacing=0  frameborder=no border=0  marginheight=0 marginwidth=0>
</iframe>
</form>
</body>