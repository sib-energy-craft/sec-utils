package com.github.sib_energy_craft.sec_common.screen;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import java.util.function.Consumer;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public interface TypedPropertyScreenHandler {
    /**
     * Get typed properties handlers, property id to handler map
     *
     * @return property id to handler map
     */
    Int2ObjectMap<Consumer<Object>> getTypedPropertiesHandlers();
}
