package src.com.humanbooster.util;

import java.time.format.DateTimeFormatter;

/**
 * Classe utilitaire pour la gestion du temps.
 * Contient des méthodes et constantes pour le formatage des dates et heures.
 */
public class TimeUtil {

    /**
     * Format de date et heure utilisé dans l'application.
     * Le format est "dd/MM/yyyy HH:mm".
     */
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
}
