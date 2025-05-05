package src.com.humanbooster.repository;

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

            String content = json.toString().replace("[", "").replace("]", "");
            if (content.isEmpty()) return list;

            String[] items = content.split("},\\{");
            for (String raw : items) {
                String item = raw;
                if (!item.startsWith("{")) item = "{" + item;
                if (!item.endsWith("}")) item = item + "}";

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
                writer.write("    \"bornes\": []\n"); // simplification : bornes non sérialisées ici
                writer.write("  }" + (i < list.size() - 1 ? "," : "") + "\n");
            }
            writer.write("]");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

    private LieuRecharge parseLieu(String json) {
        try {
            json = json.replace("{", "").replace("}", "").trim();
            String[] fields = json.split(",\"");
            String id = "", nom = "", adresse = "";

            for (String field : fields) {
                String[] kv = field.replace("\"", "").split(":");
                if (kv.length < 2) continue;
                String key = kv[0].trim();
                String value = kv[1].trim();

                switch (key) {
                    case "id" -> id = value;
                    case "nom" -> nom = value;
                    case "adresse" -> adresse = value;
                }
            }

            return new LieuRecharge(id, nom, adresse);

        } catch (Exception e) {
            System.err.println("Erreur lors de la désérialisation du lieu : " + e.getMessage());
            return null;
        }
    }
}
