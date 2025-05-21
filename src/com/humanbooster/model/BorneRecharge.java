package src.com.humanbooster.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * Classe BorneRecharge
 * Décrit une borne de recharge avec ses attributs et méthodes
 */
@Entity
@Table(name = "bornes_recharge")
public class BorneRecharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EtatBorne etat;

    private double tarifHoraire;

    @ManyToOne
    @JoinColumn(name = "lieu_recharge_id")
    private LieuRecharge lieuRecharge;

    @OneToMany(mappedBy = "borneRecharge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    /**
     * Constructeur par défaut de la classe BorneRecharge
     */
    public BorneRecharge() {}

    /**
     * Constructeur de la classe BorneRecharge
     *
     * @param etat        L'état de la borne de recharge
     * @param tarifHoraire Le tarif horaire de la borne de recharge (en centimes)
     * @param lieuRecharge        Le lieu de recharge associé à la borne
     */
    public BorneRecharge(EtatBorne etat, double tarifHoraire, LieuRecharge lieuRecharge) {
        this.etat = etat;
        this.tarifHoraire = tarifHoraire;
        this.lieuRecharge = lieuRecharge;
    }

    /**
     * Récupère l'identifiant unique de la borne de recharge
     * @return L'identifiant unique de la borne de recharge
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de la borne de recharge
     * @param id L'identifiant unique de la borne de recharge
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Récupère l'état de la borne de recharge
     * @return L'état de la borne de recharge
     */
    public EtatBorne getEtat() {
        return etat;
    }

    /**
     * Définit l'état de la borne de recharge
     * @param etat L'état de la borne de recharge
     */
    public void setEtat(EtatBorne etat) {
        this.etat = etat;
    }

    /**
     * Récupère le tarif horaire de la borne de recharge
     * @return Le tarif horaire de la borne de recharge (en centimes)
     */
    public double getTarifHoraire() {
        return tarifHoraire;
    }

    /**
     * Définit le tarif horaire de la borne de recharge
     * @param tarifHoraire Le tarif horaire de la borne de recharge (en centimes)
     */
    public void setTarifHoraire(double tarifHoraire) {
        this.tarifHoraire = tarifHoraire;
    }

    /**
     * Récupère le lieu de recharge associé à la borne
     * @return Le lieu de recharge associé à la borne
     */
    public LieuRecharge getLieuRecharge() {
        return lieuRecharge;
    }

    /**
     * Définit le lieu de recharge associé à la borne
     * @param lieuRecharge Le lieu de recharge associé à la borne
     */
    public void setLieuRecharge(LieuRecharge lieuRecharge) {
        this.lieuRecharge = lieuRecharge;
    }
}
