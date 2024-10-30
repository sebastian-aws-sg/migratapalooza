//Java Script Document

//This function is used to check or uncheck all the checkboxes when the checkbox in grid header is clicked
function CheckAll( checkAllBox )
{

	var selected = document.getElementsByName("transaction.selectedDocuments");
	if(checkAllBox.checked)
	{	
		if (selected.length)
		{ //For Multiple checkbox
			var len = selected.length;
		    for(var i=0;i< len;i++)
		    {
		    	if (!selected[i].checked)
		    	{
		    		selected[i].checked = true;
		    	}
			}
	 	}
	 	else
	 	{ //For Single checkbox
			checked = selected.checked;
		}
	}
    else 
    {
		var len = selected.length;
		for(var i=0;i< len;i++)
		{
		    if (selected[i].checked)
		    {
		    	selected[i].checked = false;
		    }
		}
   	}

/*
	alert('1');
	var selected = document.getElementsByName("selectedDocuments");
	alert('selected >>>'+selected);
	var actVar = checkAllBox.checked ;
	alert('actVar >>>'+actVar);
	if(selected.type != 'checkbox')
	{
		alert('selected.type >>>'+selected.type);
		alert('selected.length >>>'+selected.length);
	    for(i=0;i< selected.length;i++)
	    {
			e=selected[i];
	        alert("e.type >>>"+e.type);
	        if ( e.type=='checkbox')
	        	e.checked = actVar ;
		}
	}
    else 
   		selected.checked = actVar;
*/   		
}

function UncheckHeader(){
	document.ManagedDocumentForm.selectall.checked = false;
}

// This function is called when any of the values in dropdown in PreExistingor Others is selected.
//If Add CounterParty is selected then the addCounterParty popup opens if the user has access.
//If a manually added counterParty is selected the edit Cp button is enabled if the user has access.
function addCpWindow(Object) {	

	var loginCmpnyId = document.ManagedDocumentForm.cmpnyId.value;
	var maxRows = 5;
	
	var form = document.ManagedDocumentForm ;
	cboObject=document.getElementById("selectedDealerClient");
	
	var objClaim = cboObject.options[cboObject.selectedIndex];
	
	
	var screen = document.getElementById("manageDocTab").value;
		
	var RenameButton = form.RenameCP;
	var DeleteButton = form.DeleteCP;
	var RenameButtonDisabled = form.RenameCPDisabled;
	var DeleteButtonDisabled = form.DeleteCPDisabled;
	
	var name = objClaim.text;
	
	/*var id = objClaim.value;
	var pos = id.indexOf('-');
	var companyId = "";
	if(pos>0){
		companyId = id.substring(0,pos);
	}*/
	var companyId = objClaim.value;
	
	if(loginCmpnyId == companyId && screen == "Others")
	{
	
		for(var index = 0; index < maxRows; index++)
			{
				
				var cpView = document.getElementsByName("transaction.selectedCPView["+index+"]");
				cpView[0].checked = false;
				cpView[1].checked = true;
				
				cpView[0].disabled = true;
				cpView[1].disabled = true;
			}
			
			/*RenameButton.disabled = true;
			DeleteButton.disabled = true;
		
			RenameButton.style.cursor = "default";
			DeleteButton.style.cursor = "default";*/
			
			RenameButton.style.display = 'none';
			RenameButtonDisabled.style.display = 'block';
			DeleteButton.style.display = 'none';
			DeleteButtonDisabled.style.display = 'block';
			
			return;
	}

	
	var len = name.length;
	--len;
  	
			
	if(len == name.lastIndexOf('*')){	
		/*RenameButton.disabled = false;
		DeleteButton.disabled = false;
		RenameButton.style.cursor = "hand";
		DeleteButton.style.cursor = "hand";*/
		RenameButton.style.display = 'block';
		RenameButtonDisabled.style.display = 'none';
		DeleteButton.style.display = 'block';
		DeleteButtonDisabled.style.display = 'none';
		
		if(screen == "Others")
		{
			for(index = 0; index < maxRows; index++)
			{
				var cpView = document.getElementsByName("transaction.selectedCPView["+index+"]");
				cpView[0].checked = false;
				cpView[1].checked = true;
				
				cpView[0].disabled = true;
				cpView[1].disabled = true;
			}
  		} /*else
  		{
			// read/selectable mode  		
				cpView[0].disabled = false;
				cpView[1].disabled = false;
  		}*/
	} else {
		/*RenameButton.disabled = true;
		DeleteButton.disabled = true;
		
		RenameButton.style.cursor = "default";
		DeleteButton.style.cursor = "default";*/
		
		RenameButton.style.display = 'none';
		RenameButtonDisabled.style.display = 'block';
		DeleteButton.style.display = 'none';
		DeleteButtonDisabled.style.display = 'block';
						
		if(screen == "Others")
		{
			for(index = 0; index < maxRows; index++)
			{
				var cpView = document.getElementsByName("transaction.selectedCPView["+index+"]");
				cpView[0].disabled = false;
				cpView[1].disabled = false;
			}
		}
	}	
	
	if(Object.value=="Add Counterparty" ){	
		var url = '/mcx/action/addcpWindow';	
		var win = window.showModalDialog(url,objClaim,'dialogLeft=50; dialogTop=50;dialogHeight=280px;dialogWidth=413px;status:no;help:no;unadorned:yes');   
		
		/*if(win == 'success'){
		
			if(screen == "PreExisting"){
		  	  window.location.href='/mcx/action/manageDoc?manageDocTab=P';
	 		}
  			else if(screen == "Others"){
  				window.location.href='/mcx/action/manageDoc?manageDocTab=O';
  			}
  		}*/
  			document.getElementById("selectedDealerClientId").value = win;
  		if(screen == "PreExisting"){
	    	document.forms[0].action ='/mcx/action/manageDoc?manageDocTab=P';
	 	}
		else if(screen == "Others"){
  			document.forms[0].action ='/mcx/action/manageDoc?manageDocTab=O';
  		}
  		
  		  		
  		document.forms[0].submit();
	}	
	
}

