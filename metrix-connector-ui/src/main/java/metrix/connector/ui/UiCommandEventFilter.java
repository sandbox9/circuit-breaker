package metrix.connector.ui;

import metrix.connector.command.CommandExecutionCallback;
import metrix.connector.command.trail.TrailContext;
import metrix.connector.command.trail.TrailContextHolder;
import metrix.connector.command.ui.UICommand;

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
        
//      FIXME TrailHandlerInterceptor와 여기에서 둘다 초기화 하고 있음. 초기화 로직을 한곳으로 모아야 함
//        	   현재는 Servlet Filter에 UICommand가 달려있어 HandlerInterceptor보다 먼저 호출 되기에 임시로 넣어놓은 코드(나중에 제거되어야 함)
        if(TrailContextHolder.getTrailContext() == null) {
        	TrailContextHolder.setTrailContext(new TrailContext());
        }
        
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
