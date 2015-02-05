//package sample.front.config;
//
//import org.springframework.aop.Advisor;
//import org.springframework.aop.aspectj.AspectJExpressionPointcut;
//import org.springframework.aop.support.DefaultPointcutAdvisor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//
//@Configuration
//@EnableAspectJAutoProxy
//public class UICommandConfig {
//
//	@Bean
//	public HystrixAspect hystrixAspect() {
//		return new HystrixAspect();
//	}
//	
//	@Bean
//	public Advisor advisor() {
//
//		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
////		pointcut.setExpression("@within(org.springframework.stereotype.Controller)");
//		pointcut.setExpression("target(sample.front.controller.SampleFrontController)");
//		return new DefaultPointcutAdvisor(pointcut, hystrixAspect());
//	}
//	
//	
//}
