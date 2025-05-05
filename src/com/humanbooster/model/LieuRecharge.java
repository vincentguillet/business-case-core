package src.com.humanbooster.model;

import java.util.List;

public class LieuRecharge {

    private int id;
    private String nom;
    private List<BorneRecharge> bornes;

    public LieuRecharge(int id, String nom, List<BorneRecharge> bornes) {
        this.id = id;
        this.nom = nom;
        this.bornes = bornes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<BorneRecharge> getBornes() {
        return bornes;
    }

    public void setBornes(List<BorneRecharge> bornes) {
        this.bornes = bornes;
    }
}