// This function is used to enable or disable the edit/delete button based on the selected value
// while refreshing the page after add or edit or delete.
function selectdefault(){
	
	if (document.forms[0].CancelDisabled)
	{
		document.forms[0].CancelDisabled.style.display = 'none';
	}

	if (document.getElementById("selectedDealerClient") == null)
	{
		return;
	}
	var selectedCp = document.getElementById("selectedDealerClient").value;
	
	var loginCmpnyId = document.ManagedDocumentForm.cmpnyId.value;
	var maxRows = 5;
	
	/*var text = selectedCp;
	var pos = text.indexOf('-');
	
	var id = '';
	var name = '';
	
	if(pos > 0)
	{
	   id = text.substring(0,pos);
	   var lastPos = text.lastIndexOf('-');
	   name = text.substring(pos+1,lastPos);
	}*/
	
	//document.getElementById('RenameCPButton').className = 'attachDisabled';
	var id = selectedCp;

	var form = document.ManagedDocumentForm ;
	var RenameButton = form.RenameCP;
	var DeleteButton = form.DeleteCP;
	var RenameButtonDisabled = form.RenameCPDisabled;
	var DeleteButtonDisabled = form.DeleteCPDisabled;
	
	var cboObject = document.getElementById("selectedDealerClient");
	
	if (cboObject!=null)
	{
		var objClaim = cboObject.options[cboObject.selectedIndex];
		var screen = document.getElementById("manageDocTab").value;
		
		var name = objClaim.text;
		
		
	
		var len = name.length;
		--len;
		var maxRows = 5;
		if( form.RenameCP || form.DeleteCP) 
		{	
			if(len == name.lastIndexOf('*'))
			{	
				//RenameButton.disabled = false;
				//DeleteButton.disabled = false;
				RenameButton.style.display = 'block';
				RenameButtonDisabled.style.display = 'none';
				DeleteButton.style.display = 'block';
				DeleteButtonDisabled.style.display = 'none';
				
				/*document.getElementById('RenameCPButton').style.display = "none";
				document.getElementById('RenameCPButtonDisabled').style.display = "block";*/
				if(screen == "Others"){
					for(index = 0; index < maxRows; index++)
					{
						var cpView = document.getElementsByName("transaction.selectedCPView["+index+"]");
						/*cpView[0].checked = false;
						cpView[1].checked = true;*/
				
						cpView[0].disabled = true;
						cpView[1].disabled = true;
					}
				}
			} else 
			{
				//RenameButton.disabled = true;
				//DeleteButton.disabled = true;
				RenameButton.style.display = 'none';
				RenameButtonDisabled.style.display = 'block';
				DeleteButton.style.display = 'none';
				DeleteButtonDisabled.style.display = 'block';
				if(screen == "Others")
				{
					for(index = 0; index < maxRows; index++)
					{
						var cpView = document.getElementsByName("transaction.selectedCPView["+index+"]");
						/*cpView[0].checked = false;
						cpView[1].checked = true;*/
						
						if(loginCmpnyId == id)
						{
							cpView[0].disabled = true;
							cpView[1].disabled = true;
						}
					}
					
					
				}
			}
		}
	}
}

//This function is used to open the popup for editCp.
function renameCpWindow() {	
	
	var frm = document.ManagedDocumentForm ;
	cboObject=document.getElementById("selectedDealerClient");
	var objClaim = cboObject.options[cboObject.selectedIndex];
	
	
	var screen = document.getElementById("manageDocTab").value;
	
    var url = '/mcx/action/editcpWindow';
	var win = window.showModalDialog(url,objClaim,'dialogLeft=50; dialogTop=50; dialogHeight=280px;dialogWidth=413px;status:no;help:no;unadorned:yes');   
		
	/*if(win == 'success'){
		if(screen == "PreExisting"){
	    	window.location.href='/mcx/action/manageDoc?manageDocTab=P';
	 	}
		else if(screen == "Others"){
  			window.location.href='/mcx/action/manageDoc?manageDocTab=O';
  		}
  	}
  	else
  	{*/
  		
  			document.getElementById("selectedDealerClientId").value = objClaim.value;
  		if(screen == "PreExisting"){
	    	document.forms[0].action ='/mcx/action/manageDoc?manageDocTab=P';
	 	}
		else if(screen == "Others"){
  			document.forms[0].action ='/mcx/action/manageDoc?manageDocTab=O';
  		}
  		
  		/*if(screen == "PreExisting"){
	    	window.location.href='/mcx/action/manageDoc?manageDocTab=P&selectedDealerClient='+objClaim.value;
	 	}
		else if(screen == "Others"){
  			window.location.href='/mcx/action/manageDoc?manageDocTab=O&selectedDealerClient='+objClaim.value;
  		}*/
  		
  		document.forms[0].submit();
  	
  	//}
}




//This function is used to open the Delete Counterparty Dialog Window.


