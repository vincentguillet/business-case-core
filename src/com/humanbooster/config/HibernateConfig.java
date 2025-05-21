package src.com.humanbooster.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import src.com.humanbooster.model.BorneRecharge;
import src.com.humanbooster.model.LieuRecharge;
import src.com.humanbooster.model.Reservation;
import src.com.humanbooster.model.Utilisateur;

public class HibernateConfig {

    private static final SessionFactory sessionFactory;
    public static final boolean LOCAL = true;
    private static String url;
    private static String username;
    private static String password;

    static {

        if (LOCAL) {
            url = "jdbc:mysql://127.0.0.1:3306/electricity-business";
            username = "admin";
            password = "admin";
        } else {
            url = "jdbc:mysql://mysql:3306/electricity-business?useSSL=false&amp;allowPublicKeyRetrieval=true";
            username = "root";
            password = "root";
        }

        Configuration config = new Configuration()
                .setProperty("hibernate.connection.url", url)
                .setProperty("hibernate.connection.username", username)
                .setProperty("hibernate.connection.password", password)
                .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .setProperty("hibernate.show_sql", "false")
                .setProperty("hibernate.format_sql", "true")
                .addAnnotatedClass(Utilisateur.class)
                .addAnnotatedClass(LieuRecharge.class)
                .addAnnotatedClass(BorneRecharge.class)
                .addAnnotatedClass(Reservation.class);

        sessionFactory = config.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
