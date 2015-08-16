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

package com.martinwunderlich.nlp.arg.aifdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.martinwunderlich.nlp.arg.aifdb.graph.AIFdbEdge;
import com.martinwunderlich.nlp.arg.aifdb.graph.AIFdbNode;
import com.martinwunderlich.nlp.arg.aifdb.graph.NODE_TYPES;

/**
 * Represents an argument map consisting of nodes and edges.
 * The map has the structure of a directed non-cyclic graph.
 * 
 * @author Martin Wunderlich (martin@wunderlich.com)
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)	// TODO MW: This is here to avoid parse failures on "locutions" list in arg schemes corpus. Figure out what those locutions are and how to model them.
public class AIFdbArgumentMap {

	@JsonProperty("nodes")
	private List<AIFdbNode> nodes = new ArrayList<AIFdbNode>();
	@JsonProperty("edges")
	private List<AIFdbEdge> edges = new ArrayList<AIFdbEdge>();
	
	private Set<Long> fromIDs = new HashSet<Long>();
	private Set<Long> toIDs = new HashSet<Long>();
	private List<String> premises = null;
	private String conclusion = "";
	private String filename = "";
	private String sourceFile;

	private AIFdbArgumentMap() {
		//throw new UnsupportedOperationException("Please use the factory method to create a new map.");
	}

	private AIFdbArgumentMap(List<AIFdbNode> nodes, List<AIFdbEdge> edges) {
		// Validate
		if(nodes == null || nodes.size() == 0)
			throw new IllegalArgumentException("Please provide at least one node in the list.");
		if(edges == null || edges.size() == 0)
			throw new IllegalArgumentException("Please provide at least one edge in the list.");
		
		// Build node and edge lists, with ID sets
		this.nodes = nodes;
		this.edges = edges;
		
		init();
	}

	void init() {
		for(AIFdbEdge edge : this.edges) {
			
			if(edge.getFromID() > -1)
				this.fromIDs.add(edge.getFromID());
			
			if(edge.getToID() > -1)
				this.toIDs.add(edge.getToID());
		}
		
		for(AIFdbNode node : this.nodes) {
			long id = node.getNodeID();
			
			if(! node.getType().equals(NODE_TYPES.I))
				continue;
			
			if(this.fromIDs.contains(id))
				node.setIsPremise(true);
			else
				node.setIsPremise(false);
			
			if(!this.fromIDs.contains(id))
				node.setIsConclusion(true);
		}
	}

	/**
	 * Factory method to build an argument map from a JSON file.
	 * 
	 * @param jsonFilePath Full path to the json file
	 * @return An instance of the map.
	 * @throws RuntimeException
	 */
	public static AIFdbArgumentMap buildFromJsonFile(String jsonFilePath) throws RuntimeException {
		InputStream is = null;
		AIFdbArgumentMap map = null;
		try {
			is = new FileInputStream(jsonFilePath);
	
			map = getJsonObject(is);
			is.close();
		}
		catch(Exception ex) {
			throw new RuntimeException("Error while trying to build map: " + ex.getMessage());
		}
		finally {
			try {
					is.close();
				} catch (IOException e) {
			}
		}
		
		map.setNodesetFilename( getFilename(jsonFilePath) );
		map.init();
		
		return map;
	}
	
	private void setNodesetFilename(String filename) {
		this.filename  = filename;
	}

	private static String getFilename(String filePath) {
		String fileName = "";
		int sepPos = filePath.lastIndexOf(File.separator);
		int extPos = filePath.lastIndexOf(".");
		
		fileName = filePath.substring(sepPos, extPos);
		
		return fileName;
	}

	public int getNodeCount() {
		return this.nodes.size();
	}

	public int getEdgeCount() {
		return this.edges.size();
	}
	
	protected static AIFdbArgumentMap getJsonObject(InputStream inputStream) throws JsonParseException, JsonMappingException, IOException {
	  ObjectMapper mapper = new ObjectMapper();
	  
	  AIFdbArgumentMap map = mapper.readValue(inputStream, AIFdbArgumentMap.class);
	  
	  return map;
	}

	public List<AIFdbNode> getNodes() {
		return this.nodes;
	}

	public List<String> getPremises() {
		if(this.premises  == null) {
			this.premises = new ArrayList<String>();
			for(AIFdbNode node : this.getNodes())
				if(node.isPremise())
					this.premises.add(node.getText());
		}
		
		return this.premises;
	}

	public String getConclusion() {
		if(this.conclusion.isEmpty()) {
			for(AIFdbNode node : this.getNodes())
				if(node.isConclusion()) {
					this.conclusion = node.getText();
					break;
				}
		}
		
		return this.conclusion;
	}
	
	public List<AIFdbNode> getSchemeNodes() {
		List<AIFdbNode> resultList = new ArrayList<>();
		
		for(AIFdbNode node: this.nodes)
			if(node.getScheme() != null && !node.getScheme().isEmpty())
				resultList.add(node);
		
		return resultList;
	}
	
	public List<AIFdbNode> getPrecedingNodes(AIFdbNode node) {
		List<AIFdbNode> resultList = new ArrayList<>();
		long id = node.getNodeID();
		
		for(AIFdbEdge edge : this.edges)
			if(edge.getToID() == id)
				resultList.add(getNodeById(edge.getFromID()));
		
		return resultList;
	}
	
	public List<AIFdbNode> getFollowingNodes(AIFdbNode node) {
		List<AIFdbNode> resultList = new ArrayList<>();
		long id = node.getNodeID();
		
		for(AIFdbEdge edge : this.edges)
			if(edge.getFromID() == id)
				resultList.add(getNodeById(edge.getToID()));
		
		return resultList;
	}
	
	public AIFdbNode getNodeById(long nodeID) {
		for(AIFdbNode node: this.nodes)  // TODO MW: This would be more efficient, if we store nodes in a hash map
			if(node.getNodeID() == nodeID)
				return node;
			
		return null;
	}

	public void setSourceFile(String file) {
		this.sourceFile = file;
	}
	
	public String getSourceFile() {
		return this.sourceFile;
	}
}
