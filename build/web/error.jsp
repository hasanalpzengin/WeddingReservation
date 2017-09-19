<%--
    Document   : error
    Created on : Sep 16, 2017, 11:22:53 AM
    Author     : hasalp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <style>
        *{
            text-align: center;
            color: #FFFFFF;
        }
    </style>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
        <meta http-equiv="refresh" content="10;URL=index.html">
        <link href="main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Error</h1>
        <% 
            out.println("<h1>"+session.getAttribute("error")+"</h1>");
            out.println("<h2>"+"You will be redirected to <a href='index.html'>main page</a> in 5 seconds"+"</h2>");
            session.removeAttribute("error");
        %>
    </body>
</html>
