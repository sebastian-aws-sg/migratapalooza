// JavaScript Document
pre_obj=null;
var ondropdown=null;
var onsidemenu=null;
var onsubmenu=null;

function tab_click(obj)
{
	if(pre_obj==null)
	{
		obj.onmouseover=null;
		obj.onmouseout=null;
		obj.className="tab_on";
	}
	else
	{
		obj.onmouseover=null;
		obj.onmouseout=null;
		pre_obj.className="tab_con";
		pre_obj.onmouseover=tab_mouseover;
		pre_obj.onmouseout=tab_mouseout;
		obj.className="tab_on";
		
	}
	pre_obj=obj;
}
function tab_mouseover(obj)
{
	this.className='tab_on';
}
function tab_mouseout(obj)
{
	this.className='tab_con';
}

function cancel()
{
	var answer = confirm("Are you sure you want to cancel this Enrollment process?")
	if (answer){
				window.location = "counter_enroll_step1.jsp";
	}
	else{
		window.location = "counter_enroll_step2.jsp";
	}
}
function enroll()
{
	var answer = confirm("Are you sure you want to enroll with the selected Dealer(s) & MCA(s)?")
	if (answer){
				window.location = "counter_enroll_confirm.jsp";
	}
	else{
		window.location = "counter_enroll_step2.jsp";
	}

}

function cancel_comment()
{
	var answer = confirm("Are you sure you want to cancel?")
	if (answer){
				window.close();
	}
	else{
		window.close();
	}
}
function save_choice()
{
	confirm("Are you sure you want to save choices?")
}
function save_change()
{
	var answer = confirm("Are you sure you want to save the changes?")
	if (answer){
				window.open('../mca_wizard/save_template.html','','top=50,left=50px,height=100,width=450,toolbar=no,resizable=no,status=no,memubar=no,location=no,scrollbars=no');
	}
	else{
		window.close();
	}
}
function send_back()
{
	confirm("Are you sure you want to send back to dealer?")
}
function agree()
{
	confirm("Are you sure you agree with this MCA?")
}

function unlock()
{
	var answer = confirm("Are you sure you would like to unlock the template?")
	if (answer){
				save_change();
	           }
	else{
		window.close();
	}
}

var flag1=true;

function showhide1(imgobj)
{
	if(flag1==true)
	{
		imgobj.src="/mcx/static/images/tree/ftv2mlastnode.gif";
		document.getElementById("subnodes1").style.display="block";
		document.getElementById("subnodes2").style.display="block";
		document.getElementById("subnodes3").style.display="block";
		document.getElementById("subnodes4").style.display="block";
	}
	else
	{
		imgobj.src="/mcx/static/images/tree/ftv2plastnode_new.gif";
		document.getElementById("subnodes1").style.display="none";
		document.getElementById("subnodes2").style.display="none";
		document.getElementById("subnodes3").style.display="none";
		document.getElementById("subnodes4").style.display="none";
	}
	flag1=!flag1;
}

var flag2=true;
function showhide2(imgobj)
{
	if(flag2==true)
	{
		imgobj.src="/mcx/static/images/tree/ftv2mlastnode.gif";
		document.getElementById("subnodes5").style.display="block";
		document.getElementById("subnodes6").style.display="block";
		document.getElementById("subnodes7").style.display="block";
		document.getElementById("subnodes8").style.display="block";
	}
	else
	{
		imgobj.src="/mcx/static/images/tree/ftv2plastnode_new.gif";
		document.getElementById("subnodes5").style.display="none";
		document.getElementById("subnodes6").style.display="none";
		document.getElementById("subnodes7").style.display="none";
		document.getElementById("subnodes8").style.display="none";
	}
	flag2=!flag2;
}

