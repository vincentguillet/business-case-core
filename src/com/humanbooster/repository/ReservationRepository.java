package src.com.humanbooster.repository;

import src.com.humanbooster.model.Reservation;
import src.com.humanbooster.model.StatutReservation;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    private static final String FILE_PATH = "data/reservations.json";
    private final List<Reservation> reservations;

    public ReservationRepository() {
        this.reservations = readReservations();
    }

    public List<Reservation> findAll() {
        return reservations;
    }

    public void save(Reservation reservation) {
        reservations.removeIf(r -> r.getId().equals(reservation.getId()));
        reservations.add(reservation);
        writeReservations(reservations);
    }

    public Reservation findById(String id) {
        return reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Reservation> findByBorneId(String idBorne) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getIdBorne().equals(idBorne)) {
                result.add(r);
            }
        }
        return result;
    }

    private List<Reservation> readReservations() {
        List<Reservation> list = new ArrayList<>();
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

                Reservation r = parseReservation(item);
                if (r != null) list.add(r);
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        return list;
    }

    private void writeReservations(List<Reservation> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("[\n");
            for (int i = 0; i < list.size(); i++) {
                Reservation r = list.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": \"" + r.getId() + "\",\n");
                writer.write("    \"idUtilisateur\": \"" + r.getIdUtilisateur() + "\",\n");
                writer.write("    \"idBorne\": \"" + r.getIdBorne() + "\",\n");
                writer.write("    \"dateDebut\": \"" + r.getDateDebut() + "\",\n");
                writer.write("    \"dateFin\": \"" + r.getDateFin() + "\",\n");
                writer.write("    \"statut\": \"" + r.getStatut().name() + "\"\n");
                writer.write("  }" + (i < list.size() - 1 ? "," : "") + "\n");
            }
            writer.write("]");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

    private Reservation parseReservation(String json) {
        try {
            json = json.replace("{", "").replace("}", "").trim();
            String[] fields = json.split(",\"");
            String id = "", idUser = "", idBorne = "", debut = "", fin = "", statutStr = "";

            for (String field : fields) {
                String[] kv = field.replace("\"", "").split(":");
                if (kv.length < 2) continue;
                String key = kv[0].trim();
                String value = kv[1].trim();

                switch (key) {
                    case "id" -> id = value;
                    case "idUtilisateur" -> idUser = value;
                    case "idBorne" -> idBorne = value;
                    case "dateDebut" -> debut = value;
                    case "dateFin" -> fin = value;
                    case "statut" -> statutStr = value;
                }
            }

            return new Reservation(
                    id,
                    idUser,
                    idBorne,
                    LocalDateTime.parse(debut),
                    LocalDateTime.parse(fin),
                    StatutReservation.valueOf(statutStr)
            );

        } catch (Exception e) {
            System.err.println("Erreur lors de la désérialisation de la réservation : " + e.getMessage());
            return null;
        }
    }
}
