package br.com.bcp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by bcp on 16/03/17.
 */
public class ControleLote<T> implements Iterator<T[]> {

    private T[] listaRegistros;

    private int posicaoAtual = 0;

    private int tamanhoPagina;

    public ControleLote(int pTamanhoPagina, T[] pListaRegistros) {
	tamanhoPagina = pTamanhoPagina;
	listaRegistros = pListaRegistros;
    }

    public static void main(String[] args) {
	List<String> lista = new ArrayList<String>();
	for (int i = 1; i <= 22; i++) {
	    lista.add("X" + i);
	}
	final ControleLote<String> lote = new ControleLote<>(4, lista.toArray(new String[lista.size()]));

	while (lote.hasNext()) {
	    final String[] next = lote.next();

	    try {
		for (String valor : next) {
		    if ("X5".equalsIgnoreCase(valor)) {
			throw new IllegalArgumentException();
		    }
		    System.out.println(">>> valor" + valor);
		}
	    } catch (IllegalArgumentException e) {
		lote.ajustarPaginacao(4);
	    }
	}

    }

    public void ajustarPaginacao(int pPosicaoErro) {
	System.out.println("erro na posicao " + pPosicaoErro +
			" voltando da posicao: " + posicaoAtual + " para: " + (posicaoAtual - tamanhoPagina + pPosicaoErro + 1));
	posicaoAtual = posicaoAtual - tamanhoPagina + pPosicaoErro + 1;
    }

    @Override public boolean hasNext() {
	if (posicaoAtual < listaRegistros.length) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public T[] next() {
	return getProximoLote();
    }

    private T[] getProximoLote() {
	int proxPosicao;

	if (posicaoAtual + tamanhoPagina > listaRegistros.length) {
	    proxPosicao = listaRegistros.length;
	    tamanhoPagina = proxPosicao - posicaoAtual;
	} else {
	    proxPosicao = posicaoAtual + tamanhoPagina;
	}

	final T[] range = Arrays.copyOfRange(listaRegistros, posicaoAtual, proxPosicao);
	System.out.println("entregando lote: " + posicaoAtual + "  ate " + proxPosicao);

	posicaoAtual = proxPosicao;
	return range;
    }
}
