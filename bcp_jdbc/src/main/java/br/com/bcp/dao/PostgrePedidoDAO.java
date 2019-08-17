package br.com.bcp.dao;

import br.com.bcp.ControleLote;
import br.com.bcp.Pedido;

import java.sql.*;
import java.util.List;

public class PostgrePedidoDAO implements PedidoDAO {

    // a conexão com o banco de dados
    private Connection connection;

    private String insertStmt = "insert into pedidos (id, descricao, numero, data, valor, id_merc) values (nextval('SEQ_PEDIDOS'), ?, ?, ?, ?, ?)";

    // construtor que recebe a conexão
    public PostgrePedidoDAO(Connection con) {
        this.connection = con;
    }

    public void adiciona(Pedido... pPedidos) throws SQLException {
        // prepared statement para inserção
        PreparedStatement stmt = this.connection.prepareStatement(insertStmt);

        for (Pedido pedido : pPedidos) {
            // seta os valores
            stmt.setString(1, pedido.getDescricao());
            stmt.setInt(2, pedido.getNumero());
            stmt.setDate(3, pedido.getData());
            stmt.setDouble(4, pedido.getValor());
            stmt.setInt(5, pedido.getIdMerc());
            try {
                stmt.execute();
            } catch (SQLException buex) {
                buex.printStackTrace();
            }
        }

        stmt.close();
    }

    public void adicionaLote(List<Pedido> pPedidos) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(insertStmt);
        final int batchSize = 1000;

        final ControleLote<Pedido> pedidoControleLote = new ControleLote<>(batchSize,
                pPedidos.toArray(new Pedido[pPedidos.size()]));

        do {
            for (Pedido pedido : pedidoControleLote.next()) {
                ps.setString(1, pedido.getDescricao());
                ps.setInt(2, pedido.getNumero());
                ps.setDate(3, pedido.getData());
                ps.setDouble(4, pedido.getValor());
                ps.setInt(5, pedido.getIdMerc());
                ps.addBatch();
            }
            try {
                ps.executeBatch();
            } catch (BatchUpdateException e) {
                e.printStackTrace();
                //pedidoControleLote.ajustarPaginacao(ps.getUpdateCount());
                //PostgreSQL - can't get updateCount value :(
            }
        } while (pedidoControleLote.hasNext());

        ps.close();
    }

    public Long getTableSize() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*), max(id) XYZ, 'ABC' TEXTO from pedidos");
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
        Long size = Long.valueOf(0);
        if (resultSet.next()) {
            size = resultSet.getLong(1);
            System.out.println("*** Count(*) = " + size + " max(id) = " + resultSet.getInt(2));
        } else {
            System.out.println("*** Tabela vazia");
        }
        resultSet.close();
        preparedStatement.close();
        return size;
    }

    @Override
    public void truncate() throws SQLException {
        final Statement stmt = connection.createStatement();
        stmt.executeUpdate("truncate pedidos");
        stmt.close();
    }

    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }


    private void tratarErroBatchIns(BatchUpdateException buex) {
        buex.printStackTrace();
        int[] updateCounts = buex.getUpdateCounts();
        int successCount = 0;
        int failCount = 0;
        int notAavailable = 0;

        for (int i = 0; i < updateCounts.length; i++) {
            if (updateCounts[i] >= 0) {
                successCount++;

            } else if (updateCounts[i] == Statement.SUCCESS_NO_INFO) {
                notAavailable++;

            } else if (updateCounts[i] == Statement.EXECUTE_FAILED) {
                failCount++;

            }
        }
        System.out.println("Number of affected rows before Batch Error :: " + successCount);
        System.out.println("Number of affected rows not available:" + notAavailable);
        System.out.println("Failed Count in Batch because of Error:" + failCount);
    }

}