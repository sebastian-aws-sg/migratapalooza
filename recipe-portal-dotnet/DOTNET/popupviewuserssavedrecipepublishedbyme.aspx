<%@ Page Language="C#" AutoEventWireup="true" EnableViewState="false" CodeFile="popupviewuserssavedrecipepublishedbyme.aspx.cs" Inherits="popupviewuserssavedrecipepublishedbyme" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head id="Head1" runat="server">
    <title>Top 25 recipe published by me saved by users in Cook Book</title>
        <link href="CSS/cssreciaspx.css" rel="stylesheet" type="text/css" /> 
</head>
<body>
    <form id="form1" runat="server">
    <div>
     <asp:Label runat="server" id="lblyouarenotlogin" Visible="false" CssClass="content12" EnableViewState="false" />
     <asp:Panel ID="HideContentIfNotLogin" runat="server">
        <div style="margin-bottom: 8px; margin-top: 8px;">
        <span class="content2"><b>Top 25 recipe published by me saved by users in Cook Book</b></span>
     </div>
        <asp:Repeater id="UserSavedRecipeCookBookPublishedByme" runat="server">     
        <ItemTemplate>
            <div class="dcnt2" style="margin-top: 2px;">
             <span class="content2"><a title="View this recipe" href="recipedetail.aspx?id=<%# Eval("RecipeID")%>" class="content2" target="_blank"><%# Eval("RecipeName")%></a> added in Cook Book on: <%# Eval("Date", "{0:M/d/yyyy}")%></span>
            </div>         
       </ItemTemplate>
    </asp:Repeater>
    </asp:Panel>
    </div>
    </form>
</body>
</html>
