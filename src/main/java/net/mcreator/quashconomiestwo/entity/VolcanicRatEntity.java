
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
public class VolcanicRatEntity extends QuashconomiestwoModElements.ModElement {
	public static EntityType entity = null;
	public VolcanicRatEntity(QuashconomiestwoModElements instance) {
		super(instance, 156);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 1.8f)).build("volcanic_rat")
						.setRegistryName("volcanic_rat");
		elements.entities.add(() -> entity);
		elements.items
				.add(() -> new SpawnEggItem(entity, -1, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("volcanic_rat_spawn_egg"));
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
				return new MobRenderer(renderManager, new ModelVolcanic_Rat(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("quashconomiestwo:textures/volcanic_rat.png");
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
	public static class ModelVolcanic_Rat extends EntityModel<Entity> {
		private final ModelRenderer bone8;
		private final ModelRenderer tail;
		private final ModelRenderer body;
		private final ModelRenderer head;
		private final ModelRenderer leg1;
		private final ModelRenderer leg2;
		private final ModelRenderer leg3;
		private final ModelRenderer leg4;
		public ModelVolcanic_Rat() {
			textureWidth = 32;
			textureHeight = 32;
			bone8 = new ModelRenderer(this);
			bone8.setRotationPoint(0.0F, 24.0F, 0.0F);
			tail = new ModelRenderer(this);
			tail.setRotationPoint(0.0F, -3.0F, 0.3F);
			bone8.addChild(tail);
			tail.setTextureOffset(0, 11).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
			tail.setTextureOffset(14, 0).addBox(-0.5F, 0.0F, 4.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
			body = new ModelRenderer(this);
			body.setRotationPoint(0.5F, -1.0F, -0.7F);
			bone8.addChild(body);
			body.setTextureOffset(0, 0).addBox(-2.0F, -3.0F, -7.0F, 3.0F, 3.0F, 8.0F, 0.0F, false);
			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, -4.0F, -7.7F);
			bone8.addChild(head);
			head.setTextureOffset(0, 11).addBox(-2.5F, -2.0F, -2.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			head.setTextureOffset(0, 14).addBox(0.5F, -2.0F, -2.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			head.setTextureOffset(8, 11).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
			head.setTextureOffset(14, 0).addBox(-0.5F, 1.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			leg1 = new ModelRenderer(this);
			leg1.setRotationPoint(-1.5F, -3.0F, -0.7125F);
			bone8.addChild(leg1);
			leg1.setTextureOffset(0, 18).addBox(-2.0F, 0.0F, -0.9875F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			leg1.setTextureOffset(8, 19).addBox(-1.5F, 2.0F, -2.0125F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			leg2 = new ModelRenderer(this);
			leg2.setRotationPoint(1.5F, -3.0F, -0.625F);
			bone8.addChild(leg2);
			leg2.setTextureOffset(18, 15).addBox(0.5F, 2.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			leg2.setTextureOffset(12, 17).addBox(0.0F, 0.0F, -1.075F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			leg3 = new ModelRenderer(this);
			leg3.setRotationPoint(1.7F, -3.0F, -6.0F);
			bone8.addChild(leg3);
			leg3.setTextureOffset(17, 11).addBox(0.0F, 2.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			leg3.setTextureOffset(0, 4).addBox(-0.7F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			leg4 = new ModelRenderer(this);
			leg4.setRotationPoint(-1.5125F, -3.0F, -6.0F);
			bone8.addChild(leg4);
			leg4.setTextureOffset(14, 5).addBox(-1.1375F, 2.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			leg4.setTextureOffset(0, 0).addBox(-1.4875F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			bone8.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.leg1.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.leg4.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.tail.rotateAngleY = f4 / (180F / (float) Math.PI);
			this.leg2.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.leg3.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		}
	}
}
