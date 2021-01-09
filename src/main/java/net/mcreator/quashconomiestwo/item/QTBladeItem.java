
package net.mcreator.quashconomiestwo.item;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.mcreator.quashconomiestwo.itemgroup.QuashconomiesItemGroup;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

@QuashconomiestwoModElements.ModElement.Tag
public class QTBladeItem extends QuashconomiestwoModElements.ModElement {
	@ObjectHolder("quashconomiestwo:qt_blade")
	public static final Item block = null;
	public QTBladeItem(QuashconomiestwoModElements instance) {
		super(instance, 33);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {
			public int getMaxUses() {
				return 2900;
			}

			public float getEfficiency() {
				return 12f;
			}

			public float getAttackDamage() {
				return 14f;
			}

			public int getHarvestLevel() {
				return 1;
			}

			public int getEnchantability() {
				return 24;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(QuashTeaItem.block, (int) (1)));
			}
		}, 3, -0.5f, new Item.Properties().group(QuashconomiesItemGroup.tab)) {
			@Override
			@OnlyIn(Dist.CLIENT)
			public boolean hasEffect(ItemStack itemstack) {
				return true;
			}
		}.setRegistryName("qt_blade"));
	}
}
