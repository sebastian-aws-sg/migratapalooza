<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"      prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>

<html:html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Modify</title>
<LINK rel="stylesheet" href="/mcx/static/css/mca.css" type="text/css">
<script language="JavaScript" src="/mcx/static/js/mca_setup_term_details.js"></script>
</head>
<body>
<html:form action="/AdminISDAModify" method="post" enctype="multipart/form-data" >
<html:hidden name ="TermForm" property="imgPrsnt" />
<div style="width:100%">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td class="blue_header border_comment">Modify</td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="45%" valign="bottom" class="comment_head_txt">MCA Type:</td>
        <td width="20%" valign="bottom" class="comment_head_txt">Category:</td>
        <td width="35%" valign="bottom" class="comment_head_txt">Term:</td>
      </tr>
      <tr>
        <td valign="top" class="comment_top_txt"><bean:write name="TermForm" property="transaction.ISDATmpltNm" /></td>
        <td valign="top" class="comment_top_txt"><bean:write name="TermForm" property="transaction.catgyNm" /></td>
        <td valign="top" class="comment_top_txt"><bean:write name="TermForm" property="transaction.termNm" /></td>
      </tr>
     
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="3" class="enter_comm"></td>
      </tr>
      <tr><td>
    </td></tr>
      <tr>
       <td width="100%" height="100%"> 
            <jsp:include page="../common/rte/rtfEditor.jsp" />
            <html:hidden property="amendmentValue" />
		</td>
      </tr>
        <tr>
        <td colspan="3"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr class="comment_spacer">
            <td width="72%">&nbsp;</td>
            <td width="13%"><input class="enroll" type="button" name="Submit" value="MODIFY" onclick="post_amendment()" /></td>
            <td width="1%">&nbsp;</td>
            <td width="14%"><input align="right" class="enroll" type="button" name="Submit" value="CANCEL" onclick="cancel('<bean:write name="TermForm" property="transaction.tmpltId" />')"/></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="20px" class="comment_spacer"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
</table>
</div>
</html:form>
</body>
</html:html>
