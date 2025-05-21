package com.humanbooster.ui;

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