package src.com.humanbooster.service;

import src.com.humanbooster.model.LieuRecharge;

import java.util.List;

/**
 * Interface pour le service de gestion des lieux de recharge.
 */
public interface LieuService {

    LieuRecharge ajouterLieu(String nom, String adresse);
    boolean modifierLieu(String id, String nouveauNom, String nouvelleAdresse);
    boolean supprimerLieu(String id);
    List<LieuRecharge> listerLieux();
    LieuRecharge trouverParId(String id);
}
