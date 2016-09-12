package listeners;

public class EventData {

	private SenderType senderType;
	private EventType eventType;
	private Object data;

	public EventData(SenderType senderType, EventType eventType, Object data) {
		super();
		this.senderType = senderType;
		this.eventType = eventType;
		this.data = data;
	}

	public SenderType getSenderType() {
		return senderType;
	}

	public EventType getEventType() {
		return eventType;
	}

	public Object getData() {
		return data;
	}
}
