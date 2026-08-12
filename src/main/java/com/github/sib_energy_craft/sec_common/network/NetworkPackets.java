package com.github.sib_energy_craft.sec_common.network;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Network packets screen registration types
 *
 * @author sibmaks
 * @since 0.0.1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NetworkPackets {
    public static final Identifier UPDATE_SCREEN_TYPED_PROPERTY;
    public static final CustomPacketPayload.Type<ScreenTypedPropertyUpdateS2CPacket> UPDATE_SCREEN_PACKET_TYPE_ID;
    public static final StreamCodec<FriendlyByteBuf, ScreenTypedPropertyUpdateS2CPacket> UPDATE_SCREEN_PACKET_TYPE_CODEC;

    static {
        UPDATE_SCREEN_TYPED_PROPERTY = Identifiers.of("update_screen_typed_property");
        UPDATE_SCREEN_PACKET_TYPE_ID = new CustomPacketPayload.Type<>(UPDATE_SCREEN_TYPED_PROPERTY);
        UPDATE_SCREEN_PACKET_TYPE_CODEC = new ScreenTypedPropertyUpdateS2CPacketCodec();
    }
}
