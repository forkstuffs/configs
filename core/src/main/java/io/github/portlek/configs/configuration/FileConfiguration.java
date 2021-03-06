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

package io.github.portlek.configs.configuration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

/**
 * This is a base class for all File based implementations from {@link Configuration}
 */
public abstract class FileConfiguration extends MemoryConfiguration {

    /**
     * Creates an empty {@link FileConfiguration} with no default values.
     */
    protected FileConfiguration() {
        super();
    }

    /**
     * Saves this {@link FileConfiguration} to the specified location.
     * <p>
     * If the file does not exist, it will be created. If already exists, it
     * will be overwritten. If it cannot be overwritten or created, an
     * exception will be thrown.
     * <p>
     * This method will save using the system default encoding, or possibly
     * using UTF8.
     *
     * @param file File to save to.
     * @throws IllegalArgumentException Thrown when file is null.
     */
    @SneakyThrows
    public final void save(@NotNull final File file) {
        file.getParentFile().mkdirs();
        final String data = this.saveToString();
        try (final Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(data);
        }
    }

    /**
     * Saves this {@link FileConfiguration} to a string, and returns it.
     *
     * @return String containing this configuration.
     */
    @NotNull
    public abstract String saveToString();

    /**
     * Loads this {@link FileConfiguration} from the specified location.
     * <p>
     * All the values contained within this configuration will be removed,
     * leaving only settings and defaults, and the new values will be loaded
     * from the given file.
     * <p>
     * If the file cannot be loaded for any reason, an exception will be
     * thrown.
     *
     * @param file File to load from.
     * @throws IllegalArgumentException Thrown when file is null.
     */
    public final void load(@NotNull final String file) {
        this.load(new File(file));
    }

    /**
     * Loads this {@link FileConfiguration} from the specified location.
     * <p>
     * All the values contained within this configuration will be removed,
     * leaving only settings and defaults, and the new values will be loaded
     * from the given file.
     * <p>
     * If the file cannot be loaded for any reason, an exception will be
     * thrown.
     *
     * @param file File to load from.
     * @throws IllegalArgumentException Thrown when file is null.
     */
    @SneakyThrows
    public final void load(@NotNull final File file) {
        final FileInputStream stream = new FileInputStream(file);
        this.load(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }

    /**
     * Loads this {@link FileConfiguration} from the specified reader.
     * <p>
     * All the values contained within this configuration will be removed,
     * leaving only settings and defaults, and the new values will be loaded
     * from the given stream.
     *
     * @param reader the reader to load from
     * @throws IllegalArgumentException thrown when reader is null
     */
    @SneakyThrows
    public final void load(@NotNull final Reader reader) {
        final BufferedReader input;
        if (reader instanceof BufferedReader) {
            input = (BufferedReader) reader;
        } else {
            input = new BufferedReader(reader);
        }
        @Cleanup final BufferedReader fnlinput = input;
        final StringBuilder builder = new StringBuilder();
        String line;
        while ((line = fnlinput.readLine()) != null) {
            builder.append(line);
            builder.append('\n');
        }
        this.loadFromString(builder.toString());
    }

    /**
     * Loads this {@link FileConfiguration} from the specified string, as
     * opposed to from file.
     * <p>
     * All the values contained within this configuration will be removed,
     * leaving only settings and defaults, and the new values will be loaded
     * from the given string.
     * <p>
     * If the string is invalid in any way, an exception will be thrown.
     *
     * @param contents Contents from a Configuration to load.
     * @throws IllegalArgumentException Thrown if contents is null.
     */
    public abstract void loadFromString(@NotNull String contents);

    @NotNull
    @Override
    public FileConfigurationOptions options() {
        if (this.options == null) {
            this.options = new FileConfigurationOptions(this);
        }
        return (FileConfigurationOptions) this.options;
    }

}
