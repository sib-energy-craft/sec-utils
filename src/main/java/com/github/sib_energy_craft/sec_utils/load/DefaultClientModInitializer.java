package com.github.sib_energy_craft.sec_utils.load;

import net.fabricmc.api.ClientModInitializer;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author sibmaks
 * @since 0.0.13
 */
public interface DefaultClientModInitializer extends ClientModInitializer {
    @Override
    default void onInitializeClient() {
        var type = getClass();
        var log = getLogger(type);
        log.debug("Initialize client: {}", type.getName());
    }
}
