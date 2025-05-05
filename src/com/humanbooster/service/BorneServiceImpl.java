package src.com.humanbooster.service;

import src.com.humanbooster.model.BorneRecharge;
import src.com.humanbooster.model.LieuRecharge;
import src.com.humanbooster.repository.LieuRepository;
import src.com.humanbooster.repository.ReservationRepository;

import java.util.List;

public class BorneServiceImpl implements BorneService {

    private final LieuRepository lieuRepository;
    private final ReservationRepository reservationRepository;

    public BorneServiceImpl(LieuRepository lieuRepository, ReservationRepository reservationRepository) {
        this.lieuRepository = lieuRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public boolean ajouterBorne(String idLieu, BorneRecharge borne) {
        LieuRecharge lieu = lieuRepository.findById(idLieu);
        if (lieu == null) return false;

        if (lieu.getBornes().stream().anyMatch(b -> b.getId().equals(borne.getId()))) {
            return false; // borne déjà existante
        }

        lieu.ajouterBorne(borne);
        lieuRepository.save(lieu);
        return true;
    }

    @Override
    public boolean modifierBorne(String idLieu, BorneRecharge borneModifiee) {
        LieuRecharge lieu = lieuRepository.findById(idLieu);
        if (lieu == null) return false;

        List<BorneRecharge> bornes = lieu.getBornes();
        for (int i = 0; i < bornes.size(); i++) {
            if (bornes.get(i).getId().equals(borneModifiee.getId())) {
                bornes.set(i, borneModifiee);
                lieuRepository.save(lieu);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean supprimerBorne(String idLieu, String idBorne) {
        LieuRecharge lieu = lieuRepository.findById(idLieu);
        if (lieu == null) return false;

        // Vérifie s'il existe une réservation FUTURE sur cette borne
        boolean hasFutureReservation = reservationRepository.findByBorneId(idBorne).stream()
                .anyMatch(r -> r.getDateFin().isAfter(java.time.LocalDateTime.now()));

        if (hasFutureReservation) {
            System.out.println("Impossible de supprimer cette borne : des réservations futures existent.");
            return false;
        }

        boolean removed = lieu.getBornes().removeIf(b -> b.getId().equals(idBorne));
        if (removed) {
            lieuRepository.save(lieu);
            return true;
        }
        return false;
    }

    @Override
    public List<BorneRecharge> listerBornesParLieu(String idLieu) {
        LieuRecharge lieu = lieuRepository.findById(idLieu);
        return (lieu != null) ? lieu.getBornes() : List.of();
    }

    @Override
    public BorneRecharge trouverBorneParId(String idLieu, String idBorne) {
        LieuRecharge lieu = lieuRepository.findById(idLieu);
        if (lieu == null) return null;

        return lieu.getBornes().stream()
                .filter(b -> b.getId().equals(idBorne))
                .findFirst()
                .orElse(null);
    }
}
