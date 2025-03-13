package simulator.Events.Exceptions;

/**
 * Exception pour éteindre un incendie impossible.
 */
public class TurnOffImpossibleException extends Exception{

    /**
     * Constructeur de l'exception d'éteinte impossible.
     * @param msg Message d'alerte.
     */
    public TurnOffImpossibleException(String msg) {
        super(msg);
    }
}
