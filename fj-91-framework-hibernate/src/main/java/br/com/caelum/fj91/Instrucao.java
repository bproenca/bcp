package br.com.caelum.fj91;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "instrucao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Instrucao {

    @Id @GeneratedValue
    private Long id;

    private String nivel;

    public Long getId() {
	return id;
    }

    public String getNivel() {
	return nivel;
    }

    public void setId(final Long pId) {
	id = pId;
    }

    public void setNivel(final String pNivel) {
	nivel = pNivel;
    }

    @Override public String toString() {
	return "Instrucao{" +
			"id=" + id +
			", nivel='" + nivel + '\'' +
			'}';
    }

}