var history = new Object;
history.items = [];
history.cursor = -1;

function saveHistory() {
	if (!getOption("history")) return;
	codeSweeper();
	history.items[history.items.length] = doc.innerHTML;
	history.cursor = history.items.length;
	showHistory();
}

function goHistory(value) {

	if (!RichEditor.txtView) return;
	switch(value) {
	case -1:
		i = history.cursor - 1;
		if (history.cursor == history.items.length) {
			saveHistory();
		}
		history.cursor = i;
		break;
	case 1:
		history.cursor ++;
		break;
	}
	if (history.items[history.cursor]) {
		doc.innerHTML = history.items[history.cursor];
	}
	showHistory()
}

function showHistory() {

	if (history.cursor > 0) {
		btnPrev.className = "";
		btnPrev.disabled = false;
	} else {
		btnPrev.className = "disabled";
		btnPrev.disabled = true;
	}

	if (history.cursor < history.items.length - 1) {
		btnNext.className = "";
		btnNext.disabled = false;
	} else {
		btnNext.className = "disabled";
		btnNext.disabled = true;
	}
}
