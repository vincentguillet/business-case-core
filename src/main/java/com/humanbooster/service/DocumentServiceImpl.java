package com.humanbooster.service;

import com.humanbooster.dao.LieuRechargeDao;
import com.humanbooster.model.BorneRecharge;
import com.humanbooster.model.LieuRecharge;
import com.humanbooster.model.Reservation;

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

    private final LieuRechargeDao lieuDao;

    /**
     * Constructeur de la classe DocumentServiceImpl.
     *
     * @param lieuRechargeDao Le dao des lieux de recharge.
     */
    public DocumentServiceImpl(LieuRechargeDao lieuRechargeDao) {
        this.lieuDao = lieuRechargeDao;
    }

    /**
     * Génère un reçu de réservation au format texte.
     *
     * @param r La réservation pour laquelle générer le reçu.
     */
    @Override
    public void genererRecuReservation(Reservation r) {
        BorneRecharge borne = trouverBorneParId(r.getBorneRecharge().getId());
        if (borne == null) {
            System.out.println("Impossible de générer le reçu : borne introuvable.");
            return;
        }

        long dureeHeures = Duration.between(r.getDateDebut(), r.getDateFin()).toHours();
        double total = dureeHeures * borne.getTarifHoraire();

        File dir = new File(EXPORT_DIR);
        if (!dir.exists()) dir.mkdirs();

        File recu = new File(dir, "recu_" + r.hashCode() + ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(recu))) {
            writer.write("=== Reçu de réservation ===\n");
            writer.write("ID Réservation : " + r.getId().hashCode() + "\n");
            writer.write("ID Borne       : " + r.getBorneRecharge().hashCode() + "\n");
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
    private BorneRecharge trouverBorneParId(Long idBorne) {
        for (LieuRecharge lieu : lieuDao.readAll()) {
            for (BorneRecharge borne : lieu.getBornes()) {
                if (borne.getId().equals(idBorne)) return borne;
            }
        }
        return null;
    }
}
