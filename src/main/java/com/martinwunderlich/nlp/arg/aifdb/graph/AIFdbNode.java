package com.martinwunderlich.nlp.arg.aifdb.graph;


public class AIFdbNode {

	private long nodeID = -1;
	private String text = "";
	private NODE_TYPES type = null;
	private String timestamp = "";
	private String scheme = "";
	
	private boolean isPremise = false;
	private boolean isConclusion = false;
	
	public long getNodeID() {
		return nodeID;
	}
	public void setNodeID(long nodeID) {
		this.nodeID = nodeID;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public NODE_TYPES getType() {
		return type;
	}
	public void setType(NODE_TYPES type) {
		this.type = type;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public boolean isPremise() {
		return this.isPremise;
	}
	public void setIsPremise(boolean b) {
		this.isPremise = b;
	}
	public boolean isConclusion() {
		return this.isConclusion;
	}
	public void setIsConclusion(boolean b) {
		this.isConclusion = b;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
}
