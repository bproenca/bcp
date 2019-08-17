import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TesteLote implements Runnable {

    private Connection conn;

    private int count;

    public TesteLote(final Connection pConn, int pCount) {
	conn = pConn;
	count = pCount;
    }

    /**
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException, InterruptedException {

	//final Connection connection = ConnectionFactory.getConnection(); usar mesma connection da erro
	final Thread thread1 = new Thread(new TesteLote(ConnectionFactory.getConnection(), 50));
	final Thread thread2 = new Thread(new TesteLote(ConnectionFactory.getConnection(), 70));
	thread1.start();
	thread2.start();

	thread1.join();
	thread2.join();

	System.out.println("Encerrando");

    }

    public void run() {
	boolean autoCommit = true;
	try {
	    List<Carro> carros = new ArrayList<Carro>();
	    String strThread = Thread.currentThread().getName();

	    for (int i = 1; i <= count; i++) {
		carros.add(new Carro(strThread + " Nome" + i, strThread + " Marca" + i));
	    }

	    autoCommit = conn.getAutoCommit();
	    conn.setAutoCommit(false);

	    final DAOCarro dao = new DAOCarro(conn);
	    dao.adiciona(carros.toArray(new Carro[carros.size()]));

	    dao.printSize();
	} catch (SQLException pE) {
	    pE.printStackTrace();
	} finally {
	    try {
		conn.setAutoCommit(autoCommit);
		conn.commit();
	    } catch (SQLException pE) {
		pE.printStackTrace();
	    }
	}
    }

}
