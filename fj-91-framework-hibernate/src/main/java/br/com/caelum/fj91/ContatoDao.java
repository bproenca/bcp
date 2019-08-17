package br.com.caelum.fj91;

import java.sql.SQLException;
import java.util.List;

public interface ContatoDao {

	public abstract void adiciona(Contato c);

	public abstract Contato busca(Long id);

	public abstract List<Contato> buscaPorNome(String nome) ;

}