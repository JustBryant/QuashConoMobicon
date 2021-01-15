
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

import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.World;
import net.minecraft.world.BossInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.entity.player.ServerPlayerEntity;
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
import net.minecraft.block.material.Material;

import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

import java.util.Random;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@QuashconomiestwoModElements.ModElement.Tag
public class MokeyMokeyKingEntity extends QuashconomiestwoModElements.ModElement {
	public static EntityType entity = null;
	public MokeyMokeyKingEntity(QuashconomiestwoModElements instance) {
		super(instance, 120);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(5f, 15f)).build("mokey_mokey_king")
						.setRegistryName("mokey_mokey_king");
		elements.entities.add(() -> entity);
		elements.items.add(
				() -> new SpawnEggItem(entity, -1, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("mokey_mokey_king_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
		boolean biomeCriteria = false;
		if (new ResourceLocation("nether_wastes").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("mushroom_fields").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("mushroom_field_shore").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("small_end_islands").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("end_midlands").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("end_highlands").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("end_barrens").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("soul_sand_valley").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("crimson_forest").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("warped_forest").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("basalt_deltas").equals(event.getName()))
			biomeCriteria = true;
		if (!biomeCriteria)
			return;
		event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity, 2, 1, 1));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(this::setupAttributes);
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos,
						random) -> (world.getBlockState(pos.down()).getMaterial() == Material.ORGANIC && world.getLightSubtracted(pos, 0) > 8));
	}
	private static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
				return new MobRenderer(renderManager, new Modelmokey_mokey_king(), 1f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("quashconomiestwo:textures/mokey_mokey_king.png");
					}
				};
			});
		}
	}
	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.4);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 550);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 5);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 10);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1);
		GlobalEntityTypeAttributes.put(entity, ammma.create());
	}
	public static class CustomEntity extends MonsterEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 125;
			setNoAI(false);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, true));
			this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 1));
			this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
			this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(5, new SwimGoal(this));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
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
			if (source == DamageSource.CACTUS)
				return false;
			if (source == DamageSource.DROWN)
				return false;
			return super.attackEntityFrom(source, amount);
		}

		@Override
		public boolean isNonBoss() {
			return false;
		}
		private final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS);
		@Override
		public void addTrackingPlayer(ServerPlayerEntity player) {
			super.addTrackingPlayer(player);
			this.bossInfo.addPlayer(player);
		}

		@Override
		public void removeTrackingPlayer(ServerPlayerEntity player) {
			super.removeTrackingPlayer(player);
			this.bossInfo.removePlayer(player);
		}

		@Override
		public void updateAITasks() {
			super.updateAITasks();
			this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
		}

		public void livingTick() {
			super.livingTick();
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Random random = this.rand;
			Entity entity = this;
			if (true)
				for (int l = 0; l < 8; ++l) {
					double d0 = (x + random.nextFloat());
					double d1 = (y + random.nextFloat());
					double d2 = (z + random.nextFloat());
					int i1 = random.nextInt(2) * 2 - 1;
					double d3 = (random.nextFloat() - 0.5D) * 1D;
					double d4 = (random.nextFloat() - 0.5D) * 1D;
					double d5 = (random.nextFloat() - 0.5D) * 1D;
					world.addParticle(ParticleTypes.EFFECT, d0, d1, d2, d3, d4, d5);
				}
		}
	}

	// Made with Blockbench 3.7.5
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	public static class Modelmokey_mokey_king extends EntityModel<Entity> {
		private final ModelRenderer right_wing;
		private final ModelRenderer body_r1;
		private final ModelRenderer left_wing;
		private final ModelRenderer body_r2;
		private final ModelRenderer body;
		private final ModelRenderer body_r3;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r1_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer cube_r2_r1;
		private final ModelRenderer cube_r3;
		private final ModelRenderer cube_r3_r1;
		private final ModelRenderer cube_r4;
		private final ModelRenderer cube_r4_r1;
		private final ModelRenderer cube_r5;
		private final ModelRenderer cube_r5_r1;
		private final ModelRenderer cube_r6;
		private final ModelRenderer cube_r6_r1;
		private final ModelRenderer cube_r7;
		private final ModelRenderer cube_r7_r1;
		private final ModelRenderer cube_r8;
		private final ModelRenderer cube_r8_r1;
		private final ModelRenderer cube_r9;
		private final ModelRenderer cube_r9_r1;
		private final ModelRenderer bb_main;
		private final ModelRenderer bb_main_r1;
		public Modelmokey_mokey_king() {
			textureWidth = 512;
			textureHeight = 512;
			right_wing = new ModelRenderer(this);
			right_wing.setRotationPoint(19.0F, -169.0F, 50.0F);
			body_r1 = new ModelRenderer(this);
			body_r1.setRotationPoint(-19.0F, 193.0F, -50.0F);
			right_wing.addChild(body_r1);
			setRotationAngle(body_r1, 0.0F, 0.0F, 0.0F);
			body_r1.setTextureOffset(338, 136).addBox(13.0F, -190.0F, 50.0F, 13.0F, 15.0F, 13.0F, 0.0F, false);
			body_r1.setTextureOffset(147, 329).addBox(13.0F, -207.0F, 34.0F, 13.0F, 15.0F, 13.0F, 0.0F, false);
			body_r1.setTextureOffset(201, 68).addBox(13.0F, -200.0F, 23.0F, 13.0F, 35.0F, 33.0F, 0.0F, false);
			body_r1.setTextureOffset(201, 0).addBox(13.0F, -219.0F, 41.0F, 13.0F, 35.0F, 33.0F, 0.0F, false);
			left_wing = new ModelRenderer(this);
			left_wing.setRotationPoint(19.0F, -169.0F, -57.0F);
			body_r2 = new ModelRenderer(this);
			body_r2.setRotationPoint(-19.0F, 193.0F, 57.0F);
			left_wing.addChild(body_r2);
			setRotationAngle(body_r2, 0.0F, 0.0F, 0.0F);
			body_r2.setTextureOffset(205, 328).addBox(13.0F, -206.0F, -61.0F, 13.0F, 15.0F, 15.0F, 0.0F, false);
			body_r2.setTextureOffset(0, 68).addBox(13.0F, -217.0F, -88.0F, 13.0F, 33.0F, 35.0F, 0.0F, false);
			body_r2.setTextureOffset(261, 328).addBox(13.0F, -190.0F, -78.0F, 13.0F, 15.0F, 15.0F, 0.0F, false);
			body_r2.setTextureOffset(0, 0).addBox(13.0F, -199.0F, -69.0F, 13.0F, 35.0F, 35.0F, 0.0F, false);
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 4.0F, 0.0F);
			body_r3 = new ModelRenderer(this);
			body_r3.setRotationPoint(0.0F, 20.0F, 0.0F);
			body.addChild(body_r3);
			setRotationAngle(body_r3, 0.0F, 0.0F, 0.0F);
			body_r3.setTextureOffset(0, 294).addBox(-7.0F, -242.0F, -14.0F, 15.0F, 34.0F, 15.0F, 0.0F, false);
			body_r3.setTextureOffset(325, 279).addBox(-7.0F, -197.0F, -14.0F, 15.0F, 15.0F, 15.0F, 0.0F, false);
			body_r3.setTextureOffset(0, 0).addBox(-15.0F, -174.0F, -75.0F, 32.0F, 157.0F, 137.0F, 0.0F, false);
			cube_r1 = new ModelRenderer(this);
			cube_r1.setRotationPoint(1.0F, -216.0F, 37.0F);
			body.addChild(cube_r1);
			setRotationAngle(cube_r1, 1.5708F, 0.0F, 0.0F);
			cube_r1_r1 = new ModelRenderer(this);
			cube_r1_r1.setRotationPoint(-1.0F, 236.0F, -37.0F);
			cube_r1.addChild(cube_r1_r1);
			setRotationAngle(cube_r1_r1, 0.0F, 0.0F, 0.0F);
			cube_r1_r1.setTextureOffset(61, 0).addBox(-31.0F, -272.0F, 28.0F, 18.0F, 16.0F, 15.0F, 0.0F, false);
			cube_r2 = new ModelRenderer(this);
			cube_r2.setRotationPoint(-4.0F, -216.0F, -18.0F);
			body.addChild(cube_r2);
			setRotationAngle(cube_r2, 1.5708F, 0.0F, 0.0F);
			cube_r2_r1 = new ModelRenderer(this);
			cube_r2_r1.setRotationPoint(4.0F, 236.0F, 18.0F);
			cube_r2.addChild(cube_r2_r1);
			setRotationAngle(cube_r2_r1, 0.0F, 0.0F, 0.0F);
			cube_r2_r1.setTextureOffset(260, 0).addBox(-36.0F, -272.0F, -27.0F, 32.0F, 16.0F, 15.0F, 0.0F, false);
			cube_r3 = new ModelRenderer(this);
			cube_r3.setRotationPoint(-25.0F, -216.0F, 27.0F);
			body.addChild(cube_r3);
			setRotationAngle(cube_r3, 1.5708F, 0.0F, 0.0F);
			cube_r3_r1 = new ModelRenderer(this);
			cube_r3_r1.setRotationPoint(25.0F, 236.0F, -27.0F);
			cube_r3.addChild(cube_r3_r1);
			setRotationAngle(cube_r3_r1, 0.0F, 0.0F, 0.0F);
			cube_r3_r1.setTextureOffset(220, 294).addBox(-40.0F, -270.0F, 18.0F, 15.0F, 19.0F, 15.0F, 0.0F, false);
			cube_r4 = new ModelRenderer(this);
			cube_r4.setRotationPoint(-25.0F, -216.0F, -16.0F);
			body.addChild(cube_r4);
			setRotationAngle(cube_r4, 1.5708F, 0.0F, 0.0F);
			cube_r4_r1 = new ModelRenderer(this);
			cube_r4_r1.setRotationPoint(25.0F, 236.0F, 16.0F);
			cube_r4.addChild(cube_r4_r1);
			setRotationAngle(cube_r4_r1, 0.0F, 0.0F, 0.0F);
			cube_r4_r1.setTextureOffset(325, 325).addBox(-15.0F, -270.0F, -25.0F, 15.0F, 19.0F, 15.0F, 0.0F, false);
			cube_r4_r1.setTextureOffset(280, 294).addBox(-40.0F, -270.0F, -25.0F, 15.0F, 19.0F, 15.0F, 0.0F, false);
			cube_r5 = new ModelRenderer(this);
			cube_r5.setRotationPoint(-29.0F, -216.0F, 21.0F);
			body.addChild(cube_r5);
			setRotationAngle(cube_r5, 1.5708F, 0.0F, 0.0F);
			cube_r5_r1 = new ModelRenderer(this);
			cube_r5_r1.setRotationPoint(29.0F, 236.0F, -21.0F);
			cube_r5.addChild(cube_r5_r1);
			setRotationAngle(cube_r5_r1, 0.0F, 0.0F, 0.0F);
			cube_r5_r1.setTextureOffset(61, 68).addBox(-44.0F, -270.0F, 12.0F, 15.0F, 20.0F, 15.0F, 0.0F, false);
			cube_r6 = new ModelRenderer(this);
			cube_r6.setRotationPoint(-29.0F, -216.0F, -10.0F);
			body.addChild(cube_r6);
			setRotationAngle(cube_r6, 1.5708F, 0.0F, 0.0F);
			cube_r6_r1 = new ModelRenderer(this);
			cube_r6_r1.setRotationPoint(29.0F, 236.0F, 10.0F);
			cube_r6.addChild(cube_r6_r1);
			setRotationAngle(cube_r6_r1, 0.0F, 0.0F, 0.0F);
			cube_r6_r1.setTextureOffset(160, 294).addBox(-44.0F, -270.0F, -19.0F, 15.0F, 20.0F, 15.0F, 0.0F, false);
			cube_r7 = new ModelRenderer(this);
			cube_r7.setRotationPoint(-34.0F, -216.0F, -5.0F);
			body.addChild(cube_r7);
			setRotationAngle(cube_r7, 1.5708F, 0.0F, 0.0F);
			cube_r7_r1 = new ModelRenderer(this);
			cube_r7_r1.setRotationPoint(34.0F, 236.0F, 5.0F);
			cube_r7.addChild(cube_r7_r1);
			setRotationAngle(cube_r7_r1, 0.0F, 0.0F, 0.0F);
			cube_r7_r1.setTextureOffset(293, 87).addBox(-7.0F, -270.0F, -14.0F, 15.0F, 34.0F, 15.0F, 0.0F, false);
			cube_r7_r1.setTextureOffset(293, 31).addBox(-49.0F, -270.0F, -14.0F, 15.0F, 41.0F, 15.0F, 0.0F, false);
			cube_r8 = new ModelRenderer(this);
			cube_r8.setRotationPoint(1.0F, -148.243F, -63.6676F);
			body.addChild(cube_r8);
			setRotationAngle(cube_r8, -0.5672F, 0.0F, 0.0F);
			cube_r8_r1 = new ModelRenderer(this);
			cube_r8_r1.setRotationPoint(-1.0F, 168.243F, 63.6676F);
			cube_r8.addChild(cube_r8_r1);
			setRotationAngle(cube_r8_r1, 0.0F, 0.0F, 0.0F);
			cube_r8_r1.setTextureOffset(60, 294).addBox(-6.0F, -146.913F, -63.6676F, 13.0F, 37.0F, 12.0F, 0.0F, false);
			cube_r9 = new ModelRenderer(this);
			cube_r9.setRotationPoint(1.0F, -79.913F, 77.1324F);
			body.addChild(cube_r9);
			setRotationAngle(cube_r9, 0.5672F, 0.0F, 0.0F);
			cube_r9_r1 = new ModelRenderer(this);
			cube_r9_r1.setRotationPoint(-1.0F, 99.913F, -77.1324F);
			cube_r9.addChild(cube_r9_r1);
			setRotationAngle(cube_r9_r1, 0.0F, 0.0F, 0.0F);
			cube_r9_r1.setTextureOffset(110, 294).addBox(-6.0F, -146.913F, 77.1324F, 13.0F, 36.0F, 12.0F, 0.0F, false);
			bb_main = new ModelRenderer(this);
			bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
			bb_main_r1 = new ModelRenderer(this);
			bb_main_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
			bb_main.addChild(bb_main_r1);
			setRotationAngle(bb_main_r1, 0.0F, 0.0F, 0.0F);
			bb_main_r1.setTextureOffset(96, 31).addBox(-10.0F, -17.0F, -46.0F, 10.0F, 15.0F, 9.0F, 0.0F, false);
			bb_main_r1.setTextureOffset(96, 103).addBox(-10.0F, -17.0F, 24.0F, 10.0F, 15.0F, 9.0F, 0.0F, false);
		}

		@Override
		public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			// previously the render function, render code was moved to a method below
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			right_wing.render(matrixStack, buffer, packedLight, packedOverlay);
			left_wing.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	}
}
