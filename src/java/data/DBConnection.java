/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hasalp
 */
public class DBConnection {
    private Connection conn=null;
    private final String DB = "jdbc:mysql://localhost:3306/wedding_reservation";
    private final String DB_UNAME = "root";
    private final String DB_PASS = "";
    private final int TABLE_ID = 0;
    private final int CHAIR_COUNT=1;
    
    public Connection connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(DB,DB_UNAME,DB_PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("MYSQL CONNECTION ERROR");
        }
        return conn;
    }
    
    public void disconnect() throws SQLException{
        if(conn != null){
            conn.close();
            System.out.println("Disconnected");  
        }
    }
    
    public List setWedding(int id){
        
        List wedding = new ArrayList();
        
        String sql = "SELECT * FROM weddings WHERE wedding_id="+id+";";
        
        if(conn == null){
            connect();
        }
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            rs.next();
            
            if(rs.getRow()==0){
                System.out.println("ID couldn't find");
            }else{
                wedding.add(rs.getInt("wedding_id"));
                wedding.add(rs.getString("bride_name"));
                wedding.add(rs.getString("groom_name"));
                wedding.add(String.valueOf(rs.getDate("date")));
                wedding.add(rs.getString("location"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wedding;
    }
    
    public int[][] setTables(int wedding_id){
        String sql = "SELECT table_id, chair_count FROM tables WHERE wedding_id="+wedding_id+";";
        int[][] table_data = null;
        try {
            int count = 0;
            Statement stmt = (Statement) conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //get row count
            rs.last();
            table_data = new int[rs.getRow()][2];
            rs.beforeFirst();
            
            while(rs.next()){
                table_data[count][TABLE_ID]=rs.getInt("table_id");
                table_data[count][CHAIR_COUNT]=rs.getInt("chair_count");
                count++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return table_data;
    }
    
    public List getGuests(int table_id) throws SQLException{       
        String sql = "SELECT name, surname, adult_count, child_count FROM guests WHERE table_id="+table_id+";";
        List guest_list = new ArrayList();
        
        connect();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                guest_list.add(rs.getString("name")+" "+rs.getString("surname")+" - "+rs.getInt("adult_count")+"/"+rs.getInt("child_count"));   
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        disconnect();
        
        return guest_list;
    }
    
    public int getGuestCount(int table_id) throws SQLException{
        String sql = "SELECT adult_count,child_count FROM guests WHERE table_id="+table_id+";";
        int guest_count=0;
        
        connect();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                guest_count += (rs.getInt("adult_count")+rs.getInt("child_count"));   
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        disconnect();
        
        return guest_count;
    }
    
    public boolean insertGuest(String name, String surname, int table_id, int adult_count, int child_count) throws SQLException{
        String sql = "INSERT INTO guests(name, surname, table_id, adult_count, child_count) VALUES('"+name+"','"+surname+"',"+table_id+","+adult_count+","+child_count+")";
                
        connect();
        try{
            Statement stmt = (Statement) conn.createStatement();
            stmt.execute(sql);
            disconnect();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            disconnect();
            return false;
        }
    }
}
