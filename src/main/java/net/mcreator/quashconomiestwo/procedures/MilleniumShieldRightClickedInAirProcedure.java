package net.mcreator.quashconomiestwo.procedures;

@QuashconomiestwoModElements.ModElement.Tag
public class MilleniumShieldRightClickedInAirProcedure extends QuashconomiestwoModElements.ModElement {

	public MilleniumShieldRightClickedInAirProcedure(QuashconomiestwoModElements instance) {
		super(instance, 141);

	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				QuashconomiestwoMod.LOGGER.warn("Failed to load dependency entity for procedure MilleniumShieldRightClickedInAir!");
			return;
		}

		Entity entity = (Entity) dependencies.get("entity");

		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.RESISTANCE, (int) 20, (int) 3));
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, (int) 20, (int) 3));

	}

}
