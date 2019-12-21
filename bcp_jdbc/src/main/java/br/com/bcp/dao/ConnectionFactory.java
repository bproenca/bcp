/*
 * Created on 18/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.bcp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {


    public static final String ORACLE = "oracle";
    public static final String POSTGRESQL = "postgresql";

    private static Connection getConnection(String database) throws SQLException {
        String url;
        String driver;
        String usuario;
        String senha;

        try {

            if (ORACLE.equalsIgnoreCase(database)) {
                url = "jdbc:oracle:thin:@localhost:1522:XE";
                driver = "oracle.jdbc.OracleDriver";
                usuario = "BCP";
                senha = "BCP";
            } else if (POSTGRESQL.equalsIgnoreCase(database)) {
                url = "jdbc:postgresql://172.17.0.2:5432/bcpdb";
                driver = "org.postgresql.Driver";
                usuario = "bcp";
                senha = "bcp";
            } else {
                throw new RuntimeException("BCP - Unknown database");
            }
            Class.forName(driver);
            System.out.println("Conectando ao banco");
            return DriverManager.getConnection(url, usuario, senha);
        } catch (ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static PedidoDAO buildDAO(String database) throws SQLException {
        Connection con = getConnection(database);
        PedidoDAO dao;
        if (ORACLE.equalsIgnoreCase(database)) {
            dao = new OraclePedidoDAO(con);
        } else if (POSTGRESQL.equalsIgnoreCase(database)) {
            dao = new PostgrePedidoDAO(con);
        } else {
            throw new RuntimeException("BCP - Unknown database");
        }
        return dao;
    }
}