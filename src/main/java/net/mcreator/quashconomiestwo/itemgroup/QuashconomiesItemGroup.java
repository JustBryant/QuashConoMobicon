
package net.mcreator.quashconomiestwo.itemgroup;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

import net.mcreator.quashconomiestwo.item.QuashTeaItem;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

@QuashconomiestwoModElements.ModElement.Tag
public class QuashconomiesItemGroup extends QuashconomiestwoModElements.ModElement {
	public QuashconomiesItemGroup(QuashconomiestwoModElements instance) {
		super(instance, 3);
	}

	@Override
	public void initElements() {
		tab = new ItemGroup("tabquashconomies") {
			@OnlyIn(Dist.CLIENT)
			@Override
			public ItemStack createIcon() {
				return new ItemStack(QuashTeaItem.block, (int) (1));
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return true;
			}
		}.setBackgroundImageName("item_search.png");
	}
	public static ItemGroup tab;
}
