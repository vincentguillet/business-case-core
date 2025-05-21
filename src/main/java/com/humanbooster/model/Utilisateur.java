package com.humanbooster.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.List;

/**
 * Classe Utilisateur
 * Décrit un utilisateur avec ses attributs et méthodes
 */
@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Email(message = "L'adresse e-mail doit être valide")
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    @Column(nullable = false)
    private String codeValidation;

    private boolean valide;

    @Enumerated(EnumType.STRING)
    private RoleUtilisateur role;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    /**
     * Constructeur par défaut de la classe Utilisateur
     */
    public Utilisateur() {}

    /**
     * Constructeur de la classe Utilisateur
     *
     * @param email          Adresse e-mail de l'utilisateur
     * @param motDePasse     Mot de passe de l'utilisateur
     * @param codeValidation Code de validation de l'utilisateur
     * @param valide      Indique si l'utilisateur est valide
     * @param role           Rôle de l'utilisateur (ADMIN, USER)
     */
    public Utilisateur(String email, String motDePasse, String codeValidation, boolean valide, RoleUtilisateur role) {
        this.email = email;
        this.motDePasse = motDePasse;
        this.codeValidation = codeValidation;
        this.valide = valide;
        this.role = role;
    }

    /**
     * Récupère l'identifiant unique de l'utilisateur
     * @return L'identifiant unique de l'utilisateur
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de l'utilisateur
     * @param id L'identifiant unique de l'utilisateur
     */
    public void setId(Long id) {
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
    public String getCodeValidation() {
        return codeValidation;
    }

    /**
     * Définit le code de validation de l'utilisateur
     * @param codeValidation Le code de validation de l'utilisateur
     */
    public void setCodeValidation(String codeValidation) {
        this.codeValidation = codeValidation;
    }

    /**
     * Vérifie si l'utilisateur est valide
     * @return true si l'utilisateur est valide, false sinon
     */
    public boolean isValide() {
        return valide;
    }

    /**
     * Définit si l'utilisateur est valide
     * @param estValide true si l'utilisateur est valide, false sinon
     */
    public void setValide(boolean estValide) {
        this.valide = estValide;
    }
}
