package br.com.bcp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by bcp on 24/03/17.
 */
public class TestLock {
    public static void main(String[] args) throws SQLException {
	final Connection conn = ConnectionFactory.getConnection();

	boolean autoCommit = conn.getAutoCommit();

	String query = "SELECT ID, NAME, STATUS FROM T_JOB WHERE ID = ? FOR UPDATE";
	try {
	    conn.setAutoCommit(false);

	    PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
	    //PreparedStatement stmt = conn.prepareStatement(query);
	    stmt.setInt(1, 123);
	    ResultSet resultSet = stmt.executeQuery();

	    if (resultSet.next()) {
		resultSet.updateString(3, "DONE " + System.currentTimeMillis());
		resultSet.updateRow();
		conn.commit();
	    }

	    resultSet.close();
	    stmt.close();
	} catch (SQLException pE) {
	    pE.printStackTrace();
	} finally {
	    conn.setAutoCommit(autoCommit);
	}

    }
}
