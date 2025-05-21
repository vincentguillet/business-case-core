package com.humanbooster.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.humanbooster.model.Utilisateur;

/**
 * Classe UtilisateurDao
 * Décrit la gestion des utilisateurs en mémoire via un fichier JSON.
 */
public class UtilisateurDao extends GenericDaoImpl<Utilisateur, Long> {

    /**
     * Constructeur de la classe UtilisateurDao
     * Charge les utilisateurs depuis le fichier JSON.
     */
    public UtilisateurDao(SessionFactory sessionFactory) {
        super(sessionFactory, Utilisateur.class);
    }

    /**
     * Récupère un utilisateur grâce à son email.
     *
     * @param email L'email de l'utilisateur à rechercher.
     */
    public Utilisateur findByEmail(String email) {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("FROM " + Utilisateur.class.getSimpleName() + " WHERE email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }

    /**
     * Vérifie si un utilisateur existe déjà dans la liste.
     *
     * @param email L'email de l'utilisateur à vérifier.
     * @return true si l'utilisateur existe, false sinon.
     */
    public boolean existsByEmail(String email) {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);

            query.select(cb.count(query.from(Utilisateur.class)))
                    .where(cb.equal(query.from(Utilisateur.class).get("email"), email));

            return session.createQuery(query).getSingleResult() > 0;
        }
    }
}
