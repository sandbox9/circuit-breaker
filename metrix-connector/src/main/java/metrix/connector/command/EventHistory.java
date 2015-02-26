package metrix.connector.command;

import java.io.Serializable;
import java.util.Date;

public class EventHistory implements Serializable {
	
	private static final long serialVersionUID = -6538230641928076913L;

	private String id;
	
	private int order;
	
	private Date time;
	
	private String status;
	
	private Throwable exception;

	public EventHistory() {
		this.time = new Date();
		this.status = "성공";
	}

	public EventHistory(String eventId, int eventOrder) {
		this();
		this.id = eventId;
		this.order = eventOrder;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EventHistory [id=").append(id).append(", order=")
				.append(order).append(", time=").append(time)
				.append(", status=").append(status).append(", exception=")
				.append(exception).append("]");
		return builder.toString();
	}
	
}
