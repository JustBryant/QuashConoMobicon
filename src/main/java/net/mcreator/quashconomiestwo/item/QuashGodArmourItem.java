
package net.mcreator.quashconomiestwo.item;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ArmorItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.quashconomiestwo.procedures.QuashGodArmourLeggingsTickEventProcedure;
import net.mcreator.quashconomiestwo.procedures.QuashGodArmourHelmetTickEventProcedure;
import net.mcreator.quashconomiestwo.procedures.QuashGodArmourBootsTickEventProcedure;
import net.mcreator.quashconomiestwo.procedures.QuashGodArmourBodyTickEventProcedure;
import net.mcreator.quashconomiestwo.itemgroup.QuashconomiesItemGroup;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

import java.util.Map;
import java.util.HashMap;

@QuashconomiestwoModElements.ModElement.Tag
public class QuashGodArmourItem extends QuashconomiestwoModElements.ModElement {
	@ObjectHolder("quashconomiestwo:quash_god_armour_helmet")
	public static final Item helmet = null;
	@ObjectHolder("quashconomiestwo:quash_god_armour_chestplate")
	public static final Item body = null;
	@ObjectHolder("quashconomiestwo:quash_god_armour_leggings")
	public static final Item legs = null;
	@ObjectHolder("quashconomiestwo:quash_god_armour_boots")
	public static final Item boots = null;
	public QuashGodArmourItem(QuashconomiestwoModElements instance) {
		super(instance, 62);
	}

	@Override
	public void initElements() {
		IArmorMaterial armormaterial = new IArmorMaterial() {
			@Override
			public int getDurability(EquipmentSlotType slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 64;
			}

			@Override
			public int getDamageReductionAmount(EquipmentSlotType slot) {
				return new int[]{6, 10, 12, 6}[slot.getIndex()];
			}

			@Override
			public int getEnchantability() {
				return 35;
			}

			@Override
			public net.minecraft.util.SoundEvent getSoundEvent() {
				return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
			}

			@Override
			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(TheQuashGodItem.block, (int) (1)));
			}

			@OnlyIn(Dist.CLIENT)
			@Override
			public String getName() {
				return "quash_god_armour";
			}

			@Override
			public float getToughness() {
				return 8f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.5f;
			}
		};
		elements.items.add(() -> new ArmorItem(armormaterial, EquipmentSlotType.HEAD, new Item.Properties().group(QuashconomiesItemGroup.tab)) {
			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "quashconomiestwo:textures/models/armor/qg__layer_" + (slot == EquipmentSlotType.LEGS ? "2" : "1") + ".png";
			}

			@Override
			public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
				super.onArmorTick(itemstack, world, entity);
				double x = entity.getPosX();
				double y = entity.getPosY();
				double z = entity.getPosZ();
				{
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("entity", entity);
					QuashGodArmourHelmetTickEventProcedure.executeProcedure($_dependencies);
				}
			}
		}.setRegistryName("quash_god_armour_helmet"));
		elements.items.add(() -> new ArmorItem(armormaterial, EquipmentSlotType.CHEST, new Item.Properties().group(QuashconomiesItemGroup.tab)) {
			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "quashconomiestwo:textures/models/armor/qg__layer_" + (slot == EquipmentSlotType.LEGS ? "2" : "1") + ".png";
			}

			@Override
			public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
				double x = entity.getPosX();
				double y = entity.getPosY();
				double z = entity.getPosZ();
				{
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("entity", entity);
					QuashGodArmourBodyTickEventProcedure.executeProcedure($_dependencies);
				}
			}
		}.setRegistryName("quash_god_armour_chestplate"));
		elements.items.add(() -> new ArmorItem(armormaterial, EquipmentSlotType.LEGS, new Item.Properties().group(QuashconomiesItemGroup.tab)) {
			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "quashconomiestwo:textures/models/armor/qg__layer_" + (slot == EquipmentSlotType.LEGS ? "2" : "1") + ".png";
			}

			@Override
			public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
				double x = entity.getPosX();
				double y = entity.getPosY();
				double z = entity.getPosZ();
				{
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("entity", entity);
					QuashGodArmourLeggingsTickEventProcedure.executeProcedure($_dependencies);
				}
			}
		}.setRegistryName("quash_god_armour_leggings"));
		elements.items.add(() -> new ArmorItem(armormaterial, EquipmentSlotType.FEET, new Item.Properties().group(QuashconomiesItemGroup.tab)) {
			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "quashconomiestwo:textures/models/armor/qg__layer_" + (slot == EquipmentSlotType.LEGS ? "2" : "1") + ".png";
			}

			@Override
			public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
				double x = entity.getPosX();
				double y = entity.getPosY();
				double z = entity.getPosZ();
				{
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("entity", entity);
					QuashGodArmourBootsTickEventProcedure.executeProcedure($_dependencies);
				}
			}
		}.setRegistryName("quash_god_armour_boots"));
	}
}
