// JavaScript Document
var cnclMsgInd="Y";
var errPrsnt = false;
var errMsg	 = "";
var hasModify = false;

function validate(elmnt)
{
	var name = elmnt.value;
	if(trimAll(name) == "")
	{
		errPrsnt = true;
		errMsg = "Please Enter a Valid Name";
	}
	else
	{
		errPrsnt = false;
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


function submitForm()
{
	validate(document.getElementById('transaction.nxtTmpltNm'));
	if(errPrsnt)
	{
		alert(errMsg);
		return false;
	}
	else
	{
		if(document.forms[0].opnInd.value != "U")
		{
			document.forms[0].opnInd.value = "C";
		}
		document.forms[0].action = "/mcx/action/saveTemplate?transaction.ISDATmpltNm="+ISDATmpltNm+"&transaction.tmpltTyp="+tmpltType;
		document.forms[0].submit();
	}
}

function selectTemplate() {
		document.forms[0].sltInd.value="TE";
		document.forms[0].viewInd.value="F";
		document.forms[0].catgyId.value="";
		document.getElementById('transaction.tmpltId').blur();
		document.getElementById("catgyID").value = "";
		if(document.getElementById("viewIndicator")!=null){
		document.getElementById("viewIND").value = "F";
		}
		document.getElementById("tmpltIdValHidden").value = document.getElementById("Template").value;
		document.forms[0].submit();
}
function printMCA() {
	var myObject = new Object();
	myObject.tmpltID = tmpltID;
	myObject.frmScr	 = document.forms[0].frmScr.value;
	var url = "/mcx/action/printViewPopup"
	var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:670px;dialogWidth:1010px;status:no;scroll:yes;help:no;unadorned:no');
}

function selectCategory() {
		document.forms[0].tmpltId.value = tmpltID;
		document.forms[0].sltInd.value="CG";		
		document.getElementById('catgyId').blur();		
		document.getElementById("catgyID").value = document.getElementById("Category").value;
		if(document.getElementById("viewIndicator")!=null){
			document.getElementById("viewIND").value = document.getElementById("viewIndicator").value;	
		}	
		if(document.getElementById("Template")!=null){
			document.getElementById("tmpltIdValHidden").value = document.getElementById("Template").value;
		}
		
		document.forms[0].submit();
}
function selectView() {
		document.forms[0].tmpltId.value = tmpltID;
		document.forms[0].sltInd.value="VW";
		document.getElementById('viewInd').blur();	
		document.getElementById("catgyID").value = document.getElementById("Category").value;
		document.getElementById("viewIND").value = document.getElementById("viewIndicator").value;	
		if(document.getElementById("Template")!=null){
			document.getElementById("tmpltIdValHidden").value = document.getElementById("Template").value;
		}
		document.forms[0].submit();
}
function selectRegion() {
		document.forms[0].sltInd.value="RG";
		document.forms[0].viewInd.value="F";
		document.forms[0].catgyId.value="";
		document.getElementById("catgyID").value = "";
		if(document.getElementById("viewIndicator")!=null){
			document.getElementById("viewIND").value = "F";	
		}
		document.getElementById('transaction.rgnCd').blur();
		document.getElementById("RegionCD").value = document.getElementById("RegCode").value;
		document.forms[0].submit();
}
function resetFilter() {
		document.forms[0].tmpltId.value = tmpltID;
		document.forms[0].sltInd.value="CG";
		document.forms[0].catgyId.value="";
		document.forms[0].viewInd.value="F";
		if(document.getElementById("catgyID")!=null)
		{
			document.getElementById("catgyID").value="";
		}
		if(document.getElementById("viewIND")!=null)
		{
			document.getElementById("viewIND").value="";
		}
		document.forms[0].submit();
}
function generateTemplateName() 
{
	   document.forms[0].scrollPosition.value = 0;
	   var categyId = document.getElementById("Category").value;
		if(document.getElementById("viewIndicator") != null)
		{
			var viewInd = document.getElementById("viewIndicator").value;
		}
	   var scrolltop = document.body.scrollTop;     
	   var myObject = new Object();
	   myObject.tmpltId = tmpltID;
	   myObject.ISDATmpltNm = ISDATmpltNm;
	   myObject.tmpltType = tmpltType;
	   var fromScreen = document.forms[0].frmScr.value;

       var url = "/mcx/action/generateTemplateNameMain";
       var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:160px;dialogWidth:500px;status:no;scroll:yes;help:no;unadorned:no');
	   	if(win!=null)
	   	{	   	
	   	var tmpltIDNew = win.tempId;	   	   
	   	if(win.refresh == "refresh"){	   	   		   	
        window.location.href = "/mcx/action/ViewMCA?frmScr="+fromScreen+"&tmpltId="+tmpltIDNew+"&catgyId="+categyId+"&viewInd="+viewInd+"&sltInd=AC&scrollPosition=0";
        }
        }
}

function getApplyTemplateToCP()
{
		document.forms[0].scrollPosition.value = 0;
	   var categyId = document.getElementById("Category").value;
	if(document.getElementById("viewIndicator") != null){
	var viewInd = document.getElementById("viewIndicator").value;
	}
		var myObject = new Object();
		myObject.tmpltID = tmpltID;
		myObject.tmpltTyp= tmpltType;
		myObject.ISDATmpltNm = ISDATmpltNm;
	 	var url = "/mcx/action/applyToCPPopup"
	   document.forms[0].scrollPosition.value = 0;
       
       var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:200px;dialogWidth:550px;status:no;scroll:yes;help:no;unadorned:yes');       
       if(win!=null){	   	
	   	var tmpltIDNew = win.tempId;	   	   
		var enableCust = win.tempVal;
	   	if(win.refresh == "refresh"){	   	   		   	
        window.location.href = "/mcx/action/ViewMCA?tmpltId="+tmpltIDNew+"&catgyId="+categyId+"&viewInd="+viewInd+"&enableCustomize="+enableCust+"&sltInd=AC&frmScr=MW2&viewInd=F&catgyId=&scrollPosition=0";
        }
        }
}

function sendToCP(tmpltName)
{
	var answer = confirm("Please ensure any required internal approval(s) were received and click Ok to Submit");
	if(answer)
	{
		var ans = confirm('The selected MCA ('+ tmpltName +') will be submitted to the counterparty.');
		if(ans)
		{
			document.forms[0].tmpltId.value = tmpltID;
			document.forms[0].action = "/mcx/action/sendTemplateToCP?transaction.tmpltTyp="+tmpltType;		
	document.forms[0].submit();
}
}
}

function submitToCP()
{	
	var myObject = new Object();
	myObject.tmpltId 	= tmpltID;
	myObject.tmpltType 	= tmpltType;
	myObject.frmScr 	= document.forms[0].frmScr.value;
	var url = "/mcx/action/sendTemplateToCPMain";
	var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:200px;dialogWidth:500px;status:no;scroll:yes;help:no;unadorned:yes');
    if(win == "refresh"){          
          window.location.href = "/mcx/action/getDealerDetails?mcaStatus=P";
    }
}

var tmpltName;
function setTmpltNm(val)
{
	tmpltName = val.value;
}

	
function saveTemplate(tmpltNm)
{
	document.getElementById("catgyID").value = document.getElementById("Category").value;
	if(document.getElementById("viewIndicator") != null)
	{
		document.getElementById("viewIND").value =  document.getElementById("viewIndicator").value;
	}	
	document.forms[0].scrollPosition.value = 0;
	document.forms[0].action = "/mcx/action/saveTemplate?opnInd=S&tmpltId="+tmpltID+"&transaction.tmpltNm="+tmpltNm+"&transaction.tmpltTyp="+tmpltType;
	document.forms[0].submit();
}

function checkTemplateName()
{	
	if(tmpltName)
	{
		if(tmpltName == "")
			alert('Please give the Template Name ');
	}
	else
	{
	    document.forms[0].submit();
        }
}

function showAlert(alrt)
{
	if(alrt == "TMPLTEXISTS-OVERWRITE")
{
	var answer = confirm("The Template already exists, do you want to Overwrite?");
	if (answer)
	{
		document.forms[0].opnInd.value = "S";
		document.forms[0].submit();		
	}
}
	else if(alrt == "LOCKED")
	{
		alert("This Template is Currently Locked. Please save as another Name.");
	}
	else if(alrt == "TMPLTEXISTS")
	{
		alert("The Template already exists as CP Final or Executed. Please save as another Name.");
	}
}


var termValueId = 0;
var lockSt;
var tmpltID;
var tmpltType;
var ISDATmpltNm;
var tmpltShrtNm;
var lockUsrInd;
var isDealer;
var cpName;
var	displayNm;
var tmpltNm;
function displayMenu(amendId, amendStCd, cmntInd,catgyStCd)
{
	 rightmenu.style.height = "auto";
	termValueId = amendId;
   rightmenu.style.leftPos+=10;
   rightmenu.style.posLeft=event.clientX+10+scrOfX;
   var winwidth = document.body.clientWidth - 198;
   
 	if(rightmenu.style.posLeft > winwidth){
 		rightmenu.style.posLeft = winwidth;
 	}
 	
 	
   rightmenu.style.posTop=event.clientY+10+window.document.body.scrollTop;
  
   
  
   rightmenu.style.display="";
   if(amendStCd == "I" || catgyStCd =="P")
   {
   		viewISDA.style.display = "none";
   }
   else
   {
   		viewISDA.style.display = "";
   }
   if(cmntInd == "false")
   {
   		viewComnt.style.display = "none";
   }
   else if(postComnt.style.display == "none")
   { 
   		viewComnt.style.display = "";
   }
   if(lockSt == "Y" && lockUsrInd != "L")
   {
   		postAmend.style.display = "none";
   		postComnt.style.display = "none";
   }
   if(document.forms[0].enableCustomize)
   {
	   if(document.forms[0].enableCustomize.value == "false")
	   {
	   		postAmend.style.display = "none";
	   		postComnt.style.display = "none";	   		
	   }
	   else
	   {
	   		postAmend.style.display = "";
	   		postComnt.style.display = "";
	   		viewComnt.style.display = "none";
	   }
  }

   //Agree Amendment comes only in Negotiation Page
   agreeAmend.style.display = "none";
   var count = 0;
      
      if(postAmend.style.display == "")
      {
      	count++;
      }
      if(postComnt.style.display == "")
      {
      	count++;
      }
      if(agreeAmend.style.display == "")
      {
      	count++;
      }
      if(viewComnt.style.display == "")
      {
      	count++;
      }
      if(viewISDA.style.display == "")
      {
      	count++;
      }
     
      
   
   
    var winheight = window.document.body.clientHeight + window.document.body.scrollTop - ((count * 30));
  
   if(rightmenu.style.posTop > winheight)
   {
   		rightmenu.style.posTop = winheight;
   }
   
   if(postAmend.style.display == "none" && postComnt.style.display == "none" && agreeAmend.style.display == "none"
   		&& viewComnt.style.display == "none" && viewISDA.style.display == "none")
   {
   		rightmenu.style.display="none";
   		return;
   }
   if( event.preventDefault )
	{
	event.preventDefault();
	}
	event.returnValue = false;

	return true;
   rightmenu.setCapture();
}

var enableAgree = "false";
function displayMenu_negotiate(amendId, amendStCd, cmntInd,catgyStCd)
{
	rightmenu.style.height = "auto";
	termValueId = amendId;
   rightmenu.style.leftPos+=10;
   rightmenu.style.posLeft=event.clientX+10+scrOfX;
   
   var winwidth = document.body.clientWidth - 198;
    
 	if(rightmenu.style.posLeft > winwidth){
 		rightmenu.style.posLeft = winwidth;
 	}
 	
   rightmenu.style.posTop=event.clientY+10+window.document.body.scrollTop;
   
   rightmenu.style.display="";
   
  
   if(amendStCd == "I" || catgyStCd =="P")
   {
   		viewISDA.style.display = "none";
   }
   else
   {
   		viewISDA.style.display = "";
   		
   }
   if(amendStCd == "P" && enableAgree == "true" && hasModify)
   {
   	  agreeAmend.style.display = "";
   }
   else if(amendStCd != "P" && enableAgree == "true")
   {
   	  agreeAmend.style.display = "none";
   }
   if(lockSt == "Y" && lockUsrInd != "L")
   {
   		postAmend.style.display = "none";
   		postComnt.style.display = "none";
   		agreeAmend.style.display = "none";
   }
   if(cmntInd == "false")
   {   		
   		viewComnt.style.display = "none";
   }
   else if(postComnt.style.display == "none")
   {
   		viewComnt.style.display = "";
   }
   
      var count = 0;
      
      if(postAmend.style.display == "")
      {
      	count++;
      }
      if(postComnt.style.display == "")
      {
      	count++;
      }
      if(agreeAmend.style.display == "")
      {
      	count++;
      }
      if(viewComnt.style.display == "")
      {
      	count++;
      }
      if(viewISDA.style.display == "")
      {
      	count++;
      }
   
   
    var winheight = window.document.body.clientHeight + window.document.body.scrollTop - ((count * 30));
  
   if(rightmenu.style.posTop > winheight)
   {
   		rightmenu.style.posTop = winheight;
   }
   
   
   if(postAmend.style.display == "none" && postComnt.style.display == "none" && agreeAmend.style.display == "none"
   		&& viewComnt.style.display == "none" && viewISDA.style.display == "none")
   {
   		rightmenu.style.display="none";
   		return;
   }
   if( event.preventDefault )
	{
	event.preventDefault();
	}
	event.returnValue = false;

	return true;
   
   rightmenu.setCapture();
}

function switchMenu() {   
   el=event.srcElement;
   if (el.className=="menuItem") {
      el.className="highlightItem";
   } else if (el.className=="highlightItem") {
      el.className="menuItem";
   }
}

function viewTerm(tmpltType,lockSt,termValueId)
{
	var myObject = new Object();
	myObject.termValueId = termValueId;
	myObject.lockSt = lockSt;
	myObject.tmpltType = tmpltType;
	myObject.ISDATmpltNm = tmpltShrtNm;
	var url = "/mcx/action/viewTermPopup"
      var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:500px;dialogWidth:632px;status:no;scroll:yes;help:no;unadorned:no;resizable:yes');	   		   		   		   	
}

/* RIGHT CLICK FUNCTION */
var scrOfX = 0, scrOfY = 0;
function scrollHeightWidth()
{
  if( typeof( window.pageYOffset ) == 'number' ) 
  {
    //Netscape compliant
    scrOfY = window.pageYOffset;
    scrOfX = window.pageXOffset;
  } else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) 
  {
    //DOM compliant
    scrOfY = document.body.scrollTop;
    scrOfX = document.body.scrollLeft;
  } else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) 
  {
    //IE6 standards compliant mode
    scrOfY = document.documentElement.scrollTop;
    scrOfX = document.documentElement.scrollLeft;
  }
}

