package jacksonjsonp;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonMergePatch;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonPatch;
import javax.json.JsonPatchBuilder;
import javax.json.JsonPointer;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.spi.JsonProvider;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;

public class JsonProviderJackson extends JsonProvider {
    @Override
    public JsonParser createParser(Reader reader) {
        return new JsonParserJackson(reader);
    }

    @Override
    public JsonParser createParser(InputStream in) {
        return new JsonParserJackson(in);
    }

    @Override
    public JsonParserFactory createParserFactory(Map<String, ?> config) {
        return new JsonParserFactoryJackson(config);
    }

    @Override
    public JsonGenerator createGenerator(Writer writer) {
        return null;
    }

    @Override
    public JsonGenerator createGenerator(OutputStream out) {
        return null;
    }

    @Override
    public JsonGeneratorFactory createGeneratorFactory(Map<String, ?> config) {
        return null;
    }

    @Override
    public JsonReader createReader(Reader reader) {
        return null;
    }

    @Override
    public JsonReader createReader(InputStream in) {
        return null;
    }

    @Override
    public JsonWriter createWriter(Writer writer) {
        return null;
    }

    @Override
    public JsonWriter createWriter(OutputStream out) {
        return null;
    }

    @Override
    public JsonWriterFactory createWriterFactory(Map<String, ?> config) {
        return null;
    }

    @Override
    public JsonReaderFactory createReaderFactory(Map<String, ?> config) {
        return null;
    }

    @Override
    public JsonObjectBuilder createObjectBuilder() {
        return new JsonObjectBuilderJackson();
    }

    @Override
    public JsonObjectBuilder createObjectBuilder(JsonObject object) {
        return new JsonObjectBuilderJackson(object);
    }

    @Override
    public JsonObjectBuilder createObjectBuilder(Map<String, Object> map) {
        return new JsonObjectBuilderJackson(map);
    }

    @Override
    public JsonArrayBuilder createArrayBuilder() {
        return new JsonArrayBuilderJackson();
    }

    @Override
    public JsonArrayBuilder createArrayBuilder(JsonArray array) {
        return new JsonArrayBuilderJackson(array);
    }

    @Override
    public JsonArrayBuilder createArrayBuilder(Collection<?> collection) {
        return new JsonArrayBuilderJackson(collection);
    }

    @Override
    public JsonPointer createPointer(String jsonPointer) {
        return null;
    }

    public JsonPatchBuilder createPatchBuilder() {
        return null;
    }

    public JsonPatchBuilder createPatchBuilder(JsonArray array) {
        return null;
    }

    public JsonPatch createPatch(JsonArray array) {
        return null;
    }

    public JsonPatch createDiff(JsonStructure source, JsonStructure target) {
        return null;
    }

    public JsonMergePatch createMergePatch(JsonValue patch) {
        return null;
    }

    public JsonMergePatch createMergeDiff(JsonValue source, JsonValue target) {
        return null;
    }

    @Override
    public JsonBuilderFactory createBuilderFactory(Map<String, ?> config) {
        return new JsonBuilderFactoryJackson(config);
    }

    public JsonString createValue(String value) {
        return new JsonStringJackson(value);
    }

    public JsonNumber createValue(int value) {
        return new JsonNumberJackson(value);
    }

    public JsonNumber createValue(long value) {
        return new JsonNumberJackson(value);
    }

    public JsonNumber createValue(double value) {
        return new JsonNumberJackson(value);
    }

    public JsonNumber createValue(BigDecimal value) {
        return new JsonNumberJackson(value);
    }

    public JsonNumber createValue(BigInteger value) {
        return new JsonNumberJackson(value);
    }
}
