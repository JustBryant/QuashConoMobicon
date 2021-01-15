package net.mcreator.quashconomiestwo.procedures;

import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.quashconomiestwo.item.TeaSingularityItem;
import net.mcreator.quashconomiestwo.item.FusionAlloyItem;
import net.mcreator.quashconomiestwo.item.CondensedFusionAlloyItem;
import net.mcreator.quashconomiestwo.block.TeaBlockBlock;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;
import net.mcreator.quashconomiestwo.QuashconomiestwoMod;

import java.util.Map;

@QuashconomiestwoModElements.ModElement.Tag
public class TeaCompressorOnBlockRightClickedProcedure extends QuashconomiestwoModElements.ModElement {
	public TeaCompressorOnBlockRightClickedProcedure(QuashconomiestwoModElements instance) {
		super(instance, 127);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				QuashconomiestwoMod.LOGGER.warn("Failed to load dependency entity for procedure TeaCompressorOnBlockRightClicked!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(FusionAlloyItem.block, (int) (1)).getItem())) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(FusionAlloyItem.block, (int) (1));
				((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
						((PlayerEntity) entity).container.func_234641_j_());
			}
			if (entity instanceof PlayerEntity) {
				ItemStack _setstack = new ItemStack(CondensedFusionAlloyItem.block, (int) (1));
				_setstack.setCount((int) 1);
				ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
			}
		}
		if ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(CondensedFusionAlloyItem.block, (int) (1)).getItem())) {
			if (((entity instanceof PlayerEntity)
					? ((PlayerEntity) entity).inventory.hasItemStack(new ItemStack(TeaBlockBlock.block, (int) (1)))
					: false)) {
				if (entity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CondensedFusionAlloyItem.block, (int) (1));
					((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) entity).container.func_234641_j_());
				}
				if (entity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(TeaBlockBlock.block, (int) (1));
					((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) entity).container.func_234641_j_());
				}
				if (entity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(TeaSingularityItem.block, (int) (1));
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
				}
			}
		}
	}
}
