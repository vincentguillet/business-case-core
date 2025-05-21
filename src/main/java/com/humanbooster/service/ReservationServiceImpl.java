package com.humanbooster.service;

import com.humanbooster.dao.LieuRechargeDao;
import com.humanbooster.dao.ReservationDao;
import com.humanbooster.dao.UtilisateurDao;
import com.humanbooster.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du service de réservation.
 */
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;
    private final LieuRechargeDao lieuDao;
    private final UtilisateurDao utilisateurDao;
    private final DocumentService documentService;

    /**
     * Constructeur de la classe ReservationServiceImpl.
     */
    public ReservationServiceImpl(ReservationDao reservationDao, LieuRechargeDao lieuRechargeDao,
                                  UtilisateurDao utilisateurDao, DocumentService documentService) {
        this.reservationDao = reservationDao;
        this.lieuDao = lieuRechargeDao;
        this.utilisateurDao = utilisateurDao;
        this.documentService = documentService;
    }

    @Override
    public List<Reservation> chercherBornesDisponibles(LocalDateTime debut, LocalDateTime fin) {
        List<Reservation> toutes = reservationDao.readAll();
        List<Reservation> conflits = new ArrayList<>();

        for (Reservation r : toutes) {
            if (r.getStatut() == StatutReservation.ACCEPTEE &&
                    r.getDateFin().isAfter(debut) && r.getDateDebut().isBefore(fin)) {
                conflits.add(r);
            }
        }

        List<Long> bornesIndisponibles = conflits.stream()
                .map(r -> r.getBorneRecharge().getId())
                .distinct()
                .toList();

        List<Reservation> bornesDisponibles = new ArrayList<>();
        List<LieuRecharge> lieux = lieuDao.readAll();

        for (LieuRecharge lieu : lieux) {
            for (BorneRecharge borne : lieu.getBornes()) {
                if (!bornesIndisponibles.contains(borne.getId()) && borne.getEtat() == EtatBorne.DISPONIBLE) {
                    // On crée une réservation "simulée" (non persistée) pour afficher les créneaux disponibles
                    Reservation r = new Reservation(debut, fin, StatutReservation.EN_ATTENTE, null, borne);
                    bornesDisponibles.add(r);
                }
            }
        }

        return bornesDisponibles;
    }

    @Override
    public Reservation creerReservation(Long idUtilisateur, Long idBorne, LocalDateTime debut, LocalDateTime fin) {
        Utilisateur utilisateur = utilisateurDao.readById(idUtilisateur);
        if (utilisateur == null) return null;

        // Recherche de la borne dans tous les lieux
        BorneRecharge borneTrouvee = null;
        for (LieuRecharge lieu : lieuDao.readAll()) {
            for (BorneRecharge b : lieu.getBornes()) {
                if (b.getId().equals(idBorne)) {
                    borneTrouvee = b;
                    break;
                }
            }
            if (borneTrouvee != null) break;
        }

        if (borneTrouvee == null) return null;

        Reservation r = new Reservation(debut, fin, StatutReservation.EN_ATTENTE, utilisateur, borneTrouvee);
        reservationDao.create(r);
        return r;
    }

    @Override
    public boolean accepterReservation(Long idReservation) {
        Reservation r = reservationDao.readById(idReservation);
        if (r == null || r.getStatut() != StatutReservation.EN_ATTENTE) return false;

        r.setStatut(StatutReservation.ACCEPTEE);
        reservationDao.update(r);
        documentService.genererRecuReservation(r);
        return true;
    }

    @Override
    public boolean refuserReservation(Long idReservation) {
        Reservation r = reservationDao.readById(idReservation);
        if (r == null || r.getStatut() != StatutReservation.EN_ATTENTE) return false;

        r.setStatut(StatutReservation.REFUSEE);
        reservationDao.update(r);
        return true;
    }

    @Override
    public List<Reservation> getReservationsUtilisateur(Long idUtilisateur) {
        Utilisateur utilisateur = utilisateurDao.readById(idUtilisateur);
        if (utilisateur == null) return List.of();

        return reservationDao.readAll().stream()
                .filter(r -> r.getUtilisateur() != null && idUtilisateur.equals(r.getUtilisateur().getId()))
                .toList();
    }

    @Override
    public List<Reservation> getToutesReservations() {
        return reservationDao.readAll();
    }
}