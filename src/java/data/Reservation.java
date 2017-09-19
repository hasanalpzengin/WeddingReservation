
package data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hasalp
 */
public class Reservation extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //session to get error
        HttpSession session = request.getSession();
        int adult_count = 0;
        int child_count = 0;
        try {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            int table_id = Integer.parseInt(request.getParameter("table_ID"));
            if(!request.getParameter("adult_count").isEmpty()){
                adult_count = Integer.parseInt(request.getParameter("adult_count"));
            }
            if(!request.getParameter("child_count").isEmpty()){
                child_count = Integer.parseInt(request.getParameter("child_count"));
            }
            HashMap<Integer,Integer> available_chair_count = (HashMap<Integer, Integer>) session.getAttribute("available_chair_count");
            //check is chair amount enough to user
            if(available_chair_count.get(table_id)<(adult_count+child_count)){
                session.setAttribute("error", 111);
            }else{
                DBConnection connect = new DBConnection();
                connect.insertGuest(name, surname, table_id, adult_count, child_count);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("wedding.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException ex) {
            session.setAttribute("error", "there was an error while inserting reservation, contact with wedding owner.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
        
    }

}
