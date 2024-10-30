<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" EnableViewState="false" AutoEventWireup="true" CodeFile="myaccount.aspx.cs" Inherits="myaccount" Title="Untitled Page" %>
<%@ Register TagPrefix="ucl" TagName="sidemenu" Src="Control/sidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="usersearchtab" Src="Control/usersearchtab.ascx" %>
<%@ Register TagPrefix="ucl" TagName="userconfigfetaures" Src="Control/userconfigfetaures.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
<ucl:sidemenu id="menu1" runat="server"></ucl:sidemenu>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
<ucl:usersearchtab id="searchusercont" runat="server"></ucl:usersearchtab>
    <div style="margin-left: 10px; margin-bottom: 12px; margin-right: 12px; background-color: #FFF9EC; margin-top: 0px;">
    &nbsp;&nbsp;<a href="default.aspx" class="dsort" title="Back to recipe homepage">Home</a>&nbsp;<span class="bluearrow">»</span>&nbsp; <span class="content2">You are here: My Account</span>
    </div>
    <div style="margin-left: 15px;"> 
    <table border="0" cellpadding="2" align="left" cellspacing="2" width="75%">
      <tr>
    <td width="68%">
    <fieldset><legend><asp:Label runat="server" id="lblusernameheader" EnableViewState="false" /></legend>
     <div style="padding-top: 8px; margin-bottom: 20px;">
     <asp:Label runat="server" id="lblyouarenotlogin" Visible="false" CssClass="content12" EnableViewState="false" />
     <asp:Panel ID="HideContentIfNotLogin" runat="server">
         <div style="margin-top: 5px; margin-bottom: 25px; margin-right: 25px;">
         <span class="content12">Welcome to your account manager and control panel setting page. From here you can edit and control your profile page settings.</span>
         <asp:Label runat="server" CssClass="content12" id="lbllastactivitymsg" EnableViewState="false" />
         </div>
         <div style="margin-top: 2px; margin-bottom: 4px; margin-right: 25px;">
          <asp:Label runat="server" CssClass="content12" id="lblupdatesettingsmsg" Visible="false" EnableViewState="false" />
         </div>
         <div style="margin-top: 5px;">
