
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
public class SonicDuckEntity extends QuashconomiestwoModElements.ModElement {
	public static EntityType entity = null;
	public SonicDuckEntity(QuashconomiestwoModElements instance) {
		super(instance, 102);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 1.8f)).build("sonic_duck")
						.setRegistryName("sonic_duck");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -10027213, -3355444, new Item.Properties().group(ItemGroup.MISC))
				.setRegistryName("sonic_duck_spawn_egg"));
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
				return new MobRenderer(renderManager, new ModelSonic_Duck(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("quashconomiestwo:textures/sonic_duck.png");
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
	public static class ModelSonic_Duck extends EntityModel<Entity> {
		private final ModelRenderer scarf;
		private final ModelRenderer cube_r1;
		private final ModelRenderer wing2;
		private final ModelRenderer neck;
		private final ModelRenderer head;
		private final ModelRenderer bucket;
		private final ModelRenderer tail;
		private final ModelRenderer left_leg;
		private final ModelRenderer right_leg;
		private final ModelRenderer body;
		private final ModelRenderer wing1;
		public ModelSonic_Duck() {
			textureWidth = 128;
			textureHeight = 128;
			scarf = new ModelRenderer(this);
			scarf.setRotationPoint(0.0F, -0.4F, -14.575F);
			setRotationAngle(scarf, 0.624F, 0.0F, 0.0F);
			cube_r1 = new ModelRenderer(this);
			cube_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
			scarf.addChild(cube_r1);
			setRotationAngle(cube_r1, -0.2356F, 0.0F, 0.0087F);
			cube_r1.setTextureOffset(0, 0).addBox(-2.9998F, -3.8903F, -2.0067F, 1.0F, 4.0F, 10.0F, 0.0F, false);
			cube_r1.setTextureOffset(0, 33).addBox(3.0F, -3.9412F, -2.019F, 1.0F, 4.0F, 34.0F, 0.0F, false);
			cube_r1.setTextureOffset(53, 16).addBox(-3.0F, -3.9412F, -3.019F, 7.0F, 4.0F, 1.0F, 0.0F, false);
			wing2 = new ModelRenderer(this);
			wing2.setRotationPoint(7.5F, 7.725F, -4.75F);
			setRotationAngle(wing2, -0.2923F, 0.0F, 0.0F);
			wing2.setTextureOffset(55, 61).addBox(-0.5F, -4.0F, -7.5F, 1.0F, 8.0F, 15.0F, 0.0F, false);
			neck = new ModelRenderer(this);
			neck.setRotationPoint(0.0F, -4.0F, -16.0F);
			setRotationAngle(neck, 0.3927F, 0.0F, 0.0F);
			neck.setTextureOffset(17, 33).addBox(-1.675F, -5.51F, -1.5535F, 4.0F, 11.0F, 4.0F, 0.0F, false);
			head = new ModelRenderer(this);
			head.setRotationPoint(0.925F, 4.0F, 3.0F);
			neck.addChild(head);
			head.setTextureOffset(0, 71).addBox(-4.0F, -10.9739F, -8.1809F, 7.0F, 7.0F, 8.0F, 0.0F, false);
			head.setTextureOffset(36, 50).addBox(-4.0F, -5.9739F, -11.1809F, 7.0F, 2.0F, 3.0F, 0.0F, false);
			bucket = new ModelRenderer(this);
			bucket.setRotationPoint(-0.4208F, -11.7284F, -6.4658F);
			head.addChild(bucket);
			setRotationAngle(bucket, -0.0218F, 0.0F, 0.0F);
			bucket.setTextureOffset(53, 8).addBox(-2.2042F, -3.9804F, 0.8533F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			bucket.setTextureOffset(36, 44).addBox(-2.5042F, 0.1367F, 0.5845F, 5.0F, 1.0F, 5.0F, 0.0F, false);
			bucket.setTextureOffset(53, 0).addBox(-3.7292F, 0.1367F, 1.8595F, 7.0F, 7.0F, 1.0F, 0.0F, false);
			tail = new ModelRenderer(this);
			tail.setRotationPoint(0.0F, 13.0F, 9.425F);
			setRotationAngle(tail, 0.1484F, 0.0F, 0.0F);
			tail.setTextureOffset(0, 33).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 4.0F, 1.0F, 0.0F, false);
			left_leg = new ModelRenderer(this);
			left_leg.setRotationPoint(3.9875F, 18.75F, -0.5F);
			left_leg.setTextureOffset(36, 36).addBox(-1.9875F, 4.25F, -6.5F, 4.0F, 1.0F, 7.0F, 0.0F, false);
			left_leg.setTextureOffset(30, 56).addBox(-0.5125F, -5.75F, -0.5F, 1.0F, 10.0F, 1.0F, 0.0F, false);
			right_leg = new ModelRenderer(this);
			right_leg.setRotationPoint(-3.9844F, 18.75F, -0.5F);
			right_leg.setTextureOffset(0, 14).addBox(-2.0156F, 4.25F, -6.5F, 4.0F, 1.0F, 7.0F, 0.0F, false);
			right_leg.setTextureOffset(26, 56).addBox(-0.5156F, -5.75F, -0.5F, 1.0F, 10.0F, 1.0F, 0.0F, false);
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 7.0F, -6.5F);
			setRotationAngle(body, -0.2923F, 0.0F, 0.0F);
			body.setTextureOffset(53, 0).addBox(-6.0F, 4.0F, -8.5F, 12.0F, 1.0F, 24.0F, 0.0F, false);
			body.setTextureOffset(0, 0).addBox(-7.0F, -4.0F, -9.5F, 14.0F, 8.0F, 25.0F, 0.0F, false);
			body.setTextureOffset(0, 56).addBox(-6.0F, -3.0F, -10.5F, 12.0F, 6.0F, 1.0F, 0.0F, false);
			body.setTextureOffset(36, 36).addBox(-6.0F, -5.0F, -8.5F, 12.0F, 1.0F, 24.0F, 0.0F, false);
			wing1 = new ModelRenderer(this);
			wing1.setRotationPoint(0.0F, 25.0F, -9.0F);
			setRotationAngle(wing1, -0.3054F, 0.0F, 0.0F);
			wing1.setTextureOffset(0, 33).addBox(-8.0F, -21.8339F, -10.1708F, 1.0F, 8.0F, 15.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			scarf.render(matrixStack, buffer, packedLight, packedOverlay);
			wing2.render(matrixStack, buffer, packedLight, packedOverlay);
			neck.render(matrixStack, buffer, packedLight, packedOverlay);
			tail.render(matrixStack, buffer, packedLight, packedOverlay);
			left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
			right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			wing1.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.left_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.right_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.neck.rotateAngleZ = f4 / (180F / (float) Math.PI);
		}
	}
}