var flag2a=true;
function showhide2_pending(imgobj)
{
	if(flag2a==true)
	{
		imgobj.src="/mcx/static/images/tree/ftv2mlastnode.gif";
		document.getElementById("subnodes2").style.display="block";
		document.getElementById("subnodes3").style.display="block";
		document.getElementById("subnodes4").style.display="block";
		document.getElementById("subnodes5").style.display="block";
		document.getElementById("subnodes6").style.display="block";
	}
	else 
	{
		imgobj.src="/mcx/static/images/tree/ftv2plastnode_new.gif";
		document.getElementById("subnodes2").style.display="none";
		document.getElementById("subnodes3").style.display="none";
		document.getElementById("subnodes4").style.display="none";
		document.getElementById("subnodes5").style.display="none";
		document.getElementById("subnodes6").style.display="none";
	}
	flag2a=!flag2a;
} 
var flag3a=true;
function showhide3_pending(imgobj)
{
	if(flag3a==true)
	{
		imgobj.src="/mcx/static/images/tree/ftv2mlastnode.gif";
		document.getElementById("subnodes7").style.display="block";
		document.getElementById("subnodes8").style.display="block";
		document.getElementById("subnodes9").style.display="block";
		document.getElementById("subnodes10").style.display="block";
		document.getElementById("subnodes11").style.display="block";
	}
	else 
	{
		imgobj.src="/mcx/static/images/tree/ftv2plastnode_new.gif";
		document.getElementById("subnodes7").style.display="none";
		document.getElementById("subnodes8").style.display="none";
		document.getElementById("subnodes9").style.display="none";
		document.getElementById("subnodes10").style.display="none";
		document.getElementById("subnodes11").style.display="none";
	}
	flag3a=!flag3a;
} 
var flag4=true;
function showhide2_dealer_pending(imgobj)
{
	if(flag4==true)
	{
		imgobj.src="/mcx/static/images/tree/ftv2mlastnode.gif";
		document.getElementById("subnodes2").style.display="block";
		document.getElementById("subnodes3").style.display="block";
		document.getElementById("subnodes4").style.display="block";
		document.getElementById("subnodes5").style.display="block";
		}
	else 
	{
		imgobj.src="/mcx/static/images/tree/ftv2plastnode_new.gif";
		document.getElementById("subnodes2").style.display="none";
		document.getElementById("subnodes3").style.display="none";
		document.getElementById("subnodes4").style.display="none";
		document.getElementById("subnodes5").style.display="none";
		
	}
	flag4=!flag4;
} 
var flag5=true;
function showhide3_dealer_pending(imgobj)
{
	if(flag5==true)
	{
		imgobj.src="/mcx/static/images/tree/ftv2mlastnode.gif";
		document.getElementById("subnodes7").style.display="block";
		document.getElementById("subnodes8").style.display="block";
		document.getElementById("subnodes9").style.display="block";
		document.getElementById("subnodes10").style.display="block";
		
	}
	else 
	{
		imgobj.src="/mcx/static/images/tree/ftv2plastnode_new.gif";
		document.getElementById("subnodes7").style.display="none";
		document.getElementById("subnodes8").style.display="none";
		document.getElementById("subnodes9").style.display="none";
		document.getElementById("subnodes10").style.display="none";
		
	}
	flag5=!flag5;
} 
function printit()
{
	window.print();
}

function setdropdownmenu()
{
	ondropdown="true";
}

function offdropdownmenu()
{

	ondropdown=null;
}

function mca_mouseover(isDealer, tmpltPrsnt)
{
		var curleft = curtop = 0;
	obj=document.getElementById("tab2");
	
	objParent = obj.offsetParent;
	curleft = obj.offsetLeft;
	curtop=objParent.offsetTop+objParent.offsetHeight;
	
	document.getElementById("tab2").className="tab_mouseover";
	document.getElementById("menu").style.top=curtop;
	document.getElementById("menu").style.left=curleft;
	document.getElementById("menu").style.display="block";
	document.getElementById("menu").style.height="auto";
	if(isDealer)
	{
		if(tmpltPrsnt == true)
		{
			document.getElementById("menu").innerHTML="<table width='105%'  border='0' cellspacing='0' cellpadding='2' onmouseover='setdropdownmenu()' onmouseout='offdropdownmenu()'><tr onmouseover='side_menu_hide()' onclick=javascript:window.location.href='/mcx/action/ViewMCA?frmScr=ISDA&sltInd=ME&viewInd=F&catgyId='><td><a href='#' class='menu'>Industry Published </a></td></tr><tr onmouseover='showsidemenu()' onmouseout='hidesidemenu()'  ><td ><a href='#' id='sidetab2' class='menu_bot'>CP/Dealer<img border='0' style='margin-left:50px' src='/mcx/static/images/orange_arrow_side.gif' alt='arrow for drop down' /></a></td></tr></table>";	
		}
		else
		{
			document.getElementById("menu").innerHTML="<table width='105%'  border='0' cellspacing='0' cellpadding='1' onmouseover='setdropdownmenu()' onmouseout='offdropdownmenu()'><tr onmouseover='side_menu_hide()' onclick=javascript:window.location.href='/mcx/action/ViewMCA?frmScr=ISDA&sltInd=ME&viewInd=F&catgyId='><td><a href='#' class='menu'>Industry Published </a></td></tr></table>";
		}	
	}
	else
	{
		if(tmpltPrsnt == true)
		{
			document.getElementById("menu").innerHTML="<table width='120%'  border='0' cellspacing='0' cellpadding='2' onmouseover='setdropdownmenu()' onmouseout='offdropdownmenu()'><tr onmouseover='side_menu_hide()' onclick=javascript:window.location.href='/mcx/action/ViewMCA?frmScr=ISDA&sltInd=ME&viewInd=F&catgyId='><td><a href='#' class='menu'>Industry Published </a></td></tr><tr onmouseover='showsidemenu()' onmouseout='hidesidemenu()' ><td ><a href='#' id='sidetab2' class='menu_bot'>Dealer Specific <img border='0' class='padL20a' align='absmiddle' src='/mcx/static/images/orange_arrow_side.gif' alt='arrow for drop down' /></a></td></tr></table>";
		}
		else
		{
			document.getElementById("menu").innerHTML="<table width='120%'  border='0' cellspacing='0' cellpadding='1' onmouseover='setdropdownmenu()' onmouseout='offdropdownmenu()'><tr onmouseover='side_menu_hide()' onclick=javascript:window.location.href='/mcx/action/ViewMCA?frmScr=ISDA&sltInd=ME&viewInd=F&catgyId='><td><a href='#' class='menu'>Industry Published </a></td></tr></table>";
		}
	}
	
	
	document.getElementById("menuframe").style.top=curtop;
	document.getElementById("menuframe").style.left=curleft;
	document.getElementById('menuframe').style.display="block";
	document.getElementById("menuframe").style.width = document.getElementById("menu").clientWidth;
	document.getElementById("menuframe").style.height = document.getElementById("menu").clientHeight;
	
	
}


