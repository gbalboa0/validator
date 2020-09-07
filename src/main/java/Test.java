import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static utils.JsonLoader.getJsonFile;

public class Test {
	public static void main(String[] args) throws IOException {

		ArrayList <Actor> actors = new ArrayList<Actor>(Arrays.asList(
				new Actor(25, "Mexico", "Hombre"),
				new Actor(25, "Argentina", "Hombre"),
				new Actor(75, "Brasil", "Hombre")
				)){};

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(getJsonFile("example"));
		Map.Entry<String, JsonNode> root = node.fields().next();

		Validator firstValidator = Validator.getNextValidator(ValidatorType.valueOf(root.getKey()), root.getValue());
		actors.forEach(actor ->
			System.out.println("Validation: " + firstValidator.evaluate(actor))
		);

		JsonNode node2 = mapper.readTree(Paths.get("/Users/gbalboa/Downloads/TestJackson/src/main/java/example2.json").toFile());
		Map.Entry<String, JsonNode> root2 = node2.fields().next();

		firstValidator.updateValues(root2.getValue());

		ArrayList <Actor> actors2 = new ArrayList<Actor>(Arrays.asList(
				new Actor(25, "Argentina", "Hombre"),
				new Actor(25, "Brasil", "Hombre"),
				new Actor(25, "Mexico", "Hombre"),
				new Actor(27, "Mexico", "Hombre")
		)){};
		actors2.forEach(actor ->
				System.out.println("Validation: " + firstValidator.evaluate(actor))
		);
	}
}
