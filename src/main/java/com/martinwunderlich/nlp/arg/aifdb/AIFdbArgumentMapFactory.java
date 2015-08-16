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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Factory class to create a list of argument maps from their JSON representation.
 * 
 * @author Martin Wunderlich (martin@wunderlich.com)
 *
 */
public class AIFdbArgumentMapFactory {

	public static List<AIFdbArgumentMap> buildArgumentMapListFromNodesetJsonFiles(String nodesetPath) {
		FileFilter fileFilter = new WildcardFileFilter("nodeset*.json");
		File sourceDir = new File(nodesetPath);
		
		if(sourceDir == null || !sourceDir.isDirectory() || !sourceDir.exists())
			throw new IllegalArgumentException("Invalid nodeset path provided for argument factory: " + nodesetPath + " is not a directory or does not exist.");
		
		File[] jsonFiles = sourceDir.listFiles(fileFilter);
		List<AIFdbArgumentMap> argMaps = new ArrayList<>();
		for(File jsonFile : jsonFiles) {
			try {
				AIFdbArgumentMap map = buildFromJsonFile(jsonFile.getAbsolutePath());
				argMaps.add(map);
			}
			catch(Exception ex) {
				System.out.println("Error while trying to build argumentation map from file " + jsonFile);
				System.out.println("Details: " + ex.getMessage());
				System.out.println(ex.getStackTrace().toString());
			}
		}
		
		System.out.println("Building list of argumentation maps...DONE");
		System.out.println("Found " + argMaps.size() + " argumentation maps.");
		
		return argMaps;
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
		map.setSourceFile(jsonFilePath.substring(jsonFilePath.lastIndexOf(File.separator) + 1));
		
		return map;
	}
	
	protected static AIFdbArgumentMap getJsonObject(InputStream inputStream) throws JsonParseException, JsonMappingException, IOException {
		  ObjectMapper mapper = new ObjectMapper();
		  
		  AIFdbArgumentMap map = mapper.readValue(inputStream, AIFdbArgumentMap.class);
		  
		  return map;
		}
}
