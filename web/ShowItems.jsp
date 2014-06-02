<%-- 
    Document   : ShowItems
    Created on : 2014-06-02, 23:20:34
    Author     : Jakub
--%>

<%@page import="kinomaniak_objs.Movie"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:useBean id="db" scope="session" class="kinomaniak_database.DBConnector" />
        <% db.connect();
            ArrayList<Object> arr = db.load("Movie");
            for(Object o : arr){
                if(o instanceof Movie){ 
                    Movie m = (Movie)o;%>
                    <h1><%= m.getName() %></h1><br />
                    <%= m.getGenre() %><br />
                    <%= m.getRating() %><br />
                    <i><%= m.getDesc() %></i><br /><br />
               <% }
            } %>
    </body>
</html>
