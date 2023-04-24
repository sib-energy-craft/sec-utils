package com.github.sib_energy_craft.sec_utils.common;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * Class contain some entity and it's identifier.<br/>
 * Can be useful in case if you register some in registry and need entity + identifier later.
 *
 * @since 0.0.1
 * @author sibmaks
 */
public record Identified<T>(@NotNull Identifier identifier, @NotNull T entity) {
}
