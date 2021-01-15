
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
import net.mcreator.quashconomiestwo.block.TeaBlockBlock;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

@QuashconomiestwoModElements.ModElement.Tag
public class EnhancedQuashBladeItem extends QuashconomiestwoModElements.ModElement {
	@ObjectHolder("quashconomiestwo:enhanced_quash_blade")
	public static final Item block = null;
	public EnhancedQuashBladeItem(QuashconomiestwoModElements instance) {
		super(instance, 114);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {
			public int getMaxUses() {
				return 5200;
			}

			public float getEfficiency() {
				return 12f;
			}

			public float getAttackDamage() {
				return 34f;
			}

			public int getHarvestLevel() {
				return 4;
			}

			public int getEnchantability() {
				return 52;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(TeaBlockBlock.block, (int) (1)));
			}
		}, 3, 0f, new Item.Properties().group(QuashconomiesItemGroup.tab)) {
			@Override
			@OnlyIn(Dist.CLIENT)
			public boolean hasEffect(ItemStack itemstack) {
				return true;
			}
		}.setRegistryName("enhanced_quash_blade"));
	}
}
