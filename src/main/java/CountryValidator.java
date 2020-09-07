import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;
import utils.NodeHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static utils.NodeHelper.getNodeKeys;

public class CountryValidator extends Validator {
	ArrayList<String> countries = new ArrayList<>();

	CountryValidator(JsonNode node){
		super(node);
		setCountries(node);
	}

	@Override
	public void updateValues (JsonNode node){
		setCountries(node);
		clearOldValidators();
		node.fields().forEachRemaining(this::updateNextValidators);
	}

	@Override
	public boolean evaluate(Actor actor) {
		return countries.contains(actor.country) && evaluateNextNode(actor);
	}

	private boolean evaluateNextNode(Actor actor) {
		return nextValidators.get(actor.country) == null || nextValidators.get(actor.country).evaluate(actor);
	}

	private void setCountries(JsonNode node) {
		countries.clear();
		node.fieldNames().forEachRemaining(n -> {
			countries.addAll(getNodeKeys(n));
		});
	}

	private void clearOldValidators() {
		nextValidators.keySet().forEach(k -> {
			if(!countries.contains(k)){
				nextValidators.remove(k);
			}
		});
	}
}
