package thor.connector.ui;

import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Created by chanwook on 2015. 2. 5..
 */
public class UiCommandEventFilterTests {

    @Test
    public void basicFlow() throws Exception {
        //given
        MockUiCommandEventFilter eventFilter = new MockUiCommandEventFilter();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        req.setServletPath("/test");

        //when
        eventFilter.doFilter(req, res, filterChain);

        //then
        assertTrue(eventFilter.isCalled());
    }

    static class MockUiCommandEventFilter extends UiCommandEventFilter {

        public boolean called = false;

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            super.doFilter(servletRequest, servletResponse, filterChain);
            called = true;
        }

        public boolean isCalled() {
            return called;
        }
    }
}
