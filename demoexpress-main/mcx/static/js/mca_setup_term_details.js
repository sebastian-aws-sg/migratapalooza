var cnclMsgInd="Y";
function printMCA() {
	var myObject = new Object();
	myObject.tmpltID = tmpltID;
	var url = "/mcx/action/adminPrintViewPopup"
	var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:670px;dialogWidth:1010px;status:no;scroll:yes;help:no;unadorned:no');
}
function goBack()
{
	document.forms[0].action='/mcx/action/adminSetup';
	document.forms[0].submit();
}
function admin_save_inprogress_mca(scrTmpltId, scrTmpltNm, scrTmpltTyp)
{

	document.forms[0].action='/mcx/action/SaveAdminTemplate'
	document.forms[0].tmpltId.value = scrTmpltId
	document.forms[0].tmpltNm.value = scrTmpltNm
	document.forms[0].tmpltTyp.value = scrTmpltTyp
	document.forms[0].opnInd.value = 'S';
	document.forms[0].submit();
}


function admin_send_approve_inprogress_mca(scrAction, scrTmpltId, appRejStatus)
{
	var conf1 = confirm("Are you sure you want to submit for approval ?")		
	if(conf1) {
		document.forms[0].action=scrAction
		document.forms[0].tmpltId.value = scrTmpltId
	//	document.forms[0].mcaStatusCd.value = '<bean:write name ="TemplateForm" property="template.mcaStatusCd" />'
		document.forms[0].appRejStatus.value = appRejStatus
		document.forms[0].submit();
	}
}


function admin_unlock_inprogress_mca(scrTmpltId,tempValAvl)
{
	document.forms[0].tmpltId.value = scrTmpltId		

	if(tempValAvl=="true"){

		var conf1 = confirm("Would you like to unlock the template ?")
		
		if(conf1) {

			var answer = confirm("There are unsaved changes to the template.\n Would you like to save the changes before unlock ?")
			if (answer)
			{
				document.forms[0].action='/mcx/action/SaveAdminTemplate';
				document.forms[0].opnInd.value = 'U';
				document.forms[0].sltInd.value = ' ';
				document.forms[0].submit();
			}else {
				document.forms[0].action='/mcx/action/SaveAdminTemplate';
				document.forms[0].opnInd.value = 'D';
				document.forms[0].sltInd.value = ' ';
				document.forms[0].submit();
			}
		}

	}else if(tempValAvl=="false"){

		var answer = confirm("Would you like to unlock the template ?")
		if (answer)
		{
			document.forms[0].action='/mcx/action/UnlockAdminTemplate';
			document.forms[0].opnInd.value = 'U';
			document.forms[0].submit();
		}
	}
}


function admin_approve_pending_mca(scrAction, scrTmpltId)
{

	document.forms[0].action='/mcx/action/ApproveRejectAdminTemplate';
	document.forms[0].appRejStatus.value = 'P';
	document.forms[0].submit();
}


