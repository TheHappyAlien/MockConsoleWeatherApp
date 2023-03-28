package Sensors_TheOverkillPackage.UsabilityClasses;

import com.google.gson.*;

import java.lang.reflect.Type;


public class InterfaceAdapter implements JsonSerializer, JsonDeserializer {

    private static final String sensorType = "SensorType";
    private static final String data = "Data";

    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(sensorType);
        String className = prim.getAsString();
        Class someClass = getObjectClass(className);
        return jsonDeserializationContext.deserialize(jsonObject.get(data), someClass);
    }
    public JsonElement serialize(Object jsonElement, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(sensorType, jsonElement.getClass().getName());
        jsonObject.add(data, jsonSerializationContext.serialize(jsonElement));
        return jsonObject;
    }
    /****** Helper method to get the className of the object to be deserialized *****/
    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
    }
}
