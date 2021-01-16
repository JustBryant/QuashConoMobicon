
package net.mcreator.quashconomiestwo.item;

@QuashconomiestwoModElements.ModElement.Tag
public class GravityAxeGrarlItem extends QuashconomiestwoModElements.ModElement {

	@ObjectHolder("quashconomiestwo:gravity_axe_grarl")
	public static final Item block = null;

	public GravityAxeGrarlItem(QuashconomiestwoModElements instance) {
		super(instance, 139);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new AxeItem(new IItemTier() {
			public int getMaxUses() {
				return 1050;
			}

			public float getEfficiency() {
				return 12f;
			}

			public float getAttackDamage() {
				return 22f;
			}

			public int getHarvestLevel() {
				return 3;
			}

			public int getEnchantability() {
				return 2;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.EMPTY;
			}
		}, 1, -3.2f, new Item.Properties().group(QuashconomiesItemGroup.tab)) {

		}.setRegistryName("gravity_axe_grarl"));
	}

}
