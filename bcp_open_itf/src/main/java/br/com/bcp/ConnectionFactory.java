package br.com.bcp;/*
 * Created on 18/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";

    private static final String driver = "oracle.jdbc.OracleDriver";
    //private static final String driver = "oracle.driver.jdbc.OracleDriver";

    private static final String usuario = "BCP";

    private static final String senha = "BCP";

    public static Connection getConnection() throws SQLException {
	try {
	    Class.forName(driver);
	    System.out.println("Conectando ao banco");
	    return DriverManager.getConnection(url, usuario, senha);
	} catch (ClassNotFoundException e) {
	    throw new SQLException(e.getMessage());
	}
    }
}