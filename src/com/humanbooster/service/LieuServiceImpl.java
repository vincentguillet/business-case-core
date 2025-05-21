package src.com.humanbooster.service;

import src.com.humanbooster.dao.LieuRechargeDao;
import src.com.humanbooster.model.LieuRecharge;

import java.util.List;
import java.util.UUID;

/**
 * Implémentation du service de gestion des lieux de recharge.
 */
public class LieuServiceImpl implements LieuService {

    private final LieuRechargeDao lieuDao;

    /**
     * Constructeur de la classe LieuServiceImpl.
     *
     * @param lieuRechargeDao Le dépôt de lieux de recharge.
     */
    public LieuServiceImpl(LieuRechargeDao lieuRechargeDao) {
        this.lieuDao = lieuRechargeDao;
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
        LieuRecharge lieu = new LieuRecharge(nom, adresse, null);
        lieuDao.create(lieu);
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
    public boolean modifierLieu(Long id, String nouveauNom, String nouvelleAdresse) {
        LieuRecharge lieu = lieuDao.readById(id);
        if (lieu == null) return false;

        lieu.setNom(nouveauNom);
        lieu.setAdresse(nouvelleAdresse);
        lieuDao.update(lieu);
        return true;
    }

    /**
     * Supprime un lieu de recharge.
     *
     * @param id L'identifiant du lieu à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    @Override
    public boolean supprimerLieu(Long id) {
        LieuRecharge lieu = lieuDao.readById(id);
        if (lieu == null) return false;

        if (!lieu.getBornes().isEmpty()) {
            System.out.println("Ce lieu contient des bornes. Veuillez d'abord les supprimer.");
            return false;
        }

        lieuDao.delete(id);
        return true;
    }

    /**
     * Liste tous les lieux de recharge.
     *
     * @return La liste des lieux de recharge.
     */
    @Override
    public List<LieuRecharge> listerLieux() {
        return lieuDao.readAll();
    }

    /**
     * Trouve un lieu de recharge par son identifiant.
     *
     * @param id L'identifiant du lieu à trouver.
     * @return Le lieu de recharge trouvé, ou null s'il n'existe pas.
     */
    @Override
    public LieuRecharge trouverParId(Long id) {
        return lieuDao.readById(id);
    }
}
