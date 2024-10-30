function showhide(treeObject)

{

      var index = treeObject.index - 1;

      var ishide = false;

      var bodyObject = treeObject.parentNode.parentNode.parentNode;

      var tableLength = bodyObject.childNodes.length;

      var trStyle = bodyObject.childNodes(index).className;  
      
     
      
      

      ++index;

      for(var count = index  ; count<tableLength;count++)

      {

            var newStyle = bodyObject.childNodes(count).className;

            

            if(newStyle !=  trStyle || newStyle == "odd hide" || newStyle == "even hide")

            {
				
                  bodyObject.childNodes(count).className = trStyle;
                  var styleClassName = bodyObject.childNodes(count).childNodes(1).className;                  
                  bodyObject.childNodes(count).childNodes(1).className = styleClassName + " paddingLeft";				


            }

            else

            {

                  bodyObject.childNodes(count).className = "hide";      

                  ishide = true;          

            }

            

            if(bodyObject.childNodes(count).id == "grpEnd")

            {

                  break;

            }

      }

      

      if(ishide)

      {

            treeObject.src="/mcx/static/images/tree/ftv2plastnode_new.gif";

      }

      else

      {

            treeObject.src="/mcx/static/images/tree/ftv2mlastnode.gif";

      }

      

}

 