function mca_mouseout()
{
	document.getElementById("tab2").className="tab_con";
	document.getElementById("menu").style.display="none";
	document.getElementById('menuframe').style.display="none";
}

function showsidemenu()
{	
	var curleft1 = curtop1 = 0;
		obj1=document.getElementById("sidetab2")
		while (obj1 = obj1.offsetParent) 
		{
			curleft1 += obj1.offsetLeft
			curtop1 += obj1.offsetTop;
			curtop1=curtop1;
			curleft1=curleft1+29;
		}
	document.getElementById("sidemenu").style.top=curtop1;
	document.getElementById("sidemenu").style.left=curleft1;
	document.getElementById("sidemenu").style.display="block";
	
	document.getElementById("sidemenuframe").style.top=curtop1;
	document.getElementById("sidemenuframe").style.left=curleft1-2;
	document.getElementById("sidemenuframe").style.display="block";
	document.getElementById("sidemenuframe").style.width = document.getElementById("sidemenu").clientWidth+5;
	document.getElementById("sidemenuframe").style.height = document.getElementById("sidemenu").clientHeight+2;
}




function showsubmenu1(partyName, num)
{	
	var templates = partyName;
	var len = templates.length;
	var subMenu = "<table border='0' cellspacing='0' onmouseover='sub_menu1()' onmouseout='hidesubmenu1()'><tr onclick=javascript:window.location.href='#'><td><a href='#' class='menu_side_bot_col2'>";
	if(len > 0)
	{
		for(var i=0; i < templates.length; i++)
		{
			var tmpltId = templates[i].id;
			subMenu = subMenu + "<tr onclick=javascript:window.location.href='#'><td><a href='/mcx/action/ViewMCA?tmpltId="+tmpltId +"&frmScr=NEG&sltInd=TE&viewInd=F&catgyId= ' class='menu_side_bot_col2'>";
			subMenu = subMenu + templates[i].value;
			subMenu = subMenu + "</a></td></tr>";
		}
	}
	else
	{
		var tmpltId = templates.id;
		subMenu = subMenu + "<tr onclick=javascript:window.location.href='#'><td><a href='/mcx/action/ViewMCA?tmpltId="+tmpltId +"&frmScr=NEG&sltInd=TE&viewInd=F&catgyId= ' class='menu_side_bot_col2'>";
		subMenu = subMenu + templates.value;
		subMenu = subMenu + "</a></td></tr>";
	}
	subMenu = subMenu + "</table>";
	var curleft2 = curtop2 = 0;
	var submenuid = "submenu" + num;
		obj2=document.getElementById(submenuid);
		do
		{
			curleft2 += obj2.offsetLeft;
			curtop2 += obj2.offsetTop;
			curtop2=curtop2;
			curleft2=sidemenu.offsetLeft+sidemenu.clientWidth+1;
		}while (obj2 = obj2.offsetParent) 
	document.getElementById("sub_menu").style.top=curtop2;
	document.getElementById("sub_menu").style.left=curleft2;
	document.getElementById("sub_menu").style.display="block";
	document.getElementById("sub_menu").innerHTML=subMenu;
	document.getElementById("sub_menu2").style.display="none";
	
	document.getElementById("submenuframe").style.top=curtop2-1;
	document.getElementById("submenuframe").style.left=curleft2;
	document.getElementById("submenuframe").style.display="block";
	document.getElementById("submenuframe").style.width = document.getElementById("sub_menu").clientWidth+2;
	document.getElementById("submenuframe").style.height = document.getElementById("sub_menu").clientHeight+3;
	
}


function hidesidemenu()
{	
	    onsidemenu=null;
		if(onsubmenu==null)
		{
			document.getElementById("sidemenu").style.display="none";
			document.getElementById("sidemenuframe").style.display="none";
			document.getElementById("menu").style.display="none";
			document.getElementById('menuframe').style.display="none";
			document.getElementById("tab2").className="tab_con";
		}
}

function side_menu()
{
onsidemenu="true";
document.getElementById("menu").style.display="block";
document.getElementById('menuframe').style.display="block";
document.getElementById("sidemenu").style.display="block";
document.getElementById("sidemenuframe").style.display="block";
document.getElementById("tab2").className="tab_on";
}

