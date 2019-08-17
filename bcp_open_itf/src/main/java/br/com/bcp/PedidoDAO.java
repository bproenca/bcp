package br.com.bcp;

import java.sql.*;
import java.util.List;

public class PedidoDAO {

    // a conexão com o banco de dados
    private Connection connection;

    private String insertStmt = "insert into pedidos (id, descricao, numero, data, valor, id_merc) values (SEQ_PEDIDOS.NEXTVAL, ?, ?, ?, ?, ?)";

    // construtor que recebe a conexão
    public PedidoDAO(Connection con) {
	this.connection = con;
    }

    public void adicionaLote(List<Pedido> pPedidos) throws SQLException {

	PreparedStatement ps = connection.prepareStatement(insertStmt);
	//PreparedStatement ps = connection.prepareCall(insertStmt);

	final int batchSize = 100000;
	int count = 0;

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

    public void printSize() throws SQLException {

	PreparedStatement preparedStatement = connection.prepareStatement("select count(*), max(id) from pedidos");
	ResultSet resultSet = preparedStatement.executeQuery();

	if (resultSet.next()) {
	    System.out.println("*** Count(*) = " + resultSet.getLong(1) + " max(id) = " + resultSet.getInt(2));
	} else {
	    System.out.println("*** Tabela vazia");
	}
	resultSet.close();
	preparedStatement.close();

    }

    public void truncate() throws SQLException {
	final Statement stmt = connection.createStatement();
	stmt.executeUpdate("truncate table pedidos");
	stmt.close();
    }

}