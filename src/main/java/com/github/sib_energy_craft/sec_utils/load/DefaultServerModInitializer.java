package com.github.sib_energy_craft.sec_utils.load;

import net.fabricmc.api.DedicatedServerModInitializer;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author sibmaks
 * @since 0.0.13
 */
public interface DefaultServerModInitializer extends DedicatedServerModInitializer {
    @Override
    default void onInitializeServer() {
        var type = getClass();
        var log = getLogger(type);
        log.debug("Initialize server: {}", type.getName());
    }
}
