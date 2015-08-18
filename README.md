# JAIFdb
A Java wrapper for the AIFdb data set of argumentation structures (http://www.aifdb.org/)

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
		
