package com.humanbooster.service;

import com.humanbooster.model.BorneRecharge;

import java.util.List;

/**
 * Interface pour le service de gestion des bornes de recharge.
 * Fournit des méthodes pour ajouter, modifier, supprimer et lister les bornes de recharge.
 */
public interface BorneService {

    boolean ajouterBorne(Long idLieu, BorneRecharge borne);
    boolean modifierBorne(Long idLieu, BorneRecharge borneModifiee);
    boolean supprimerBorne(Long idLieu, Long idBorne);
    List<BorneRecharge> listerBornesParLieu(Long idLieu);
    BorneRecharge trouverBorneParId(Long idLieu, Long idBorne);
}
