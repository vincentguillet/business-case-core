package src.com.humanbooster.model;

public class BorneRecharge {

    private int id;
    private EtatBorne etat;
    private int tarifHoraire; // Exprimé en centimes

    public BorneRecharge(int id, EtatBorne etat, int tarifHoraire) {
        this.id = id;
        this.etat = etat;
        this.tarifHoraire = tarifHoraire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EtatBorne getEtat() {
        return etat;
    }

    public void setEtat(EtatBorne etat) {
        this.etat = etat;
    }

    public int getTarifHoraire() {
        return tarifHoraire;
    }

    public void setTarifHoraire(int tarifHoraire) {
        this.tarifHoraire = tarifHoraire;
    }
}
