package thor.connector.command;

/**
 * Created by chanwook on 2015. 2. 4..
 */
public interface CommandExecutionCallback<T> {
    T execute();
}
