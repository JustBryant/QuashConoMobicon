
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
public class FlyingPenguinEntity extends QuashconomiestwoModElements.ModElement {
	public static EntityType entity = null;
	public FlyingPenguinEntity(QuashconomiestwoModElements instance) {
		super(instance, 103);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 1.8f)).build("flying_penguin")
						.setRegistryName("flying_penguin");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -13421569, -1, new Item.Properties().group(ItemGroup.MISC))
				.setRegistryName("flying_penguin_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
		event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, 20, 4, 4));
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
				return new MobRenderer(renderManager, new Modelflying_penguin(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("quashconomiestwo:textures/flying_penguin.png");
					}
				};
			});
		}
	}
	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 10);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 0);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 3);
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

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
		}
	}

	// Made with Blockbench 3.7.5
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	public static class Modelflying_penguin extends EntityModel<Entity> {
		private final ModelRenderer ArmRight;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer cube_r3;
		private final ModelRenderer cube_r4;
		private final ModelRenderer ArmLeft;
		private final ModelRenderer cube_r5;
		private final ModelRenderer cube_r6;
		private final ModelRenderer cube_r7;
		private final ModelRenderer cube_r8;
		private final ModelRenderer Body;
		private final ModelRenderer Head;
		private final ModelRenderer cube_r9;
		private final ModelRenderer cube_r10;
		private final ModelRenderer cube_r11;
		private final ModelRenderer cube_r12;
		private final ModelRenderer cube_r13;
		private final ModelRenderer cube_r14;
		private final ModelRenderer cube_r15;
		private final ModelRenderer cube_r16;
		private final ModelRenderer cube_r17;
		private final ModelRenderer cube_r18;
		private final ModelRenderer Ears;
		private final ModelRenderer cube_r19;
		private final ModelRenderer cube_r20;
		private final ModelRenderer cube_r21;
		private final ModelRenderer cube_r22;
		private final ModelRenderer cube_r23;
		private final ModelRenderer cube_r24;
		private final ModelRenderer cube_r25;
		private final ModelRenderer cube_r26;
		private final ModelRenderer LeftFoot;
		private final ModelRenderer RightFoot;
		private final ModelRenderer Sword;
		private final ModelRenderer cube_r27;
		private final ModelRenderer cube_r28;
		private final ModelRenderer cube_r29;
		private final ModelRenderer cube_r30;
		private final ModelRenderer cube_r31;
		private final ModelRenderer cube_r32;
		private final ModelRenderer cube_r33;
		private final ModelRenderer cube_r34;
		public Modelflying_penguin() {
			textureWidth = 75;
			textureHeight = 75;
			ArmRight = new ModelRenderer(this);
			ArmRight.setRotationPoint(-3.499F, 12.3517F, -2.4F);
			cube_r1 = new ModelRenderer(this);
			cube_r1.setRotationPoint(-1.75F, 1.25F, -4.0F);
			ArmRight.addChild(cube_r1);
			setRotationAngle(cube_r1, 0.0F, 0.0F, -0.48F);
			cube_r1.setTextureOffset(0, 0).addBox(-4.0F, -1.0F, 3.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);
			cube_r2 = new ModelRenderer(this);
			cube_r2.setRotationPoint(-1.75F, 1.15F, -4.4F);
			ArmRight.addChild(cube_r2);
			setRotationAngle(cube_r2, 0.0F, 0.0F, -0.48F);
			cube_r2.setTextureOffset(0, 0).addBox(-4.0F, -1.0F, 4.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
			cube_r3 = new ModelRenderer(this);
			cube_r3.setRotationPoint(1.0238F, 0.6554F, -4.0F);
			ArmRight.addChild(cube_r3);
			setRotationAngle(cube_r3, 0.0F, 0.0F, -0.2618F);
			cube_r3.setTextureOffset(0, 0).addBox(-4.0F, -1.0F, 3.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);
			cube_r4 = new ModelRenderer(this);
			cube_r4.setRotationPoint(1.0238F, 0.7554F, -3.5F);
			ArmRight.addChild(cube_r4);
			setRotationAngle(cube_r4, 0.0F, 0.0F, -0.2618F);
			cube_r4.setTextureOffset(0, 0).addBox(-4.0F, -1.0F, 2.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
			ArmLeft = new ModelRenderer(this);
			ArmLeft.setRotationPoint(5.6601F, 12.487F, -2.65F);
			cube_r5 = new ModelRenderer(this);
			cube_r5.setRotationPoint(4.25F, 2.5F, -3.75F);
			ArmLeft.addChild(cube_r5);
			setRotationAngle(cube_r5, 0.0F, 0.0F, 0.48F);
			cube_r5.setTextureOffset(0, 0).addBox(-2.0F, -1.0F, 3.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);
			cube_r6 = new ModelRenderer(this);
			cube_r6.setRotationPoint(4.25F, 2.35F, -3.25F);
			ArmLeft.addChild(cube_r6);
			setRotationAngle(cube_r6, 0.0F, 0.0F, 0.48F);
			cube_r6.setTextureOffset(0, 0).addBox(-2.0F, -1.0F, 3.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
			cube_r7 = new ModelRenderer(this);
			cube_r7.setRotationPoint(1.713F, 1.2966F, -2.75F);
			ArmLeft.addChild(cube_r7);
			setRotationAngle(cube_r7, 0.0F, 0.0F, 0.2618F);
			cube_r7.setTextureOffset(0, 0).addBox(-2.0F, -1.0F, 2.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);
			cube_r8 = new ModelRenderer(this);
			cube_r8.setRotationPoint(1.713F, 1.3966F, -3.25F);
			ArmLeft.addChild(cube_r8);
			setRotationAngle(cube_r8, 0.0F, 0.0F, 0.2618F);
			cube_r8.setTextureOffset(0, 0).addBox(-2.0F, -1.0F, 2.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
			Body = new ModelRenderer(this);
			Body.setRotationPoint(-0.5F, 11.4F, -0.5F);
			Body.setTextureOffset(67, 69).addBox(-3.5F, 3.0F, -5.1F, 10.0F, 3.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-3.0F, 3.5F, -5.3F, 9.0F, 2.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-3.2F, 2.0F, -4.9F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-2.9F, 1.0F, -4.9F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-2.3F, 1.5F, -5.2F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-0.2F, 2.5F, -5.2F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-2.6F, 2.5F, -5.2F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-2.1F, 0.5F, -5.2F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-2.7F, 0.0F, -4.9F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-2.5F, -1.0F, -4.9F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-1.7F, -2.5F, -5.2F, 2.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-1.9F, -0.5F, -5.2F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-1.7F, -1.5F, -5.2F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-2.3F, -2.9F, -4.9F, 3.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(2.9F, -2.5F, -5.2F, 2.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(0.1F, -2.6F, -4.9F, 3.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-2.3F, -2.0F, -4.9F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-1.7F, -2.0F, -4.9F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-1.1F, -1.5F, -5.2F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-0.9F, -0.5F, -5.2F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-1.5F, -1.0F, -4.9F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-1.3F, 0.0F, -4.9F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-0.7F, 0.5F, -5.2F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-1.1F, 1.0F, -4.9F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-0.5F, 1.5F, -5.2F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-0.8F, 2.0F, -4.9F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-0.8F, 6.0F, -4.9F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(0.6F, 7.0F, -4.7F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(1.6F, 8.0F, -4.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-1.65F, 8.0F, -4.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-2.5F, 7.0F, -4.7F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(-3.2F, 6.0F, -4.9F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(1.2F, 9.0F, -4.3F, 4.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(4.6F, 8.0F, -4.4F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(5.2F, 7.0F, -4.4F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(5.6F, 6.0F, -4.4F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(5.9F, 3.0F, -4.4F, 1.0F, 3.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(5.5F, 2.0F, -4.4F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(4.9F, 0.0F, -4.4F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(5.2F, 1.0F, -4.4F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(4.7F, -1.0F, -4.4F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(1.5F, -3.0F, -4.3F, 4.0F, 12.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(4.5F, -3.0F, -4.4F, 1.0F, 2.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(67, 69).addBox(2.3F, -2.9F, -4.9F, 3.0F, 1.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-2.5F, -3.0F, -4.4F, 1.0F, 2.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-2.7F, -1.0F, -4.4F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-2.9F, 0.0F, -4.4F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-3.2F, 1.0F, -4.4F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-3.5F, 2.0F, -4.4F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-3.9F, 3.0F, -4.4F, 1.0F, 3.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-3.5F, 6.0F, -4.4F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-3.2F, 7.0F, -4.4F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-2.1F, 9.0F, -4.3F, 4.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-2.5F, 8.0F, -4.3F, 1.0F, 1.0F, 5.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-2.0F, 6.8F, 0.0F, 7.0F, 3.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-2.5F, 5.8F, 0.0F, 8.0F, 3.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-3.5F, 3.0F, 0.0F, 10.0F, 3.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-2.5F, 0.0F, 0.0F, 8.0F, 3.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-2.0F, -3.0F, 0.0F, 7.0F, 3.0F, 1.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-2.5F, -3.0F, -4.4F, 4.0F, 12.0F, 5.0F, 0.0F, false);
			Head = new ModelRenderer(this);
			Head.setRotationPoint(0.9657F, 5.754F, -2.2875F);
			Head.setTextureOffset(0, 0).addBox(-3.0657F, 0.646F, 1.7875F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			Head.setTextureOffset(0, 0).addBox(-3.4657F, 1.646F, 1.7875F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Head.setTextureOffset(0, 0).addBox(-2.4657F, -1.354F, 1.7875F, 5.0F, 2.0F, 1.0F, 0.0F, false);
			Head.setTextureOffset(0, 0).addBox(-1.4657F, -2.354F, 1.7875F, 3.0F, 1.0F, 1.0F, 0.0F, false);
			Head.setTextureOffset(0, 67).addBox(-1.4657F, 1.346F, -5.4125F, 3.0F, 1.0F, 8.0F, 0.0F, false);
			Head.setTextureOffset(0, 67).addBox(-0.9657F, 1.846F, -5.4125F, 2.0F, 1.0F, 8.0F, 0.0F, false);
			cube_r9 = new ModelRenderer(this);
			cube_r9.setRotationPoint(-0.9449F, 0.6502F, -2.6125F);
			Head.addChild(cube_r9);
			setRotationAngle(cube_r9, 0.0F, 0.0F, -1.2915F);
			cube_r9.setTextureOffset(0, 0).addBox(-3.0F, -2.0F, 0.0F, 5.0F, 2.0F, 5.0F, 0.0F, false);
			cube_r10 = new ModelRenderer(this);
			cube_r10.setRotationPoint(2.4634F, -0.7498F, -2.6125F);
			Head.addChild(cube_r10);
			setRotationAngle(cube_r10, 0.0F, 0.0F, -1.8151F);
			cube_r10.setTextureOffset(0, 0).addBox(-4.0F, -2.0F, 0.0F, 5.0F, 2.0F, 5.0F, 0.0F, false);
			cube_r11 = new ModelRenderer(this);
			cube_r11.setRotationPoint(1.4329F, 1.1251F, -3.7125F);
			Head.addChild(cube_r11);
			setRotationAngle(cube_r11, 0.0F, 0.0F, -0.2182F);
			cube_r11.setTextureOffset(70, 52).addBox(0.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, false);
			cube_r12 = new ModelRenderer(this);
			cube_r12.setRotationPoint(-7.2625F, -0.2648F, -3.7125F);
			Head.addChild(cube_r12);
			setRotationAngle(cube_r12, 0.0F, 0.0F, 0.2182F);
			cube_r12.setTextureOffset(70, 52).addBox(4.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, false);
			cube_r13 = new ModelRenderer(this);
			cube_r13.setRotationPoint(0.3551F, -0.7498F, -2.6125F);
			Head.addChild(cube_r13);
			setRotationAngle(cube_r13, 0.0F, 0.0F, -1.5708F);
			cube_r13.setTextureOffset(0, 0).addBox(-4.0F, -2.0F, 0.0F, 6.0F, 2.0F, 5.0F, 0.0F, false);
			cube_r14 = new ModelRenderer(this);
			cube_r14.setRotationPoint(1.7134F, -0.7498F, -2.6125F);
			Head.addChild(cube_r14);
			setRotationAngle(cube_r14, 0.0F, 0.0F, -1.5708F);
			cube_r14.setTextureOffset(0, 0).addBox(-4.0F, -2.0F, 0.0F, 6.0F, 2.0F, 5.0F, 0.0F, false);
			cube_r15 = new ModelRenderer(this);
			cube_r15.setRotationPoint(-1.3984F, 0.0679F, -2.6125F);
			Head.addChild(cube_r15);
			setRotationAngle(cube_r15, 0.0F, 0.0F, 0.6981F);
			cube_r15.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, 0.0F, 1.0F, 2.0F, 5.0F, 0.0F, false);
			cube_r16 = new ModelRenderer(this);
			cube_r16.setRotationPoint(-2.0339F, 3.1636F, -2.6125F);
			Head.addChild(cube_r16);
			setRotationAngle(cube_r16, 0.0F, 0.0F, 0.2618F);
			cube_r16.setTextureOffset(0, 0).addBox(-2.0F, -4.0F, 0.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
			cube_r17 = new ModelRenderer(this);
			cube_r17.setRotationPoint(2.1024F, 3.1636F, -2.6125F);
			Head.addChild(cube_r17);
			setRotationAngle(cube_r17, 0.0F, 0.0F, -0.2618F);
			cube_r17.setTextureOffset(0, 0).addBox(1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
			cube_r18 = new ModelRenderer(this);
			cube_r18.setRotationPoint(1.4669F, 0.0679F, -2.6125F);
			Head.addChild(cube_r18);
			setRotationAngle(cube_r18, 0.0F, 0.0F, -0.6981F);
			cube_r18.setTextureOffset(0, 0).addBox(1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 5.0F, 0.0F, false);
			Ears = new ModelRenderer(this);
			Ears.setRotationPoint(-6.5173F, 1.9473F, -2.6115F);
			Head.addChild(Ears);
			cube_r19 = new ModelRenderer(this);
			cube_r19.setRotationPoint(0.0F, 0.0F, 0.0F);
			Ears.addChild(cube_r19);
			setRotationAngle(cube_r19, 0.0F, 0.0F, -0.0873F);
			cube_r19.setTextureOffset(0, 0).addBox(-3.0F, -4.0F, 0.0F, 4.0F, 1.0F, 3.0F, 0.0F, false);
			cube_r20 = new ModelRenderer(this);
			cube_r20.setRotationPoint(1.5441F, 0.3094F, 0.0F);
			Ears.addChild(cube_r20);
			setRotationAngle(cube_r20, 0.0F, 0.0F, 0.2618F);
			cube_r20.setTextureOffset(0, 0).addBox(-2.0F, -4.0F, 0.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
			cube_r21 = new ModelRenderer(this);
			cube_r21.setRotationPoint(-0.5452F, -3.3121F, -1.101F);
			Ears.addChild(cube_r21);
			setRotationAngle(cube_r21, 0.0F, 0.0F, 0.2182F);
			cube_r21.setTextureOffset(67, 69).addBox(1.0F, -1.0F, 1.0F, 5.0F, 1.0F, 0.0F, 0.0F, false);
			cube_r22 = new ModelRenderer(this);
			cube_r22.setRotationPoint(-4.2462F, -2.64F, -1.101F);
			Ears.addChild(cube_r22);
			setRotationAngle(cube_r22, 0.0F, 0.0F, -0.0873F);
			cube_r22.setTextureOffset(67, 69).addBox(1.0F, -1.0F, 1.0F, 4.0F, 1.0F, 0.0F, 0.0F, false);
			cube_r23 = new ModelRenderer(this);
			cube_r23.setRotationPoint(11.3319F, -2.9717F, -1.101F);
			Ears.addChild(cube_r23);
			setRotationAngle(cube_r23, 0.0F, 0.0F, 0.0873F);
			cube_r23.setTextureOffset(67, 69).addBox(1.0F, -1.0F, 1.0F, 4.0F, 1.0F, 0.0F, 0.0F, false);
			cube_r24 = new ModelRenderer(this);
			cube_r24.setRotationPoint(11.3319F, -2.9717F, -0.001F);
			Ears.addChild(cube_r24);
			setRotationAngle(cube_r24, 0.0F, 0.0F, 0.0873F);
			cube_r24.setTextureOffset(0, 0).addBox(1.0F, -1.0F, 0.0F, 4.0F, 1.0F, 3.0F, 0.0F, false);
			cube_r25 = new ModelRenderer(this);
			cube_r25.setRotationPoint(8.7265F, -2.0386F, -0.001F);
			Ears.addChild(cube_r25);
			setRotationAngle(cube_r25, 0.0F, 0.0F, -0.2182F);
			cube_r25.setTextureOffset(0, 0).addBox(1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
			cube_r26 = new ModelRenderer(this);
			cube_r26.setRotationPoint(7.7502F, -1.8222F, -1.101F);
			Ears.addChild(cube_r26);
			setRotationAngle(cube_r26, 0.0F, 0.0F, -0.2182F);
			cube_r26.setTextureOffset(67, 69).addBox(0.0F, -1.0F, 1.0F, 5.0F, 1.0F, 0.0F, 0.0F, false);
			LeftFoot = new ModelRenderer(this);
			LeftFoot.setRotationPoint(2.95F, 21.5F, 0.1F);
			LeftFoot.setTextureOffset(0, 67).addBox(-1.45F, 0.0F, -8.0F, 3.0F, 0.0F, 8.0F, 0.0F, false);
			RightFoot = new ModelRenderer(this);
			RightFoot.setRotationPoint(-0.8F, 21.5F, 0.1F);
			RightFoot.setTextureOffset(0, 67).addBox(-1.5F, 0.0F, -8.0F, 3.0F, 0.0F, 8.0F, 0.0F, false);
			Sword = new ModelRenderer(this);
			Sword.setRotationPoint(5.8389F, 15.7022F, 2.7292F);
			setRotationAngle(Sword, 0.0F, 0.0F, -0.5236F);
			Sword.setTextureOffset(63, 28).addBox(-3.2465F, -2.0022F, -2.3882F, 4.0F, 1.0F, 2.0F, 0.0F, false);
			Sword.setTextureOffset(63, 28).addBox(-1.6635F, -1.012F, -1.8882F, 1.0F, 5.0F, 1.0F, 0.0F, false);
			cube_r27 = new ModelRenderer(this);
			cube_r27.setRotationPoint(0.0F, 0.0F, 0.0F);
			Sword.addChild(cube_r27);
			setRotationAngle(cube_r27, 0.1309F, 0.2967F, 0.0F);
			cube_r27.setTextureOffset(65, 1).addBox(-1.0F, -16.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			cube_r28 = new ModelRenderer(this);
			cube_r28.setRotationPoint(0.2899F, -0.1305F, -3.7092F);
			Sword.addChild(cube_r28);
			setRotationAngle(cube_r28, -0.1309F, -0.2967F, 0.0F);
			cube_r28.setTextureOffset(65, 1).addBox(-1.0F, -16.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			cube_r29 = new ModelRenderer(this);
			cube_r29.setRotationPoint(-0.5749F, -0.0022F, -1.8805F);
			Sword.addChild(cube_r29);
			setRotationAngle(cube_r29, 0.0F, 0.2967F, 0.0F);
			cube_r29.setTextureOffset(65, 1).addBox(-1.0F, -15.0F, 0.0F, 1.0F, 13.0F, 1.0F, 0.0F, false);
			cube_r30 = new ModelRenderer(this);
			cube_r30.setRotationPoint(-0.2826F, -0.0022F, -1.8368F);
			Sword.addChild(cube_r30);
			setRotationAngle(cube_r30, 0.0F, -0.2967F, 0.0F);
			cube_r30.setTextureOffset(65, 1).addBox(-1.0F, -15.0F, 0.0F, 1.0F, 13.0F, 1.0F, 0.0F, false);
			cube_r31 = new ModelRenderer(this);
			cube_r31.setRotationPoint(-1.0635F, -11.8973F, -1.2055F);
			Sword.addChild(cube_r31);
			setRotationAngle(cube_r31, 0.1309F, -0.2967F, 0.0F);
			cube_r31.setTextureOffset(65, 1).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			cube_r32 = new ModelRenderer(this);
			cube_r32.setRotationPoint(-0.9465F, -0.0022F, -1.5882F);
			Sword.addChild(cube_r32);
			setRotationAngle(cube_r32, 0.0F, -0.2967F, 0.0F);
			cube_r32.setTextureOffset(65, 1).addBox(-1.0F, -15.0F, 0.0F, 1.0F, 13.0F, 1.0F, 0.0F, false);
			cube_r33 = new ModelRenderer(this);
			cube_r33.setRotationPoint(-1.8113F, -0.1305F, -4.0015F);
			Sword.addChild(cube_r33);
			setRotationAngle(cube_r33, -0.1309F, 0.2967F, 0.0F);
			cube_r33.setTextureOffset(65, 1).addBox(-1.0F, -16.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			cube_r34 = new ModelRenderer(this);
			cube_r34.setRotationPoint(-1.2389F, -0.0022F, -2.1292F);
			Sword.addChild(cube_r34);
			setRotationAngle(cube_r34, 0.0F, 0.2967F, 0.0F);
			cube_r34.setTextureOffset(65, 1).addBox(-1.0F, -15.0F, 0.0F, 1.0F, 13.0F, 1.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			ArmRight.render(matrixStack, buffer, packedLight, packedOverlay);
			ArmLeft.render(matrixStack, buffer, packedLight, packedOverlay);
			Body.render(matrixStack, buffer, packedLight, packedOverlay);
			Head.render(matrixStack, buffer, packedLight, packedOverlay);
			LeftFoot.render(matrixStack, buffer, packedLight, packedOverlay);
			RightFoot.render(matrixStack, buffer, packedLight, packedOverlay);
			Sword.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.ArmLeft.rotateAngleZ = f3 / (180F / (float) Math.PI);
			this.Head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.Head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.ArmRight.rotateAngleZ = f3 / (180F / (float) Math.PI);
			this.RightFoot.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.LeftFoot.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		}
	}
}
