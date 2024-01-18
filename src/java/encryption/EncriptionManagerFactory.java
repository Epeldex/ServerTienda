package encryption;

/**
 * The EncriptionManagerFactory class is responsible for providing an instance
 * of the EncriptionManager interface, allowing clients to obtain a concrete
 * implementation of the encryption manager.
 *
 * The default implementation returned by this factory is EncriptionManagerImpl.
 *
 * @author Alexander Epelde
 */
public class EncriptionManagerFactory {

    /**
     * The default instance of the EncriptionManager interface.
     */
    private static EncriptionManager obj = new EncriptionManagerImpl();

    /**
     * Returns an instance of the EncriptionManager interface.
     *
     * @return An instance of EncriptionManager.
     */
    public static EncriptionManager getInstance() {
        return obj;
    }

}
