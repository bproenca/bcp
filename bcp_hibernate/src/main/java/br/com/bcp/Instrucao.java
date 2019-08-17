package br.com.bcp;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
	return "br.com.bcp.Instrucao{" +
			"id=" + id +
			", nivel='" + nivel + '\'' +
			'}';
    }

}