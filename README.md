# JAIFdb
A Java wrapper for the AIFdb data set of argumentation structures (http://www.aifdb.org/)

Copyright (C) 2015 Martin Wunderlich 
Home page: http://www.martinwunderlich.com/ 
Support and questions: martin@wunderlich.com

-------------------------------------------------------------------------------------------------------------
This program is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License (LGPL) as published by the Free Software Foundation. See http://www.gnu.org/copyleft/lesser.html for details.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
-------------------------------------------------------------------------------------------------------------


This library provides a Java class structure to represent the argumentation data of the AIFdb. It converts the node maps from their JSON representation into a directed graph structure of Java objects, while keeping all the original data intact. 

Note that the data itself is not included, so the unit tests (which rely on this data) will probably fail. 

You can use the library like this: 
```java
		String baseDir = nodesetPath;
		FileFilter fileFilter = new WildcardFileFilter("nodeset*.json");
		File[] jsonFiles = new File(baseDir).listFiles(fileFilter);
		
		List<AIFdbArgumentMap> argMaps = new ArrayList<>();
    for(File jsonFile : jsonFiles) {
				AIFdbArgumentMap map = AIFdbArgumentMap.buildFromJsonFile(jsonFile.getAbsolutePath());
				argMaps.add(map);
				
				for(AIFdbNode oneNode : map.getNodes()) {
				  System.out.println("Found node of type " + node.getType());
				  System.out.println("with text " + oneNode.getText());
				}
		}
```
		
TODO: Need to publish the jar to Maven central repo; add documentation. 
		
