package src.com.humanbooster.service;

import src.com.humanbooster.dao.LieuRechargeDao;
import src.com.humanbooster.dao.ReservationDao;
import src.com.humanbooster.model.BorneRecharge;
import src.com.humanbooster.model.LieuRecharge;

import java.util.List;

/**
 * Implémentation du service de gestion des bornes de recharge.
 */
public class BorneServiceImpl implements BorneService {

    private final LieuRechargeDao lieuDao;
    private final ReservationDao reservationDao;

    /**
     * Constructeur de la classe BorneServiceImpl.
     *
     * @param lieuRechargeDao        Le dao pour les lieux de recharge.
     * @param reservationDao  Le dao pour les réservations.
     */
    public BorneServiceImpl(LieuRechargeDao lieuRechargeDao, ReservationDao reservationDao) {
        this.lieuDao = lieuRechargeDao;
        this.reservationDao = reservationDao;
    }

    /**
     * Ajoute une borne de recharge à un lieu donné.
     *
     * @param idLieu  L'identifiant du lieu de recharge.
     * @param borne    La borne de recharge à ajouter.
     * @return true si la borne a été ajoutée avec succès, false sinon.
     */
    @Override
    public boolean ajouterBorne(Long idLieu, BorneRecharge borne) {
        LieuRecharge lieu = lieuDao.readById(idLieu);
        if (lieu == null) return false;

        if (lieu.getBornes().stream().anyMatch(b -> b.getId().equals(borne.getId()))) {
            return false; // borne déjà existante
        }

        lieu.ajouterBorne(borne);
        lieuDao.create(lieu);
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
    public boolean modifierBorne(Long idLieu, BorneRecharge borneModifiee) {
        LieuRecharge lieu = lieuDao.readById(idLieu);
        if (lieu == null) return false;

        List<BorneRecharge> bornes = lieu.getBornes();
        for (int i = 0; i < bornes.size(); i++) {
            if (bornes.get(i).getId().equals(borneModifiee.getId())) {
                bornes.set(i, borneModifiee);
                lieuDao.update(lieu);
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
    public boolean supprimerBorne(Long idLieu, Long idBorne) {
        LieuRecharge lieu = lieuDao.readById(idLieu);
        if (lieu == null) return false;

        // Vérifie s'il existe une réservation FUTURE sur cette borne
        boolean hasFutureReservation = reservationDao.findByBorneId(idBorne).stream()
                .anyMatch(r -> r.getDateFin().isAfter(java.time.LocalDateTime.now()));

        if (hasFutureReservation) {
            System.out.println("Impossible de supprimer cette borne : des réservations futures existent.");
            return false;
        }

        boolean removed = lieu.getBornes().removeIf(b -> b.getId().equals(idBorne));
        if (removed) {
            lieuDao.update(lieu);
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
    public List<BorneRecharge> listerBornesParLieu(Long idLieu) {
        LieuRecharge lieu = lieuDao.readById(idLieu);
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
    public BorneRecharge trouverBorneParId(Long idLieu, Long idBorne) {
        LieuRecharge lieu = lieuDao.readById(idLieu);
        if (lieu == null) return null;

        return lieu.getBornes().stream()
                .filter(b -> b.getId().equals(idBorne))
                .findFirst()
                .orElse(null);
    }
}
