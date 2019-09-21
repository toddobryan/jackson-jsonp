package jacksonjsonp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;

abstract class JsonValueJackson implements JsonValue {
    static JsonValue toJson(boolean b) {
        return b ? JsonValue.TRUE : JsonValue.FALSE;
    }

    static JsonValue toJson(int value) {
        return new JsonNumberJackson(value);
    }

    static JsonValue toJson(long value) {
        return new JsonNumberJackson(value);
    }

    static JsonValue toJson(double value) {
        return new JsonNumberJackson(value);
    }

    static JsonValue toJson(JsonNode node) {
        if (node == null) {
            throw new IllegalArgumentException("null is not a legal JsonValue");
        } else if (node instanceof NullNode) {
            return JsonValue.NULL;
        } else if (node instanceof BooleanNode) {
            return node.asBoolean() ? JsonValue.TRUE : JsonValue.FALSE;
        } else if (node instanceof NumericNode) {
            return new JsonNumberJackson((NumericNode) node);
        } else if (node instanceof TextNode) {
            return new JsonStringJackson((TextNode) node);
        } else if (node instanceof ArrayNode) {
            return new JsonArrayJackson((ArrayNode) node);
        } else if (node instanceof ObjectNode) {
            return new JsonObjectJackson((ObjectNode) node);
        } else {
            throw new IllegalArgumentException("Unrecognized JsonNode: "
                    + node.getClass().getCanonicalName());
        }
    }

    static JsonValue toJson(Object o) {
        if (o == null) {
            return JsonValue.NULL;
        } else if (o instanceof JsonValue) {
            return (JsonValue) o;
        } else if (o instanceof JsonArrayBuilder) {
            return ((JsonArrayBuilder) o).build();
        } else if (o instanceof Boolean) {
            return toJson(((Boolean) o).booleanValue());
        } else if (o instanceof Integer) {
            return toJson(((Integer) o).intValue());
        } else if (o instanceof Long) {
            return toJson(((Long) o).longValue());
        } else if (o instanceof Double) {
            return toJson(((Double) o).doubleValue());
        } else if (o instanceof BigInteger) {
            return new JsonNumberJackson((BigInteger) o);
        } else if (o instanceof BigDecimal) {
            return new JsonNumberJackson((BigDecimal) o);
        } else if (o instanceof String) {
            return new JsonStringJackson((String) o);
        } else {
            throw new IllegalArgumentException("Unsupported type: "
                    + o.getClass().getCanonicalName());
        }
    }
}
