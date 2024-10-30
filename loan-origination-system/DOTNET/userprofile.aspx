<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" EnableViewState="false" AutoEventWireup="true" CodeFile="userprofile.aspx.cs" Inherits="userprofile" Title="Untitled Page" %>
<%@ Register TagPrefix="ucl" TagName="sidemenu" Src="Control/sidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="usersearchtab" Src="Control/usersearchtab.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
<ucl:sidemenu id="menu1" runat="server"></ucl:sidemenu>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
<ucl:usersearchtab id="searchusercont" runat="server"></ucl:usersearchtab>
    <div style="margin-left: 10px; margin-right: 12px; background-color: #FFF9EC; margin-top: 0px;">
    &nbsp;&nbsp;<a href="default.aspx" class="dsort" title="Back to recipe homepage">Home</a>&nbsp;<span class="bluearrow">»</span>&nbsp; <span class="content2">You are here: User Profile Page</span>
    </div>
    <div style="margin-left: 15px; margin-top: 15px;">
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
        <div style="margin-top: 15px; margin-bottom:4px;">
        <img src="images/user_add.png" alt="add" />&nbsp;<asp:Label runat="server" CssClass="content2" Visible="false" id="lbladdtofriendslist" EnableViewState="false" /><asp:LinkButton id="LinkButtonAddfriendLogin" CausesValidation="false" runat="server" CssClass="content2" Visible="false" OnClick="Add_Friend" EnableViewState="false" />
        </div>
        <div style="margin-top: 3px; margin-bottom:12px;">
        <img src="images/sendpm_icon.gif" />&nbsp;<asp:Label runat="server" CssClass="content2" id="lblsendpm" EnableViewState="false" />
        </div>        
        <asp:Panel ID="MyFriendsListPanel" runat="server" Visible="false">     
        <div style="margin-top: 10px; margin-bottom:6px;">
        <img src="images/iconuser2.gif" alt="user" />&nbsp;<span class="content12" style="color:Black;"><b>My Friends</b> <asp:Label runat="server" CssClass="content2" ForeColor="#000000" id="lblmyfriendscount" EnableViewState="false" /></span>
        <br />
        <asp:Label runat="server" CssClass="content2" id="lblnofriends" EnableViewState="false" />
        <asp:Repeater id="MyFriendsList" runat="server" EnableViewState="false">
       <ItemTemplate>
        <div class="dcnt2">
        <img src="images/user-icon.gif" />&nbsp;<a class="content2" title="View <%# Eval("Username") %> profile." onmouseover="Tip('<b>User name:</b> <%# Eval("Username") %><br><b>Full name:</b> <%# Eval("FirstName") %> <%# Eval("LastName") %><br><b>Country:</b> <%# Eval("Country") %><br><b>Profile views:</b> (<%# Eval("Hits") %>)<br><b>Date joined:</b> <%# Eval("DateJoined", "{0:M/d/yyyy}")%><br><b>Last visit:</b> (<%# Eval("LastVisit") %>)<br>Added to Friends List on: <%# Eval("Date", "{0:M/d/yyyy}")%><br><b>Photo:</b><br><img src=&quot;UserImages/<%# Eval("Photo")%>&quot; width=&quot;160&quot; height=&quot;140&quot;>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='<%# Eval("FriendID", "userprofile.aspx?uid={0}") %>'><%# Eval("Username")%></a>
        </div>
       </ItemTemplate>
      </asp:Repeater>
        </div> 
        </asp:Panel>       
        <asp:Panel ID="MyCookBookPanel" runat="server" Visible="false">   
        <div style="margin-top: 10px;">
        <img src="images/cookbookicon2.gif" alt="user" /> <span class="content12" style="color:Black;"><b>My CookBook</b> <asp:Label runat="server" CssClass="content2" ForeColor="#000000" id="lblmycookbookcount" EnableViewState="false" /></span>
        <br />
        <asp:Label runat="server" CssClass="content2" id="lblnosavedrecipe" EnableViewState="false" />
        <asp:Repeater id="SavedUserCookBookProfile" runat="server" EnableViewState="false">
       <ItemTemplate>
        <div class="dcnt2">
        <span class="cyel">&raquo;</span>&nbsp;<a class="content2" title="View <%# Eval("RecipeName") %> recipe" onmouseover="Tip('<b>Recipe Name:</b> <%# Eval("RecipeName") %><br><b>Author:</b> <%# Eval("Author") %><br><b>Category:</b> <%# Eval("Category") %><br><b>Hits:</b> (<%# Eval("Hits") %>)<br><b>Rating:</b> (<%# Eval("Rating") %>)<br><b>Comments:</b> (<%# Eval("Comments") %>)<br>Added to the CookBook on: <%# Eval("Date", "{0:M/d/yyyy}")%><br><b>Photo:</b><br><img src=&quot;RecipeImageUpload/<%# Eval("RecipeImage")%>&quot; width=&quot;150&quot; height=&quot;120&quot;>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='<%# Eval("RecipeID", "recipedetail.aspx?id={0}") %>'><%# Eval("RecipeName")%></a>
        </div>
       </ItemTemplate>
      </asp:Repeater>
        </div>
        </asp:Panel>       
        </td>
        <td width="60%" valign="top">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Username:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblusername" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Joined On:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lbljoined" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Full Name:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblfullname" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>DOB:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lbldob" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>City:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblcity" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>State/Province:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblstate" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Country:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblcountry" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Profile Views:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblprofileviews" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Last Visit:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lbllastlogin" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Last Updated:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lbllastupdate" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Submitted Recipe:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblpostedrecipecount" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Published Article:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblpostedarticlecount" EnableViewState="false" /></div></td>
    </tr>
     <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Comments Recipe:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblcommentedrecipe" EnableViewState="false" /></div></td>
    </tr>
     <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Comments Article:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblcommentarticle" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Favorite Food1:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblfavfood1" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Favorite Food2:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblfavfood2" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Favorite Food3:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblfavfood3" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>Website:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblwebsite" EnableViewState="false" /></div></td>
    </tr>
    <tr>
        <td width="35%"><div style="margin-top: 9px;"><span class="contentpro"><b>About Me:</b></span></div></td>
        <td width="60%" align="left"><div style="margin-top: 9px;"><asp:Label runat="server" CssClass="content12" id="lblaboutme" EnableViewState="false" /></div></td>
    </tr>

  </tr>
