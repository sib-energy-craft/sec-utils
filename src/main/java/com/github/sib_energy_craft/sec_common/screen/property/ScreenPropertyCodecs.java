package com.github.sib_energy_craft.sec_common.screen.property;

import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.energy_api.serialization.EnergyPacketCodec;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

/**
 * @author sibmaks
 * @since 0.0.6
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScreenPropertyCodecs {
    public static final StreamCodec<ByteBuf, Short> SHORT = ByteBufCodecs.SHORT;
    public static final StreamCodec<ByteBuf, Integer> INTEGER = ByteBufCodecs.INT;

    public static final StreamCodec<ByteBuf, Float> FLOAT = ByteBufCodecs.FLOAT;
    public static final StreamCodec<ByteBuf, Double> DOUBLE = ByteBufCodecs.DOUBLE;

    public static final StreamCodec<RegistryFriendlyByteBuf, ItemStack> ITEM_STACK = ItemStack.STREAM_CODEC;

    public static final StreamCodec<ByteBuf, Boolean> BOOLEAN = ByteBufCodecs.BOOL;
    public static final StreamCodec<ByteBuf, String> STRING = ByteBufCodecs.STRING_UTF8;

    public static final StreamCodec<FriendlyByteBuf, Energy> ENERGY = EnergyPacketCodec.CODEC;
}
