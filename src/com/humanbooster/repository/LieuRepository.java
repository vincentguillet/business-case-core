package src.com.humanbooster.repository;

import src.com.humanbooster.model.BorneRecharge;
import src.com.humanbooster.model.EtatBorne;
import src.com.humanbooster.model.LieuRecharge;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LieuRepository {

    private static final String FILE_PATH = "data/lieux.json";
    private final List<LieuRecharge> lieux;

    public LieuRepository() {
        this.lieux = readLieux();
    }

    public List<LieuRecharge> findAll() {
        return lieux;
    }

    public void save(LieuRecharge lieu) {
        lieux.removeIf(l -> l.getId().equals(lieu.getId()));
        lieux.add(lieu);
        writeLieux(lieux);
    }

    public LieuRecharge findById(String id) {
        return lieux.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(String id) {
        lieux.removeIf(l -> l.getId().equals(id));
        writeLieux(lieux);
    }

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
