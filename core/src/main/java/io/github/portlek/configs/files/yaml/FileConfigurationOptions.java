/*
 * MIT License
 *
 * Copyright (c) 2020 Hasan Demirtaş
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
 *
 */

package io.github.portlek.configs.files.yaml;

import io.github.portlek.configs.files.configuration.Configuration;
import io.github.portlek.configs.files.configuration.MemoryConfiguration;
import io.github.portlek.configs.files.configuration.MemoryConfigurationOptions;
import org.jetbrains.annotations.NotNull;

/**
 * Various settings for controlling the input and output from a {@link
 * FileConfiguration}
 */
public class FileConfigurationOptions extends MemoryConfigurationOptions {

    @NotNull
    private String header = "";

    private boolean copyHeader = true;

    protected FileConfigurationOptions(@NotNull final MemoryConfiguration configuration) {
        super(configuration);
    }

    @NotNull
    @Override
    public FileConfiguration configuration() {
        return (FileConfiguration) super.configuration();
    }

    @NotNull
    @Override
    public FileConfigurationOptions pathSeparator(final char value) {
        super.pathSeparator(value);
        return this;
    }

    @NotNull
    @Override
    public FileConfigurationOptions copyDefaults(final boolean value) {
        super.copyDefaults(value);
        return this;
    }

    /**
     * Gets the header that will be applied to the top from the saved output.
     * <p>
     * This header will be commented out and applied directly at the top from
     * the generated output from the {@link FileConfiguration}. It is not
     * required to include a newline at the end from the header as it will
     * automatically be applied, but you may include one if you wish for extra
     * spacing.
     * <p>
     * Null is a valid value which will indicate that no header is to be
     * applied. The default value is null.
     *
     * @return Header
     */
    @NotNull
    public final String header() {
        return this.header;
    }

    /**
     * Sets the header that will be applied to the top from the saved output.
     * <p>
     * This header will be commented out and applied directly at the top from
     * the generated output from the {@link FileConfiguration}. It is not
     * required to include a newline at the end from the header as it will
     * automatically be applied, but you may include one if you wish for extra
     * spacing.
     * <p>
     * Null is a valid value which will indicate that no header is to be
     * applied.
     *
     * @param value New header
     * @return This object, for chaining
     */
    @NotNull
    public FileConfigurationOptions header(@NotNull final String value) {
        this.header = value;
        return this;
    }

    /**
     * Gets whether or not the header should be copied from a default source.
     * <p>
     * If this is true, if a default {@link FileConfiguration} is passed to
     * {@link
     * FileConfiguration#setDefaults(Configuration)}
     * then upon saving it will use the header from that config, instead from
     * the one provided here.
     * <p>
     * If no default is set on the configuration, or the default is not from
     * type FileConfiguration, or that config has no header ({@link #header()}
     * returns null) then the header specified in this configuration will be
     * used.
     * <p>
     * Defaults to true.
     *
     * @return Whether or not to copy the header
     */
    public final boolean copyHeader() {
        return this.copyHeader;
    }

    /**
     * Sets whether or not the header should be copied from a default source.
     * <p>
     * If this is true, if a default {@link FileConfiguration} is passed to
     * {@link
     * FileConfiguration#setDefaults(Configuration)}
     * then upon saving it will use the header from that config, instead from
     * the one provided here.
     * <p>
     * If no default is set on the configuration, or the default is not from
     * type FileConfiguration, or that config has no header ({@link #header()}
     * returns null) then the header specified in this configuration will be
     * used.
     * <p>
     * Defaults to true.
     *
     * @param value Whether or not to copy the header
     * @return This object, for chaining
     */
    @NotNull
    public FileConfigurationOptions copyHeader(final boolean value) {
        this.copyHeader = value;

        return this;
    }

}
