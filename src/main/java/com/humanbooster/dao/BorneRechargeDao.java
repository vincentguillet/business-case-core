package com.humanbooster.dao;

import com.humanbooster.model.BorneRecharge;
import org.hibernate.SessionFactory;

/**
 * Classe BorneRechargeDao
 * Permet de gérer les opérations de base de données pour les bornes de recharge.
 */
public class BorneRechargeDao extends GenericDaoImpl<BorneRecharge, Long> {

    /**
     * Constructeur de la classe GenericDaoImpl.
     *
     * @param sessionFactory L'instance de SessionFactory utilisée pour gérer les sessions Hibernate
     */
    public BorneRechargeDao(SessionFactory sessionFactory) {
        super(sessionFactory, BorneRecharge.class);
    }
}
