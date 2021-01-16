package net.mcreator.quashconomiestwo.procedures;

@QuashconomiestwoModElements.ModElement.Tag
public class FusionArmourBodyTickEventProcedure extends QuashconomiestwoModElements.ModElement {

	public FusionArmourBodyTickEventProcedure(QuashconomiestwoModElements instance) {
		super(instance, 121);

	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				QuashconomiestwoMod.LOGGER.warn("Failed to load dependency entity for procedure FusionArmourBodyTickEvent!");
			return;
		}

		Entity entity = (Entity) dependencies.get("entity");

		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.STRENGTH, (int) 60, (int) 9, (false), (false)));

	}

}
