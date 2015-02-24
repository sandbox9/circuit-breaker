package com.netflix.hystrix;

import metrix.connector.command.CommandExecutionCallback;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.HystrixCommand.Setter;



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

	/**
	 * 
	 * @param metrixCommandMetrics
	 */
	protected void recodeExtensionData(MetrixCommandMetrics metrixCommandMetrics) {
//		 TODO 확장데이터 기록
//		 MetrixCommandMetrics에 기록해야 하고, 어떤 데이터를 어떻게 기록할지는 차후 개발
	}

	public MetrixCommandMetrics getMetrixCommandMetrics() {
		return (MetrixCommandMetrics) super.getMetrics();
	}
	
}