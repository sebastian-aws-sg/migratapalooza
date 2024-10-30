<%-- Each tiles jsp needs the taglibs definition in it. --%>
<%@ include file="/WEB-INF/jsp/cwf/common/include.jsp" %>
<table width="96%" id="errtbl">
<tr><td>
<logic:messagesPresent>
   <html:messages id="error"> 
      <c:if test="${error!=''}"><br><span class="error">&nbsp;*&nbsp;<c:out value="${error}"/></span><br></c:if>
   </html:messages>
</logic:messagesPresent>

<logic:messagesPresent message="true">
   <html:messages id="message" message="true">
      <br><span class="status">&nbsp;&nbsp;<c:out value="${message}"/></span><br>
   </html:messages>
</logic:messagesPresent>
</td></tr>
</table>
