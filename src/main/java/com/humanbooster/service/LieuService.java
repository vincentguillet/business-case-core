package com.humanbooster.service;

import com.humanbooster.model.LieuRecharge;

import java.util.List;

/**
 * Interface pour le service de gestion des lieux de recharge.
 */
public interface LieuService {

    LieuRecharge ajouterLieu(String nom, String adresse);
    boolean modifierLieu(Long id, String nouveauNom, String nouvelleAdresse);
    boolean supprimerLieu(Long id);
    List<LieuRecharge> listerLieux();
    LieuRecharge trouverParId(Long id);
}
