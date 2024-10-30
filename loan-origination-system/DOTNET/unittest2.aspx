<%@ Page Language="C#" AutoEventWireup="true" CodeFile="unittest2.aspx.cs" Inherits="unittest2" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>Untitled Page</title>
    <script type="text/javascript">
    //Handle Check Username Availability Using Ajax
 var http = createRequestObject();

 function createRequestObject() 
     {
           var xmlhttp;
	 try 
                 { 
                    xmlhttp=new ActiveXObject("Msxml2.XMLHTTP"); 
                 }
	  catch(e) 
                 {
	    try { xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");}
	    catch(f) { xmlhttp=null; }
	    }
	        if(!xmlhttp&&typeof XMLHttpRequest!="undefined") 
                        {
	  	   xmlhttp=new XMLHttpRequest();
	           }
		   return  xmlhttp;
 }

function sendRequestTextPost() 
  {
    var CharUname = /\W/;
    var rnd = Math.random();
    var loginname = escape(document.getElementById("loginname").value);
            try
              {
                 http.open('Get','CheckusernameAvailability.aspx?uname='+ loginname);
                 http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                 http.onreadystatechange = handleResponseText;
	             http.send(null);
	 }
	    catch(e){}
	    finally{}
  }

function handleResponseText() 
  {
     document.getElementById('prog').innerHTML="<br><img src='../images/loadingemail.gif'> Loading...";

             if((http.readyState == 4)&& (http.status == 200))
                 {
    	            var response = http.responseText;
                     document.getElementById("idforresults").innerHTML = response;
                     document.getElementById("prog").style.display='none';
	     }

}
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
    <input type="text" id="loginname" name="loginname" class="textbox" size="20" runat="server" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />&nbsp;<input type="button" id="subbutton" onClick="sendRequestTextPost()" />
    <span id="prog"></span>
    <br />
    <span id="idforresults"></span>
    </div>
    </form>
</body>
</html>
