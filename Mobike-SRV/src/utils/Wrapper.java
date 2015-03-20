package utils;


import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Wrapper {

	private ObjectMapper mapper;

	public Wrapper() {
		this.mapper = new ObjectMapper();

	}

	public String wrap(Map<String, String> map) throws Exception {

		if (map != null) {
			String output = null;
			output = this.mapper.writeValueAsString(map);
			return output;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> unwrap(String wrappingJson) throws Exception {
		Map<String, String> map = null;
		if (wrappingJson != null) {
			map = new HashMap<String, String>();
			map = (HashMap<String, String>) mapper.readValue(wrappingJson,
					Map.class);
		}
		return map;
	}

}
