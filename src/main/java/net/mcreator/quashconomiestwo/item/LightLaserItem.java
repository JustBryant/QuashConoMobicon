
package net.mcreator.quashconomiestwo.item;

@QuashconomiestwoModElements.ModElement.Tag
public class LightLaserItem extends QuashconomiestwoModElements.ModElement {

	@ObjectHolder("quashconomiestwo:light_laser")
	public static final Item block = null;

	public LightLaserItem(QuashconomiestwoModElements instance) {
		super(instance, 121);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {
			public int getMaxUses() {
				return 0;
			}

			public float getEfficiency() {
				return 4f;
			}

			public float getAttackDamage() {
				return 2f;
			}

			public int getHarvestLevel() {
				return 1;
			}

			public int getEnchantability() {
				return 2;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.EMPTY;
			}
		}, 3, -3f, new Item.Properties().group(QuashconomiesItemGroup.tab)) {

			@Override
			public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity entity, Hand hand) {
				ActionResult<ItemStack> retval = super.onItemRightClick(world, entity, hand);
				ItemStack itemstack = retval.getResult();
				double x = entity.getPosX();
				double y = entity.getPosY();
				double z = entity.getPosZ();
				{
					Map<String, Object> $_dependencies = new HashMap<>();

					$_dependencies.put("entity", entity);

					LightLaserRightClickedInAirProcedure.executeProcedure($_dependencies);
				}
				return retval;
			}

		}.setRegistryName("light_laser"));
	}

}
