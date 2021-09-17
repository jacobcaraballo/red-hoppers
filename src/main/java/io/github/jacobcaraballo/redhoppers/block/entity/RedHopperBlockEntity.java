package io.github.jacobcaraballo.redhoppers.block.entity;

import java.util.Set;
import java.util.stream.IntStream;

import io.github.jacobcaraballo.redhoppers.Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class RedHopperBlockEntity extends HopperBlockEntity implements SidedInventory {

	public RedHopperBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	public boolean isAcceptedByFilter(ItemStack stack) {
		if (stack == null || stack.isEmpty()) return true;

		Inventory target = this.getTargetInventory();
		if (target == null) return false;
		Item item = stack.getItem();
		return target.containsAny(Set.of(item));
	}

	@Override
	public int[] getAvailableSlots(Direction side) {
		return IntStream.range(0, 5).toArray();
	}

	@Override
	public boolean isValid(int slot, ItemStack stack) {
		return this.isAcceptedByFilter(stack);
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, Direction direction) {
		return this.isAcceptedByFilter(stack);
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction direction) {
		return true;
	}

	@Override
	protected Text getContainerName() {
		return new TranslatableText("container.red_hopper");
	}

	@Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound tag) {
		return super.writeNbt(tag);
	}

	@Override
	public BlockEntityType<?> getType() {
		return Main.RED_HOPPER_BLOCK_ENTITY_TYPE;
	}

	@Nullable
	private Inventory getTargetInventory() {
		if (world == null) return null;
		
		Direction direction = getCachedState().get(HopperBlock.FACING);
		Inventory inventory = getInventoryAt(world, pos.offset(direction));
		
		if (inventory == this) return null;
		
		if (inventory instanceof RedHopperBlockEntity redHopper)
			return redHopper.getTargetInventory();
		
		return inventory;
	}

}
