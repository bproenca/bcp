package br.com.bcp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JobDAO {

    // a conexão com o banco de dados
    private Connection connection;

    // construtor que recebe a conexão
    public JobDAO(Connection con) {
	this.connection = con;
    }

    public void createJob(long pJobNumber) {
	String insertStmt = "insert into t_job (id, name, status) values (?, ?, ?)";
	try {
	    // prepared statement para inserção
	    PreparedStatement stmt = this.connection.prepareStatement(insertStmt);
	    // seta os valores
	    stmt.setLong(1, pJobNumber);
	    stmt.setString(2, Thread.currentThread().getName());
	    stmt.setString(3, "Inicio");
	    // executa
	    stmt.execute();

	    stmt.close();
	} catch (SQLException pE) {
	    pE.printStackTrace();
	}
    }

    public void truncate() {
	try {
	    final Statement stmt = connection.createStatement();
	    stmt.executeUpdate("truncate table t_job");
	    stmt.executeUpdate("truncate table t_job_lock");
	    stmt.close();
	} catch (SQLException pE) {
	    pE.printStackTrace();
	}
    }

    public void updateJobStatus(long pJobNumber, String pJobStatus) {
	String updateStmt = "update t_job set status = ? where id = ?";
	try {
	    // prepared statement para inserção
	    PreparedStatement stmt = this.connection.prepareStatement(updateStmt);
	    // seta os valores
	    stmt.setString(1, pJobStatus);
	    stmt.setLong(2, pJobNumber);
	    // executa
	    stmt.execute();

	    stmt.close();
	} catch (SQLException buex) {
	    buex.printStackTrace();
	}
    }

}