</table>
        </td>
</tr>
    </table>
     </div>
     <div style="float: right; margin-right: 85px; margin-top: 20px;">
     <asp:Panel ID="PanelLast5recipeByUser" Visible="false" runat="server">
         <div class="containertop5userrecipe">     
               <b>Published Recipe</b>
               <asp:Repeater id="Last5RecipeByUser" runat="server">
               <ItemTemplate>
                <div class="dcnt2" style="margin-top: 3px;">
                <span class="cyel">&raquo;</span>&nbsp;<a class="content2" onmouseover="Tip('<b>Recipe Name:</b> <%# Eval("RecipeName") %><br><b>Category:</b> <%# Eval("Category") %><br><b>Hits:</b> (<%# Eval("Hits") %>)<br><b>Photo:</b><br><img src=&quot;RecipeImageUpload/<%# Eval("RecipeImage")%>&quot; width=&quot;150&quot; height=&quot;120&quot;>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='<%# Eval("ID", "recipedetail.aspx?id={0}") %>'><%# Eval("RecipeName")%></a>
                </div>       
               </ItemTemplate>
              </asp:Repeater>
              <div style="margin-top: 4px; margin-left: 3px;">
               <asp:Label runat="server" CssClass="content2" id="lblmorerecipebyuser" Visible="false" EnableViewState="false" />
              </div>         
         </div>
         </asp:Panel>
         <div style="clear:both"><br /></div>
         <asp:Panel ID="PanelLast5ArticleByUser" Visible="false" runat="server">
            <div class="containertop5userarticle">
               <b>Published Article</b>
               <asp:Repeater id="Last5ArticleByUser" runat="server">
               <ItemTemplate>
                <div class="dcnt2" style="margin-top: 3px;">
                <span class="cyel">&raquo;</span>&nbsp;<a class="content2" onmouseover="Tip('<b>Title:</b> <%# Eval("Title") %><br><b>Category:</b> <%# Eval("Category") %>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='<%# Eval("ID", "articledetail.aspx?aid={0}") %>'><%# Eval("ShortTitle")%></a>
                </div>       
               </ItemTemplate>
              </asp:Repeater>
              <div style="margin-top: 4px; margin-left: 3px;">
               <asp:Label runat="server" CssClass="content2" id="lbllast5articlebyuser" Visible="false" EnableViewState="false" />
              </div>  
             </div>
         </asp:Panel>
         </asp:Panel>
     </div>
    </fieldset>
    </td>
      </tr>
    </table>
    </div>
    <!--End Insert Recipe Form-->
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

