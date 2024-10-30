function DBGGetWindow(el) {
	if (el) {
		try { el.className; } catch(e) {
			if (e.number == -2147418094) {
				return null;
			}
		}
	}
	return el;
}

function DBG(n, str)
{
	if (typeof(n) == "undefined" || !DBG.fInitialised) {
		var el = DBGGetWindow(public_description.debugWindow);
		if (el) {
			el.className = "debugWindow";
			el.innerHTML = '<table width="100%" id="debug">'
							+ '<tr><th>Seq</th><th>Caller</th><th>Debug</th></tr>'
							+ '</table>';
			DBG.idTable = el.all("debug");
		}
		DBG.fInitialised = true;
		DBG.seq = 0;
	}

	if (typeof(str) != "undefined") {
		var el = DBGGetWindow(DBG.idTable);
		if (el) {
			var row = el.insertRow(1);
			var caller = DBG.caller.toString().substr(9);
			var cell = row.insertCell();
			cell.innerText = DBG.seq++;
			cell.nowrap = '';
			cell = row.insertCell();
			cell.innerText = caller.substr(0, caller.indexOf('\n'));
			cell.nowrap = '';
			row.insertCell().innerText = str;
		} else {
			if (RichEditor.debug) {
				window.status = str;
			}
		}
	}
}
