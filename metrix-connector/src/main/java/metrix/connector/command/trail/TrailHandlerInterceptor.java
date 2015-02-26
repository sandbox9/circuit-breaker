package metrix.connector.command.trail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TrailHandlerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//		TODO 여러 WAS에 걸쳐서 Trail 정보를 전달하기 위한 구현이 필요하다.(ex. UI -> API -(HTTP)-> UI -> DB)
		if(TrailContextHolder.getTrailContext() == null) {
			TrailContextHolder.setTrailContext(new TrailContext());
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		TrailContextHolder.clear();
	}
}
