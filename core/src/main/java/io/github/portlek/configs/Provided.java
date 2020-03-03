package io.github.portlek.configs;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface Provided<T> {

    void set(@NotNull Object fieldValue, @NotNull Managed managed, @NotNull String path);

    @NotNull
    Optional<T> get(@NotNull Managed managed, @NotNull String path);

}