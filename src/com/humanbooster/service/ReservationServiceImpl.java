package src.com.humanbooster.service;

import src.com.humanbooster.dao.LieuRechargeDao;
import src.com.humanbooster.dao.ReservationDao;
import src.com.humanbooster.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implémentation du service de réservation.
 */
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;
    private final LieuRechargeDao lieuDao;
    private final DocumentService documentService;

    /**
     * Constructeur de la classe ReservationServiceImpl.
     *
     * @param reservationDao le dépôt de réservations
     * @param lieuRechargeDao        le dépôt de lieux
     * @param documentService       le service de documents
     */
    public ReservationServiceImpl(ReservationDao reservationDao, LieuRechargeDao lieuRechargeDao, DocumentService documentService) {
        this.reservationDao = reservationDao;
        this.lieuDao = lieuRechargeDao;
        this.documentService = documentService;
    }

    /**
     * Cherche les bornes disponibles entre deux dates.
     *
     * @param debut la date de début
     * @param fin   la date de fin
     * @return une liste de réservations disponibles
     */
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
                .map(Reservation::getIdBorne)
                .distinct()
                .toList();

        List<Reservation> bornesDisponibles = new ArrayList<>();
        List<LieuRecharge> lieux = lieuDao.readAll();

        for (LieuRecharge lieu : lieux) {
            for (BorneRecharge borne : lieu.getBornes()) {
                if (!bornesIndisponibles.contains(borne.getId()) && borne.getEtat() == EtatBorne.DISPONIBLE) {
                    Reservation r = new Reservation(
                            Long.valueOf(UUID.randomUUID().toString()),
                            null,
                            borne.getId(),
                            debut,
                            fin,
                            StatutReservation.EN_ATTENTE
                    );
                    bornesDisponibles.add(r);
                }
            }
        }

        return bornesDisponibles;
    }

    /**
     * Crée une réservation.
     *
     * @param idUtilisateur l'identifiant de l'utilisateur
     * @param idBorne       l'identifiant de la borne
     * @param debut         la date de début
     * @param fin           la date de fin
     * @return la réservation créée
     */
    @Override
    public Reservation creerReservation(Long idUtilisateur, Long idBorne, LocalDateTime debut, LocalDateTime fin) {
        Reservation r = new Reservation(
                Long.valueOf(UUID.randomUUID().toString()),
                idUtilisateur,
                idBorne,
                debut,
                fin,
                StatutReservation.EN_ATTENTE
        );
        reservationDao.create(r);
        return r;
    }

    /**
     * Accepte une réservation.
     *
     * @param idReservation l'identifiant de la réservation
     * @return true si la réservation a été acceptée, false sinon
     */
    @Override
    public boolean accepterReservation(Long idReservation) {
        Reservation r = reservationDao.readById(idReservation);
        if (r == null || r.getStatut() != StatutReservation.EN_ATTENTE) return false;

        r.setStatut(StatutReservation.ACCEPTEE);
        reservationDao.update(r);

        documentService.genererRecuReservation(r); // génération du reçu

        return true;
    }

    /**
     * Refuser une réservation.
     *
     * @param idReservation l'identifiant de la réservation
     * @return true si la réservation a été refusée, false sinon
     */
    @Override
    public boolean refuserReservation(Long idReservation) {
        Reservation r = reservationDao.readById(idReservation);
        if (r == null || r.getStatut() != StatutReservation.EN_ATTENTE) return false;

        r.setStatut(StatutReservation.REFUSEE);
        reservationDao.update(r);
        return true;
    }

    /**
     * Récupère toutes les réservations d'un utilisateur.
     *
     * @param idUtilisateur L'identifiant de l'utilisateur
     * @return La liste des réservations de l'utilisateur
     */
    @Override
    public List<Reservation> getReservationsUtilisateur(Long idUtilisateur) {
        List<Reservation> toutes = reservationDao.readAll();
        return toutes.stream()
                .filter(r -> idUtilisateur.equals(r.getIdUtilisateur()))
                .toList();
    }

    /**
     * Récupère toutes les réservations.
     *
     * @return La liste de toutes les réservations
     */
    @Override
    public List<Reservation> getToutesReservations() {
        return reservationDao.readAll();
    }
}
