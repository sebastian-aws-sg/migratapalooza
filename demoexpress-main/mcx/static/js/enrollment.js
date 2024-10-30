
//This method is used to check the user selected at least one dealer
function selectDealer(EnrollmentForm)
{
    var selectedDealers = EnrollmentForm.elements["transaction.selectedDealers"];
	var length = selectedDealers.length;
	var selected = false;
	if(length > 0 )
	{
		for(var index = 0; index < length; index++) 
	    {
	    	if(selectedDealers[index].checked == true)
		    {
		    	selected = true;
		    	break;
		    }
	    }
	    if(!selected)
	    {
	      	alert("Please select at least one Dealer");
	    	return;
	    }
    }
    else
    {
    	if(selectedDealers.checked == false)
	    {
	    	alert("Please select at least one Dealer");
	    	return;
	    }
    }
	EnrollmentForm.submit();
}
/*
    This method is used to check the user selected at least one MCa for each dealer & to get the confirmation on enroll
    
	RTM reference 3.3.3.22 : Client should select at least one MCA for initiation with each Dealer previously selected, else the system should throw an alert or an exception message
*/
function enrollDealer(EnrollmentForm)
{
	var mcaId = EnrollmentForm.elements["transaction.mcaIds"];
	var mcaIdLength = mcaId.length;
	if(mcaIdLength > 0 )
	{
		for(var index = 0; index < mcaIdLength; index++) 
	    {
	    	if(mcaId[index].value == "")
		    {
		    	alert("Please select at least one MCA for each Dealer");
		    	return;
		    }
	    }
    }
    else
    {
    	if(mcaId.value == "")
	    {
	    	alert("Please select at least one MCA for each Dealer");
	    	return;
	    }
    }
    var answer = confirm("Are you sure you want to enroll with the selected Dealer(s) & MCA(s)?")
	if (answer)
	{
		EnrollmentForm.submit();
	}
}

/*
		This method will show the selected cell as higlighed 
		
		RTM reference 3.3.3.3 : Actor chooses to select a non executed interdealer form from selection grid
		RTM reference 3.3.3.4 : Actor chooses to select a non executed common form from selection grid 
		RTM reference 3.3.3.5 : Actor chooses to select a non executed common form, non executed interdealer form from selection grid 
		RTM reference 3.3.3.12: Actor closes the selection grid shown to select MCAs even without selecting any of the MCAs 
		RTM reference 3.3.3.13: Actor closes the selection grid shown to select MCAs after selecting one or more MCAs 
		RTM reference 3.3.3.14: Actor chooses to deselect a MCA already chosen from selection grid 
*/
function selectGrid(selectedCell)
{
	if(selectedCell.childNodes(0).className == "innerTableCellWhite1"||selectedCell.childNodes(0).className == "innerTableCellWhite")
	{
		selectedCell.childNodes(0).className = "innerTableCellSelected"
		selectedCell.childNodes(0).childNodes(0).checked = true;
	}
	else
	{
		selectedCell.childNodes(0).className = "innerTableCellWhite1"
		selectedCell.childNodes(0).childNodes(0).checked = false;
	}

}
function selectGridMore(selectedCell)
{
	if(selectedCell.childNodes(0).className == "innerTableCellWhite1"||selectedCell.childNodes(0).className == "innerTableCellWhite")
	{
		selectedCell.childNodes(0).className = "innerTableCellSelected"
		selectedCell.childNodes(0).childNodes(0).checked = true;
	}
	else
	{
		selectedCell.childNodes(0).className = "innerTableCellWhite"
		selectedCell.childNodes(0).childNodes(0).checked = false;
	}

}
/*
		This method is used to collect the selected mcas and send it to the step two
		
		RTM reference 3.3.3.12: Actor closes the selection grid shown to select MCAs even without selecting any of the MCAs 
		RTM reference 3.3.3.13: Actor closes the selection grid shown to select MCAs after selecting one or more MCAs 
*/
function saveMCAChoices(selectMCAForm,dealerCode)
{
	var chBox = selectMCAForm.mcaList;
	if(chBox == null)
	{
		window.close();
		return;
	}
	
	var chkLength = chBox.length;
	
	var selectedMcaIds = "";
	var selectedMcaNames = "";
	var displayMcaNames = "";
	var MCA_NONE = "None"; 
	
	if(chkLength > 0 )
	{
		for(var index = 0; index < chkLength; index++) 
	    {
	      if(chBox[index].checked)
	      {
		      selectedMcaIds = selectedMcaIds + chBox[index].id + "~";
		      displayMcaNames = displayMcaNames + chBox[index].value + "\n";
		      selectedMcaNames = selectedMcaNames + chBox[index].value + "<br>";
	      }
	    }
	}
	else
	{
		  if(chBox.checked)
	      {
		      selectedMcaIds = selectedMcaIds + chBox.id + "~";
		      displayMcaNames = displayMcaNames + chBox.value;
		      selectedMcaNames = selectedMcaNames + chBox.value;
	      }
	
	}
	
	if(selectedMcaIds == "")
	{
    	alert("Please select at least one MCA");
    	return;
	}
     
    var mcaIdNode = window.opener.document.getElementById(dealerCode + "Id");
    var mcaNameNode = window.opener.document.getElementById(dealerCode + "Name");
    
     
    mcaNameNode.childNodes(0).value = displayMcaNames;
    mcaIdNode.childNodes(0).value = selectedMcaNames;
    if(selectedMcaNames == "")
    {
        mcaNameNode.childNodes(0).value = MCA_NONE;
    	mcaIdNode.childNodes(0).value = MCA_NONE;
    }
    mcaIdNode.childNodes(2).value = selectedMcaIds;
    
    window.close();
}

// This method will be used to show the selected MCAs as highlighted
function showSelectedMCAs(dealerCode)
{
	window.opener = window.dialogArguments;
	var selectedIDs = window.opener.document.getElementById(dealerCode + "Id").childNodes(2).value;
	var dealerName = window.opener.document.getElementById(dealerCode).value;
	if(selectedIDs != "")
	{
		var mcaIdArray = selectedIDs.split("~");
		var length = mcaIdArray.length - 1;
		for(var index = 0 ; index < length ; index++)
		{
			var mcaId = mcaIdArray[index];
			var chkBox = document.getElementById(mcaId);
			if(chkBox != null)
			{
				var selectedCell = chkBox.parentNode.parentNode;
				selectedCell.childNodes(0).className = "innerTableCellSelected"
				selectedCell.childNodes(0).childNodes(0).checked = true;
			}
		
		}
	}
	document.getElementById(dealerCode).innerHTML = document.getElementById(dealerCode).innerHTML + dealerName;
}

// This method will be used to direct to enrollment step 1 - dealer list page
function showDealerList()
{
	document.forms[0].action = "/mcx/action/getDealerInfo";
	document.forms[0].submit();
}


