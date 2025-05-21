package src.com.humanbooster.dao;

import src.com.humanbooster.model.BorneRecharge;
import src.com.humanbooster.model.EtatBorne;
import src.com.humanbooster.model.LieuRecharge;
import src.com.humanbooster.model.Utilisateur;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe LieuDao
 * Permet de lire, écrire, trouver et supprimer des lieux dans un fichier JSON.
 */
public class LieuDao extends GenericDaoImpl<LieuRecharge, Long> {

    private static final String FILE_PATH = "data/lieux.json";
    private final List<LieuRecharge> lieux;

    /**
     * Constructeur de la classe LieuDao.
     * Initialise la liste des lieux en lisant le fichier JSON.
     */
    public LieuDao() {
        this.lieux = readLieux();
    }

    /**
     * Récupère la liste de tous les lieux.
     *
     * @return Liste de tous les lieux.
     */
    public List<LieuRecharge> findAll() {
        return lieux;
    }

    /**
     * Enregistre un lieu dans le fichier JSON.
     *
     * @param lieu Le lieu à enregistrer.
     */
    public void save(LieuRecharge lieu) {
        lieux.removeIf(l -> l.getId().equals(lieu.getId()));
        lieux.add(lieu);
        writeLieux(lieux);
    }

    /**
     * Récupère un lieu via son ID.
     *
     * @param id L'ID du lieu à récupérer.
     */
    public LieuRecharge findById(String id) {
        return lieux.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Supprime un lieu via son ID.
     *
     * @param id L'ID du lieu à supprimer.
     */
    public void deleteById(String id) {
        lieux.removeIf(l -> l.getId().equals(id));
        writeLieux(lieux);
    }

    /**
     * Lit le fichier JSON et désérialise les lieux.
     *
     */
    private List<LieuRecharge> readLieux() {
        List<LieuRecharge> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line.trim());
            }

            String content = json.toString();
            if (content.isEmpty()) return list;

            // Nettoyage des crochets extérieurs
            if (content.startsWith("[")) content = content.substring(1);
            if (content.endsWith("]")) content = content.substring(0, content.length() - 1);

            // Découpe intelligente sur les objets "lieu"
            String[] items = content.split("\\},\\s*\\{\\s*\"id\":");
            for (int i = 0; i < items.length; i++) {
                String item = items[i];

                // Réinjecte la clé "id" supprimée par le split
                if (i != 0) item = "{\"id\":" + item;
                if (!item.startsWith("{")) item = "{" + item;
                if (!item.endsWith("}")) item += "}";

                LieuRecharge lieu = parseLieu(item);
                if (lieu != null) list.add(lieu);
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur lors de la désérialisation des lieux : " + e.getMessage());
        }

        return list;
    }

    /**
     * Écrit la liste des lieux dans le fichier JSON.
     *
     * @param list La liste des lieux à écrire.
     */
    private void writeLieux(List<LieuRecharge> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("[\n");
            for (int i = 0; i < list.size(); i++) {
                LieuRecharge l = list.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": \"" + l.getId() + "\",\n");
                writer.write("    \"nom\": \"" + l.getNom() + "\",\n");
                writer.write("    \"adresse\": \"" + l.getAdresse() + "\",\n");
                writer.write("    \"bornes\": [\n");

                List<BorneRecharge> bornes = l.getBornes();
                for (int j = 0; j < bornes.size(); j++) {
                    BorneRecharge b = bornes.get(j);
                    writer.write("      {\n");
                    writer.write("        \"id\": \"" + b.getId() + "\",\n");
                    writer.write("        \"etat\": \"" + b.getEtat().name() + "\",\n");
                    writer.write("        \"tarifHoraire\": " + b.getTarifHoraire() + "\n");
                    writer.write("      }" + (j < bornes.size() - 1 ? "," : "") + "\n");
                }

                writer.write("    ]\n");
                writer.write("  }" + (i < list.size() - 1 ? "," : "") + "\n");
            }
            writer.write("]");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

    /**
     * Parse un JSON brut pour créer un objet LieuRecharge.
     *
     * @param json Le JSON à parser.
     * @return Un objet LieuRecharge ou null en cas d'erreur.
     */
    private LieuRecharge parseLieu(String json) {
        try {
            json = json.trim();
            if (!json.startsWith("{") || !json.endsWith("}")) return null;

            // Séparation brute entre partie méta et les bornes
            int indexBornes = json.indexOf("\"bornes\"");
            if (indexBornes == -1) return null;

            String metaPart = json.substring(0, indexBornes);
            String bornesPart = json.substring(indexBornes);

            String id = extractJsonValue(metaPart, "id");
            String nom = extractJsonValue(metaPart, "nom");
            String adresse = extractJsonValue(metaPart, "adresse");

            LieuRecharge lieu = new LieuRecharge(id, nom, adresse);

            // Désérialisation des bornes
            if (bornesPart.contains("[") && bornesPart.contains("]")) {
                String rawList = bornesPart.substring(bornesPart.indexOf("[") + 1, bornesPart.lastIndexOf("]"));
                String[] items = rawList.split("},\\{");

                for (String raw : items) {
                    String item = raw;
                    if (!item.startsWith("{")) item = "{" + item;
                    if (!item.endsWith("}")) item = item + "}";
                    BorneRecharge b = parseBorne(item);
                    if (b != null) lieu.ajouterBorne(b);
                }
            }

            return lieu;

        } catch (Exception e) {
            System.err.println("Erreur parsing lieu : " + e.getMessage());
            return null;
        }
    }

    /**
     * Parse un JSON brut pour créer un objet BorneRecharge.
     *
     * @param json Le JSON à parser.
     * @return Un objet BorneRecharge ou null en cas d'erreur.
     */
    private BorneRecharge parseBorne(String json) {
        try {
            if (json.equals("{}")) return null; // 👈 Ignore les objets vides

            json = json.replace("{", "").replace("}", "").trim();
            String[] fields = json.split(",");

            String id = "";
            String etatStr = "";
            double tarif = 0;

            for (String field : fields) {
                String[] kv = field.split(":");
                if (kv.length < 2) continue;

                String key = kv[0].replace("\"", "").trim();
                String value = kv[1].replace("\"", "").trim();

                switch (key) {
                    case "id" -> id = value;
                    case "etat" -> etatStr = value;
                    case "tarifHoraire" -> tarif = Double.parseDouble(value);
                }
            }

            return new BorneRecharge(id, EtatBorne.valueOf(etatStr), tarif);

        } catch (Exception e) {
            System.err.println("Erreur lors du parsing de borne : " + e.getMessage());
            return null;
        }
    }

    /**
     * Extrait la valeur d'une clé dans un JSON brut.
     *
     * @param json Le JSON brut.
     * @param key  La clé à rechercher.
     * @return La valeur associée à la clé ou une chaîne vide si non trouvée.
     */
    private String extractJsonValue(String json, String key) {
        try {
            String[] parts = json.split(",");
            for (String part : parts) {
                if (part.contains("\"" + key + "\"")) {
                    String[] kv = part.split(":");
                    if (kv.length == 2) {
                        return kv[1].replace("\"", "").trim();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'extraction de la valeur JSON : " + e.getMessage());
        }
        return "";
    }
}
