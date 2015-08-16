/*************************************************************************************************************
 * JAIFdb - A Java wrapper for the AIFdb argumentation data.
 * Copyright 2015 Martin Wunderlich
 * 
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * 
 * GNU Lesser General Public License (LGPL)
 * 
 * as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details. You should have received a copy
 * of the GNU Lesser General Public License along with this library. If not, see http://www.gnu.org/licenses/.
 **************************************************************************************************************/

package com.martinwunderlich.nlp.arg.aifdb.graph;

/**
 * POJO to represent a node in the graph structure of the argument map.
 * 
 * @author Martin Wunderlich (martin@wunderlich.com)
 *
 */
public class AIFdbNode {

	private long nodeID = -1;
	private String text = "";
	private NODE_TYPES type = null;
	private String timestamp = "";
	private String scheme = "";
	
	private boolean isPremise = false;
	private boolean isConclusion = false;
	private int begin;
	private int end;
	
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
	
	@Override
	public String toString() {
		return this.type + " - " + this.nodeID + ": " + this.text;
	}
	public void setBegin(int b) {
		this.begin = b;
	}
	public void setEnd(int e) {
		this.end = e;
	}
	public int getBegin() {
		return begin;
	}
	public int getEnd() {
		return end;
	}
}
