import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ValidatorInterface {
	boolean evaluate(Actor actor);

	void createNextValidators (@NotNull Map.Entry<String, JsonNode> stringJsonNodeEntry);

	void updateValues (JsonNode node);
}
