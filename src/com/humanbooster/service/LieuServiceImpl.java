package src.com.humanbooster.service;

import src.com.humanbooster.model.LieuRecharge;
import src.com.humanbooster.repository.LieuRepository;

import java.util.List;
import java.util.UUID;

public class LieuServiceImpl implements LieuService {

    private final LieuRepository lieuRepository;

    public LieuServiceImpl(LieuRepository lieuRepository) {
        this.lieuRepository = lieuRepository;
    }

    @Override
    public LieuRecharge ajouterLieu(String nom, String adresse) {
        LieuRecharge lieu = new LieuRecharge(UUID.randomUUID().toString(), nom, adresse);
        lieuRepository.save(lieu);
        return lieu;
    }

    @Override
    public boolean modifierLieu(String id, String nouveauNom, String nouvelleAdresse) {
        LieuRecharge lieu = lieuRepository.findById(id);
        if (lieu == null) return false;

        lieu.setNom(nouveauNom);
        lieu.setAdresse(nouvelleAdresse);
        lieuRepository.save(lieu);
        return true;
    }

    @Override
    public List<LieuRecharge> listerLieux() {
        return lieuRepository.findAll();
    }

    @Override
    public LieuRecharge trouverParId(String id) {
        return lieuRepository.findById(id);
    }
}