function deleteCpWindow() {	

	var manageDocs = '';

	var frm = document.ManagedDocumentForm;
	var deleteCmpnyId = document.getElementById("selectedDealerClient").value;
	var screen = document.getElementById("manageDocTab").value;
	/*var documentInd = deleteCmpnyId.substring(deleteCmpnyId.length-1, deleteCmpnyId.length);
	
	var text = deleteCmpnyId;
	var pos = text.indexOf('-');
	var id = text.substring(0,pos);
	var lastPos = text.lastIndexOf('-');
	var name = text.substring(pos+1,lastPos);*/
	var id = deleteCmpnyId;
	manageDocs = 'D';
	
	var url = "/mcx/action/deletecpWindow1?manageDocs="+manageDocs+"&deleteCmpnyId="+id;//+"&documentInd="+documentInd;
	var win = window.showModalDialog(url,deleteCmpnyId,'dialogLeft=50; dialogTop=50; dialogHeight=350px;dialogWidth=500px;status:no;help:no;unadorned:yes');   
   
	if(win == 'success'){
		if(screen == "PreExisting"){
	    	window.location.href='/mcx/action/manageDoc?manageDocTab=P';
	 	}
		else if(screen == "Others"){
  			window.location.href='/mcx/action/manageDoc?manageDocTab=O';
  		}
  	}
  	else
  	{
  		
  		document.getElementById("selectedDealerClientId").value = deleteCmpnyId;
  		if(screen == "PreExisting"){
	    	document.forms[0].action ='/mcx/action/manageDoc?manageDocTab=P';
	 	}
		else if(screen == "Others"){
  			document.forms[0].action ='/mcx/action/manageDoc?manageDocTab=O';
  		}
  		/*if(screen == "PreExisting"){
	    	window.location.href='/mcx/action/manageDoc?manageDocTab=P&selectedDealerClient='+deleteCmpnyId;
	 	}
		else if(screen == "Others"){
  			window.location.href='/mcx/action/manageDoc?manageDocTab=O&selectedDealerClient='+deleteCmpnyId;
  		}*/
  		
  		document.forms[0].submit();
  	}
  	
}



//This function is used to close the addCP window if the CP is added.
function addload(){
	var counterPartyId = document.getElementById("counterPartyId").value;
	if(document.getElementById("manageCPStatus") != null){
		if(document.getElementById("manageCPStatus").value == "ADDCPSUCCESS"){
				window.close();
				window.returnValue = counterPartyId;
				
		}else if(document.getElementById("manageCPStatus").value != "ADDCPFAILURE"){
			
			document.getElementById("manageCPStatus").value = '';
			document.getElementById("dealerClientName").value = '';
			document.getElementById("dealerClientName").focus();
		}else 
		{
			document.getElementById("manageCPStatus").value = "";
		}
	}
}

//This function is used to close the editCP window if the CP is renamed.
function editload(){	
	var obj = window.dialogArguments;	
   if(obj!=null){
   		
   		if(document.getElementById("manageCPStatus") != null){
      		if(document.getElementById("manageCPStatus").value=="ADDCPSUCCESS"){
      			window.close();
      			window.returnValue = "success";
      			return;
			}else if(document.getElementById("manageCPStatus").value == "ADDCPFAILURE")
			{
			//document.forms[0].reset();
				document.getElementById("manageCPStatus").value = '';
			 	return;
			}
			else
			{
				document.getElementById("manageCPStatus").value = '';
			}
		}
		
		var text = obj.value;
		
		var label = obj.text.substring(0,obj.text.length-1);
		
		var id = text;	
		/*var pos = text.indexOf('-');
		var id = text.substring(0,pos);
		var lastPos = text.lastIndexOf('-');
		var name = text.substring(pos+1,lastPos);*/
		var name = label;
		var len = name.length;
		--len;
	
		if(len == name.lastIndexOf('*')){	
			name = name.substr(0,len);
		}		
		document.getElementById("selectedDealerClient").value=id;
		document.getElementById("dealerClientName").value=label;
		document.getElementById("CPName").value=text;
  }
}

/*function replaceSpaceCharacters(html) {

	
	if (html) {
		
		
		for(var j = 0; j < set[0].length; j++){
			
			html = html.replace('&nbsp;',',');
		}
	}

	// Return the HTML or an empty string if no HTML was supplied
	return html || "";
}*/


//This function is used to close the delete CP window if the CP is deleted.
function deleteload(){
	if(document.getElementById("manageCPStatus") != null)
	{
		
		if(document.getElementById("manageCPStatus").value == "success"){
				window.close();
				window.returnValue = "success";
				return;
				
		}else if(document.getElementById("manageCPStatus").value == "D"){
			
			var reassign = confirm("Do you want to allow duplicates?");
			
			if(reassign != true){
				window.close();
				return;
			}
			else
			{
				
				document.getElementById("duplicateDocsCheck").value = "A";
				document.forms[0].action = "/mcx/action/deleteReassignCPWindow";
				document.forms[0].submit();
			} 
		}
		else if(document.getElementById("manageCPStatus").value != "failure"){
			
			document.getElementById("manageCPStatus").value = '';
			return;
			
		}else
		{
			document.getElementById("manageCPStatus").value = "";
		}
	}
}







// Removes leading and trailing spaces from the passed string.
function trim(inputString) {
  
   if (typeof inputString != "string") { return inputString; }
   var retValue = inputString;
   var ch = retValue.substring(0, 1);
   while (ch == " ") { // Check for spaces at the beginning of the string
      retValue = retValue.substring(1, retValue.length);
      ch = retValue.substring(0, 1);
   }
   ch = retValue.substring(retValue.length-1, retValue.length);
   while (ch == " ") { // Check for spaces at the end of the string
      retValue = retValue.substring(0, retValue.length-1);
      ch = retValue.substring(retValue.length-1, retValue.length);
   }
   
   return retValue; // Return the trimmed string back to the user
} // Ends the "trim" function


