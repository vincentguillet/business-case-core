package src.com.humanbooster.service;

import src.com.humanbooster.model.BorneRecharge;

import java.util.List;

/**
 * Interface pour le service de gestion des bornes de recharge.
 * Fournit des méthodes pour ajouter, modifier, supprimer et lister les bornes de recharge.
 */
public interface BorneService {

    boolean ajouterBorne(String idLieu, BorneRecharge borne);
    boolean modifierBorne(String idLieu, BorneRecharge borneModifiee);
    boolean supprimerBorne(String idLieu, String idBorne);
    List<BorneRecharge> listerBornesParLieu(String idLieu);
    BorneRecharge trouverBorneParId(String idLieu, String idBorne);
}
