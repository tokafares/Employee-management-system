package employee.management.system;

import java.sql.*;

public class Conn {
    
    public Statement s;
    public Connection c;
    public static Conn instance = null;

    private Conn() {
        try {
            // Load driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeemanagementsystem", "root", "codeforinterview");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Conn getInstance() {
        if (instance == null) {
            instance = new Conn();
        }
        return instance;
    }
}

