import com.fasterxml.jackson.databind.JsonNode;

public enum ValidatorType {
	Country ("Country"), Gender("Gender"), Age("Age");

	public final String label;

	ValidatorType(String label) {
		this.label = label;
	}


}
