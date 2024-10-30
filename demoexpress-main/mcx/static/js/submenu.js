var menu = new Array();
var submenu = new Array();
var submenubar = new Array();
var workingonmenu;

function highlightmenusandsubmenus(menuid,submenuid)
{

   if(menuid!=null)
   {
     if(menuid.length==0) menuid=null;
   }
   if(submenuid!=null)
   {
     if(submenuid.length==0) submenuid=null;
   }
   
   if(menuid==null) return;
    initializemenuitems();
    for(var i=0;i<menu.length;i++)
    {
      if(menu[i]==menuid)
      {
	    document.getElementById(menu[i]).className="tab_on";
	    workingonmenu[menu[i]]='true';
	    if(submenuid!=null)
	    {
  	    	document.getElementById(submenubar[i]).style.display="block";
        	var thissubmenu = submenu[i];
        	for(var j=0;j<thissubmenu.length;j++)
        	{
	        	if(thissubmenu[j]==submenuid)
	        	{
			    	document.getElementById(thissubmenu[j]).className="tab2_con";
	        	}
	        	else
	        	{
			    	var objtmp = document.getElementById(thissubmenu[j]);
			    	if(objtmp != null)
			      		objtmp.className="tab2a_con";
	        	}
        	}	
	    
	    } 
      }
      else
      {
	    if(document.getElementById(menu[i])!=null)
	    {
	       	workingonmenu[menu[i]]='false';
	      	document.getElementById(menu[i]).className="tab_con";
	    }
	    
      }
    }
}

function initializemenuitems()
{
  workingonmenu=new Object();

  menu[0]="tab";
  menu[1]="tab2";
  menu[2]="tab3";
  menu[3]="tab4";
  menu[4]="mcasetup";
  menu[5]="alerts";
  menu[6]="tab2_1";
  
  var homemenu=new Array();
  homemenu[0]="pending";
  homemenu[1]="approved";
  homemenu[2]="pendingMca";
  homemenu[3]="executedMca";
  homemenu[4]="pendingMca1";
  homemenu[5]="executedMca1";
  
  var mcamenu=new Array();
  
  var managedocmenu=new Array();
  managedocmenu[0]="preexistmca";
  managedocmenu[1]="otherdoc";
  managedocmenu[2]="searchdoc";
  
  var enrollmenu=new Array();
  
  var alertsmenu=new Array();
  alertsmenu[0]="adminviewalerttab";
  alertsmenu[1]="postalerttab";
  
  submenu[0]=homemenu;
  submenu[2]=managedocmenu;
  submenu[5]=alertsmenu;
  
  submenubar[0]="home_sub";
  submenubar[2]="magdoc_sub";
  
  submenubar[5]="alerts_sub";
  
}

function menumouseout(menuid)
{
   if(ondropdown==null && onsidemenu==null && onsubmenu==null)
   {
    var turnoff_highlight=true;
    if(workingonmenu==null) 
    {
      turnoff_highlight=true;
    }
    else
    {
      if(workingonmenu[menuid]=='true')
      {
        turnoff_highlight = false;
      }
      else
      {
	    turnoff_highlight=true;
	  }
    }
    
	if(turnoff_highlight)
    {
      var thisobj = document.getElementById(menuid);
	  thisobj.className="tab_con";
	}	
    document.getElementById('menu').style.display="none";
    document.getElementById('menuframe').style.display="none";
    if(menuid != 'mcasetup' && menuid != 'alerts'){
    document.getElementById('sidemenu').style.display="none";
    	document.getElementById("sidemenuframe").style.display="none";
    }
    }
}

function menu_click(urlhref)
{   

 	window.location.href=urlhref;
}



function dropdownmenu_mouseout()
{ 
    document.getElementById('menu').style.display="none";
     document.getElementById('menuframe').style.display="none";
}

function dropdownmenu_mouseover()
{
  document.getElementById('menu').style.display="block";
  document.getElementById('menuframe').style.display="block";
}

function sidemenu_mouseout()
{
  document.getElementById('sidemenu').style.display="none";
  document.getElementById("sidemenuframe").style.display="none";
  if(document.getElementById('menu').style.display=="block")
  {
    document.getElementById('menu').style.display="none";
    document.getElementById('menuframe').style.display="none";
  }
  
}

function sidemenu_mouseover()
{
  mca_mouseover();
  document.getElementById('sidemenu').style.display="block";
  document.getElementById("sidemenuframe").style.display="block";
  
}