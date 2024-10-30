// JavaScript Document
document.getElementById("footer").innerHTML="<table  align='center' border='0' cellpadding='0' cellspacing='0'><tr><td height='19' align='center' valign='middle' class='con6 padLR5'><a href='#'>&copy; 2007 The Depository Trust &amp; Clearing Corporation</a></td><td height='19' align='center' valign='middle' class='con7'>|</td> <td height='19' align='center' valign='middle' class='con7 padLR5'><a href='#' onclick='privacy()'>Privacy Policy</a></td><td height='19' align='center' valign='middle' class='con7'> | </td><td height='19' align='center' valign='middle' class='con7 padLR5'><a href='#' onclick='legal()'>Legal Information</a></td><td height='19' align='center' valign='middle' class='con7'> | </td><td height='19' align='center' valign='middle' class='con6 padLR5'><a href='#' onclick='dtcc()'>DerivSERV</a> </td></tr></table>"

function dtcc()
{
	window.open('http://www.dtcc.com/products/derivserv/ ')
}

function privacy()
{
	window.open('http://www.dtcc.com/privacy.php')
}

function legal()
{
	window.open('http://www.dtcc.com/legal.php')
}