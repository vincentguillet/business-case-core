package src.com.humanbooster.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Classe générique pour la gestion des opérations CRUD sur les entités.
 *
 * @param <T>  Le type de l'entité
 * @param <ID> Le type de l'identifiant de l'entité
 */
public class GenericDaoImpl<T, ID> implements  GenericDao<T,ID> {

    protected final SessionFactory sessionFactory;
    private final Class<T> entityClass;

    /**
     * Constructeur de la classe GenericDaoImpl.
     *
     * @param sessionFactory L'instance de SessionFactory utilisée pour gérer les sessions Hibernate
     * @param entityClass   La classe correspondant à l'entité gérée par ce DAO
     */
    public GenericDaoImpl(SessionFactory sessionFactory, Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    /**
     * Persiste une nouvelle entité en base de données.
     *
     * @param entity L'entité à persister
     */
    @Override
    public void create(T entity) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        }
    }

    /**
     * Récupère une entité enregistrée en base de données par son identifiant.
     *
     * @param id L'identifiant de l'entité à récupérer dans la base de données
     * @return L'entité correspondante
     */
    @Override
    public T readById(ID id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(entityClass, id);
        }
    }

    /**
     * Récupère toutes les entités enregistrées en base de données.
     *
     * @return La liste de toutes les entités
     */
    @Override
    public List<T> readAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM " + entityClass.getName(), entityClass).list();
        }
    }

    /**
     * Met à jour une entité déjà enregistrée en base de données.
     *
     * @param entity L'entité à mettre à jour
     */
    @Override
    public void update(T entity) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        }
    }

    /**
     * Supprime une entité de la base de données.
     *
     * @param id L'identifiant de l'entité à supprimer
     */
    @Override
    public void delete(ID id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            T entity = session.get(entityClass, id);
            if (entity != null) session.remove(entity);
            session.getTransaction().commit();
        }
    }
}
