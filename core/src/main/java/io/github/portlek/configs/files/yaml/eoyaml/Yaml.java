/**
 * Copyright (c) 2016-2020, Mihai Emil Andronache
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package io.github.portlek.configs.files.yaml.eoyaml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Yaml.
 *
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id: 2f4f35a1734bc30075ad48e30403afe1c1efb08b $
 * @since 1.0.0
 */
public final class Yaml {

    /**
     * Hidden ctor.
     */
    private Yaml() {
    }

    /**
     * Create a {@link YamlMappingBuilder}.
     *
     * @return Builder of YamlMapping.
     */
    public static YamlMappingBuilder createYamlMappingBuilder() {
        return new RtYamlMappingBuilder();
    }

    /**
     * Create a {@link YamlSequenceBuilder}.
     *
     * @return Builder of YamlMapping.
     */
    public static YamlSequenceBuilder createYamlSequenceBuilder() {
        return new RtYamlSequenceBuilder();
    }

    /**
     * Create a {@link YamlScalarBuilder}.
     *
     * @return Builder of Yaml Scalars.
     */
    public static YamlScalarBuilder createYamlScalarBuilder() {
        return new RtYamlScalarBuilder();
    }

    /**
     * Create a {@link YamlStreamBuilder}.
     *
     * @return Builder of YamlStream.
     */
    public static YamlStreamBuilder createYamlStreamBuilder() {
        return new RtYamlStreamBuilder();
    }

    /**
     * Create a {@link YamlInput} from a String.
     *
     * @param input String to read from.
     * @return YamlInput, reader of Yaml.
     */
    public static YamlInput createYamlInput(final String input) {
        return Yaml.createYamlInput(new ByteArrayInputStream(input.getBytes()));
    }

    /**
     * Create a {@link YamlInput} from an InputStream.
     *
     * @param input InputStream to read from.
     * @return YamlInput, reader of Yaml.
     */
    public static YamlInput createYamlInput(final InputStream input) {
        return new RtYamlInput(input);
    }

    /**
     * Create a YAML dump to represent the given object as YAML.
     *
     * @param object Object to dump.
     * @return YamlDump.
     */
    public static YamlDump createYamlDump(final Object object) {
        return new ReflectedYamlDump(object);
    }

}
