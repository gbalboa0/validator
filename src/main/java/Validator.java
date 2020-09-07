import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;
import utils.NodeHelper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static utils.NodeHelper.getNodeKeys;

public abstract class Validator {
	ConcurrentHashMap<String, Validator> nextValidators = new ConcurrentHashMap<>();

	public Validator (JsonNode node) {
		node.fields().forEachRemaining(this::createNextValidators);
	}

	boolean evaluate(Actor actor){
		return true;
	}

	public void createNextValidators (@NotNull Map.Entry<String, JsonNode> stringJsonNodeEntry) {
		JsonNode node = stringJsonNodeEntry.getValue();
		List<String> keys = Arrays.asList(stringJsonNodeEntry.getKey().split("\\s*,\\s*"));
		if (node.size() != 0) {
			Validator nextValidator = getNextValidator(NodeType(node), NodeHelper.getNextNode(node));
			keys.forEach(k -> {
				nextValidators.put(k, nextValidator);
			});
		}
	}

	void updateValues (JsonNode node) {

	}

	protected void updateNextValidators(@NotNull Map.Entry<String, JsonNode> stringJsonNodeEntry) {
		List<String> keys = getNodeKeys(stringJsonNodeEntry.getKey());
		List<Validator> validatorsToUpdate = new ArrayList<>();


		keys.forEach(key -> {
			validatorsToUpdate.add(nextValidators.get(key));
		});

		updateValidators(validatorsToUpdate, stringJsonNodeEntry);
	}

	protected void updateValidators(List<Validator> validatorsToUpdate, Map.Entry<String, JsonNode> stringJsonNodeEntry){
		JsonNode node = stringJsonNodeEntry.getValue();

		if (node.size() != 0) {
			validatorsToUpdate.forEach(nextValidator -> {
				if (nextValidator != null){
					nextValidator.updateValues(NodeHelper.getNextNode(node));
				} else {
					createNextValidators(stringJsonNodeEntry);
				}
			});
		}
	}

	public static Validator getNextValidator(ValidatorType validatorType, JsonNode node) {
		switch (validatorType) {
			case Country:
				return new CountryValidator(node);
			case Gender:
				return new GenderValidator(node);
			case Age:
				return new AgeValidator(node);
			default:
				return null;
		}
	}

	public ValidatorType NodeType (JsonNode node) {
		return ValidatorType.valueOf(node.fieldNames().next());
	}
}
