
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
public class SinisterSerpentEntity extends QuashconomiestwoModElements.ModElement {
	public static EntityType entity = null;
	public SinisterSerpentEntity(QuashconomiestwoModElements instance) {
		super(instance, 157);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 1.8f)).build("sinister_serpent")
						.setRegistryName("sinister_serpent");
		elements.entities.add(() -> entity);
		elements.items.add(
				() -> new SpawnEggItem(entity, -1, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("sinister_serpent_spawn_egg"));
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
				return new MobRenderer(renderManager, new ModelSinister_Serpant(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("quashconomiestwo:textures/sinister_serpant.png");
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
	public static class ModelSinister_Serpant extends EntityModel<Entity> {
		private final ModelRenderer left_wing;
		private final ModelRenderer cube_r1;
		private final ModelRenderer right_wing;
		private final ModelRenderer tail;
		private final ModelRenderer body;
		private final ModelRenderer cube_r2;
		private final ModelRenderer sneknek;
		private final ModelRenderer head;
		public ModelSinister_Serpant() {
			textureWidth = 64;
			textureHeight = 64;
			left_wing = new ModelRenderer(this);
			left_wing.setRotationPoint(0.5F, 13.0F, 2.95F);
			cube_r1 = new ModelRenderer(this);
			cube_r1.setRotationPoint(9.5F, -4.475F, -5.95F);
			left_wing.addChild(cube_r1);
			setRotationAngle(cube_r1, -0.6981F, 0.0F, 0.0F);
			cube_r1.setTextureOffset(0, 28).addBox(0.0F, -2.0F, 4.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);
			cube_r1.setTextureOffset(27, 14).addBox(-3.0F, -2.0F, 4.0F, 3.0F, 1.0F, 4.0F, 0.0F, false);
			cube_r1.setTextureOffset(16, 22).addBox(-10.0F, -2.0F, 4.0F, 7.0F, 1.0F, 6.0F, 0.0F, false);
			cube_r1.setTextureOffset(0, 7).addBox(-9.0F, -1.0F, 4.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
			right_wing = new ModelRenderer(this);
			right_wing.setRotationPoint(-2.5F, 13.0F, 2.95F);
			setRotationAngle(right_wing, -0.6981F, 0.0F, 0.0F);
			right_wing.setTextureOffset(8, 8).addBox(-12.5F, -1.5601F, -3.3747F, 3.0F, 1.0F, 2.0F, 0.0F, false);
			right_wing.setTextureOffset(0, 14).addBox(-9.5F, -1.5601F, -3.3747F, 3.0F, 1.0F, 4.0F, 0.0F, false);
			right_wing.setTextureOffset(27, 7).addBox(-1.5F, -0.5601F, -3.3747F, 1.0F, 1.0F, 6.0F, 0.0F, false);
			right_wing.setTextureOffset(27, 0).addBox(-6.5F, -1.5601F, -3.3747F, 7.0F, 1.0F, 6.0F, 0.0F, false);
			tail = new ModelRenderer(this);
			tail.setRotationPoint(-1.0F, 23.2F, 14.0F);
			tail.setTextureOffset(0, 22).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 12.0F, 0.0F, false);
			body = new ModelRenderer(this);
			body.setRotationPoint(-1.25F, 16.5F, 6.25F);
			cube_r2 = new ModelRenderer(this);
			cube_r2.setRotationPoint(1.25F, -4.5F, -8.25F);
			body.addChild(cube_r2);
			setRotationAngle(cube_r2, -0.6981F, 0.0F, 0.0F);
			cube_r2.setTextureOffset(0, 0).addBox(-3.0F, -3.0F, 0.0F, 4.0F, 3.0F, 19.0F, 0.0F, false);
			sneknek = new ModelRenderer(this);
			sneknek.setRotationPoint(-0.75F, 11.0F, -0.75F);
			sneknek.setTextureOffset(0, 0).addBox(-2.25F, -1.25F, -3.25F, 4.0F, 3.0F, 4.0F, 0.0F, false);
			head = new ModelRenderer(this);
			head.setRotationPoint(0.75F, 0.75F, -2.25F);
			sneknek.addChild(head);
			head.setTextureOffset(0, 22).addBox(-2.0F, -1.55F, -5.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			left_wing.render(matrixStack, buffer, packedLight, packedOverlay);
			right_wing.render(matrixStack, buffer, packedLight, packedOverlay);
			tail.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			sneknek.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.sneknek.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.sneknek.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.right_wing.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.left_wing.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.tail.rotateAngleX = f3 / (180F / (float) Math.PI);
		}
	}
}
