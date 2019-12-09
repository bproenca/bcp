package bcp;

import java.sql.*;
import java.util.Properties;

public class App {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("java.vendor"));
    }

    private String database = "vhs4h179";
    private String username = "SYN4TDF_DEV";
    private String password = "Syn4Tdf_Dev_01";
    private String port = "30044";
    //fieldOpcoes.setText("?autocommit=false");

    public void run() {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Connection to HANA successful!");
            runQuery(conn);
        } else {
            System.out.println("Could not create Connection");
        }
    }

    private Connection getConnection() {
        Connection connection = null;


        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        //connectionProps.put("encrypt", "true");
        //connectionProps.put("validateCertificate", "false");

        try {
            connection = DriverManager.getConnection(
                    "jdbc:sap://" + database + ":"+ port +"/?autocommit=false", connectionProps);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    private void runQuery(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM \"SYN4TDF_DEV\".\"/SYN/CFOP\"\n");
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.println(rsmd.getColumnName(i) + " -> " + rsmd.getColumnType(i));
            }

        } catch (SQLException e) {
            System.err.println("Query failed!");
        }
    }
}
