
  // Collect interval
  new Ajax.Request('/mcx/servlet/session?aid=int',
  {
    method:'get',
    onSuccess: function(transport){
      var interval = transport.responseText; 
      if(isNaN(interval) == false)
        window.setInterval(function () {verifyInactivity();}, interval);                       
    }
  });
  
  // Verify Inactivity Function
  function verifyInactivity() {
    var winw = 475;
    var winh = 225;
    var winl = (screen.width - winw) / 2;
    var wint = (screen.height - winh) / 2;        
    var win = window.open("/mcx/static/html/SessionExpireWarning.html", null,
                          "top="+wint+",left="+winl+",height="+winh+",width="+winw+
                          ",status=no,toolbar=no,menubar=no,location=no");                       
    try {
	    win.focus();
	} catch (err) {
	}
  }  

  
  
  