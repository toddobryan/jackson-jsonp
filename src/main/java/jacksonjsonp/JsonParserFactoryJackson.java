package jacksonjsonp;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;

public class JsonParserFactoryJackson extends AbstractFactory implements JsonParserFactory {
    JsonParserFactoryJackson(Map<String, ?> config) {
        super(config);
    }

    @Override
    public Set<String> getSupportedProperties() {
        return Collections.emptySet();
    }

    @Override
    public JsonParser createParser(Reader reader) {
        return new JsonParserJackson(reader);
    }

    @Override
    public JsonParser createParser(InputStream in) {
        return new JsonParserJackson(in);
    }

    @Override
    public JsonParser createParser(InputStream in, Charset charset) {
        return new JsonParserJackson(new InputStreamReader(in, charset));
    }

    @Override
    public JsonParser createParser(JsonObject obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonParser createParser(JsonArray array) {
        throw new UnsupportedOperationException();
    }
}
