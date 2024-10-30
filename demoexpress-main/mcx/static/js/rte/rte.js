RichEditor.txtView = true;  
var curPostn;

function initEditor()
{
	if (!public_description.styleData) {
	  public_description.put_styleData(null);
	}

	var strDefaults = 'dragdrop=no;source=yes';
	strDefaults += ';history=' + (document.queryCommandSupported('Undo') ? 'yes' : 'no');
	applyOptions(strDefaults);

	loading.style.display = 'none';
    doc.contentEditable = "true";
    editor.style.visibility = 'visible';

	doc.focus();
}

function checkRange()
{
   RichEditor.selectedImage = null;
   if (!RichEditor.txtView) return;      
   doc.focus();
   if (document.selection.type == "None") {
      document.selection.createRange();
   }
var r = document.selection.createRange();
   DBG(1, 'RANGE Bounding('
            + 'top='+r.boundingHeight
            + ', left='+r.boundingHeight
            + ', width='+r.boundingWidth
            + ', height='+r.boundingHeight + ')'
         + ', Offset('
            + 'top='+r.offsetTop
            + ', left='+r.offsetLeft + ')'
         + ', Text=(' + r.text + ')'
         + ', HTML=(' + r.htmlText + ')'
      );
}

function insert(what)
{
   if (!RichEditor.txtView) return;      

   DBG(1, 'insert(' + what + ')');

   switch(what)
   {
   case "table":
      strPage = "../static/html/dlg_ins_table.html";
      strAttr = "status:no;dialogWidth:340px;dialogHeight:360px;help:no";
      break;
   case "char":
      strPage = "../static/html/dlg_ins_char.html";
      strAttr = "status:no;dialogWidth:450px;dialogHeight:290px;help:no";
      break;
   case "image":
   		alert('Please Click on Browse and Select the Image you wish to insert');
      return;
   }

   html = showModalDialog(strPage, window, strAttr);
   if (html) {
      insertHtml(html);
   }
}

function insertImage(imgObj)
{
   if (!RichEditor.txtView) return;      
	var html = '<IMG id="img1" src="' + imgObj.value + '" />';
	if(html) {
		insertHtml(html);
	}
}

function insertHtml(html)
{

   var sel = document.selection.createRange();     
   if (document.selection.type == 'Control') {
      return;
   }
   curPostn.pasteHTML(html);  
   doc.focus();
}

function doStyle(s){ 
   if(!RichEditor.txtView) return; 
   DBG(1, 'doStyle(' + s + ')'); 
   checkRange(); 
   if(s!='InsertHorizontalRule'){ 
      document.execCommand(s); 
   } else if( s=='InsertHorizontalRule') { 
      document.execCommand(s,false, null); 

   } 
   reset(); 
} 


function link(on)
{
   if (!RichEditor.txtView) return;      

   var strURL = "http://";
   var strText;

   doc.focus();
   var r = document.selection.createRange();
   var el = r.parentElement();

   if (el && el.nodeName == "A") {
      r.moveToElementText(el);
      if (!on) {      
         r.pasteHTML(el.innerHTML);
         return;
      }
      strURL = el.href;
   }

   strText = r.text;

   strURL = window.prompt("Enter URL", strURL);
   if (strURL) {
      if (!strText || !strText.length) {
         strText = strURL;
      }

      r.pasteHTML('<A href=' + strURL + ' target=_new>' + strText + '</a>');
   }

   reset();
}

function sel(el)
{
   if (!RichEditor.txtView) return;      
   checkRange();
   switch(el.id)
   {
   case "ctlFont":
      document.execCommand('FontName', false, el[el.selectedIndex].value);
      break;
   case "ctlSize":
      document.execCommand('FontSize', false, el[el.selectedIndex].value);
      break;
   case "ctlStyle":
      document.execCommand('FormatBlock', false, el[el.selectedIndex].text);
      break;
   }
   doc.focus();
   reset();
}

function pickColor(fg)
{
   if (!RichEditor.txtView) return;      
   checkRange();
   var el = window.event.srcElement;
   if (el && el.nodeName == "IMG") {
      setState(el, true);
   }
   document.getElementById('color').style.top = window.event.clientY + 10;
   document.getElementById('color').style.left = window.event.clientX - 250;
   document.getElementById('color').style.display = 'block';
   document.getElementById('color')._fg = fg;
}

