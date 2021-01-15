
package net.mcreator.quashconomiestwo.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@QuashconomiestwoModElements.ModElement.Tag
public class PenguinSoldierEntity extends QuashconomiestwoModElements.ModElement {
	public static EntityType entity = null;
	public PenguinSoldierEntity(QuashconomiestwoModElements instance) {
		super(instance, 100);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 1.8f)).build("penguin_soldier")
						.setRegistryName("penguin_soldier");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -10066177, -52429, new Item.Properties().group(ItemGroup.MISC))
				.setRegistryName("penguin_soldier_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
		boolean biomeCriteria = false;
		if (new ResourceLocation("ocean").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("quashconomiestwo:quashiome").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("river").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("swamp").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("frozen_ocean").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("frozen_river").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("snowy_tundra").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("snowy_mountains").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("deep_ocean").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("snowy_mountains").equals(event.getName()))
			biomeCriteria = true;
		if (!biomeCriteria)
			return;
		event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, 10, 1, 6));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(this::setupAttributes);
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				MonsterEntity::canMonsterSpawn);
	}
	private static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
				return new MobRenderer(renderManager, new Modelpenguin_soldier(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("quashconomiestwo:textures/penguin_soldier.png");
					}
				};
			});
		}
	}
	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.6);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 25);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 0);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 6);
		GlobalEntityTypeAttributes.put(entity, ammma.create());
	}
	public static class CustomEntity extends MonsterEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 5;
			setNoAI(false);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
			this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 1));
			this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
			this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(5, new SwimGoal(this));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
			super.dropSpecialItems(source, looting, recentlyHitIn);
			this.entityDropItem(new ItemStack(Items.FEATHER, (int) (1)));
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (source == DamageSource.DROWN)
				return false;
			return super.attackEntityFrom(source, amount);
		}
	}

	// Made with Blockbench 3.7.5
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	public static class Modelpenguin_soldier extends EntityModel<Entity> {
		private final ModelRenderer leftfoot;
		private final ModelRenderer rightfoot;
		private final ModelRenderer body;
		private final ModelRenderer rightflipper;
		private final ModelRenderer leftflipper;
		private final ModelRenderer Head;
		private final ModelRenderer leftpauldron;
		private final ModelRenderer rightpauldron;
		private final ModelRenderer bb_main;
		public Modelpenguin_soldier() {
			textureWidth = 64;
			textureHeight = 64;
			leftfoot = new ModelRenderer(this);
			leftfoot.setRotationPoint(4.5F, 22.375F, 7.5F);
			leftfoot.setTextureOffset(14, 49).addBox(-2.5F, -0.375F, -1.5F, 5.0F, 2.0F, 3.0F, 0.0F, false);
			leftfoot.setTextureOffset(35, 50).addBox(1.5F, 0.625F, -4.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);
			leftfoot.setTextureOffset(30, 49).addBox(-0.5F, 0.625F, -4.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);
			leftfoot.setTextureOffset(9, 45).addBox(-2.5F, 0.625F, -4.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);
			rightfoot = new ModelRenderer(this);
			rightfoot.setRotationPoint(0.0F, 24.0F, 6.0F);
			rightfoot.setTextureOffset(48, 35).addBox(-7.0F, -2.0F, 0.0F, 5.0F, 2.0F, 3.0F, 0.0F, false);
			rightfoot.setTextureOffset(37, 5).addBox(-3.0F, -1.0F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
			rightfoot.setTextureOffset(0, 4).addBox(-5.0F, -1.0F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
			rightfoot.setTextureOffset(0, 0).addBox(-7.0F, -1.0F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 24.0F, 0.0F);
			body.setTextureOffset(0, 0).addBox(-7.0F, -15.0F, 3.0F, 14.0F, 13.0F, 9.0F, 0.0F, false);
			rightflipper = new ModelRenderer(this);
			rightflipper.setRotationPoint(-8.0F, 10.0F, 7.5F);
			rightflipper.setTextureOffset(46, 0).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 8.0F, 5.0F, 0.0F, false);
			leftflipper = new ModelRenderer(this);
			leftflipper.setRotationPoint(8.0F, 10.0F, 7.5F);
			leftflipper.setTextureOffset(0, 45).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 8.0F, 5.0F, 0.0F, false);
			Head = new ModelRenderer(this);
			Head.setRotationPoint(0.0F, 6.25F, 7.25F);
			Head.setTextureOffset(0, 22).addBox(-4.0F, -4.25F, -3.25F, 8.0F, 7.0F, 7.0F, 0.0F, false);
			Head.setTextureOffset(44, 30).addBox(-4.0F, -0.25F, -5.25F, 8.0F, 2.0F, 2.0F, 0.0F, false);
			leftpauldron = new ModelRenderer(this);
			leftpauldron.setRotationPoint(-4.75F, 21.25F, 0.0F);
			setRotationAngle(leftpauldron, 0.0F, 0.0F, 0.3054F);
			leftpauldron.setTextureOffset(44, 24).addBox(5.0F, -18.0F, 5.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);
			leftpauldron.setTextureOffset(0, 36).addBox(4.0F, -17.0F, 4.0F, 7.0F, 2.0F, 7.0F, 0.0F, false);
			leftpauldron.setTextureOffset(41, 41).addBox(4.0F, -15.0F, 4.0F, 3.0F, 1.0F, 7.0F, 0.0F, false);
			rightpauldron = new ModelRenderer(this);
			rightpauldron.setRotationPoint(-7.25F, 25.75F, 0.0F);
			setRotationAngle(rightpauldron, 0.0F, 0.0F, -0.4363F);
			rightpauldron.setTextureOffset(23, 23).addBox(5.0F, -18.0F, 5.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);
			rightpauldron.setTextureOffset(23, 29).addBox(4.0F, -17.0F, 4.0F, 7.0F, 2.0F, 7.0F, 0.0F, false);
			rightpauldron.setTextureOffset(21, 38).addBox(7.9523F, -16.9355F, 4.0F, 3.0F, 4.0F, 7.0F, 0.0F, false);
			bb_main = new ModelRenderer(this);
			bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
			bb_main.setTextureOffset(38, 24).addBox(-2.0F, -5.0F, 12.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			leftfoot.render(matrixStack, buffer, packedLight, packedOverlay);
			rightfoot.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			rightflipper.render(matrixStack, buffer, packedLight, packedOverlay);
			leftflipper.render(matrixStack, buffer, packedLight, packedOverlay);
			Head.render(matrixStack, buffer, packedLight, packedOverlay);
			leftpauldron.render(matrixStack, buffer, packedLight, packedOverlay);
			rightpauldron.render(matrixStack, buffer, packedLight, packedOverlay);
			bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.leftfoot.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.Head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.Head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.rightflipper.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
			this.rightfoot.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.leftflipper.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
		}
	}
}
