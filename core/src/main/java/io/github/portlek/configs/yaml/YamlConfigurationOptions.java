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

package io.github.portlek.configs.yaml;

import io.github.portlek.configs.util.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Various settings for controlling the input and output of a {@link
 * YamlConfiguration}
 */
public class YamlConfigurationOptions extends FileConfigurationOptions {

    private int indent = 2;

    protected YamlConfigurationOptions(@NotNull final YamlConfiguration configuration) {
        super(configuration);
    }

    @NotNull
    @Override
    public final YamlConfiguration configuration() {
        return (YamlConfiguration) super.configuration();
    }

    @NotNull
    @Override
    public final YamlConfigurationOptions pathSeparator(final char value) {
        super.pathSeparator(value);
        return this;
    }

    @NotNull
    @Override
    public final YamlConfigurationOptions copyDefaults(final boolean value) {
        super.copyDefaults(value);
        return this;
    }

    @NotNull
    @Override
    public final YamlConfigurationOptions header(@Nullable final String value) {
        super.header(value);
        return this;
    }

    @NotNull
    @Override
    public final YamlConfigurationOptions copyHeader(final boolean value) {
        super.copyHeader(value);
        return this;
    }

    /**
     * Gets how much spaces should be used to indent each line.
     * <p>
     * The minimum value this may be is 2, and the maximum is 9.
     *
     * @return How much to indent by
     */
    public final int indent() {
        return this.indent;
    }

    /**
     * Sets how much spaces should be used to indent each line.
     * <p>
     * The minimum value this may be is 2, and the maximum is 9.
     *
     * @param value New indent
     * @return This object, for chaining
     */
    @NotNull
    public final YamlConfigurationOptions indent(final int value) {
        Validate.isTrue(value >= 2, "Indent must be at least 2 characters");
        Validate.isTrue(value <= 9, "Indent cannot be greater than 9 characters");

        this.indent = value;
        return this;
    }

}