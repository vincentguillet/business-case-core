package src.com.humanbooster.dao;

import src.com.humanbooster.model.Utilisateur;
import src.com.humanbooster.util.JsonFileManager;

import java.util.List;

/**
 * Classe UserDao
 * Décrit la gestion des utilisateurs en mémoire via un fichier JSON.
 */
public class UserDao {

    private final List<Utilisateur> utilisateurs;

    /**
     * Constructeur de la classe UserDao
     * Charge les utilisateurs depuis le fichier JSON.
     */
    public UserDao() {
        this.utilisateurs = JsonFileManager.readUtilisateurs();
    }

    /**
     * Enregistre un utilisateur dans la liste et le fichier JSON.
     * Si l'utilisateur existe déjà (même email), il est remplacé.
     *
     * @param utilisateur L'utilisateur à enregistrer.
     */
    public void save(Utilisateur utilisateur) {
        utilisateurs.removeIf(u -> u.getEmail().equalsIgnoreCase(utilisateur.getEmail()));
        utilisateurs.add(utilisateur);
        JsonFileManager.writeUtilisateurs(utilisateurs);
    }

    /**
     * Récupère un utilisateur grâce à son email.
     *
     * @param email L'email de l'utilisateur à rechercher.
     */
    public Utilisateur findByEmail(String email) {
        return utilisateurs.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Vérifie si un utilisateur existe déjà dans la liste.
     *
     * @param email L'email de l'utilisateur à vérifier.
     * @return true si l'utilisateur existe, false sinon.
     */
    public boolean existsByEmail(String email) {
        return utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    /**
     * Récupère la liste de tous les utilisateurs.
     *
     * @return La liste de tous les utilisateurs.
     */
    public List<Utilisateur> findAll() {
        return utilisateurs;
    }
}
