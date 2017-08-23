package com.blog.library;

import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/*
 * This is a singleton class which reads json files.
 */
public class JsonReader {
	
	private static JSONParser parser = new JSONParser();
	private static JSONObject jsonObject = null;

	private JsonReader() {}

	private static class JsonReaderInstance {
		public static final JsonReader INSTANCE = new JsonReader();
	}

	public static JsonReader getInstance() {

		return JsonReaderInstance.INSTANCE;
	}

	/*
	 * This method sets the json file to current jsonObject
	 */
	public void setJsonFile(String fileName) {

		try {
			Object obj = parser.parse(new FileReader(fileName));
			jsonObject = (JSONObject) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * This method is to get Value from json file by 
	 * providing json key and returns json Object Type
	 */
	public JSONObject getJsonValue(String jsonKey) {
		return (JSONObject) jsonObject.get(jsonKey);
	}
	
	/*
	 * This method is to get jsonArray from json file by providing json key
	 */
	public JSONArray getJsonArray(String jsonKey) {
        return (JSONArray) jsonObject.get(jsonKey);
	}

	/*
	 * This method is to get value from json file by providing json key and returns Object Type
	 */
	public Object getValue(String jsonKey) {
		return (Object) jsonObject.get(jsonKey);
	}

}
