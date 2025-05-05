package src.com.humanbooster.model;

public class Utilisateur {

    private int id;
    private String email;
    private String motDePasse;
    private int codeValidation;
    private boolean estValide;

    public Utilisateur(int id, String email, String motDePasse, int codeValidation, boolean estValide) {
        this.id = id;
        this.email = email;
        this.motDePasse = motDePasse;
        this.codeValidation = codeValidation;
        this.estValide = estValide;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getCodeValidation() {
        return codeValidation;
    }

    public void setCodeValidation(int codeValidation) {
        this.codeValidation = codeValidation;
    }

    public boolean isEstValide() {
        return estValide;
    }

    public void setEstValide(boolean estValide) {
        this.estValide = estValide;
    }
}
