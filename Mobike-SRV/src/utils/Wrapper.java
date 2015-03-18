package utils;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Wrapper {

	private ObjectMapper mapper;

	public Wrapper() {
		this.mapper = new ObjectMapper();

	}

	public String wrap(Map<String, String> map) {

		if (map != null) {
			String output = null;

			try {
				output = this.mapper.writeValueAsString(map);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return output;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> unwrap(String wrappingJson) {
		Map<String, String> map = null;
		if(wrappingJson != null){
			try {
				map = (Map<String, String>) mapper.readValue(wrappingJson,
						Map.class);
			} catch (IOException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
	

}

