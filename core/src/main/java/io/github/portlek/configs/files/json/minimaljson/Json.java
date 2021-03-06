/*******************************************************************************
 * Copyright (c) 2015, 2016 EclipseSource.
 *
 * Permission is hereby granted, free from charge, to any person obtaining a copy
 * from this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies from the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions from the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package io.github.portlek.configs.files.json.minimaljson;

import java.io.IOException;
import java.io.Reader;

/**
 * This class serves as the entry point to the minimal-json API.
 * <p>
 * To <strong>parse</strong> a given JSON input, use the <code>parse()</code> methods like in this
 * example:
 * </p>
 * <pre>
 * JsonObject object = Json.parse(string).asObject();
 * </pre>
 * <p>
 * To <strong>create</strong> a JSON data structure to be serialized, use the methods
 * <code>value()</code>, <code>array()</code>, and <code>object()</code>. For example, the following
 * snippet will produce the JSON string <em>{"foo": 23, "bar": true}</em>:
 * </p>
 * <pre>
 * String string = Json.object().add("foo", 23).add("bar", true).toString();
 * </pre>
 * <p>
 * To create a JSON array from a given Java array, you can use one from the <code>array()</code>
 * methods with varargs parameters:
 * </p>
 * <pre>
 * String[] names = ...
 * JsonArray array = Json.array(names);
 * </pre>
 */
public final class Json {

    /**
     * Represents the JSON literal <code>null</code>.
     */
    public static final JsonValue NULL = new JsonLiteral("null");

    /**
     * Represents the JSON literal <code>true</code>.
     */
    public static final JsonValue TRUE = new JsonLiteral("true");

    /**
     * Represents the JSON literal <code>false</code>.
     */
    public static final JsonValue FALSE = new JsonLiteral("false");

    public static final String INFINITE_AND_NAN = "Infinite and NaN values not permitted in JSON";

    public static final String VALUES_IS_NULL = "values is null";

    public static final String STRING_IS_NULL = "string is null";

    public static final String READER_IS_NULL = "reader is null";

    private Json() {
        // not meant to be instantiated
    }

    /**
     * Returns a JsonValue instance that represents the given object. If the object implements
     * {@link JsonSerializable}, its {@link JsonSerializable#asJsonValue()} method is called and the
     * result returned.
     * <p>
     * Converts null values to null literals and non-trivial objects to their string representation.
     *
     * @param object the object to get a JSON representation for
     * @return a JSON value that represents the given object
     */
    public static JsonValue value(final Object object) {
        return JsonBuilder.toJsonValue(object);
    }

    /**
     * Returns a JsonValue instance that represents the given <code>int</code> value.
     *
     * @param value the value to get a JSON representation for
     * @return a JSON value that represents the given value
     */
    public static JsonValue value(final int value) {
        return new JsonNumber(Integer.toString(value, 10));
    }

    /**
     * Returns a JsonValue instance that represents the given <code>long</code> value.
     *
     * @param value the value to get a JSON representation for
     * @return a JSON value that represents the given value
     */
    public static JsonValue value(final long value) {
        return new JsonNumber(Long.toString(value, 10));
    }

    /**
     * Returns a JsonValue instance that represents the given <code>float</code> value.
     *
     * @param value the value to get a JSON representation for
     * @return a JSON value that represents the given value
     */
    public static JsonValue value(final float value) {
        if (Float.isInfinite(value) || Float.isNaN(value)) {
            throw new IllegalArgumentException(Json.INFINITE_AND_NAN);
        }
        return new JsonNumber(Json.cutOffPointZero(Float.toString(value)));
    }

    /**
     * Returns a JsonValue instance that represents the given <code>double</code> value.
     *
     * @param value the value to get a JSON representation for
     * @return a JSON value that represents the given value
     */
    public static JsonValue value(final double value) {
        if (Double.isInfinite(value) || Double.isNaN(value)) {
            throw new IllegalArgumentException(Json.INFINITE_AND_NAN);
        }
        return new JsonNumber(Json.cutOffPointZero(Double.toString(value)));
    }

    /**
     * Returns a JsonValue instance that represents the given string.
     *
     * @param string the string to get a JSON representation for
     * @return a JSON value that represents the given string
     */
    public static JsonValue value(final String string) {
        return string == null ? Json.NULL : new JsonString(string);
    }

    /**
     * Returns a JsonValue instance that represents the given <code>boolean</code> value.
     *
     * @param value the value to get a JSON representation for
     * @return a JSON value that represents the given value
     */
    public static JsonValue value(final boolean value) {
        return value ? Json.TRUE : Json.FALSE;
    }

    /**
     * Creates a new empty JsonArray. This is equivalent to creating a new JsonArray using the
     * constructor.
     *
     * @return a new empty JSON array
     */
    public static JsonArray array() {
        return new JsonArray();
    }

    /**
     * Creates a new JsonArray that contains the JSON representations from the given <code>int</code>
     * values.
     *
     * @param values the values to be included in the new JSON array
     * @return a new JSON array that contains the given values
     */
    public static JsonArray array(final int... values) {
        if (values == null) {
            throw new NullPointerException(Json.VALUES_IS_NULL);
        }
        final JsonArray array = new JsonArray();
        for (final int value : values) {
            array.add(value);
        }
        return array;
    }

