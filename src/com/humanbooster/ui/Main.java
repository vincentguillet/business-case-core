package src.com.humanbooster.ui;

import org.hibernate.SessionFactory;
import src.com.humanbooster.config.HibernateConfig;
import src.com.humanbooster.controller.AuthController;
import src.com.humanbooster.controller.BorneController;
import src.com.humanbooster.controller.LieuController;
import src.com.humanbooster.controller.ReservationController;
import src.com.humanbooster.dao.LieuRechargeDao;
import src.com.humanbooster.dao.ReservationDao;
import src.com.humanbooster.dao.UtilisateurDao;
import src.com.humanbooster.service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Démarrage de l'application");

        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

        Scanner scanner = new Scanner(System.in);

        // Dao
        UtilisateurDao utilisateurDao = new UtilisateurDao(sessionFactory);
        LieuRechargeDao lieuRechargeDao = new LieuRechargeDao(sessionFactory);
        ReservationDao reservationDao = new ReservationDao(sessionFactory);

        // Service
        AuthService authService = new AuthServiceImpl(utilisateurDao);
        LieuService lieuService = new LieuServiceImpl(lieuRechargeDao);
        BorneService borneService = new BorneServiceImpl(lieuRechargeDao, reservationDao);
        DocumentService documentService = new DocumentServiceImpl(lieuRechargeDao);
        ReservationService reservationService = new ReservationServiceImpl(reservationDao, lieuRechargeDao, utilisateurDao, documentService);

        // Controller
        AuthController authController = new AuthController(authService, scanner);
        LieuController lieuController = new LieuController(lieuService, scanner);
        BorneController borneController = new BorneController(borneService, lieuController, scanner);
        ReservationController reservationController = new ReservationController(reservationService, scanner);

        // Menu principal
        Menu menu = new Menu(scanner, authController, lieuController, borneController, reservationController);
        menu.afficherMenu();
    }
}