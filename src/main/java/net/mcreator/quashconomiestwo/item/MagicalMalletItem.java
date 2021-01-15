
package net.mcreator.quashconomiestwo.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.mcreator.quashconomiestwo.itemgroup.QuashconomiesItemGroup;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

@QuashconomiestwoModElements.ModElement.Tag
public class MagicalMalletItem extends QuashconomiestwoModElements.ModElement {
	@ObjectHolder("quashconomiestwo:magical_mallet")
	public static final Item block = null;
	public MagicalMalletItem(QuashconomiestwoModElements instance) {
		super(instance, 140);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {
			public int getMaxUses() {
				return 1050;
			}

			public float getEfficiency() {
				return 12f;
			}

			public float getAttackDamage() {
				return 27f;
			}

			public int getHarvestLevel() {
				return 1;
			}

			public int getEnchantability() {
				return 10;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.EMPTY;
			}
		}, 3, -3.4f, new Item.Properties().group(QuashconomiesItemGroup.tab)) {
		}.setRegistryName("magical_mallet"));
	}
}