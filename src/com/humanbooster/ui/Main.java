package src.com.humanbooster.ui;

import src.com.humanbooster.controller.AuthController;
import src.com.humanbooster.controller.BorneController;
import src.com.humanbooster.controller.LieuController;
import src.com.humanbooster.controller.ReservationController;
import src.com.humanbooster.dao.LieuDao;
import src.com.humanbooster.dao.ReservationDao;
import src.com.humanbooster.dao.UserDao;
import src.com.humanbooster.service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Dao
        UserDao userRepo = new UserDao();
        LieuDao lieuRepo = new LieuDao();
        ReservationDao reservationRepo = new ReservationDao();

        // Service
        AuthService authService = new AuthServiceImpl(userRepo);
        LieuService lieuService = new LieuServiceImpl(lieuRepo);
        BorneService borneService = new BorneServiceImpl(lieuRepo, reservationRepo);
        DocumentService documentService = new DocumentServiceImpl(lieuRepo);
        ReservationService reservationService = new ReservationServiceImpl(reservationRepo, lieuRepo, documentService);

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