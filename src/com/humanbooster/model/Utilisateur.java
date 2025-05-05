package src.com.humanbooster.model;

/**
 * Classe Utilisateur
 * Décrit un utilisateur avec ses attributs et méthodes
 */
public class Utilisateur {

    private int id;
    private String email;
    private String motDePasse;
    private int codeValidation;
    private boolean estValide;

    /**
     * Constructeur de la classe Utilisateur
     *
     * @param id            Identifiant unique de l'utilisateur
     * @param email         Adresse e-mail de l'utilisateur
     * @param motDePasse    Mot de passe de l'utilisateur
     * @param codeValidation Code de validation de l'utilisateur
     * @param estValide     Indique si l'utilisateur est valide ou non
     */
    public Utilisateur(int id, String email, String motDePasse, int codeValidation, boolean estValide) {
        this.id = id;
        this.email = email;
        this.motDePasse = motDePasse;
        this.codeValidation = codeValidation;
        this.estValide = estValide;
    }

    /**
     * Récupère l'identifiant unique de l'utilisateur
     * @return L'identifiant unique de l'utilisateur
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de l'utilisateur
     * @param id L'identifiant unique de l'utilisateur
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Récupère l'adresse e-mail de l'utilisateur
     * @return L'adresse e-mail de l'utilisateur
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'adresse e-mail de l'utilisateur
     * @param email L'adresse e-mail de l'utilisateur
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Récupère le mot de passe de l'utilisateur
     * @return Le mot de passe de l'utilisateur
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Définit le mot de passe de l'utilisateur
     * @param motDePasse Le mot de passe de l'utilisateur
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * Récupère le code de validation de l'utilisateur
     * @return Le code de validation de l'utilisateur
     */
    public int getCodeValidation() {
        return codeValidation;
    }

    /**
     * Définit le code de validation de l'utilisateur
     * @param codeValidation Le code de validation de l'utilisateur
     */
    public void setCodeValidation(int codeValidation) {
        this.codeValidation = codeValidation;
    }

    /**
     * Vérifie si l'utilisateur est valide
     * @return true si l'utilisateur est valide, false sinon
     */
    public boolean isEstValide() {
        return estValide;
    }

    /**
     * Définit si l'utilisateur est valide
     * @param estValide true si l'utilisateur est valide, false sinon
     */
    public void setEstValide(boolean estValide) {
        this.estValide = estValide;
    }
}
