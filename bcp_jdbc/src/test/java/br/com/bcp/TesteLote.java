package br.com.bcp;

import br.com.bcp.dao.ConnectionFactory;
import br.com.bcp.dao.OraclePedidoDAO;
import br.com.bcp.dao.PedidoDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TesteLote {

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
    public void testAdicionaLoteSucesso() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            pedidos.add(new Pedido(new Date(System.currentTimeMillis()), "Pedido" + i, i, 999.88, 1));
        }

        dao.adicionaLote(pedidos);
        final Long tableSize = dao.getTableSize();

        Assert.assertEquals(10000, tableSize.intValue());
    }

    @Test
    public void testAdicionaLoteErro() throws SQLException {
        List<Pedido> pedidos = new ArrayList<Pedido>();
        for (int i = 0; i < 10000; i++) {
            if (i % 33 == 0) {
                //error FK ORA (id_merc = 5 does not exist)
                pedidos.add(new Pedido(new Date(System.currentTimeMillis()), "Pedido" + i, i, 999.88, 5));
            } else {
                pedidos.add(new Pedido(new Date(System.currentTimeMillis()), "Pedido" + i, i, 999.88, 1));
            }
        }

        dao.adicionaLote(pedidos);
        final Long tableSize = dao.getTableSize();

        if (dao instanceof OraclePedidoDAO) {
            Assert.assertEquals(9990, tableSize.intValue());
        } else {
            Assert.assertEquals(0, tableSize.intValue());
        }
    }

}
