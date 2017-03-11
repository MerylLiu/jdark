package com.iptv.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	public static String getJson(Object data){
		ObjectMapper mapper = new ObjectMapper();
		String res = "";

		try {
			res = mapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return res;
	}
}
