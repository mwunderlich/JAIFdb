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
package com.martinwunderlich.nlp.arg.aifdb.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.martinwunderlich.nlp.arg.aifdb.AIFdbArgumentMap;
import com.martinwunderlich.nlp.arg.aifdb.graph.AIFdbNode;
import com.martinwunderlich.nlp.arg.aifdb.graph.NODE_TYPES;

/**
 * Static utility class to provide various helper functions.
 * 
 * @author Martin Wunderlich (martin@wunderlich.com)
 *
 */
public class AIFdbArgumentMapUtil {

	public static void printNodesToFile(List<AIFdbArgumentMap> list, NODE_TYPES type, String outfilePath) throws IOException
	{
		StringBuilder builder = new StringBuilder();
		
		for(AIFdbArgumentMap map : list) {
			for(AIFdbNode node : map.getNodes())
				if(node.getType().equals(type))
					builder.append(node.getText() + "\n");
		}
		
		FileUtils.writeStringToFile(new File(outfilePath), builder.toString());
	}
}
