package com.todo;
import com.todo.util.DatabaseConnection;

import java.sql.SQLException;
import java.sql.Connection;
public class Main{
    public static void main(String[] args){
    DatabaseConnection db = new DatabaseConnection();
    try{
        Connection cn=db.getDBConnection();
        System.out.println("database connected Successfully");
    }
    catch(SQLException e){
        System.out.println("database connected Failure");

    }


}
        }