function admin_reject_pending_mca(scrTmpltId)
{
	var conf1 = confirm("Are you sure you want to reject this MCA Setup?")
		
	if(conf1) {
		document.forms[0].action='/mcx/action/ApproveRejectAdminTemplate';
		document.forms[0].tmpltId.value = scrTmpltId;	
		document.forms[0].appRejStatus.value = 'I';	
		document.forms[0].submit();
	}
}
function admin_approve_pending_mca()
{
	var randomnumber=Math.floor(Math.random()*100001); 
	var win = window.showModalDialog('/mcx/action/post',self,'top:50;left:50px;status:no;dialogHeight:200px;dialogWidth:430px;status:no;scroll:no;help:no;unadorned:yes');
	if(win=='refresh')
	{
		window.location.href = '/mcx/action/adminSetup?random='+randomnumber;
	}
}
var termValueId = 0;
var tmpltType ='';
var lockSt;
var catgyId = 0;
var catgySqId = 0;
var catgyNm ='';
var termId = 0;
var termSqId = 0;
var termNm ='';
var tempValAvl='';
function displayMenu(type,valId,cgyId,cgySqId,cgyNm,trmId,trmSqId,trmNm)
{
	termValueId = valId;
	tmpltType =type;
	catgyId = cgyId;
	catgySqId =cgySqId;
	catgyNm = cgyNm;
	termId =trmId;
	termSqId = trmSqId;
	termNm =trmNm;
   rightmenu.style.leftPos+=10;
   rightmenu.style.posLeft=event.clientX+10+scrOfX;
   
   rightmenu.style.posLeft=event.clientX+10+scrOfX;
  
   var winwidth = document.body.clientWidth - rightmenu.clientWidth - 15;
   if(rightmenu.style.posLeft > winwidth){
 		rightmenu.style.posLeft = winwidth;
 	}
    
   rightmenu.style.posTop=event.clientY+10+window.document.body.scrollTop;   
    var winheight = window.document.body.clientHeight + window.document.body.scrollTop - rightmenu.clientHeight -12;
     if(rightmenu.style.posTop > winheight)
   {
   		rightmenu.style.posTop = winheight;
   }
   
   rightmenu.style.display="";
   if( event.preventDefault )
	{
	event.preventDefault();
	}
	event.returnValue = false;

	return true;
   rightmenu.setCapture();
}

function rightClickAction()
{
   rightmenu.releaseCapture();
   rightmenu.style.display="none";
   el=event.srcElement;
   var myObject = new Object();
   myObject.termValueId = termValueId;
   myObject.lockSt = lockSt;
   myObject.tmpltType = tmpltType;
   myObject.tmpltID = tmpltID;
   myObject.ISDATmpltNm = ISDATmpltNm;
   myObject.lockUsrInd = lockUsrInd;
   myObject.catgyId = catgyId;
   myObject.catgySqId = catgySqId;
   myObject.catgyNm = catgyNm;
   myObject.termId = termId;
   myObject.termSqId = termSqId;
   myObject.termNm = termNm;
   var scrolltop = document.body.scrollTop; 
   
   if (el.id=="modify") 
   {
   		url = "/mcx/action/adminModifyPopup"
      
       var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:520px;dialogWidth:635px;status:no;scroll:yes;help:no;unadorned:yes;resizable:yes');
       if(win == "refresh"){          
          window.location.href = "/mcx/action/ViewAdminISDATemplate?tmpltId="+tmpltID+"&sltInd=AC&scrollPosition="+scrolltop;
          }
   }
}

function editTerm(tmpltType,termValueId,catgyId,catgySqId,catgyNm,termId,termSqId,termNm){
var myObject = new Object();
   myObject.termValueId = termValueId;
   myObject.lockSt = lockSt;
   myObject.tmpltType = tmpltType;
   myObject.tmpltID = tmpltID;
   myObject.ISDATmpltNm = ISDATmpltNm;
   myObject.lockUsrInd = lockUsrInd;
   myObject.catgyId = catgyId;
   myObject.catgySqId = catgySqId;
   myObject.catgyNm = catgyNm;
   myObject.termId = termId;
   myObject.termSqId = termSqId;
   myObject.termNm = termNm;
   myObject.mcaStatusCd = mcaStatusCd;
	var scrolltop = document.body.scrollTop; 	
	   url = "/mcx/action/adminEditTerm"
	   
       
       var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:520px;dialogWidth:635px;status:no;scroll:yes;help:no;unadorned:yes;resizable:yes');
       if(win == "refresh"){          
          window.location.href = "/mcx/action/ViewAdminISDATemplate?tmpltId="+tmpltID+"&sltInd=AC&scrollPosition="+scrolltop;
          }

}
function clickBody()
{
	rightmenu.style.display="none";
}

function switchMenu() 
{   
   el=event.srcElement;
   if (el.className=="menuItem") {
      el.className="highlightItem";
   } else if (el.className=="highlightItem") {
      el.className="menuItem";
   }
}

function viewSetupByCategory()
{
	document.forms[0].submit();
}