//This function is used to check if a CP is entered or not.
function emptyCP(){
		
        var name = document.getElementById("dealerClientName");
       var cmpnyName = trim(name.value);
        if(cmpnyName.length == 0){
        	alert("Enter the CounterParty name");
        	document.getElementById("dealerClientName").value = "";
        	document.getElementById("dealerClientName").focus();
        	return false;
        }
        var validname = checkCharacters(cmpnyName);
        
        if(validname)
        {
        	document.getElementById("dealerClientName").value = convertToHTMLName(document.getElementById("dealerClientName").value);
        	return true;
        }
       else 
       {
       		alert("Please Enter atleast one character");
       		return false;
       }
        
}
		
		
		
//This function is used to check if the CP is edited or not
		
function checkCP(){
		 var name = document.getElementById("dealerClientName");
        
        var cmpnyName = trim(name.value);
        
        //Setting value in selectedDocumentType property to check if same CP name is given
        var cmpnyOldName = document.getElementById("CPName").value;
        
        var trimCmpnyOLdName = trim(cmpnyOldName);
        
        if(cmpnyName.length == 0){
        	alert("Enter the CounterParty name");
        	document.getElementById("dealerClientName").value = "";
        	document.getElementById("dealerClientName").focus();
        	return false;
        }
         var validname = checkCharacters(cmpnyName);
        if(validname)
        {
        	document.getElementById("dealerClientName").value = convertToHTMLName(document.getElementById("dealerClientName").value);
        	return true;
        }
       else 
       {
       		alert("Please Enter atleast one character");
       		return false;
       }
 }
 
 
 function convertToHTMLName(str) {

  var bstr = '';
  for(i=0; i<str.length; i++)
  {
    if(str.charCodeAt(i)>127)
    {
     if(str.charCodeAt(i)==160)
	  {
	  	bstr += ' ';
	  }
	  else
	  {
	  	bstr += '&#' + str.charCodeAt(i) + ';';
	  }
    }
    else
    {
      bstr += str.charAt(i);
    }
   }
   
   return bstr;
}


 // This function is used to set the property action for search button in search screen to decide the flow
 function searchAction(){
	
 	document.forms[0].action = "/mcx/action/search";
 	var fileName = document.getElementById("docNameTextBox").value;
 	
 	document.getElementById("selectedDealerClientId").value = document.getElementById("selectedDealerClient").value;
 	document.getElementById("docNameHidden").value = document.getElementById("docNameTextBox").value;
 	document.getElementById("selectedDocumentTypeHidden").value = document.getElementById("selectedDocumentTypeValue").value;
 	
 	//alert(document.getElementById("docNameTextBox").value);
 	
 	var index = 0;
 	var length = fileName.length;
 	var specialCharacter = false;
 	while(index<length){
 		if(fileName.charAt(index)=='>'||fileName.charAt(index)=='<'
 		||fileName.charAt(index)==':'|| fileName.charAt(index)=='\\'|| fileName.charAt(index)=='|')
 		{	
 			specialCharacter = true;
 			break;
 		}
 		index++;
 	}
 	
 	
 	if(specialCharacter == false){
 		document.getElementById("searchActionParam").value = "SEARCH";
 		
 	document.forms[0].submit();
 	//	return true;
 	}
 	else {
 	
 	alert("The characters '\\: < >|' are not allowed in a file name");
 	    return ;
 	}
 
 }
 
 // This function is used to set the property action for search button in search screen to decide the flow
 function clearAction(){
 	document.forms[0].action = "/mcx/action/search";
 	document.getElementById("searchActionParam").value = "CLEAR";
 	document.forms[0].submit();
 }
 
 


// This function is called when the user clicks on delete documents to confirm the delete.
function deleteDocument(){
	
	var selected = document.getElementsByName("selectedDocuments");
	var none = false;
	if(selected.type == 'checkbox'){
		if(selected.checked == true)
			none = true;
			}
	else {
	
	  	for(i=0;i< selected.length;i++)
	    	{
	        	e=selected[i];
	            	if ( e.type=='checkbox'){
	                  if(e.checked ==true){
	                  	none = true;
	                  	break;
	                  	}
	                 }	
	    	}
	   }
	   
	    if(none == false){
	    	alert("Please select a document to delete");
	    	return false;
    	} 
    	else{
    		var confirmdelete = confirm("Do you want to delete the selected document(s)?");
    		document.getElementById("transaction.action").value = "DELETE";
    		return confirmdelete;
    	}
}

function cplistStatus(docInd)
{
	
	var reassignButton = document.forms[0].reassign;
	var i=0;
	
	for (i=0; i<reassignButton.length; i++) 
	{
    	if (reassignButton[i].checked) 
    	{
    	
	  		if (i==0 && docInd == 'Y')
  			{
  				if(document.forms[0].reassignedDealerClient != null) 
  				{
	  				document.forms[0].reassignedDealerClient.disabled=false;
	  			}
  			} else if(i==1)
  			{
  				window.close();
  			} else
  			{
  				if(document.forms[0].reassignedDealerClient != null) 
  				{
	  				document.forms[0].reassignedDealerClient.disabled=true;
	  			}
  			}
        	break;
    	}
  	}
}


