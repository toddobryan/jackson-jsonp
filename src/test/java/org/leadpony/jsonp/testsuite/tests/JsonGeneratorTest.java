/*
 * Copyright 2019 the JSON-P Test Suite Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.leadpony.jsonp.testsuite.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.StringWriter;
import java.util.function.Consumer;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test type to test {@link JsonGenerator}.
 *
 * @author leadpony
 */
public class JsonGeneratorTest {

    private static JsonGeneratorFactory factory;

    @BeforeAll
    public static void setUpOnce() {
        factory = Json.createGeneratorFactory(null);
    }

    /**
     * Test cases for boolean values.
     *
     * @author leadpony
     */
    enum BooleanTestCase {
        TRUE(true, "true"),
        FALSE(false, "false");

        final boolean value;
        final String expected;

        BooleanTestCase(boolean value, String expected) {
            this.value = value;
            this.expected = expected;
        }
    }

    @ParameterizedTest
    @EnumSource(BooleanTestCase.class)
    public void writeShouldWriteBoolean(BooleanTestCase test) {

        String actual = generate(g -> {
            g.write(test.value);
        });

        assertThat(actual).isEqualTo(test.expected);
    }

    @Test
    public void writeNullShouldWriteNull() {

        String actual = generate(g -> {
            g.writeNull();
        });

        assertThat(actual).isEqualTo("null");
    }

    /**
     * Test cases for string value.
     *
     * @author leadpony
     */
    enum StringTestCase {
        EMPTY_STRING("", "\"\""),
        BLANK_STRING(" ", "\" \""),
        SINGLE_WORD("hello", "\"hello\""),
        NULL("null", "\"null\""),
        INTEGER("42", "\"42\""),
        NUMBER("3.14", "\"3.14\""),
        CONTAINING_SPACE("hello world", "\"hello world\"");

        final String value;
        final String expected;

        StringTestCase(String value, String expected) {
            this.value = value;
            this.expected = expected;
        }
    }

    @ParameterizedTest
    @EnumSource(StringTestCase.class)
    public void writeShouldWriteString(StringTestCase test) {

        String actual = generate(g -> {
            g.write(test.value);
        });

        assertThat(actual).isEqualTo(test.expected);
    }

    /**
     * Test cases for integer value.
     *
     * @author leadpony
     */
    enum IntTestCase {
        ZERO(0, "0"),
        ONE(1, "1"),
        MINUS_ONE(-1, "-1"),
        TEN(10, "10"),
        MINUS_TEN(-10, "-10"),
        HUNDRED(100, "100"),
        MINUS_HUNDRED(-100, "-100"),
        THOUSAND(1000, "1000"),
        MINUS_THOUSAND(-1000, "-1000"),
        HOURS_PER_DAY(24, "24"),
        DAYS_PER_YEAR(365, "365"),
        MINUS_HOURS_PER_DAY(-24, "-24"),
        MINUS_DAYS_PER_YEAR(-365, "-365"),
        MAX_INTEGER(Integer.MAX_VALUE, "2147483647"),
        MIN_INTEGER(Integer.MIN_VALUE, "-2147483648");

        final int value;
        final String expected;

        IntTestCase(int value, String expected) {
            this.value = value;
            this.expected = expected;
        }
    }

    @ParameterizedTest
    @EnumSource(IntTestCase.class)
    public void writeShouldWriteInteger(IntTestCase test) {

        String actual = generate(g -> {
            g.write(test.value);
        });

        assertThat(actual).isEqualTo(test.expected);
    }

    /**
     * Test cases for long value.
     *
     * @author leadpony
     */
    enum LongTestCase {
        ZERO(0L, "0"),
        ONE(1L, "1"),
        MINUS_ONE(-1L, "-1"),
        TEN(10L, "10"),
        MINUS_TEN(-10L, "-10"),
        HUNDRED(100L, "100"),
        MINUS_HUNDRED(-100L, "-100"),
        THOUSAND(1000L, "1000"),
        MINUS_THOUSAND(-1000L, "-1000"),
        HOURS_PER_DAY(24L, "24"),
        DAYS_PER_YEAR(365L, "365"),
        MINUS_HOURS_PER_DAY(-24L, "-24"),
        MINUS_DAYS_PER_YEAR(-365L, "-365"),
        MAX_INTEGER(Long.MAX_VALUE, "9223372036854775807"),
        MIN_INTEGER(Long.MIN_VALUE, "-9223372036854775808");

        final long value;
        final String expected;

        LongTestCase(long value, String expected) {
            this.value = value;
            this.expected = expected;
        }
    }

    @ParameterizedTest
    @EnumSource(LongTestCase.class)
    public void writeShouldWriteLong(LongTestCase test) {

        String actual = generate(g -> {
            g.write(test.value);
        });

        assertThat(actual).isEqualTo(test.expected);
    }

    /**
     * Test cases for double value.
     *
     * @author leadpony
     */
    enum DoubleTestCase {
        ZERO(0.0, "0.0"),
        E(Math.E, "2.718281828459045"),
        PI(Math.PI, "3.141592653589793"),
        MAX_VALUE(Double.MAX_VALUE, "1.7976931348623157E308"),
        MIN_VALUE(Double.MIN_VALUE, "4.9E-324");

        final double value;
        final String expected;

        DoubleTestCase(double value, String expected) {
            this.value = value;
            this.expected = expected;
        }
    }

    @ParameterizedTest
    @EnumSource(DoubleTestCase.class)
    public void writeShouldWriteDouble(DoubleTestCase test) {

        String actual = generate(g -> {
            g.write(test.value);
        });

        assertThat(actual).isEqualTo(test.expected);
    }

