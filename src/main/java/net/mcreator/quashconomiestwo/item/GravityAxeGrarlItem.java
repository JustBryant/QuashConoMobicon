
package net.mcreator.quashconomiestwo.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.AxeItem;

import net.mcreator.quashconomiestwo.itemgroup.QuashconomiesItemGroup;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

@QuashconomiestwoModElements.ModElement.Tag
public class GravityAxeGrarlItem extends QuashconomiestwoModElements.ModElement {
	@ObjectHolder("quashconomiestwo:gravity_axe_grarl")
	public static final Item block = null;
	public GravityAxeGrarlItem(QuashconomiestwoModElements instance) {
		super(instance, 139);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new AxeItem(new IItemTier() {
			public int getMaxUses() {
				return 1050;
			}

			public float getEfficiency() {
				return 12f;
			}

			public float getAttackDamage() {
				return 22f;
			}

			public int getHarvestLevel() {
				return 3;
			}

			public int getEnchantability() {
				return 2;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.EMPTY;
			}
		}, 1, -3.2f, new Item.Properties().group(QuashconomiesItemGroup.tab)) {
		}.setRegistryName("gravity_axe_grarl"));
	}
}
