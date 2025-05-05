package src.com.humanbooster.model;

import java.util.List;

/**
 * Classe LieuRecharge
 * Décrit un lieu de recharge avec ses attributs et méthodes
 */
public class LieuRecharge {

    private int id;
    private String nom;
    private List<BorneRecharge> bornes;

    /**
     * Constructeur de la classe LieuRecharge
     *
     * @param id    Identifiant unique du lieu de recharge
     * @param nom   Nom du lieu de recharge
     * @param bornes Liste des bornes de recharge associées à ce lieu
     */
    public LieuRecharge(int id, String nom, List<BorneRecharge> bornes) {
        this.id = id;
        this.nom = nom;
        this.bornes = bornes;
    }

    /**
     * Récupère l'identifiant unique du lieu de recharge
     * @return L'identifiant unique du lieu de recharge
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique du lieu de recharge
     * @param id L'identifiant unique du lieu de recharge
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Récupère le nom du lieu de recharge
     * @return Le nom du lieu de recharge
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du lieu de recharge
     * @param nom Le nom du lieu de recharge
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Récupère la liste des bornes de recharge associées à ce lieu
     * @return La liste des bornes de recharge
     */
    public List<BorneRecharge> getBornes() {
        return bornes;
    }

    /**
     * Définit la liste des bornes de recharge associées à ce lieu
     * @param bornes La liste des bornes de recharge
     */
    public void setBornes(List<BorneRecharge> bornes) {
        this.bornes = bornes;
    }
}
