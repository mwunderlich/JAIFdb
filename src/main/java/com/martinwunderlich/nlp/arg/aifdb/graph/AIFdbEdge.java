package com.martinwunderlich.nlp.arg.aifdb.graph;

public class AIFdbEdge {

	private long edgeID = -1;
	private long fromID = -1;
	private long toID = -1;
	private long formEdgeID = -1;
	
	public long getEdgeID() {
		return edgeID;
	}
	public void setEdgeID(long edgeID) {
		this.edgeID = edgeID;
	}
	public long getFromID() {
		return this.fromID;
	}
	public void setFromID(long fromID) {
		this.fromID = fromID;
	}
	public long getToID() {
		return this.toID;
	}
	public void setToID(long toID) {
		this.toID = toID;
	}
	public long getFormEdgeID() {
		return this.formEdgeID;
	}
	public void setFormEdgeID(long formEdgeID) {
		this.formEdgeID = formEdgeID;
	}
}
