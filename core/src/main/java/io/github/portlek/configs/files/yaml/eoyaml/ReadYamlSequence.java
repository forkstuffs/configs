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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * YamlSequence read from somewhere.
 *
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id: 9a0c9470e924496a87d584981c831601f46d18b4 $
 * @since 1.0.0
 */
final class ReadYamlSequence extends BaseYamlSequence {

    /**
     * Yaml line just previous to the one where this sequence starts. E.g.
     * <pre>
     * 0  sequence:
     * 1    - elem1
     * 2    - elem2
     * </pre>
     * In the above example the sequence consists of elem1 and elem2, while
     * "previous" is line 0. If the sequence starts at the root, then line
     * "previous" is {@link YamlLine.NullYamlLine}; E.g.
     * <pre>
     * 0  - elem1
     * 1  - elem2
     * </pre>
     */
    private final YamlLine previous;

    /**
     * All lines of the YAML document.
     */
    private final AllYamlLines all;

    /**
     * Only the significant lines of this sequence.
     */
    private final YamlLines significant;

    /**
     * If set to true we will try to guess the correct indentation
     * of misplaced lines.
     */
    private final boolean guessIndentation;

    /**
     * Ctor.
     *
     * @param lines Given lines.
     */
    ReadYamlSequence(final AllYamlLines lines) {
        this(lines, false);
    }

    /**
     * Ctor.
     *
     * @param lines Given lines.
     * @param guessIndentation If set to true, we will try to
     * guess the correct indentation of misplaced lines.
     */
    ReadYamlSequence(
        final AllYamlLines lines,
        final boolean guessIndentation
    ) {
        this(new YamlLine.NullYamlLine(), lines, guessIndentation);
    }

    /**
     * Ctor.
     *
     * @param previous Line just before the start of this sequence.
     * @param lines Given lines.
     */
    ReadYamlSequence(final YamlLine previous, final AllYamlLines lines) {
        this(previous, lines, false);
    }

    /**
     * Ctor.
     *
     * @param previous Line just before the start of this sequence.
     * @param lines Given lines.
     * @param guessIndentation If set to true, we will try to guess the
     * correct indentation of misplaced lines.
     */
    ReadYamlSequence(
        final YamlLine previous,
        final AllYamlLines lines,
        final boolean guessIndentation
    ) {
        this.previous = previous;
        this.all = lines;
        this.significant = new SameIndentationLevel(
            new WellIndented(
                new Skip(
                    lines,
                    line -> line.number() <= previous.number(),
                    line -> line.trimmed().startsWith("#"),
                    line -> line.trimmed().startsWith("---"),
                    line -> line.trimmed().startsWith("..."),
                    line -> line.trimmed().startsWith("%"),
                    line -> line.trimmed().startsWith("!!")
                ),
                guessIndentation
            )
        );
        this.guessIndentation = guessIndentation;
    }

    @Override
    public Collection<YamlNode> values() {
        final List<YamlNode> kids = new LinkedList<>();
        final boolean foldedSequence = this.previous.trimmed().matches(
            "^.*\\|.*\\-$"
        );
        for (final YamlLine line : this.significant) {
            final String trimmed = line.trimmed();
            if (foldedSequence || trimmed.startsWith("-")) {
                if ("-".equals(trimmed)
                    || trimmed.endsWith("|")
                    || trimmed.endsWith(">")
                ) {
                    kids.add(
                        this.significant.toYamlNode(
                            line, this.guessIndentation
                        )
                    );
                } else {
                    if (trimmed.matches("^.*\\-.*\\:.*$")) {
                        kids.add(
                            new ReadYamlMapping(
                                new RtYamlLine("", line.number() - 1),
                                this.all,
                                this.guessIndentation
                            )
                        );
                    } else {
                        kids.add(new ReadPlainScalar(this.all, line));
                    }
                }
            }
        }
        return kids;
    }

    @Override
    public Comment comment() {
        //LineLength (50 lines)
        return new ReadComment(
            new Backwards(
                new FirstCommentFound(
                    new Backwards(
                        new Skip(
                            this.all,
                            line -> {
                                final boolean skip;
                                if (this.previous.number() < 0) {
                                    if (this.significant.iterator().hasNext()) {
                                        skip = line.number() >= this.significant
                                            .iterator().next().number();
                                    } else {
                                        skip = false;
                                    }
                                } else {
                                    skip = line.number() >= this.previous.number();
                                }
                                return skip;
                            },
                            line -> line.trimmed().startsWith("---"),
                            line -> line.trimmed().startsWith("..."),
                            line -> line.trimmed().startsWith("%"),
                            line -> line.trimmed().startsWith("!!")
                        )
                    )
                )
            ),
            this
        );
    }

}
