package com.utek.disasterrelief.demos.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtil {

    public static final String DEFAULT_KEY="DEFAULT";

    private static ObjectMapper mapper=new ObjectMapper();

    public static ObjectNode newNode(){
        return mapper.createObjectNode();
    }

    public static String toJSONString(Object object){
        ObjectMapper mapper=new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode readTree(String str){
        try {
            return mapper.readTree(str);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T>  T parse(String str,Class<T> obj)
    {

        try {
            return mapper.readValue(str,obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T>  T parse(String str, JavaType javaType)
    {
        try {
            return mapper.readValue(str,javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
         return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static <T>  T parseFromDefault(String json, Class<T> obj){
        try {
            JsonNode jsonNode= mapper.readValue(json, JsonNode.class);
            JsonNode key = jsonNode.get(DEFAULT_KEY);
            if (key.isTextual()){
                String str= key.asText();
                return mapper.readValue(str,obj);
            }
            return null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }
}
