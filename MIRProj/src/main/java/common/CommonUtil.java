package common;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.ObjectMapper;

import domain.User;

// singleton pattern
public enum CommonUtil {
	INSTANCE;

	private ObjectMapper _objectMapper;
	private Map<Integer, User> _dbUserSet = new HashMap<>();

	private CommonUtil() {
		_objectMapper = new ObjectMapper();
		_objectMapper.setVisibilityChecker(_objectMapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE));
		_dbUserSet.put(1, new User(1, "John Doe", "John", "Doe", "john.doe@acme.com", 858_555_1212L));
		_dbUserSet.put(2, new User(2, "Hash brown", "Hash", "brown", "hb@acme.com", 123_456_7890L));
		_dbUserSet.put(3, new User(3, "David white", "David", "white", "dw@acme.com", 333_222_1234L));
	}

	public ObjectMapper getObjectMapper() {
		return _objectMapper;
	}

	public Map<Integer, User> getDbUserMap() {
		return _dbUserSet;
	}

}
