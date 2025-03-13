package simulator.Events.Exceptions;

/**
 * Exception pour un mouvement impossible.
 */
public class MoveImpossibleException extends Exception {

    /**
     * Constructeur de l'exception.
     * @param message Message d'alerte.
     */
    public MoveImpossibleException(String message) {
        super(message);
    }
}
