package br.com.caelum.fj91;

import org.hibernate.Session;

public class Teste {

    public static void main(String[] args) {
	Teste teste = new Teste();

	teste.incluir();
	teste.carregar();
	teste.alterar();

	HibernateUtil.close();
    }

    private void alterar() {
	Session session = HibernateUtil.getSession();

	session.beginTransaction();

	Contato contato = new HibernateContatoDao(session).busca(1l);
	contato.setNome(contato.getNome() + Thread.currentThread().getName());
	System.out.println(contato.getNome());

	session.getTransaction().commit();
	session.close();
    }

    private void carregar() {
	Session session = HibernateUtil.getSession();

	session.beginTransaction();

	Contato contato = new HibernateContatoDao(session).busca(1l);
	System.out.println(contato.getNome());

	session.getTransaction().commit();

	session.close();
    }

    private void incluir() {
	Session session = HibernateUtil.getSession();

	Contato contato = new Contato();
	contato.setEmail("teste@teste.com");
	contato.setEndereco("endereco");
	contato.setNome("luiz");

	session.beginTransaction();

	new HibernateContatoDao(session).adiciona(contato);

	session.getTransaction().commit();

	session.close();
    }

}
