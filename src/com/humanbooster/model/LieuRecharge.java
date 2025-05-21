package src.com.humanbooster.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe LieuRecharge
 * Décrit un lieu de recharge avec ses attributs et méthodes
 */
@Entity
@Table(name = "lieux_recharge")
public class LieuRecharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;

    @OneToMany(mappedBy = "lieuRecharge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorneRecharge> bornes;

    /**
     * Constructeur par défaut de la classe LieuRecharge
     */
    public LieuRecharge() {}

    /**
     * Constructeur de la classe LieuRecharge
     *
     * @param nom     Le nom du lieu de recharge
     * @param adresse L'adresse du lieu de recharge
     */
    public LieuRecharge(String nom, String adresse, List<BorneRecharge> bornes) {
        this.nom = nom;
        this.adresse = adresse;
        this.bornes = bornes != null ? bornes : new ArrayList<>();
    }

    /**
     * Récupère l'identifiant unique du lieu de recharge
     *
     * @return L'identifiant unique du lieu de recharge
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique du lieu de recharge
     *
     * @param id L'identifiant unique du lieu de recharge
     */
    public void setId(Long id) {
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

    /**
     * Ajoute une borne de recharge à ce lieu
     *
     * @param borne La borne de recharge à ajouter
     */
    public void ajouterBorne(BorneRecharge borne) {
        this.bornes.add(borne);
    }

    /**
     * Suppression d'une borne de recharge par son identifiant
     *
     * @param borneId L'identifiant de la borne de recharge à supprimer
     */
    public void supprimerBorne(String borneId) {
        this.bornes.removeIf(b -> b.getId().equals(borneId));
    }
}
