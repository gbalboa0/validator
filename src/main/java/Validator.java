import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;
import utils.NodeHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Validator {
	HashMap<String, Validator> nextValidators = new LinkedHashMap<>();

	public Validator (JsonNode node) {
		node.fields().forEachRemaining(this::createNextValidators);
	}

	boolean evaluate(Actor actor){
		return true;
	}

	public void createNextValidators (@NotNull Map.Entry<String, JsonNode> stringJsonNodeEntry) {
		JsonNode node = stringJsonNodeEntry.getValue();
		if (node.size() != 0)
			nextValidators.put(stringJsonNodeEntry.getKey(), getNextValidator(NodeType(node), NodeHelper.getNextNode(node)));
	}

	void updateValues (JsonNode node) {

	}

	protected void updateNextValidators(@NotNull Map.Entry<String, JsonNode> stringJsonNodeEntry) {
		JsonNode node = stringJsonNodeEntry.getValue();
		Validator nextValidator = nextValidators.get(stringJsonNodeEntry.getKey());

		if (node.size() != 0) {
			if (nextValidator != null){
				nextValidator.updateValues(NodeHelper.getNextNode(node));
			} else {
				createNextValidators(stringJsonNodeEntry);
			}
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
