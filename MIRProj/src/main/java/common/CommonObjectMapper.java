package common;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.ObjectMapper;

// singleton pattern
public enum CommonObjectMapper {
	INSTANCE;

	private ObjectMapper _objectMapper;

	private CommonObjectMapper() {
		_objectMapper = new ObjectMapper();
		_objectMapper.setVisibilityChecker(_objectMapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE));
	}

	public ObjectMapper getObjectMapper() {
		return _objectMapper;
	}

}
