package com.github.sib_energy_craft.sec_common.network;

import com.github.sib_energy_craft.sec_common.screen.TypedPropertyScreenHandler;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.jspecify.annotations.NonNull;

/**
 * @author sibmaks
 * @since 0.0.7-1.21.4
 */
@Slf4j
@Environment(EnvType.CLIENT)
public class ScreenTypedPropertyUpdateS2CPacketHandler
        implements ClientPlayNetworking.PlayPayloadHandler<ScreenTypedPropertyUpdateS2CPacket> {

    @Override
    public void receive(
            @NonNull ScreenTypedPropertyUpdateS2CPacket packet,
            ClientPlayNetworking.@NonNull Context context
    ) {
        try {
            var playerEntity = context.player();
            var currentScreenHandler = playerEntity.containerMenu;

            if (!(currentScreenHandler instanceof TypedPropertyScreenHandler propertyListener)) {
                return;
            }

            if (currentScreenHandler.containerId != packet.syncId()) {
                return;
            }
            var typedPropertiesHandlers = propertyListener.getTypedPropertiesHandlers();
            for (var property : packet.properties()) {
                var handler = typedPropertiesHandlers.get(property.id());
                if (handler == null) {
                    var currentScreenHandlerType = currentScreenHandler.getClass();
                    log.error("Unknown property id: {} for {}", property.id(), currentScreenHandlerType.getName());
                    continue;
                }
                handler.accept(property.value());
            }
        } catch (Exception e) {
            log.error("Int property process error", e);
        }
    }

}
