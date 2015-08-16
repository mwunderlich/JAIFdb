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
 * POJO to represent an edge in the graph structure of the argument map.
 * 
 * @author Martin Wunderlich (martin@wunderlich.com)
 *
 */
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
	
	@Override
	public String toString() {
		return this.fromID + " -> " + this.toID;
	}
}
