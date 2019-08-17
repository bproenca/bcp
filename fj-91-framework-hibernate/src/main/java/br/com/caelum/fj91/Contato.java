package br.com.caelum.fj91;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "contatos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contato {

    @Column(nullable = false)
    private String email;

    private String endereco;

    @Id @GeneratedValue

    private Long id;

    @OneToOne
    private Instrucao instrucao;

    private String nome;

    public String getEmail() {
	return email;
    }

    public String getEndereco() {
	return endereco;
    }

    public Long getId() {
	return id;
    }

    public Instrucao getInstrucao() {
	return instrucao;
    }

    public String getNome() {
	return nome;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public void setEndereco(String endereco) {
	this.endereco = endereco;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setInstrucao(final Instrucao pInstrucao) {
	instrucao = pInstrucao;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    @Override public String toString() {
	return "Contato{" +
			"email='" + email + '\'' +
			", endereco='" + endereco + '\'' +
			", id=" + id +
			", instrucao=" + instrucao +
			", nome='" + nome + '\'' +
			'}';
    }

}