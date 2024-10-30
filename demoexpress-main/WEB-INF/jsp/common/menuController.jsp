<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/derivServ.tld" prefix="derivserv" %>
<div class="sub_menu" id="sub_menu"></div>
<derivserv:userType role="T">
	<jsp:include page="/WEB-INF/jsp/common/menuadmin.jsp" flush="true"/>
</derivserv:userType>

<derivserv:userType role="D">
	<jsp:include page="/WEB-INF/jsp/common/menudealer.jsp" flush="true"/>
</derivserv:userType>

<derivserv:userType role="C">
	<jsp:include page="/WEB-INF/jsp/common/menuclient.jsp" flush="true"/>
</derivserv:userType>
