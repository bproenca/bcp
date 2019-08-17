import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by bcp on 27/07/17.
 */
public class FuncTest {


    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        HibernateUtil.close();
    }

    @Test
    public void test_mesma_transacao() {
        Session session = HibernateUtil.getSession();
        CallFunctionDao procDao = new CallFunctionDao(session);

        Integer valorEsperado;
        Integer valorEncontrado;

        session.beginTransaction();
        valorEsperado = 5;
        valorEncontrado = procDao.iniciar(5);
        Assert.assertEquals(valorEsperado, valorEncontrado);

        valorEsperado = 8;
        valorEncontrado = procDao.somar(3);
        Assert.assertEquals(valorEsperado, valorEncontrado);
        session.getTransaction().commit();

        session.close();
    }

    @Test
    public void test_diferentes_transacoes() {
        Session session = HibernateUtil.getSession();
        CallFunctionDao procDao = new CallFunctionDao(session);

        Integer valorEsperado;
        Integer valorEncontrado;

        session.beginTransaction();
        valorEsperado = 2;
        valorEncontrado = procDao.iniciar(2);
        Assert.assertEquals(valorEsperado, valorEncontrado);

        valorEsperado = 5;
        valorEncontrado = procDao.somar(3);
        Assert.assertEquals(valorEsperado, valorEncontrado);
        session.getTransaction().commit();

        // outra transacao
        session.beginTransaction();
        valorEsperado = 9;
        valorEncontrado = procDao.somar(4);
        Assert.assertEquals(valorEsperado, valorEncontrado);
        session.getTransaction().commit();

        session.close();
    }

    @Test
    public void test_diferentes_sessions() {
        Session session1 = HibernateUtil.getSession();
        Session session2 = HibernateUtil.getSession();

        System.out.println(session1.equals(session2));

        CallFunctionDao procDao1 = new CallFunctionDao(session1);
        CallFunctionDao procDao2 = new CallFunctionDao(session2);

        Integer valorEsperado;
        Integer valorEncontrado;

        session1.beginTransaction();
        valorEsperado = 4;
        valorEncontrado = procDao1.iniciar(4);
        Assert.assertEquals(valorEsperado, valorEncontrado);

        try {
            session2.beginTransaction();
            valorEsperado = 8;
            valorEncontrado = procDao2.somar(8);
            Assert.assertEquals(valorEsperado, valorEncontrado);
            session2.getTransaction().commit();

            session2.beginTransaction();
            valorEsperado = 10;
            valorEncontrado = procDao2.somar(2);
            Assert.assertEquals(valorEsperado, valorEncontrado);
            session2.getTransaction().commit();
        } finally {
            // do nothing
        }

        valorEsperado = 6;
        valorEncontrado = procDao1.somar(2);
        Assert.assertEquals(valorEsperado, valorEncontrado);
        session1.getTransaction().commit();

        session2.clear();
        session2.close();

        session1.clear();
        session1.close();

        session1 = HibernateUtil.getSession();
        procDao1 = new CallFunctionDao(session1);

        try {
            // valor retornado foi da session que primeiro foi encerrada
            session1.beginTransaction();
            valorEsperado = 13;
            valorEncontrado = procDao1.somar(3);
            Assert.assertEquals(valorEsperado, valorEncontrado);
            session1.getTransaction().commit();
        } finally {
            //do nothing
        }

        session1.clear();
        session1.close();
    }

}