function setColor(name, data)
{
   document.getElementById('color').style.display = 'none';
   checkRange();
   if (!data) {
      removeFormat(document.selection.createRange(), document.getElementById('color')._fg);
   } else {
      document.execCommand(document.getElementById('color')._fg, false, data);
   }   
   var btnText = document.getElementById('btnText');
   setState(btnText, false);
   doc.focus();
}

function removeFormat(r, name)
{
   var cmd = [ "Bold", "Italic", "Underline", "Strikethrough", "FontName", "FontSize", "ForeColor", "BackColor" ];
   var on = new Array(cmd.length);
   for (var i = 0; i < cmd.length; i++) {
      on[i] = name == cmd[i] ? null : r.queryCommandValue(cmd[i]);
   }
   r.execCommand('RemoveFormat');
   for (var i = 0; i < cmd.length; i++) {
      if (on[i]) r.execCommand(cmd[i], false, on[i]);
   }
}

function selValue(el, str)
{
   if (!RichEditor.txtView) return;      
   for (var i = 0; i < el.length; i++) {
      if ((!el[i].value && el[i].text == str) || el[i].value == str) {
         el.selectedIndex = i;
         return;
      }
   }
   el.selectedIndex = 0;
}

function setState(el, on)
{
   if (!RichEditor.txtView) return;     
   if (!el.disabled) {
      if (on) {
         el.defaultState = el.className = "down";
      } else {
         el.defaultState = el.className = null;
      }
   }
}

function getStyle() {
   var style = document.queryCommandValue('FormatBlock');
   if (style == "Normal") {
      doc.focus();
      var rng = document.selection.createRange();
      if (typeof(rng.parentElement) != "undefined") {
         var el = rng.parentElement();
         var tag = el.nodeName.toUpperCase();
         var str = el.className.toLowerCase();
         if (!(tag == "DIV" && el.id == "doc" && str == "textedit")) {
            if (tag == "SPAN") {
               style = "." + str;
            } else if (str == "") {
               style = tag;
            } else {
               style = tag + "." + str;
            }
         }
         return style;
      }
   }
   return style;
}

function getfontface()
{
var family = document.selection.createRange(); 

if (document.selection.type == 'Control') {
   return;
}

var el = family.parentElement(); 
var tag = el.nodeName.toUpperCase(); 

	if (typeof(el.parentElement) != "undefined" && tag == "FONT") { 
		var elface = el.getAttribute('FACE'); 
		return elface; 
	}
}

function markSelectedElement() {

   RichEditor.selectedImage = null;

   var r = document.selection.createRange();

   if (document.selection.type != 'Text') {
      if (r.length == 1) {
         if (r.item(0).tagName == "IMG") {
            RichEditor.selectedImage = r.item(0);
         }
      }
   }
}

function reset(el)
{	
   if (!RichEditor.txtView) return;      
   var ctlStyle = document.getElementById('ctlStyle');
   var ctlFont = document.getElementById('ctlFont');
   var ctlSize = document.getElementById('ctlSize');
   var btnBold = document.getElementById('btnBold');
   var btnItalic = document.getElementById('btnItalic');
   var btnUnderline = document.getElementById('btnUnderline');
   var btnStrikethrough = document.getElementById('btnStrikethrough');
   var btnLeftJustify = document.getElementById('btnLeftJustify');
   var btnCenter = document.getElementById('btnCenter');
   var btnRightJustify = document.getElementById('btnRightJustify');
   var btnFullJustify = document.getElementById('btnFullJustify');
   var btnNumList = document.getElementById('btnNumList');
   var btnBulList = document.getElementById('btnBulList');
   if (!el) document.getElementById('color').style.display = 'none';
   if (!el || el == ctlStyle)         selValue(ctlStyle, getStyle());
   if (!el || el == ctlFont)         selValue(ctlFont, getfontface());
   if (!el || el == ctlSize)         selValue(ctlSize, document.queryCommandValue('FontSize'));
   if (!el || el == btnBold)         setState(btnBold, document.queryCommandValue('Bold'));
   if (!el || el == btnItalic)         setState(btnItalic,   document.queryCommandValue('Italic'));
   if (!el || el == btnUnderline)      setState(btnUnderline, document.queryCommandValue('Underline'));
   if (!el || el == btnStrikethrough)   setState(btnStrikethrough, document.queryCommandValue('Strikethrough'));
   if (!el || el == btnLeftJustify)   setState(btnLeftJustify, document.queryCommandValue('JustifyLeft'));
   if (!el || el == btnCenter)         setState(btnCenter,   document.queryCommandValue('JustifyCenter'));
   if (!el || el == btnRightJustify)   setState(btnRightJustify, document.queryCommandValue('JustifyRight'));
   if (!el || el == btnFullJustify)   setState(btnFullJustify, document.queryCommandValue('JustifyFull'));
   if (!el || el == btnNumList)      setState(btnNumList, document.queryCommandValue('InsertOrderedList'));
   if (!el || el == btnBulList)      setState(btnBulList, document.queryCommandValue('InsertUnorderedList'));
}

