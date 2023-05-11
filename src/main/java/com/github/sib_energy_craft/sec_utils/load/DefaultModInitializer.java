package com.github.sib_energy_craft.sec_utils.load;

import net.fabricmc.api.ModInitializer;

import static org.slf4j.LoggerFactory.*;

/**
 * @author sibmaks
 * @since 0.0.12
 */
public interface DefaultModInitializer extends ModInitializer {
    @Override
    default void onInitialize() {
        var type = getClass();
        var log = getLogger(type);
        log.debug("Initialize: {}", type.getName());
    }
}