function side_menu_hide()
{
document.getElementById("sidemenu").style.display="none";
document.getElementById("sidemenuframe").style.display="none";
document.getElementById("sub_menu").style.display="none";
document.getElementById("submenuframe").style.display="none";
document.getElementById("sub_menu2").style.display="none";
}


function hidesubmenu1()
{

   onsubmenu=null;   
   document.getElementById("sub_menu").style.display="none";
   document.getElementById("submenuframe").style.display="none";
   
   if(onsidemenu==null && ondropdown==null)
   {
   
		document.getElementById("sidemenu").style.display="none";
		document.getElementById("sidemenuframe").style.display="none";
		document.getElementById("menu").style.display="none";
		document.getElementById('menuframe').style.display="none";
		document.getElementById("tab2").className="tab_con";
	}
}

function sub_menu1()
{
onsubmenu="true";
document.getElementById("menu").style.display="block";
document.getElementById('menuframe').style.display="block";
document.getElementById("sidemenu").style.display="block";
document.getElementById("sidemenuframe").style.display="block";
document.getElementById("sub_menu").style.display="block";
document.getElementById("submenuframe").style.display="block";
document.getElementById("tab2").className="tab_on";
}

function mp_mouseover()
{
document.getElementById("menu").style.display="block";

if(document.getElementById("tab2")!=null)
{
  document.getElementById("tab2").className="tab_on";
}

}
function mp_mouseout()
{
document.getElementById("menu").style.display="none";
if(document.getElementById("tab2")!=null)
{
  document.getElementById("tab2").className="tab_con";
}
}
function mp_mouseover4()
{
document.getElementById("menu").style.display="block";
document.getElementById("tab2").className="tab_on";
}
function mp_mouseout4()
{
document.getElementById("menu").style.display="none";
document.getElementById("tab2").className="tab_con";
}
function mp_mouseover4a()
{
document.getElementById("menu").style.display="block";
document.getElementById("tab2").className="tab_on";
}
function mp_mouseout4a()
{
document.getElementById("menu").style.display="none";
document.getElementById("tab2").className="tab_con";
}

function mp_mouseover3_dealer()
{
document.getElementById("menu").style.display="block";
document.getElementById("tab2").className="tab_on";
}
function mp_mouseout3_dealer()
{
document.getElementById("menu").style.display="none";

}

function mp_mouseover_dealer()
{
document.getElementById("menu").style.display="block";
document.getElementById("tab2").className="tab_on";
}
function mp_mouseout_dealer()
{
document.getElementById("menu").style.display="none";
document.getElementById("tab2").className="tab_con";
}

function mp_mouseover4_dealer()
{
document.getElementById("menu").style.display="block";
document.getElementById("tab2").className="tab_on";
}
function mp_mouseout4_dealer()
{
document.getElementById("menu").style.display="none";
document.getElementById("tab2").className="tab_con";
}


var count=0;
function form_image(obj,imgfile,imgbackfile)
{
count=count+1
var r=count % 2;
if(count % 2 != 0)
{
imgfile="../images/"+imgfile;
	obj.src=imgfile;
}
else
{
	imgbackfile="../images/"+imgbackfile;
	obj.src=imgbackfile;
	
}
}

var count=0;
function showhide_dealertree()
{
count=count+1
var r=count % 2;
if(count % 2 != 0)
{
	document.getElementById("dealer_tree").style.display="block";
	document.getElementById("side_arrow").src="../images/white_down_arrow.gif";
}
else
{
	document.getElementById("dealer_tree").style.display="none";
	document.getElementById("down_arrow").src="../images/white_side_arrow.gif";
}
}
var f1=true;
function showhide3(imgobj,num)
{
	nodeobja="subnodes"+num+"a";
	nodeobjb="subnodes"+num+"b";
	nodeobjc="subnodes"+num+"c";
	if(f1==true)
	{
		imgobj.src="/mcx/static/images/tree/ftv2mlastnode.gif";
		document.getElementById(nodeobja).style.display="block";
		document.getElementById(nodeobjb).style.display="block";
		document.getElementById(nodeobjc).style.display="block";
	}
	else
	{
		imgobj.src="/mcx/static/images/tree/ftv2plastnode_new.gif";
		document.getElementById(nodeobja).style.display="none";
		document.getElementById(nodeobjb).style.display="none";
		document.getElementById(nodeobjc).style.display="none";
	}
	f1=!f1;
} 

function attach()
{
document.getElementById('attach').innerHTML="<input name='att' type='file' />";
}

function close_win()
{
	window.close();
}

function sign_in()
{
   if (document.getElementById("txt_name").value=='dealer')
     {
		 window.location = "home/dealer_pending_enroll_approval.jsp"
	 }
   else if(document.getElementById("txt_name").value=='admin')
     {
		 window.location = "admin/Admin_Participant.jsp"
	 }
   else if (document.getElementById("txt_name").value!='dealer')
     {
		 window.location = "/mcx/action/homepage"
	 }
 
}

