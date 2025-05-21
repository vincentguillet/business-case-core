package com.humanbooster.service;

import com.humanbooster.model.Utilisateur;

/**
 * Interface pour le service d'authentification.
 * Fournit des méthodes pour inscrire, connecter, déconnecter un utilisateur,
 * valider un compte et vérifier l'état de connexion.
 */
public interface AuthService {

    Utilisateur inscrire(String email, String motDePasse);
    boolean validerCompte(String email, String code);
    Utilisateur connecter(String email, String motDePasse);
    void deconnecter();
    Utilisateur getUtilisateurConnecte();
    boolean estConnecte();
    boolean estCompteValide(String email);

}
