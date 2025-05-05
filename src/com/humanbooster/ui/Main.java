package src.com.humanbooster.ui;

import src.com.humanbooster.controller.AuthController;
import src.com.humanbooster.controller.BorneController;
import src.com.humanbooster.controller.LieuController;
import src.com.humanbooster.controller.ReservationController;
import src.com.humanbooster.repository.LieuRepository;
import src.com.humanbooster.repository.ReservationRepository;
import src.com.humanbooster.repository.UserRepository;
import src.com.humanbooster.service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Repositories
        UserRepository userRepo = new UserRepository();
        LieuRepository lieuRepo = new LieuRepository();
        ReservationRepository reservationRepo = new ReservationRepository();

        // Services
        AuthService authService = new AuthServiceImpl(userRepo);
        LieuService lieuService = new LieuServiceImpl(lieuRepo);
        BorneService borneService = new BorneServiceImpl(lieuRepo);
        DocumentService documentService = new DocumentServiceImpl(lieuRepo);
        ReservationService reservationService = new ReservationServiceImpl(reservationRepo, lieuRepo, documentService);

        // Controllers
        AuthController authController = new AuthController(authService, scanner);
        LieuController lieuController = new LieuController(lieuService, scanner);
        BorneController borneController = new BorneController(borneService, scanner);
        ReservationController reservationController = new ReservationController(reservationService, scanner);

        // Menu principal
        Menu menu = new Menu(scanner, authController, lieuController, borneController, reservationController);
        menu.afficherMenu();
    }
}