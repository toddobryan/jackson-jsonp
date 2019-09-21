package jacksonjsonp;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.stream.JsonLocation;
import javax.json.stream.JsonParsingException;

import static com.fasterxml.jackson.core.JsonToken.END_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.END_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;
import static com.fasterxml.jackson.core.JsonToken.NOT_AVAILABLE;
import static com.fasterxml.jackson.core.JsonToken.START_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.VALUE_FALSE;
import static com.fasterxml.jackson.core.JsonToken.VALUE_NULL;
import static com.fasterxml.jackson.core.JsonToken.VALUE_NUMBER_FLOAT;
import static com.fasterxml.jackson.core.JsonToken.VALUE_NUMBER_INT;
import static com.fasterxml.jackson.core.JsonToken.VALUE_STRING;
import static com.fasterxml.jackson.core.JsonToken.VALUE_TRUE;
import static java.util.Spliterator.ORDERED;

public class JsonParserJackson implements javax.json.stream.JsonParser {
    private JsonParser jacksonParser;
    //private JsonToken prev = null;
    //private com.fasterxml.jackson.core.JsonLocation prevLoc = null;
    private JsonToken next = null;
    private boolean hasNextCalled = false;

    JsonParserJackson(Reader reader) {
        try {
            this.jacksonParser = new JsonFactory().createParser(reader);
        } catch (IOException e) {
            throw new JsonException("Error creating parser", e);
        }
    }

    JsonParserJackson(InputStream in) {
        try {
            this.jacksonParser = new JsonFactory().createParser(in);
        } catch (IOException e) {
            throw new JsonException("Error creating parser", e);
        }
    }

    @Override
    public boolean hasNext() {
        if (!hasNextCalled) {
            jacksonNext();
            hasNextCalled = true;
        }
        return next != null && next != NOT_AVAILABLE;
    }

    @Override
    public Event next() {
        if (!hasNextCalled) {
            jacksonNext();
        }
        hasNextCalled = false;

        if (next == null) {
            throw new NoSuchElementException("reached end of JSON stream");
        } else if (next == NOT_AVAILABLE) {
            throw new NoSuchElementException("stream not currently available");
        } else if (next == START_ARRAY) {
            return Event.START_ARRAY;
        } else if (next == END_ARRAY) {
            return Event.END_ARRAY;
        } else if (next == START_OBJECT) {
            return Event.START_OBJECT;
        } else if (next == END_OBJECT) {
            return Event.END_OBJECT;
        } else if (next == FIELD_NAME) {
            return Event.KEY_NAME;
        } else if (next == VALUE_NULL) {
            return Event.VALUE_NULL;
        } else if (next == VALUE_TRUE) {
            return Event.VALUE_TRUE;
        } else if (next == VALUE_FALSE) {
            return Event.VALUE_FALSE;
        } else if (next == VALUE_STRING) {
            return Event.VALUE_STRING;
        } else if (next == VALUE_NUMBER_INT
                || next == VALUE_NUMBER_FLOAT) {
            return Event.VALUE_NUMBER;
        } else {
            throw new IllegalArgumentException("Unrecognized token: " + next);
        }
    }

    private void jacksonNext() {
        try {
            //prev = next;
            //prevLoc = jacksonParser.getTokenLocation();
            next = jacksonParser.nextToken();
        } catch (JsonParseException e) {
            throw new JsonParsingException(e.getMessage(), e, getLocation());
        } catch (IOException e) {
            throw new JsonException(e.getMessage(), e);
        }
    }

