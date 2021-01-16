package net.mcreator.quashconomiestwo.procedures;

@QuashconomiestwoModElements.ModElement.Tag
public class LightLaserRightClickedInAirProcedure extends QuashconomiestwoModElements.ModElement {

	public LightLaserRightClickedInAirProcedure(QuashconomiestwoModElements instance) {
		super(instance, 121);

	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				QuashconomiestwoMod.LOGGER.warn("Failed to load dependency entity for procedure LightLaserRightClickedInAir!");
			return;
		}

		Entity entity = (Entity) dependencies.get("entity");

		if (entity instanceof LivingEntity) {
			ItemStack _setstack = new ItemStack(LightLaserOnItem.block, (int) (1));
			_setstack.setCount((int) 1);
			((LivingEntity) entity).setHeldItem(Hand.MAIN_HAND, _setstack);
			if (entity instanceof ServerPlayerEntity)
				((ServerPlayerEntity) entity).inventory.markDirty();
		}

	}

}
