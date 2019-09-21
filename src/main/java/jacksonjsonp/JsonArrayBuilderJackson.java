package jacksonjsonp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import static jacksonjsonp.JsonValueJackson.toJson;
import static jacksonjsonp.Util.throwIfNull;

public class JsonArrayBuilderJackson implements JsonArrayBuilder {
    private List<JsonValue> values;

    JsonArrayBuilderJackson() {
        this.values = new ArrayList<>();
    }

    JsonArrayBuilderJackson(JsonArray array) {
        this.values = new ArrayList<>(array);
    }

    JsonArrayBuilderJackson(Collection<?> collection) {
        this.values = new ArrayList<>();
        for (Object x : collection) {
            if (x instanceof Optional) {
                Optional opt = (Optional) x;
                if (opt.isPresent()) {
                    values.add(toJson(opt.get()));
                }
            } else {
                values.add(toJson(x));
            }
        }
    }

    @Override
    public JsonArrayBuilder add(JsonValue value) {
        throwIfNull(value, "value");
        return addFluently(value);
    }

    @Override
    public JsonArrayBuilder add(String value) {
        throwIfNull(value, "value");
        return addFluently(toJson(value));
    }

    @Override
    public JsonArrayBuilder add(BigDecimal value) {
        throwIfNull(value, "value");
        return addFluently(toJson(value));
    }

    @Override
    public JsonArrayBuilder add(BigInteger value) {
        throwIfNull(value, "value");
        return addFluently(toJson(value));
    }

    @Override
    public JsonArrayBuilder add(int value) {
        return addFluently(toJson(value));
    }

    @Override
    public JsonArrayBuilder add(long value) {
        return addFluently(toJson(value));
    }

    @Override
    public JsonArrayBuilder add(double value) {
        return addFluently(toJson(value));
    }

    @Override
    public JsonArrayBuilder add(boolean value) {
        return addFluently(toJson(value));
    }

    @Override
    public JsonArrayBuilder addNull() {
        return addFluently(JsonValue.NULL);
    }

    @Override
    public JsonArrayBuilder add(JsonObjectBuilder builder) {
        throwIfNull(builder, "builder");
        return addFluently(builder.build());
    }

    @Override
    public JsonArrayBuilder add(JsonArrayBuilder builder) {
        throwIfNull(builder, "builder");
        return addFluently(builder.build());
    }

    @Override
    public JsonArrayBuilder addAll(JsonArrayBuilder builder) {
        throwIfNull(builder, "builder");
        values.addAll(builder.build());
        return this;
    }

    @Override
    public JsonArrayBuilder add(int index, JsonValue value) {
        throwIfNull(value, "value");
        return addFluently(index, value);
    }

    @Override
    public JsonArrayBuilder add(int index, String value) {
        throwIfNull(value, "value");
        return addFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder add(int index, BigDecimal value) {
        throwIfNull(value, "value");
        return addFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder add(int index, BigInteger value) {
        throwIfNull(value, "value");
        return addFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder add(int index, int value) {
        return addFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder add(int index, long value) {
        return addFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder add(int index, double value) {
        return addFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder add(int index, boolean value) {
        return addFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder addNull(int index) {
        return addFluently(index, JsonValue.NULL);
    }

    @Override
    public JsonArrayBuilder add(int index, JsonObjectBuilder builder) {
        throwIfNull(builder, "builder");
        return addFluently(index, builder.build());
    }

    @Override
    public JsonArrayBuilder add(int index, JsonArrayBuilder builder) {
        throwIfNull(builder, "builder");
        return addFluently(index, builder.build());
    }

    @Override
    public JsonArrayBuilder set(int index, JsonValue value) {
        throwIfNull(value, "value");
        return setFluently(index, value);
    }

    @Override
    public JsonArrayBuilder set(int index, String value) {
        throwIfNull(value, "value");
        return setFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder set(int index, BigDecimal value) {
        throwIfNull(value, "value");
        return setFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder set(int index, BigInteger value) {
        throwIfNull(value, "value");
        return setFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder set(int index, int value) {
        return setFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder set(int index, long value) {
        return setFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder set(int index, double value) {
        return setFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder set(int index, boolean value) {
        return setFluently(index, toJson(value));
    }

    @Override
    public JsonArrayBuilder setNull(int index) {
        return setFluently(index, JsonValue.NULL);
    }

    @Override
    public JsonArrayBuilder set(int index, JsonObjectBuilder builder) {
        throwIfNull(builder, "builder");
        return setFluently(index, builder.build());
    }

    @Override
    public JsonArrayBuilder set(int index, JsonArrayBuilder builder) {
        throwIfNull(builder, "builder");
        return setFluently(index, builder.build());
    }

    @Override
    public JsonArrayBuilder remove(int index) {
        values.remove(index);
        return this;
    }

    @Override
    public JsonArray build() {
        return new JsonArrayJackson(values);
    }

    private JsonArrayBuilder addFluently(JsonValue value) {
        values.add(value);
        return this;
    }

    private JsonArrayBuilder addFluently(int index, JsonValue value) {
        values.add(index, value);
        return this;
    }

    private JsonArrayBuilder setFluently(int index, JsonValue value) {
        values.set(index, value);
        return this;
    }
}
