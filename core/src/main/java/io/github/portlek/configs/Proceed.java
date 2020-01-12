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

package io.github.portlek.configs;

import io.github.portlek.configs.annotations.File;
import io.github.portlek.configs.annotations.LinkedFile;
import io.github.portlek.configs.processors.FileProceed;
import io.github.portlek.configs.processors.LinkedFileProceed;
import org.jetbrains.annotations.NotNull;

public interface Proceed {

    void load(@NotNull Object instance);

    @NotNull
    static <T> T of(@NotNull T object) {
        final File file = object.getClass().getDeclaredAnnotation(File.class);

        if (file != null) {
            new FileProceed(file).load(object);

            return object;
        }

        final LinkedFile linkedFile = object.getClass().getDeclaredAnnotation(LinkedFile.class);

        if (linkedFile != null) {
            new LinkedFileProceed(linkedFile).load(object);

            return object;
        }

        throw new UnsupportedOperationException();
    }

}
