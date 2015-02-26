package metrix.connector.command.trail;

import java.util.UUID;

public class TrailContext {
	
	private String trailId;
	private int order;
	
	public TrailContext() {
		this.trailId = UUID.randomUUID().toString();
		order = 1;
	}
	
	public String getTrailId() {
		return trailId;
	}

	public void setTrailId(String trailId) {
		this.trailId = trailId;
	}

	public int getOrder() {
		return this.order++;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TrailContext [trailId=").append(trailId)
				.append(", order=").append(order).append("]");
		return builder.toString();
	}

	public static void main(String[] args) {
		TrailContext trailContext = new TrailContext();
		
		System.out.println(trailContext);
		trailContext.getOrder();
		System.out.println(trailContext);
	}

}
