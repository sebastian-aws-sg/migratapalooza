<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" EnableViewState="false" AutoEventWireup="true" CodeFile="editrecipe.aspx.cs" Inherits="editrecipe" Title="Editting a Recipe" %>
<%@ Register TagPrefix="ucl" TagName="rsssidemenu" Src="Control/rsssidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="newestarticle" Src="Control/newestarticle.ascx" %>
<%@ Register TagPrefix="ucl" TagName="sidemenu" Src="Control/sidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="searchtab" Src="Control/searchtab.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
    <ucl:sidemenu id="menu1" runat="server"></ucl:sidemenu>
    <div style="clear: both;"></div>
    <ucl:newestarticle id="nart" runat="server"></ucl:newestarticle>
    <br />
    <ucl:rsssidemenu id="rsscont" runat="server"></ucl:rsssidemenu>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <ucl:searchtab id="searchcont" runat="server"></ucl:searchtab>
    <div style="margin-left: 10px; margin-right: 12px; background-color: #FFF9EC; margin-top: 0px;">
    &nbsp;&nbsp;<a href="default.aspx" class="dsort" title="Back to recipe homepage">Home</a><span class="bluearrow">»</span>&nbsp;<span class="content2">You are here: Editting Recipe Form</span>
    </div>
    <div style="margin-left: 120px;">
    <!--Begin Insert Recipe Form-->
        <asp:PlaceHolder id="PlaceHolder1" runat="server">
    <table border="0" cellpadding="2" align="center" cellspacing="2" width="80%">
      <tr>
    <td width="68%">
    <div style="padding: 2px; text-align: left; margin-left: 1px; margin-right: 26px;">
    <br />
    <asp:Label ID="lbvalenght" runat="server" Font-Bold="True" ForeColor="#C00000" Font-Names="Verdana" Visible="false" /> 
    </div>
    <fieldset><legend>Editing a Recipe</legend>
     <div style="padding-top: 20px;">
     <asp:Label runat="server" id="lblyouarenotlogin" Visible="false" CssClass="content12" EnableViewState="false" />
     <asp:Panel ID="HideContentIfNotLogin" Visible="false" runat="server">
     <div style="padding: 2px; margin-bottom: 15px; margin-left: 1px; margin-right: 26px;">
        <span class="content2">Fields mark with red asterisk (<span class="cred2">*</span>) are required.</span> 
    </div>
    <table border="0" cellpadding="2" align="center" cellspacing="2" width="60%">
     <tr>
        <td width="1%"><span class="content12">Author/Poster:</span></td>
        <td width="102%">
    <input type="hidden" id="Author" name="Author" size="25" class="textbox" runat="server" />
    <input type="hidden" id="Hits" name="Hits" size="25" class="textbox" runat="server" />
    <asp:Label ID="lblusername" runat="server" cssClass="cmaron" EnableViewState="false" />
    </td>
      </tr>
      <tr>
        <td width="26%"><span class="content12">Category:</span></td>
        <td width="74%">
   <asp:dropdownlist id="CategoryID" runat="server" cssClass="cselect" AutoPostBack="false"></asp:dropdownlist>&nbsp;<img src="images/help.gif" align="absmiddle" border="0" onmouseover="Tip('<b>Note:</b> If you dont want to change category, do not select anything.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />
    </td>
      </tr>
      <tr>
        <td width="26%"><div style="margin-top: 8px;"><span class="content12">Name:</span><span class="cred2">*</span></div></td>
        <td width="74%">
        <div style="margin-top: 8px;">
    <input type="text" id="Name" name="Name" class="txtinput" size="45" runat="server" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />
          <asp:RequiredFieldValidator runat="server"
            id="Recipename" ControlToValidate="Name"
            cssClass="cred2" errormessage="* Recipe Name:<br />"
            display="Dynamic" />
            </div>
    </td>
      </tr>
      <tr>
        <td width="26%" valign="top"><div style="margin-top: 8px;"><span class="content12">Ingredients:</span><span class="cred2">*</span></div></td>
        <td width="74%">
        <div style="margin-top: 8px;">
    <textarea runat="server" id="Ingredients" class="textbox" cols="80" rows="30" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />
          <asp:RequiredFieldValidator runat="server"
            id="RecIngred" ControlToValidate="Ingredients"
            cssClass="cred2" errormessage="* Ingredients:<br />"
            display="Dynamic" />
            </div>
    </td>
      </tr>
      <tr>
        <td width="26%" valign="top"><div style="margin-top: 12px;"><span class="content12">Instructions:</span><span class="cred2">*</span></div></td>
        <td width="74%">
        <div style="margin-top: 12px;">
    <textarea runat="server" id="Instructions" class="textbox" cols="80" rows="32" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />
          <asp:RequiredFieldValidator runat="server"
            id="RecInstruc" ControlToValidate="Instructions"
            cssClass="cred2" errormessage="* Instructions:<br />"
            display="Dynamic" />
            </div>
    </td>
      </tr>
              <tr>
        <td width="26%" valign="top"></td>
        <td width="74%">
<img src="<%=strRecipeImage%>" style="margin-top: 12px; border: solid 1px #A0BEE2; padding: 1px;" width="150" height="120" />
    </td>
      </tr>
        <tr>
        <td width="26%" valign="top"><span class="content12">Photo:<br />(Optional)</span></td>
        <td width="74%">
    <asp:FileUpload ID="RecipeImageFileUpload" runat="server" />&nbsp;<span class="content2"><br />Maximum Image size is 200 x 200 and less than 20,000 bytes.
    <br />Note: If you don't want to update the photo, just leave it blank.</span>
    </td>
      </tr>
      <tr>
        <td width="26%"></td>
        <td width="74%">
    <input type="text" class="textbox" ID="hd" name="hd" runat="server" style="visibility:hidden;">
    <br />
    <asp:Button runat="server" Text="Update" id="UpdateRec" cssClass="submitadmin" OnClick="Update_Recipe" />   
    </td>
      </tr>
    </table>
  </asp:Panel>
     </div>
    </fieldset>
    </td>
      </tr>
    </table>
    </asp:PlaceHolder>
    </div>
    <asp:Literal id="JSLiteral" runat="server"></asp:Literal>
    <!--End Insert Recipe Form-->
</asp:Content>

