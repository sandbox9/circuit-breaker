//package sample.front.config;
//
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import thor.connector.command.ui.UICommand;
//
//public class HystrixAspect implements MethodInterceptor  {
//	
//	@Override
//	public Object invoke(MethodInvocation invocation) throws Throwable {
//		
//		System.out.println("Controller MethodInterceptor");
//		
//		RequestMapping annotation = invocation.getMethod().getAnnotation(RequestMapping.class);
//		String url = annotation.value()[0];
//		
////		return new UICommand(url, invocation).execute();
//		return null;
//	}
//}
