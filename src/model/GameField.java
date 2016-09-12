package model;

import listeners.*;

public class GameField {

	private ObjectInfo objectInfo;
	private ListenerList listener;

	public GameField(ObjectInfo objectInfo, ListenerList listener) {
		this.listener = listener;
		this.setObjectInfo(objectInfo);
	}

	public ObjectInfo getObjectInfo() {
		return objectInfo;
	}

	public void setObjectInfo(ObjectInfo objectInfo) {
		this.objectInfo = objectInfo;
		EventData eventData = new EventData(SenderType.GAME_FIELD, EventType.INITIALIZE,
				this.objectInfo);
		listener.notify(eventData);
	}
}
