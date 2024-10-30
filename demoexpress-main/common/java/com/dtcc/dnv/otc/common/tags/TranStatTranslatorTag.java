package com.dtcc.dnv.otc.common.tags;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;


/**
 * @author hdabir
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TranStatTranslatorTag extends ExBodyTagSupport {
	
private PageContext pc = null;
private Tag parent = null;
private String tranStatCd = null;

public void setPageContext(PageContext p) {
	pc = p;
}

public void setParent(Tag t) {
	parent = t;
}

public Tag getParent() {
	return parent;
}




public int doAfterBody() throws JspException {
	try {

		String _bodyContent = bodyContent.getString().trim();
		JspWriter writer = bodyContent.getEnclosingWriter();
		if(_bodyContent != null) 
		{
			if(_bodyContent.equalsIgnoreCase("Allege"))
				writer.print("A");
			else if (_bodyContent.equalsIgnoreCase("Confirm"))
				writer.print("C");
			else if (_bodyContent.equalsIgnoreCase("Confirmed"))
				writer.print("C");
			else if (_bodyContent.equalsIgnoreCase("Error"))
				writer.print("R");
			else if (_bodyContent.equalsIgnoreCase("Reject"))
				writer.print("R");
			else if (_bodyContent.equalsIgnoreCase("Rejected"))
				writer.print("R");
			else if (_bodyContent.equalsIgnoreCase("Unconfirm"))
				writer.print("U");
			else if (_bodyContent.equalsIgnoreCase("Unconfirmed"))
				writer.print("U");
			else if (_bodyContent.equalsIgnoreCase("DK"))
				writer.print("DK");
			else if (_bodyContent.startsWith("Can"))
				writer.print("Can");
			else if (_bodyContent.equalsIgnoreCase("Matched"))
				writer.print("M");
			else if (_bodyContent.equalsIgnoreCase("Matched-Allege"))
				writer.print("MA");
			else if (_bodyContent.equalsIgnoreCase("Pending"))
				writer.print("P");
			else if (_bodyContent.equalsIgnoreCase("Pending-Allege"))
				writer.print("PA");
			 																																										
			
	}
	} catch(IOException e) {
		throw new JspTagException("An IOException occurred.");
	}
	return SKIP_BODY;
}


/**
 * Returns the tranStatCd.
 * @return String
 */
public String getTranStatCd() {
	return tranStatCd;
}

/**
 * Sets the tranStatCd.
 * @param tranStatCd The tranStatCd to set
 */
public void setTranStatCd(String tranStatCd) {
	this.tranStatCd = tranStatCd;
}

public void release() {
		super.release();
	}


}//end of tag


     

