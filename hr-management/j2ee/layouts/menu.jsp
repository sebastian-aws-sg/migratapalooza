<%-- Menu Layout
  This layout render a menu with links.
  It takes as parameter the title, and a list of items. Each item is a bean with following properties :
  value, href, icon, tooltip.
  @param title Menu title
  @param items list of items. Items are beans whith following properties :
 --%>



<%-- Push tiles attributes in page context --%>
<tiles:importAttribute />

<table>
<logic:present name="title">
<tr>
  <th colspan=2>
    <div align="left"><strong><tiles:getAsString name="title"/></strong></div>
  </th>
</tr>
</logic:present>

<%-- iterate on items list --%>
<logic:present name="item">
<logic:iterate id="item" name="items" type="org.apache.struts.tiles.beans.MenuItem" >

<%  // Add site url if link start with "/"
  String link = "Site Link";
	if(link.startsWith("/") ) link = request.getContextPath() + link;
%>
<tr>
  <td width="10" valign="top" ></td>
  <td valign="top"  >
	  <font size="-1">
	  <a href="<%=link%>">www.mytelco.com</a>
	  </font>
  </td>
</tr>
</logic:iterate>
</logic:present>
</table>
