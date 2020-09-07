import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static utils.JsonLoader.getJsonFile;
import static utils.NodeHelper.getNextMap;

public class ValidatorTest {

	static List<Actor> actors = getMockedActors();

	@Test
	public void TestSuccesfulValidations () throws IOException {
		HashMap<Integer, Boolean> results = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(getJsonFile("example"));

		Map.Entry<String, JsonNode> root = getNextMap(node);

		Validator validator = Validator.getNextValidator(ValidatorType.valueOf(root.getKey()), root.getValue());

		actors.forEach(a -> {
			results.put(a.id, validator.evaluate(a));
		});

		Assert.assertEquals(results.get(1), true);
		Assert.assertEquals(results.get(2), true);
		Assert.assertEquals(results.get(3), false);
		Assert.assertEquals(results.get(4), true);
		Assert.assertEquals(results.get(5), false);
		Assert.assertEquals(results.get(6), true);
		Assert.assertEquals(results.get(7), true);
		Assert.assertEquals(results.get(8), true);
	}

	@Test
	public void TestValidationsAfterTreeUpdate () throws IOException {
		HashMap<Integer, Boolean> results = new HashMap<>();
		HashMap<Integer, Boolean> updateResults = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();

		JsonNode node = mapper.readTree(getJsonFile("example"));
		Map.Entry<String, JsonNode> root = getNextMap(node);

		Validator validator = Validator.getNextValidator(ValidatorType.valueOf(root.getKey()), root.getValue());

		actors.forEach(a -> {
			results.put(a.id, validator.evaluate(a));
		});

		JsonNode node2 = mapper.readTree(getJsonFile("example2"));
		Map.Entry<String, JsonNode> root2 = getNextMap(node2);

		validator.updateValues(root2.getValue());

		actors.forEach(a -> {
			updateResults.put(a.id, validator.evaluate(a));
		});


		Assert.assertEquals(results.get(1), true);
		Assert.assertEquals(results.get(2), true);
		Assert.assertEquals(results.get(3), false);
		Assert.assertEquals(results.get(4), true);
		Assert.assertEquals(results.get(5), false);
		Assert.assertEquals(results.get(6), true);
		Assert.assertEquals(results.get(7), true);
		Assert.assertEquals(results.get(8), true);

		Assert.assertEquals(updateResults.get(1), false);
		Assert.assertEquals(updateResults.get(2), false);
		Assert.assertEquals(updateResults.get(3), false);
		Assert.assertEquals(updateResults.get(4), true);
		Assert.assertEquals(updateResults.get(5), true);
		Assert.assertEquals(updateResults.get(6), false);
		Assert.assertEquals(updateResults.get(7), true);
		Assert.assertEquals(updateResults.get(8), false);
	}

	public static List<Actor> getMockedActors() {
		return new ArrayList<Actor>(Arrays.asList(
				new Actor(25, "Mexico", "Hombre"),
				new Actor(25, "Argentina", "Hombre"),
				new Actor(75, "Brasil", "Hombre"),
				new Actor(19, "Argentina", "Mujer"),
				new Actor(65, "Brasil", "Mujer"),
				new Actor(100, "Mexico", "Mujer"),
				new Actor(27, "Mexico", "Hombre"),
				new Actor(25, "Ecuador", "Hombre")
		)){};
	}
}
