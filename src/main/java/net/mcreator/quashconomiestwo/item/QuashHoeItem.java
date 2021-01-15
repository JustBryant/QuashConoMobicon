
package net.mcreator.quashconomiestwo.item;

@QuashconomiestwoModElements.ModElement.Tag
public class QuashHoeItem extends QuashconomiestwoModElements.ModElement {

	@ObjectHolder("quashconomiestwo:quash_hoe")
	public static final Item block = null;

	public QuashHoeItem(QuashconomiestwoModElements instance) {
		super(instance, 113);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new HoeItem(new IItemTier() {
			public int getMaxUses() {
				return 2600;
			}

			public float getEfficiency() {
				return 14f;
			}

			public float getAttackDamage() {
				return 4f;
			}

			public int getHarvestLevel() {
				return 4;
			}

			public int getEnchantability() {
				return 26;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(QuashTeaItem.block, (int) (1)));
			}
		}, 0, -2.6f, new Item.Properties().group(QuashconomiesItemGroup.tab)) {

			@Override
			@OnlyIn(Dist.CLIENT)
			public boolean hasEffect(ItemStack itemstack) {
				return true;
			}

		}.setRegistryName("quash_hoe"));
	}

}
