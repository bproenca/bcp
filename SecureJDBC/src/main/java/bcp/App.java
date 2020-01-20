package bcp;

import java.sql.*;
import java.util.Properties;

public class App {
   
    private String database = "vhs4h179";
    private String username = "";
    private String password = "";
    private String port = "30044";
    private String trustStorePassword = "changeit";
    private String trustStorePath = "C:\\Program Files\\Java\\jdk-11.0.5\\lib\\security\\cacerts";

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("java.vendor"));
        new App().run();
    }

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
        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        connectionProps.put("encrypt", "true");
        //connectionProps.put("validateCertificate", "true");
        //connectionProps.put("trustStore", trustStorePath);
        //connectionProps.put("trustStorePassword", trustStorePassword);
        //connectionProps.put("trustStoreType", "JKS");
        
        Connection connection = null;
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
            ResultSet rs = stmt.executeQuery("select now() from dummy;");
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.println(rsmd.getColumnName(i) + " -> " + rsmd.getColumnTypeName(i));
            }

            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    System.out.print(rs.getObject(i) + " | ");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Query failed!");
        }
    }
}
