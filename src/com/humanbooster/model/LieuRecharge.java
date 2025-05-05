package src.com.humanbooster.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe LieuRecharge
 * Décrit un lieu de recharge avec ses attributs et méthodes
 */
public class LieuRecharge {

    private String id;
    private String nom;
    private String adresse;
    private List<BorneRecharge> bornes;

    /**
     * Constructeur de la classe LieuRecharge
     *
     * @param id     Identifiant unique du lieu de recharge
     * @param nom    Nom du lieu de recharge
     */
    public LieuRecharge(String id, String nom, String adresse) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.bornes = new ArrayList<>();
    }

    /**
     * Récupère l'identifiant unique du lieu de recharge
     *
     * @return L'identifiant unique du lieu de recharge
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique du lieu de recharge
     *
     * @param id L'identifiant unique du lieu de recharge
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Récupère le nom du lieu de recharge
     *
     * @return Le nom du lieu de recharge
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du lieu de recharge
     *
     * @param nom Le nom du lieu de recharge
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Récupère l'adresse du lieu de recharge
     *
     * @return L'adresse du lieu de recharge
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Définit l'adresse du lieu de recharge
     *
     * @param adresse L'adresse du lieu de recharge
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Récupère la liste des bornes de recharge associées à ce lieu
     *
     * @return La liste des bornes de recharge
     */
    public List<BorneRecharge> getBornes() {
        return bornes;
    }

    /**
     * Définit la liste des bornes de recharge associées à ce lieu
     *
     * @param bornes La liste des bornes de recharge
     */
    public void setBornes(List<BorneRecharge> bornes) {
        this.bornes = bornes;
    }

    public void ajouterBorne(BorneRecharge borne) {
        this.bornes.add(borne);
    }

    public void supprimerBorne(String borneId) {
        this.bornes.removeIf(b -> b.getId().equals(borneId));
    }
}