function admin_part()
{
	if (document.getElementById("admin_part").value=="")
     {
		 window.location = "../admin/mca_setup.html"
	 }
   else if (document.getElementById("admin_part").value!="")
     {
		 window.location = "../home/cp_pending_mca.html"
	 }
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

function displayMenu() 
{
   whichDiv=event.srcElement;
   menu1.style.leftPos+=10;
   /*menu1.style.posLeft=scrOfX;
   menu1.style.posTop=scrOfY;*/
    menu1.style.posLeft=event.clientX+10+scrOfX;
   menu1.style.posTop=event.clientY+10+scrOfY;
   menu1.style.display="";
   menu1.setCapture();
}
function switchMenu() {   
   el=event.srcElement;
   if (el.className=="menuItem") {
      el.className="highlightItem";
   } else if (el.className=="highlightItem") {
      el.className="menuItem";
   }
}

function clickMenu() 
{
   menu1.releaseCapture();
   menu1.style.display="none";
   el=event.srcElement;
    if (el.id=="t1") 
	{
      window.open('../home/dealer_cp_comment_history.jsp','','top=50,left=50px,height=615,width=627,toolbar=no,resizable=no,status=no,memubar=no,location=no,scrollbars=no')
   } 
   else if (el.id=="t2")
   {
     window.open('../home/view_industry_pub.jsp','','top=50,left=50px,height=360,width=625,toolbar=no,resizable=no,status=no,memubar=no,location=no,scrollbars=no')    
   } 
   
}

function click_body()
{
menu1.style.display="none";	
}

function displayMenu2() {
   whichDiv=event.srcElement;
   menu2.style.leftPos+=10;
   menu2.style.posLeft=event.clientX+10+scrOfX;
   menu2.style.posTop=event.clientY+10+scrOfY;
   menu2.style.display="";
   menu2.setCapture();
}
function switchMenu2() {   
   el=event.srcElement;
   if (el.className=="menuItem") {
      el.className="highlightItem";
   } else if (el.className=="highlightItem") {
      el.className="menuItem";
   }
}

function clickMenu2() 
{
   menu2.releaseCapture();
   menu2.style.display="none";
   el=event.srcElement;
    if (el.id=="t11") 
	{
      window.open('../home/dealer_cp_comment_history.jsp','','top=50,left=50px,height=615,width=627,toolbar=no,resizable=no,status=no,memubar=no,location=no,scrollbars=no')
   } 
   else if (el.id=="t12")
   {
    document.getElementById("terms_red1").className="green border5_blue";
	document.getElementById("terms_red2").className="green border5_blue";
   } 
   else if (el.id=="t13")
   {
      window.open('../home/view_industry_pub.jsp','','top=50,left=50px,height=360,width=625,toolbar=no,resizable=no,status=no,memubar=no,location=no,scrollbars=no') 
   } 
}

function displayMenu3() {
   whichDiv=event.srcElement;
   menu3.style.leftPos+=10;
   menu3.style.posLeft=event.clientX+10+scrOfX;
   menu3.style.posTop=event.clientY+10+scrOfY;
   menu3.style.display="";
   menu3.setCapture();
}
function switchMenu3() {   
   el=event.srcElement;
   if (el.className=="menuItem") {
      el.className="highlightItem";
   } else if (el.className=="highlightItem") {
      el.className="menuItem";
   }
}

function clickMenu3() 
{
   menu3.releaseCapture();
   menu3.style.display="none";
   el=event.srcElement;
    if (el.id=="t21") 
	{
      window.open('../home/dealer_cp_comment_history.jsp','','top=50,left=50px,height=615,width=627,toolbar=no,resizable=no,status=no,memubar=no,location=no,scrollbars=no')
   } 
   else if (el.id=="t22")
   {
    window.open('../home/dealer_post_amendment.jsp','','top=50,left=50px,height=360,width=625,toolbar=no,resizable=no,status=no,memubar=no,location=no,scrollbars=no')  
   } 
   else if (el.id=="t23")
   {
      window.open('../home/view_industry_pub.jsp','','top=50,left=50px,height=360,width=625,toolbar=no,resizable=no,status=no,memubar=no,location=no,scrollbars=no') 
   } 
}

function click_body_dealer()
{
menu3.style.display="none";	
}


function displayMenu4() 
{
   whichDiv=event.srcElement;
   menu4.style.leftPos+=10;
   menu4.style.posLeft=event.clientX+10+scrOfX;
   menu4.style.posTop=event.clientY+10+scrOfY;
   menu4.style.display="";
   menu4.setCapture();
}
function switchMenu4() {   
   el=event.srcElement;
   if (el.className=="menuItem") {
      el.className="highlightItem";
   } else if (el.className=="highlightItem") {
      el.className="menuItem";
   }
}

function clickMenu4() 
{
   menu4.releaseCapture();
   menu4.style.display="none";
   el=event.srcElement;
    if (el.id=="t1") 
	{
      window.open('../home/dealer_cp_comment_history.jsp','','top=50,left=50px,height=615,width=627,toolbar=no,resizable=no,status=no,memubar=no,location=no,scrollbars=no')
   } 
   else 
   {
     return;   
   } 
   
}
function click_body4()
{
menu4.style.display="none";	
}

function displayMenu5() 
{
   whichDiv=event.srcElement;
   menu5.style.leftPos+=10;
   menu5.style.posLeft=event.clientX+10+scrOfX;
   menu5.style.posTop=event.clientY+10+scrOfY;
   menu5.style.display="";
   menu5.setCapture();
}
function switchMenu5() {   
   el=event.srcElement;
   if (el.className=="menuItem") {
      el.className="highlightItem";
   } else if (el.className=="highlightItem") {
      el.className="menuItem";
   }
}

function clickMenu5() 
{
   menu5.releaseCapture();
   menu5.style.display="none";
   el=event.srcElement;
    if (el.id=="t1") 
	{
      window.location('#');
   } 
   else 
   {
     return;   
   } 
   
}
function click_body5()
{
menu5.style.display="none";	
}

function apply_temp()
{
window.opener.location.href="mca_wizard_step2.jsp"
window.close();
}
function back_setup()
{
window.opener.location.href="mca_setup.jsp"
window.close();
}
function submit_counter()
{
	var answer = confirm("Please ensure any required internal approval(s) were received and click Ok to Submit")
	if (answer){
				confirm_sub();
				
	}
	else{
		return;
	}
}
function confirm_sub()
{
	alert("The selected MCA has been submitted to the counterparty.");
	window.location="mca_sub_confirm.html";
}
function save_pending()
{
	var answer = confirm("Are you sure you want to save this MCA Setup?")
	if (answer){
				window.location = "mca_setup.jsp";
	}
	else{
		window.location = "mca_setup.jsp";
	}
}
function cancel_pending()
{
	var answer = confirm("Are you sure you want to cancel this MCA Setup?")
	if (answer){
				window.location = "mca_setup.jsp";
	}
	else{
		window.location = "mca_setup.jsp";
	}
}
function approve_pending()
{
	var answer = confirm("Are you sure you want to approve this MCA Setup?")
	if (answer){
				window.location = "mca_setup.jsp";
	}
	else{
		window.location = "mca_setup.jsp";
	}
}
function reject_pending()
{
	var answer = confirm("Are you sure you want to reject this MCA Setup?")
	if (answer){
				window.location = "mca_setup.jsp";
	}
	else{
		window.location = "mca_setup.jsp";
	}
}

function comp_view()
{
	document.getElementById("comp").style.display="block";
	document.getElementById("amend").style.display="none";
	document.getElementById("comm").style.display="none";
}
function amend_view()
{
	document.getElementById("comp").style.display="none";
	document.getElementById("amend").style.display="block";
	document.getElementById("comm").style.display="none";
}
function comm_view()
{
	document.getElementById("comp").style.display="none";
	document.getElementById("amend").style.display="none";
	document.getElementById("comm").style.display="block";
}

function getposition(eid,edropid)
{
		var curleft = curtop = 0;
		obj=document.getElementById(eid);
		do
		{
			curleft += obj.offsetLeft
			curtop += obj.offsetTop;
			curtop=curtop+4.1;
			curleft=curleft+1;
		}while (obj = obj.offsetParent) 
	document.getElementById(edropid).style.top=curtop;
	document.getElementById(edropid).style.left=curleft;
}

function home_mouseover()
{	
	var curleft = curtop = 0;
	obj=document.getElementById("tab");
	objParent = obj.offsetParent;
	curleft = obj.offsetLeft;
	curtop=objParent.offsetTop+objParent.offsetHeight;
	document.getElementById("menu").style.top=curtop;
	document.getElementById("menu").style.left=curleft;
	document.getElementById("tab").className="tab_mouseover";
	document.getElementById("menu").style.display="block";
	document.getElementById("menu").innerHTML="<table width='100%'  border='0' cellspacing='0' cellpadding='2' ><tr onclick=javascript:window.location.href='/mcx/action/dealerPendingApproval?selectedTab=P&printParam=N'><td style='white-space: nowrap;'><a href='#' class='menu'>Pending Enrollment Approval</a></td></tr><tr onclick=javascript:window.location.href='/mcx/action/dealerPendingApproval?selectedTab=A&printParam=N'><td ><a href='#' class='menu'>Approved Firms</a></td></tr><tr onclick=javascript:window.location.href='/mcx/action/getDealerDetails?mcaStatus=P&printParam=N'><td ><a href='#' class='menu'>Pending MCAs</a></td></tr><tr onclick=javascript:window.location.href='/mcx/action/getDealerDetails?mcaStatus=E&printParam=N'><td ><a href='#' class='menu_bot'>Executed MCAs</a></td></tr></table>";
	
	
	document.getElementById("menuframe").style.top=curtop;
	document.getElementById("menuframe").style.left=curleft;
	document.getElementById('menuframe').style.display="block";
	document.getElementById("menuframe").style.width = document.getElementById("menu").clientWidth+2;
	document.getElementById("menuframe").style.height = document.getElementById("menu").clientHeight+2;
	
}

function home_mouseout()
{
	document.getElementById("tab").className="tab_on";
	document.getElementById("home_menu").style.display="none";
}

function home_menu_mouseover()
{
document.getElementById("home_menu").style.display="block";
document.getElementById("tab").className="tab_on";
}
function home_menu_mouseout()
{
document.getElementById("home_menu").style.display="none";
document.getElementById("tab").className="tab_on";
}


function home2_mouseover()
{
		var curleft = curtop = 0;
		obj=document.getElementById("tab")
		while (obj = obj.offsetParent) 
		{
			curleft += obj.offsetLeft
			curtop += obj.offsetTop;
			curtop=curtop+5.9;
			curleft=curleft+1;
		}
	document.getElementById("tab").className="tab_mouseover";
	document.getElementById("menu").style.top=curtop;
	document.getElementById("menu").style.left=curleft;
	document.getElementById("menu").style.display="block";
	document.getElementById("menu").innerHTML="<table width='100%'  border='0' cellspacing='0' cellpadding='2' ><tr onclick=javascript:window.location.href='../home/cp_pending_mca.html'><td ><a href='#' class='menu'>Pending MCAs</a></td></tr><tr onclick=javascript:window.location.href='../home/cp_executed_mca.html'><td ><a href='#' class='menu_bot'>Executed MCAs</a></td></tr></table>";
	
	document.getElementById("menuframe").style.top=curtop;
	document.getElementById("menuframe").style.left=curleft;
	document.getElementById('menuframe').style.display="block";
	document.getElementById("menuframe").style.width = document.getElementById("menu").clientWidth;
	document.getElementById("menuframe").style.height = document.getElementById("menu").clientHeight;
	
}

function home2_mouseout()
{
	document.getElementById("tab").className="tab_con";
	document.getElementById("home2_menu").style.display="none";
}

function home2_menu_mouseover()
{
document.getElementById("home2_menu").style.display="block";
document.getElementById("tab").className="tab_on";
}
function home2_menu_mouseout()
{
document.getElementById("home2_menu").style.display="none";
document.getElementById("tab").className="tab_con";
}

function home2a_mouseover()
{
	var curleft = curtop = 0;
	obj=document.getElementById("tab");
	objParent = obj.offsetParent;
	curleft = obj.offsetLeft;
	curtop = objParent.offsetTop+objParent.offsetHeight;
	document.getElementById("menu").style.top=curtop;
	document.getElementById("menu").style.left=curleft;
	document.getElementById("tab").className="tab_mouseover";
	document.getElementById("menu").style.display="block";
	document.getElementById("menu").innerHTML="<table width='120%'  border='0' cellspacing='0' cellpadding='2' ><tr onclick=javascript:window.location.href='/mcx/action/getDealerDetails?mcaStatus=P&printParam=N'><td ><a href='#' class='menu'>Pending MCAs</a></td></tr><tr onclick=javascript:window.location.href='/mcx/action/getDealerDetails?mcaStatus=E&printParam=N'><td ><a href='#' class='menu_bot'>Executed MCAs</a></td></tr></table>";
	
	document.getElementById("menuframe").style.top=curtop;
	document.getElementById("menuframe").style.left=curleft;
	document.getElementById('menuframe').style.display="block";
	document.getElementById("menuframe").style.width = document.getElementById("menu").clientWidth;
	document.getElementById("menuframe").style.height = document.getElementById("menu").clientHeight;
	
}


function home2a_mouseout()
{
	document.getElementById("tab").className="tab_con";
	document.getElementById("home2_menu").style.display="none";
}


function home2a_mngdoc_mouseover(isDealer,leftpos)
{	
	var curleft = curtop = 0;
		obj=document.getElementById("tab3")		
	objParent = obj.offsetParent;
	curleft = obj.offsetLeft;
	curtop = objParent.offsetTop+objParent.offsetHeight;
	document.getElementById("menu").style.top=curtop;
	document.getElementById("menu").style.left=curleft;
	document.getElementById("tab3").className="tab_mouseover";
	document.getElementById("menu").style.display="block";
	if(isDealer){
	document.getElementById("menu").innerHTML="<table width='156%'  border='0' cellspacing='0' cellpadding='2' ><tr onclick=javascript:window.location.href='/mcx/action/manageDoc?manageDocTab=P'><td ><a href='#' class='menu'>Pre-existing MCAs</a></td></tr><tr onclick=javascript:window.location.href='/mcx/action/manageDoc?manageDocTab=O'><td ><a href='#' class='menu'>Other Documents</a></td></tr><tr onclick=javascript:window.location.href='/mcx/action/searchDealerNames'><td ><a href='#' class='menu_bot'>Search Documents</a></td></tr></table>";
	}else{
	document.getElementById("menu").innerHTML="<table width='178%'  border='0' cellspacing='0' cellpadding='2' ><tr onclick=javascript:window.location.href='/mcx/action/manageDoc?manageDocTab=P'><td ><a href='#' class='menu'>Pre-existing MCAs</a></td></tr><tr onclick=javascript:window.location.href='/mcx/action/manageDoc?manageDocTab=O'><td ><a href='#' class='menu'>Other Documents</a></td></tr><tr onclick=javascript:window.location.href='/mcx/action/searchDealerNames'><td ><a href='#' class='menu_bot'>Search Documents</a></td></tr></table>";
	}
	
	document.getElementById("menuframe").style.top=curtop;
	document.getElementById("menuframe").style.left=curleft;
	document.getElementById('menuframe').style.display="block";
	document.getElementById("menuframe").style.width = document.getElementById("menu").clientWidth;
	document.getElementById("menuframe").style.height = document.getElementById("menu").clientHeight;
	
}

function home2a_mngdoc_mouseout()
{
	document.getElementById("tab3").className="tab_con";
	document.getElementById("home2_menu").style.display="none";
}

function home2a_menu_mouseover()
{
document.getElementById("home2_menu").style.display="block";
document.getElementById("tab").className="tab_on";
}
function home2a_menu_mouseout()
{
document.getElementById("home2_menu").style.display="none";
document.getElementById("tab").className="tab_on";
}

function add_cp(obj)
{
	if(obj.value=="AddCounterparty")
	{
		 window.open('add_cp_pop.html','','top=50,left=50px,height=120,width=403,toolbar=no,resizable=no,status=no,memubar=no,location=no,scrollbars=no');
	}
	
}

function index_opt()
{
	document.getElementById("index_opt").className="green border3a";
	document.getElementById("share_opt").className="green border3a";
}

function share_opt()
{
	document.getElementById("index_opt").className="green border3a";
	document.getElementById("share_opt").className="green border3a";
}

//For sub- Menu(HomePage)
function PendingEnroll_click(){
	window.location.href='/mcx/action/dealerPendingApproval?selectedTab=P&printParam=N';
}

function ApprovedFirms_click(){
	window.location.href='/mcx/action/dealerPendingApproval?selectedTab=A&printParam=N';	
}

function highLightApprovedFirms(){
	document.getElementById("pending").className="tab2a_con";
	document.getElementById("approved").className="tab2_con";
}

function PendingMca_click(){
	window.location.href='/mcx/action/getDealerDetails?mcaStatus=P&printParam=N';	
}
function highlightPendingMca(){	
 if(document.getElementById("pending")!= null){
	document.getElementById("pending").className="tab2a_con";
	document.getElementById("pendingMca").className="tab2_con";
	}
}

function executedMca_click(){
	window.location.href='/mcx/action/getDealerDetails?mcaStatus=E&printParam=N';	
}

function highlightexecutedMca(){
	if(document.getElementById("pending")!= null){
	document.getElementById("pending").className="tab2a_con";
	document.getElementById("executedMca").className="tab2_con";
	}
}

function Pending(){
	window.location.href='/mcx/action/getDealerDetails?mcaStatus=P&printParam=N';
}
function executed(){
	window.location.href='/mcx/action/getDealerDetails?mcaStatus=E&printParam=N';
	}
function highlightExecuted(){
	if(document.getElementById("pendingMca1")!= null){
	document.getElementById("pendingMca1").className="tab2a_con";
	document.getElementById("executedMca1").className="tab2_con";
	}
	}

function adminalert_mouseover()
{
	var curleft = curtop = 0;
	obj=document.getElementById("alerts");
	objParent = obj.offsetParent;
	curleft = obj.offsetLeft;
	curtop=objParent.offsetTop+objParent.offsetHeight;

	document.getElementById("alerts").className="tab_mouseover";
	document.getElementById("menu").style.top=curtop;
	document.getElementById("menu").style.left=curleft;
	document.getElementById("menu").style.display="block";
	document.getElementById("menu").innerHTML="<table width='100%'  border='0' cellspacing='0' cellpadding='2' ><tr onclick=javascript:window.location.href='/mcx/action/adminViewAlert'><td ><a href='#' class='menu'>View Alert(s)</a></td></tr><tr onclick=javascript:window.location.href='/mcx/action/adminPostAlert'><td ><a href='#' class='menu'>Post Alert(s)</a></td></tr></table>";
	
	document.getElementById("menuframe").style.top=curtop;
	document.getElementById("menuframe").style.left=curleft;
	document.getElementById('menuframe').style.display="block";
	document.getElementById("menuframe").style.width = document.getElementById("menu").clientWidth;
	document.getElementById("menuframe").style.height = document.getElementById("menu").clientHeight;
	
}		


