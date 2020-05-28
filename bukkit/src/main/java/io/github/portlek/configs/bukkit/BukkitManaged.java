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

package io.github.portlek.configs.bukkit;

import com.cryptomorin.xseries.XMaterial;
import io.github.portlek.configs.bukkit.provided.BukkitItemStackProvider;
import io.github.portlek.configs.bukkit.provided.BukkitLocationProvider;
import io.github.portlek.configs.bukkit.provided.BukkitSoundProvider;
import io.github.portlek.configs.bukkit.provided.BukkitTitleProvider;
import io.github.portlek.configs.bukkit.util.PlayableSound;
import io.github.portlek.configs.bukkit.util.SentTitle;
import io.github.portlek.configs.files.configuration.FileConfiguration;
import io.github.portlek.configs.structure.managed.FileManaged;
import io.github.portlek.configs.structure.managed.FlManaged;
import io.github.portlek.configs.structure.managed.section.CfgSection;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BukkitManaged extends BukkitSection implements FlManaged {

    static {
        FlManaged.addProvidedClass(ItemStack.class, new BukkitItemStackProvider());
        FlManaged.addProvidedClass(PlayableSound.class, new BukkitSoundProvider());
        FlManaged.addProvidedClass(SentTitle.class, new BukkitTitleProvider());
        FlManaged.addProvidedClass(Location.class, new BukkitLocationProvider());
        FlManaged.addProvidedGetMethod(Material.class, (section, s) ->
            section.getString(s)
                .map(XMaterial::matchXMaterial)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(xMaterial -> Optional.ofNullable(xMaterial.parseMaterial())));
        FlManaged.addProvidedSetMethod(Material.class, Enum::toString);
        FlManaged.addProvidedGetMethod(XMaterial.class, (section, s) ->
            section.getString(s)
                .flatMap(XMaterial::matchXMaterial));
        FlManaged.addProvidedSetMethod(XMaterial.class, Enum::toString);
    }

    @SafeVarargs
    public BukkitManaged(@NotNull final Map.Entry<String, Object>... objects) {
        this(new FileManaged(), objects);
    }

    @SafeVarargs
    public BukkitManaged(@NotNull final FileManaged managed, @NotNull final Map.Entry<String, Object>... objects) {
        super(managed);
        Arrays.stream(objects).forEach(entry -> this.addObject(entry.getKey(), entry.getValue()));
    }

    @NotNull
    @Override
    public FlManaged getBase() {
        return (FlManaged) super.getBase();
    }

    @NotNull
    @Override
    public final FileConfiguration getConfigurationSection() {
        return this.getBase().getConfigurationSection();
    }

    @NotNull
    @Override
    public final Optional<Object> pull(@NotNull final String id) {
        return this.getBase().pull(id);
    }

    @Override
    public final void setup(@NotNull final File file, @NotNull final FileConfiguration fileConfiguration) {
        this.getBase().setup(file, fileConfiguration);
    }

    @NotNull
    @Override
    public final File getFile() {
        return this.getBase().getFile();
    }

    @Override
    public final void addObject(@NotNull final String key, @NotNull final Object object) {
        this.getBase().addObject(key, object);
    }

    @Override
    public final boolean isAutoSave() {
        return this.getBase().isAutoSave();
    }

    @Override
    public final void setAutoSave(final boolean autosv) {
        this.getBase().setAutoSave(autosv);
    }

    @Override
    public final void autoSave() {
        this.getBase().autoSave();
    }

}
