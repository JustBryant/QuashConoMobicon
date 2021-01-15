
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
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

import net.mcreator.quashconomiestwo.item.QuashCentItem;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@QuashconomiestwoModElements.ModElement.Tag
public class MokeyMokeyEntity extends QuashconomiestwoModElements.ModElement {
	public static EntityType entity = null;
	public MokeyMokeyEntity(QuashconomiestwoModElements instance) {
		super(instance, 101);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 1.8f)).build("mokey_mokey")
						.setRegistryName("mokey_mokey");
		elements.entities.add(() -> entity);
		elements.items.add(
				() -> new SpawnEggItem(entity, -1, -13108, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("mokey_mokey_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
		boolean biomeCriteria = false;
		if (new ResourceLocation("quashconomiestwo:quashiome").equals(event.getName()))
			biomeCriteria = true;
		if (!biomeCriteria)
			return;
		event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, 20, 3, 4));
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
				return new MobRenderer(renderManager, new Modelmokey_mokey(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("quashconomiestwo:textures/mokey_mokey.png");
					}
				};
			});
		}
	}
	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 10);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 0);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 2);
		GlobalEntityTypeAttributes.put(entity, ammma.create());
	}
	public static class CustomEntity extends MonsterEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 0;
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
			this.entityDropItem(new ItemStack(QuashCentItem.block, (int) (1)));
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
			if (source == DamageSource.FALL)
				return false;
			return super.attackEntityFrom(source, amount);
		}
	}

	// Made with Blockbench 3.7.5
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	public static class Modelmokey_mokey extends EntityModel<Entity> {
		private final ModelRenderer body;
		private final ModelRenderer body_r1;
		private final ModelRenderer armsandlegs;
		private final ModelRenderer armsandlegs_r1;
		private final ModelRenderer question_mark;
		private final ModelRenderer wingsandmark_r1;
		private final ModelRenderer right_wing;
		private final ModelRenderer wingsandmark_r2;
		private final ModelRenderer left_wing;
		private final ModelRenderer wingsandmark_r3;
		public Modelmokey_mokey() {
			textureWidth = 128;
			textureHeight = 128;
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 4.0F, 0.0F);
			body_r1 = new ModelRenderer(this);
			body_r1.setRotationPoint(0.0F, 20.0F, 0.0F);
			body.addChild(body_r1);
			setRotationAngle(body_r1, 0.0F, -1.5708F, 0.0F);
			body_r1.setTextureOffset(0, 0).addBox(-3.0F, -29.0F, -7.0F, 7.0F, 17.0F, 16.0F, 0.0F, false);
			armsandlegs = new ModelRenderer(this);
			armsandlegs.setRotationPoint(-4.0F, 24.0F, 0.0F);
			armsandlegs_r1 = new ModelRenderer(this);
			armsandlegs_r1.setRotationPoint(4.0F, 0.0F, 0.0F);
			armsandlegs.addChild(armsandlegs_r1);
			setRotationAngle(armsandlegs_r1, 0.0F, -1.5708F, 0.0F);
			armsandlegs_r1.setTextureOffset(59, 0).addBox(-1.0F, -13.0F, 3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);
			armsandlegs_r1.setTextureOffset(60, 24).addBox(-1.0F, -13.0F, -4.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);
			armsandlegs_r1.setTextureOffset(27, 61).addBox(-1.0F, -25.0F, -9.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);
			armsandlegs_r1.setTextureOffset(41, 61).addBox(-1.0F, -25.0F, 8.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);
			question_mark = new ModelRenderer(this);
			question_mark.setRotationPoint(0.0F, 24.0F, 0.0F);
			wingsandmark_r1 = new ModelRenderer(this);
			wingsandmark_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
			question_mark.addChild(wingsandmark_r1);
			setRotationAngle(wingsandmark_r1, 0.0F, -1.5708F, 0.0F);
			wingsandmark_r1.setTextureOffset(11, 56).addBox(-1.0F, -34.0F, -1.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
			wingsandmark_r1.setTextureOffset(41, 41).addBox(-8.0F, -42.0F, 2.0F, 5.0F, 4.0F, 4.0F, 0.0F, false);
			wingsandmark_r1.setTextureOffset(22, 35).addBox(-8.0F, -42.0F, -7.0F, 7.0F, 4.0F, 4.0F, 0.0F, false);
			wingsandmark_r1.setTextureOffset(31, 0).addBox(-11.0F, -42.0F, -4.0F, 3.0F, 4.0F, 7.0F, 0.0F, false);
			wingsandmark_r1.setTextureOffset(0, 48).addBox(-10.0F, -42.0F, 0.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
			wingsandmark_r1.setTextureOffset(48, 8).addBox(-10.0F, -42.0F, -5.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
			wingsandmark_r1.setTextureOffset(0, 0).addBox(-9.0F, -42.0F, 1.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
			wingsandmark_r1.setTextureOffset(48, 18).addBox(-9.0F, -42.0F, -6.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
			wingsandmark_r1.setTextureOffset(29, 51).addBox(-3.0F, -42.0F, -6.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
			wingsandmark_r1.setTextureOffset(45, 51).addBox(-2.0F, -42.0F, -5.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
			wingsandmark_r1.setTextureOffset(0, 35).addBox(-1.0F, -42.0F, -4.0F, 3.0F, 4.0F, 7.0F, 0.0F, false);
			wingsandmark_r1.setTextureOffset(56, 56).addBox(-1.0F, -39.0F, -1.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
			right_wing = new ModelRenderer(this);
			right_wing.setRotationPoint(-7.5F, -7.0F, 5.875F);
			setRotationAngle(right_wing, 0.0F, -0.0436F, 0.0F);
			wingsandmark_r2 = new ModelRenderer(this);
			wingsandmark_r2.setRotationPoint(7.5F, 31.0F, -5.875F);
			right_wing.addChild(wingsandmark_r2);
			setRotationAngle(wingsandmark_r2, 0.0F, -1.5708F, 0.0F);
			wingsandmark_r2.setTextureOffset(56, 46).addBox(3.0F, -34.0F, 7.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
			wingsandmark_r2.setTextureOffset(16, 45).addBox(3.0F, -32.0F, 4.0F, 3.0F, 4.0F, 5.0F, 0.0F, false);
			wingsandmark_r2.setTextureOffset(45, 0).addBox(3.0F, -31.0F, 8.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
			wingsandmark_r2.setTextureOffset(61, 15).addBox(3.0F, -33.0F, 6.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
			left_wing = new ModelRenderer(this);
			left_wing.setRotationPoint(5.5F, -7.0F, 4.125F);
			wingsandmark_r3 = new ModelRenderer(this);
			wingsandmark_r3.setRotationPoint(-5.5F, 31.0F, -4.125F);
			left_wing.addChild(wingsandmark_r3);
			setRotationAngle(wingsandmark_r3, 0.0F, -1.5708F, 0.0F);
			wingsandmark_r3.setTextureOffset(56, 35).addBox(3.0F, -34.0F, -9.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
			wingsandmark_r3.setTextureOffset(42, 29).addBox(3.0F, -32.0F, -7.0F, 3.0F, 4.0F, 5.0F, 0.0F, false);
			wingsandmark_r3.setTextureOffset(0, 10).addBox(3.0F, -31.0F, -8.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
			wingsandmark_r3.setTextureOffset(28, 45).addBox(3.0F, -33.0F, -6.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			armsandlegs.render(matrixStack, buffer, packedLight, packedOverlay);
			question_mark.render(matrixStack, buffer, packedLight, packedOverlay);
			right_wing.render(matrixStack, buffer, packedLight, packedOverlay);
			left_wing.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.right_wing.rotateAngleX = f3 / (180F / (float) Math.PI);
			this.left_wing.rotateAngleX = f3 / (180F / (float) Math.PI);
		}
	}
}
