
package net.mcreator.quashconomiestwo.block;

import net.minecraft.block.material.Material;

@QuashconomiestwoModElements.ModElement.Tag
public class QuashcentBlockBlock extends QuashconomiestwoModElements.ModElement {

	@ObjectHolder("quashconomiestwo:quashcent_block")
	public static final Block block = null;

	public QuashcentBlockBlock(QuashconomiestwoModElements instance) {
		super(instance, 86);

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

					Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(10f, 15f).setLightLevel(s -> 0)
							.harvestLevel(1).harvestTool(ToolType.PICKAXE));

			setRegistryName("quashcent_block");
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(this, 1));
		}

	}

}
