<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" EnableViewState="false" AutoEventWireup="true" CodeFile="addrecipe.aspx.cs" Inherits="addrecipe" Title="Submitting Recipe Form" %>
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
    &nbsp;&nbsp;<a href="default.aspx" class="dsort" title="Back to recipe homepage">Home</a>&nbsp;<span class="bluearrow">»</span>&nbsp;<a href="submitrecipecategory.aspx" class="dsort" title="Back to recipe submit category listing">Recipe Submit Category Listing</a>&nbsp;<span class="bluearrow">»</span>&nbsp; <span class="content2">You are here: Recipe Submission Form</span>
    </div>
    <div style="margin-left: 120px; margin-top: 20px;">
    <!--Begin Insert Recipe Form-->
    <asp:PlaceHolder id="PlaceHolder1" runat="server">
    <table border="0" cellpadding="2" align="center" cellspacing="2" width="80%">
      <tr>
    <td width="68%">
    <div style="padding: 2px; text-align: left; margin-left: 1px; margin-right: 26px;">
    <asp:Label ID="lbvalenght" runat="server" Font-Bold="True" ForeColor="#C00000" Font-Names="Verdana" Visible="false" /> 
    </div>
    <fieldset><legend>Recipe Submission Form</legend>
     <div style="margin-top: 20px;">
     <asp:Label ID="lblwarning" runat="server" cssClass="content12" Visible="false" EnableViewState="false" />
     <asp:Panel ID="Panel3" runat="server">
         <div style="padding: 2px; margin-bottom: 15px; margin-left: 1px; margin-right: 26px;">
        <span class="content2">Fields mark with red asterisk (<span class="cred2">*</span>) are required.</span> 
    </div>
    <table border="0" cellpadding="2" align="center" cellspacing="2" width="60%"> 
     <tr>
        <td width="1%"><span class="content12">Username:</span></td>
        <td width="102%">
    <input type="hidden" id="Author" name="Author" size="25" class="textbox" runat="server" />
    <asp:Label ID="lblusername" runat="server" cssClass="cmaron" EnableViewState="false" />
    </td>
      </tr>
      <tr>
        <td width="26%"><span class="content12">Category:</span></td>
        <td width="74%">
<asp:dropdownlist id="CategoryID" runat="server" cssClass="cselect" AutoPostBack="false"></asp:dropdownlist>
    </td>
      </tr>
      <tr>
        <td width="26%"><div style="margin-top: 8px;"><span class="content12">Name:</span><span class="cred2">*</span></div></td>
        <td width="74%">
        <div style="margin-top: 8px;">
    <input type="text" id="Name" name="Name" class="txtinput" size="50" runat="server" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />
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
    <textarea runat="server" id="Ingredients" class="textbox" textmode="multiline" cols="75" rows="30" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />
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
        <div style="margin-top: 8px;">
    <textarea runat="server" id="Instructions" class="textbox" textmode="multiline" cols="75" rows="32" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />
          <asp:RequiredFieldValidator runat="server"
            id="RecInstruc" ControlToValidate="Instructions"
            cssClass="cred2" errormessage="* Instructions:<br />"
            display="Dynamic" />
            </div>
    </td>
      </tr>
        <tr>
        <td width="26%" valign="top"><span class="content12">Photo:<br />(Optional)</span></td>
        <td width="74%">
    <asp:FileUpload ID="RecipeImageFileUpload" runat="server" />&nbsp;<span class="content2"><br />Maximum Image size is 200 x 200 and less than 20,000 bytes.</span>
    </td>
      </tr>
      <tr>
        <td width="26%"></td>
        <td width="74%" align="left">
    <input type="text" class="textbox" ID="hd" name="hd" runat="server" style="visibility:hidden;">
    <div style="text-align: left;">
    <asp:Button runat="server" Text="Submit" id="AddComments" cssClass="submitadmin" OnClick="Add_Recipe" />
    </div> 
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

