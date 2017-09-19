/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hasalp
 */
@WebServlet(name = "findWedding", urlPatterns = {"/findWedding.do"})
public class findWedding extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try{
            List guest_list = new ArrayList();
            //create connection
            DBConnection connection = new DBConnection();
            connection.connect();
            //get weddingID from form
            int weddingID = Integer.parseInt(request.getParameter("invitationID"));
            //get datas from database
            WeddingInfo info = new WeddingInfo(connection.setWedding(weddingID));
            int[][] table_data = connection.setTables(weddingID);
            //set session
            session.setAttribute("wedding_id", info.getWedding_id());
            session.setAttribute("bride_name", info.getBride_name());
            session.setAttribute("groom_name", info.getGroom_name());
            session.setAttribute("date", info.getDate());
            session.setAttribute("location", info.getLocation());
            session.setAttribute("table_data", table_data);
            //redirect
            RequestDispatcher dispatcher = request.getRequestDispatcher("wedding.jsp");
            dispatcher.forward(request, response);
            
        }catch(Exception e){
            session.setAttribute("error", "Wedding ID is wrong");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

}
