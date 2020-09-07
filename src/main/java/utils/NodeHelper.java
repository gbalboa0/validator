package utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public class NodeHelper {
	public static JsonNode getNextNode(JsonNode node){
		return node.elements().next();
	}
	public static Map.Entry<String, JsonNode> getNextMap (JsonNode node) { return node.fields().next();}
}
