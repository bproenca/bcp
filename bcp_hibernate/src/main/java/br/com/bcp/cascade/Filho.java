package br.com.bcp.cascade;

import javax.persistence.*;

@Entity
@Table(name = "filho")
public class Filho {

    @Id
    @GeneratedValue
    private Long id;
    private String item;
    @ManyToOne
    @JoinColumn(name = "pai_id")
    private Pai meupai;

    public Filho() {
    }

    public Filho(String item) {
        this.item = item;
    }

    public Pai getMeupai() {
        return meupai;
    }

    public void setMeupai(Pai meupai) {
        this.meupai = meupai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filho filho = (Filho) o;

        return item.equals(filho.item);
    }

    @Override
    public int hashCode() {
        return item.hashCode();
    }

    @Override
    public String toString() {
        return "Filho{" +
                "id=" + id +
                ", item='" + item + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
