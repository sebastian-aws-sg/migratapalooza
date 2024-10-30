<%@ Control Language="C#" AutoEventWireup="true" CodeFile="Upcomingevents.ascx.cs" Inherits="Upcomingevents" %>
<asp:Panel ID="PanelListViewSide" runat="server" Visible="false">
<div style="margin-left: 4px; margin-right: 4px;"> 
    <fieldset><legend><span style="font-size: 12px;">Upcoming Events</span></legend>
    <div style="margin-top: 10px;">
     <asp:Label runat="server" id="lbcountupcomingevent" CssClass="content2" EnableViewState="false" />
     <asp:Repeater id="UpComingEventsControl" runat="server">     
        <ItemTemplate>
            <div class="dcnt2" style="margin-top: 2px; width: 150px;" id="sideupcomingeventID<%# Eval("EventsID") %>">
             <img src='images/<%# Eval("EventType") %>.gif' height="12" width="12" border="0" onmouseover="Tip('<b>Type:</b> <%# Eval("EventType") %>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />&nbsp;<a class="content2" href="javascript:void(0);" onclick="javascript: showUpcomingEventSideMenu(<%# Eval("EventsID") %>,'top','right')" onmouseover="Tip('<b>Type:</b> <%# Eval("EventType") %> on <%# CustomDateFormat(Eval("StartDate"))%> <%# Eval("AppMeetingStartTime") %> - <%# Eval("AppMeetingEndTime") %><br>View <%# Eval("EventTitle") %> detail.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()"><%# Eval("EventTitle") %></a>
            </div>        
       </ItemTemplate>
    </asp:Repeater>
     </fieldset>
    <br />
    <fieldset><legend><span style="font-size: 12px;">Recent Events</span></legend>
    <div style="margin-top: 10px;">
     <asp:Label runat="server" id="lblrecentevents" CssClass="content2" EnableViewState="false" />
     <asp:Repeater id="RecentEvents" runat="server">     
        <ItemTemplate>
            <div class="dcnt2" style="margin-top: 2px; width: 150px;" id="siderecenteventID<%# Eval("EventsID") %>">
             <img src='images/<%# Eval("EventType") %>.gif' height="12" width="12" border="0" onmouseover="Tip('<b>Type:</b> <%# Eval("EventType") %>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />&nbsp;<a class="content2" href="javascript:void(0);" onclick="javascript: showRecentEventSideMenu(<%# Eval("EventsID") %>,'top','right')" onmouseover="Tip('<b>Type:</b> <%# Eval("EventType") %> on <%# CustomDateFormat(Eval("StartDate"))%> <%# Eval("AppMeetingStartTime") %> - <%# Eval("AppMeetingEndTime") %><br>View <%# Eval("EventTitle") %> detail.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()"><%# Eval("EventTitle") %></a>
            </div>         
       </ItemTemplate>
    </asp:Repeater>
    </div>
    </fieldset>
</div>
</asp:Panel>