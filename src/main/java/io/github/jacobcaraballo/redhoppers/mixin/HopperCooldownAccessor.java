package io.github.jacobcaraballo.redhoppers.mixin;

import net.minecraft.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HopperBlockEntity.class)
public interface HopperCooldownAccessor {

    @Accessor("transferCooldown")
    void setTransferCooldown(int cooldown);

}
