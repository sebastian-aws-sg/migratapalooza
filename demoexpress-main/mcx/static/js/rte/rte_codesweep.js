function convertToHTML(str) {
  var bstr = '';
  for(i=0; i<str.length; i++)
  {
    if(str.charCodeAt(i)>127)
    {
      bstr += '&#' + str.charCodeAt(i) + ';';
    }
    else
    {
      bstr += str.charAt(i);
    }
   }
   return bstr;
}
function replaceCharacters(html) {

	var set = [
		["€","‘","’","’","“","”","–","—","¡","¢","£","£","¤","¥","¦","§","¨","©","ª","«","¬","­","®","¯","°","±","²","³","´","µ","¶","·","¸","¹","º","»","¼","½","¾","¿"],
		["&euro;","&lsquo;","&rsquo;","&rsquo;","&ldquo;","&rdquo;","&ndash;","&mdash;","&iexcl;","&cent;","&pound;","&pound;","&curren;","&yen;","&brvbar;","&sect;","&uml;","&copy;","&ordf;","&laquo;","&not;","­","&reg;","&macr;","&deg;","&plusmn;","&sup2;","&sup3;","&acute;","&micro;","&para;","&middot;","&cedil;","&sup1;","&ordm;","&raquo;","&frac14;","&frac12;","&frac34;","&iquest;"]
	];

	if (html) {
		for(var j = 0; j < set[0].length; j++){
			html = html.replace(eval("/"+set[0][j]+"/g"),set[1][j]);
		}
	}

	return html || "";
}

function removeEmptyTags(html) {
	var re = /<[^(>|\/)]+>[ |	]*<\/[^>]+>/gi;
	while(re.test(html)) {
		html = html.replace(re,"");
		while(re.test(html)) {
			html = html.replace(re,"");
		}
	}
	return html;
}

function replaceAbsoluteUrls(html) {
	var docLoc = document.location.toString();
	docLoc = docLoc.substring(0,docLoc.lastIndexOf("/")+1);
	docLoc = docLoc.replace(/\//gi,"\\\/");
	var re = eval("/"+docLoc+"/gi");
	return html.replace(re, "");
}

function replaceTags(set, html) {
	var re;
	for(var i = 0; i < set.length; i++) {
		re = eval("/(<[\/]{0,1})"+set[i][0]+">/gi");
		html=html.replace(re,"$1"+set[i][1]+">");
	}
	return html
}

function codeSweeper() {
	var html = doc.innerHTML;
	if (html) html = replaceCharacters(html);
	if (html) html = replaceAbsoluteUrls(html);
	if (html) html = replaceTags([["strong","B"],["em","I"]],html);
	return html;
}
