package src.com.humanbooster.repository;

import src.com.humanbooster.model.Utilisateur;
import src.com.humanbooster.util.JsonFileManager;

import java.util.List;

public class UserRepository {

    private final List<Utilisateur> utilisateurs;

    public UserRepository() {
        this.utilisateurs = JsonFileManager.readUtilisateurs();
    }

    public void save(Utilisateur utilisateur) {
        utilisateurs.removeIf(u -> u.getEmail().equalsIgnoreCase(utilisateur.getEmail()));
        utilisateurs.add(utilisateur);
        JsonFileManager.writeUtilisateurs(utilisateurs);
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurs.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public boolean existsByEmail(String email) {
        return utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public List<Utilisateur> findAll() {
        return utilisateurs;
    }
}