    @Override
    public String getString() {
        throwUnless("getString()", "name, string, or number",
                FIELD_NAME, VALUE_STRING, VALUE_NUMBER_FLOAT, VALUE_NUMBER_INT);
        try {
            if (next == FIELD_NAME) {
                return jacksonParser.getCurrentName();
            } else {
                return jacksonParser.getValueAsString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isIntegralNumber() {
        throwUnless("isIntegralNumber()", "number", VALUE_NUMBER_FLOAT, VALUE_NUMBER_INT);
        return next == VALUE_NUMBER_INT;
    }

    @Override
    public int getInt() {
        throwUnless("getInt()", "number", VALUE_NUMBER_FLOAT, VALUE_NUMBER_INT);
        return new BigDecimal(getString()).intValue();
    }

    @Override
    public long getLong() {
        throwUnless("getLong()", "number", VALUE_NUMBER_FLOAT, VALUE_NUMBER_INT);
        return new BigDecimal(getString()).longValue();
    }

    @Override
    public BigDecimal getBigDecimal() {
        throwUnless("getLong()", "number", VALUE_NUMBER_FLOAT, VALUE_NUMBER_INT);
        return new BigDecimal(getString());
    }

    @Override
    public JsonLocation getLocation() {
        System.out.println("tokenLoc: " + jacksonParser.getTokenLocation());
        System.out.println("next: " + next);
        System.out.println("prev: " + prev + ", asString: " + prev.asCharArray());
        System.out.println("prevLoc: " + prevLoc);
        if (next == null) {
            return new JsonLocationJackson(jacksonParser.getTokenLocation());
        }
        switch (prev) {
            case VALUE_NULL:
            case VALUE_TRUE:
                return new JsonLocationJackson(prevLoc, 4);
            case VALUE_FALSE:
                return new JsonLocationJackson(prevLoc, 5);
            case VALUE_NUMBER_INT:
            case VALUE_NUMBER_FLOAT:
            case VALUE_STRING:
            case FIELD_NAME:
                return new JsonLocationJackson(prevLoc, prev.asString().length());
            case START_ARRAY:
            case START_OBJECT:
            case END_ARRAY:
            case END_OBJECT:
                return new JsonLocationJackson(prevLoc, 1);
            default:
                return new JsonLocationJackson(jacksonParser.getCurrentLocation());
        }
    }

    @Override
    public JsonObject getObject() {
        if (next == START_OBJECT) {
            JsonObjectBuilder builder = new JsonObjectBuilderJackson();
            while (hasNext()) {
                String key;
                JsonValue value;
                Event event = next();
                if (event == Event.END_OBJECT) {
                    return builder.build();
                }
                if (event != Event.KEY_NAME) {
                    throw new JsonException("Expected key at location: " + getLocation());
                } else {
                    key = getString();
                }
                next();
                value = getValue();
                builder.add(key, value);
            }
        }
        throw new IllegalStateException("Can't call getObject() except at start of object.");
    }

    @Override
    public JsonArray getArray() {
        if (next == START_ARRAY) {
            JsonArrayBuilder builder = new JsonArrayBuilderJackson();
            while (hasNext()) {
                Event event = next();
                if (event == Event.END_ARRAY) {
                    return builder.build();
                }
                builder.add(getValue());
            }
            return builder.build();
        }
        throw new IllegalStateException("Can't call getArray() except at start of array.");
    }

    @Override
    public Stream<JsonValue> getArrayStream() {
        if (next == START_ARRAY) {
            Spliterator<JsonValue> split =
                    new Spliterators.AbstractSpliterator<JsonValue>(
                            Long.MAX_VALUE, Spliterator.ORDERED) {
                        @Override
                        public boolean tryAdvance(Consumer<? super JsonValue> action) {
                            if (hasNext()) {
                                Event event = next();
                                if (event == Event.END_ARRAY) {
                                    return false;
                                } else {
                                    action.accept(getValue());
                                    return true;
                                }
                            }
                            throw new IllegalStateException(
                                    "Reached the end of the stream before the end of the array.");
                        }
                    };
            return StreamSupport.stream(split, false);
        }
        throw new IllegalStateException("Can't stream array except at start of array.");
    }

    @Override
    public Stream<Map.Entry<String, JsonValue>> getObjectStream() {
        if (next == START_OBJECT) {
            Spliterator<Map.Entry<String, JsonValue>> split =
                    new Spliterators.AbstractSpliterator<Map.Entry<String, JsonValue>>(
                            Long.MAX_VALUE, ORDERED) {
                        @Override
                        public boolean tryAdvance(Consumer<? super Map.Entry<String, JsonValue>> action) {
                            if (hasNext()) {
                                Event event = next();
                                if (event == Event.END_OBJECT) {
                                    return false;
                                } else {
                                    if (event != Event.KEY_NAME) {
                                        throw new JsonException("Expected key name, found: " + event);
                                    }
                                    String key = getString();
                                    next();
                                    JsonValue value = getValue();
                                    action.accept(new AbstractMap.SimpleEntry<String, JsonValue>(
                                            key, value));
                                    return true;
                                }
                            }
                            throw new JsonException("Got to end of stream before end of object.");
                        }
                    };
            return StreamSupport.stream(split, false);
        }
        throw new IllegalStateException("Can't stream object except at start of object.");
    }

    /*@Override
    public Stream<JsonValue> getValueStream() {
        return null;
    }*/


    @Override
    public JsonValue getValue() {
        if (next == null) {
            throw new IllegalStateException("Can't getValue() after end of stream.");
        }
        switch (next) {
            case VALUE_NULL:
                return JsonValue.NULL;
            case VALUE_TRUE:
                return JsonValue.TRUE;
            case VALUE_FALSE:
                return JsonValue.FALSE;
            case VALUE_NUMBER_INT:
                return JsonNumberJackson.fromString(getString(), true);
            case VALUE_NUMBER_FLOAT:
                return JsonNumberJackson.fromString(getString(), false);
            case VALUE_STRING:
                return new JsonStringJackson(getString());
            case START_ARRAY:
                return getArray();
            case START_OBJECT:
                return getObject();
            default:
                throw new IllegalStateException("Can't get value at:"
                        + jacksonParser.getCurrentLocation());
        }
    }


    /*Override
    public void skipArray() {

    }*/

    /*@Override
    public void skipObject() {

    }*/

    @Override
    public void close() {
        try {
            jacksonParser.close();
        } catch (IOException e) {
            throw new JsonException("Error closing parser", e);
        }
    }

    private void throwUnless(String methodName, String expected, JsonToken... tokens) {
        if (!Arrays.asList(tokens).contains(next)) {
            throw new IllegalStateException(
                    "Can't call " + methodName + " unless token is " + expected);
        }
    }
}
