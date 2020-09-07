import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;
import utils.NodeHelper;

import java.util.ArrayList;
import java.util.Map;

public class CountryValidator extends Validator {
	ArrayList<String> countries = new ArrayList<>();

	CountryValidator(JsonNode node){
		super(node);
		node.fieldNames().forEachRemaining(n -> countries.add(n));
	}

	@Override
	public void updateValues (JsonNode node){
		countries.clear();
		node.fieldNames().forEachRemaining(e -> countries.add(e));
		node.fields().forEachRemaining(this::updateNextValidators);
	}

	@Override
	public boolean evaluate(Actor actor) {
		return countries.contains(actor.country) && evaluateNextNode(actor);
	}

	private boolean evaluateNextNode(Actor actor) {
		return nextValidators.get(actor.country) == null || nextValidators.get(actor.country).evaluate(actor);
	}
}
