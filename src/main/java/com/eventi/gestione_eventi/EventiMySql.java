package com.eventi.gestione_eventi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.PreparableStatement;

public class EventiMySql {

    private static final String connectionUrl = "jdbc:mysql://localhost:3306/event_database";
    private static final String dbUser = "root";
    private static final String dbPasswd = "threadementi";


    // QUERRYs
    private static final String querryList = "SELECT * FROM events";
    private static final String querryCreate = "INSERT INTO events(name, seats, max_seats) VALUES (?, ?, ?)"; 
    private static final String querryRemove = "DELETE FROM events WHERE name=?";
    private static final String querryBook = "UPDATE events SET seats=seats-? WHERE name=?";
    private static final String querryAdd = "UPDATE events SET max_seats=max_seats+? WHERE name=?";


    public EventiMySql(){
        try{
        } catch (Exception e){

        }
    }

    private Connection connectDatabase() {
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(connectionUrl, dbUser, dbPasswd);
        } catch (SQLException e ) {
            System.out.println(e);
        }
        return conn;
    }

    private void disconnectDatabase( Connection conn ) {
        if(conn == null){
            return;
        }
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void getList(){

    }

    public void eventCreate(String name, Integer max_seats){
        Connection conn = connectDatabase();
        if(conn == null) return;
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(querryCreate);
            stmt.setString(1, name);
            stmt.setInt(2, max_seats);
            stmt.setInt(3, max_seats);
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            // print e
            if(conn!=null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    //print e
                }
            }
        }
    }

    public static void /*TEST*/ main(String[] args) {
        var sql = new EventiMySql();
        var conn = sql.connectDatabase();
        sql.eventCreate("che fine ha fatto mike bongiorno?", 1000);
    }
}