    /**
     * Creates a new JsonArray that contains the JSON representations from the given <code>long</code>
     * values.
     *
     * @param values the values to be included in the new JSON array
     * @return a new JSON array that contains the given values
     */
    public static JsonArray array(final long... values) {
        if (values == null) {
            throw new NullPointerException(Json.VALUES_IS_NULL);
        }
        final JsonArray array = new JsonArray();
        for (final long value : values) {
            array.add(value);
        }
        return array;
    }

    /**
     * Creates a new JsonArray that contains the JSON representations from the given <code>float</code>
     * values.
     *
     * @param values the values to be included in the new JSON array
     * @return a new JSON array that contains the given values
     */
    public static JsonArray array(final float... values) {
        if (values == null) {
            throw new NullPointerException(Json.VALUES_IS_NULL);
        }
        final JsonArray array = new JsonArray();
        for (final float value : values) {
            array.add(value);
        }
        return array;
    }

    /**
     * Creates a new JsonArray that contains the JSON representations from the given <code>double</code>
     * values.
     *
     * @param values the values to be included in the new JSON array
     * @return a new JSON array that contains the given values
     */
    public static JsonArray array(final double... values) {
        if (values == null) {
            throw new NullPointerException(Json.VALUES_IS_NULL);
        }
        final JsonArray array = new JsonArray();
        for (final double value : values) {
            array.add(value);
        }
        return array;
    }

    /**
     * Creates a new JsonArray that contains the JSON representations from the given
     * <code>boolean</code> values.
     *
     * @param values the values to be included in the new JSON array
     * @return a new JSON array that contains the given values
     */
    public static JsonArray array(final boolean... values) {
        if (values == null) {
            throw new NullPointerException(Json.VALUES_IS_NULL);
        }
        final JsonArray array = new JsonArray();
        for (final boolean value : values) {
            array.add(value);
        }
        return array;
    }

    /**
     * Creates a new JsonArray that contains the JSON representations from the given strings.
     *
     * @param strings the strings to be included in the new JSON array
     * @return a new JSON array that contains the given strings
     */
    public static JsonArray array(final String... strings) {
        if (strings == null) {
            throw new NullPointerException(Json.VALUES_IS_NULL);
        }
        final JsonArray array = new JsonArray();
        for (final String value : strings) {
            array.add(value);
        }
        return array;
    }

    /**
     * Creates a new empty JsonObject. This is equivalent to creating a new JsonObject using the
     * constructor.
     *
     * @return a new empty JSON object
     */
    public static JsonObject object() {
        return new JsonObject();
    }

    /**
     * Parses the given input string as JSON. The input must contain a valid JSON value, optionally
     * padded with whitespace.
     *
     * @param string the input string, must be valid JSON
     * @return a value that represents the parsed JSON
     * @throws ParseException if the input is not valid JSON
     */
    public static JsonValue parse(final String string) {
        if (string == null) {
            throw new NullPointerException(Json.STRING_IS_NULL);
        }
        final Json.DefaultHandler handler = new Json.DefaultHandler();
        new JsonParser(handler).parse(string);
        return handler.getValue();
    }

    /**
     * Reads the entire input from the given reader and parses it as JSON. The input must contain a
     * valid JSON value, optionally padded with whitespace.
     * <p>
     * Characters are read in chunks into an input buffer. Hence, wrapping a reader in an additional
     * <code>BufferedReader</code> likely won't improve reading performance.
     * </p>
     *
     * @param reader the reader to read the JSON value from
     * @return a value that represents the parsed JSON
     * @throws IOException if an I/O error occurs in the reader
     * @throws ParseException if the input is not valid JSON
     */
    public static JsonValue parse(final Reader reader) throws IOException {
        if (reader == null) {
            throw new NullPointerException(Json.READER_IS_NULL);
        }
        final Json.DefaultHandler handler = new Json.DefaultHandler();
        new JsonParser(handler).parse(reader);
        return handler.getValue();
    }

    private static String cutOffPointZero(final String string) {
        if (string.endsWith(".0")) {
            return string.substring(0, string.length() - 2);
        }
        return string;
    }

    static class DefaultHandler extends JsonHandler<JsonArray, JsonObject> {

        protected JsonValue value;

        @Override
        public void endNull() {
            this.value = Json.NULL;
        }

        @Override
        public void endBoolean(final boolean bool) {
            this.value = bool ? Json.TRUE : Json.FALSE;
        }

        @Override
        public void endString(final String string) {
            this.value = new JsonString(string);
        }

        @Override
        public void endNumber(final String string) {
            this.value = new JsonNumber(string);
        }

        @Override
        public JsonArray startArray() {
            return new JsonArray();
        }

        @Override
        public void endArray(final JsonArray array) {
            this.value = array;
        }

        @Override
        public void endArrayValue(final JsonArray array) {
            array.add(this.value);
        }

        @Override
        public JsonObject startObject() {
            return new JsonObject();
        }

        @Override
        public void endObject(final JsonObject object) {
            this.value = object;
        }

        @Override
        public void endObjectValue(final JsonObject object, final String name) {
            object.add(name, this.value);
        }

        JsonValue getValue() {
            return this.value;
        }

    }

}
