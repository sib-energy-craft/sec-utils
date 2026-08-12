package com.github.sib_energy_craft.sec_common.network;

import com.github.sib_energy_craft.sec_common.screen.property.TypedScreenProperty;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * Screen typed property update packet
 *
 * @param syncId     screen identifier
 * @param properties screen typed properties
 * @author sibmaks
 * @since 0.0.1
 */
public record ScreenTypedPropertyUpdateS2CPacket(
        int syncId,
        List<TypedScreenProperty<?, ?>> properties
) implements CustomPacketPayload {

    @NonNull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return NetworkPackets.UPDATE_SCREEN_PACKET_TYPE_ID;
    }
}
