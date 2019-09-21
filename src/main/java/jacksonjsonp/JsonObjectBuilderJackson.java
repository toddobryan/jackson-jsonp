package jacksonjsonp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import static jacksonjsonp.JsonValueJackson.toJson;
import static jacksonjsonp.Util.throwIfNull;

public class JsonObjectBuilderJackson implements JsonObjectBuilder {
    private Map<String, JsonValue> map;

    JsonObjectBuilderJackson() {
        this.map = new LinkedHashMap<>();
    }

    JsonObjectBuilderJackson(JsonObject obj) {
        this.map = new LinkedHashMap<>(obj);
    }

    JsonObjectBuilderJackson(Map<String, Object> map) {
        this.map = new LinkedHashMap<>();
        for (Map.Entry<String, Object> e : map.entrySet()) {
            this.map.put(e.getKey(), toJson(e.getValue()));
        }
    }

    @Override
    public JsonObjectBuilder add(String name, JsonValue value) {
        throwIfNull(value, "value");
        return addFluently(name, value);
    }

    @Override
    public JsonObjectBuilder add(String name, String value) {
        throwIfNull(value, "value");
        return addFluently(name, toJson(value));
    }

    @Override
    public JsonObjectBuilder add(String name, BigInteger value) {
        throwIfNull(value, "value");
        return addFluently(name, toJson(value));
    }

    @Override
    public JsonObjectBuilder add(String name, BigDecimal value) {
        throwIfNull(value, "value");
        return addFluently(name, toJson(value));
    }

    @Override
    public JsonObjectBuilder add(String name, int value) {
        return addFluently(name, toJson(value));
    }

    @Override
    public JsonObjectBuilder add(String name, long value) {
        return addFluently(name, toJson(value));
    }

    @Override
    public JsonObjectBuilder add(String name, double value) {
        return addFluently(name, toJson(value));
    }

    @Override
    public JsonObjectBuilder add(String name, boolean value) {
        return addFluently(name, toJson(value));
    }

    @Override
    public JsonObjectBuilder addNull(String name) {
        return addFluently(name, JsonValue.NULL);
    }

    @Override
    public JsonObjectBuilder add(String name, JsonObjectBuilder builder) {
        throwIfNull(builder, "builder");
        return addFluently(name, builder.build());
    }

    @Override
    public JsonObjectBuilder add(String name, JsonArrayBuilder builder) {
        throwIfNull(builder, "builder");
        return addFluently(name, builder.build());
    }

    @Override
    public JsonObjectBuilder addAll(JsonObjectBuilder builder) {
        throwIfNull(builder, "builder");
        map.putAll(builder.build());
        return this;
    }

    @Override
    public JsonObjectBuilder remove(String name) {
        map.remove(name);
        return this;
    }

    @Override
    public JsonObject build() {
        return new JsonObjectJackson(map);
    }

    private JsonObjectBuilder addFluently(String name, JsonValue value) {
        map.put(name, value);
        return this;
    }
}
