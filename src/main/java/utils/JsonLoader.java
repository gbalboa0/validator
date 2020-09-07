package utils;

import java.io.File;
import java.nio.file.Paths;

public class JsonLoader {
	public static File getJsonFile(String name){
		return Paths.get("/Users/gbalboa/Downloads/TestJackson/src/main/java/" + name + ".json").toFile();
	}
}
