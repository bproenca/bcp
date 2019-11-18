package br.com.bcp.dao;

import java.sql.*;
import java.util.List;

public class OraclePedidoDAO implements PedidoDAO {

    // a conexão com o banco de dados
    private Connection connection;

    private String insertStmt = "insert into pedidos (id, descricao, numero, data, valor, id_merc) values (SEQ_PEDIDOS.NEXTVAL, ?, ?, ?, ?, ?)";

    // construtor que recebe a conexão
    public OraclePedidoDAO(Connection con) {
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
            // executa
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
        //PreparedStatement ps = connection.prepareCall(insertStmt);

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
                System.out.println(e.getMessage());
                pedidoControleLote.ajustarPaginacao(ps.getUpdateCount());
            }
        } while (pedidoControleLote.hasNext());

        ps.close();
    }

//    public void altera(Pedido pPedido) throws SQLException {
//        PreparedStatement stmt = connection
//                .prepareStatement("update pedidos set nome=?, email=?, endereco=? where id=?");
//        stmt.setString(1, pPedido.getNome());
//        stmt.setString(2, pPedido.getEmail());
//        stmt.setString(3, pPedido.getEndereco());
//        stmt.setLong(4, pPedido.getId());
//        stmt.execute();
//        stmt.close();
//    }

//    public List<Pedido> getLista() throws SQLException {
//
//        	PreparedStatement stmt = this.connection
//        			.prepareStatement("select * from pedidos");
//        	ResultSet rs = stmt.executeQuery();
//
//        	List<Pedido> list = new ArrayList<Pedido>();
//        	while (rs.next()) {
//        	    // criando o objeto Pedido
//        	    Pedido pedido = new Pedido();
//        	    pedido.setId(rs.getLong("id"));
//        	    pedido.setNome(rs.getString("nome"));
//        	    pedido.setEmail(rs.getString("email"));
//        	    pedido.setEndereco(rs.getString("endereco"));
//
//        	    // adicionando o objeto à lista
//        	    list.add(pedido);
//        	}
//
//        	rs.close();
//        	stmt.close();
//
//        	return list;
//        return null;
//    }

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
        stmt.executeUpdate("truncate table pedidos");
        stmt.close();
    }

    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }

//    public Pedido procura(Long id) throws SQLException {
//        	PreparedStatement stmt = connection
//        			.prepareStatement("select * from pedidos where id=?");
//        	ResultSet rs = stmt.executeQuery();
//
//        	if (!rs.next()) {
//        	    return null;
//        	}
//
//        	Pedido c = new Pedido();
//        	c.setId(rs.getLong("id"));
//        	c.setNome(rs.getString("nome"));
//        	c.setEmail(rs.getString("email"));
//        	c.setEndereco(rs.getString("endereco"));
//        	rs.close();
//        	stmt.close();
//        	return c;
//        return null;
//    }

//    public void remove(Pedido pPedido) throws SQLException {
//        	PreparedStatement stmt = connection
//        			.prepareStatement("delete from pedidos where id=?");
//        	stmt.setLong(1, pPedido.getId());
//        	stmt.execute();
//        	stmt.close();
//    }

//    public void truncate() throws SQLException {
//        final Statement stmt = connection.createStatement();
//        stmt.executeUpdate("truncate table pedidos");
//        stmt.close();
//    }

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
        successCount = 0;
    }
}