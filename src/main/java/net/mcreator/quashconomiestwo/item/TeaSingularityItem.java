
package net.mcreator.quashconomiestwo.item;

@QuashconomiestwoModElements.ModElement.Tag
public class TeaSingularityItem extends QuashconomiestwoModElements.ModElement {

	@ObjectHolder("quashconomiestwo:tea_singularity")
	public static final Item block = null;

	public TeaSingularityItem(QuashconomiestwoModElements instance) {
		super(instance, 131);

	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	public static class ItemCustom extends Item {

		public ItemCustom() {
			super(new Item.Properties().group(QuashconomiesItemGroup.tab).maxStackSize(64).rarity(Rarity.EPIC));
			setRegistryName("tea_singularity");
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
