package net.mcreator.quashconomiestwo.procedures;

import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;
import net.mcreator.quashconomiestwo.QuashconomiestwoMod;

import java.util.Map;
import java.util.Collection;

@QuashconomiestwoModElements.ModElement.Tag
public class FluffalDolphinOnEntityTickUpdateProcedure extends QuashconomiestwoModElements.ModElement {
	public FluffalDolphinOnEntityTickUpdateProcedure(QuashconomiestwoModElements instance) {
		super(instance, 155);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				QuashconomiestwoMod.LOGGER.warn("Failed to load dependency entity for procedure FluffalDolphinOnEntityTickUpdate!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (((new Object() {
			boolean check(Entity _entity) {
				if (_entity instanceof LivingEntity) {
					Collection<EffectInstance> effects = ((LivingEntity) _entity).getActivePotionEffects();
					for (EffectInstance effect : effects) {
						if (effect.getPotion() == Effects.SLOW_FALLING)
							return true;
					}
				}
				return false;
			}
		}.check(entity)) != (true))) {
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, (int) 1000, (int) 1, (false), (false)));
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.DOLPHINS_GRACE, (int) 1000, (int) 2, (false), (false)));
		}
	}
}
