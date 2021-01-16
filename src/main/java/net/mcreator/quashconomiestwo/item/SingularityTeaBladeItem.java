
package net.mcreator.quashconomiestwo.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.mcreator.quashconomiestwo.itemgroup.QuashconomiesItemGroup;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

@QuashconomiestwoModElements.ModElement.Tag
public class SingularityTeaBladeItem extends QuashconomiestwoModElements.ModElement {
	@ObjectHolder("quashconomiestwo:singularity_tea_blade")
	public static final Item block = null;
	public SingularityTeaBladeItem(QuashconomiestwoModElements instance) {
		super(instance, 130);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {
			public int getMaxUses() {
				return 50000;
			}

			public float getEfficiency() {
				return 12f;
			}

			public float getAttackDamage() {
				return 162f;
			}

			public int getHarvestLevel() {
				return 4;
			}

			public int getEnchantability() {
				return 68;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.EMPTY;
			}
		}, 3, 1f, new Item.Properties().group(QuashconomiesItemGroup.tab)) {
		}.setRegistryName("singularity_tea_blade"));
	}
}