function deleteMCADoc()
{
//alert("inside the deleteMCADoc >>>");

	var selected = document.getElementsByName("transaction.selectedDocuments");
//alert("inside the deleteMCADoc selected >>>" + selected);
	var len = selected.length;
	var isChecked = 'false';
	for(var i=0;i< len;i++)
	{
	    if (selected[i].checked)
	    {
	    	isChecked = 'true';
	    	break;
	    }
	}	
	if (isChecked == 'true')
	{
		var confirmdelete = confirm("Do you want to delete the selected document(s)?");
		if(confirmdelete == true){
			var screen = document.getElementById("manageDocTab").value;
			
			if(screen == "PreExisting"){
	    		document.forms[0].action = '/mcx/action/deleteCPDoc?manageDocs=P';
			}
			else if(screen == "Others"){
				document.forms[0].action = '/mcx/action/deleteCPDoc?manageDocs=O';
			}
			else if(screen == "Search"){
				
				document.forms[0].action = '/mcx/action/deleteCPDoc?manageDocs=S';
			}	
			
			document.forms[0].submit();	
		}
		
	}
	else
	{
		alert("Select atleast one document to delete");
	}
}


function downloadMCADoc(id,docType,cpId) 
{
	
	//var screen = document.getElementById("manageDocTab").value;
	//The action that downloads the document from the database
	
	
	  document.forms[0].action = "/mcx/action/downloadMCADoc?manageDocs="+docType+"&docId="+id+"&counterPartyId="+cpId;	
	

	document.forms[0].submit();
}


function setCPListStatus(docInd)
{

	if (document.forms[0].reassignedDealerClient != null)
	{
		if (docInd == 'Y')
		{
			document.forms[0].reassignedDealerClient.disabled=false;
		} 
		else
		{
			document.forms[0].reassignedDealerClient.disabled=true;
		}
	}
}

function deleteReassignButton()
{
	
	//var docInd = document.getElementById("documentInd").value;
	var reassignButton = document.getElementsByName("reassign");

	if (reassignButton != null)
	{
		for (i=0; i<reassignButton.length; i++) 
		{
	    	if (reassignButton[i].checked) 
	    	{
		  		/*if (i==0 )
	  			{
	  				//document.forms[0].action = "/mcx/action/deleteReassignCPWindow?documentInd="+docInd;
	  				//document.forms[0].submit();
	  			} else 
	  			{
	  				window.close();
	  			}*/
	  			if(i!=0)
	  			{
	  				window.close();
	  				return false;
	  			}
	        	break;
	    	}
	  	}
	}
  	return true;
}

function uploadPreexistingDocs()
{	
	var selectedCp = document.getElementById("selectedDealerClient").value;
	var maxRows = 5;
	var upload = validation();
	if(upload ==true)
	{
		
		var busValidation = busValidatePreExisting();
		
		if(busValidation)
		{
			var screen = document.getElementById("manageDocTab").value;
			var uploadDoc = confirm("Do you want to proceed uploading the document(s)?");
			
			for(var index = 0;index < maxRows;index++)
			{
				var fileName = document.getElementsByName("transaction.uploads["+index+"]");
				var pathName = document.getElementById("transaction.filesPath["+index+"]");
				pathName.value = fileName[0].value;
			}
			
			
			
			if(uploadDoc)
			{
				/*document.forms[0].Cancel.disabled = "true";
				document.forms[0].Cancel.style.cursor = 'default';*/
				document.forms[0].Cancel.style.display = 'none';
				document.forms[0].CancelDisabled.style.display = 'block';
				
				/*if(screen == "PreExisting")
				{*/
					//document.forms[0].action = "/mcx/action/uploadManageDoc?manageDocTab=P&counterPartyId="+selectedCp;
					
					document.forms[0].action = "/mcx/action/uploadManageDoc?manageDocTab=P";
				/*}
				else if(screen == "Others")
				{
					document.forms[0].action = "/mcx/action/uploadManageDoc?manageDocTab=O";
				}*/


				document.forms[0].submit();
				
			}
		}
	}
}

function validation()
{
	populateAllDate();
	var selectedCp = document.getElementById("selectedDealerClient").value;
	if(selectedCp=="default")
	{
		alert("Select a CounterParty to upload documents")
		return false;
	}
	var valid =  true;
	var validDate = null;
	var validFileName = null;
	var index = 0;
	var count = 0;
	for(index=0;index<5;index++){
	
		validDate = null;
		validFileName = null;
			
		var date = document.getElementsByName("transaction.executedDate["+index+"]");
		
		
		if(date[0].value!="" && date[0].value != 'mm/dd/yyyy')
		{
			 validDate = ValidateDate(date[0].value);
			 if(validDate == false)
			 {
			 	 date[0].focus();
			 }
		}
		var fileName = document.getElementsByName("transaction.uploads["+index+"]");

		if(fileName[0].value!=""){
			 validFileName = ValidateName(fileName[0].value);
		}
         

		var mcaNameObject = document.getElementsByName("transaction.selectedMCANames["+index+"]");
		var mcaName = mcaNameObject[0].value;

		if(validDate && validFileName && mcaName != "default"){
			valid = true;
		} else if(validDate==null && validFileName==null && mcaName == "default")
		{
			valid = true;
			count++;
		} else if(mcaName != "default" && validFileName==null && validDate){
			valid = true;
		} else if(mcaName != "default" && validFileName==null && validDate == null){
			valid = false;
			alert("Please Enter the Execution Date");
			break;
		}else if(mcaName != "default" && validFileName && validDate == null){
			valid = false;
			alert("Please Enter the Execution Date ");
			break;
		}else if(mcaName == "default" && validFileName==null && validDate){
			valid = false;
			alert("Please Select MCA Name");
			break;
		}else if(mcaName == "default" && validFileName && validDate){
			valid = false;
			alert("Please Select MCA Name");
			break;
		}else if(mcaName == "default" && validDate == null && validFileName){
			valid = false;
			alert("Please Select MCA Name and enter Execution Date ");
			break;
		}else{
			valid = false;
			break;
		}
		
	}
	
	if(count == 5){
		alert("Please Select atleast one MCA Name and enter the corresponding details to upload the file");
		return false;
	}
	if(valid){
		return true;
	}
	else {
		return false;	
	}
}

