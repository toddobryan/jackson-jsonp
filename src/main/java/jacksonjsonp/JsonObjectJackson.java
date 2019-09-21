package jacksonjsonp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

public class JsonObjectJackson implements JsonObject {
    private Map<String, JsonValue> map;

    JsonObjectJackson(ObjectNode object) {
        this.map = new LinkedHashMap<>();
        Iterator<String> it = object.fieldNames();
        while (it.hasNext()) {
            String key = it.next();
            JsonNode value = object.get(key);
            map.put(key, JsonValueJackson.toJson(value));
        }
    }

    JsonObjectJackson(Map<String, JsonValue> map) {
        this.map = map;
    }

    @Override
    public JsonArray getJsonArray(String name) {
        return (JsonArray) map.get(name);
    }

    @Override
    public JsonObject getJsonObject(String name) {
        return (JsonObject) map.get(name);
    }

    @Override
    public JsonNumber getJsonNumber(String name) {
        return (JsonNumber) map.get(name);
    }

    @Override
    public JsonString getJsonString(String name) {
        return (JsonString) map.get(name);
    }

    @Override
    public String getString(String name) {
        return getJsonString(name).getString();
    }

    @Override
    public String getString(String name, String defaultValue) {
        JsonValue value = map.get(name);
        if (value != null && value.getValueType() == ValueType.STRING) {
            return getString(name);
        } else {
            return defaultValue;
        }
    }

    @Override
    public int getInt(String name) {
        return getJsonNumber(name).intValue();
    }

    @Override
    public int getInt(String name, int defaultValue) {
        JsonValue value = map.get(name);
        if (value != null && value.getValueType() == ValueType.NUMBER) {
            return getInt(name);
        } else {
            return defaultValue;
        }
    }

    private Boolean booleanOrNull(String name) {
        JsonValue value = map.get(name);
        if (value == JsonValue.TRUE) {
            return Boolean.TRUE;
        } else if (value == JsonValue.FALSE) {
            return Boolean.FALSE;
        } else {
            return null;
        }

    }

    @Override
    public boolean getBoolean(String name) {
        Boolean value = booleanOrNull(name);
        if (value != null) {
            return value;
        } else {
            throw new ClassCastException("Expected boolean, given: " + map.get(name));
        }
    }

    @Override
    public boolean getBoolean(String name, boolean defaultValue) {
        Boolean value = booleanOrNull(name);
        if (value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }

    @Override
    public boolean isNull(String name) {
        return map.get(name) == JsonValue.NULL;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public JsonValue get(Object key) {
        return map.get(key);
    }

    @Override
    public JsonValue put(String key, JsonValue value) {
        throw new UnsupportedOperationException("JsonObject is immutable");
    }

    @Override
    public JsonValue remove(Object key) {
        throw new UnsupportedOperationException("JsonObject is immutable");
    }

    @Override
    public void putAll(Map<? extends String, ? extends JsonValue> m) {
        throw new UnsupportedOperationException("JsonObject is immutable");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("JsonObject is immutable");
    }

    @Override
    public Set<String> keySet() {
        return new ImmutableSet<>(map.keySet());
    }

    @Override
    public Collection<JsonValue> values() {
        return new ImmutableCollection<JsonValue>(map.values());
    }

    @Override
    public Set<Entry<String, JsonValue>> entrySet() {
        return new ImmutableSet<>(map.entrySet());
    }

    @Override
    public ValueType getValueType() {
        return ValueType.OBJECT;
    }

    @Override
    public String toString() {
        return "{" +
                String.join(",", map.entrySet().stream().map(
                        e -> "\"" + e.getKey() + "\"" + ":" + e.getValue())
                        .collect(Collectors.toList()))
                + "}";
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof JsonObject) {
            return this.map.equals(o);
        } else {
            return false;
        }
    }
}
