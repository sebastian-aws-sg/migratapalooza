
function alertmytrim(stringToTrim) {
    if(stringToTrim==null) return "";
    else
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function alerttextCounter(field, maxlimit) {
if (field.value.length > maxlimit) // if too long...trim it!
{
  field.value = field.value.substring(0, maxlimit);
   alert("You may only entered " + maxlimit + " characters.");
 }
}

	function alertsend() {
		    var subval = alertmytrim(document.all.subjectval.value);
		    if(subval.length==0) 
		    {
		      alert("Subject is a mandatory field.");
		      return false;
		    }
		    var msgval = alertmytrim(document.all.textmessage.value);
		    if(msgval.length==0) 
		    {
		      alert("Message body is a mandatory field.");
		      return false;
		    }
  		var answer = confirm("Are you sure you want to send this alert?")
		if (answer){
		    document.getElementById("subject").value = subval;
		    document.getElementById("message").value=msgval;
	  		document.forms[0].submit();
	  		return true;
	  	}
	 	else
	 	{
	   		return false;
	 	}
	 
	}
	
	function alertcancel()
	{
	
		var answer = confirm("Are you sure you want to cancel?")
		if (answer){
		   document.all.postaction.value="cancel";
	  		document.forms[0].submit();
		   return true;
		}
		else
		{
			return false;
		}
		
	}
	
	function alertgetHtml(str)
	{
	  var thisstr = str.replace(/&amp;/g,'&').replace(/&lt;/g,'<').replace(/&gt;/g,'>');
	  document.getElementById("msgstr").innerHTML = thisstr;
	}
	
	function alertURLencode(sStr) {
    return escape(sStr)
       .replace(/\+/g, '%2B')
          .replace(/\"/g,'%22')
             .replace(/\'/g, '%27');
  }
  


