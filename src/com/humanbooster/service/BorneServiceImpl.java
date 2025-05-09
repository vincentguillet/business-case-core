package src.com.humanbooster.service;

import src.com.humanbooster.model.BorneRecharge;
import src.com.humanbooster.model.LieuRecharge;
import src.com.humanbooster.repository.LieuRepository;
import src.com.humanbooster.repository.ReservationRepository;

import java.util.List;

/**
 * Implémentation du service de gestion des bornes de recharge.
 */
public class BorneServiceImpl implements BorneService {

    private final LieuRepository lieuRepository;
    private final ReservationRepository reservationRepository;

    /**
     * Constructeur de la classe BorneServiceImpl.
     *
     * @param lieuRepository        Le repository pour les lieux de recharge.
     * @param reservationRepository  Le repository pour les réservations.
     */
    public BorneServiceImpl(LieuRepository lieuRepository, ReservationRepository reservationRepository) {
        this.lieuRepository = lieuRepository;
        this.reservationRepository = reservationRepository;
    }

    /**
     * Ajoute une borne de recharge à un lieu donné.
     *
     * @param idLieu  L'identifiant du lieu de recharge.
     * @param borne    La borne de recharge à ajouter.
     * @return true si la borne a été ajoutée avec succès, false sinon.
     */
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

    /**
     * Modifie une borne de recharge existante dans un lieu donné.
     *
     * @param idLieu          L'identifiant du lieu de recharge.
     * @param borneModifiee   La borne de recharge modifiée.
     * @return true si la borne a été modifiée avec succès, false sinon.
     */
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

    /**
     * Supprime une borne de recharge d'un lieu donné.
     *
     * @param idLieu  L'identifiant du lieu de recharge.
     * @param idBorne L'identifiant de la borne de recharge à supprimer.
     * @return true si la borne a été supprimée avec succès, false sinon.
     */
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

    /**
     * Liste toutes les bornes de recharge d'un lieu donné.
     *
     * @param idLieu L'identifiant du lieu de recharge.
     * @return La liste des bornes de recharge du lieu.
     */
    @Override
    public List<BorneRecharge> listerBornesParLieu(String idLieu) {
        LieuRecharge lieu = lieuRepository.findById(idLieu);
        return (lieu != null) ? lieu.getBornes() : List.of();
    }

    /**
     * Trouve une borne de recharge par son identifiant dans un lieu donné.
     *
     * @param idLieu  L'identifiant du lieu de recharge.
     * @param idBorne L'identifiant de la borne de recharge.
     * @return La borne de recharge trouvée, ou null si elle n'existe pas.
     */
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
