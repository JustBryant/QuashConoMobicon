
package net.mcreator.quashconomiestwo.item;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.AxeItem;

import net.mcreator.quashconomiestwo.itemgroup.QuashconomiesItemGroup;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

@QuashconomiestwoModElements.ModElement.Tag
public class QuashAxeItem extends QuashconomiestwoModElements.ModElement {
	@ObjectHolder("quashconomiestwo:quash_axe")
	public static final Item block = null;
	public QuashAxeItem(QuashconomiestwoModElements instance) {
		super(instance, 70);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new AxeItem(new IItemTier() {
			public int getMaxUses() {
				return 2600;
			}

			public float getEfficiency() {
				return 21f;
			}

			public float getAttackDamage() {
				return 10f;
			}

			public int getHarvestLevel() {
				return 4;
			}

			public int getEnchantability() {
				return 2;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(QuashTeaItem.block, (int) (1)));
			}
		}, 1, -3f, new Item.Properties().group(QuashconomiesItemGroup.tab)) {
			@Override
			@OnlyIn(Dist.CLIENT)
			public boolean hasEffect(ItemStack itemstack) {
				return true;
			}
		}.setRegistryName("quash_axe"));
	}
}
