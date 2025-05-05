package src.com.humanbooster.service;

import src.com.humanbooster.model.*;
import src.com.humanbooster.repository.LieuRepository;
import src.com.humanbooster.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final LieuRepository lieuRepository;
    private final DocumentService documentService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, LieuRepository lieuRepository, DocumentService documentService) {
        this.reservationRepository = reservationRepository;
        this.lieuRepository = lieuRepository;
        this.documentService = documentService;
    }

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

    @Override
    public boolean accepterReservation(String idReservation) {
        Reservation r = reservationRepository.findById(idReservation);
        if (r == null || r.getStatut() != StatutReservation.EN_ATTENTE) return false;

        r.setStatut(StatutReservation.ACCEPTEE);
        reservationRepository.save(r);

        documentService.genererRecuReservation(r); // génération du reçu

        return true;
    }

    @Override
    public boolean refuserReservation(String idReservation) {
        Reservation r = reservationRepository.findById(idReservation);
        if (r == null || r.getStatut() != StatutReservation.EN_ATTENTE) return false;

        r.setStatut(StatutReservation.REFUSEE);
        reservationRepository.save(r);
        return true;
    }

    @Override
    public List<Reservation> getReservationsUtilisateur(String idUtilisateur) {
        List<Reservation> toutes = reservationRepository.findAll();
        return toutes.stream()
                .filter(r -> idUtilisateur.equals(r.getIdUtilisateur()))
                .toList();
    }

    @Override
    public List<Reservation> getToutesReservations() {
        return reservationRepository.findAll();
    }
}
