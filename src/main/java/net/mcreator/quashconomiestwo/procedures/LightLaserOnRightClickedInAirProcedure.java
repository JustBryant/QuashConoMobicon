package net.mcreator.quashconomiestwo.procedures;

@QuashconomiestwoModElements.ModElement.Tag
public class LightLaserOnRightClickedInAirProcedure extends QuashconomiestwoModElements.ModElement {

	public LightLaserOnRightClickedInAirProcedure(QuashconomiestwoModElements instance) {
		super(instance, 122);

	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				QuashconomiestwoMod.LOGGER.warn("Failed to load dependency entity for procedure LightLaserOnRightClickedInAir!");
			return;
		}

		Entity entity = (Entity) dependencies.get("entity");

		if (entity instanceof LivingEntity) {
			ItemStack _setstack = new ItemStack(LightLaserItem.block, (int) (1));
			_setstack.setCount((int) 1);
			((LivingEntity) entity).setHeldItem(Hand.MAIN_HAND, _setstack);
			if (entity instanceof ServerPlayerEntity)
				((ServerPlayerEntity) entity).inventory.markDirty();
		}

	}

}
