import org.hibernate.Session;

import java.math.BigDecimal;

public class CallFunctionDao {
    private final Session session;

    public CallFunctionDao(Session session) {
        this.session = session;
    }

    public Integer iniciar(Integer pValor) {
        String query = "SELECT BCP_PKG.INICIAR(:valor) FROM DUAL";
        BigDecimal valor = (BigDecimal) session.createSQLQuery(query)
                .setParameter("valor", pValor)
                .uniqueResult();

        System.out.println("Retorno da function (iniciar): " + valor);
        return valor.intValue();
    }

    public Integer somar(Integer pValor) {
        String query = "SELECT BCP_PKG.SOMAR(:valor) FROM DUAL";
        BigDecimal valor = (BigDecimal) session.createSQLQuery(query)
                .setParameter("valor", pValor)
                .uniqueResult();

        System.out.println("Retorno da function (somar): " + valor);
        return valor.intValue();
    }

    public Integer subtrair(Integer pValor) {
        String query = "SELECT BCP_PKG.SUBTRAIR(:valor) FROM DUAL";
        BigDecimal valor = (BigDecimal) session.createSQLQuery(query)
                .setParameter("valor", pValor)
                .uniqueResult();

        System.out.println("Retorno da function (subtrair): " + valor);
        return valor.intValue();
    }

}
