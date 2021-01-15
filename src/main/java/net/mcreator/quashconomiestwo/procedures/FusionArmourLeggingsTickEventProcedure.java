package net.mcreator.quashconomiestwo.procedures;

@QuashconomiestwoModElements.ModElement.Tag
public class FusionArmourLeggingsTickEventProcedure extends QuashconomiestwoModElements.ModElement {

	public FusionArmourLeggingsTickEventProcedure(QuashconomiestwoModElements instance) {
		super(instance, 122);

	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				QuashconomiestwoMod.LOGGER.warn("Failed to load dependency entity for procedure FusionArmourLeggingsTickEvent!");
			return;
		}

		Entity entity = (Entity) dependencies.get("entity");

		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SPEED, (int) 60, (int) 6, (false), (false)));

	}

}
