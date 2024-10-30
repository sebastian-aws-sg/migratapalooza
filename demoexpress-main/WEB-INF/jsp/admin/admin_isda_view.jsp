<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>

<html:html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>View Industry Published Term</title>
<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/primary.js">
</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td class="blue_header border_comment">View Term</td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="34%" valign="bottom" class="comment_head_txt">MCA Type:</td>
        <td width="33%" valign="bottom" class="comment_head_txt">Category:</td>
        <td width="33%" valign="bottom" class="comment_head_txt">Term:</td>
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
        <td height="7px" colspan="3" class="comment_spacer"><img src="/mcx/static/images/spacer.gif"></td>
      </tr>
      <tr>
        <td width="15px" height="30px" class="comment_spacer"><img src="/mcx/static/images/spacer.gif"></td>
        <td align="left" width="99%">
			<bean:write name="TermForm" property="transaction.termVal" filter="false" />
		</td>
        <td width="15px" class="comment_spacer"><img src="/mcx/static/images/spacer.gif"></td>
      </tr>
      <tr>
        <td height="10px" class="comment_spacer" colspan="3"><img src="/mcx/static/images/spacer.gif"></td>
      </tr>
	  <tr>
        <td colspan="3"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr class="comment_spacer">
            <td width="72%">&nbsp;</td>
            <td width="13%"></td>
            <td width="1%">&nbsp;</td>
            <td width="14%"><input align="right" class="enroll" type="button" name="Submit" value="CLOSE" onclick="close_win()"/></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html:html>
