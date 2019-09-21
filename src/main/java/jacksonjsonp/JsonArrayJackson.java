package jacksonjsonp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

public class JsonArrayJackson implements JsonArray {
    private List<JsonValue> values;

    JsonArrayJackson(ArrayNode array) {
        values = new ArrayList<>();
        for (JsonNode node : array) {
            values.add(JsonValueJackson.toJson(node));
        }
    }

    JsonArrayJackson(List<JsonValue> values) {
        this.values = values;
    }

    @Override
    public JsonObject getJsonObject(int index) {
        return new JsonObjectJackson((ObjectNode) values.get(index));
    }

    @Override
    public JsonArray getJsonArray(int index) {
        return new JsonArrayJackson((ArrayNode) values.get(index));
    }

    @Override
    public JsonNumber getJsonNumber(int index) {
        return new JsonNumberJackson((NumericNode) values.get(index));
    }

    @Override
    public JsonString getJsonString(int index) {
        return new JsonStringJackson(((TextNode) values.get(index)).textValue());
    }

    @Override
    public <T extends JsonValue> List<T> getValuesAs(Class<T> clazz) {
        return values.stream().map(clazz::cast).collect(Collectors.toList());
    }

    @Override
    public <T, K extends JsonValue> List<T> getValuesAs(Function<K, T> func) {
        return null;
    }

    @Override
    public String getString(int index) {
        return getJsonString(index).getString();
    }

    @Override
    public String getString(int index, String defaultValue) {
        return get(index) instanceof JsonString ? getString(index) : defaultValue;
    }

    @Override
    public int getInt(int index) {
        return getJsonNumber(index).intValue();
    }

    @Override
    public int getInt(int index, int defaultValue) {
        return get(index) instanceof JsonNumber ? getJsonNumber(index).intValue() : defaultValue;
    }

    private Boolean booleanOrNull(int index) {
        JsonValue value = get(index);
        if (value == JsonValue.TRUE) {
            return true;
        } else if (value == JsonValue.FALSE) {
            return false;
        } else {
            return null;
        }

    }

    @Override
    public boolean getBoolean(int index) {
        Boolean value = booleanOrNull(index);
        if (value == null) {
            throw new ClassCastException("Expected JSON boolean, given: " + get(index));
        } else {
            return value;
        }
    }

    @Override
    public boolean getBoolean(int index, boolean defaultValue) {
        Boolean value = booleanOrNull(index);
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

    @Override
    public boolean isNull(int index) {
        return get(index) == JsonValue.NULL;
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return values.contains(o);
    }

    @Override
    public Iterator<JsonValue> iterator() {
        return new UnmodifiableIterator<>(values.iterator());
    }

    @Override
    public void forEach(Consumer<? super JsonValue> action) {
        values.forEach(action);
    }

    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return values.toArray(a);
    }

    @Override
    public boolean add(JsonValue jsonValue) {
        throw new UnsupportedOperationException("JsonArray is immutable.");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("JsonArray is immutable.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return values.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends JsonValue> c) {
        throw new UnsupportedOperationException("JsonArray is immutable.");
    }

    @Override
    public boolean addAll(int index, Collection<? extends JsonValue> c) {
        throw new UnsupportedOperationException("JsonArray is immutable.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("JsonArray is immutable.");
    }

    @Override
    public boolean removeIf(Predicate<? super JsonValue> filter) {
        throw new UnsupportedOperationException("JsonArray is immutable.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("JsonArray is immutable.");
    }

    @Override
    public void replaceAll(UnaryOperator<JsonValue> operator) {
        throw new UnsupportedOperationException("JsonArray is immutable.");
    }

    @Override
    public void sort(Comparator<? super JsonValue> c) {
        throw new UnsupportedOperationException("JsonArray is immutable.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("JsonArray is immutable.");
    }

    @Override
    public JsonValue get(int index) {
        return values.get(index);
    }

    @Override
    public JsonValue set(int index, JsonValue element) {
        throw new UnsupportedOperationException("JsonArray is immutable.");
    }

    @Override
    public void add(int index, JsonValue element) {
        throw new UnsupportedOperationException("JsonArray is immutable.");

    }

    @Override
    public JsonValue remove(int index) {
        throw new UnsupportedOperationException("JsonArray is immutable.");
    }

    @Override
    public int indexOf(Object o) {
        return values.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return values.lastIndexOf(o);
    }

    @Override
    public ListIterator<JsonValue> listIterator() {
        return new UnmodifiableListIterator<>(values.listIterator());
    }

    @Override
    public ListIterator<JsonValue> listIterator(int index) {
        return new UnmodifiableListIterator<>(values.listIterator(index));
    }

    @Override
    public List<JsonValue> subList(int fromIndex, int toIndex) {
        return new ImmutableList<>(values.subList(fromIndex, toIndex));
    }

    @Override
    public Spliterator<JsonValue> spliterator() {
        return values.spliterator();
    }

    @Override
    public Stream<JsonValue> stream() {
        return values.stream();
    }

    @Override
    public Stream<JsonValue> parallelStream() {
        return values.parallelStream();
    }

    @Override
    public ValueType getValueType() {
        return ValueType.ARRAY;
    }

    @Override
    public JsonArray asJsonArray() {
        return this;
    }

    @Override
    public String toString() {
        return "[" + String.join(",",
                values.stream().map(v -> v.toString()).collect(Collectors.toList()))
                + "]";
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof JsonArray) {
            return Arrays.deepEquals(this.toArray(), ((JsonArray) o).toArray());
        } else {
            return false;
        }
    }
}
