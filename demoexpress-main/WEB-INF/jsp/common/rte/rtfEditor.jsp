<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"      prefix="bean" %>
<%@ taglib uri="/WEB-INF/derivServ.tld" prefix="derivserv" %>

<html:html><head>
<link rel="StyleSheet" type="text/css" href="/mcx/static/css/richedit.css">
<link rel="StyleSheet" type="text/css" href="/mcx/static/css/syntax.css">
<link rel="StyleSheet" type="text/css" href="/mcx/static/css/custom.css">

<script language="JavaScript" src="/mcx/static/js/rte/rte_interface.js"></script>
<script language="JavaScript" src="/mcx/static/js/rte/rte_debug.js"></script>
<script language="JavaScript" src="/mcx/static/js/rte/rte.js"></script>
<script language="JavaScript" src="/mcx/static/js/rte/rte_codesweep.js"></script>

<SCRIPT language="JavaScript" src="/mcx/static/js/rte/tableEditor.js"></SCRIPT>
<script language="JavaScript">
// This defines the scriptlets public interface.  See rte_interface.js for
// the actual interface definition.
var public_description =  new RichEditor();
// Initialise the editor as soon as the window is loaded.
window.attachEvent("onload", initEditor);
// Initialise the tEdit var
var tEdit = null;
</script>
</head>
<body leftMargin="0" topMargin="0" onload="tEdit = new tableEditor('doc', 'textedit');"	onMouseMove="if (tEdit) { tEdit.changePos(); tEdit.resizeCell() }" >
<div id="loading" style="position: absolute; top: 0px; left: 0px; height: 100%; z-index: -1">
	<table width="100%" height="100%"><tr><td align="center" valign="middle">
	<font size="+2">Loading ...</font>
	</td></tr></table>
