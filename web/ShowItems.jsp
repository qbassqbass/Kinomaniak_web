<%-- 
    Document   : ShowItems
    Created on : 2014-06-02, 23:20:34
    Author     : Jakub
--%>

<%@page import="kinomaniak_objs.*"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Movies</title>
    </head>
    <body>
        <jsp:useBean id="db" scope="session" type="kinomaniak_database.DBConnector" />
        Movies:
        <% //db.connect();
            ArrayList<Object> arr = db.load("Movie");
            for(Object o : arr){
                if(o instanceof Movie){ 
                    Movie m = (Movie)o;%>
                    <h1><%= m.getId() %>. <%= m.getName() %></h1>
                    <%= m.getGenre() %><br />
                    <%= m.getRating() %><br />
                    <i><%= m.getDesc() %></i><br /><br />
               <% }
            } %>
            Products:
            <% arr = db.load("Product");
            for(Object o : arr){
                if(o instanceof Product){
                    Product p = (Product)o;%>
                    <h1><%= p.getId() %>. <%= p.getName() %> (Type: <%= p.getType() %>)</h1>
                    Price: <%= p.getPrice() %><br />
                    Count: <%= p.getCount() %> <br /><br />
                    
                    
            <%    }
            } %>
    </body>
</html>
