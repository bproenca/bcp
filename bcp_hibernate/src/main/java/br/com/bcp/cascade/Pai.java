package br.com.bcp.cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pai")
public class Pai {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String email;
    private String nome;
    @OneToMany(mappedBy = "meupai", targetEntity = Filho.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Filho> filhos;

    public Pai() {
    }

    public Pai(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }

    public void addFilho(Filho filho) {
        if (filhos == null) {
            filhos = new HashSet<Filho>();
        }
        filho.setMeupai(this);
        filhos.add(filho);
    }

    public Set<Filho> getFilhos() {
        return filhos;
    }

    public void setFilhos(Set<Filho> filhos) {
        this.filhos = filhos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pai pai = (Pai) o;

        return id != null ? id.equals(pai.id) : pai.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    @Override
    public String toString() {
        return "Pai{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