</div>
<table id="editor" height="100%" cellspacing="0" cellpadding="0" width="100%" bgcolor="buttonface" border="0">
  <tr onmouseup="press(false)" onmousedown="press(true)" onmouseover="hover(true)" onmouseout="hover(false)">
    <td class="rebar" width="627" ><nobr><span class="toolbar">
		<img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="2"><span class="start"></span>
		<img id="btnCut"	onclick="doStyle('Cut')" alt="@{Cut}" src="/mcx/static/images/rte/icon_cut.gif" width="20" height="20" />
		<img id="btnCopy"	onclick="doStyle('Copy')" alt="@{Copy}" src="/mcx/static/images/rte/icon_copy.gif" width="20" height="20" />
		<img id="btnPaste"	onclick="doStyle('Paste')" alt="@{Paste}" src="/mcx/static/images/rte/icon_paste.gif" width="20" height="20" />
		<img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="2"><span class="sep"></span>
		<img id="btnSelect" onclick="doStyle('SelectAll')" alt="@{SelectAll}" src="/mcx/static/images/rte/icon_select_all.gif" width="20" height="20" />
		<img id="btnRemove" onclick="doStyle('RemoveFormat')" alt="@{RemoveFormatting}" src="/mcx/static/images/rte/icon_rem_formatting.gif" width="20" height="20" />
		<img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="2"><span class="sep"></span>
		<input type="file" id="file1" name="imageFile" onchange="insertImage(this)" />
		<img id="btnImage"  onclick="insert('image')" alt="@{InsertImage}" src="/mcx/static/images/rte/icon_ins_image.gif" width="20" height="20" />
		<img id="btnTable"  onclick="insert('table')" alt="@{InsertTable}" src="/mcx/static/images/rte/icon_ins_table.gif" width="20" height="20" />
		<img id="btnTable"  onclick="document.getElementById('tblCtrl').style.visibility = 'visible';" alt="@{EditTable}" src="/mcx/static/images/rte/icon_edt_table.gif" width="20" height="20" />
		<img id="btnRule"   onclick="doStyle('InsertHorizontalRule')" alt="@{InsertLine}" src="/mcx/static/images/rte/icon_rule.gif" width="20" height="20" />
		<img id="btnChar"	onclick="insert('char')" alt="@{InsertCharacter}" src="/mcx/static/images/rte/icon_ins_char.gif" width="20" height="20" border="0" />
		<img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="2"><span class="sep"></span>
    </span></nobr></td></tr>
  <tr onmouseup="press(false)" onmousedown="press(true)" onmouseover="hover(true)" onmouseout="hover(false)">
    <td class="rebar" width="627" ><nobr><span class="toolbar">
		<img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="2"><span class="start"></span>
		<img id="btnBold"     onclick="doStyle('bold')" alt="@{Bold}" src="/mcx/static/images/rte/icon_bold.gif" width="20" height="20" />
		<img id="btnItalic"   onclick="doStyle('italic')" alt="@{Italic}" src="/mcx/static/images/rte/icon_italic.gif" width="20" height="20" />
		<img id="btnUnderline"  onclick="doStyle('underline')" alt="@{Underline}" src="/mcx/static/images/rte/icon_underline.gif" width="20" height="20" />
		<img id="btnStrikethrough"  onclick="doStyle('strikethrough')" alt="@{Strikethrough}" src="/mcx/static/images/rte/icon_strikethrough.gif" width="20" height="20" />
		<img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="2"><span class="sep"></span>
		<img id="btnLeftJustify"  onclick="doStyle('JustifyLeft')" alt="@{AlignLeft}" src="/mcx/static/images/rte/icon_left.gif" width="20" height="20" />
		<img id="btnCenter"   onclick="doStyle('JustifyCenter')" alt="@{Center}" src="/mcx/static/images/rte/icon_center.gif" width="20" height="20" />
		<img id="btnRightJustify"  onclick="doStyle('JustifyRight')" alt="@{AlignRight}" src="/mcx/static/images/rte/icon_right.gif" width="20" height="20" />
		<img id="btnFullJustify" onclick="doStyle('JustifyFull')" alt="@{AlignBlock}" src="/mcx/static/images/rte/icon_block.gif" width="20" height="20" />
		<img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="2"><span class="sep"></span>
		<img id="btnNumList"  onclick="doStyle('InsertOrderedList')" alt="@{NumberedList}" src="/mcx/static/images/rte/icon_numlist.gif" width="20" height="20" />
		<img id="btnBulList"  onclick="doStyle('InsertUnorderedList')" alt="@{BulettedList}" src="/mcx/static/images/rte/icon_bullist.gif" width="20" height="20" />
		<img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="2"><span class="sep"></span>
		<img id="btnOutdent"  onmousedown="doStyle('Outdent')" alt="@{DecreaseIndent}" src="/mcx/static/images/rte/icon_outdent.gif" width="20" height="20" />
		<img id="btnIndent"   onmousedown="doStyle('Indent')" alt="@{IncreaseIndent}" src="/mcx/static/images/rte/icon_indent.gif" width="20" height="20" />
		<span id="featureHistory">
		<img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="2"><span class="sep"></span>
		<img id="btnPrev" onmousedown="document.execCommand('Undo')" alt="@{HistoryBack}"    src="/mcx/static/images/rte/icon_undo.gif" width="20" height="20" />
		<img id="btnNext" onmousedown="document.execCommand('Redo')"  alt="@{HistoryForward}" src="/mcx/static/images/rte/icon_redo.gif" width="20" height="20" />
		</span>
		</span></nobr></td></tr>
	<tr id="featureStyleBar" onmouseup="press(false)" onmousedown="press(true)" onmouseover="hover(true)" onmouseout="hover(false)">
    <td class="rebar" width="627" ><nobr><span class="toolbar">
		<span id="featureStyle">
			<span class="label">@{Style}</span>
			<select name="" id="ctlStyle" class="button" onchange="addTag(this)">
			</select>
			<span class="sep"></span>
		</span>
		<span id="featureFont">
			<span class="label">@{Font}</span>
			<select class="fontselect" id="ctlFont" onchange="sel(this)">
				<option selected></option>
				<option id="Arial" value="Arial, Helvetica, sans-serif">Arial</option>
				<option id="Times New Roman" value="Times New Roman, Times, serif">Times New Roman</option>
				<option id="Courier New" value="Courier New, Courier, mono">Courier New</option>
				<option id="Georgia" value="Georgia, Times New Roman, Times, serif">Georgia</option>
				<option id="Verdana" value="Verdana, Arial, Helvetica, sans-serif">Verdana</option>
				<option id="Geneva" value="Geneva, Arial, Helvetica, sans-serif">Geneva</option>
			</select>
		</span>
		<span id="featureFontSize">
			<span class="sep"></span>
			<span class="label">@{Size}</span>
			<select class="button" id="ctlSize"  onchange="sel(this)">
				<option selected></option>
				<option value="1">xx-small</option>
				<option value="2">x-small</option>
				<option value="3">small</option>
				<option value="4">medium</option>
				<option value="5">large</option>
				<option value="6">x-large</option>
				<option value="7">xx-large</option>
			</select>
		</span>
		<span id="featureColour">
			<span class="sep"></span>
			<img id="btnText"  onclick="pickColor('ForeColor')" alt="@{TextColor}" src="/mcx/static/images/rte/icon_color_text.gif" width="36" height="20" />
		</span>
	</span></nobr></td>
