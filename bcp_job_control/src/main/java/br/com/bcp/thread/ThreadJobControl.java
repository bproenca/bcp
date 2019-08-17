package br.com.bcp.thread;

import br.com.bcp.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by bcp on 24/03/17.
 */
public class ThreadJobControl implements Runnable {

    private long jobNumber;

    boolean autoCommit = true;

    Connection conn = null;

    public ThreadJobControl(final long pJobNumber) {
	jobNumber = pJobNumber;
	conn = ConnectionFactory.getConnection();
    }

    public void run() {
	createLock();

	ThreadJobExec threadJobExec = new ThreadJobExec(jobNumber);
	new Thread(threadJobExec).start();

	synchronized (threadJobExec) {

	    String query = "select id_job, done from t_job_lock where id_job = ? FOR UPDATE";
	    try {
		autoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);

		PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		stmt.setLong(1, jobNumber);
		ResultSet resultSet = stmt.executeQuery(); // aqui adquire o lock

		if (resultSet.next()) {
		    resultSet.updateString(2, "S");
		    resultSet.updateRow();

		    System.out.println("> Aguardando o ThreadJobExec completar... - " + Thread.currentThread());
		    try {
			threadJobExec.wait(); // aqui segura o lock ate ThreadJobExec finalizar
		    } catch (InterruptedException pE) {
			pE.printStackTrace();
			conn.rollback(); // aqui libera o lock
			throw new RuntimeException(pE);
		    }
		    conn.commit(); // aqui libera o lock
		}

		resultSet.close();
		stmt.close();
		conn.setAutoCommit(autoCommit);
	    } catch (SQLException pE) {
		pE.printStackTrace();
		throw new RuntimeException(pE);
	    }

	    System.out.println("> Total é igual a: " + threadJobExec.total + " - " + Thread.currentThread());
	}

    }

    private void createLock() {
	String insertStmt = "insert into t_job_lock (id_job, done, dh) values (?, 'N', sysdate)";
	try {
	    // prepared statement para inserção
	    PreparedStatement stmt = conn.prepareStatement(insertStmt);
	    // seta os valores
	    stmt.setLong(1, jobNumber);
	    // executa
	    stmt.execute();

	    stmt.close();
	} catch (SQLException pE) {
	    pE.printStackTrace();
	}
    }

}
