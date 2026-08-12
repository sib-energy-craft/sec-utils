package com.github.sib_energy_craft.sec_common.mixin;

import com.github.sib_energy_craft.sec_common.Hooks;
import com.github.sib_energy_craft.sec_common.block.StrippedBlock;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;

/**
 * {@link AxeItem} mixin. Add ability to extends Axe logic.
 *
 * @author drobyshev-ma
 * @since 0.0.17
 */
@Slf4j
@Mixin(AxeItem.class)
public class AxeItemMixin {
    /**
     * Stripped block static final field into {@link AxeItem}.<br/>
     */
    @Mutable
    @Shadow @Final protected static Map<Block, Block> STRIPPABLES;

    static {
        Hooks.AxeItemClassInit = value -> AxeItemMixin.STRIPPABLES = new ImmutableMap.Builder<Block, Block>()
                        .putAll(STRIPPABLES)
                        .putAll(value)
                        .build();
    }

    @Inject(method = "getStripped", at = @At("HEAD"), cancellable = true)
    private void getStripped(BlockState state,
                             CallbackInfoReturnable<Optional<BlockState>> callbackInfoReturnable) {
        var strippedBlock = STRIPPABLES.get(state.getBlock());
        if(strippedBlock instanceof StrippedBlock block) {
            var strippedState = block.getStrippedState(state);
            callbackInfoReturnable.setReturnValue(Optional.of(strippedState));
        }
    }
}
