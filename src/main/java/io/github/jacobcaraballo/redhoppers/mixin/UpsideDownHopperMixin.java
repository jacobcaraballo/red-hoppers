package io.github.jacobcaraballo.redhoppers.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HopperBlockEntity.class)
public abstract class UpsideDownHopperMixin extends LootableContainerBlockEntity {


    protected UpsideDownHopperMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    private static Direction getExtractDirection(Hopper hopper) {
        if (hopper instanceof BlockEntity hopperBlock) {
            BlockState state = hopperBlock.getCachedState();
            if (state.getProperties().contains(HopperBlock.FACING)) {
                return state.get(HopperBlock.FACING) == Direction.UP
                        ? Direction.UP
                        : Direction.DOWN;
            }
        }

        return Direction.DOWN;
    }

    @ModifyVariable(method = "extract(Lnet/minecraft/world/World;Lnet/minecraft/block/entity/Hopper;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;isInventoryEmpty(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/util/math/Direction;)Z"))
    private static Direction jacobc$extractDirection(Direction direction, World world, Hopper hopper) {
        return getExtractDirection(hopper);
    }

    @ModifyConstant(method = "getInputInventory", constant = @Constant(doubleValue = 1.0))
    private static double jacobc$getInputInventory(double constant, World world, Hopper hopper) {
        return getExtractDirection(hopper) == Direction.UP ? -1 : 1;
    }

}
