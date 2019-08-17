package br.com.bcp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Teste {

    /**
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {

	Connection con = ConnectionFactory.getConnection();

	List<Pedido> pedidos = new ArrayList<Pedido>();
	for (int i = 1; i < 10001; i++) {
	    if (i % 1000 == 0) {
		//pedidos.add(new Pedido(new Date(System.currentTimeMillis()), "Pedido" + i, i, 999.88, 5));
		pedidos.add(new Pedido(new Date(System.currentTimeMillis()), "Pedido" + i, i, 999.88, 2));
	    } else {
		pedidos.add(new Pedido(new Date(System.currentTimeMillis()), "Pedido" + i, i, 999.88, 1));
	    }
	}

	final PedidoDAO dao = new PedidoDAO(con);
	final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

	dao.truncate();

	System.out.println("### lote BEGIN " + simpleDateFormat.format(new java.util.Date()));
	dao.adicionaLote(pedidos);
	System.out.println("### lote END   " + simpleDateFormat.format(new java.util.Date()));

	dao.printSize();
	con.close();
    }

}