function ValidateName(fileName){
	
	var regex =  '^(([a-zA-Z]:))';//To check for c:\ format
	var regex1 = '^(/{1})';// for OS which has /
	var regex2 = '^(\\\\{2})';// For Files in Shared paths
	var result = fileName.search(regex);
	var result1 = fileName.search(regex1);
	var result2 = fileName.search(regex2);
		
	if((result < 0 & result1 < 0 & result2 < 0))
	{
		alert("Please enter the correct file path");
		return false;
	}

	
	
	var pos = 0;
 	var length = fileName.length;
 	
 	var filestart = fileName.lastIndexOf('\\');
	var name = fileName.substring(filestart+1,fileName.length);

 	if(name.length > 216)
 	{
 		alert("FileName " + name + " should not be more than 216 characters");
 		return false;
 	}
 	
 	
 	var specialCharacter = false;
 	while(pos<length){
 		if(fileName.charAt(pos)=='?' || fileName.charAt(pos)=='"'||fileName.charAt(pos)=='>'||fileName.charAt(pos)=='<'
 		||  fileName.charAt(pos)=='|')
 		{	
 			specialCharacter = true;
 			break;
 		}
 		pos++;
 	}
 	pos=0;
 	if(specialCharacter == false){
 		
 		pos = fileName.lastIndexOf(".");
 		var ext = (fileName.substring(pos,fileName.length)).toLowerCase();
 		
 		if(ext==".doc"||ext==".zip"||ext==".xls"||ext==".pdf"||ext==".jpg"||ext==".tif"||ext==".gif"||ext==".ppt"||ext==".pps"){
 			return true;
 		}
 		else {
 			alert("File Extension is not supported. Supported file extensions are .doc,.zip,.xls,.pdf,.jpg,.tif,.ppt,.pps");
 			return false;
 		}
 	}
 	else {
 		alert("The characters '/\:? \"< >|' are not allowed in a file name.");
 	    return false;
 	}
}

function cancel1()
{
	var selectedCp = document.getElementById("selectedDealerClient").value;
	var screen = document.getElementById("manageDocTab").value;
	var loginCmpnyId = document.ManagedDocumentForm.cmpnyId.value;
	var cancel = confirm("Do you want to cancel uploading the document(s)?");
	if(cancel){
		document.forms[0].reset();
	}
	var maxRows = 5;
	var id = selectedCp;
	document.getElementById("selectedDealerClient").value = selectedCp;
	/*var text = selectedCp;
	var pos = text.indexOf('-');
	
	var id = '';
	var name = '';
	
	if(pos > 0)
	{
	   id = text.substring(0,pos);
	   var lastPos = text.lastIndexOf('-');
	   name = text.substring(pos+1,lastPos);
	}*/
	if(loginCmpnyId == id && screen == "Others")
	{
		
		for(var index = 0; index < maxRows; index++)
			{
				
				var cpView = document.getElementsByName("transaction.selectedCPView["+index+"]");
				cpView[0].checked = false;
				cpView[1].checked = true;
				
				cpView[0].disabled = true;
				cpView[1].disabled = true;
			}
			return;
	}
	var cboObject=document.getElementById("selectedDealerClient");
	var objClaim = cboObject.options[cboObject.selectedIndex];
	var name = objClaim.text;
		
	var len = name.length;
	--len;
	
	
	if(screen == "Others" )
	{
		if(len == name.lastIndexOf('*') && len > 0)
		{
			
			for(index = 0; index < maxRows; index++)
				{
					var cpView = document.getElementsByName("transaction.selectedCPView["+index+"]");
					cpView[0].checked = false;
					cpView[1].checked = true;
				
					cpView[0].disabled = true;
					cpView[1].disabled = true;
				}
		}
		else
		{
			for(index = 0; index < maxRows; index++)
			{
				var cpView = document.getElementsByName("transaction.selectedCPView["+index+"]");
				cpView[0].checked = false;
				cpView[1].checked = true;
			}
		}
	}
}

function uploadOtherDocs()
{
	var upload = validateOtherupload();
	var maxRows = 5;
	var selectedCp = document.getElementById("selectedDealerClient").value;

	if(upload ==true)
	{
	
		var businessValidation = busValidateOthers();
		
		if(businessValidation)
		{
			var screen = document.getElementById("manageDocTab").value;
			
			var uploadDoc = confirm("Do you want to proceed uploading the document(s)?");
			
			for(var index = 0;index < maxRows;index++)
			{
				var fileName = document.getElementsByName("transaction.uploads["+index+"]");
				var pathName = document.getElementById("transaction.filesPath["+index+"]");
				pathName.value = fileName[0].value;
			}
			
			if(uploadDoc)
			{
				/*document.forms[0].Cancel.disabled = "true";
				document.forms[0].Cancel.style.cursor = 'default';*/
				document.forms[0].Cancel.style.display = 'none';
				document.forms[0].CancelDisabled.style.display = 'block';
				
				/*if(screen == "PreExisting")
				{
					document.forms[0].action = "/mcx/action/uploadManageDoc?manageDocTab=P";
				}
				else if(screen == "Others")
				{*/
					//document.forms[0].action = "/mcx/action/uploadManageDoc?manageDocTab=O&counterPartyId="+selectedCp;
					
					document.forms[0].action = "/mcx/action/uploadManageDoc?manageDocTab=O";
				//}

	
				document.forms[0].submit();
				
			}
		}
	}
}



