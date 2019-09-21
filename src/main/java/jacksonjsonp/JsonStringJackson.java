package jacksonjsonp;

import com.fasterxml.jackson.databind.node.TextNode;

import javax.json.JsonString;

public class JsonStringJackson implements JsonString {
    private TextNode node;

    JsonStringJackson(String value) {
        node = new TextNode(value);
    }

    JsonStringJackson(TextNode node) {
        this.node = node;
    }

    @Override
    public String getString() {
        return node.textValue();
    }

    @Override
    public CharSequence getChars() {
        return node.textValue();
    }

    @Override
    public ValueType getValueType() {
        return ValueType.STRING;
    }

    @Override
    public String toString() {
        return node.toString();
    }

    @Override
    public int hashCode() {
        return getString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof JsonString) {
            return this.getString().equals(((JsonString) o).getString());
        } else {
            return false;
        }
    }
}
