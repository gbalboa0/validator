import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static utils.NodeHelper.getNodeKeys;

public class GenderValidator extends Validator {
	ArrayList<String> genders = new ArrayList<>();

	public GenderValidator(JsonNode node) {
		super(node);
		setValues(node);
	}

	/*@Override
	public void updateValues(JsonNode node) {
		genders.clear();
		node.fieldNames().forEachRemaining(e -> genders.add(e));
		node.fields().forEachRemaining(this::updateNextValidators);
	}*/

	@Override
	public boolean evaluate(Actor actor) {
		return genders.contains(actor.gender) && evaluateNextNode(actor);
	}

	@Override
	protected void setValues(JsonNode node) {
		genders.clear();
		node.fieldNames().forEachRemaining(n -> {
			genders.addAll(getNodeKeys(n));
		});
	}

	private boolean evaluateNextNode(Actor actor) {
		return nextValidators.get(actor.gender) == null || nextValidators.get(actor.gender).evaluate(actor);
	}

	@Override
	protected void clearOldValidators() {
		nextValidators.keySet().forEach(k -> {
			if(!genders.contains(k)){
				nextValidators.remove(k);
			}
		});
	}
}
