package com.netflix.hystrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import metrix.connector.command.EventHistory;

import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesFactory;

/**
 * 	{@link MetrixCommand} 단위로 생성되며, 모니터링 지표를 관리한다.
 * 
 * @author jin
 *
 */

public class MetrixCommandMetrics extends HystrixCommandMetrics {
	
	private List<EventHistory> eventHistoryList = new ArrayList<EventHistory>() ;
	
	
//	모든 이벤트 타입이 MetrixCommandMetrics를 사용한다고 가정
//	FIXME HystrixCommandMetrics의 reset()관련 처리가 되어야 함
	private static final ConcurrentHashMap<String, HystrixCommandMetrics> metrics = new ConcurrentHashMap<String, HystrixCommandMetrics>();
	
	MetrixCommandMetrics(HystrixCommandKey key, HystrixCommandGroupKey commandGroup,
			HystrixCommandProperties properties, HystrixEventNotifier eventNotifier) {
		super(key, commandGroup, properties, eventNotifier);
	}

	/**
	 * 
	 * 
	 * @see {@link HystrixCommandMetrics#getInstance(HystrixCommandKey, HystrixCommandGroupKey, HystrixCommandProperties)}
	 * @see {@link AbstractCommand#getMetrics()}
	 * @param setter
	 * @return
	 */
	public static HystrixCommandMetrics getInstance(Setter setter) {
		
//		AbstractCommand의 생성자 참조하여 작성
		HystrixCommandKey commandKey = setter.commandKey;
		
		HystrixCommandMetrics commandMetrics = metrics.get(commandKey.name());
        if (commandMetrics != null) {
            return commandMetrics;
        }
		
		HystrixCommandGroupKey commandGroupKey = setter.groupKey;
        HystrixCommandProperties commandProperties = HystrixPropertiesFactory.getCommandProperties(commandKey, setter.commandPropertiesDefaults);
        
        commandMetrics = new MetrixCommandMetrics(commandKey, commandGroupKey, commandProperties, HystrixPlugins.getInstance().getEventNotifier());
        
     // attempt to store it (race other threads)
        HystrixCommandMetrics existing = metrics.putIfAbsent(commandKey.name(), commandMetrics);
        if (existing == null) {
            // we won the thread-race to store the instance we created
            return commandMetrics;
        } else {
            // we lost so return 'existing' and let the one we created be garbage collected
            return existing;
        }
        
	}
	
    public static HystrixCommandMetrics getInstance(HystrixCommandKey key) {
        return metrics.get(key.name());
    }
    
    /**
     * All registered instances of {@link HystrixCommandMetrics}
     * 
     * @return {@code Collection<HystrixCommandMetrics>}
     */
    public static Collection<HystrixCommandMetrics> getInstances() {
        return Collections.unmodifiableCollection(metrics.values());
    }
    
    /**
     * Clears all state from metrics. If new requests come in instances will be recreated and metrics started from scratch.
     */
    /* package */ static void reset() {
        metrics.clear();
    }
    
	public List<EventHistory> getEventHistoryList() {
		return eventHistoryList;
	}

	protected void markExecution(EventHistory eventHistory) {
		this.eventHistoryList.add(eventHistory);
	}

	public void markExceptionThrown(String eventId, Throwable failedExecutionException) {

//		for(int i = eventHistoryList.size() - 1; i >= 0 ;i--) {
			
//			TODO 스레드로컬로 관리되는 trailID를 통해서 일단 구분하지만, Command는 스레드를 넘어서 공유되기 때문에 동시성 문제가 발생할 듯...
//			fallback은 다른 스레드로 사용하기 때문에 구분할 수 없음...
			EventHistory eventHistory = eventHistoryList.get(eventHistoryList.size() - 1);
//			if(eventHistory.getId().equals(eventId)) {
				eventHistory.setException(failedExecutionException);
				eventHistory.setStatus("실패");
//				break;
//			}
//		}
	}
}
