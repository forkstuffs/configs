package io.github.portlek.configs.processors;

import io.github.portlek.configs.annotations.LinkedConfig;
import io.github.portlek.configs.annotations.LinkedFile;
import io.github.portlek.configs.structure.linked.LnkdManaged;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class LinkedConfigProceed {

    @NotNull
    private final LinkedConfig config;

    @NotNull
    private final LnkdManaged linked;

    public void load() {
        Arrays.stream(this.config.value())
            .filter(linkedFile -> linkedFile.key().equals(this.linked.getChosen().get()))
            .findFirst()
            .map(LinkedFile::config)
            .map(cnfg -> new ConfigProceed(cnfg, this.linked))
            .ifPresent(ConfigProceed::load);
    }

}
