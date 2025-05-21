package src.com.humanbooster.service;

import src.com.humanbooster.model.LieuRecharge;
import src.com.humanbooster.dao.LieuDao;

import java.util.List;
import java.util.UUID;

/**
 * Implémentation du service de gestion des lieux de recharge.
 */
public class LieuServiceImpl implements LieuService {

    private final LieuDao lieuDao;

    /**
     * Constructeur de la classe LieuServiceImpl.
     *
     * @param lieuDao Le dépôt de lieux de recharge.
     */
    public LieuServiceImpl(LieuDao lieuDao) {
        this.lieuDao = lieuDao;
    }

    /**
     * Ajoute un nouveau lieu de recharge.
     *
     * @param nom      Le nom du lieu.
     * @param adresse  L'adresse du lieu.
     * @return Le lieu de recharge ajouté.
     */
    @Override
    public LieuRecharge ajouterLieu(String nom, String adresse) {
        LieuRecharge lieu = new LieuRecharge(UUID.randomUUID().toString(), nom, adresse);
        lieuDao.save(lieu);
        return lieu;
    }

    /**
     * Modifie un lieu de recharge existant.
     *
     * @param id            L'identifiant du lieu à modifier.
     * @param nouveauNom    Le nouveau nom du lieu.
     * @param nouvelleAdresse La nouvelle adresse du lieu.
     * @return true si la modification a réussi, false sinon.
     */
    @Override
    public boolean modifierLieu(String id, String nouveauNom, String nouvelleAdresse) {
        LieuRecharge lieu = lieuDao.findById(id);
        if (lieu == null) return false;

        lieu.setNom(nouveauNom);
        lieu.setAdresse(nouvelleAdresse);
        lieuDao.save(lieu);
        return true;
    }

    /**
     * Supprime un lieu de recharge.
     *
     * @param id L'identifiant du lieu à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    @Override
    public boolean supprimerLieu(String id) {
        LieuRecharge lieu = lieuDao.findById(id);
        if (lieu == null) return false;

        if (!lieu.getBornes().isEmpty()) {
            System.out.println("Ce lieu contient des bornes. Veuillez d'abord les supprimer.");
            return false;
        }

        lieuDao.deleteById(id);
        return true;
    }

    /**
     * Liste tous les lieux de recharge.
     *
     * @return La liste des lieux de recharge.
     */
    @Override
    public List<LieuRecharge> listerLieux() {
        return lieuDao.findAll();
    }

    /**
     * Trouve un lieu de recharge par son identifiant.
     *
     * @param id L'identifiant du lieu à trouver.
     * @return Le lieu de recharge trouvé, ou null s'il n'existe pas.
     */
    @Override
    public LieuRecharge trouverParId(String id) {
        return lieuDao.findById(id);
    }
}
