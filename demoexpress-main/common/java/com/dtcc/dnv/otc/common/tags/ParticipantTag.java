package com.dtcc.dnv.otc.common.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.HiddenTag;
import org.apache.struts.taglib.html.OptionsCollectionTag;
import org.apache.struts.taglib.html.SelectTag;

import com.dtcc.dnv.otc.common.security.model.IUser;
import com.dtcc.dnv.otc.common.security.model.UserFactory;

/**
 * @author Steve Velez
 * @date Jan 2, 2004
 * @version 1.0
 * 
 *
 * Particpant tag used to build appropriate output for a family or non-family 
 * participant.  If the participant is a family participant (has more than one member)
 * then a dropdown is created with the family members.  If the participant is a non-family
 * member (has only one member) then a hard coded HTML and HIDDEN tags is created.
 * 
 * Rev  1.1     July 17, 2004       sav
 * Corrected bug where the value of the hidden participant id field should be set as the
 * participant id in the user object when the user is not in a family (only
 * one member).  Renamed setTag() to setSelectTag().  Updated comments and javadoc.
 * 
 * rev 1.2 scb 9/22/2004
 * made tag indexed.  added setIndexed() and setParent() calls
 * 
 * rev 1.3 scb 4/20/2005
 * added multiselect option to tag
 * 
 * @version 1.1 
 * @author Cognizant
 * 09/11/2007
 * Modified to obtain IUser instance from UserFactory.getUser() instead of UserFactory.createUser(). 
 * 
 */

public class ParticipantTag extends BaseHandlerTag {

    // General constants
    public final String FAMILY_LIST_PROPERTY = "familyListAsLabelValueArray";
    
    // Instance members
	private String name = Constants.BEAN_KEY;
	private String property = "";
	
	//optional fields if user wants multiselect
	private String multiple = "false";
	private String size = "";

	private SelectTag selectTag = new SelectTag();
	
	/**
	 * Overide parent method to perform start tag functionality for this tag.  This method
     * creates a select tag if the user is in a family (more than one member).  Otherwise
     * nothing is done in this method.
     * 
     * @throws JspException thrown if there is a JspException or there are issues attaining
     * user information.
     * 
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException {

		try {
			// replacing UserFactory.createUser() to UserFactory.getUser()
			IUser user = UserFactory.getUser((HttpServletRequest) pageContext.getRequest());
			
            //  Build a select tag if the user belongs in a family.
			if (user.isFamily()) {
                // Sets the select tag with the properties of this tag.
                setSelectTag();
			}
		}
		catch (JspException je) {
			throw je;
		}
		catch (Exception e) {
			throw new JspException(e.getMessage());
		}
		return EVAL_PAGE;
	}

    /**
     * Overide parent method to perform end tag functionality for this tag.  This method
     * closes a select tag (already created in startTag() if the user is in a family 
     * (more than one member).  Otherwise it will created static html to display the
     * participant information and create a hidden tag to store the appropriate values
     * of the participant.
     * 
     * @throws JspException thrown if there is a JspException or there are issues attaining
     * user information.
     * 
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
	public int doEndTag() throws JspException {

		try {
			// replacing UserFactory.createUser() to UserFactory.getUser()
			IUser user = UserFactory.getUser((HttpServletRequest) pageContext.getRequest());
			
			if (user.isFamily()){
				generateFamilyDropdown(user);
                selectTag.doEndTag();
                pageContext.removeAttribute(Constants.SELECT_KEY);
            }
			else
				generateNonFamilyParticipant(user);
		}
		catch (JspException je) {
			throw je;
		}
		catch (Exception e) {
			throw new JspException(e.getMessage());
		}
		return EVAL_PAGE;
	}

	/**
	 * Generates the a select tag of the family members if the user is in a family.
	 * @param user IUser object used to create the select and options tag.
	 * @throws JspException thrown if error occured building JSP tag
	 */
	private void generateFamilyDropdown(IUser user) throws JspException {

        // Save the current parent attribute object to recover at the end of the method.
		Object value = pageContext.findAttribute(getName());

        // Overwrite the 'named' object to allow the option tag to process.  The current 
        // state will be recovered later.
		pageContext.setAttribute(getName(), user);

		// Store this tag itself as a page attribute
		pageContext.setAttribute(Constants.SELECT_KEY, selectTag);
		OptionsCollectionTag optionCollectionTag = new OptionsCollectionTag();
		optionCollectionTag.setName(getName());
		optionCollectionTag.setProperty(FAMILY_LIST_PROPERTY);
		optionCollectionTag.setPageContext(pageContext);

        // Create option tags
		optionCollectionTag.doStartTag();
		optionCollectionTag.doEndTag();

		// Recover previous state.  Remove the page scope attributes created and
        // recover the previous value of the 'named' attribute.
		pageContext.removeAttribute(Constants.SELECT_KEY);
		pageContext.setAttribute(getName(), value);
	}

