package com.github.sib_energy_craft.sec_common.network;

import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

/**
 * Network entry point
 *
 * @author sibmaks
 * @since 0.0.1
 */
@Slf4j
public final class SecNetwork implements DefaultModInitializer {
    static {
        var payloadS2CRegistry = PayloadTypeRegistry.clientboundPlay();
        payloadS2CRegistry
                .register(NetworkPackets.UPDATE_SCREEN_PACKET_TYPE_ID, NetworkPackets.UPDATE_SCREEN_PACKET_TYPE_CODEC);
    }
}
