import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

import static utils.NodeHelper.getNodeKeys;

public class CountryValidator extends Validator {
	ArrayList<String> countries = new ArrayList<>();

	CountryValidator(JsonNode node){
		super(node);
		setValues(node);
	}

	/*@Override
	public void updateValues (JsonNode node){
		setValues(node);
		clearOldValidators(countries);
		node.fields().forEachRemaining(this::updateNextValidators);
	}*/

	@Override
	public boolean evaluate(Actor actor) {
		return countries.contains(actor.country) && evaluateNextNode(actor);
	}

	private boolean evaluateNextNode(Actor actor) {
		return nextValidators.get(actor.country) == null || nextValidators.get(actor.country).evaluate(actor);
	}

	@Override
	protected void setValues(JsonNode node) {
		countries.clear();
		node.fieldNames().forEachRemaining(n -> {
			countries.addAll(getNodeKeys(n));
		});
	}

	@Override
	protected void clearOldValidators() {
		nextValidators.keySet().forEach(k -> {
			if(!countries.contains(k)){
				nextValidators.remove(k);
			}
		});
	}
}
