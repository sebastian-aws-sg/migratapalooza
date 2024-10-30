
function tableEditor(docID, pntCell) {

   this.docID = docID;        
   this.pntCell = pntCell;    
   this.tableCell = null;     
   this.tableElem = null;     
   this.cellResizeObj = null; 
   this.cellWidth = null;     
   this.cellHeight = null;    
   this.cellX = null;         
   this.cellY = null;         
   this.moveable = null;      

   if (typeof(_tableEditor_prototype_called) == 'undefined') {
      _tableEditor_prototype_called = true;

      tableEditor.prototype.mergeDown = mergeDown;
      tableEditor.prototype.unMergeDown = unMergeDown;
      tableEditor.prototype.mergeRight = mergeRight;
      tableEditor.prototype.splitCell = splitCell;
      tableEditor.prototype.addCell = addCell;
      tableEditor.prototype.removeCell = removeCell;
      tableEditor.prototype.processRow = processRow;
      tableEditor.prototype.processColumn = processColumn;
      tableEditor.prototype.buildTable = buildTable;
      tableEditor.prototype.setTableElements = setTableElements;
      tableEditor.prototype.unSetTableElements = unSetTableElements;
      tableEditor.prototype.setDrag = setDrag;
      tableEditor.prototype.stopCellResize = stopCellResize;
      tableEditor.prototype.markCellResize = markCellResize;
      tableEditor.prototype.resizeCell = resizeCell;
      tableEditor.prototype.changePos = changePos;
      tableEditor.prototype.resizeColumn = resizeColumn;
      tableEditor.prototype.resizeRow = resizeRow;
      tableEditor.prototype.repositionArrows = repositionArrows;
      tableEditor.prototype.explore = explore;

      tableEditor.prototype.__addOrRemoveCols = __addOrRemoveCols;
      tableEditor.prototype.__findParentTable = __findParentTable;
      tableEditor.prototype.__hideArrows = __hideArrows;
      tableEditor.prototype.__showArrows = __showArrows;
      tableEditor.prototype.__resizeColumn = __resizeColumn;
   }

   document.body.innerHTML += ' <div id="rArrow" title="Drag to modify cell width." style="position:absolute; visibility:hidden; cursor: E-resize; z-index: 1" onmousedown="tEdit.markCellResize(this)" onmouseup="tEdit.stopCellResize(false)" ondragstart="handleDrag(0)"> <table border="0" cellpadding="0" cellspacing="0" width="7" height="7"> <tr><td bgcolor="#000000"></td></tr> </table> </div> <div id="dArrow" title="Drag to modify cell height." style="position:absolute; visibility:hidden; cursor: S-resize; z-index: 1" onmousedown="tEdit.markCellResize(this)" onmouseup="tEdit.stopCellResize(false)" ondragstart="handleDrag(0)"> <table border="0" cellpadding="0" cellspacing="0" width="7" height="7"> <tr><td bgcolor="#000000"></td></tr> </table> </div>';



   function setTableElements(){

      this.stopCellResize(true);
      this.tableCell = null;
      
      cursorPos=document.selection.createRange();

      if (document.selection.type == 'Text') {
         var elt = cursorPos.parentElement(); 
         while (elt) {
            if (elt.tagName == "TD") {
               break;
            }
            elt = elt.parentElement;
         }


         if (elt) {
            if (elt.id == this.docID)
               return;

            if (this.pntCell)
               if (this.pntCell == elt.id)
                  return;

            this.tableCell = elt;

            this.cellWidth = this.tableCell.offsetWidth;
            this.cellHeight = this.tableCell.offsetHeight;
            this.__showArrows();
         }
      } else {
         if (cursorPos.length == 1) {
            if (cursorPos.item(0).tagName == "TABLE") {
               this.tableElem = cursorPos.item(0);
               this.__hideArrows();
               this.tableCell = null;
            }
         }
      }
   }

   function unSetTableElements(){

      this.tableCell = null;
      this.tableElem = null;
      return;
   }

   function mergeDown() {
      if (!this.tableCell)
         return;
      
      if (!this.tableCell.parentNode.nextSibling) {
         alert("There is not a cell below this one to merge with.");
         return;
      }

      var topRowIndex = this.tableCell.parentNode.rowIndex;

      var bottomCell = this.tableCell.parentNode.parentNode.childNodes[ topRowIndex + this.tableCell.rowSpan ].childNodes[ this.tableCell.cellIndex ];

      if (!bottomCell) {
         alert("There is not a cell below this one to merge with.");
         return;
      }

      if (this.tableCell.colSpan != bottomCell.colSpan) {
         alert("Can't merge cells with different colSpans."); 
         return;
      }

      this.tableCell.innerHTML += bottomCell.innerHTML;
      this.tableCell.rowSpan += bottomCell.rowSpan;
      bottomCell.removeNode(true); 
      this.repositionArrows();
   }

   function unMergeDown() {
      if (!this.tableCell)
         return;
      
      if (this.tableCell.rowSpan <= 1) {
         alert("RowSpan is already set to 1.");
         return;
      }

      var topRowIndex = this.tableCell.parentNode.rowIndex;

      this.tableCell.parentNode.parentNode.childNodes[ topRowIndex + this.tableCell.rowSpan - 1 ].appendChild( document.createElement("TD") );

      this.tableCell.rowSpan -= 1;

   }

   function mergeRight() {
      if (!this.tableCell)
         return;
      if (!this.tableCell.nextSibling)
         return;

      if (this.tableCell.rowSpan != this.tableCell.nextSibling.rowSpan) {
         alert("Can't merge cells with different rowSpans.");
         return;
      }

      this.tableCell.innerHTML += this.tableCell.nextSibling.innerHTML;
      this.tableCell.colSpan += this.tableCell.nextSibling.colSpan;
      this.tableCell.nextSibling.removeNode(true);
       

      this.repositionArrows();
      this.__hideArrows();
      this.tableCell = null;
   }

   function splitCell() {
      if (!this.tableCell)
         return;
      if (this.tableCell.colSpan < 2) {
         alert("Cell can't be divided.  Add another cell instead");
         return;
      }

      this.tableCell.colSpan = this.tableCell.colSpan - 1;
      var newCell = this.tableCell.parentNode.insertBefore( document.createElement("TD"), this.tableCell);
      newCell.rowSpan = this.tableCell.rowSpan;
      this.repositionArrows();
   }

   function removeCell() {
      if (!this.tableCell)
         return;

      if (!this.tableCell.previousSibling && !this.tableCell.nextSibling) {
         alert("You can't remove the only remaining cell in a row.");
         return;
      }

      this.tableCell.removeNode(false);

      this.repositionArrows();
      this.tableCell = null;
   } 
 
   function addCell() {
      if (!this.tableCell)
         return;

      this.tableCell.parentElement.insertBefore(document.createElement("TD"), this.tableCell.nextSibling);
   }

   function processRow(action) {
      if (!this.tableCell)
        return;

      var idx = 0;
      var rowidx = -1;
      var tr = this.tableCell.parentNode;
      var numcells = tr.childNodes.length;
     

      while (tr) {
         if (tr.tagName == "TR")
            rowidx++;
         tr = tr.previousSibling;
      }

      var tbl = this.__findParentTable(this.tableCell);
  
      if (!tbl) {
         alert("Could not " + action + " row.");
         return;
      }
     
      if (action == "add") {
         var r = tbl.insertRow(rowidx);
         for (var i = 0; i < numcells; i++) {
            var c = r.appendChild( document.createElement("TD") );
            if (this.tableCell.parentNode.childNodes[i].colSpan)
               c.colSpan = this.tableCell.parentNode.childNodes[i].colSpan;
         }
      } else {
         tbl.deleteRow(rowidx);
         this.stopCellResize(true);
         this.tableCell = null;
      }
   }

   function processColumn(action) {
      if (!this.tableCell)
        return;

      var cellidx = this.tableCell.cellIndex;
      
      var tbl = this.__findParentTable(this.tableCell);
  
      if (!tbl) {
         alert("Could not " + action + " column.");
         return;
      }
         
      this.__addOrRemoveCols(tbl, cellidx, action);

      if (action == 'remove') {
         this.stopCellResize(true);
         this.tableCell = null;
      } else {
         this.repositionArrows();
      }
   }

   function __addOrRemoveCols(tbl, cellidx, action) {
      if (!tbl.childNodes.length)
         return;
      var i;
      for (i = 0; i < tbl.childNodes.length; i++) {
         if (tbl.childNodes[i].tagName == "TR") {
            var cell = tbl.childNodes[i].childNodes[ cellidx ];
            if (!cell)
               break; 
            if (action == "add") {
               cell.insertAdjacentElement("AfterEnd",  document.createElement("TD") );
            } else {
                 
               if (cell.rowSpan > 1) {
                  i += (cell.rowSpan - 1);
               }
               cell.removeNode(true);
            }
         } else {
            this.__addOrRemoveCols(tbl.childNodes[i], cellidx, action); 
         }
      }
   }

   function __findParentTable(cell) {
      var tbl = cell.parentElement
      while (tbl) {
         if (tbl.tagName == "TABLE") {
            return tbl;
         }
         tbl = tbl.parentElement;
      }
      return false;
   }

   function exploreTree(obj, pnt) {
      if (!obj.childNodes.length)
         return;
      var i;
      var ul = pnt.appendChild( document.createElement("UL") );
      for (i = 0; i < obj.childNodes.length; i++) {
         var li = document.createElement("LI");
         explore(obj.childNodes[i], li);
         ul.appendChild(li);
         exploreTree(obj.childNodes[i], li); 
      }
   }

   function explore(obj, pnt) {
      var i;
      for (i in obj) {
         var n = document.createTextNode(i +"="+obj[i]);
         pnt.appendChild(n);
         pnt.appendChild( document.createElement("BR") );
      }
   }

   function buildTable(pnt) {
      var t = pnt.appendChild( document.createElement("TABLE") );
      t.border=1;
      t.cellPadding=2;
      t.cellSpacing=0;
      var tb = t.appendChild( document.createElement("TBODY") );
      for(var r = 0; r < 10; r++) {
         var tr = tb.appendChild( document.createElement("TR") );
         for(var c = 0; c < 10; c++) {
            var cell = tr.appendChild( document.createElement("TD") );
            cell.appendChild( document.createTextNode(r+"-"+c) );
         }
      }
   }

   function setDrag(obj) {
     if (this.moveable) 
       this.moveable = null;
     else 
       this.moveable = obj; 
   }


   function changePos() {
      if (!this.moveable) 
         return;

      this.moveable.style.posTop = event.clientY - 10;
      this.moveable.style.posLeft = event.clientX - 25;
   }


   function markCellResize(obj) {
      if (this.cellResizeObj) {
         this.cellResizeObj = null;
      } else {
         this.cellResizeObj = obj;
      }
   }

   function stopCellResize(hidearrows) {
      this.cellResizeObj = null;
      if (hidearrows)
         this.__hideArrows();
   }

   function __hideArrows() {
      document.getElementById("rArrow").style.visibility = 'hidden';
      document.getElementById("dArrow").style.visibility = 'hidden';
   }

   function __showArrows() {
      if (!this.tableCell)
         return;

      var cell_hgt = this.tableCell.offsetTop;
      var cell_wdt = this.tableCell.offsetLeft;
      var par = this.tableCell.offsetParent;
      while (par) {
         cell_hgt = cell_hgt + par.offsetTop;
         cell_wdt = cell_wdt + par.offsetLeft;
         current_obj = par;
         par = current_obj.offsetParent;
      }
      this.cellX = cell_wdt + this.tableCell.offsetWidth; 
      this.cellY = cell_hgt + this.tableCell.offsetHeight; 

      var scrollTop = document.getElementById(this.docID).scrollTop;
      var scrollLeft = document.getElementById(this.docID).scrollLeft;

      document.getElementById("rArrow").style.posLeft = cell_wdt + this.tableCell.offsetWidth - 6 - scrollLeft;
      document.getElementById("rArrow").style.posTop = cell_hgt + (this.tableCell.offsetHeight / 2) - 2 - scrollTop;

      document.getElementById("dArrow").style.posLeft = cell_wdt + (this.tableCell.offsetWidth / 2) - 2 - scrollLeft;
      document.getElementById("dArrow").style.posTop = cell_hgt + this.tableCell.offsetHeight - 6 - scrollTop;

      document.getElementById("rArrow").style.visibility = 'visible';
      document.getElementById("dArrow").style.visibility = 'visible';
   }

   function repositionArrows() {

      if (!this.tableCell)
         return;

      var cell_hgt = this.tableCell.offsetTop;
      var cell_wdt = this.tableCell.offsetLeft;
      var par = this.tableCell.offsetParent;
      while (par) {
         cell_hgt = cell_hgt + par.offsetTop;
         cell_wdt = cell_wdt + par.offsetLeft;
         current_obj = par;
         par = current_obj.offsetParent;
      }

      var scrollTop = document.getElementById(this.docID).scrollTop;
      var scrollLeft = document.getElementById(this.docID).scrollLeft;

      document.getElementById("rArrow").style.posLeft = cell_wdt + this.tableCell.offsetWidth - 6 - scrollLeft;
      document.getElementById("rArrow").style.posTop = cell_hgt + (this.tableCell.offsetHeight / 2) - 2 - scrollTop;

      document.getElementById("dArrow").style.posLeft = cell_wdt + (this.tableCell.offsetWidth / 2) - 2 - scrollLeft; 
      document.getElementById("dArrow").style.posTop = cell_hgt + this.tableCell.offsetHeight - 6 - scrollTop;
   }

   function resizeCell() {
      if (!this.cellResizeObj)
         return;

      if (this.cellResizeObj.id == 'dArrow') {
         var scrollTop = document.getElementById(this.docID).scrollTop;
         var newHeight = (event.clientY - (this.cellY - scrollTop) ) + this.cellHeight;

         if (newHeight > 0)
            if (this.tableCell.rowSpan > 1) 
               this.tableCell.style.height = newHeight;
            else 
               this.resizeRow(newHeight);

         this.repositionArrows();

      } else if (this.cellResizeObj.id == 'rArrow') {
         var scrollLeft = document.getElementById(this.docID).scrollLeft;
         var newWidth = (event.clientX - (this.cellX - scrollLeft) ) + this.cellWidth;

         if (newWidth > 0) 
            if (this.tableCell.colSpan > 1)
               this.tableCell.style.width = newWidth;
            else
               this.resizeColumn(newWidth);

         this.repositionArrows();

      } else {
      }
   }

   function resizeRow(size) {
      if (!this.tableCell)
        return;

      var idx = 0;
      var rowidx = -1;
      var tr = this.tableCell.parentNode;
      var numcells = tr.childNodes.length;

      while (tr) {
         if (tr.tagName == "TR")
            rowidx++;
         tr = tr.previousSibling;
      }

      var tbl = this.__findParentTable(this.tableCell);
  
      if (!tbl) {
         return;
      }
     
      for (var j = 0; j < tbl.rows(rowidx).cells.length; j++) {
         if (tbl.rows(rowidx).cells(j).rowSpan == 1)
            tbl.rows(rowidx).cells(j).style.height = size;
      }
   }


   function resizeColumn(size) {
      if (!this.tableCell)
        return;

      var cellidx = this.tableCell.cellIndex;
      
      var tbl = this.__findParentTable(this.tableCell);
  
      if (!tbl) {
         alert("Could not resize  column.");
         return;
      }
         
      this.__resizeColumn(tbl, cellidx, size);
   }

   function __resizeColumn(tbl, cellidx, size) {
      if (!tbl.childNodes.length)
         return;
      var i;
      for (i = 0; i < tbl.childNodes.length; i++) {
         if (tbl.childNodes[i].tagName == "TR") {
            var cell = tbl.childNodes[i].childNodes[ cellidx ];
            if (!cell)
               break; 

            if (cell.colSpan == 1)
               cell.style.width = size;
         } else {
            this.__resizeColumn(tbl.childNodes[i], cellidx, size); 
         }
      }
   }
} 
