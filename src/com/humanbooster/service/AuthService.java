package src.com.humanbooster.service;

import src.com.humanbooster.model.Utilisateur;

public interface AuthService {

    Utilisateur inscrire(String email, String motDePasse);
    boolean validerCompte(String email, String code);
    Utilisateur connecter(String email, String motDePasse);
    void deconnecter();
    Utilisateur getUtilisateurConnecte();
    boolean estConnecte();
    boolean estCompteValide(String email);

}
