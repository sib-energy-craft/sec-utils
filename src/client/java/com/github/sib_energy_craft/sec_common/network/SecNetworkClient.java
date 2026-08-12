package com.github.sib_energy_craft.sec_common.network;

import com.github.sib_energy_craft.sec_common.load.DefaultClientModInitializer;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.registerGlobalReceiver;

/**
 * @author sibmaks
 * @since 0.0.1
 */
@Slf4j
@Environment(EnvType.CLIENT)
public final class SecNetworkClient implements DefaultClientModInitializer {

    static {
        var handler = new ScreenTypedPropertyUpdateS2CPacketHandler();
        registerGlobalReceiver(
                NetworkPackets.UPDATE_SCREEN_PACKET_TYPE_ID,
                handler
        );
    }

}
