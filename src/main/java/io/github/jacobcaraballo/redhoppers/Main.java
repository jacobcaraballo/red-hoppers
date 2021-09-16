package io.github.jacobcaraballo.redhoppers;

import io.github.jacobcaraballo.redhoppers.block.RedHopperBlock;
import io.github.jacobcaraballo.redhoppers.block.entity.RedHopperBlockEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {
	public static final String MOD_ID = "redhoppers";

	private static final Identifier RED_HOPPER_ID = new Identifier(MOD_ID, "red_hopper");
	public static final Block RED_HOPPER = new RedHopperBlock(FabricBlockSettings.copyOf(Blocks.HOPPER).mapColor(MapColor.RED));
	public static final Item RED_HOPPER_ITEM = new BlockItem(RED_HOPPER, new Item.Settings().group(ItemGroup.REDSTONE));

	public static final BlockEntityType<RedHopperBlockEntity> RED_HOPPER_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(RedHopperBlockEntity::new, RED_HOPPER).build(null);

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, RED_HOPPER_ID, RED_HOPPER);
		Registry.register(Registry.ITEM, RED_HOPPER_ID, RED_HOPPER_ITEM);

		Registry.register(Registry.BLOCK_ENTITY_TYPE, RED_HOPPER_ID, RED_HOPPER_BLOCK_ENTITY_TYPE);
	}
}