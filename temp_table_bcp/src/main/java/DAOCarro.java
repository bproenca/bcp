import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOCarro {

    // a conexão com o banco de dados
    private Connection connection;

    private String insertStmt = "insert into CARROS (id, nome, marca) values (SEQ_CARROS.NEXTVAL, ?, ?)";

    // construtor que recebe a conexão
    public DAOCarro(Connection con) {
	this.connection = con;
    }

    public void adiciona(Carro... pCarros) throws SQLException {

	// prepared statement para inserção
	PreparedStatement stmt = this.connection.prepareStatement(insertStmt);

	for (Carro carro : pCarros) {
	    // seta os valores
	    stmt.setString(1, carro.getNome());
	    stmt.setString(2, carro.getMarca());
	    // executa
	    try {
		stmt.execute();
	    } catch (SQLException buex) {
		buex.printStackTrace();
	    }

	}

	stmt.close();
    }

    public void printSize() throws SQLException {

	PreparedStatement preparedStatement = connection.prepareStatement("select count(*), max(id) from carros");
	ResultSet resultSet = preparedStatement.executeQuery();

	if (resultSet.next()) {
	    System.out.println(Thread.currentThread() + " *** Count(*) = " + resultSet.getLong(1) + " max(id) = " + resultSet
			    .getInt(2));
	} else {
	    System.out.println(Thread.currentThread() + " *** Tabela vazia");
	}
	resultSet.close();
	preparedStatement.close();

    }

}