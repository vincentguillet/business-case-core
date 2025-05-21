package com.humanbooster.ui;

import com.humanbooster.dao.BorneRechargeDao;
import org.hibernate.SessionFactory;
import com.humanbooster.config.HibernateConfig;
import com.humanbooster.controller.AuthController;
import com.humanbooster.controller.BorneController;
import com.humanbooster.controller.LieuController;
import com.humanbooster.controller.ReservationController;
import com.humanbooster.dao.LieuRechargeDao;
import com.humanbooster.dao.ReservationDao;
import com.humanbooster.dao.UtilisateurDao;
import com.humanbooster.service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Démarrage de l'application...");

        System.out.println("\nLancez-vous ce projet via une commande docker ? (oui/non)");
        Scanner scanner = new Scanner(System.in);

        String reponse = scanner.nextLine();

        if (reponse.equalsIgnoreCase("oui") || reponse.equalsIgnoreCase("o")) {
            HibernateConfig.setLocalEnvironment(false);
            System.out.println("[DEBUG] localDev = " + HibernateConfig.isLocalEnvironment());
        } else if (reponse.equalsIgnoreCase("non") || reponse.equalsIgnoreCase("n")) {
            HibernateConfig.setLocalEnvironment(true);
            System.out.println("[DEBUG] localDev = " + HibernateConfig.isLocalEnvironment());
        } else {
            System.out.println("Réponse non valide. Lancement en mode local par défaut.");
            System.out.println("[DEBUG] localDev = " + HibernateConfig.isLocalEnvironment());
        }

        try {
            SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

            // Dao
            UtilisateurDao utilisateurDao = new UtilisateurDao(sessionFactory);
            LieuRechargeDao lieuRechargeDao = new LieuRechargeDao(sessionFactory);
            BorneRechargeDao borneRechargeDao = new BorneRechargeDao(sessionFactory);
            ReservationDao reservationDao = new ReservationDao(sessionFactory);

            // Service
            AuthService authService = new AuthServiceImpl(utilisateurDao);
            LieuService lieuService = new LieuServiceImpl(lieuRechargeDao);
            BorneService borneService = new BorneServiceImpl(lieuRechargeDao, borneRechargeDao, reservationDao);
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
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la SessionFactory : " + e.getMessage());
        }
    }
}