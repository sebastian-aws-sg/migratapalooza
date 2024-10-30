<%@ Page Language="C#" AutoEventWireup="true" CodeFile="popupuserprofile.aspx.cs" Inherits="popupuserprofile" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>User profile</title>
    <script type="text/javascript" src="js/rcipejs.js"></script>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/CheckUserNameEmailAjax.js"></script>
    <script type="text/javascript" src="js/validator.js"></script>
    <script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
    <script type="text/javascript" src="js/alertbox.js"></script>
    <script type="text/javascript" src="js/thickbox-compressed.js"></script>
    <link href="CSS/cssreciaspx.css" rel="stylesheet" type="text/css" /> 
   <link href="CSS/thickbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="js/wz_tooltip.js"></script>
    <form id="form1" runat="server">
    <div style="margin-left: 25px; margin-right: 25px; margin-top: 15px;">
    <asp:Label runat="server" CssClass="content12" id="lbladdfriendsuccessmsg" Visible="false" EnableViewState="false" />
    <table border="0" cellpadding="2" align="center" cellspacing="2" width="97%">
      <tr>
    <td width="68%">
    <fieldset><legend><asp:Label runat="server" id="lblusernameheader" EnableViewState="false" /></legend>
     <div style="padding-top: 1px;">
     <asp:Label runat="server" id="lblyouarenotauthorizedtoview" Visible="false" CssClass="content12" EnableViewState="false" />
     <asp:Panel ID="PanelIsProfilePublicOrPrivate" Visible="false" runat="server">
    <table border="0" cellpadding="4" align="left" cellspacing="2" width="60%">
      <tr>
        <td width="40%" valign="top">
        <div style="margin-top: 9px;">
        <asp:Image ID="userimage" Width="180" Height="130" runat="server"/>
        </div>
        <div style="margin-top: 15px; margin-bottom:12px;">
        <img src="images/iconaddfriend.gif" alt="add" />&nbsp;<asp:Label runat="server" CssClass="content2" Visible="false" id="lbladdtofriendslist" EnableViewState="false" /><asp:LinkButton id="LinkButtonAddfriendLogin" runat="server" CssClass="content2" Visible="false" OnClick="Add_Friend" EnableViewState="false" />
        </div> 
        
        <asp:Panel ID="MyFriendsListPanel" runat="server" Visible="false">     
        <div style="margin-top: 15px; margin-bottom:22px;">
        <img src="images/iconuser.gif" alt="user" /> <span class="content12" style="color:Black;"><b>My Friends</b> <asp:Label runat="server" CssClass="content2" ForeColor="#000000" id="lblmyfriendscount" EnableViewState="false" /></span>
        <br />
        <asp:Label runat="server" CssClass="content2" id="lblnofriends" EnableViewState="false" />
        <asp:Repeater id="MyFriendsList" runat="server" EnableViewState="false">
       <ItemTemplate>
        <div class="dcnt2">
        <span class="cyel">&raquo;</span> <a class="content2" title="View <%# Eval("Username") %> profile." onmouseover="Tip('<b>User name:</b> <%# Eval("Username") %><br><b>Full name:</b> <%# Eval("FirstName") %> <%# Eval("LastName") %><br><b>Country:</b> <%# Eval("Country") %><br><b>Profile views:</b> (<%# Eval("Hits") %>)<br><b>Date joined:</b> <%# Eval("DateJoined", "{0:M/d/yyyy}")%><br><b>Last visit:</b> (<%# Eval("LastVisit") %>)<br>Added to Friends List on: <%# Eval("Date", "{0:M/d/yyyy}")%><br><b>Photo:</b><br><img src=&quot;UserImages/<%# Eval("Photo")%>&quot; width=&quot;160&quot; height=&quot;140&quot;>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='<%# Eval("FriendID", "popupuserprofile.aspx?uid={0}") %>'><%# Eval("Username")%></a>
        </div>
       </ItemTemplate>
      </asp:Repeater>
        </div> 
        </asp:Panel> 
        
        <asp:Panel ID="MyCookBookPanel" runat="server" Visible="false">   
        <div style="margin-top: 18px;">
        <img src="images/cookbook.gif" alt="user" /> <span class="content12" style="color:Black;"><b>My CookBook</b> <asp:Label runat="server" CssClass="content2" ForeColor="#000000" id="lblmycookbookcount" EnableViewState="false" /></span>
        <br />
        <asp:Label runat="server" CssClass="content2" id="lblnosavedrecipe" EnableViewState="false" />
        <asp:Repeater id="SavedUserCookBookProfile" runat="server" EnableViewState="false">
       <ItemTemplate>
        <div class="dcnt2">
        <span class="cyel">&raquo;</span> <a class="content2" title="View <%# Eval("RecipeName") %> recipe" onmouseover="Tip('<b>Recipe Name:</b> <%# Eval("RecipeName") %><br><b>Author:</b> <%# Eval("Author") %><br><b>Category:</b> <%# Eval("Category") %><br><b>Hits:</b> (<%# Eval("Hits") %>)<br><b>Rating:</b> (<%# Eval("Rating") %>)<br><b>Comments:</b> (<%# Eval("Comments") %>)<br>Added to the CookBook on: <%# Eval("Date", "{0:M/d/yyyy}")%><br><b>Photo:</b><br><img src=&quot;RecipeImageUpload/<%# Eval("RecipeImage")%>&quot; width=&quot;150&quot; height=&quot;120&quot;>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='<%# Eval("RecipeID", "printrecipe.aspx?id={0}") %>'><%# Eval("RecipeName")%></a>
        </div>
       </ItemTemplate>
      </asp:Repeater>
        </div>
        </asp:Panel>
        
        </td>
        <td width="60%" valign="top">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>Username:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblusername" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>Joined On:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lbljoined" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>Full Name:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblfullname" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>DOB:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lbldob" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>City:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblcity" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>State/Province:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblstate" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>Country:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblcountry" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>Profile Views:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblprofileviews" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>Last Visit:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lbllastlogin" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>Last Updated:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lbllastupdate" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>Published Recipe:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblpostedrecipecount" EnableViewState="false" /></div></td>
    </tr>
        <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Published Article:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblpostedarticlecount" EnableViewState="false" /></div></td>
    </tr>
     <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>Comments Recipe:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblcommentedrecipe" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>Favorite Food1:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblfavfood1" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>Favorite Food2:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblfavfood2" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>Favorite Food3:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblfavfood3" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>Website:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblwebsite" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="content12"><b>About Me:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblaboutme" EnableViewState="false" /></div></td>
    </tr>

  </tr>
</table>
        </td>
</tr>
    </table>
    </asp:Panel>
     </div>
    </fieldset>
    </td>
      </tr>
    </table>
    
    </div>
    </form>
</body>
</html>