function post_amendment()
{
	cnclMsgInd = "N";
	if(document.getElementById('file1').value && ! document.getElementById('img1'))
	{	
		document.forms[0].imgPrsnt.value="false";	
	} else if(document.getElementById('file1') &&  document.getElementById('img1'))
	{
		document.forms[0].imgPrsnt.value="true";		
	}
	
	var val = document.getElementById('doc').innerText;
	if(trimAll(val) == "")
	{
		alert('Please Enter a Value');
		return;
	}
	var answer = confirm("Are you sure you want to post this Amendment?")
	if (answer)
	{
		document.getElementById('amendmentValue').value = convertToHTML(document.getElementById('doc').innerHTML);
		if(document.getElementById('amendmentValue').value.indexOf('v:imagedata') != -1){
			alert('Please delete the image in the editor and insert a gif or jpg file using browse button');
			return;
		}
		if(document.getElementById('amendmentValue').value.length > 32000){
			alert("The content you are attempting to paste exceeds the current 32kb size limit. \n Please reduce the content size or, if not possible, paste the text from Notepad or WordPad.");
			return;
		}
		document.forms[0].submit();
	}
}

function cancel(tmpltId)
{
	var answer = confirm("Are you sure you want to cancel?")
	if (answer)
	{		
		self.close();			
        }
}

function unload(tmpltId)
{
	if(cnclMsgInd!='N'){
		var answer = confirm("Are you sure you want to close the window?")
		if (answer){		
			var url = "/mcx/action/ViewAdminISDATemplate?tmpltId="+tmpltId+"&sltInd=AC";
			window.opener.location.href = url;
			self.close();			
		}
	}
}
function approveTemplate() {

var dateStr = document.forms[0].publishDt.value;

if(dateStr.length == 0){
	alert("Please enter an Agreement Date");
	document.forms[0].publishDt.focus();
	return false;
	}
	
var datePat = /^(\d{1,2})(\/)(\d{1,2})(\/)(\d{4})$/;

if(dateStr.length != 0){

var matchArray = dateStr.match(datePat); // is the format ok?



if (matchArray == null) {
	
	alert("Please enter a valid date in MM/DD/YYYY format");
	document.forms[0].publishDt.focus();
	return false; 
	}


if(matchArray != null){
month = matchArray[1]; // p@rse date into variables
day = matchArray[3];
year = matchArray[5];

if (month < 1 || month > 12) { // check month range
alert("Please enter a valid month");
document.forms[0].publishDt.focus();
return false; 
}

if (day < 1 || day > 31) {
alert("Please enter a valid day");
document.forms[0].publishDt.focus();
return false; 
}

if ((month==4 || month==6 || month==9 || month==11) && day==31) {
alert("Please enter a valid date in MM/DD/YYYY format");
document.forms[0].publishDt.focus();
return false; 
}

if (month == 2) { // check for february 29th
var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
if (day > 29 || (day==29 && !isleap)) {
alert("Please enter a valid date in MM/DD/YYYY format");
document.forms[0].publishDt.focus();
return false; 
}
}
}
}
	window.opener = window.dialogArguments;
	window.opener.document.getElementById('publishDt').value =document.forms[0].publishDt.value;
	window.opener.document.forms[0].action='/mcx/action/ApproveRejectAdminTemplate';
	window.opener.document.getElementById('appRejStatus').value = 'P';
	window.opener.document.forms[0].submit();
	self.close();
	
}function scrollToPosotion()
{
	var scrolltop = document.forms[0].scrollPosition.value;
	window.scrollTo(0,scrolltop);
	document.forms[0].scrollPosition.value = 0;
	
	approveRejectError();
}




function approveRejectError()
{
	if(document.forms[0].flagTemplateApprovedRejected.value == 'true')
	{
		alert(document.forms[0].errorTemplateApprovedRejected.value);
		window.location.href = '/mcx/action/adminSetup';
	}
}

function trimAll(sString) 
{ 
	while (sString.substring(0,1) == ' ') 
	{ 
		sString = sString.substring(1, sString.length); 
	} 
	while (sString.substring(sString.length-1, sString.length) == ' ') 
	{ 
		sString = sString.substring(0,sString.length-1); 
	} 
	return sString; 
}