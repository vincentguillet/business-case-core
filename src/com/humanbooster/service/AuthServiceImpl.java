package src.com.humanbooster.service;

import src.com.humanbooster.dao.UserDao;
import src.com.humanbooster.model.Utilisateur;

import java.util.UUID;

/**
 * Implémentation du service d'authentification.
 */
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;
    private Utilisateur utilisateurEnSession;

    /**
     * Constructeur de la classe AuthServiceImpl.
     *
     * @param userDao Le dépôt d'utilisateurs.
     */
    public AuthServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Inscrit un nouvel utilisateur.
     *
     * @param email       L'email de l'utilisateur.
     * @param motDePasse  Le mot de passe de l'utilisateur.
     * @return L'utilisateur inscrit ou null si l'email existe déjà.
     */
    @Override
    public Utilisateur inscrire(String email, String motDePasse) {
        if (userDao.existsByEmail(email)) {
            System.out.println("Un compte existe déjà avec cet e-mail.");
            return null;
        }

        String codeValidation = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        Utilisateur u = new Utilisateur(Long.valueOf(UUID.randomUUID().toString()), email, motDePasse, codeValidation, false);
        userDao.create(u);
        System.out.println("Code de validation : " + codeValidation);
        return u;
    }

    /**
     * Valide le compte d'un utilisateur
     * @param email L'email du compte utilisateur à valider
     * @param code Le code de validation reçu par l'utilisateur dans la console
     */
    @Override
    public boolean validerCompte(String email, String code) {
        Utilisateur u = userDao.findByEmail(email);
        if (u != null && u.getCodeValidation().equalsIgnoreCase(code)) {
            u.setEstValide(true);
            userDao.update(u);
            return true;
        }
        return false;
    }

    /**
     * Permet à un utilisateur de se connecter
     * @param email L'email de l'utilisateur
     * @param motDePasse Le mot de passe de l'utilisateur
     * @return L'utilisateur connecté
     */
    @Override
    public Utilisateur connecter(String email, String motDePasse) {
        Utilisateur u = userDao.findByEmail(email);
        if (u != null && u.getMotDePasse().equals(motDePasse) && u.isEstValide()) {
            utilisateurEnSession = u;
            return u;
        }
        return null;
    }

    /**
     * Permet à un utilisateur de se déconnecter
     */
    @Override
    public void deconnecter() {
        utilisateurEnSession = null;
    }

    /**
     * Récupère l'utilisateur connecté
     * @return L'utilisateur connecté
     */
    @Override
    public Utilisateur getUtilisateurConnecte() {
        return utilisateurEnSession;
    }

    /**
     * Vérifie si un utilisateur est connecté
     * @return true si l'utilisateur est connecté, false sinon
     */
    @Override
    public boolean estConnecte() {
        return utilisateurEnSession != null;
    }

    /**
     * Vérifie si un compte est valide
     * @param email L'email de l'utilisateur
     * @return true si le compte est valide, false sinon
     */
    @Override
    public boolean estCompteValide(String email) {
        Utilisateur u = userDao.findByEmail(email);
        return u != null && u.isEstValide();
    }
}
