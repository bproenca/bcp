package br.com.bcp.cascade;

import org.hibernate.Session;

public class CascadeDao {

    private final Session session;

    public CascadeDao(final Session session) {
        this.session = session;
    }

    public Long adiciona(Pai pai) {
        return (Long) session.save(pai);
    }

    public Pai busca(Long id) {
        return (Pai) session.load(Pai.class, id);
    }

    public void excluir(final Pai pai) {
        session.delete(pai);
    }

}
