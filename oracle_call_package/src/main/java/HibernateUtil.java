import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class HibernateUtil {

    private static Configuration cfg;

    private static SessionFactory factory;

    static {
        // cfg = new AnnotationConfiguration();
        // cfg.configure();
        // factory = cfg.buildSessionFactory();

        cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
        factory = cfg.buildSessionFactory(ssrb.build());

        createDB(true);
    }

    public static void close() {
        factory.close();
    }

    public static void createDB(boolean create) {
        new SchemaExport(cfg).create(true, create);
    }

    public static Session getSession() {
        return factory.openSession();
    }

}
