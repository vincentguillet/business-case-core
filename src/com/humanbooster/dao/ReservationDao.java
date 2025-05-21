package src.com.humanbooster.dao;

import org.hibernate.SessionFactory;
import src.com.humanbooster.model.Reservation;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe ReservationDao
 * Permet de gérer les réservations en les lisant et en les écrivant dans un fichier JSON.
 */
public class ReservationDao extends GenericDaoImpl<Reservation, Long> {

    /**
     * Constructeur de la classe ReservationDao.
     * Initialise la liste des réservations en lisant le fichier JSON.
     */
    public ReservationDao(SessionFactory sessionFactory) {
        super(sessionFactory, Reservation.class);
    }

    /**
     * Récupère la liste des réservations concernant une borne
     * @param idBorne L'identifiant de la borne
     * @return La liste des réservations concernées
     */
    public List<Reservation> findByBorneId(Long idBorne) {
        List<Reservation> reservations = readAll();
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getBorneRecharge().equals(idBorne)) {
                result.add(r);
            }
        }
        return result;
    }
}
