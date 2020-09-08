package utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NodeHelper {
	public static JsonNode getNextNode(JsonNode node){
		return node.elements().next();
	}
	public static String getNodeType (JsonNode node){
		return node.elements().next().textValue();
	}
	public static Map.Entry<String, JsonNode> getNextMap (JsonNode node) { return node.fields().next();}
	public static List<String> getNodeKeys (String keys) { return Arrays.asList(keys.split("\\s*,\\s*"));}
}
