package src.com.humanbooster.model;

/**
 * Classe BorneRecharge
 * Décrit une borne de recharge avec ses attributs et méthodes
 */
public class BorneRecharge {

    private String id;
    private EtatBorne etat;
    private double tarifHoraire;

    /**
     * Constructeur de la classe BorneRecharge
     *
     * @param id         Identifiant unique de la borne de recharge
     * @param etat       État de la borne de recharge (DISPONIBLE, OCCUPEE, EN_PANNE)
     * @param tarifHoraire Tarif horaire de la borne de recharge (en centimes)
     */
    public BorneRecharge(String id, EtatBorne etat, double tarifHoraire) {
        this.id = id;
        this.etat = etat;
        this.tarifHoraire = tarifHoraire;
    }

    /**
     * Récupère l'identifiant unique de la borne de recharge
     * @return L'identifiant unique de la borne de recharge
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de la borne de recharge
     * @param id L'identifiant unique de la borne de recharge
     */
    public void setId(String id) {
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
}
