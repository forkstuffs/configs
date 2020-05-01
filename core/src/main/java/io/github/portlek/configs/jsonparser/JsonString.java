/*
 * MIT License
 *
 * Copyright (c) 2020 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package io.github.portlek.configs.jsonparser;

import java.io.IOException;

@SuppressWarnings("serial")
    // use default serial UID
class JsonString extends JsonValue {

    private final String string;

    JsonString(final String string) {
        super();
        if (string == null) {
            throw new NullPointerException("string is null");
        }
        this.string = string;
    }

    @Override
    public final boolean isString() {
        return true;
    }

    @Override
    public final String asString() {
        return this.string;
    }

    @Override
    final void write(final JsonWriter writer) throws IOException {
        writer.writeString(this.string);
    }

    @Override
    public final int hashCode() {
        return this.string.hashCode();
    }

    @Override
    public final boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }
        final JsonString other = (JsonString) object;
        return this.string.equals(other.string);
    }

}
