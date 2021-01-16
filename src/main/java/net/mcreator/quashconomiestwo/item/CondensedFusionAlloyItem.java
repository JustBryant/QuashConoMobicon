
package net.mcreator.quashconomiestwo.item;

@QuashconomiestwoModElements.ModElement.Tag
public class CondensedFusionAlloyItem extends QuashconomiestwoModElements.ModElement {

	@ObjectHolder("quashconomiestwo:condensed_fusion_alloy")
	public static final Item block = null;

	public CondensedFusionAlloyItem(QuashconomiestwoModElements instance) {
		super(instance, 126);

	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	public static class ItemCustom extends Item {

		public ItemCustom() {
			super(new Item.Properties().group(QuashconomiesItemGroup.tab).maxStackSize(64).rarity(Rarity.EPIC));
			setRegistryName("condensed_fusion_alloy");
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean hasEffect(ItemStack itemstack) {
			return true;
		}

	}

}
