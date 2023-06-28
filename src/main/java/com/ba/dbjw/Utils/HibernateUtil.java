package com.ba.dbjw.Utils;

import com.ba.dbjw.Entity.Customer.Customer;
import com.ba.dbjw.Entity.Employee.Employee;
import com.ba.dbjw.Entity.Invoice.Invoice;
import com.ba.dbjw.Entity.Invoice.InvoiceItem;
import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Entity.UserAuth.UserAuth;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.mariadb.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mariadb://localhost:3306/dbjw");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);

                // mapping entity with database
                configuration.addAnnotatedClass(UserAuth.class);
                configuration.addAnnotatedClass(Product.class);
                configuration.addAnnotatedClass(Customer.class);
                configuration.addAnnotatedClass(Employee.class);
                configuration.addAnnotatedClass(Invoice.class);
                configuration.addAnnotatedClass(InvoiceItem.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
