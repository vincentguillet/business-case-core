package src.com.humanbooster.util;

import src.com.humanbooster.model.Utilisateur;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe JsonFileManager
 * Permet de lire et écrire des utilisateurs dans un fichier JSON.
 */
public class JsonFileManager {

    private static final String FILE_PATH = "data/utilisateurs.json";

    /**
     * Lit les utilisateurs depuis le fichier JSON et les retourne sous forme de liste.
     *
     * @return Liste d'utilisateurs.
     */
    public static List<Utilisateur> readUtilisateurs() {
        List<Utilisateur> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        // Vérification de l'existence du fichier
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line.trim());
            }

            // Suppression des espaces inutiles
            String content = json.toString().replace("[", "").replace("]", "");
            if (content.isEmpty()) return list;

            // Nettoyage des crochets extérieurs
            String[] items = content.split("},\\{");
            for (String raw : items) {
                String item = raw;
                if (!item.startsWith("{")) item = "{" + item;
                if (!item.endsWith("}")) item = item + "}";

                // Parsing de l'utilisateur
                Utilisateur u = parseUtilisateur(item);
                if (u != null) list.add(u);
            }

        } catch (IOException e) {
            System.err.println("Impossible de lire le fichier : " + e.getMessage());
        }

        return list;
    }

    /**
     * Écrit la liste des utilisateurs dans le fichier JSON.
     *
     * @param list Liste d'utilisateurs à écrire.
     */
    public static void writeUtilisateurs(List<Utilisateur> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("[\n");
            for (int i = 0; i < list.size(); i++) {
                Utilisateur u = list.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": \"" + u.getId() + "\",\n");
                writer.write("    \"email\": \"" + u.getEmail() + "\",\n");
                writer.write("    \"motDePasse\": \"" + u.getMotDePasse() + "\",\n");
                writer.write("    \"codeValidation\": \"" + u.getCodeValidation() + "\",\n");
                writer.write("    \"estValide\": " + u.isEstValide() + "\n");
                writer.write("  }" + (i < list.size() - 1 ? "," : "") + "\n");
            }
            writer.write("]");
        } catch (IOException e) {
            System.err.println("Impossible d'écrire dans le fichier : " + e.getMessage());
        }
    }

    /**
     * Parse un utilisateur à partir d'une chaîne JSON.
     *
     * @param json La chaîne JSON à parser.
     * @return Un objet Utilisateur ou null en cas d'erreur.
     */
    private static Utilisateur parseUtilisateur(String json) {
        // Suppression des espaces inutiles et des accolades
        try {
            json = json.replace("{", "").replace("}", "").trim();
            String[] fields = json.split(",\"");
            String id = "", email = "", mdp = "", code = "";
            boolean estValide = false;

            // Parsing des champs
            for (String field : fields) {
                String[] kv = field.replace("\"", "").split(":");
                if (kv.length < 2) continue;
                String key = kv[0].trim();
                String value = kv[1].trim();

                // Vérification de la clé et assignation de la valeur
                switch (key) {
                    case "id" -> id = value;
                    case "email" -> email = value;
                    case "motDePasse" -> mdp = value;
                    case "codeValidation" -> code = value;
                    case "estValide" -> estValide = Boolean.parseBoolean(value);
                }
            }

            // Création de l'utilisateur
            return new Utilisateur(id, email, mdp, code, estValide);

        } catch (Exception e) {
            System.err.println("Erreur lors du parsage de l'utilisateur : " + e.getMessage());
            return null;
        }
    }
}