<table cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td width="33%">
        <div style="margin-top: 5px; margin-left: 6px; margin-bottom: 16px;">
        <div class="containermylinks" style="line-height: 21px;">
            <div style="padding-left: 3px; padding-top: 8px; padding-bottom: 3px;">
            <img src="images/link_icon.gif" align="absmiddle" />&nbsp;<span class="content3">My Links</span>
            </div>
            <div style="padding-right: 8px; padding-left: 8px; padding-top: 2px; line-height: 18px">
                <asp:Label runat="server" CssClass="content12" id="lblmyprofilelink" EnableViewState="false" /><br />
                <asp:Label runat="server" CssClass="content12" id="lbleditmyprofile" EnableViewState="false" /><br />
                <asp:Label runat="server" CssClass="content12" id="lblmycookbooklink" EnableViewState="false" /><br />
                <asp:Label runat="server" CssClass="content12" id="lblmyfriendslistlink" EnableViewState="false" /><br />
                <asp:Label runat="server" CssClass="content12" id="lblmypminbox" EnableViewState="false" /><br />    
                <asp:Label runat="server" CssClass="content12" id="lblviewallmysubmittedrecipe" EnableViewState="false" /><br />
                <asp:Label runat="server" CssClass="content12" id="lblviewallmypublishedarticle" EnableViewState="false" />             
            </div>
       </div>
     </div>

       <div style="margin-top: 5px; margin-left: 6px; margin-bottom: 16px;">
        <div class="containermylinks">
            <div style="padding-left: 3px; padding-top: 8px; padding-bottom: 3px;">
            <img src="images/friendlisticon_myaccount.gif" align="absmiddle" />&nbsp;<span class="content3">Last 5 Users Who Add Me</span>
            </div>
            <div style="padding-right: 8px; padding-left: 8px; padding-top: 2px;">
            <asp:Label cssClass="content2" runat="server" id="lblcountwhoaddmeinfriendslist" />
            <asp:Repeater id="WhoAddsMe" runat="server">
               <ItemTemplate>
                <div class="dcnt2">
                <img src="images/user-icon.gif" />&nbsp;<a class="content2" onmouseover="Tip('<b><%# Eval("Username")%> </b> add you as a friend<br>on:</b> <%# Eval("Date", "{0:M/d/yyyy}")%>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='userprofile.aspx?uid=<%# Eval("FriendID") %>'><%# Eval("Username")%></a>
                </div>
               </ItemTemplate>
          </asp:Repeater>
     </div>
       </div>
     </div>              
    </td>
    <td width="33%">
        <div style="margin-top: 5px; margin-left: 6px; margin-bottom: 16px;">
           <div class="containersitestat">
    <div style="padding-left: 3px; padding-top: 8px; padding-bottom: 3px;">
    <img src="images/stats_icon.gif" align="absmiddle" />&nbsp;<span class="content3">Site Statistics</span>
    </div>
    <div style="padding-right: 8px; padding-left: 8px; padding-top: 2px; line-height: 17px;">
     <asp:Label runat="server" id="lbltotalrecipe" EnableViewState="false" /><br />
     <asp:Label runat="server" id="lbltotalarticle" EnableViewState="false" /><br />
     <asp:Label runat="server" id="lbltotalrecipecomments" EnableViewState="false" /><br />
     <asp:Label runat="server" id="lbltotalarticlecomments" EnableViewState="false" /><br />
     <asp:Label runat="server" id="lbltotalsavedrecipeincookbook" EnableViewState="false" /><br />
     <asp:Label runat="server" id="lbltotaluserswhousecookbook" EnableViewState="false" /><br />
     <asp:Label runat="server" id="lbltotaluserswhousefriendslist" EnableViewState="false" /><br />
     <asp:Label runat="server" id="lbltotalprivatemessage" EnableViewState="false" />
    </div>
  </div>
        </div>
        
       <div style="margin-top: 5px; margin-left: 6px; margin-bottom: 16px;">
        <div class="containersitestat">
            <div style="padding-left: 3px; padding-top: 8px; padding-bottom: 3px;">
            <img src="images/stats_icon.gif" align="absmiddle" />&nbsp;<span class="content3">Members Statistics</span>
            </div>
            <div style="padding-right: 8px; padding-left: 8px; padding-top: 2px; line-height: 18px;">
            <asp:Label runat="server" id="lbltotalRegisteredUsers" EnableViewState="false" /><br />
            <asp:Label runat="server" id="lbltotaluserjoinedtoday" EnableViewState="false" /><br />
            <asp:Label runat="server" id="lbltotaluserjoininaweek" EnableViewState="false" /><br />
            <asp:Label runat="server" id="lbltotaluserjoinedinamonth" EnableViewState="false" /><br />
            </div>
       </div>
     </div> 
    </td>
    <td width="34%">
     <div style="margin-top: 5px; margin-left: 6px; margin-bottom: 16px;">
        <ucl:userconfigfetaures id="uprofilepanelconfig" runat="server"></ucl:userconfigfetaures>
     </div>
     
        <div style="margin-top: 5px; margin-left: 6px; margin-bottom: 16px;">
        <div class="containeruprofilepanel">
            <div style="padding-left: 3px; padding-top: 8px; padding-bottom: 3px;">
            <img src="images/tools_icon.gif" align="absmiddle" />&nbsp;<span class="content3">Current Page Layout Settings</span>
            </div>
            <div style="padding-right: 8px; padding-left: 8px; padding-top: 2px;">
                <div style="padding: 7px;">
                    <asp:dropdownlist id="uconfigturnonofflayoutpagesize" runat="server" cssClass="ddl" Width="90" AutoPostBack="false" onmouseover="Tip('<b>Turn On</b> - When you turn on<br>preferred layout and pagesize,<br>and you go to the category page,<br>select a layout and pagesize<br>from the dropdown menu<br>in the top right corner. It will be<br>save in your preferrence.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">
                    <asp:Listitem Value="1">Turn On</asp:Listitem>
                    <asp:Listitem Value="0">Turn Off</asp:Listitem>
                    </asp:dropdownlist>
                </div>
                <div style="padding: 7px;">
                    <asp:dropdownlist id="uconfigpreflayout" runat="server" cssClass="ddl" Width="130" AutoPostBack="false">
                    <asp:Listitem Value="1">Rows</asp:Listitem>
                    <asp:Listitem Value="2">Grid - 2 Columns</asp:Listitem>
                    <asp:Listitem Value="3">Grid - 3 Columns</asp:Listitem>
                    </asp:dropdownlist>
                </div>
                <div style="padding: 7px;">
                    <asp:dropdownlist id="uconfigprefpagesize" runat="server" cssClass="ddl" Width="170" AutoPostBack="false">
                    <asp:Listitem Value="10">10 Records Per Page</asp:Listitem>
                    <asp:Listitem Value="20">20 Records Per Page</asp:Listitem>
                    <asp:Listitem Value="30">30 Records Per Page</asp:Listitem>
                    <asp:Listitem Value="40">40 Records Per Page</asp:Listitem>
                    <asp:Listitem Value="50">50 Records Per Page</asp:Listitem>
                    </asp:dropdownlist>
                </div>
                <div style="padding: 5px;">
                  <asp:Button ID="Sconfigbutonlayout" runat="server" cssClass="submitadmin" OnClick="UpdatePreferredLayoutPageSize_Click" Text="Save Settings" onmouseover="Tip('<b>Turn On</b> - When you turn on<br>preferred layout and pagesize,<br>and you go to the category page,<br>select a layout and pagesize<br>from the dropdown menu<br>in the top right corner. It will be<br>save in your preferrence.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />&nbsp;<a class="thickbox content2" href="images/grid2columns_large.gif?keepThis=true&TB_iframe=true&height=600&width=750" target="_blank">Screenshot</a>
                </div>
            </div>
       </div>
     </div> 
    </td>
  </tr>
   <tr>
    <td width="33%">
        <div style="margin-top: 5px; margin-left: 6px; margin-bottom: 16px;">
        <div class="containermylinks" style="line-height: 21px;">
           <div style="padding-left: 3px; padding-top: 8px; padding-bottom: 3px;">
            <img src="images/cookbookicon_smll2.gif" align="absmiddle" />&nbsp;<span class="content3">Last 5 Users Who Saved My Recipe in Cook Book</span>
            </div>
            <div style="padding-left: 3px; padding-top: 3px; padding-bottom: 2px;">
            <asp:Label cssClass="content2" runat="server" id="lblcountuserswhosavedmyrecipe" />
            <asp:Repeater id="Last5UsersWhoSavedMyRecipe" runat="server">
               <ItemTemplate>
                <div class="dcnt2">
                <img src="images/user-icon.gif" />&nbsp;<a class="content2 thickbox" href="popupviewuserssavedrecipepublishedbyme.aspx?uid=<%# Eval("UID")%>&keepThis=true&TB_iframe=true&height=360&width=400" onmouseover="Tip('<b><%# Eval("Username")%> </b> added one of your published recipe<br>on:</b> <%# Eval("Date", "{0:M/d/yyyy}")%>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href="#"><%# Eval("Username")%></a>
                </div>
               </ItemTemplate>
          </asp:Repeater>
            </div>
       </div>
     </div>
    </td>
    <td width="33%">
        <div style="margin-top: 5px; margin-left: 6px; margin-bottom: 16px;">
           <div class="containersitestat">
            <div style="padding-left: 3px; padding-top: 8px; padding-bottom: 3px;">
                <img src="images/stats_icon.gif" align="absmiddle" />&nbsp;<span class="content3">My Statistics Counter</span>
            </div>
    <div style="padding-left: 3px; padding-top: 8px; padding-bottom: 3px;">
            <asp:Label runat="server" id="lblcountmysavedrecipe" EnableViewState="false" /><br />
            <asp:Label runat="server" id="lblcountmyfriends" EnableViewState="false" /><br />
            <asp:Label runat="server" id="lblpostedrecipecount" EnableViewState="false" /><br />
            <asp:Label runat="server" id="lblpostedarticlecount" EnableViewState="false" /><br />
            <asp:Label runat="server" id="lblcommentedrecipe" EnableViewState="false" /><br />
            <asp:Label runat="server" id="lblcommentarticle" EnableViewState="false" /><br />
  </div>
  </div>
  </div>
    </td>
    <td width="34%">
        <div style="margin-top: 5px; margin-left: 6px; margin-bottom: 16px;">
        <div class="containeruprofilepanel">
                    <div style="padding-left: 3px; padding-top: 8px; padding-bottom: 3px;">
            <img src="images/tools_icon.gif" align="absmiddle" />&nbsp;<span class="content3">Current Private Message Settings</span>
            </div>
            <div style="padding-left: 3px; padding-top: 1px; padding-bottom: 3px;">
                <div style="padding: 7px;">
                    <asp:dropdownlist id="uconfigreceivepm" runat="server" cssClass="ddl" Width="140" AutoPostBack="false" onmouseover="Tip('<b>Receive Private Message</b> - If you select yes, other user can send you a private message.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">
                    <asp:Listitem Value="1">Yes</asp:Listitem>
                    <asp:Listitem Value="0">No</asp:Listitem>
                    </asp:dropdownlist><br /><asp:Button ID="btnreceivepm" runat="server" cssClass="submitadmin" OnClick="UpdateReceivePM_Click" Text="Save" onmouseover="Tip('<b>Receive Private Message</b> - If you select yes, other user can send you a private message.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />
                </div>
                <div style="padding: 7px;">
                    <asp:dropdownlist id="uconfigreceivepmemailalert" runat="server" cssClass="ddl" Width="170" AutoPostBack="false" onmouseover="Tip('<b>Receive PM Email Alert</b> - If you select yes, you will<br>receive a notification email when someone send you a PM', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">
                    <asp:Listitem Value="1">Yes</asp:Listitem>
                    <asp:Listitem Value="0">No</asp:Listitem>
                    </asp:dropdownlist><br /><asp:Button ID="btnepmmailalert" runat="server" cssClass="submitadmin" OnClick="UpdateReceivePMEmailAlert_Click" Text="Save" onmouseover="Tip('<b>Receive PM Email Alert</b> - If you select yes, you will<br>receive a notification email when someone send you a PM', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />
                </div>
       </div>
       </div>
     </div> 
    </td>
  </tr>
</table>
         </div>
      </asp:Panel>
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

