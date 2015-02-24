package com.netflix.hystrix;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

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
	
//	모든 이벤트 타입이 MetrixCommandMetrics를 사용한다고 가정
//	FIXME HystrixCommandMetrics의 reset()관련 처리가 되어야 함
	private static final ConcurrentHashMap<String, HystrixCommandMetrics> metrics = new ConcurrentHashMap<String, HystrixCommandMetrics>();
	
//	데이터 확장 테스트 확인, HystrixCommand 단위
	private String trailId = "testTrailId";
	
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

	public String getTrailId() {
		return trailId;
	}

	public void setTrailId(String trailId) {
		this.trailId = trailId;
	}

}
