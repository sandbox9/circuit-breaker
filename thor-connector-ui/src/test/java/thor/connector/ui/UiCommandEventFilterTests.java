package thor.connector.ui;

import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Created by chanwook on 2015. 2. 5..
 */
public class UiCommandEventFilterTests {

    @Test
    public void call() throws Exception {
        UiCommandEventFilter eventFilter = new UiCommandEventFilter();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        eventFilter.doFilter(req, res, filterChain);
    }
}