function validateOtherupload()
{
	var index = 0;
	
	var validFileName = null;
	var valid = true;
	var count = 0;

	var selectedCp = document.getElementById("selectedDealerClient").value;
	if(selectedCp=="default"){
		alert("Select a CounterParty to upload documents")
		return false;
	}

	for(index = 0; index < 5; index++)
	{
		
		validFileName = null;
		
		var fileName = document.getElementsByName("transaction.uploads["+index+"]");
		
		if(fileName[0].value!=""){
			 validFileName = ValidateName(fileName[0].value);
		}
		if(validFileName){
			valid = true;		
		}
		else if(validFileName == null){
			valid = true;
			count++;
		}
		else {
			valid = false;
			break;
		}
	}
	
	if(count == 5){
		alert("Enter the details of atleast one file to upload the file");
		return false;
	}
	if(valid){
		return true;
	}
	else {
		return false;	
	}
	
	
}

function busValidatePreExisting(){
	var validation = true;
	
	for (var index = 0; index < 5; index++)
	{
		var mcaNameObject = document.getElementsByName("transaction.selectedMCANames["+index+"]");
		var mcaName = mcaNameObject[0].value;
		
		var dateObject = document.getElementsByName("transaction.executedDate["+index+"]");
		var date = dateObject[0].value;
		
		if(mcaName!="default" && date!="")
		{	
			for (var nextIndex = index+1;nextIndex < 5; nextIndex++)
			{
				var mcaNameObject1 = document.getElementsByName("transaction.selectedMCANames["+nextIndex+"]");
				var mcaName1 = mcaNameObject1[0].value;
		
				var dateObject1 = document.getElementsByName("transaction.executedDate["+nextIndex+"]");
				var date1 = dateObject1[0].value;
			
				if(mcaName == mcaName1 && date == date1 && validation == true)
				{
					alert("MCA Name & Execution Date Cannot Be Same for Document "+ (index+1) + " & "+ (nextIndex+1) );
					validation = false;
					break;
				}
			}
		}
	}
	return validation;
}



function busValidateOthers(){
	var validation = true;
	
	for (var index = 0; index < 5; index++)
	{
		var fileNameObject = document.getElementsByName("transaction.uploads["+index+"]");
		var fileName = fileNameObject[0].value;
		
		if(fileName != '' && validation)
		{
			for (var nextIndex = index+1;nextIndex < 5; nextIndex++)
			{
				
				var fileName1Object = document.getElementsByName("transaction.uploads["+nextIndex+"]");
				var fileName1 = fileName1Object[0].value;
				
				if(fileName == fileName1 && validation == true)
				{
					alert("File names Cannot Be Same for Document "+ (index+1) + " & "+ (nextIndex+1))
					validation = false;
					break;
				}
			 
			}
		}
	}
	return validation;
}



function otherDocsDefaultSelected()
{
	var maxRows = 5;
	for(var index = 0; index < maxRows; index++)
	{
		var cpView = document.getElementsByName("transaction.selectedCPView["+index+"]");
		
		if(cpView[0] != undefined)
		{
			cpView[0].checked = false;
			cpView[1].checked = true;
		}
	}
}

function AlertMessages()
{
	prepareTableHeader();
	//fileSizeError();
	uploadDocumentsMsg();
	deleteDocuments();
	
}

function AlertMessagesSearch()
{
	prepareTableHeader();
	deleteDocuments();
	
}


function fileSizeError()
{
	var uploadError = document.forms[0].uploadFileSizeError;
	if(uploadError != null && uploadError.value == "true")
	{
		document.forms[0].uploadFileSizeError.value = "false";
		alert("File Size should not be greater than 2 MB");
	}
}

function uploadDocumentsMsg()
{
	var upload = document.forms[0].uploadDocs;
	if (upload == null)
	{
		return;
	}
	if(upload != null && upload.value == "true")
	{
		alert("Document(s) saved successfully");
	}
	upload.value == "false";
}

function deleteDocuments(){
	var deleteDoc = document.forms[0].docsDeleted;
	if (deleteDoc == null)
	{
		return;
	}	
	if(deleteDoc != null && deleteDoc.value == "true")
	{
		alert("Selected document(s) deleted successfully");
	}
}

function checkCharacters(cmpnyName)
{
	var validName = false;
	for(var index=0;index<cmpnyName.length;index++)
	{
		var asc = asciiValue(cmpnyName.charAt(index));
		
		if((asc>=65 && asc<=90) || (asc>=97 && asc<=122))
		{
			validName = true;
			return validName;
		}
	}
	return validName;
}

function asciiValue(char)
{
	var chr = new Array();
	var chrs = '';

	for (var i=65;i<=122;i++) {
        if (unescape('%' + i.toString(16)) == char)
            return i;
    }
	return 0;
}


