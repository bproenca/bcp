package br.com.caelum.fj91;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class HibernateContatoDao implements ContatoDao {

	private final Session session;

	public HibernateContatoDao(final Session session) {
		this.session = session;
	}

	public void adiciona(Contato contato) {
		session.save(contato);
	}

	public Contato busca(Long id) {
		return (Contato) session.load(Contato.class, id);
	}

	public List<Contato> buscaPorNome(String nome) {
		return session.createCriteria(Contato.class).add(
				Restrictions.ilike("nome", nome)).list();
	}

}
