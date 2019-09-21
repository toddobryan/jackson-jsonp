package jacksonjsonp;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class JsonBuilderFactoryJackson extends AbstractFactory implements JsonBuilderFactory {
    JsonBuilderFactoryJackson(Map<String, ?> config) {
        super(config);
    }

    @Override
    public Set<String> getSupportedProperties() {
        return Collections.emptySet();
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
    public JsonObjectBuilder createObjectBuilder(Map<String, Object> object) {
        return new JsonObjectBuilderJackson(object);
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
}
