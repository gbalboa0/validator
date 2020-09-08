import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AgeValidator extends Validator {
	int minAge;
	int maxAge;

	AgeValidator (JsonNode node){
		super(node);
		setValues(node);
	}

	/*@Override
	public void updateValues (JsonNode node){
		minAge = node.get("minAge").intValue();
		maxAge = node.get("maxAge").intValue();
		node.fields().forEachRemaining(this::updateNextValidators);
	}*/

	@Override
	public boolean evaluate(Actor actor) {
		return actor.age > minAge && actor.age < maxAge && evaluateNextNode(actor);
	}

	@Override
	protected void setValues(JsonNode node) {
		minAge = node.get("minAge").intValue();
		maxAge = node.get("maxAge").intValue();
	}

	private boolean evaluateNextNode(Actor actor) {
		return nextValidators.get(actor.country) == null || nextValidators.get(actor.country).evaluate(actor);
	}

	@Override
	protected void clearOldValidators() {
		/*nextValidators.keySet().forEach(k -> {
			if(!list.contains(k)){
				nextValidators.remove(k);
			}
		});*/
	}
}
