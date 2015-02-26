package metrix.connector.command.trail;


public class TrailContextHolder {
	
	private static final ThreadLocal<TrailContext> trailContextHolder = new InheritableThreadLocal<TrailContext>();
	
	public static TrailContext getTrailContext() {
		
		return trailContextHolder.get();
	}
	
	public static void setTrailContext(TrailContext trailContext) {
		trailContextHolder.set(trailContext);
	}
	
	
	public static void clear() {
		trailContextHolder.remove();
	}
}
