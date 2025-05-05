package src.com.humanbooster.service;

import src.com.humanbooster.model.LieuRecharge;

import java.util.List;

public interface LieuService {

    LieuRecharge ajouterLieu(String nom, String adresse);
    boolean modifierLieu(String id, String nouveauNom, String nouvelleAdresse);
    List<LieuRecharge> listerLieux();
    LieuRecharge trouverParId(String id);
}