function hover(on)
{
   if (!RichEditor.txtView) return;      
   var el = window.event.srcElement;
   if (el && !el.disabled && el.nodeName == "IMG" && el.className != "spacer") {
      if (on) {
         el.className = "hover";
      } else {
         el.className = el.defaultState ? el.defaultState : null;
      }
   }
}
function press(on)
{
   if (!RichEditor.txtView) return;      
   var el = window.event.srcElement;
   if (el && !el.disabled && el.nodeName == "IMG" && el.className != "spacer") {
      if (on) {
         el.className = "down";
      } else {
         el.className = el.className == "down" ? "hover" : el.defaultState ? el.defaultState : null;
      }
   }
}

function addTag(obj) {

   if (!RichEditor.txtView) return;      

   var value = obj[obj.selectedIndex].value;
   if (!value) {                        
      sel(obj);
      return;
   }

   var type = 0;                        

   if (value.indexOf(".") == 0) {            
      type = 1;
   } else if (value.indexOf(".") != -1) {      
      type = 2;
   }

   doc.focus();

   var r = document.selection.createRange();
   r.select();
   var s = r.htmlText;

   if (s == " " || s == "&nbsp;") {
      return;
   }

   switch(type)
   {
   case 1:
      value = value.substring(1,value.length);
      r.pasteHTML("<span class="+value+">" + r.htmlText + "</span>")
      break;

   case 2:
      value = value.split(".");
      r.pasteHTML('<' + value[0] + ' class="' + value[1] +'">'
               + r.htmlText
               + '</' + value[0] + '>'
            );
      break;

   default:
      r.pasteHTML("<"+value+">"+r.htmlText+"</"+value+">")
      break;
   }
}

function initStyleDropdown(styleList) {

   for (var i = 0; i < styleList.length; i++) {
      var oOption = document.createElement("OPTION");
      if (styleList[i][0]) oOption.value = styleList[i][0];
      oOption.text = styleList[i][1];
      oOption.style.backgroundColor = 'white';
      document.all.ctlStyle.add(oOption);
   }
}

function applyOptions(str)
{
   var options = str.split(";");
   for (var i = 0; i < options.length; i++) {
      var eq = options[i].indexOf('=');
      var on = eq == -1 ? true : "yes;true;1".indexOf(options[i].substr(eq+1).toLowerCase()) != -1;
      var name = eq == -1 ? options[i] : options[i].substr(0,eq);
      var el = document.all("feature" + name);
      if (el) {
         el.runtimeStyle.display = (on ? 'inline' : 'none'); 
      } else {
         if (!RichEditor.aOptions) RichEditor.aOptions = new Array;
         RichEditor.aOptions[name] = on;
      }
   }
}

function getOption(name)
{
   if (RichEditor.aOptions) return RichEditor.aOptions[name];
   return;   
} 

function handleDrag(n)
{
   if (!getOption("dragdrop"))
   {
      switch(n) {
      case 0:   
         window.event.dataTransfer.dropEffect = "none";
         break;
      }
      window.event.returnValue = false;
   }
}
function cursorpos(){ 
if (document.selection.type != 'Control')   {
   curPostn = document.selection.createRange().duplicate(); 
   }      
}

