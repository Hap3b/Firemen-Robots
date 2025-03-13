package simulator.Events.Exceptions;

/**
 * Exception pour une recharge impossible
 */
public class RefillImpossibleException extends Exception{

    /**
     * Constructeur de l'exception pour une recharge impossible
     * @param message Message d'alerte
     */
    public RefillImpossibleException(String message) {
        super(message);
    }

}
