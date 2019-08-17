package br.com.bcp;

import org.hibernate.Session;
import org.junit.*;

import java.util.List;

/**
 * Created by bcp on 27/07/17.
 */
public class CrudTest {

    private HibernateContatoDao contatoDao;

    private Session session;

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
	HibernateUtil.close();
    }

    @After
    public void after() {
	session.close();
    }

    @Before
    public void before() {
	session = HibernateUtil.getSession();
	contatoDao = new HibernateContatoDao(session);
    }

    @Test
    public void test() {
	session.beginTransaction();
	Long idBruno = contatoDao.adiciona(createContato("bruno", "ruaX", "bruno@email.com"));
	contatoDao.adiciona(createContato("felipe", "ruaX", "felipe@email.com"));
	session.getTransaction().commit();

	session.beginTransaction();
	Contato contatoBruno = contatoDao.busca(idBruno);
	Assert.assertEquals("bruno", contatoBruno.getNome());
	Assert.assertNull(contatoBruno.getInstrucao());
	contatoBruno.setNome("bruno_cesar");
	contatoBruno.setInstrucao(createInstrucao("primario"));
	session.getTransaction().commit();

	session.beginTransaction();
	contatoBruno = contatoDao.busca(idBruno);
	Assert.assertEquals("bruno_cesar", contatoBruno.getNome());
	Assert.assertEquals("primario", contatoBruno.getInstrucao().getNivel());
	session.getTransaction().commit();

	session.beginTransaction();
	List<Contato> contatos = contatoDao.buscaPorNome("bruno_cesar");
	for (Contato contato : contatos) {
	    Assert.assertEquals("bruno_cesar", contato.getNome());
	}
	session.getTransaction().commit();

	session.beginTransaction();
	contatos = contatoDao.buscaPorEndereco("ruaX");
	Assert.assertEquals(2, contatos.size());
	for (Contato contato : contatos) {
	    contatoDao.excluir(contato);
	}
	session.getTransaction().commit();

	session.beginTransaction();
	contatos = contatoDao.buscaPorEndereco("ruaX");
	Assert.assertEquals(0, contatos.size());
	session.getTransaction().commit();
    }

    private Contato createContato(String pNome, String pEndereco, String pEmail) {
	final Contato contato = new Contato();
	contato.setNome(pNome);
	contato.setEndereco(pEndereco);
	contato.setEmail(pEmail);

	return contato;
    }

    private Instrucao createInstrucao(String pNivel) {
	final Instrucao instrucao = new Instrucao();
	instrucao.setNivel(pNivel);
	return instrucao;
    }

}
