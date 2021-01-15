
package net.mcreator.quashconomiestwo.block;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.common.ToolType;

import net.minecraft.loot.LootContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import net.mcreator.quashconomiestwo.itemgroup.QuashconomiesItemGroup;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

import java.util.List;
import java.util.Collections;

@QuashconomiestwoModElements.ModElement.Tag
public class QuashGrassBlock extends QuashconomiestwoModElements.ModElement {
	@ObjectHolder("quashconomiestwo:quash_grass")
	public static final Block block = null;
	public QuashGrassBlock(QuashconomiestwoModElements instance) {
		super(instance, 111);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items
				.add(() -> new BlockItem(block, new Item.Properties().group(QuashconomiesItemGroup.tab)).setRegistryName(block.getRegistryName()));
	}
	public static class CustomBlock extends Block {
		public CustomBlock() {
			super(Block.Properties.create(Material.ORGANIC).sound(SoundType.GROUND).hardnessAndResistance(1f, 10f).setLightLevel(s -> 0)
					.harvestLevel(0).harvestTool(ToolType.SHOVEL));
			setRegistryName("quash_grass");
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(QuashDirtBlock.block, (int) (1)));
		}
	}
}
