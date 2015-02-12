package metrix.connector.api;

/**
 * Created by chanwook on 2015. 2. 4..
 */
public class ApiConnectorException extends RuntimeException {
    public ApiConnectorException(Throwable caused) {
        super(caused);
    }

    public ApiConnectorException(String message, Throwable caused) {
        super(message, caused);
    }
}
