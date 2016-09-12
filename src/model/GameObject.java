package model;

import rendering.Render;
import listeners.ListenerList;

public abstract class GameObject extends Render implements Runnable {
	protected ObjectInfo oi;
	protected GameField gf;
	protected ListenerList ls;

	public GameObject(ObjectInfo objectInfo, GameField gameField, ListenerList listeners) {
		super();
		this.oi = objectInfo;
		this.gf = gameField;
		this.ls = listeners;
	}

	public ObjectInfo getOi() {
		return oi;
	}

	public void setOi(ObjectInfo oi) {
		this.oi = oi;
	}

	public GameField getGf() {
		return gf;
	}

	public void setGf(GameField gf) {
		this.gf = gf;
	}

	public ListenerList getLs() {
		return ls;
	}

	public void setLs(ListenerList ls) {
		this.ls = ls;
	}
}
