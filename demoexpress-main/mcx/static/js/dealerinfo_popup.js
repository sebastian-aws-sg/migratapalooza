// dealerinfo_popup.js
// This method is used to display the firm details in a popup window
function popUpWindow(id,path) {
	//The action that fetches the firm details and the dealerid whose details are to be fetched is set as the url
	var url = path+'/action/displayDealer?'+"dealer="+id;
	//The firm details opens in a popup window	   
    var win = window.showModalDialog(url,'','top:50;left:50px;status:no;dialogHeight:270px;dialogWidth:610px;status:no;scroll:no;help:no;unadorned:yes');
}
function userDetailsPopUp(id){
	var url = '/mcx/action/displayUser?'+"userId="+id;
	var win = window.showModalDialog(url,'','top:50;left:50px;status:no;dialogHeight:240px;dialogWidth:610px;status:no;scroll:no;help:no;unadorned:yes');
}
function close_win()
{
	window.close();
}
function openPending(id,param){		
	var myObject = new Object();
	myObject.param = param;
	myObject.selectedTab=id;
	var url = "/mcx/action/getDealerDetailsMain";	
	var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:600px;dialogWidth:1010px;status:no;scroll:yes;help:no;unadorned:no');
	
}

function openPendingEnrollmentApproval(param,id){
	var myObject = new Object();
	myObject.param = param;
	myObject.selectedTab=id;
	var url = "/mcx/action/enrollmentApprovalPopup"
	var win = window.showModalDialog(url,myObject,'top:50;left:50px;status:no;dialogHeight:600px;dialogWidth:1010px;status:no;scroll:yes;help:no;unadorned:no');
}