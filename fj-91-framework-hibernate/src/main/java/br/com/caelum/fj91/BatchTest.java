package br.com.caelum.fj91;

import org.hibernate.Session;

public class BatchTest {

    public static void main(String[] args) {
	BatchTest teste = new BatchTest();

	teste.incluirInstrucao();
	teste.incluirContato();

	HibernateUtil.close();
    }

    private void incluirContato() {
	Instrucao instrucao = new Instrucao();
	instrucao.setId(1l);
	Instrucao instrucaoErrada = new Instrucao();
	instrucaoErrada.setId(2l);
	int cnt = 0;

	Session session = HibernateUtil.getSession();

	for (int i = 0; i < 10; i++) {
	    session.beginTransaction();
	    for (int j = 1; j < 11; j++) {
		cnt++;
		Contato contato = new Contato();
		contato.setEmail(cnt + "@teste.com");
		contato.setEndereco(cnt + " endereco");
		contato.setNome(cnt + " nome");
		contato.setInstrucao(instrucao);
		if (j == 7 && i == 1) {
		    contato.setInstrucao(instrucaoErrada);
		}
		session.save(contato);
		if (j % 5 == 0) {
		    //session.flush();
		    //session.clear();
		}
	    }
	    try {
		session.getTransaction().commit();
	    } catch (Exception e) {
		System.out.println(e.getMessage());
		session.getTransaction().rollback();
	    } finally {
		session.clear();
	    }
	}

	session.close();
    }

    private void incluirInstrucao() {
	Session session = HibernateUtil.getSession();

	session.beginTransaction();

	Instrucao instrucao = new Instrucao();
	instrucao.setNivel("superior");
	session.save(instrucao);
	session.getTransaction().commit();

	session.close();
    }

}
