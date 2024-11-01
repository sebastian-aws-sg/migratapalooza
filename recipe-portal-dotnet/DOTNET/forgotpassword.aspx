<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" EnableViewState="false" AutoEventWireup="true" CodeFile="forgotpassword.aspx.cs" Inherits="forgotpassword" Title="Password recovery" %>
<%@ Register TagPrefix="ucl" TagName="sidemenu" Src="Control/sidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="searchtab" Src="Control/searchtab.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
<ucl:sidemenu id="menu1" runat="server"></ucl:sidemenu>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
<ucl:searchtab id="searchcont" runat="server"></ucl:searchtab>
    <div style="margin-left: 10px; margin-bottom: 12px; margin-right: 12px; background-color: #FFF9EC; margin-top: 0px;">
    &nbsp;&nbsp;<a href="default.aspx" class="dsort" title="Back to recipe homepage">Home</a>&nbsp;<span class="bluearrow">�</span>&nbsp; <span class="content2">You are here: Password recovery page</span>
    </div> 
    <div style="margin-left: 15px;">   
    <table border="0" cellpadding="2" align="left" cellspacing="2" width="75%">
      <tr>
    <td width="68%">
    <fieldset><legend><asp:Label runat="server" id="passrecoveryheader" EnableViewState="false" /></legend>
     <div style="padding-top: 1px;">
     <div style="margin-top: 18px; margin-bottom: 8px;">
     <span class="content12">
     If you forgot your password, enter the email address you used when you register to recover it.
     <br />
     We will send you an email containing your username and password.
     </span>
     </div>
     <span class="content12">Email:</span>
     <input type="text" id="useremailrecoverpass" name="useremailrecoverpass" onkeyup="LostPassKeyDown()" class="txtinput" size="30" runat="server" />&nbsp;<input type="button" id="passsubbutton" value="Submit" disabled="disabled" class="submitadmin" onClick="sendRequestLostPasswordTextPost()" />
          <asp:RequiredFieldValidator runat="server"
          id="RequiredFieldEmail" ControlToValidate="useremailrecoverpass" SetFocusOnError="true"
          cssClass="cred2"
          ErrorMessage = "Please enter an email address."
          display="Dynamic"> </asp:RequiredFieldValidator>
             <asp:RegularExpressionValidator id="RegularExpressionLostPassEmail" runat="server"
            ControlToValidate="useremailrecoverpass" SetFocusOnError="true"
            ValidationExpression="\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*"
            Display="Static"
            cssClass="cred2">
 Invalid email address. Email address must be a valid format.
 </asp:RegularExpressionValidator>
     <div style="margin-top: 12px; margin-bottom: 2px;">
     <div id="idforresultslostpass"></div>
     </div>
     </div>
    </fieldset>
    </td>
      </tr>
    </table>
    </div>
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
</asp:Content>

