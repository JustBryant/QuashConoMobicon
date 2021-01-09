package net.mcreator.quashconomiestwo.procedures;

import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;
import net.mcreator.quashconomiestwo.QuashconomiestwoMod;

import java.util.Map;

@QuashconomiestwoModElements.ModElement.Tag
public class QuashGodItemInInventoryTickProcedure extends QuashconomiestwoModElements.ModElement {
	public QuashGodItemInInventoryTickProcedure(QuashconomiestwoModElements instance) {
		super(instance, 44);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				QuashconomiestwoMod.LOGGER.warn("Failed to load dependency entity for procedure QuashGodItemInInventoryTick!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.STRENGTH, (int) 5, (int) 1, (false), (false)));
	}
}
