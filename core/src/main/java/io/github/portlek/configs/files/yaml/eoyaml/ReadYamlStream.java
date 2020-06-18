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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Read YAML Stream of documents.
 *
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id: 8cdc558ff5ebbb5b1373ed3424bfe1562b27fe72 $
 * @since 3.1.4
 */
final class ReadYamlStream extends BaseYamlStream {

    /**
     * All the lines of this YamlStream.
     */
    private final YamlLines all;

    /**
     * Read lines of this YAML Stream.
     */
    private final YamlLines startMarkers;

    /**
     * If set to true we will try to guess the correct indentation
     * of misplaced lines.
     */
    private final boolean guessIndentation;

    /**
     * Constructor.
     *
     * @param lines All YAML lines as they are read from the input.
     */
    ReadYamlStream(final AllYamlLines lines) {
        this(lines, false);
    }

    /**
     * Constructor.
     *
     * @param lines All YAML lines as they are read from the input.
     * @param guessIndentation If set to true, we will try to guess
     * the correct indentation of misplaced lines.
     */
    ReadYamlStream(final AllYamlLines lines, final boolean guessIndentation) {
        this.startMarkers = new WellIndented(
            new StartMarkers(
                new Skip(
                    lines,
                    line -> line.trimmed().startsWith("#"),
                    line -> line.trimmed().startsWith("%")
                )
            )
        );
        this.all = new Skip(
            lines,
            line -> line.trimmed().startsWith("#"),
            line -> line.trimmed().startsWith("%")
        );
        this.guessIndentation = guessIndentation;
    }

    @Override
    public Collection<YamlNode> values() {
        final List<YamlNode> values = new ArrayList<>();
        for (final YamlLine startDoc : this.startMarkers) {
            final YamlLines document = this.readDocument(startDoc);
            if (!document.original().isEmpty()) {
                values.add(
                    document.toYamlNode(
                        startDoc, this.guessIndentation
                    )
                );
            }
        }
        return values;
    }

    /**
     * Read a document from this Stream.
     *
     * @param start Start marker of the document.
     * @return YamlLines of the read document.
     */
    private YamlLines readDocument(final YamlLine start) {
        final List<YamlLine> yamlDocLines = new ArrayList<>();
        final YamlLine startLine = this.all.line(start.number());
        if (!"---".equals(startLine.trimmed())) {
            yamlDocLines.add(startLine);
        }
        for (final YamlLine line : this.all.original()) {
            if (line.number() > start.number()) {
                final String current = line.trimmed();
                if ("---".equals(current) || "...".equals(current)) {
                    break;
                } else {
                    yamlDocLines.add(line);
                }
            }
        }
        return new AllYamlLines(yamlDocLines);
    }

}
