package br.com.bcp;

import br.com.bcp.db.ConnectionFactory;
import br.com.bcp.db.JobDAO;
import br.com.bcp.thread.ThreadJobControl;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by bcp on 24/03/17.
 */
public class App {
    public static void main(String[] args) throws SQLException {

	Connection con = ConnectionFactory.getConnection();
	final JobDAO jobDAO = new JobDAO(con);

	long jobNumber = System.currentTimeMillis();
	jobDAO.createJob(jobNumber);
	System.out.println("Criado Job numero " + jobNumber + " - " + Thread.currentThread());

	new Thread(new ThreadJobControl(jobNumber)).start();
    }
}
