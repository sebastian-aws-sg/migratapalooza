<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" EnableViewState="false" AutoEventWireup="true" CodeFile="editprofile.aspx.cs" Inherits="editprofile" Title="Editing Profile" %>
<%@ Register TagPrefix="ucl" TagName="sidemenu" Src="Control/sidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="searchtab" Src="Control/searchtab.ascx" %>
<%@ Register TagPrefix="ucl" TagName="CalendarDatePicker" Src="Control/DatePicker.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
    <ucl:sidemenu id="menu1" runat="server"></ucl:sidemenu>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <ucl:searchtab id="searchcont" runat="server"></ucl:searchtab>
    <div style="margin-left: 10px; margin-right: 12px; background-color: #FFF9EC; margin-top: 0px;">
    &nbsp;&nbsp;<a href="default.aspx" class="dsort" title="Back to recipe homepage">Home</a>&nbsp;<span class="bluearrow">»</span>&nbsp; <span class="content2">You are here: Editting profile page</span>
    </div>
    <div style="margin-left: 15px;">
    <!--Begin Insert Recipe Form-->
    <asp:PlaceHolder id="PlaceHolder1" runat="server">
    <table border="0" cellpadding="2" align="center" cellspacing="2" width="95%">
      <tr>
    <td width="68%">
    <div style="padding: 2px; text-align: left; margin-left: 1px; margin-right: 26px;">
    <br />
    <span class="content2">Fields mark with red asterisk (<span class="cred2">*</span>) are required</span>
    <asp:Label ID="lbvalenght" runat="server" Font-Bold="True" ForeColor="#C00000" Font-Names="Verdana" Visible="false" /> 
    </div>
    <fieldset><legend><asp:Label ID="lbllegendheader" runat="server" EnableViewState="false" /></legend>
     <div style="padding-top: 1px;">
     <asp:Label ID="lblWarningMessage" runat="server" CssClass="content12" Visible="true" EnableViewState="false" />
     <asp:Panel ID="HideFormIfLogin" runat="server">
     <div style="margin-bottom: 16px; margin-top: 12px;"><span class="content2"><b>Note:</b>&nbsp;If you don't want to change your password or email address. Just leave the default value on the password and email fields.</span></div>
    <table border="0" cellpadding="2" align="left" cellspacing="2" width="80%">
            <tr>
        <td width="15%" valign="top"><span class="content12">Password:</span><span class="cred2">*</span></td>
        <td width="74%" valign="top">
    <input type="text" id="Password1" name="Password1" class="txtinput" size="20" runat="server" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />&nbsp;
           <asp:RequiredFieldValidator runat="server"
          id="RequiredFieldPassword1" ControlToValidate="Password1" SetFocusOnError="true"
          cssClass="cred2"
          ErrorMessage = "Please enter a password."
          display="Dynamic"> </asp:RequiredFieldValidator>
             <asp:RegularExpressionValidator id="RegularExpressionPassword1" runat="server"
            ControlToValidate="Password1" SetFocusOnError="true"
            ValidationExpression="\w{6,12}"
            Display="Static"
            cssClass="cred2">
 Password must be at least 6 characters long and 12 characters maximun, and should only contain AlphaNumeric.
 </asp:RegularExpressionValidator>  
    </td>
      </tr>
      
             <tr>
        <td width="15%" valign="top"><span class="content12">Repeat Password:</span><span class="cred2">*</span></td>
        <td width="74%" valign="top">
    <input type="text" id="Password2" name="Password2" class="txtinput" size="20" runat="server" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />&nbsp;
               <asp:RequiredFieldValidator runat="server"
          id="RequiredFieldPassword2" ControlToValidate="Password2" SetFocusOnError="true"
          cssClass="cred2"
          ErrorMessage = "Please re-type the password."
          display="Dynamic"> </asp:RequiredFieldValidator>
             <asp:RegularExpressionValidator id="RegularExpressionPassword2" runat="server"
            ControlToValidate="Password2" SetFocusOnError="true"
            ValidationExpression="\w{6,12}"
            Display="Static"
            cssClass="cred2">
 Password must be at least 6 characters long and 12 characters maximun, and should only contain AlphaNumeric.
 </asp:RegularExpressionValidator> 
    </td>
      </tr>
      
               <tr>
        <td width="15%" valign="top"><span class="content12">Email:</span><span class="cred2">*</span></td>
        <td width="74%" valign="top">
    <input type="text" id="Email" name="Email" class="txtinput" size="25" runat="server" onkeyup="EmailKeyDown()" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />&nbsp;&nbsp;<span id="progressemail"></span>&nbsp;<span id="idforresultsemail"></span>
        <br />
    <input type="button" id="subbutemail" value="Verify email" title="Check if email already in used. Cannot use the same email." disabled="disabled" class="submitadmin" onClick="sendRequestEmailTextPost()" />&nbsp;
          <asp:RequiredFieldValidator runat="server"
          id="RequiredFieldEmail" ControlToValidate="Email" SetFocusOnError="true"
          cssClass="cred2"
          ErrorMessage = "Please an email address."
          display="Dynamic"> </asp:RequiredFieldValidator>
             <asp:RegularExpressionValidator id="RegularExpressionEmail" runat="server"
            ControlToValidate="Email" SetFocusOnError="true"
            ValidationExpression="\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*"
            Display="Static"
            cssClass="cred2">
 Invalid email address. Email address must be a valid format.
 </asp:RegularExpressionValidator>
 <div style="margin-top: 12px;"></div>
    </td>
      </tr>
      
                   <tr>
        <td width="15%" valign="top"><span class="content12">Firstname:</span><span class="cred2">*</span></td>
        <td width="74%" valign="top">
    <input type="text" id="Firstname" name="Firstname" class="txtinput" size="20" runat="server" onkeypress="return LetterOnly(event)" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />&nbsp;
          <asp:RequiredFieldValidator runat="server"
          id="RequiredFieldFirstname" ControlToValidate="Firstname" SetFocusOnError="true"
          cssClass="cred2"
          ErrorMessage = "Please enter your firstname."
          display="Dynamic"> </asp:RequiredFieldValidator>
            <asp:RegularExpressionValidator id="RegularExpressionFirstname" runat="server"
            ControlToValidate="Firstname" SetFocusOnError="true"
            ValidationExpression="^[a-zA-Z ]+$"
            Display="Static"
            cssClass="cred2">
 Firstname should be alphabet and not contain illegal characters.
 </asp:RegularExpressionValidator> 
    </td>
      </tr>
      
         <tr>
        <td width="15%" valign="top"><span class="content12">Lastname:</span><span class="cred2">*</span></td>
        <td width="74%" valign="top">
    <input type="text" id="Lastname" name="Lastname" class="txtinput" size="20" runat="server" onkeypress="return LetterOnly(event)" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />&nbsp;
              <asp:RequiredFieldValidator runat="server"
          id="RequiredFieldLastname" ControlToValidate="Lastname" SetFocusOnError="true"
          cssClass="cred2"
          ErrorMessage = "Please enter your lasttname."
          display="Dynamic"> </asp:RequiredFieldValidator>
          <asp:RegularExpressionValidator id="RegularExpressionLastname" runat="server"
            ControlToValidate="Lastname" SetFocusOnError="true"
            ValidationExpression="^[a-zA-Z ]+$"
            Display="Static"
            cssClass="cred2">
 Lastname should be alphabet and not contain illegal characters.
 </asp:RegularExpressionValidator> 
    </td>
      </tr>
      
         <tr>
        <td width="15%" valign="top"><span class="content12">City/Town:</span></td>
        <td width="74%" valign="top">
    <input type="text" id="City" name="City" class="txtinput" size="20" runat="server" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />&nbsp;
    <br /><br />
    </td>
      </tr>
      
        <tr>
        <td width="15%" valign="top"><span class="content12">State/Province:</span></td>
        <td width="74%" valign="top">
    <input type="text" id="State" name="State" class="txtinput" size="20" runat="server" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />&nbsp;
    <br /><br />
    </td>
      </tr>
      
       <tr>
        <td width="15%" valign="top"><span class="content12">Country:</span><span class="cred2">*</span></td>
        <td width="74%" valign="top">
    <asp:DropDownList id="Country" runat="server" cssClass="ddl" AutoPostBack="false">
    </asp:DropDownList>
    <br /><br />
    </td>
      </tr>
      
        <tr>
        <td width="15%" valign="top"><span class="content12">DOB:</span><span class="cred2">*</span></td>
        <td width="74%" valign="top">
        <ucl:CalendarDatePicker ID="Date1" runat="server" />
    </td>
      </tr>
      
         <tr>
        <td width="15%" valign="top"><span class="content2">Fav food 1:</span></td>
        <td width="74%" valign="top">
    <input type="text" id="FavoriteFoods1" name="FavoriteFoods1" class="txtinput" size="25" runat="server" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />&nbsp;
    <br /><br />
    </td>
      </tr>
      
         <tr>
        <td width="15%" valign="top"><span class="content12">Fav food 2:</span></td>
        <td width="74%" valign="top">
    <input type="text" id="FavoriteFoods2" name="FavoriteFoods2" class="txtinput" size="25" runat="server" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />&nbsp;
    <br /><br />
    </td>
      </tr>
      
         <tr>
        <td width="15%" valign="top"><span class="content12">Fav food 3:</span></td>
        <td width="74%" valign="top">
    <input type="text" id="FavoriteFoods3" name="FavoriteFoods3" class="txtinput" size="25" runat="server" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />&nbsp;
    <br /><br />
    </td>
      </tr>
      
        <tr>
        <td width="15%" valign="top"><span class="content12">Newsletter:</span><span class="cred2">*</span></td>
        <td width="74%" valign="top">
    <asp:dropdownlist id="Newsletter" runat="server" cssClass="ddl" AutoPostBack="false">
    <asp:Listitem Value="1" selected>Subscription Yes</asp:Listitem>
    <asp:Listitem Value="0">Subscription No</asp:Listitem>
    </asp:dropdownlist>
    <br /><br />
    </td>
      </tr>
      
       <tr>
        <td width="15%" valign="top"><span class="content12">Allow contact:</span><span class="cred2">*</span></td>
        <td width="74%" valign="top">
    <asp:dropdownlist id="ContactMe" runat="server" cssClass="ddl" AutoPostBack="false">
    <asp:Listitem Value="1" selected>Allow other users to email me</asp:Listitem>
    <asp:Listitem Value="0">Don't allow other users to email me</asp:Listitem>
    </asp:dropdownlist>
    <br /><br />
    </td>
      </tr>
      
        <tr>
        <td width="15%" valign="top"><span class="content12">Website:</span></td>
        <td width="74%" valign="top">
    <input type="text" id="Website" name="Website" class="txtinput" size="35" runat="server" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />&nbsp;
    </td>
      </tr>
          
      <tr>
        <td width="15%" valign="top"><span class="content2">About me:</span></td>
        <td width="74%" valign="top">
    <textarea runat="server" id="AboutMe" class="textbox" textmode="multiline" cols="60" rows="10" onFocus="this.style.backgroundColor='#FFFCF9'" onBlur="this.style.backgroundColor='#ffffff'" />
    </td>
      </tr>

        <tr>
        <td width="15%"><span class="content12">Photo:<br />(Optional)</span></td>
        <td width="74%">
        <div style="margin-top: 9px;">
        <asp:Image ID="userimageedit" Width="180" Height="130" runat="server"/>
        </div>
    <asp:FileUpload ID="UserImageFileUpload" runat="server" />&nbsp;<span class="content2"><br />Maximum Image size is 200 x 200 and less than 20,000 bytes.
    <br />
    <b>Note:</b> If you don't want to update your photo, just leave it blank.
    </span>
    </td>
      </tr>
      <tr>
        <td width="15%"></td>
        <td width="74%">
    <input type="text" class="txtinput" ID="hd" name="hd" runat="server" style="visibility:hidden;">
    <br />
    <asp:Button runat="server" Text="Submit" id="AddComments" cssClass="submitadmin" OnClick="Update_User" />
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

