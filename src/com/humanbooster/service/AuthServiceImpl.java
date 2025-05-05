package src.com.humanbooster.service;

import src.com.humanbooster.model.Utilisateur;
import src.com.humanbooster.repository.UserRepository;

import java.util.UUID;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private Utilisateur utilisateurEnSession;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Utilisateur inscrire(String email, String motDePasse) {
        if (userRepository.existsByEmail(email)) {
            System.out.println("Un compte existe déjà avec cet e-mail.");
            return null;
        }

        String codeValidation = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        Utilisateur u = new Utilisateur(UUID.randomUUID().toString(), email, motDePasse, codeValidation, false);
        userRepository.save(u);
        System.out.println("Code de validation : " + codeValidation);
        return u;
    }

    @Override
    public boolean validerCompte(String email, String code) {
        Utilisateur u = userRepository.findByEmail(email);
        if (u != null && u.getCodeValidation().equalsIgnoreCase(code)) {
            u.setEstValide(true);
            userRepository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public Utilisateur connecter(String email, String motDePasse) {
        Utilisateur u = userRepository.findByEmail(email);
        if (u != null && u.getMotDePasse().equals(motDePasse) && u.isEstValide()) {
            utilisateurEnSession = u;
            return u;
        }
        return null;
    }

    @Override
    public void deconnecter() {
        utilisateurEnSession = null;
    }

    @Override
    public Utilisateur getUtilisateurConnecte() {
        return utilisateurEnSession;
    }

    @Override
    public boolean estConnecte() {
        return utilisateurEnSession != null;
    }

    @Override
    public boolean estCompteValide(String email) {
        Utilisateur u = userRepository.findByEmail(email);
        return u != null && u.isEstValide();
    }
}
