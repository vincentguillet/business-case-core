package com.humanbooster.dao;

import org.hibernate.SessionFactory;
import com.humanbooster.model.BorneRecharge;
import com.humanbooster.model.EtatBorne;
import com.humanbooster.model.LieuRecharge;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe LieuRechargeDao
 * Permet de lire, écrire, trouver et supprimer des lieux dans un fichier JSON.
 */
public class LieuRechargeDao extends GenericDaoImpl<LieuRecharge, Long> {

    /**
     * Constructeur de la classe LieuRechargeDao.
     * Initialise la liste des lieux en lisant le fichier JSON.
     */
    public LieuRechargeDao(SessionFactory sessionFactory) {
        super(sessionFactory, LieuRecharge.class);
    }
}
