import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class GenderValidator extends Validator {
	ArrayList<String> genders = new ArrayList<>();

	public GenderValidator(JsonNode node) {
		super(node);
		node.fieldNames().forEachRemaining(e -> genders.add(e));
	}

	@Override
	public void updateValues(JsonNode node) {
		genders.clear();
		node.fieldNames().forEachRemaining(e -> genders.add(e));
		node.fields().forEachRemaining(this::updateNextValidators);
	}

	@Override
	public boolean evaluate(Actor actor) {
		return genders.contains(actor.gender) && evaluateNextNode(actor);
	}

	private boolean evaluateNextNode(Actor actor) {
		return nextValidators.get(actor.gender) == null || nextValidators.get(actor.gender).evaluate(actor);
	}
}
