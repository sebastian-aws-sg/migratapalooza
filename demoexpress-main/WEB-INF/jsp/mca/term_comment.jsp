
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>

<html:html>
<head>
<title>Post Comment</title>
<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/mca_wizard.js"></script>
<script language="JavaScript" src="/mcx/static/js/rte/rte_codesweep.js"></script>
</head>
<script>window.onscroll=scrollHeightWidth;</script>
<body>
<html:form action="/post_comment" method="post">
<html:hidden name="TermForm" property="tmpltType"/>
<html:hidden name="TermForm" property="tmpltID"/>
<html:hidden name="TermForm" property="transaction.catgyId"/>
<html:hidden name="TermForm" property="transaction.catgySqId"/>
<html:hidden name="TermForm" property="transaction.termId"/>
<html:hidden name="TermForm" property="transaction.termSqId"/>
<html:hidden name="TermForm" property="transaction.amndtStCd"/>
<html:hidden name="TermForm" property="transaction.termValId"/>
<html:hidden name="TermForm" property="transaction.termTextId"/>
<html:hidden name="TermForm" property="actingDealer" />
<html:hidden name="TermForm" property="tmpltLocked" />
<html:hidden name="TermForm" property="frmScr"/>
<div style="width:627;overflow:hidden">
<table width="627" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td class="blue_header border_comment">Post Comment/View Comment History</td>
  </tr>
  <tr>
    <td><table width="627" border="0" cellpadding="0" cellspacing="0" >
      <tr>
		<logic:equal name="TermForm" property="frmScr" value="MW1" >
        <td width="20%" valign="bottom" class="comment_head_txt">Dealer :</td>
		</logic:equal>
		<logic:equal name="TermForm" property="frmScr" value="MW2" >
        <td width="20%" valign="bottom" class="comment_head_txt">Counterparty :</td>
		</logic:equal>
		<logic:equal name="TermForm" property="frmScr" value="NEG" >
				<logic:equal name="TermForm" property="actingDealer" value="true" >
			        <td width="20%" valign="bottom" class="comment_head_txt">Counterparty :</td>
				</logic:equal>
				<logic:notEqual  name="TermForm" property="actingDealer" value="true" >
			        <td width="20%" valign="bottom" class="comment_head_txt">Dealer :</td>
				</logic:notEqual>
		</logic:equal>
        <td valign="bottom" class="comment_head_txt">MCA Type:</td>
        <td valign="bottom" class="comment_head_txt">Category:</td>
        <td valign="bottom" class="comment_head_txt">Term:</td>
      </tr>
      <tr>
        <td valign="top" class="comment_top_txt"><script>document.write(getCompanyName('<bean:write name="TermForm" property="userCompany" filter="false" />'));</script></td>
        <td valign="top" class="comment_top_txt"><bean:write name="TermForm" property="ISDATmpltNm" /></td>
        <td valign="top" class="comment_top_txt"><bean:write name="TermForm" property="transaction.catgyNm" /></td>
        <td valign="top" class="comment_top_txt"><bean:write name="TermForm" property="transaction.termNm" /></td>
      </tr>
    </table></td>
  </tr>
  <logic:notEqual name="TermForm" property="userInd" value="V" >
  <tr>
    <td><table width="627" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="7px" colspan="3" class="comment_spacer"><img src="/mcx/static/images/spacer.gif"></td>
      </tr>
      <tr>
        <td colspan="3" class="enter_comm">Enter Comment:</td>
      </tr>
      <tr>
        <td width="15px" class="comment_spacer"><img src="/mcx/static/images/spacer.gif"></td>
        <td class="comment_txt"><html:textarea property="commentTxt" style="width:597px; height:120px;" /></td>
        <td width="15px" class="comment_spacer"><img src="/mcx/static/images/spacer.gif"></td>
      </tr>
      <tr>
        <td height="10px" class="comment_spacer" colspan="3"><img src="/mcx/static/images/spacer.gif"></td>
      </tr>
	  <tr>
        <td colspan="3"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr class="comment_spacer">
            <td width="72%">&nbsp;</td>
            <td width="13%"><input class="enroll" type="button" name="Submit" value="POST" onclick="post_comment()"/></td>
            <td width="1%">&nbsp;</td>
            <td width="14%"><input align="right" class="enroll" type="button" name="Submit" value="CANCEL" onclick="cancel()"/></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  </logic:notEqual>
  <bean:define id="showHistory" value="true" />
  <logic:equal name="TermForm" property="tmpltType" value="C" >
  	<logic:notEqual name="TermForm" property="lockUsrInd" value="L" >
  		<% showHistory = "false"; %>
  	</logic:notEqual>
  </logic:equal>
  <logic:equal name="TermForm" property="tmpltType" value="E" >
  	<logic:notEqual name="TermForm" property="userInd" value="V" >
  		<% showHistory = "false"; %>
  	</logic:notEqual>
  </logic:equal>
  

  <% if(showHistory == "true") { %>
	  <logic:notEmpty name="TermForm" property="transaction.cmntList" >
	  <tr>
		<td>
			<table width="627" border="0" cellpadding="0" cellspacing="0">
		  <tr>
			<td colspan="3" class="enter_comm">Comment History:</td>
		  </tr>
		  <logic:iterate id="comments" name="TermForm" property="transaction.cmntList" >
		  <tr>
			<td width="15px" class="comment_spacer"><img src="/mcx/static/images/spacer.gif"></td>
			<td style="border:#A3BDCE solid 1px">
			<div class="comment_history_txt" style="word-break:break-all; width=597px ">Comment by <bean:write name="comments" property="rowUpdtName" /> on <bean:write name="comments" property="rowUpdtDt" /><br></div>
			<div class="comment_his_txt" style="width:597px; height:100px; "><textarea class="history_border" cols="115"   readonly><bean:write name="comments" property="cmntTxt" /></textarea></div></td>
			<td width="15px" class="comment_spacer"><img src="/mcx/static/images/spacer.gif"></td>
			</tr>
		  <tr>
			<td height="10px" class="comment_spacer" colspan="3"><img src="/mcx/static/images/spacer.gif"></td>
		  </tr>
		  </logic:iterate>
			</table> 
	  </td>
	  </tr>
	  </logic:notEmpty>
	   <% } %>
  <logic:equal name="TermForm" property="userInd" value="V" >
          <tr align="right" class="comment_spacer">
            <td><input align="right" class="enroll" type="button" name="Submit" value="CLOSE" onclick="self.close()"/></td>
       </tr>
  </logic:equal>
</table>
</div>
</html:form>
</body>
<script>
window.returnValue = "true";
</script>
</html:html>
