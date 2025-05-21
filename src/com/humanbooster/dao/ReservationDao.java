package src.com.humanbooster.dao;

import org.hibernate.SessionFactory;
import src.com.humanbooster.model.Reservation;
import src.com.humanbooster.model.StatutReservation;
import src.com.humanbooster.util.TimeUtil;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe ReservationDao
 * Permet de gérer les réservations en les lisant et en les écrivant dans un fichier JSON.
 */
public class ReservationDao extends GenericDaoImpl<Reservation, Long> {

    private static final String FILE_PATH = "data/reservations.json";
    private final List<Reservation> reservations;

    /**
     * Constructeur de la classe ReservationDao.
     * Initialise la liste des réservations en lisant le fichier JSON.
     */
    public ReservationDao(SessionFactory sessionFactory) {
        super(sessionFactory, Reservation.class);
        this.reservations = readReservations();
    }

    /**
     * Récupère la liste des réservations
     *
     * @return la liste des réservations
     */
    public List<Reservation> findAll() {
        return reservations;
    }

    /**
     * Enregistre une réservation dans le fichier JSON.
     * @param reservation La réservation à enregistrer
     */
    public void save(Reservation reservation) {
        reservations.removeIf(r -> r.getId().equals(reservation.getId()));
        reservations.add(reservation);
        writeReservations(reservations);
    }

    /**
     * Récupère une réservation via son identifiant.
     * @param id L'identifiant de la réservation
     */
    public Reservation findById(String id) {
        return reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Récupère la liste des réservations concernant une borne
     * @param idBorne L'identifiant de la borne
     * @return La liste des réservations concernées
     */
    public List<Reservation> findByBorneId(String idBorne) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getIdBorne().equals(idBorne)) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Récupère la liste des réservations dans le fichier JSON
     * @return La liste des réservations
     */
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

    /**
     * Écrit la liste des réservations dans le fichier JSON
     * @param list La liste des réservations
     */
    private void writeReservations(List<Reservation> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("[\n");
            for (int i = 0; i < list.size(); i++) {
                Reservation r = list.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": \"" + r.getId() + "\",\n");
                writer.write("    \"idUtilisateur\": \"" + r.getIdUtilisateur() + "\",\n");
                writer.write("    \"idBorne\": \"" + r.getIdBorne() + "\",\n");
                writer.write("    \"dateDebut\": \"" + r.getDateDebut().format(TimeUtil.FORMATTER) + "\",\n");
                writer.write("    \"dateFin\": \"" + r.getDateFin().format(TimeUtil.FORMATTER) + "\",\n");
                writer.write("    \"statut\": \"" + r.getStatut().name() + "\"\n");
                writer.write("  }" + (i < list.size() - 1 ? "," : "") + "\n");
            }
            writer.write("]");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

    /**
     * Désérialise une chaîne JSON en un objet Reservation
     * @param json La chaîne JSON à désérialiser
     * @return L'objet Reservation correspondant
     */
    private Reservation parseReservation(String json) {
        try {
            json = json.replace("{", "").replace("}", "").trim();
            String[] fields = json.split(",\"");
            String id = "", idUser = "", idBorne = "", debut = "", fin = "", statutStr = "";

            for (String field : fields) {
                String[] kv = field.replace("\"", "").split(":", 2);
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
                    LocalDateTime.parse(debut, TimeUtil.FORMATTER),
                    LocalDateTime.parse(fin, TimeUtil.FORMATTER),
                    StatutReservation.valueOf(statutStr)
            );

        } catch (Exception e) {
            System.err.println("Erreur lors de la désérialisation de la réservation : " + e.getMessage());
            return null;
        }
    }
}
