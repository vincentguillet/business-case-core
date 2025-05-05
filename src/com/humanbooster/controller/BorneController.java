package src.com.humanbooster.controller;

import src.com.humanbooster.model.BorneRecharge;
import src.com.humanbooster.model.EtatBorne;
import src.com.humanbooster.service.BorneService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class BorneController {

    private final BorneService borneService;
    private final Scanner scanner;

    public BorneController(BorneService borneService, Scanner scanner) {
        this.borneService = borneService;
        this.scanner = scanner;
    }

    public void ajouterBorne() {
        System.out.println("=== Ajouter une borne ===");
        System.out.print("ID du lieu : ");
        String idLieu = scanner.nextLine();

        System.out.print("Tarif horaire : ");
        double tarif = Double.parseDouble(scanner.nextLine());

        BorneRecharge borne = new BorneRecharge(UUID.randomUUID().toString(), EtatBorne.DISPONIBLE, tarif);
        boolean ok = borneService.ajouterBorne(idLieu, borne);

        if (ok) System.out.println("Borne ajoutée à " + idLieu + " avec ID : " + borne.getId());
        else System.out.println("Échec : lieu introuvable ou borne déjà existante.");
    }

    public void modifierBorne() {
        System.out.println("=== Modifier une borne ===");
        System.out.print("ID du lieu : ");
        String idLieu = scanner.nextLine();
        System.out.print("ID de la borne : ");
        String idBorne = scanner.nextLine();

        BorneRecharge existante = borneService.trouverBorneParId(idLieu, idBorne);
        if (existante == null) {
            System.out.println("Borne non trouvée.");
            return;
        }

        System.out.print("Nouveau tarif horaire (" + existante.getTarifHoraire() + ") : ");
        String tarifStr = scanner.nextLine();
        double nouveauTarif = tarifStr.isBlank() ? existante.getTarifHoraire() : Double.parseDouble(tarifStr);

        EtatBorne nouvelEtat = existante.getEtat();
        BorneRecharge modifiee = new BorneRecharge(idBorne, nouvelEtat, nouveauTarif);
        boolean ok = borneService.modifierBorne(idLieu, modifiee);

        if (ok) System.out.println("Borne modifiée.");
        else System.out.println("Échec de la modification.");
    }

    public void supprimerBorne() {
        System.out.println("=== Supprimer une borne ===");
        System.out.print("ID du lieu : ");
        String idLieu = scanner.nextLine();
        System.out.print("ID de la borne : ");
        String idBorne = scanner.nextLine();

        boolean ok = borneService.supprimerBorne(idLieu, idBorne);
        if (ok) System.out.println("Borne supprimée.");
        else System.out.println("Échec : borne introuvable ou impossible à supprimer.");
    }

    public void listerBornes() {
        System.out.print("ID du lieu : ");
        String idLieu = scanner.nextLine();

        List<BorneRecharge> bornes = borneService.listerBornesParLieu(idLieu);
        if (bornes.isEmpty()) {
            System.out.println("Aucune borne pour ce lieu.");
            return;
        }

        System.out.println("=== Bornes du lieu ===");
        for (BorneRecharge b : bornes) {
            System.out.println("- [" + b.getId() + "] " + b.getEtat() + " - " + b.getTarifHoraire() + "€/h");
        }
    }
}
