<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/derivServ.tld" prefix="derivserv" %>
<derivserv:userType role="T">
	<jsp:include page="/WEB-INF/jsp/common/submenuadmin.jsp" flush="true"/>
</derivserv:userType>

<derivserv:userType role="D">
	<jsp:include page="/WEB-INF/jsp/common/submenudealer.jsp" flush="true"/>
</derivserv:userType>

<derivserv:userType role="C">
	<jsp:include page="/WEB-INF/jsp/common/submenuclient.jsp" flush="true"/>
</derivserv:userType>
	
		