package com.netflix.hystrix;

import metrix.connector.command.CommandExecutionCallback;
import metrix.connector.command.EventHistory;
import metrix.connector.command.trail.TrailContext;
import metrix.connector.command.trail.TrailContextHolder;


public abstract class MetrixCommand<T> extends HystrixCommand<T> {

	private String commandId;

	private final CommandExecutionCallback<T> executionCallback;

	public MetrixCommand(String commandId, CommandExecutionCallback<T> executionCallback, Setter setter) {

		super(setter.groupKey, setter.commandKey, setter.threadPoolKey, null, null, 
				setter.commandPropertiesDefaults, setter.threadPoolPropertiesDefaults, MetrixCommandMetrics.getInstance(setter), null, null, 
				null, null);

		this.executionCallback = executionCallback;
	}

	@Override
	protected T run() throws Exception {

		recodeExtensionData(getMetrixCommandMetrics());
		return executionCallback.execute();
	}
	
	@Override
	protected T getFallback() {
		
		recodeExtensionData(getMetrixCommandMetrics(), getFailedExecutionException());
		return super.getFallback();
	}

//	TODO 에러 유형별로 처리해야 함. 사유도 정확히 기록해야 하고
	private void recodeExtensionData(MetrixCommandMetrics metrixCommandMetrics, Throwable failedExecutionException) {
		
		TrailContext trailContext = TrailContextHolder.getTrailContext();
		if(trailContext != null) {
			metrixCommandMetrics.markExceptionThrown(trailContext.getTrailId(), failedExecutionException);
		}
	}

	/**
	 * 
	 * @param metrixCommandMetrics
	 */
	protected void recodeExtensionData(MetrixCommandMetrics metrixCommandMetrics) {
//		 TODO 확장데이터 기록
//		 MetrixCommandMetrics에 기록해야 하고, 어떤 데이터를 어떻게 기록할지는 차후 개발
		
		//Metrix 확장데이터
		//1. 커맨드ID
		//2. 순서(or 시간)
		//3. 에러여부 및 에러정보
		TrailContext trailContext = TrailContextHolder.getTrailContext();
//		FIXME static resouce 요청을 잡아서 null이 되는 듯... handlerInterceptor에서 만들어 주기 때문에, 일단 null 체크해서 빼기
		if(trailContext != null) {
			metrixCommandMetrics.markExecution(new EventHistory(trailContext.getTrailId(), trailContext.getOrder()));
		}
		
	}

	private MetrixCommandMetrics getMetrixCommandMetrics() {
		return (MetrixCommandMetrics) super.getMetrics();
	}
	
}