</tr>
<tr id="rebarBottom">
    <td class="spacer" height="2" width="627" ><img height="1" src="/mcx/static/images/rte/spacer.gif" align="left"></td></tr>
  <tr>
    <td class="textedit" id="textedit" valign="top" height="100%" width="100%" >
		<div class="document" id="doc" onmouseout="cursorpos();" onkeyup="markSelectedElement(); tEdit.setTableElements(); tEdit.repositionArrows();" style="word-break: break-word; width=100% ;HEIGHT: 230px;" onclick="markSelectedElement()"  onmouseup="markSelectedElement(); tEdit.setTableElements(); tEdit.stopCellResize(false);">
		<bean:write name="TermForm" property="transaction.termVal" filter="false" />
	  </div>
<object id="color" data="/mcx/static/html/colorchooser.html" type="text/x-scriptlet">
</object>
<script for="color" event="onscriptletevent(name, data)">
	setColor(name, data);
</script>
	</td></tr>
</table>
<div id="tblCtrl" style="position:absolute; visibility:hidden; left:250; top:220; z-index: 5">
<table border="0" cellpadding="0" cellspacing="0" width="68" bgcolor="buttonface">
  <tr ondragstart="handleDrag(0)">
   <td><img src="/mcx/static/images/rte/spacer.gif" width="8" height="1" border="0"></td>
   <td><img src="/mcx/static/images/rte/spacer.gif" width="5" height="1" border="0"></td>
   <td><img src="/mcx/static/images/rte/spacer.gif" width="18" height="1" border="0"></td>
   <td><img src="/mcx/static/images/rte/spacer.gif" width="2" height="1" border="0"></td>
   <td><img src="/mcx/static/images/rte/spacer.gif" width="22" height="1" border="0"></td>
   <td><img src="/mcx/static/images/rte/spacer.gif" width="4" height="1" border="0"></td>
   <td><img src="/mcx/static/images/rte/spacer.gif" width="9" height="1" border="0"></td>
   <td><img src="/mcx/static/images/rte/spacer.gif" width="1" height="1" border="0"></td>
  </tr>

  <tr ondragstart="handleDrag(0)">
   <td colspan="2" onClick="document.getElementById('tblCtrl').style.visibility = 'hidden';"><img name="CloseWindow" src="/mcx/static/images/rte/CloseWindow.gif" width="13" height="16" border="0"></td>
   <td colspan="3" onmousedown="tEdit.setDrag( document.getElementById('tblCtrl') )" onmouseup="tEdit.setDrag( document.getElementById('tblCtrl') )"><img name="toolbar" src="/mcx/static/images/rte/toolbar.gif" width="42" height="16" border="0"></td>
   <td colspan="2"><img name="minimize" src="/mcx/static/images/rte/minimize.gif" width="13" height="16" border="0"></td>
   <td><img src="/mcx/static/images/rte/spacer.gif" width="1" height="16" border="0"></td>
  </tr>
  <tr ondragstart="handleDrag(0)">
	  <td colspan="7"><img name="hdr_tables" src="@{hdr_tables}" width="68" height="15" border="0"></td>
   <td><img src="/mcx/static/images/rte/spacer.gif" width="1" height="15" border="0"></td>
  </tr>
  <tr ondragstart="handleDrag(0)">
   <td colspan="7"><img name="plt_hdr" src="/mcx/static/images/rte/plt_hdr.gif" width="68" height="9" border="0"></td>
   <td><img src="/mcx/static/images/rte/spacer.gif" width="1" height="9" border="0"></td>
  </tr>

  <tr ondragstart="handleDrag(0)" onmouseup="press(false)" onmousedown="press(true)" onmouseover="hover(true)" onmouseout="hover(false)">
   <td rowspan="8"><img class="spacer" name="Editor_r4_c1" src="/mcx/static/images/rte/Editor_r4_c1.gif" width="8" height="112" border="0"></td>
   <td colspan="2" class="tbl"><img name="rmv_colspan"  onclick="tEdit.splitCell();" src="/mcx/static/images/rte/rmv_colspan.gif" width="21" height="24" alt="@{RemoveColspan}"></td>
   <td rowspan="8"><img class="spacer" name="Editor_r4_c4" src="/mcx/static/images/rte/Editor_r4_c4.gif" width="2" height="112" border="0"></td>
   <td colspan="2" class="tbl"><img name="rmv_rowspan"  onclick="tEdit.unMergeDown();" src="/mcx/static/images/rte/rmv_rowspan.gif" width="24" height="24" alt="@{RemoveRowspan}"></td>
   <td rowspan="8"><img class="spacer" name="Editor_r4_c7" src="/mcx/static/images/rte/Editor_r4_c7.gif" width="9" height="112" border="0"></td>
   <td><img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="1" height="26" border="0"></td>
  </tr>

  <tr ondragstart="handleDrag(0)">
   <td colspan="2"><img class="spacer" name="Editor_r5_c2" src="/mcx/static/images/rte/Editor_r5_c2.gif" width="23" height="2" border="0"></td>
   <td colspan="2"><img class="spacer" name="Editor_r5_c5" src="/mcx/static/images/rte/Editor_r5_c5.gif" width="26" height="2" border="0"></td>
   <td><img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="1" height="2" border="0"></td>
  </tr>

  <tr ondragstart="handleDrag(0)" onmouseup="press(false)" onmousedown="press(true)" onmouseover="hover(true)" onmouseout="hover(false)">
	  <td colspan="2" class="tbl"><img name="incr_colspan"  onclick="tEdit.mergeRight();" src="/mcx/static/images/rte/incr_colspan.gif" width="21" height="24" alt="@{IncreaseColspan}"  border="0"></td> 
	  <td colspan="2" class="tbl"><img name="incr_rowspan" onclick="tEdit.mergeDown();" src="/mcx/static/images/rte/incr_rowspan.gif" width="24" height="24" alt="@{IncreaseRowspan}"  border="0"></td>
   <td><img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="1" height="26" border="0"></td>
  </tr>

  <tr ondragstart="handleDrag(0)">
   <td colspan="2"><img class="spacer"  name="Editor_r7_c2" src="/mcx/static/images/rte/Editor_r7_c2.gif" width="23" height="2" border="0"></td>
   <td colspan="2"><img class="spacer" name="Editor_r7_c5" src="/mcx/static/images/rte/Editor_r7_c5.gif" width="26" height="2" border="0"></td>
   <td><img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="1" height="2" border="0"></td>
  </tr>

  <tr ondragstart="handleDrag(0)" onmouseup="press(false)" onmousedown="press(true)" onmouseover="hover(true)" onmouseout="hover(false)">
	  <td colspan="2" class="tbl"><img name="add_col" onclick="tEdit.processColumn('add');"  src="/mcx/static/images/rte/add_col.gif" width="21" height="22" alt="@{AddColumn}" border="0" ></td> 
	  <td colspan="2" class="tbl"><img name="add_row"  onclick="tEdit.processRow('add');" src="/mcx/static/images/rte/add_row.gif" width="24" height="22" alt="@{AddRow}" border="0" ></td>
   <td><img src="/mcx/static/images/rte/spacer.gif" width="1" height="24" border="0"></td>
  </tr>

  <tr ondragstart="handleDrag(0)">
   <td colspan="2"><img class="spacer" name="Editor_r9_c2" src="/mcx/static/images/rte/Editor_r9_c2.gif" width="23" height="4" border="0"></td>
   <td colspan="2"><img class="spacer" name="Editor_r9_c5" src="/mcx/static/images/rte/Editor_r9_c5.gif" width="26" height="4" border="0"></td>
   <td><img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="1" height="4" border="0"></td>
  </tr>

  <tr ondragstart="handleDrag(0)" onmouseup="press(false)" onmousedown="press(true)" onmouseover="hover(true)" onmouseout="hover(false)">
	  <td colspan="2" class="tbl"><img name="rmv_col" onclick="tEdit.processColumn('remove');" src="/mcx/static/images/rte/rmv_col.gif" width="21" height="22" alt="@{RemoveColumn}" border="0" ></td> 
	  <td colspan="2" class="tbl"><img name="rmv_row" onclick="tEdit.processRow('remove');" src="/mcx/static/images/rte/rmv_row.gif" width="24" height="22" alt="@{RemoveRow}" border="0" ></td>
   <td><img src="/mcx/static/images/rte/spacer.gif" width="1" height="24" border="0"></td>
  </tr>

  <tr ondragstart="handleDrag(0)">
   <td colspan="2"><img name="Editor_r11_c2" src="/mcx/static/images/rte/Editor_r11_c2.gif" width="23" height="4" border="0"></td>
   <td colspan="2"><img name="Editor_r11_c5" src="/mcx/static/images/rte/Editor_r11_c5.gif" width="26" height="4" border="0"></td>
   <td><img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="1" height="4" border="0"></td>
  </tr>

  <tr ondragstart="handleDrag(0)">
   <td colspan="7"><img name="plt_ftr" src="/mcx/static/images/rte/plt_ftr.gif" width="68" height="8" border="0"></td>
   <td><img class="spacer" src="/mcx/static/images/rte/spacer.gif" width="1" height="8" border="0"></td>
  </tr>
</table>
</div>
</body></html:html>
