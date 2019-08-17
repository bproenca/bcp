package br.com.bcp;

import java.util.List;

public interface ContatoDao {

    Long adiciona(Contato c);

    Contato busca(Long id);

    List<Contato> buscaPorEndereco(String endereco);

    List<Contato> buscaPorNome(String nome);

    void excluir(Contato contato);

}