// This function checks the mouse event
function checkEvent(e) {
 if (!e) var e = window.event;
 if (e.target) targ = e.target;
 else if (e.srcElement) targ = e.srcElement;
 showHideToolTip(targ, e, e.type)
}
// This function shows/hides the tooltip
function showHideToolTip (theDropDown, e, eType)
{
 var toolTipObj = new Object();
 toolTipObj = document.getElementById("tooltip");
 var text = '';
  	
 var ttipText = theDropDown.options[theDropDown.selectedIndex].value;
  
 	var text = '';
	var pos1 = ttipText.indexOf('|');
	
	if(pos1 > 0)
	{
		ttipText = ttipText.substring(pos1+1,ttipText.length);
	
		var pos2 = ttipText.indexOf('|');
	
		if(pos2 > 0)
		{
			text = ttipText.substring(0,pos2);
		}
	}
	if(ttipText == 'default')
	{
		text = '- Select -';
	}
 
 toolTipObj.innerHTML = text;
 if(eType == "mouseout"){
  toolTipObj.style.display = "none";
   } else
 {
 
 var mcaObject = document.getElementsByName("transaction.selectedMCANames[0]");
  var posarray = findPos(mcaObject[0]);
  var leftpos = posarray[0];
  
  leftpos = leftpos + document.getElementById("selectedMCANames").clientWidth;

  toolTipObj.style.display = "inline";
  toolTipObj.style.top =   window.document.body.scrollTop + e.y + 5;
  toolTipObj.style.left = leftpos+2;//e.x + 10;
  
 }
}


function emptyDate(index){

	populateAllDate();
	
	var date = document.getElementsByName("transaction.executedDate["+index+"]");
	
	if(date[0].value == 'mm/dd/yyyy')
	{
		date[0].value = '';
	}
}

function populateAllDate(){

	for(var index = 0; index < 5; index++)
	{
		var date = document.getElementsByName("transaction.executedDate["+index+"]");
		if(trim(date[0].value) =='')
		{
			date[0].value = 'mm/dd/yyyy';
		}
	}
}

function findPos(obj) {
	var curleft = curtop = 0;
	if (obj.offsetParent) {
		curleft = obj.offsetLeft
		curtop = obj.offsetTop
		
		while (obj = obj.offsetParent) {
			curleft += obj.offsetLeft
			curtop += obj.offsetTop
		}
	}
	
	return [curleft,curtop];
}


function mcavalueset(mcaObj)
{
	var index = mcaObj.selectedIndex;
	index--;
	var ttipText123 = document.getElementsByName('mca['+index+'].tmpltIdDetails');
	
	mcaObj.options[mcaObj.selectedIndex].value = ttipText123[0].value;
}


function prepareTableHeader()
{
	if ( document.getElementById("manageDocTab") == null)
	{
		return;
	}
	var screen = document.getElementById("manageDocTab").value;
	
	
	var baseaction = "";
	if(screen == "PreExisting"){
		baseaction = "/mcx/action/manageDoc";
		columns = 6;
	}
	else if(screen == "Others")
	{
		baseaction = "/mcx/action/manageDoc";
		columns = 5;
	}
	else
	{
		baseaction = "/mcx/action/search";
		columns = 7;
	}
    var table = document.getElementById("DealerClientDetail");
    if(table !=null)
    {
    var header = table.getElementsByTagName("thead");
    var links = table.getElementsByTagName("a");
    var i=0;
    var trueVal = "true";
    var curLink = new Array();
    for (i=0; i < columns; i++) {
    	 curLink[i] = links[i];
		curLink[i].onclick = function() {
			
			var selected = document.getElementsByName("transaction.selectedDocuments");
			//var selected = document.getElementById('selectedDocuments')
			var len = selected.length;
			
			var isChecked = 'false';
			for(var i=0;i< len;i++)
			{
	    		if (selected[i].checked)
	    		{
	    			isChecked = 'true';
	    			break;
	    		}
			}
			
			if(isChecked == 'false')
			{
				selected = null;
			}
			
			var target = this.search;
			var pos = target.search('documentsDeleted');
			if(pos>0)
			{
				target = target.substring(0,(pos-1));
			}
			var pos = target.search('documentsUploaded');
			if(pos>0)
			{
				target = target.substring(0,(pos-1));
			}
			var pos = target.search('manageDocs');
			if(pos>0)
			{
				target = target.substring(0,(pos-1));
			}
			var pos = target.search('sorting');
			if(pos>0)
			{
				target = target.substring(0,(pos-1));
			}
			var toadd = target.substring(1,target.length);
			
			
			if (baseaction.indexOf("?") == -1)
			{
				document.forms[0].action = baseaction + "?" + toadd + "&sorting=" + trueVal;
			}
			else
			{
				document.forms[0].action = baseaction + "&" + toadd + "&sorting=" + trueVal;
			}
			
			document.forms[0].submit();
			return false;
		};
    }
    i=0;
    }
}



function searchLoad(){
	var selectedDealerClientHidden = document.getElementById("selectedDealerClientId").value;
	if(selectedDealerClientHidden == " "){
		document.getElementById("selectedDealerClient").value = "ALL";
	}
	else
	{
		document.getElementById("selectedDealerClient").value = selectedDealerClientHidden;
	}
	var docTypeHidden = document.getElementById("selectedDocumentTypeHidden").value;
	if(docTypeHidden == " "){
		document.getElementById("selectedDocumentTypeValue").value = "ALL";
	}
	else
	{
		document.getElementById("selectedDocumentTypeValue").value = docTypeHidden;	
	}
	
	document.getElementById("docNameTextBox").value = document.getElementById("docNameHidden").value;
}