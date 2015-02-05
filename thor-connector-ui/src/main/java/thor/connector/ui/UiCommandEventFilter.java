package thor.connector.ui;

import thor.connector.command.CommandExecutionCallback;
import thor.connector.command.ui.UICommand;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chanwook on 2015. 2. 4..
 */
public class UiCommandEventFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;

        String servletPath = req.getServletPath();
        UICommand command = new UICommand(servletPath, new CommandExecutionCallback() {
            @Override
            public Object execute() {
                try {
                    filterChain.doFilter(req, res);
                } catch (Throwable e) {
                    throw new UiConnectorException(e);
                }
                return null;
            }
        });
        command.execute();
    }

    @Override
    public void destroy() {

    }
}
