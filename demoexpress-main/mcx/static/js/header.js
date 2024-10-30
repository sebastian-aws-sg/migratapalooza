// JavaScript Document
document.getElementById("head").innerHTML="<table  cellpadding='0' width='1003'  cellspacing=' 0' ><tr><td width='744' rowspan='2'   class=' header'  id=' head' ><img src='../images/head2.gif'  style='cursor:pointer' alt=' Logo2'  onclick='home()' /></td><td valign='bottom' width='77' height='30'   class=' header sign'  id=' head' >Signed in as:</td><td valign='bottom' width='96'   class=' header con8'  id=' head' ><a href='#' onclick='user_popup()'>Username 1</a></td><td width='84' rowspan='2'   class=' header signout'  id=' head' ><a href=' #' onclick='sign_out()'>Sign out</a></td></tr><tr><td colspan='2'  align='center' class=' timestamp'  id=' head' >March 15 2007 - 11:52 AM ET</td></tr></table>"

function home()
{
	window.location.href="../sign_in.html";
}

function sign_out()
{
var answer = confirm("Are you sure you want to Sign out?")
	if (answer){
				window.location = "../sign_in.html";
	}
	else{
		window.location = "#";
	}	
}
function user_popup()
{
	window.open('user_popup.html','','top=50,left=50px,height=230,width=605,toolbar=no,resizable=no,status=no,menubar=no,location=no,scrollbars=no')
}