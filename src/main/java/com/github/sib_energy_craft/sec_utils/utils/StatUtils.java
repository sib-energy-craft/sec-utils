package com.github.sib_energy_craft.sec_utils.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.stat.Stats.CUSTOM;

/**
 * @author sibmaks
 * @since 0.0.19
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StatUtils {

    /**
     * Register new stat into registry
     *
     * @param id stat identification
     * @param formatter stat UI formatter
     * @return stat registered identifier
     */
    public static Identifier register(@NotNull String id, @NotNull StatFormatter formatter) {
        var identifier = new Identifier(id);
        Registry.register(Registries.CUSTOM_STAT, id, identifier);
        CUSTOM.getOrCreateStat(identifier, formatter);
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
