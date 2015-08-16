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
package com.martinwunderlich.nlp.arg.aifdb.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.martinwunderlich.nlp.arg.aifdb.AIFdbArgumentMap;
import com.martinwunderlich.nlp.arg.aifdb.AIFdbArgumentMapFactory;
import com.martinwunderlich.nlp.arg.aifdb.graph.AIFdbNode;
import com.martinwunderlich.nlp.arg.aifdb.graph.NODE_TYPES;
import com.martinwunderlich.nlp.arg.aifdb.util.AIFdbArgumentMapUtil;

/**
 * Unit tests.
 * 
 * @author Martin Wunderlich (martin@wunderlich.com)
 *
 */
public class ArgMapTest {

	String araucariaPath = "src/test/resources/araucaria/nodesets";
	String schemesPath = "src/test/resources/schemes/nodesets";
	
	@org.junit.Test
	public void testMapCountsA() {
		List<AIFdbArgumentMap> araucariaList = AIFdbArgumentMapFactory.buildArgumentMapListFromNodesetJsonFiles(araucariaPath);
		assertEquals("The number of argument maps in the araucaria list is incorrect", araucariaList.size(), 661);
		
		try {
			String filePath = "src/test/resources/araucaria/outfile.txt";
			File outFile = new File(filePath);
			if(outFile.exists())
				FileUtils.forceDelete(outFile);
			AIFdbArgumentMapUtil.printNodesToFile(araucariaList, NODE_TYPES.I, "src/test/resources/araucaria/outfile.txt");
		} catch (IOException e) {
			fail("Error while trying to write map list to file: " + e.getLocalizedMessage());
		}
	}
	
	@org.junit.Test
	public void testNodeAndEdgeCounts() {
		List<AIFdbArgumentMap> araucariaList = AIFdbArgumentMapFactory.buildArgumentMapListFromNodesetJsonFiles(araucariaPath);
		int nodeCount = 0;
		int edgeCount = 0;
		
		for(AIFdbArgumentMap map : araucariaList) {
			nodeCount += map.getNodeCount();
			edgeCount += map.getEdgeCount();
		}
		
		assertEquals("The total number of argument nodes is incorrect", 5858, nodeCount);
		assertEquals("The total number of argument edges is incorrect", 5221, edgeCount);
	}
	
	@org.junit.Test
		public void testMapCountsS() {
		List<AIFdbArgumentMap> schemesList = AIFdbArgumentMapFactory.buildArgumentMapListFromNodesetJsonFiles(schemesPath);
		assertEquals("The number of argument maps in the schemes list is incorrect", schemesList.size(), 31);
		
		try {
			String filePath = "src/test/resources/schemes/outfile.txt";
			File outFile = new File(filePath);
			if(outFile.exists())
				FileUtils.forceDelete(outFile);
			AIFdbArgumentMapUtil.printNodesToFile(schemesList, NODE_TYPES.I, "src/test/resources/schemes/outfile.txt");
		} catch (IOException e) {
			fail("Error while trying to write map list to file: " + e.getLocalizedMessage());
		}
	}
	
	@org.junit.Test
	public void testNodeRetrieval() {
		List<AIFdbArgumentMap> araucariaList = AIFdbArgumentMapFactory.buildArgumentMapListFromNodesetJsonFiles(araucariaPath);

		AIFdbArgumentMap map15 = null;
		for(AIFdbArgumentMap map : araucariaList)
			if(map.getSourceFile().equals("nodeset15.json")) {	// TODO MW: Create arg map class with convenient methods for this.
				map15 = map;
				break;
			}
		
		assertNotNull("Argument for nodeset15 not found.", map15);
		
		assertEquals("Incorrect number of nodes.", 9, map15.getNodeCount());
		assertEquals("Incorrect number of edges.", 8, map15.getEdgeCount());
		assertEquals("Incorrect number of scheme nodes.", 2, map15.getSchemeNodes().size());
		
		AIFdbNode schemeNode1 = map15.getNodeById(307);
		assertNotNull("Scheme/RA node 307 not found", schemeNode1);
		AIFdbNode schemeNode2 = map15.getNodeById(308);
		assertNotNull("Scheme/RA node 308 not found", schemeNode2);
		
		assertEquals("Incorrect number of preceding nodes.", 3, map15.getPrecedingNodes(schemeNode1).size());
		assertEquals("Incorrect number of following nodes.", 1, map15.getFollowingNodes(schemeNode1).size());
		assertEquals("Incorrect number of preceding nodes.", 3, map15.getPrecedingNodes(schemeNode2).size());
		assertEquals("Incorrect number of following nodes.", 1, map15.getFollowingNodes(schemeNode2).size());
	}
}
