package com.github.sib_energy_craft.sec_utils.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.StatFormatter;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.stats.Stats.CUSTOM;

/**
 * @author sibmaks
 * @since 0.0.19
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StatUtils {

    /**
     * Register new stat into registry
     *
     * @param id        stat identification
     * @param formatter stat UI formatter
     * @return stat registered identifier
     */
    public static Identifier register(@NotNull String id, @NotNull StatFormatter formatter) {
        var identifier = Identifier.parse(id);
        Registry.register(BuiltInRegistries.CUSTOM_STAT, id, identifier);
        CUSTOM.get(identifier, formatter);
        return identifier;
    }

    /**
     * Register new stat into registry with default formatter
     *
     * @param id stat identification
     * @return stat registered identifier
     */
    public static Identifier register(@NotNull String id) {
        return register(id, StatFormatter.DEFAULT);
    }
}
