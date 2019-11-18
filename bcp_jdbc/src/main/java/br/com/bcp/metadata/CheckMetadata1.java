package br.com.bcp.metadata;

import java.sql.*;

public class CheckMetadata1 {

    public static void main(String[] args) throws Throwable {
        String url;
        String driver;
        String usuario;
        String senha;

        url = "jdbc:oracle:thin:@localhost:1522:xe";
        driver = "oracle.jdbc.OracleDriver";
        usuario = "BCP";
        senha = "BCP";
        Class.forName(driver);
        System.out.println("Conectando ao banco");
        final Connection connection = DriverManager.getConnection(url, usuario, senha);

        PreparedStatement preparedStatement = connection.prepareStatement("select * from PEDIDOS where 1 = 2");
        int colCount = preparedStatement.getMetaData().getColumnCount();
        for (int i = 1; i <= colCount; i++) {
            System.out.println("\tgetColumnName(" + i + ") " + preparedStatement.getMetaData().getColumnName(i));
            System.out.println("\tgetColumnLabel(" + i + ") " + preparedStatement.getMetaData().getColumnLabel(i));
            System.out.println("\tgetColumnType(" + i + ") " + preparedStatement.getMetaData().getColumnType(i));
            System.out.println("\tgetColumnTypeName(" + i + ") " + preparedStatement.getMetaData().getColumnTypeName(i));
            System.out.println("\tgetColumnDisplaySize(" + i + ") " + preparedStatement.getMetaData().getColumnDisplaySize(i));
            System.out.println("\tgetColumnClassName(" + i + ") " + preparedStatement.getMetaData().getColumnClassName(i));
            System.out.println();
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
        resultSet.close();
        preparedStatement.close();
    }
}
