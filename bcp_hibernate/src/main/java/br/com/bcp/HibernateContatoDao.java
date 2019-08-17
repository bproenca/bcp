package br.com.bcp;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class HibernateContatoDao implements ContatoDao {

    private final Session session;

    public HibernateContatoDao(final Session session) {
	this.session = session;
    }

    public Long adiciona(Contato contato) {
	return (Long) session.save(contato);
    }

    public Contato busca(Long id) {
	return (Contato) session.load(Contato.class, id);
    }

    public List<Contato> buscaPorEndereco(final String endereco) {
	return session.createCriteria(Contato.class).add(
			Restrictions.ilike("endereco", endereco)).list();
    }

    public List<Contato> buscaPorNome(String nome) {
	return session.createCriteria(Contato.class).add(
			Restrictions.ilike("nome", nome)).list();
    }

    public void excluir(final Contato contato) {
	session.delete(contato);
    }

}