	/**
	 * Generates the output if the participant is not in a family (only one member).  
     * Creates a hard-coded string composed of the participant id and name, and then 
     * created a hidden tag to hold the value of the participant.
     * 
	 * @param user IUser object used to created the output.
	 * @throws JspException thrown is error occurs creating JSP tag
	 * @throws IOException thrown if error occurred writing hard-coded html to the OutputStream.
	 */
	private void generateNonFamilyParticipant(IUser user) throws JspException, IOException {

		HiddenTag hiddenTag = new HiddenTag();
		
		 //in order to make this an indexed tag, we need to connect it to the parent (so it can see the iterate above it)
		hiddenTag.setParent(getParent());

		// Print out hard coded id and name
		String html = user.getParticipantId() + " - " + user.getParticipantName() + "\n";
		pageContext.getOut().print(html);

		// Print hidden value holding id.        
		hiddenTag.setName(getName());
		hiddenTag.setProperty(getProperty());
		hiddenTag.setStyleId(getStyleId());
		hiddenTag.setPageContext(pageContext);
        hiddenTag.setValue(user.getParticipantId());
		hiddenTag.setIndexed(getIndexed());

        
		hiddenTag.doStartTag();
		hiddenTag.doEndTag();
	}
    
    
	/**
	 * Sets the appropriate properties of the select tag based on what was passed to
     * this tag.
     * 
	 * @throws JspException thrown if error occurs while creating JSP tag.
	 */
    private void setSelectTag() throws JspException{
        
        pageContext.setAttribute(Constants.SELECT_KEY, selectTag);
        selectTag.setName(getName());
        selectTag.setProperty(getProperty());
        selectTag.setPageContext(pageContext);
        selectTag.setOnblur(getOnblur());
        selectTag.setOnchange(getOnchange());
        selectTag.setOnclick(getOnclick());
        selectTag.setOndblclick(getOndblclick());
        selectTag.setOnfocus(getOnfocus());
        selectTag.setOnkeydown(getOnkeydown());
        selectTag.setOnkeypress(getOnkeypress());
        selectTag.setOnkeyup(getOnkeyup());
        selectTag.setOnmousedown(getOnmousedown());
        selectTag.setOnmousemove(getOnmousemove());
        selectTag.setOnmouseout(getOnmouseout());
        selectTag.setOnmouseover(getOnmouseover());
        selectTag.setOnmouseup(getOnmouseup());
        selectTag.setOnselect(getOnselect());
        selectTag.setStyle(getStyle());
        selectTag.setStyleClass(getStyleClass());
        selectTag.setStyleId(getStyleId());
        selectTag.setTabindex(getTabindex());
        selectTag.setTitle(getTitle());
        selectTag.setTitleKey(getTitleKey());
        
        //in order to make this an indexed tag, we need to connect it to the parent (so it can see the iterate above it)
        selectTag.setIndexed(getIndexed());
        selectTag.setParent(getParent());
        
        
        //for multiselect
        if(multiple.equalsIgnoreCase("true"))
        {
        	selectTag.setMultiple(getMultiple());
        	selectTag.setSize(getSize());
        }
        
        selectTag.doStartTag();
    }

	/**
	 * Method getName.
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Method setName.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
    
    
	/**
	 * Returns the property.
	 * @return String
	 */
	public String getProperty() {
		return property;
	}


	/**
	 * Sets the property.
	 * @param property The property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}
	/**
	 * Returns the multiple.
	 * @return String
	 */
	public String getMultiple()
	{
		return multiple;
	}


	/**
	 * Sets the multiple.
	 * @param multiple The multiple to set
	 */
	public void setMultiple(String multiple)
	{
		this.multiple = multiple;
	}



	/**
	 * Returns the size.
	 * @return String
	 */
	public String getSize()
	{
		return size;
	}

	/**
	 * Sets the size.
	 * @param size The size to set
	 */
	public void setSize(String size)
	{
		this.size = size;
	}

}
