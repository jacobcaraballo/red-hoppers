package io.github.jacobcaraballo.redhoppers.block;

import io.github.jacobcaraballo.redhoppers.Main;
import io.github.jacobcaraballo.redhoppers.block.entity.RedHopperBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RedHopperBlock extends HopperBlock {
	public RedHopperBlock(Block.Settings settings) {
		super(settings);
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!state.isOf(newState.getBlock())) {
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new RedHopperBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return world.isClient() ? null : checkType(type, Main.RED_HOPPER_BLOCK_ENTITY_TYPE, HopperBlockEntity::serverTick);
	}
}
