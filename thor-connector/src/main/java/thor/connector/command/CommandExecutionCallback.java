package thor.connector.command;

/**
 * Command 요청 내에서 실제 로직 수행 부분을 래핑하는 Wrapper 인터페이스
 * <p/>
 * Created by chanwook on 2015. 2. 4..
 */
public interface CommandExecutionCallback<T> {
    T execute();
}
