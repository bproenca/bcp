package br.com.bcp;

import br.com.bcp.cascade.CascadeDao;
import br.com.bcp.cascade.Filho;
import br.com.bcp.cascade.Pai;
import org.hibernate.Session;
import org.junit.*;

/**
 * Created by bcp on 27/07/17.
 */
public class CascadeTest {

    private CascadeDao cascadeDao;

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
        cascadeDao = new CascadeDao(session);
    }

    @Test
    public void test() {
        Pai pai = new Pai("email-1", "nome-1");
        pai.addFilho(new Filho("item A"));
        pai.addFilho(new Filho("item B"));

        session.beginTransaction();
        Long idBruno = cascadeDao.adiciona(pai);
        session.getTransaction().commit();

        session.beginTransaction();
        pai = cascadeDao.busca(idBruno);
        Assert.assertEquals("email-1", pai.getEmail());
        Assert.assertEquals(2, pai.getFilhos().size());
        session.getTransaction().commit();

        session.beginTransaction();
        cascadeDao.excluir(pai);
        session.getTransaction().commit();
    }

}
