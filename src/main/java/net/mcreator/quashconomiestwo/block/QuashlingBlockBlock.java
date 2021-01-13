
package net.mcreator.quashconomiestwo.block;

import net.minecraft.block.material.Material;

@QuashconomiestwoModElements.ModElement.Tag
public class QuashlingBlockBlock extends QuashconomiestwoModElements.ModElement {

	@ObjectHolder("quashconomiestwo:quashling_block")
	public static final Block block = null;

	public QuashlingBlockBlock(QuashconomiestwoModElements instance) {
		super(instance, 88);

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

					Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(26f, 30f).setLightLevel(s -> 0));

			setRegistryName("quashling_block");
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
