package br.com.bcp.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckMetadata2 {
  public static void main(String[] args) throws Exception {
    Connection conn = getOracleConnection();
    System.out.println("Got Connection.");
    
    /*
    Statement st = conn.createStatement();
    st.executeUpdate("drop table survey;");
    st.executeUpdate("create table survey (id int,name varchar(30));");
    st.executeUpdate("insert into survey (id,name ) values (1,'nameValue')");
    */

    ResultSet rsColumns = null;
    DatabaseMetaData meta = conn.getMetaData();
    //rsColumns = meta.getColumns(null, null, "survey", null);
    rsColumns = meta.getColumns(null, null, "PEDIDOS", null);
    while (rsColumns.next()) {
      String columnName = rsColumns.getString("COLUMN_NAME");
      System.out.println("column name=" + columnName);
      String columnType = rsColumns.getString("TYPE_NAME");
      System.out.println("type:" + columnType);
      int size = rsColumns.getInt("COLUMN_SIZE");
      System.out.println("size:" + size);
      int nullable = rsColumns.getInt("NULLABLE");
      if (nullable == DatabaseMetaData.columnNullable) {
        System.out.println("nullable true");
      } else {
        System.out.println("nullable false");
      }
      int position = rsColumns.getInt("ORDINAL_POSITION");
      System.out.println("position:" + position);
      System.out.println();
    }

    //st.close();
    conn.close();
  }

  private static Connection getHSQLConnection() throws Exception {
    Class.forName("org.hsqldb.jdbcDriver");
    System.out.println("Driver Loaded.");
    String url = "jdbc:hsqldb:data/tutorial";
    return DriverManager.getConnection(url, "sa", "");
  }

  public static Connection getMySqlConnection() throws Exception {
    String driver = "org.gjt.mm.mysql.Driver";
    String url = "jdbc:mysql://localhost/demo2s";
    String username = "oost";
    String password = "oost";

    Class.forName(driver);
    Connection conn = DriverManager.getConnection(url, username, password);
    return conn;
  }

  public static Connection getOracleConnection() throws Exception {
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1522:xe";
    String username = "BCP";
    String password = "BCP";

    Class.forName(driver); // load Oracle driver
    Connection conn = DriverManager.getConnection(url, username, password);
    return conn;
  }
}