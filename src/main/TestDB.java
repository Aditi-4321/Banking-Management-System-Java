package main;

import java.sql.Connection;
import dao.DBConnection;

public class TestDB {

    public static void main(String[] args) {

        Connection conn = DBConnection.getConnection();

        if(conn != null) {
            System.out.println("Connection Working...");
        }
    }
}
