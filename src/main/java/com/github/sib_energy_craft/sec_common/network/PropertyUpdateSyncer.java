package com.github.sib_energy_craft.sec_common.network;

import com.github.sib_energy_craft.screen.property.StaticTypedScreenProperty;
import com.github.sib_energy_craft.screen.property.TypedScreenProperty;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.AllArgsConstructor;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class send screen properties to client
 *
 * @author sibmaks
 * @since 0.0.1
 */
@AllArgsConstructor
public class PropertyUpdateSyncer implements Runnable {
    private final int syncId;
    private final ServerPlayer serverPlayerEntity;
    private final List<TypedScreenProperty<?, ?>> properties;
    private final Int2ObjectMap<Object> previousProperties = new Int2ObjectOpenHashMap<>();

    @Override
    public void run() {
        var changedProperties = new ArrayList<TypedScreenProperty<?, ?>>();
        for (var property : properties) {
            var id = property.id();
            var currentValue = property.value();
            var previousValue = previousProperties.get(id);
            if (Objects.equals(currentValue, previousValue)) {
                continue;
            }
            var codec = property.codec();
            previousProperties.put(id, currentValue);
            var changedProperty = new StaticTypedScreenProperty(id, codec, currentValue);
            changedProperties.add(changedProperty);
        }
        if (changedProperties.isEmpty()) {
            return;
        }

        var packet = new ScreenTypedPropertyUpdateS2CPacket(syncId, changedProperties);
        ServerPlayNetworking.send(serverPlayerEntity, packet);
    }

}
