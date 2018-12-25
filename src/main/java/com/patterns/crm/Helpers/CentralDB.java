package com.patterns.crm.Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Class.forName;

public class CentralDB {    // Singleton --> wzorzec projektowy
    static Connection conn = null;

    private CentralDB() {

    }

    public static Connection getDBconnextion() {
        try {
            if (conn == null){
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection("jdbc:mysql://localhost/crm?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","haslo");
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }
}
