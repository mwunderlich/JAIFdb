package com.martinwunderlich.nlp.arg.aifdb.graph.test;

import org.junit.Assert;
import org.junit.Test;

import com.martinwunderlich.nlp.arg.aifdb.graph.AIFdbArgumentMap;

public class JSONTest {

	@Test
	public void test() {
		String jsonFilePath = "src/test/resources/araucaria_nodeset7.json";
		
		AIFdbArgumentMap map = AIFdbArgumentMap.buildFromJsonFile(jsonFilePath);
		
		Assert.assertEquals(map.getNodeCount(), 4);
		Assert.assertEquals(map.getEdgeCount(), 3);
		
		/* TODO MW
		Assert.assertTrue(map.getNodeById(255).isConclusion());
		Assert.assertFalse(map.getNodeById(255).isPremise());
		Assert.assertFalse(map.getNodeById(256).isConclusion());
		Assert.assertTrue(map.getNodeById(256).isPremise());
		Assert.assertFalse(map.getNodeById(257).isConclusion());
		Assert.assertTrue(map.getNodeById(257).isPremise());
		Assert.assertFalse(map.getNodeById(258).isConclusion());
		Assert.assertFalse(map.getNodeById(258).isPremise());
		*/
	}
}
