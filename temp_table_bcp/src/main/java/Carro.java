/**
 * Created by bcp on 28/03/17.
 */
public class Carro {
    private String marca;

    private String nome;

    public Carro(final String pNome, final String pMarca) {
	marca = pMarca;
	nome = pNome;
    }

    public String getMarca() {

	return marca;
    }

    public String getNome() {
	return nome;
    }

    public void setMarca(final String pMarca) {
	marca = pMarca;
    }

    public void setNome(final String pNome) {
	nome = pNome;
    }

    @Override public String toString() {
	return "Carro{" +
			"marca='" + marca + '\'' +
			", nome='" + nome + '\'' +
			'}';
    }
}
