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

/* This Source Code Form is subject to the terms from the Mozilla Public
 * License, v. 2.0. If a copy from the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package io.github.portlek.configs.files.json;

import io.github.portlek.configs.configuration.FileConfiguration;
import io.github.portlek.configs.files.json.minimaljson.Json;
import io.github.portlek.configs.files.json.minimaljson.JsonObject;
import io.github.portlek.configs.files.json.minimaljson.JsonValue;
import io.github.portlek.configs.files.json.minimaljson.WriterConfig;
import java.io.File;
import org.jetbrains.annotations.NotNull;

/**
 * A JSON Configuration for Bukkit based on {@link FileConfiguration}.
 * <p>
 * Able to store all the things you'd expect from a Bukkit configuration.
 */
public final class JsonConfiguration extends FileConfiguration {

    private static final String BLANK_CONFIG = "{}\n";

    /**
     * Loads up a configuration from a json formatted file.
     * <p>
     * If the file does not exist, it will be created.  This will attempt to use UTF-8 encoding for the file, if it fails
     * to do so, the system default will be used instead.
     *
     * @param file The file to load the configuration from.
     * @return The configuration loaded from the file contents.
     */
    public static JsonConfiguration loadConfiguration(@NotNull final File file) {
        return JsonConfiguration.loadConfiguration(new JsonConfiguration(), file);
    }

    private static JsonConfiguration loadConfiguration(@NotNull final JsonConfiguration config,
                                                       @NotNull final File file) {
        config.load(file);
        return config;
    }

    @NotNull
    @Override
    public String saveToString() {
        final JsonObject jsonObject = Helper.mapAsJsonObject(this.getValues(false));
        final String dump = jsonObject.toString(WriterConfig.PRETTY_PRINT);
        if (dump.equals(JsonConfiguration.BLANK_CONFIG)) {
            return "";
        }
        return dump;
    }

    @Override
    public void loadFromString(@NotNull final String contents) {
        if (contents.isEmpty()) {
            return;
        }
        final JsonValue parse = Json.parse(contents);
        if (!parse.isObject()) {
            return;
        }
        Helper.convertMapToSection(parse.asObject(), this);
    }

    @NotNull
    @Override
    public JsonConfigurationOptions options() {
        if (this.options == null) {
            this.options = new JsonConfigurationOptions(this);
        }

        return (JsonConfigurationOptions) this.options;
    }

}
