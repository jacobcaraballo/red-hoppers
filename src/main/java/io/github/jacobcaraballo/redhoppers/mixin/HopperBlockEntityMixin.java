package io.github.jacobcaraballo.redhoppers.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.jacobcaraballo.redhoppers.block.entity.RedHopperBlockEntity;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.Direction;

import java.util.function.BooleanSupplier;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {

	@Inject(method = "extract(Lnet/minecraft/block/entity/Hopper;Lnet/minecraft/inventory/Inventory;" +
			"ILnet/minecraft/util/math/Direction;)Z", at = @At("HEAD"), cancellable = true)
	private static void jacobc$filterInventoryInput(Hopper hopper, Inventory inventory, int slot, Direction side, CallbackInfoReturnable<Boolean> ci) {
		if (hopper instanceof RedHopperBlockEntity redHopper) {
			if (!redHopper.isAcceptedByFilter(inventory.getStack(slot))) {
				ci.setReturnValue(false);
			}
		}
	}

	@Inject(method = "extract(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/entity/ItemEntity;)Z", at = @At("HEAD"), cancellable = true)
	private static void jacobc$filterItemEntityInput(Inventory inventory, ItemEntity itemEntity, CallbackInfoReturnable<Boolean> ci) {
		if (inventory instanceof RedHopperBlockEntity redHopper) {
			if (!redHopper.isAcceptedByFilter(itemEntity.getStack())) {
				ci.setReturnValue(false);
			}
		}
	}

	@Inject(method = "insertAndExtract", at = @At(value = "RETURN", target = "Lnet/minecraft/block/entity/HopperBlockEntity;setCooldown(I)V"), cancellable = true)
	private static void jacobc$setTransferCooldown(World world, BlockPos pos, BlockState state, HopperBlockEntity blockEntity, BooleanSupplier booleanSupplier, CallbackInfoReturnable<Boolean> cir) {
		if (!isRedHopper(blockEntity)) return;
		setTransferCooldown(blockEntity);
	}

	private static void setTransferCooldown(Object hopper) {
		((HopperCooldownAccessor) hopper).setTransferCooldown(2);
	}

	private static boolean isRedHopper(Object hopper) {
		return hopper instanceof RedHopperBlockEntity;
	}

}
