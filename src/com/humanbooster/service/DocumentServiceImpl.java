package src.com.humanbooster.service;

import src.com.humanbooster.model.BorneRecharge;
import src.com.humanbooster.model.LieuRecharge;
import src.com.humanbooster.model.Reservation;
import src.com.humanbooster.repository.LieuRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

/**
 * Classe de service pour la gestion des documents liés aux réservations.
 */
public class DocumentServiceImpl implements DocumentService {

    private static final String EXPORT_DIR = "exports";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final LieuRepository lieuRepository;

    /**
     * Constructeur de la classe DocumentServiceImpl.
     *
     * @param lieuRepository Le repository des lieux de recharge.
     */
    public DocumentServiceImpl(LieuRepository lieuRepository) {
        this.lieuRepository = lieuRepository;
    }

    /**
     * Génère un reçu de réservation au format texte.
     *
     * @param r La réservation pour laquelle générer le reçu.
     */
    @Override
    public void genererRecuReservation(Reservation r) {
        BorneRecharge borne = trouverBorneParId(r.getIdBorne());
        if (borne == null) {
            System.out.println("Impossible de générer le reçu : borne introuvable.");
            return;
        }

        long dureeHeures = Duration.between(r.getDateDebut(), r.getDateFin()).toHours();
        double total = dureeHeures * borne.getTarifHoraire();

        File dir = new File(EXPORT_DIR);
        if (!dir.exists()) dir.mkdirs();

        File recu = new File(dir, "recu_" + r.getId() + ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(recu))) {
            writer.write("=== Reçu de réservation ===\n");
            writer.write("ID Réservation : " + r.getId() + "\n");
            writer.write("ID Borne       : " + r.getIdBorne() + "\n");
            writer.write("Date début     : " + r.getDateDebut().format(FORMATTER) + "\n");
            writer.write("Date fin       : " + r.getDateFin().format(FORMATTER) + "\n");
            writer.write("Durée (h)      : " + dureeHeures + "\n");
            writer.write("Tarif horaire  : " + borne.getTarifHoraire() + " €/h\n");
            writer.write("Total estimé   : " + total + " €\n");
        } catch (IOException e) {
            System.out.println("Erreur lors de la génération du reçu : " + e.getMessage());
        }
    }

    /**
     * Trouve une borne de recharge par son ID.
     *
     * @param idBorne L'ID de la borne à trouver.
     * @return La borne de recharge correspondante, ou null si introuvable.
     */
    private BorneRecharge trouverBorneParId(String idBorne) {
        for (LieuRecharge lieu : lieuRepository.findAll()) {
            for (BorneRecharge borne : lieu.getBornes()) {
                if (borne.getId().equals(idBorne)) return borne;
            }
        }
        return null;
    }
}