// Method used for scrolling to specified position in Template screens	
function scrollToPosotion()
{
	var scrolltop = document.forms[0].scrollPosition.value;
	window.scrollTo(0,scrolltop);
	document.forms[0].scrollPosition.value = 0;
}

function clickBody()
{
	rightmenu.style.display="none";
}

function rightClickAction()
{
   rightmenu.releaseCapture();
   rightmenu.style.display="none";
   el=event.srcElement;
   var url;
   var fromScreen = document.forms[0].frmScr.value;   
   	var myObject = new Object();
	myObject.termValueId = termValueId;
	myObject.lockSt = lockSt;
	myObject.tmpltType = tmpltType;
	myObject.tmpltID = tmpltID;
	myObject.ISDATmpltNm = tmpltShrtNm;
	myObject.fromScreen = fromScreen;
	myObject.displayNm = escape(displayNm);
	myObject.isDealer = isDealer;
	myObject.lockUsrInd = lockUsrInd;
	var categyId = document.getElementById("Category").value;
	if(document.getElementById("viewIndicator") != null){
	var viewInd = document.getElementById("viewIndicator").value;
	}
	var scrolltop = document.body.scrollTop; 
   if (el.id=="postAmend") 
   {  		
       	url = "/mcx/action/postAmendment"   	   
	   	var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:520px;dialogWidth:627px;status:no;scroll:yes;help:no;unadorned:yes;resizable:yes');	   		   		   		   	
	   	if(win!=null){	   	
	   	var tmpltIDNew = win.tempId;	   	   
	   //	document.getElementById("tmpltIDValue").value = tmpltIDNew;  	   	   
	   	if(win.refresh == "refresh"){	   	   		   	
          window.location.href = "/mcx/action/ViewMCA?frmScr="+fromScreen+"&tmpltId="+tmpltIDNew+"&catgyId="+categyId+"&viewInd="+viewInd+"&sltInd=AC&scrollPosition="+scrolltop;
        }
        }
   }
   else if (el.id=="postComnt")
   {
    	  url = "/mcx/action/view_comment_main"
          var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:500px;dialogWidth:650px;status:no;scroll:yes;help:no;unadorned:yes');
          if(win!=null){	   	
	   		var tmpltIDNew = win.tempId;	   	   
	   		if(win.refresh == "refresh"){	   	   		   	
          window.location.href = "/mcx/action/ViewMCA?frmScr="+fromScreen+"&tmpltId="+tmpltIDNew+"&catgyId="+categyId+"&viewInd="+viewInd+"&sltInd=AC&scrollPosition="+scrolltop;
        	}
        }         
   }
    else if (el.id=="viewComnt")
   {
   		url = "/mcx/action/viewCommentPopup"
        var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:500px;dialogWidth:650px;status:no;scroll:yes;help:no;unadorned:yes');
        if(win == "refresh"){          
          window.location.href = "/mcx/action/ViewMCA?frmScr="+fromScreen+"&tmpltId="+tmpltID+"&sltInd=AC&scrollPosition="+scrolltop;
          }
   }
   else if (el.id=="agreeAmend")
   {
   		url = "/mcx/action/agreeAmendmentPopup"
   		
	     var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:360px;dialogWidth:625px;status:no;scroll:yes;help:no;unadorned:yes');
	    
          window.location.href = "/mcx/action/ViewMCA?frmScr="+fromScreen+"&tmpltId="+tmpltID+"&catgyId="+categyId+"&viewInd="+viewInd+"&sltInd=AC&scrollPosition="+scrolltop;
        
   }
   else if (el.id=="viewISDA")
   {
   	  url = "/mcx/action/viewISDAPopup"
      var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:500px;dialogWidth:633px;status:no;scroll:yes;help:no;unadorned:yes;resizable:yes');
      if(win == "refresh"){          
          window.location.href = "/mcx/action/ViewMCA?frmScr="+fromScreen+"&tmpltId="+tmpltID+"&sltInd=AC&scrollPosition="+scrolltop;
         }
   }
}
function post_comment()
{
	if(trimAll(document.getElementById('commentTxt').value) == "")
	{
		alert('Please Enter Comment');
		return;
	}
	var answer = confirm("Are you sure you want to post this comment?");
	if (answer)
	{
		document.forms[0].submit();
	}
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
	if(document.forms[0].imgPrsnt.value=="false" && trimAll(document.getElementById('doc').innerText) == "")
	{
		alert('Please Enter Amendment');
		return;
	}
	var answer = confirm("Are you sure you want to post this Amendment?");
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

function cancel()
{	
	cnclMsgInd = "N";
	var answer = confirm("Are you sure you want to cancel?")
	if (answer)
	{
			self.close();			
	}
}

function selectProd(prodCd,subProdCd) 
{
		document.forms[0].prodCd.value=prodCd;
		document.forms[0].subProdCd.value=subProdCd;
		document.forms[0].sltInd.value="PR";
		document.forms[0].viewInd.value="F";
		document.forms[0].catgyId.value="";
		document.getElementById("Category").value = "" ;
		document.getElementById("catgyID").value = "";
		if(document.getElementById("viewIndicator") != null){
			document.getElementById("viewIndicator").value = "F";
			document.getElementById("viewIND").value = "F";
		}
		document.forms[0].submit();
}

function unlock_mca(tempValAvl, saveInd, tmpltNm)
{		
	var categyId = document.getElementById("Category").value;
	document.getElementById("catgyID").value = categyId;
	if(document.getElementById("viewIndicator") != null){
		var viewInd = document.getElementById("viewIndicator").value;
		document.getElementById("viewIND").value = viewInd;
	}
	
	if(tempValAvl=="true"){
	var scrolltop = document.body.scrollTop;
	if(document.forms[0].frmScr.value=="MW1"){
		var conf1 = confirm("Would you want to unlock the template ?")
	} else if(document.forms[0].frmScr.value=="NEG"){
		var conf1 = confirm("Would you want to unlock the document ?")
	} 

		if(conf1) {
		var answer = confirm("There are unsaved changes to the template.\n Would you want to save the changes before unlock ?")
			if (answer)
			{				
				if(saveInd)
				{
					document.forms[0].tmpltId.value = tmpltID;
					document.forms[0].action='/mcx/action/saveTemplate';
					document.forms[0].opnInd.value = 'U';
					document.forms[0].catgyId.value = categyId;
					document.forms[0].viewInd.value = viewInd;
					document.forms[0].submit();
				}
				else
				{
			    var url = "/mcx/action/generateTemplateNameMain";
				   var myObject = new Object();
				   myObject.tmpltId 	= tmpltID;
				   myObject.ISDATmpltNm = ISDATmpltNm;
				   myObject.tmpltType 	= tmpltType;
				   myObject.tmpltNm		= tmpltNm;
			       myObject.opnInd 		= "U";
				   var fromScreen 		= document.forms[0].frmScr.value;
       			   var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:160px;dialogWidth:500px;status:no;scroll:yes;help:no;unadorned:no');
	   			   if(win!=null)
	   			   {	   	
	   			var tmpltIDNew = win.tempId;	   	   
	   			if(win.refresh == "refresh"){	   	   		   	
         	 			window.location.href = "/mcx/action/ViewMCA?frmScr="+fromScreen+"&tmpltId="+tmpltIDNew+"&catgyId="+categyId+"&viewInd="+viewInd+"&sltInd=AC&scrollPosition="+scrolltop;
        		}
        		}
			}
			}
			else 
			{
				document.forms[0].action='/mcx/action/saveTemplate';
				document.forms[0].tmpltId.value = tmpltID;
				document.forms[0].opnInd.value = 'D';
				document.forms[0].submit();
			}
		}

	}else if(tempValAvl=="false"){
		var answer = confirm("Would you want to unlock the template ?")
		if (answer)
		{
			document.forms[0].action='/mcx/action/UnlockAdminTemplate?tmpltId='+tmpltID;
			document.forms[0].opnInd.value = 'U';
			document.forms[0].submit();
		}
	}
}


function updateButtonValues()
{
	if(isDealer == "true")
	{	
		if(tmpltType == "R")
		{
			document.forms[0].Execute.value  = "RE-EXECUTE";
		}
	}
	else
	{
		document.forms[0].Execute.value  = "SUBMIT FOR EXECUTION";
		document.forms[0].SubmitToCP.value 	= "SUBMIT TO DEALER";
	}
}

function executeMCA(actingDealer)
{
		var myObject = new Object();
		myObject.tmpltId 	= tmpltID;
		myObject.tmpltType 	= tmpltType;
		myObject.actingDealer = actingDealer;
		myObject.frmScr 	= document.forms[0].frmScr.value;
	    var url = "/mcx/action/executeMCAMain";
	    var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:220px;dialogWidth:500px;status:no;scroll:no;help:no;unadorned:yes');
	     if(win == "refresh" && (document.forms[0].Execute.value  == "EXECUTE MCA" || document.forms[0].Execute.value  == "RE-EXECUTE" ))
	    {      
          window.location.href = "/mcx/action/getDealerDetails?mcaStatus=E";
    }	   
	    else if(win == "refresh")
	    { 
          window.location.href = "/mcx/action/getDealerDetails?mcaStatus=P";
    	}	   
}

function submitExecuteMCA()
{
	if(document.forms[0].publishDt)
	{
		if(validateDate(document.forms[0].publishDt.value))
		{		
			document.forms[0].submit();
		}
		else
		{
			return false;
		}
	}
	else
{
	document.forms[0].submit();
}
}

function renegotiateMCA(tmpltNm, dlrCd, cltCd)
{
	var categyId = document.getElementById("Category").value;
	if(document.getElementById("viewIndicator") != null){
	var viewInd = document.getElementById("viewIndicator").value;
	}
	var answer = confirm("Are you sure you want to re-negotiate this MCA?");
	if(answer)
	{
		document.forms[0].scrollPosition.value = 0;
		document.forms[0].action = "/mcx/action/negotiateMCA?catgyId="+categyId+"&viewInd="+viewInd+"&transaction.tmpltId="+tmpltID+"&transaction.orgDlrCd="+dlrCd+"&transaction.orgCltCd="+cltCd;
		document.forms[0].submit();
	}
}

function validateDate(dateStr)
{
	var datePat = /^(\d{1,2})(\/)(\d{1,2})(\/)(\d{4})$/;
	
	if(dateStr.length == 0)
	{
		alert("Please enter an Execution Date");
		document.forms[0].publishDt.focus();
		return false;
	}
	
	if(dateStr.length != 0)
	{
		var matchArray = dateStr.match(datePat); // is the format ok?
	
		if (matchArray == null) 
		{
			alert("Please enter a Valid date in MM/DD/YYYY format");
			document.forms[0].publishDt.focus();
			return false; 
		}
		
		if(matchArray != null)
		{
			month = matchArray[1]; // p@rse date into variables
			day = matchArray[3];
			year = matchArray[5];
			
			if (month < 1 || month > 12)  // check month range
			{ 
				alert("Please enter a valid month");
				document.forms[0].publishDt.focus();
				return false; 
			}
			
			if (day < 1 || day > 31) 
			{
				alert("Please enter a valid day");
				document.forms[0].publishDt.focus();
				return false;  
			}
			
			if ((month==4 || month==6 || month==9 || month==11) && day==31) 
			{
				alert("Please enter a valid date");
				document.forms[0].publishDt.focus();
				return false; 
			}
			
			if (month == 2)  // check for february 29th
			{ 
				var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
				if (day > 29 || (day==29 && !isleap)) 
				{
					alert("Please enter a valid date");
					document.forms[0].publishDt.focus();
					return false; 
				}
			}
		}
	}
	return true;
}

function templateIdset()
{
	var hiddenvalue = document.getElementById("tmpltIdValHidden").value;
	var cboObject=document.getElementById("Template");
	var valuePresent = false;
	for(i=0;i<cboObject.length;i++)
	{
		var objClaim = cboObject.options[i];
		
		if (hiddenvalue == objClaim.value)
		{
			valuePresent = true;
			break;
		}
	}
	
	if(valuePresent)
	{
		document.getElementById("Template").value = document.getElementById("tmpltIdValHidden").value;
	}
	else
	{
		cboObject.selectedIndex = 0;
	}
		
}

function getCompanyName(cmpnyNm)
{
	while(cmpnyNm.indexOf('&amp;nbsp;') != -1)
		cmpnyNm = cmpnyNm.replace('&amp;nbsp;',' ');
	return cmpnyNm;
}

