package br.com.bcp.dao;

import br.com.bcp.Pedido;

import java.sql.SQLException;
import java.util.List;

public interface PedidoDAO {

    void adiciona(Pedido... pPedidos) throws SQLException;

    void adicionaLote(List<Pedido> pPedidos) throws SQLException;

    Long getTableSize() throws SQLException;

    void truncate() throws SQLException;

    void closeConnection() throws SQLException;
}