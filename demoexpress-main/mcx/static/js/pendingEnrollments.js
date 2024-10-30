//pendingEnrollments.js

//This function is called when the user clicks on the Approve or Deny Button to open a popup window
function openWindow(dealerCode,username,uploadTime,approve,path) {	
	var url = '/mcx/action/denyMain';
	var myObject = new Object();
	myObject.dealerCode = dealerCode;
	myObject.username = username;
	myObject.uploadTime = uploadTime;
	myObject.approve = approve;	
	var win = window.showModalDialog(url,myObject,'top=50;left=50px;height=350;width=482;toolbar=no;resizable=no;status=no;memubar=no;location=no;scrollbars=yes;help:no;unadorned:yes');
	if(win =='Refresh'){
	   window.location.href='/mcx/action/dealerPendingApproval?selectedTab=P&printParam=N';
    }else{
	  window.location.href='/mcx/action/dealerPendingApproval?selectedTab=P&printParam=N';
	}
  
}

//This function is used to check all the MCA's when ALL is selected
function CheckAll( checkAllBox )
{
	var frm = document.PendingApprovalForm.elements['transaction.selectedMCAs'];

  var actVar = checkAllBox.checked ;
  if(frm.type!='checkbox'){
  for(i=0;i< frm.length;i++)
  {
    e=frm[i];
      e.checked= actVar ;
  }
  }else
  	frm.checked = actVar ;

}

//When one of the MCa is unchecked then the check in the All should be unchecked
function unCheck(){
document.PendingApprovalForm.all.checked=false;
}

//To close the popup window when the approval is success

function fnOnLoad(){

var successParam = document.PendingApprovalForm.elements['transaction.pendingEnrollmentSuccess'].value;
if(successParam =="success"){
	window.returnValue="Refresh";
	self.close();
	}
}

//To close the popup window when the deny is success and to check all the checkbox in the deny screen during loading itself

function fnDenyOnLoad() {
var successParam = document.PendingApprovalForm.elements['transaction.pendingEnrollmentSuccess'].value;

var frm = document.PendingApprovalForm.elements['transaction.selectedMCAs'];
if(frm.type!='checkbox'){
for(i=0;i< frm.length;i++)
  {
    e=frm[i];
  
      e.checked= true ;
  }
  }else{
  	frm.checked = true;
  	}
  
  if(successParam =="success"){  		
  	window.returnValue="Refresh"; 
	self.close();
	}
}


//To check atleast one Mca is selected for approval
function fnReload() {

	var form = document.PendingApprovalForm.elements['transaction.selectedMCAs'];
	var submitForm = document.approve_form;
	var status = false;
	var checked = document.getElementsByName("radioYes");
	
	for(var i = 0 ; i < checked.length ; i++){
		if(checked[i].checked){
			if(i != 0){
				window.close();
				return false;
			}
		}
	}
	
	//this check is needed coz if there is more than one mca is there for approval then 
	//at the time form.type will not be checkbox
	if(form.type!='checkbox'){
	for(i=0; i< form.length; i++){
	var a = form[i];
	if(a.checked==true){
		status = true;
	break;
	}
	}
	}else{
	if(form.checked==true){
	status = true;
	}
	}
	if(status != true){
	alert("Please select at least one MCA");
	return false;
	}else{
    submitForm.submit();
    return true;
	}
	
}

function fnReturn(){
	var checked = document.getElementsByName("radioYes");
	for(var i = 0 ; i < checked.length ; i++){
		if(checked[i].checked){
			if(i != 0){
				window.close();
				return false;
			}
		}
	}
	return true;
}
