package bcp;

import java.sql.*;
import java.util.Properties;

public class App {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("java.vendor"));
        new App().run();
    }

    //private String database = "vhnwa751";
    //private String port = "30015";
    //private String username = "SYN4TDF_DEV1";
    //private String password = "Syn4tdf_dev_01";
    //connectionProps.put("keyStore", trustStorePath);
    //connectionProps.put("keyStorePassword", trustStorePassword);
    //connectionProps.put("keyStoreType", "JKS");


    private String database = "vhs4h179";
    private String username = "SYN4TDF_DEV";
    private String password = "Syn4Tdf_Dev_01";
    private String port = "30044";
    private String trustStorePassword = "bcp123";
    private String trustStorePath = "/dados/wks/keystore-bcp.jks";

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
        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        connectionProps.put("encrypt", "true");
        connectionProps.put("validateCertificate", "true");
        connectionProps.put("trustStore", trustStorePath);
        connectionProps.put("trustStorePassword", trustStorePassword);
        connectionProps.put("trustStoreType", "JKS");

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
            ResultSet rs = stmt.executeQuery("SELECT * FROM \"SYN4TDF_DEV\".\"/SYN/CFOP\"\n");
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
            System.err.println("Query failed!");
        }
    }
}
