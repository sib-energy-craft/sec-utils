package com.github.sib_energy_craft.sec_common.network;

import com.github.sib_energy_craft.screen.property.ScreenPropertyRegistry;
import com.github.sib_energy_craft.screen.property.StaticTypedScreenProperty;
import com.github.sib_energy_craft.screen.property.TypedScreenProperty;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;

/**
 * @author sibmaks
 * @since 0.0.6
 */
public class ScreenTypedPropertyUpdateS2CPacketCodec
        implements StreamCodec<FriendlyByteBuf, ScreenTypedPropertyUpdateS2CPacket> {

    @NonNull
    @Override
    public ScreenTypedPropertyUpdateS2CPacket decode(FriendlyByteBuf buf) {
        var syncId = buf.readShort();
        var propertiesSize = buf.readInt();
        var properties = new ArrayList<TypedScreenProperty<?, ?>>();

        for (int i = 0; i < propertiesSize; i++) {
            var propertyId = buf.readShort();
            var propertyTypeIndex = buf.readShort();
            StreamCodec propertyCodec = ScreenPropertyRegistry.findValue(propertyTypeIndex);
            var propertyValue = propertyCodec.decode(buf);

            var property = new StaticTypedScreenProperty<>(propertyId, propertyCodec, propertyValue);
            properties.add(property);
        }

        return new ScreenTypedPropertyUpdateS2CPacket(syncId, properties);
    }

    @Override
    public void encode(FriendlyByteBuf buf, ScreenTypedPropertyUpdateS2CPacket value) {
        buf.writeShort(value.syncId());

        var properties = value.properties();
        buf.writeInt(properties.size());

        for (var property : properties) {
            buf.writeShort(property.id());
            StreamCodec type = property.codec();
            var index = ScreenPropertyRegistry.getIndex(type);
            buf.writeShort(index);
            var propertyValue = property.value();
            type.encode(buf, propertyValue);
        }
    }
}