    /**
     * @author leadpony
     */
    enum NamedDoubleTestCase {
        ZERO("abc", 0.0, "{\"abc\":0.0}"),
        E("", Math.E, "{\"\":2.718281828459045}"),
        PI("a\"bc", Math.PI, "{\"a\\\"bc\":3.141592653589793}");

        final String name;
        final double value;
        final String expected;

        NamedDoubleTestCase(String name, double value, String expected) {
            this.name = name;
            this.value = value;
            this.expected = expected;
        }
    }

    @ParameterizedTest
    @EnumSource(NamedDoubleTestCase.class)
    public void writeShouldWriteDouble(NamedDoubleTestCase test) {

        String actual = generate(g -> {
            g.writeStartObject();
            g.write(test.name, test.value);
            g.writeEnd();
        });

        assertThat(actual).isEqualTo(test.expected);
    }

    @ParameterizedTest
    @EnumSource(JsonValueTestCase.class)
    public void writeShouldWriteJsonValue(JsonValueTestCase test) {

        String actual = generate(g -> {
            g.write(test.getJsonValue());
        });

        assertThat(actual).isEqualTo(test.getString());
    }

    /**
     * @author leadpony
     */
    enum GeneratorTestCase {
        EMPTY_ARRAY(
            g -> {
                g.writeStartArray();
                g.writeEnd();
            },
            "[]"
            ),

        SINGLE_ITEM_ARRAY(
            g -> {
                g.writeStartArray();
                g.write(true);
                g.writeEnd();
            },
            "[true]"
            ),

        MULTIPLE_ITEMS_ARRAY(
            g -> {
                g.writeStartArray();
                g.write(true);
                g.write(false);
                g.writeEnd();
            },
            "[true,false]"
            ),

        ARRAY_OF_ARRAY(
            g -> {
                g.writeStartArray();
                g.writeStartArray();
                g.write(1);
                g.write(2);
                g.writeEnd();
                g.writeStartArray();
                g.write(3);
                g.write(4);
                g.writeEnd();
                g.writeEnd();
            },
            "[[1,2],[3,4]]"
            ),

        ARRAY_OF_OBJECT(
            g -> {
                g.writeStartArray();
                g.writeStartObject();
                g.write("a", 1);
                g.write("b", 2);
                g.writeEnd();
                g.writeStartObject();
                g.write("c", 3);
                g.write("d", 4);
                g.writeEnd();
                g.writeEnd();
            },
            "[{\"a\":1,\"b\":2},{\"c\":3,\"d\":4}]"
            ),

        EMPTY_OBJECT(
            g -> {
                g.writeStartObject();
                g.writeEnd();
            },
            "{}"
            ),


        SINGLE_PROPERTY_OBJECT(
            g -> {
                g.writeStartObject();
                g.write("a", 365);
                g.writeEnd();
            },
            "{\"a\":365}"
            ),

        MULTIPLE_PROPERTIES_OBJECT(
            g -> {
                g.writeStartObject();
                g.write("a", 365);
                g.write("b", "hello");
                g.writeEnd();
            },
            "{\"a\":365,\"b\":\"hello\"}"
            ),

        OBJECT_OF_ARRAY(
            g -> {
                g.writeStartObject();
                g.writeStartArray("a");
                g.write(1);
                g.write(2);
                g.writeEnd();
                g.writeStartArray("b");
                g.write(3);
                g.write(4);
                g.writeEnd();
                g.writeEnd();
            },
            "{\"a\":[1,2],\"b\":[3,4]}"
            ),

        OBJECT_OF_OBJECT(
            g -> {
                g.writeStartObject();
                g.writeStartObject("a");
                g.write("a1", 1);
                g.write("a2", 2);
                g.writeEnd();
                g.writeStartObject("b");
                g.write("b1", 3);
                g.write("b2", 4);
                g.writeEnd();
                g.writeEnd();
            },
            "{\"a\":{\"a1\":1,\"a2\":2},\"b\":{\"b1\":3,\"b2\":4}}"
            ),

        KEY_VALUE_AFTER_ARRAY(
            g -> {
                g.writeStartObject();
                g.writeKey("a");
                g.writeStartArray();
                g.write(1);
                g.write(2);
                g.writeEnd();
                g.write("c", 3);
                g.writeEnd();
            },
            "{\"a\":[1,2],\"c\":3}"
            ),

        KEY_VALUE_AFTER_OBJECT(
            g -> {
                g.writeStartObject();
                g.writeKey("a");
                g.writeStartObject();
                g.write("b", 1);
                g.writeEnd();
                g.write("c", 2);
                g.writeEnd();
            },
            "{\"a\":{\"b\":1},\"c\":2}"
            );

        final Consumer<JsonGenerator> consumer;
        final String expected;

        GeneratorTestCase(Consumer<JsonGenerator> consumer, String expected) {
            this.consumer = consumer;
            this.expected = expected;
        }
    }

    @ParameterizedTest
    @EnumSource(GeneratorTestCase.class)
    public void writeShouldGenerateJson(GeneratorTestCase test) {
        StringWriter writer = new StringWriter();
        try (JsonGenerator g = factory.createGenerator(writer)) {
            test.consumer.accept(g);
            g.flush();
        }

        assertThat(writer.toString()).isEqualTo(test.expected);
    }

    private static String generate(Consumer<JsonGenerator> consumer) {
        StringWriter writer = new StringWriter();
        try (JsonGenerator g = factory.createGenerator(writer)) {
            consumer.accept(g);
        }
        return writer.toString();
    }
}
