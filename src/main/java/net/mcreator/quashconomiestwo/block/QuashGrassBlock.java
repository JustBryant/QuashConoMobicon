
package net.mcreator.quashconomiestwo.block;

import net.minecraft.block.material.Material;

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
			super(

					Block.Properties.create(Material.ORGANIC).sound(SoundType.GROUND).hardnessAndResistance(1f, 10f).setLightLevel(s -> 0)
							.harvestLevel(0).harvestTool(ToolType.SHOVEL));

			setRegistryName("quash_grass");
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(QuashDirtItem.block, (int) (1)));
		}

	}

}
