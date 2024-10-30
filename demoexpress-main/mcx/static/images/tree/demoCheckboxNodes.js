USETEXTLINKS = 1  
STARTALLOPEN = 0
HIGHLIGHT = 0
PRESERVESTATE = 1
USEICONS = 0
BUILDALL = 1
function generateCheckBox(parentfolderObject, itemLabel, checkBoxDOMId) 
{
  var newObj;
  newObj = insDoc(parentfolderObject, gLnk("R", itemLabel, "javascript:parent.op()"))
}
foldersTree = gFld("", "demoCheckboxRightFrame.html")
foldersTree.treeID = "checkboxTree"
aux1 = insFld(foldersTree, gFld("Multiple", "javascript:parent.op()"))
generateCheckBox(aux1, "Index Share Japan 2004", "")
generateCheckBox(aux1, "Index Sahre Europe 2004", "")

