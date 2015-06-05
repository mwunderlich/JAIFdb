package com.martinwunderlich.nlp.arg.aifdb.graph;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class AIFdbArgumentMap {

	@JsonProperty("nodes")
	private List<AIFdbNode> nodes = new ArrayList<AIFdbNode>();
	@JsonProperty("edges")
	private List<AIFdbEdge> edges = new ArrayList<AIFdbEdge>();
	
	private Set<Long> fromIDs = new HashSet<Long>();
	private Set<Long> toIDs = new HashSet<Long>();

	private AIFdbArgumentMap() {
		//throw new UnsupportedOperationException("Please use the factory method to create a new map.");
	}

	private AIFdbArgumentMap(List<AIFdbNode> nodes, List<AIFdbEdge> edges) {
		this.nodes = nodes;
		this.edges = edges;
		
		init();
	}

	public void init() {
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
		
		map.init();
		
		return map;
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
}
