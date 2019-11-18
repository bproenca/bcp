package br.com.bcp.dao;

import br.com.bcp.dao.ConnectionFactory;
import br.com.bcp.dao.PedidoDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by bcp on 27/07/17.
 */
public class TesteUnidade {

    private PedidoDAO dao = null;

    @After
    public void after() throws SQLException {
        dao.truncate();
        dao.closeConnection();
    }

    @Before
    public void before() throws SQLException {
        dao = ConnectionFactory.buildDAO(ConnectionFactory.ORACLE);
    }

    @Test
    public void test() throws SQLException {
        final Date date = new Date(System.currentTimeMillis());

        Pedido[] pedidos = {
                new Pedido(date, "Pedido 1", 111, 888.88, 1),
                new Pedido(date, "Pedido 2", 222, 999.99, 1)
        };

        dao.adiciona(pedidos);
        final Long tableSize = dao.getTableSize();

        Assert.assertEquals(2, tableSize.intValue());
    }

}
