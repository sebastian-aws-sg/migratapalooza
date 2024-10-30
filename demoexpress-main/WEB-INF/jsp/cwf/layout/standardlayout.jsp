<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@ include file = "/WEB-INF/jsp/cwf/common/include.jsp"%>

<html:html>

<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<title><tiles:getAsString name="title"/></title> 

<meta http-equiv="Content-Style-Type" content="text/css">
<link href="<%=request.getContextPath()%>/static/cwf/css/WebDirect_1.css" rel="stylesheet" type="text/css"/>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/static/cwf/jscript/rollover.js"></script>

</head>
<body marginwidth="0" marginheight="0" topmargin="0" leftmargin="0">
<table border="0" width="100%" cellspacing="0" height="100%">
   <tr valign="top">
      <td height="15%">
         <tiles:insert attribute="header" />
      </td>
   </tr>

   <tr valign="top">
      <td>
         <table width="96%" align="center" border="0" cellspacing="0" cellpadding="0">
            <tr valign="top">
               <td colspan="2">
                  <tiles:insert attribute='breadcrumbs'/>
               </td>
            </tr>
            <tr>
               <td colspan="2">
                  <tiles:insert attribute='messages' />
               </td>
            </tr>
            <tr>
               <td>
                  <br><h1><tiles:getAsString name="title"/></h1>
               </td>
            </tr>
            <tr>
               <td valign="top" align="left">
                  <tiles:insert attribute='body'/>
               </td>
            </tr>
         </table>
      </td>
   </tr>
   <tr valign="bottom">
      <td>
         <tiles:insert attribute="footer" />
      </td>
   </tr>
</table>

</body>

</html:html>
