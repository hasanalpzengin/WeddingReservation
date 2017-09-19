<%-- 
    Document   : RegisterWedding
    Created on : Sep 14, 2017, 8:05:03 PM
    Author     : hasalp
--%>

<%@page import="java.util.List"%>
<%@page import="data.DBConnection"%>
<%@page import="data.DBConnection"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="wedding.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css?family=Josefin+Sans" rel="stylesheet">
        <link href="main.css" rel="stylesheet" type="text/css"/>
        <title>
            <%
                out.print(session.getAttribute("bride_name"));
                out.print("&");
                out.print(session.getAttribute("groom_name"));
                out.print("'s Wedding");
            %>
        </title>
    </head>
    <body>
        <div class="banner">
            <h2>Welcome to <% out.print(session.getAttribute("bride_name")+" & "+session.getAttribute("groom_name")+"'s Wedding"); %></h2>
        </div>
        <img src="images/wedding.png" alt="wedding" class="image"/>
        <div class="weddingArea">
            <h3>Wedding Info</h3>
                    <table>
                        <tr>
                            <td>Bride Name : </td>
                            <td><% out.print(session.getAttribute("bride_name")); %></td>
                        </tr>
                        <tr>   
                            <td>Groom Name : </td>
                            <td><% out.print(session.getAttribute("groom_name")); %></td>
                        </tr>
                        <tr>   
                            <td>Wedding ID : </td>
                            <td><% out.print(session.getAttribute("wedding_id")); %></td>
                        </tr>
                        <tr>   
                            <td>Wedding Date : </td>
                            <td><% out.print(session.getAttribute("date")); %></td>
                        </tr>
                        <tr>   
                            <td>Wedding Location : </td>
                            <td><% out.print(session.getAttribute("location")); %></td>
                        </tr>
                    </table>
        </div>
            <%
                DBConnection connection = new DBConnection();
                int[][] table_data = (int[][])session.getAttribute("table_data");
                List guests;
                HashMap<Integer,Integer> available_chair_count = new HashMap<Integer, Integer>();
                //start tablearea div
                out.print("<div class='tableArea'>");
                //create each table
                for(int table=0; table<table_data.length; table++){
                    System.out.println(table);
                    guests = connection.getGuests(table_data[table][0]);
                    int guest_count = table_data[table][1]-connection.getGuestCount(table_data[table][0]);
                    available_chair_count.put(table_data[table][0], guest_count);
                    out.print(
                        "<div class='table'>"+
                                "<h4 class='tableID'>Table ID: " + table_data[table][0] + "</h4>" +
                                "<h4 class='chairAmount'>Available Chair: "+guest_count+"</h4><p>"
                    );
                    for(int count_guest = 0; count_guest<guests.size(); count_guest++){
                        out.println(guests.get(count_guest)+"<br>");
                    }
                    out.print("</p></div>");
                }
                out.print("</div>");
                session.setAttribute("available_chair_count", available_chair_count);
            %>
            <div class="reservationArea">
                <form id="Reservation" action="Reservation.do" method="POST">
                    <h3>Reservation Information</h3>
                    <table>
                        <tr>
                            <td>Name: </td>
                            <td><input class="form" type="text" name="name" value="" /></td>
                        </tr>
                        <tr>
                            <td>Surname: </td>
                            <td><input class="form" type="text" name="surname" value="" /></td>
                        </tr>
                        <tr>
                            <td>Table ID: </td>
                            <td><input class="form" type="number" name="table_ID" id="table_id"></td>
                        </tr>
                        <tr>
                            <td>Adult Count: </td>
                            <td><input class="form" type="number" name="adult_count" id="adult_count"></td>
                        </tr>
                        <tr>
                            <td>Child Count: </td>
                            <td><input class="form" type="number" name="child_count" id="child_count"></td>
                        </tr>
                    </table>
                    <input type="submit" value="Add" id="addButton" />
                </form>

            </div>
            
            <script>
                var error = <% out.println(session.getAttribute("error"));%>;
                if(parseInt(error) == 111){
                    alert("Selected table don't has enough chair to your reservation");
                <% session.removeAttribute("error"); %>
                }
            </script>
    </body>
</html>
