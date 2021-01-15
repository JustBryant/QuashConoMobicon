
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

import net.mcreator.quashconomiestwo.item.QuashlingItem;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@QuashconomiestwoModElements.ModElement.Tag
public class FlowerWolfEntity extends QuashconomiestwoModElements.ModElement {
	public static EntityType entity = null;
	public FlowerWolfEntity(QuashconomiestwoModElements instance) {
		super(instance, 99);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 1.8f)).build("flower_wolf")
						.setRegistryName("flower_wolf");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -16776961, -65281, new Item.Properties().group(ItemGroup.MISC))
				.setRegistryName("flower_wolf_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
		boolean biomeCriteria = false;
		if (new ResourceLocation("quashconomiestwo:quashiome").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("forest").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("dark_forest").equals(event.getName()))
			biomeCriteria = true;
		if (!biomeCriteria)
			return;
		event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, 3, 1, 1));
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
				return new MobRenderer(renderManager, new Modelflower_wolf(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("quashconomiestwo:textures/flower_wolf.png");
					}
				};
			});
		}
	}
	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.8);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 80);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 5);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 6);
		ammma = ammma.createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.5);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.5);
		GlobalEntityTypeAttributes.put(entity, ammma.create());
	}
	public static class CustomEntity extends MonsterEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 25;
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
			this.entityDropItem(new ItemStack(QuashlingItem.block, (int) (1)));
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
	public static class Modelflower_wolf extends EntityModel<Entity> {
		private final ModelRenderer head;
		private final ModelRenderer earpoint;
		private final ModelRenderer earpoint2;
		private final ModelRenderer earpoint3;
		private final ModelRenderer earpoint4;
		private final ModelRenderer earpoint5;
		private final ModelRenderer earpoint6;
		private final ModelRenderer body;
		private final ModelRenderer body_rotation;
		private final ModelRenderer body_sub_1;
		private final ModelRenderer mane;
		private final ModelRenderer mane_rotation;
		private final ModelRenderer mane_sub_1;
		private final ModelRenderer back_left_leg;
		private final ModelRenderer back_right_leg;
		private final ModelRenderer front_left_leg;
		private final ModelRenderer front_right_leg;
		private final ModelRenderer tail;
		private final ModelRenderer TailFlowersleft;
		private final ModelRenderer lefttailflower4_r1;
		private final ModelRenderer TailFlowersRight2;
		private final ModelRenderer righttailflower4_r1;
		private final ModelRenderer NeckFlowers;
		private final ModelRenderer SideNeckFlowers;
		private final ModelRenderer SideNeckFlowers2;
		private final ModelRenderer SideNeckFlowers3;
		private final ModelRenderer SideNeckFlowers4;
		private final ModelRenderer SideNeckFlowers5;
		private final ModelRenderer SideNeckFlowers6;
		private final ModelRenderer SideNeckFlowers7;
		private final ModelRenderer middleBackFlowers;
		private final ModelRenderer rightbackflowers;
		private final ModelRenderer leftbackflowers;
		private final ModelRenderer bigbackflowerright;
		private final ModelRenderer bigbackflowerleft;
		public Modelflower_wolf() {
			textureWidth = 64;
			textureHeight = 64;
			head = new ModelRenderer(this);
			head.setRotationPoint(1.0F, 13.5F, -7.0F);
			head.setTextureOffset(26, 26).addBox(-4.0F, -5.0F, -6.0F, 6.0F, 7.0F, 6.0F, 0.0F, false);
			head.setTextureOffset(8, 42).addBox(0.0F, -6.1F, -3.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			head.setTextureOffset(0, 42).addBox(-4.0F, -6.1F, -3.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			head.setTextureOffset(31, 8).addBox(-2.5F, -1.12F, -8.8F, 3.0F, 3.0F, 7.0F, 0.0F, false);
			earpoint = new ModelRenderer(this);
			earpoint.setRotationPoint(0.7F, -6.2F, -2.0F);
			head.addChild(earpoint);
			setRotationAngle(earpoint, 0.0F, 0.0F, -0.8378F);
			earpoint.setTextureOffset(44, 22).addBox(-0.5427F, -0.4533F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			earpoint2 = new ModelRenderer(this);
			earpoint2.setRotationPoint(1.3054F, -6.1122F, -2.0F);
			head.addChild(earpoint2);
			setRotationAngle(earpoint2, 0.0F, 0.0F, -0.6458F);
			earpoint2.setTextureOffset(44, 19).addBox(-0.4526F, -0.5722F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			earpoint3 = new ModelRenderer(this);
			earpoint3.setRotationPoint(1.039F, -6.4206F, -2.0F);
			head.addChild(earpoint3);
			setRotationAngle(earpoint3, 0.0F, 0.0F, 0.8203F);
			earpoint3.setTextureOffset(44, 12).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			earpoint4 = new ModelRenderer(this);
			earpoint4.setRotationPoint(-3.3237F, -6.1635F, -2.0F);
			head.addChild(earpoint4);
			setRotationAngle(earpoint4, 0.0F, 0.0F, 0.7679F);
			earpoint4.setTextureOffset(44, 9).addBox(-0.4424F, -0.4845F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			earpoint5 = new ModelRenderer(this);
			earpoint5.setRotationPoint(-2.9111F, -6.0622F, -2.0F);
			head.addChild(earpoint5);
			setRotationAngle(earpoint5, 0.0F, 0.0F, 0.7679F);
			earpoint5.setTextureOffset(44, 6).addBox(-0.3709F, -0.6601F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			earpoint6 = new ModelRenderer(this);
			earpoint6.setRotationPoint(-3.061F, -6.2206F, -2.0F);
			head.addChild(earpoint6);
			setRotationAngle(earpoint6, 0.0F, 0.0F, 0.8029F);
			earpoint6.setTextureOffset(43, 3).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 14.0F, 2.0F);
			body_rotation = new ModelRenderer(this);
			body_rotation.setRotationPoint(0.0F, 0.0F, 0.0F);
			body.addChild(body_rotation);
			setRotationAngle(body_rotation, 1.5708F, 0.0F, 0.0F);
			body_sub_1 = new ModelRenderer(this);
			body_sub_1.setRotationPoint(0.0F, 0.0F, 0.0F);
			body_rotation.addChild(body_sub_1);
			body_sub_1.setTextureOffset(0, 15).addBox(-4.0F, -4.0F, -3.0F, 8.0F, 9.0F, 8.0F, 0.0F, false);
			mane = new ModelRenderer(this);
			mane.setRotationPoint(1.0F, 14.0F, 2.0F);
			mane_rotation = new ModelRenderer(this);
			mane_rotation.setRotationPoint(-1.0F, 2.5F, -2.5F);
			mane.addChild(mane_rotation);
			setRotationAngle(mane_rotation, 1.5708F, 0.0F, 0.0F);
			mane_sub_1 = new ModelRenderer(this);
			mane_sub_1.setRotationPoint(0.0F, 0.0F, 0.0F);
			mane_rotation.addChild(mane_sub_1);
			mane_sub_1.setTextureOffset(0, 0).addBox(-5.0F, -7.5F, -0.5F, 10.0F, 6.0F, 9.0F, 0.0F, false);
			back_left_leg = new ModelRenderer(this);
			back_left_leg.setRotationPoint(2.5F, 17.0F, 5.0F);
			back_left_leg.setTextureOffset(24, 39).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
			back_left_leg.setTextureOffset(43, 0).addBox(-1.0F, 6.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			back_right_leg = new ModelRenderer(this);
			back_right_leg.setRotationPoint(-2.5F, 17.0F, 5.0F);
			back_right_leg.setTextureOffset(16, 32).addBox(-0.8F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
			back_right_leg.setTextureOffset(40, 42).addBox(-0.8F, 6.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			front_left_leg = new ModelRenderer(this);
			front_left_leg.setRotationPoint(3.5F, 17.0F, -6.0F);
			front_left_leg.setTextureOffset(8, 32).addBox(-1.1F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
			front_left_leg.setTextureOffset(32, 42).addBox(-1.1F, 6.0F, -1.9F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			front_right_leg = new ModelRenderer(this);
			front_right_leg.setRotationPoint(-3.5F, 17.0F, -6.0F);
			front_right_leg.setTextureOffset(0, 32).addBox(-0.8F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
			front_right_leg.setTextureOffset(16, 42).addBox(-0.8F, 6.0F, -1.9F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			tail = new ModelRenderer(this);
			tail.setRotationPoint(0.0F, 11.6F, 7.3F);
			setRotationAngle(tail, 1.405F, 0.0F, 0.0F);
			tail.setTextureOffset(0, 0).addBox(-1.0F, -0.4566F, -1.5902F, 2.0F, 7.0F, 2.0F, 0.0F, false);
			TailFlowersleft = new ModelRenderer(this);
			TailFlowersleft.setRotationPoint(-0.4F, 5.9415F, 0.1913F);
			tail.addChild(TailFlowersleft);
			setRotationAngle(TailFlowersleft, -1.3439F, -0.2443F, 0.0873F);
			lefttailflower4_r1 = new ModelRenderer(this);
			lefttailflower4_r1.setRotationPoint(0.5831F, -2.2701F, -0.0942F);
			TailFlowersleft.addChild(lefttailflower4_r1);
			setRotationAngle(lefttailflower4_r1, -1.1868F, -0.1745F, 0.0F);
			lefttailflower4_r1.setTextureOffset(20, 45).addBox(-1.2388F, 0.1841F, 0.9801F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			lefttailflower4_r1.setTextureOffset(34, 45).addBox(-1.5049F, 1.1116F, 0.2528F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			lefttailflower4_r1.setTextureOffset(36, 45).addBox(-1.7952F, 2.1336F, -0.4963F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			lefttailflower4_r1.setTextureOffset(40, 45).addBox(-2.1581F, 3.4843F, -1.116F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			TailFlowersRight2 = new ModelRenderer(this);
			TailFlowersRight2.setRotationPoint(0.1F, 5.9415F, 0.1913F);
			tail.addChild(TailFlowersRight2);
			setRotationAngle(TailFlowersRight2, 0.0F, 0.0F, -0.1047F);
			righttailflower4_r1 = new ModelRenderer(this);
			righttailflower4_r1.setRotationPoint(-0.5643F, -0.4786F, 1.7497F);
			TailFlowersRight2.addChild(righttailflower4_r1);
			setRotationAngle(righttailflower4_r1, -2.5656F, 0.0F, -0.2967F);
			righttailflower4_r1.setTextureOffset(22, 45).addBox(0.3258F, -0.2502F, 0.8842F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			righttailflower4_r1.setTextureOffset(32, 45).addBox(0.7378F, 0.6311F, 0.1676F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			righttailflower4_r1.setTextureOffset(38, 45).addBox(1.4299F, 1.9501F, -0.5475F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			righttailflower4_r1.setTextureOffset(42, 45).addBox(1.9169F, 3.0345F, -1.2084F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			NeckFlowers = new ModelRenderer(this);
			NeckFlowers.setRotationPoint(-0.5F, 8.5F, 0.5F);
			NeckFlowers.setTextureOffset(22, 48).addBox(-0.9F, -2.9F, -8.5F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			NeckFlowers.setTextureOffset(20, 48).addBox(0.85F, -2.9F, -8.5F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			NeckFlowers.setTextureOffset(10, 47).addBox(1.65F, -2.6F, -8.5F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			NeckFlowers.setTextureOffset(14, 47).addBox(0.25F, -2.3F, -8.5F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			NeckFlowers.setTextureOffset(12, 47).addBox(-0.35F, -1.8F, -8.5F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			NeckFlowers.setTextureOffset(2, 47).addBox(-1.35F, -2.4F, -8.5F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			NeckFlowers.setTextureOffset(0, 47).addBox(-2.35F, -2.9F, -8.5F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers = new ModelRenderer(this);
			SideNeckFlowers.setRotationPoint(2.45F, 7.1F, -8.0F);
			setRotationAngle(SideNeckFlowers, 0.0F, 0.0F, 0.2618F);
			SideNeckFlowers.setTextureOffset(18, 48).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers2 = new ModelRenderer(this);
			SideNeckFlowers2.setRotationPoint(3.1783F, 8.3935F, -8.2F);
			setRotationAngle(SideNeckFlowers2, 0.0F, 0.0F, 0.925F);
			SideNeckFlowers2.setTextureOffset(16, 48).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers2.setTextureOffset(8, 47).addBox(-1.7396F, -1.6828F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers2.setTextureOffset(6, 47).addBox(-0.8704F, -1.3408F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers3 = new ModelRenderer(this);
			SideNeckFlowers3.setRotationPoint(-3.0921F, 8.5526F, -8.2F);
			setRotationAngle(SideNeckFlowers3, 0.0F, 0.0F, -0.576F);
			SideNeckFlowers3.setTextureOffset(4, 47).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers3.setTextureOffset(46, 39).addBox(0.3801F, -2.1208F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers4 = new ModelRenderer(this);
			SideNeckFlowers4.setRotationPoint(3.1783F, 9.1935F, -8.2F);
			setRotationAngle(SideNeckFlowers4, 0.0F, 0.0F, 1.2741F);
			SideNeckFlowers4.setTextureOffset(46, 28).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers5 = new ModelRenderer(this);
			SideNeckFlowers5.setRotationPoint(3.3783F, 9.8935F, -8.2F);
			setRotationAngle(SideNeckFlowers5, 0.0F, 0.0F, 1.6057F);
			SideNeckFlowers5.setTextureOffset(46, 25).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers6 = new ModelRenderer(this);
			SideNeckFlowers6.setRotationPoint(-3.3961F, 9.1779F, -8.2F);
			setRotationAngle(SideNeckFlowers6, 0.0F, 0.0F, -0.9599F);
			SideNeckFlowers6.setTextureOffset(46, 46).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers7 = new ModelRenderer(this);
			SideNeckFlowers7.setRotationPoint(-3.3961F, 9.8779F, -8.2F);
			setRotationAngle(SideNeckFlowers7, 0.0F, 0.0F, 1.85F);
			SideNeckFlowers7.setTextureOffset(44, 45).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			middleBackFlowers = new ModelRenderer(this);
			middleBackFlowers.setRotationPoint(-0.237F, 7.5155F, -6.3391F);
			setRotationAngle(middleBackFlowers, -1.0821F, 0.0F, 0.0F);
			middleBackFlowers.setTextureOffset(18, 45).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			middleBackFlowers.setTextureOffset(16, 45).addBox(-0.5F, -3.0893F, 0.845F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			middleBackFlowers.setTextureOffset(44, 39).addBox(-0.5F, -4.502F, 1.5962F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			middleBackFlowers.setTextureOffset(44, 28).addBox(-0.5F, -5.8096F, 2.971F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			middleBackFlowers.setTextureOffset(44, 25).addBox(-0.5F, -7.1229F, 4.1223F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			middleBackFlowers.setTextureOffset(24, 38).addBox(-0.5F, -8.8005F, 5.0143F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			middleBackFlowers.setTextureOffset(24, 35).addBox(-0.5F, -10.3898F, 5.8593F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			middleBackFlowers.setTextureOffset(24, 32).addBox(-0.5F, -11.9791F, 6.7044F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			rightbackflowers = new ModelRenderer(this);
			rightbackflowers.setRotationPoint(-0.437F, 8.0155F, -6.9391F);
			setRotationAngle(rightbackflowers, -1.1519F, -0.8552F, 0.0F);
			rightbackflowers.setTextureOffset(6, 42).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			rightbackflowers.setTextureOffset(0, 42).addBox(0.8585F, -2.5788F, 0.4803F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			rightbackflowers.setTextureOffset(30, 39).addBox(2.066F, -3.5378F, 0.9073F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			rightbackflowers.setTextureOffset(35, 0).addBox(3.5754F, -4.4924F, 1.9891F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			rightbackflowers.setTextureOffset(22, 32).addBox(4.8584F, -5.3892F, 2.7168F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			rightbackflowers.setTextureOffset(16, 32).addBox(6.2924F, -6.528F, 3.2238F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			rightbackflowers.setTextureOffset(14, 32).addBox(7.6509F, -7.6068F, 3.7041F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			rightbackflowers.setTextureOffset(8, 32).addBox(9.0093F, -8.6856F, 4.1844F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			leftbackflowers = new ModelRenderer(this);
			leftbackflowers.setRotationPoint(0.963F, 5.4155F, -6.6391F);
			setRotationAngle(leftbackflowers, -1.1519F, 0.8552F, 0.0F);
			leftbackflowers.setTextureOffset(6, 32).addBox(-0.9331F, 0.2523F, 1.9638F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			leftbackflowers.setTextureOffset(0, 32).addBox(-2.2916F, -0.8265F, 2.4441F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			leftbackflowers.setTextureOffset(30, 21).addBox(-3.5746F, -1.8454F, 2.8977F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			leftbackflowers.setTextureOffset(29, 0).addBox(-4.9331F, -2.6802F, 3.9262F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			leftbackflowers.setTextureOffset(6, 15).addBox(-6.2161F, -3.5364F, 4.7452F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			leftbackflowers.setTextureOffset(0, 15).addBox(-7.65F, -4.6751F, 5.2522F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			leftbackflowers.setTextureOffset(6, 0).addBox(-9.0085F, -5.7539F, 5.7326F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			leftbackflowers.setTextureOffset(0, 0).addBox(-10.367F, -6.8327F, 6.2129F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			bigbackflowerright = new ModelRenderer(this);
			bigbackflowerright.setRotationPoint(-3.4F, 9.0F, -3.0F);
			setRotationAngle(bigbackflowerright, -0.576F, 0.0F, -0.8203F);
			bigbackflowerright.setTextureOffset(29, 0).addBox(-0.4544F, -2.5093F, -0.6813F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(36, 24).addBox(-2.9457F, -2.4128F, -0.679F, 3.0F, 0.0F, 2.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(35, 6).addBox(1.4513F, -2.432F, -0.6915F, 3.0F, 0.0F, 2.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(39, 18).addBox(-0.4888F, -2.4799F, 0.9467F, 2.0F, 0.0F, 3.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(37, 39).addBox(-0.4978F, -2.3297F, -3.5328F, 2.0F, 0.0F, 3.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(35, 21).addBox(0.7742F, -2.3469F, -3.4248F, 3.0F, 0.0F, 3.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(34, 3).addBox(-2.419F, -2.3518F, -3.428F, 3.0F, 0.0F, 3.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(34, 0).addBox(-2.5464F, -2.4613F, 0.7203F, 3.0F, 0.0F, 3.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(33, 18).addBox(0.7881F, -2.4522F, 0.7262F, 3.0F, 0.0F, 3.0F, 0.0F, false);
			bigbackflowerleft = new ModelRenderer(this);
			bigbackflowerleft.setRotationPoint(2.6456F, 8.4907F, -2.8813F);
			setRotationAngle(bigbackflowerleft, -0.576F, 0.0F, 0.8203F);
			bigbackflowerleft.setTextureOffset(0, 15).addBox(-0.8875F, -2.5669F, -1.1753F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(30, 24).addBox(0.8192F, -2.4875F, -1.1769F, 3.0F, 0.0F, 2.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(22, 21).addBox(-3.8498F, -2.4694F, -1.1652F, 3.0F, 0.0F, 2.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(33, 39).addBox(-0.9008F, -2.5558F, 0.3288F, 2.0F, 0.0F, 3.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(29, 39).addBox(-0.9066F, -2.4345F, -3.8849F, 2.0F, 0.0F, 3.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(29, 21).addBox(-3.5861F, -2.3674F, -0.1451F, 3.0F, 0.0F, 3.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(27, 18).addBox(-3.509F, -2.442F, -3.4129F, 3.0F, 0.0F, 3.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(21, 18).addBox(0.4534F, -2.4393F, -3.4111F, 3.0F, 0.0F, 3.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(21, 15).addBox(0.4444F, -2.5351F, -0.0154F, 3.0F, 0.0F, 3.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			head.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			mane.render(matrixStack, buffer, packedLight, packedOverlay);
			back_left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
			back_right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
			front_left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
			front_right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
			tail.render(matrixStack, buffer, packedLight, packedOverlay);
			NeckFlowers.render(matrixStack, buffer, packedLight, packedOverlay);
			SideNeckFlowers.render(matrixStack, buffer, packedLight, packedOverlay);
			SideNeckFlowers2.render(matrixStack, buffer, packedLight, packedOverlay);
			SideNeckFlowers3.render(matrixStack, buffer, packedLight, packedOverlay);
			SideNeckFlowers4.render(matrixStack, buffer, packedLight, packedOverlay);
			SideNeckFlowers5.render(matrixStack, buffer, packedLight, packedOverlay);
			SideNeckFlowers6.render(matrixStack, buffer, packedLight, packedOverlay);
			SideNeckFlowers7.render(matrixStack, buffer, packedLight, packedOverlay);
			middleBackFlowers.render(matrixStack, buffer, packedLight, packedOverlay);
			rightbackflowers.render(matrixStack, buffer, packedLight, packedOverlay);
			leftbackflowers.render(matrixStack, buffer, packedLight, packedOverlay);
			bigbackflowerright.render(matrixStack, buffer, packedLight, packedOverlay);
			bigbackflowerleft.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.back_right_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.front_left_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.back_left_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.front_right_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.tail.rotateAngleY = f4 / (180F / (float) Math.PI);
		}
	}
}
