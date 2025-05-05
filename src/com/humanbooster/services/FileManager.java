package src.com.humanbooster.services;

import java.io.*;

public class FileManager {

    /**
     * Renvoie le chemin du fichier d'exportation
     * @param fileName Le nom du fichier
     * @return Le chemin du fichier d'exportation
     */
    public String getFilePath(String fileName) {
        return "exports/" + fileName;
    }

    /**
     * Renvoie le chemin du dossier d'exportation
     * @return Le chemin du dossier d'exportation
     */
    public String getFilePath() {
        return "exports/";
    }

    /**
     * Enregistre une chaîne de caractères dans un fichier
     * @param fileName Le fichier dans lequel enregistrer la chaîne (Chemin + nom du fichier)
     * @param content La chaîne de caractères à enregistrer
     */
    public void saveFile(String fileName, String content) {
        // Save to JSON
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(fileName)))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

    /**
     * Lit le contenu d'un fichier et le retourne sous forme de chaîne de caractères
     * @param fileName Le nom du fichier à lire (Chemin + nom du fichier)
     * @return Le contenu du fichier sous forme de chaîne de caractères
     */
    public String readFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        return content.toString();
    }
}
