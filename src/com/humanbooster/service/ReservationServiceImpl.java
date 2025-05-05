package src.com.humanbooster.service;

import src.com.humanbooster.model.*;
import src.com.humanbooster.repository.LieuRepository;
import src.com.humanbooster.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implémentation du service de réservation.
 */
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final LieuRepository lieuRepository;
    private final DocumentService documentService;

    /**
     * Constructeur de la classe ReservationServiceImpl.
     *
     * @param reservationRepository le dépôt de réservations
     * @param lieuRepository        le dépôt de lieux
     * @param documentService       le service de documents
     */
    public ReservationServiceImpl(ReservationRepository reservationRepository, LieuRepository lieuRepository, DocumentService documentService) {
        this.reservationRepository = reservationRepository;
        this.lieuRepository = lieuRepository;
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
        List<Reservation> toutes = reservationRepository.findAll();
        List<Reservation> conflits = new ArrayList<>();

        for (Reservation r : toutes) {
            if (r.getStatut() == StatutReservation.ACCEPTEE &&
                    r.getDateFin().isAfter(debut) && r.getDateDebut().isBefore(fin)) {
                conflits.add(r);
            }
        }

        List<String> bornesIndisponibles = conflits.stream()
                .map(Reservation::getIdBorne)
                .distinct()
                .toList();

        List<Reservation> bornesDisponibles = new ArrayList<>();
        List<LieuRecharge> lieux = lieuRepository.findAll();

        for (LieuRecharge lieu : lieux) {
            for (BorneRecharge borne : lieu.getBornes()) {
                if (!bornesIndisponibles.contains(borne.getId()) && borne.getEtat() == EtatBorne.DISPONIBLE) {
                    Reservation r = new Reservation(
                            "DISPONIBLE",
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
    public Reservation creerReservation(String idUtilisateur, String idBorne, LocalDateTime debut, LocalDateTime fin) {
        Reservation r = new Reservation(
                UUID.randomUUID().toString(),
                idUtilisateur,
                idBorne,
                debut,
                fin,
                StatutReservation.EN_ATTENTE
        );
        reservationRepository.save(r);
        return r;
    }

    /**
     * Accepte une réservation.
     *
     * @param idReservation l'identifiant de la réservation
     * @return true si la réservation a été acceptée, false sinon
     */
    @Override
    public boolean accepterReservation(String idReservation) {
        Reservation r = reservationRepository.findById(idReservation);
        if (r == null || r.getStatut() != StatutReservation.EN_ATTENTE) return false;

        r.setStatut(StatutReservation.ACCEPTEE);
        reservationRepository.save(r);

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
    public boolean refuserReservation(String idReservation) {
        Reservation r = reservationRepository.findById(idReservation);
        if (r == null || r.getStatut() != StatutReservation.EN_ATTENTE) return false;

        r.setStatut(StatutReservation.REFUSEE);
        reservationRepository.save(r);
        return true;
    }

    /**
     * Récupère toutes les réservations d'un utilisateur.
     *
     * @param idUtilisateur L'identifiant de l'utilisateur
     * @return La liste des réservations de l'utilisateur
     */
    @Override
    public List<Reservation> getReservationsUtilisateur(String idUtilisateur) {
        List<Reservation> toutes = reservationRepository.findAll();
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
        return reservationRepository.findAll();
    }
}
