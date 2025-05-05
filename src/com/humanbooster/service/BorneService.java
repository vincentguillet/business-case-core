package src.com.humanbooster.service;

import src.com.humanbooster.model.BorneRecharge;

import java.util.List;

public interface BorneService {

    boolean ajouterBorne(String idLieu, BorneRecharge borne);
    boolean modifierBorne(String idLieu, BorneRecharge borneModifiee);
    boolean supprimerBorne(String idLieu, String idBorne);
    List<BorneRecharge> listerBornesParLieu(String idLieu);
    BorneRecharge trouverBorneParId(String idLieu, String idBorne);
}
