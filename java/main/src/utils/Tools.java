/*
 * Copyright (c) 2013 Technologic Arts.
 * All right reserved.
 */

package utils;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import facebook4j.ResponseList;

/**
 * Created the 5/10/13.
 * 
 * @author Thanh Toan
 */
public class Tools {

	/**
	 * This class object mapper, used to serialize into Json.
	 */
	private static ObjectMapper mapper = new ObjectMapper();

	public static <T> JsonNode listToJson(List<T> models) {
		return mapper.convertValue(models, JsonNode.class);
	}
	
	public static <T> JsonNode responseListToJson(ResponseList<T> models) {
		return mapper.convertValue(models, JsonNode.class);
	}

	public static JsonNode toJson(Object content) {
		return mapper.convertValue(content, JsonNode.class);
	}

	public static <T> T fromJson(String json, Class<T> valueType)
			throws IOException {
		return mapper.readValue(json, valueType);
	}

	public static <T> T fromJson(JsonNode json, Class<T> valueType)
			throws IOException {
		return mapper.readValue(json, valueType);
	}

	public static <T> List<T> listFromJson(String content, Class<T> valueType)
			throws IOException {
		return mapper.readValue(content, mapper.getTypeFactory()
				.constructCollectionType(List.class, valueType));
	}

	public static <T> List<T> listFromJson(JsonNode content, Class<T> valueType)
			throws IOException {
		return mapper.readValue(content, mapper.getTypeFactory()
				.constructCollectionType(List.class, valueType));
	}
}
