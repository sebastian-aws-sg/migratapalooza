function RichEditor()
{
   var selectedImage = null; 

	this.put_docHtml			= put_docHtml;
	this.get_docHtml			= get_docHtml;			
	this.get_docXHtml			= get_docXHtml;			
	this.put_defaultFont		= put_defaultFont;
	this.put_defaultFontSize	= put_defaultFontSize;
	this.put_styleData			= put_styleData;		
	this.put_options			= put_options;
	this.addField				= addField;
	this.getValue				= getValue;
	this.put_debugWindow		= put_debugWindow;		
}

function put_docHtml(passedValue) {
	var r = document.selection.createRange();
	doc.innerHTML = passedValue;
	r.collapse(true);
	r.select();

	if (editor.style.visibility == "visible") {
		doc.focus();
		reset();
	}
}

function get_docHtml() {
	return doc.innerHTML;
}

function get_docXHtml() {								
	return innerXHTML(doc, new RegExp("contenteditable"));
}

function put_defaultFont(passedValue) {
	doc.style.fontFamily = passedValue;
}

function put_defaultFontSize(passedValue) {
	switch(passedValue) {
	case "1": passedValue = "xx-small"; break;
	case "2": passedValue = "x-small";	break;
	case "3": passedValue = "small";	break;
	case "4": passedValue = "medium";	break;
	case "5": passedValue = "large";	break;
	case "6": passedValue = "x-large";	break;
	case "7": passedValue = "xx-large";	break;
	}
	doc.style.fontSize = passedValue;
}

function put_styleData(passedValue) {

	var a,b;

	// Define the default style list
	this.styleList = [
		// element		description			Active
		[null,			"Normal",			0],
		[null,			"Heading 1",		0],
		[null,			"Heading 2",		0],
		[null,			"Heading 3",		0],
		[null,			"Heading 4",		0],
		[null,			"Heading 5",		0],
		[null,			"Heading 6",		0],
		[null,			"Address",			0],
		[null,			"Formatted",		0],
		["BLOCKQUOTE",	"Blockquote",		0],
		["CITE",		"Citation",			0],
		["BDO",			"Reversed",			0],
		["BIG",			"Big",				0],
		["SMALL",		"Small",			0],
		["DIV",			"Div",				0],
		["SUP",			"Superscript",		0],
		["SUB",			"Subscript",		0]
	];

	for (var i = 0; passedValue && i < passedValue.length; i++)
	{
		for (var j = 0; j < passedValue[i].rules.length; j++)
		{
			a = passedValue[i].rules[j].selectorText.toString().toLowerCase();
			b = passedValue[i].rules[j].style.cssText.toLowerCase();

			if (!a || !b) continue;

			document.styleSheets[0].addRule(a,b);

			if (a.indexOf("#") != -1) {
				continue;
			}

			if (a.indexOf(".") == 0) {
				this.styleList[this.styleList.length] = [a, "Class " + a, 1];
			}

			else if(a.indexOf(".") > 0) {
				this.styleList[this.styleList.length] = [a, a, 1];
			}

			else {
				for (var k = 0; k < this.styleList.length; k++) {
					if (this.styleList[k][0] == a) {
						this.styleList[k][2] = 1;
						break;
					}
				}
			}
		}
	}

	initStyleDropdown(this.styleList);
}

function addField(name, label, maxlen, value, size) {
	var row = rebarBottom.parentElement.insertRow(rebarBottom.rowIndex);
	var cell = row.insertCell();
	cell.className = 'rebar';
	cell.width = '100%';
	cell.innerHTML = '<nobr width="100%"><span class="field" width="100%">'
						+ '<img class="spacer" src="spacer.gif" width="2">'
						+ '<span class="start"></span>'
						+ '<span class="label">' + label + ':</span>'
						+ '&nbsp;<input class="field" type="text"'
							+ ' name="' + name + '" maxlength="' + maxlen + '"'
								+ (value ? ' value="' + value + '"' : '')
								+ 'size="' + (size ? size : 58) + '"'
								+ '>&nbsp;'
						+ '</span>'
						+ '</nobr>';
}

function getValue(name) {
	return document.all(name).value;
}

function put_options(passedValue) {
	this.options = passedValue;
	applyOptions(this.options);
}

function put_debugWindow(passedValue) {
	this.debugWindow = passedValue;
	DBG();
}
