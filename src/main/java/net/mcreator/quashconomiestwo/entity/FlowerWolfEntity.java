
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
		private final ModelRenderer RightEar;
		private final ModelRenderer earpoint;
		private final ModelRenderer earpoint2;
		private final ModelRenderer earpoint3;
		private final ModelRenderer LeftEar;
		private final ModelRenderer earpoint6;
		private final ModelRenderer earpoint5;
		private final ModelRenderer earpoint4;
		private final ModelRenderer NeckFlower;
		private final ModelRenderer SideNeckFlowers;
		private final ModelRenderer NeckFlowers;
		private final ModelRenderer SideNeckFlowers2;
		private final ModelRenderer SideNeckFlowers3;
		private final ModelRenderer SideNeckFlowers6;
		private final ModelRenderer SideNeckFlowers7;
		private final ModelRenderer SideNeckFlowers5;
		private final ModelRenderer SideNeckFlowers4;
		private final ModelRenderer Thing;
		private final ModelRenderer AnotherThing;
		private final ModelRenderer head_r1;
		private final ModelRenderer Thing3;
		private final ModelRenderer head_r2;
		private final ModelRenderer tail;
		private final ModelRenderer TailFlowersleft;
		private final ModelRenderer lefttailflower4_r1;
		private final ModelRenderer TailFlowersRight2;
		private final ModelRenderer righttailflower4_r1;
		private final ModelRenderer bigbackflowerright;
		private final ModelRenderer bigbackflowerleft;
		private final ModelRenderer body;
		private final ModelRenderer back;
		private final ModelRenderer body_rotation;
		private final ModelRenderer body_sub_1;
		private final ModelRenderer front;
		private final ModelRenderer mane_rotation;
		private final ModelRenderer mane_sub_1;
		private final ModelRenderer BackLeftLeg;
		private final ModelRenderer BackRightLeg;
		private final ModelRenderer FrontLeftLEg;
		private final ModelRenderer FrontRightLeg;
		private final ModelRenderer LowerBackFlowers;
		private final ModelRenderer backleftbackflowers;
		private final ModelRenderer backrightbackflowers;
		private final ModelRenderer backmiddleBackFlowers;
		private final ModelRenderer UpperBackFlowers;
		private final ModelRenderer frontmiddleBackFlowers;
		private final ModelRenderer frontrightbackflowers;
		private final ModelRenderer frontleftbackflowers;
		public Modelflower_wolf() {
			textureWidth = 128;
			textureHeight = 128;
			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, 8.7F, -7.0F);
			head.setTextureOffset(34, 34).addBox(-3.5F, -3.2F, -4.7F, 7.0F, 7.0F, 6.0F, 0.0F, false);
			head.setTextureOffset(0, 38).addBox(-2.0F, 0.68F, -8.5F, 4.0F, 3.0F, 8.0F, 0.0F, false);
			RightEar = new ModelRenderer(this);
			RightEar.setRotationPoint(0.0F, 13.5F, 7.0F);
			head.addChild(RightEar);
			RightEar.setTextureOffset(0, 38).addBox(1.0F, -17.8F, -8.7F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			earpoint = new ModelRenderer(this);
			earpoint.setRotationPoint(1.7F, -16.7F, -9.0F);
			RightEar.addChild(earpoint);
			setRotationAngle(earpoint, 0.0F, 0.0F, -0.8378F);
			earpoint.setTextureOffset(54, 32).addBox(0.3491F, -1.2562F, 0.3F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			earpoint2 = new ModelRenderer(this);
			earpoint2.setRotationPoint(2.3054F, -16.6122F, -9.0F);
			RightEar.addChild(earpoint2);
			setRotationAngle(earpoint2, 0.0F, 0.0F, -0.6458F);
			earpoint2.setTextureOffset(54, 20).addBox(0.2695F, -1.5306F, 0.3F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			earpoint3 = new ModelRenderer(this);
			earpoint3.setRotationPoint(2.039F, -16.9206F, -9.0F);
			RightEar.addChild(earpoint3);
			setRotationAngle(earpoint3, 0.0F, 0.0F, 0.8203F);
			earpoint3.setTextureOffset(28, 38).addBox(-1.3789F, -1.3537F, 0.3F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			LeftEar = new ModelRenderer(this);
			LeftEar.setRotationPoint(0.0F, 13.5F, 7.0F);
			head.addChild(LeftEar);
			LeftEar.setTextureOffset(0, 23).addBox(-3.0F, -17.8F, -8.7F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			earpoint6 = new ModelRenderer(this);
			earpoint6.setRotationPoint(-2.061F, -16.8706F, -9.0F);
			LeftEar.addChild(earpoint6);
			setRotationAngle(earpoint6, 0.0F, 0.0F, 0.8029F);
			earpoint6.setTextureOffset(42, 25).addBox(-1.3208F, -0.9702F, 0.3F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			earpoint5 = new ModelRenderer(this);
			earpoint5.setRotationPoint(-1.9111F, -16.5622F, -9.0F);
			LeftEar.addChild(earpoint5);
			setRotationAngle(earpoint5, 0.0F, 0.0F, 0.7679F);
			earpoint5.setTextureOffset(8, 54).addBox(-1.2045F, -1.5233F, 0.3F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			earpoint4 = new ModelRenderer(this);
			earpoint4.setRotationPoint(-2.3237F, -16.6635F, -9.0F);
			LeftEar.addChild(earpoint4);
			setRotationAngle(earpoint4, 0.0F, 0.0F, 0.7679F);
			earpoint4.setTextureOffset(42, 47).addBox(-1.1897F, -1.7453F, 0.3F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			NeckFlower = new ModelRenderer(this);
			NeckFlower.setRotationPoint(0.0F, 15.3F, 6.9F);
			head.addChild(NeckFlower);
			SideNeckFlowers = new ModelRenderer(this);
			SideNeckFlowers.setRotationPoint(2.45F, -19.2F, -8.0F);
			NeckFlower.addChild(SideNeckFlowers);
			setRotationAngle(SideNeckFlowers, 0.0F, 0.0F, 0.2618F);
			SideNeckFlowers.setTextureOffset(54, 35).addBox(-0.8106F, -2.6591F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			NeckFlowers = new ModelRenderer(this);
			NeckFlowers.setRotationPoint(-0.5F, -17.9F, 0.5F);
			NeckFlower.addChild(NeckFlowers);
			NeckFlowers.setTextureOffset(55, 47).addBox(-0.9F, -4.1F, -7.2F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			NeckFlowers.setTextureOffset(0, 19).addBox(0.85F, -4.1F, -7.2F, 1.0F, 4.0F, 0.0F, 0.0F, false);
			NeckFlowers.setTextureOffset(52, 5).addBox(1.65F, -3.8F, -7.2F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			NeckFlowers.setTextureOffset(30, 53).addBox(0.25F, -3.5F, -7.2F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			NeckFlowers.setTextureOffset(52, 10).addBox(-0.35F, -3.0F, -7.2F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			NeckFlowers.setTextureOffset(10, 49).addBox(-1.35F, -3.6F, -7.2F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			NeckFlowers.setTextureOffset(2, 49).addBox(-2.35F, -4.1F, -7.2F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers2 = new ModelRenderer(this);
			SideNeckFlowers2.setRotationPoint(3.1783F, -17.8065F, -8.2F);
			NeckFlower.addChild(SideNeckFlowers2);
			setRotationAngle(SideNeckFlowers2, 0.0F, 0.0F, 0.925F);
			SideNeckFlowers2.setTextureOffset(53, 52).addBox(-1.4584F, -2.2222F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers2.setTextureOffset(52, 0).addBox(-2.698F, -2.405F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers2.setTextureOffset(30, 50).addBox(-1.8287F, -2.063F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers3 = new ModelRenderer(this);
			SideNeckFlowers3.setRotationPoint(-3.0921F, -18.0474F, -8.2F);
			NeckFlower.addChild(SideNeckFlowers3);
			setRotationAngle(SideNeckFlowers3, 0.0F, 0.0F, -0.576F);
			SideNeckFlowers3.setTextureOffset(12, 49).addBox(0.1536F, -2.5064F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers3.setTextureOffset(0, 49).addBox(1.0337F, -3.1272F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers6 = new ModelRenderer(this);
			SideNeckFlowers6.setRotationPoint(-3.3961F, -17.2221F, -8.2F);
			NeckFlower.addChild(SideNeckFlowers6);
			setRotationAngle(SideNeckFlowers6, 0.0F, 0.0F, -0.9599F);
			SideNeckFlowers6.setTextureOffset(42, 29).addBox(0.483F, -2.1883F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers7 = new ModelRenderer(this);
			SideNeckFlowers7.setRotationPoint(-3.3961F, -16.4221F, -8.2F);
			NeckFlower.addChild(SideNeckFlowers7);
			setRotationAngle(SideNeckFlowers7, 0.0F, 0.0F, 1.85F);
			SideNeckFlowers7.setTextureOffset(42, 19).addBox(-1.6535F, -1.1692F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers5 = new ModelRenderer(this);
			SideNeckFlowers5.setRotationPoint(3.3783F, -16.6065F, -8.2F);
			NeckFlower.addChild(SideNeckFlowers5);
			setRotationAngle(SideNeckFlowers5, 0.0F, 0.0F, 1.6057F);
			SideNeckFlowers5.setTextureOffset(32, 44).addBox(-1.6993F, -1.4581F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			SideNeckFlowers4 = new ModelRenderer(this);
			SideNeckFlowers4.setRotationPoint(3.1783F, -17.2065F, -8.2F);
			NeckFlower.addChild(SideNeckFlowers4);
			setRotationAngle(SideNeckFlowers4, 0.0F, 0.0F, 1.2741F);
			SideNeckFlowers4.setTextureOffset(44, 19).addBox(-1.6476F, -1.8508F, 1.3F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			Thing = new ModelRenderer(this);
			Thing.setRotationPoint(0.0F, 0.08F, -3.0F);
			head.addChild(Thing);
			setRotationAngle(Thing, -0.6109F, 0.0F, 0.0F);
			Thing.setTextureOffset(0, 38).addBox(-2.0F, 0.8276F, -1.4958F, 4.0F, 1.0F, 5.0F, 0.0F, false);
			AnotherThing = new ModelRenderer(this);
			AnotherThing.setRotationPoint(1.0F, 3.0F, 0.0F);
			head.addChild(AnotherThing);
			head_r1 = new ModelRenderer(this);
			head_r1.setRotationPoint(-2.9598F, -0.3126F, -4.6507F);
			AnotherThing.addChild(head_r1);
			setRotationAngle(head_r1, 0.8552F, 0.0F, -1.5882F);
			head_r1.setTextureOffset(0, 38).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);
			Thing3 = new ModelRenderer(this);
			Thing3.setRotationPoint(1.0F, 3.0F, 0.0F);
			head.addChild(Thing3);
			head_r2 = new ModelRenderer(this);
			head_r2.setRotationPoint(0.9773F, -1.3125F, -4.6493F);
			Thing3.addChild(head_r2);
			setRotationAngle(head_r2, -0.8552F, 0.0F, 1.5882F);
			head_r2.setTextureOffset(0, 38).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);
			tail = new ModelRenderer(this);
			tail.setRotationPoint(0.0F, 10.1F, 8.05F);
			setRotationAngle(tail, 1.2741F, 0.0F, 0.0F);
			tail.setTextureOffset(0, 54).addBox(-1.0F, -0.0232F, -0.6828F, 2.0F, 7.0F, 2.0F, 0.0F, false);
			TailFlowersleft = new ModelRenderer(this);
			TailFlowersleft.setRotationPoint(-0.4F, 2.906F, -2.2624F);
			tail.addChild(TailFlowersleft);
			setRotationAngle(TailFlowersleft, -1.3439F, -0.2443F, 0.0873F);
			lefttailflower4_r1 = new ModelRenderer(this);
			lefttailflower4_r1.setRotationPoint(0.5831F, -2.2701F, -0.0942F);
			TailFlowersleft.addChild(lefttailflower4_r1);
			setRotationAngle(lefttailflower4_r1, -1.1868F, -0.1745F, 0.0F);
			lefttailflower4_r1.setTextureOffset(16, 38).addBox(0.5601F, -4.2396F, 0.2554F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			lefttailflower4_r1.setTextureOffset(40, 5).addBox(0.294F, -3.3121F, -0.4718F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			lefttailflower4_r1.setTextureOffset(40, 29).addBox(0.0037F, -2.2901F, -1.2209F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			lefttailflower4_r1.setTextureOffset(42, 0).addBox(-0.3592F, -0.9394F, -1.8407F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			TailFlowersRight2 = new ModelRenderer(this);
			TailFlowersRight2.setRotationPoint(0.1F, 2.906F, -2.2624F);
			tail.addChild(TailFlowersRight2);
			setRotationAngle(TailFlowersRight2, 0.0F, 0.0F, -0.1047F);
			righttailflower4_r1 = new ModelRenderer(this);
			righttailflower4_r1.setRotationPoint(-0.5643F, -0.4786F, 1.7497F);
			TailFlowersRight2.addChild(righttailflower4_r1);
			setRotationAngle(righttailflower4_r1, -2.5656F, 0.0F, -0.2967F);
			righttailflower4_r1.setTextureOffset(18, 38).addBox(-1.0297F, -4.7588F, -0.1955F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			righttailflower4_r1.setTextureOffset(40, 0).addBox(-0.6176F, -3.8775F, -0.9122F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			righttailflower4_r1.setTextureOffset(32, 41).addBox(0.0745F, -2.5585F, -1.6272F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			righttailflower4_r1.setTextureOffset(42, 5).addBox(0.5615F, -1.474F, -2.2881F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			bigbackflowerright = new ModelRenderer(this);
			bigbackflowerright.setRotationPoint(-5.7F, 5.1F, -3.0F);
			setRotationAngle(bigbackflowerright, -0.576F, 0.0F, -0.8203F);
			bigbackflowerright.setTextureOffset(33, 47).addBox(-0.5768F, -3.9037F, -0.0368F, 3.0F, 6.0F, 3.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(52, 10).addBox(-3.0681F, -3.8072F, -0.0345F, 4.0F, 1.0F, 3.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(52, 5).addBox(1.3289F, -3.8264F, -0.0469F, 4.0F, 1.0F, 3.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(0, 49).addBox(-0.6112F, -3.8743F, 1.5913F, 3.0F, 1.0F, 4.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(0, 19).addBox(-0.6202F, -3.7241F, -2.8883F, 3.0F, 0.0F, 4.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(20, 45).addBox(0.6518F, -3.7413F, -2.7802F, 4.0F, 1.0F, 4.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(42, 20).addBox(-2.5413F, -3.7462F, -2.7834F, 4.0F, 1.0F, 4.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(40, 29).addBox(-2.6688F, -3.8557F, 1.3649F, 4.0F, 1.0F, 4.0F, 0.0F, false);
			bigbackflowerright.setTextureOffset(40, 5).addBox(0.6657F, -3.8466F, 1.3707F, 4.0F, 1.0F, 4.0F, 0.0F, false);
			bigbackflowerleft = new ModelRenderer(this);
			bigbackflowerleft.setRotationPoint(3.0456F, 8.3907F, -2.8813F);
			setRotationAngle(bigbackflowerleft, -0.576F, 0.0F, 0.8203F);
			bigbackflowerleft.setTextureOffset(0, 0).addBox(-3.2542F, -7.4285F, -2.3054F, 3.0F, 6.0F, 3.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(52, 0).addBox(-1.5475F, -7.3491F, -2.3071F, 4.0F, 1.0F, 3.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(46, 25).addBox(-6.2165F, -7.331F, -2.2953F, 4.0F, 1.0F, 3.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(48, 15).addBox(-3.2675F, -7.4174F, -0.8014F, 3.0F, 1.0F, 4.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(45, 47).addBox(-3.2733F, -7.2961F, -5.0151F, 3.0F, 1.0F, 4.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(40, 0).addBox(-5.9528F, -7.229F, -1.2752F, 4.0F, 1.0F, 4.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(16, 38).addBox(-5.8757F, -7.3036F, -4.543F, 4.0F, 1.0F, 4.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(30, 24).addBox(-1.9133F, -7.3009F, -4.5413F, 4.0F, 1.0F, 4.0F, 0.0F, false);
			bigbackflowerleft.setTextureOffset(30, 19).addBox(-1.9223F, -7.3966F, -1.1456F, 4.0F, 1.0F, 4.0F, 0.0F, false);
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 24.0F, 0.0F);
			back = new ModelRenderer(this);
			back.setRotationPoint(0.0F, -10.0F, 2.0F);
			body.addChild(back);
			body_rotation = new ModelRenderer(this);
			body_rotation.setRotationPoint(0.0F, 0.0F, 0.0F);
			back.addChild(body_rotation);
			setRotationAngle(body_rotation, 1.5708F, 0.0F, 0.0F);
			body_sub_1 = new ModelRenderer(this);
			body_sub_1.setRotationPoint(0.0F, 0.0F, 0.0F);
			body_rotation.addChild(body_sub_1);
			body_sub_1.setTextureOffset(0, 19).addBox(-5.1F, -2.7F, -1.8F, 10.0F, 9.0F, 10.0F, 0.0F, false);
			front = new ModelRenderer(this);
			front.setRotationPoint(1.0F, -10.0F, 2.0F);
			body.addChild(front);
			mane_rotation = new ModelRenderer(this);
			mane_rotation.setRotationPoint(-1.0F, 2.5F, -2.5F);
			front.addChild(mane_rotation);
			setRotationAngle(mane_rotation, 1.5708F, 0.0F, 0.0F);
			mane_sub_1 = new ModelRenderer(this);
			mane_sub_1.setRotationPoint(0.0F, 0.0F, 0.0F);
			mane_rotation.addChild(mane_sub_1);
			mane_sub_1.setTextureOffset(0, 0).addBox(-7.1F, -6.2F, 0.7F, 14.0F, 7.0F, 12.0F, 0.0F, false);
			BackLeftLeg = new ModelRenderer(this);
			BackLeftLeg.setRotationPoint(3.25F, 16.0F, 6.0F);
			BackLeftLeg.setTextureOffset(53, 53).addBox(-1.0F, -1.0F, -0.7F, 2.0F, 9.0F, 2.0F, 0.0F, false);
			BackLeftLeg.setTextureOffset(52, 29).addBox(-1.0F, 7.0F, -1.7F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			BackRightLeg = new ModelRenderer(this);
			BackRightLeg.setRotationPoint(-3.5F, 16.0F, 6.5F);
			BackRightLeg.setTextureOffset(45, 52).addBox(-1.05F, -1.0F, -1.2F, 2.0F, 9.0F, 2.0F, 0.0F, false);
			BackRightLeg.setTextureOffset(16, 43).addBox(-1.05F, 7.0F, -2.2F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			FrontLeftLEg = new ModelRenderer(this);
			FrontLeftLEg.setRotationPoint(5.0F, 16.0F, -5.25F);
			FrontLeftLEg.setTextureOffset(22, 50).addBox(-0.9F, -1.0F, -0.85F, 2.0F, 9.0F, 2.0F, 0.0F, false);
			FrontLeftLEg.setTextureOffset(0, 43).addBox(-0.9F, 7.0F, -1.75F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			FrontRightLeg = new ModelRenderer(this);
			FrontRightLeg.setRotationPoint(-5.5F, 16.0F, -5.25F);
			FrontRightLeg.setTextureOffset(14, 49).addBox(-0.65F, -1.0F, -1.05F, 2.0F, 9.0F, 2.0F, 0.0F, false);
			FrontRightLeg.setTextureOffset(0, 9).addBox(-0.65F, 7.0F, -1.75F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			LowerBackFlowers = new ModelRenderer(this);
			LowerBackFlowers.setRotationPoint(0.0F, 24.0F, 0.0F);
			backleftbackflowers = new ModelRenderer(this);
			backleftbackflowers.setRotationPoint(0.963F, -21.6845F, -6.1391F);
			LowerBackFlowers.addChild(backleftbackflowers);
			setRotationAngle(backleftbackflowers, -1.1519F, 0.8552F, 0.0F);
			backleftbackflowers.setTextureOffset(38, 38).addBox(-6.1406F, -3.6798F, 4.2618F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			backleftbackflowers.setTextureOffset(36, 38).addBox(-6.9708F, -4.1764F, 4.9207F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			backleftbackflowers.setTextureOffset(34, 38).addBox(-7.8764F, -4.8956F, 5.2409F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			backleftbackflowers.setTextureOffset(32, 38).addBox(-8.7821F, -5.6148F, 5.5611F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			backleftbackflowers.setTextureOffset(28, 38).addBox(-9.8387F, -6.4539F, 5.9347F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			backleftbackflowers.setTextureOffset(0, 9).addBox(-10.7443F, -7.1731F, 6.2549F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			backrightbackflowers = new ModelRenderer(this);
			backrightbackflowers.setRotationPoint(-0.437F, -18.9845F, -6.4391F);
			LowerBackFlowers.addChild(backrightbackflowers);
			setRotationAngle(backrightbackflowers, -1.1519F, -0.8552F, 0.0F);
			backrightbackflowers.setTextureOffset(6, 43).addBox(4.783F, -5.492F, 2.3247F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			backrightbackflowers.setTextureOffset(0, 43).addBox(5.6131F, -6.0293F, 2.8923F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			backrightbackflowers.setTextureOffset(42, 25).addBox(6.5188F, -6.7485F, 3.2125F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			backrightbackflowers.setTextureOffset(42, 10).addBox(7.4245F, -7.4677F, 3.5327F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			backrightbackflowers.setTextureOffset(40, 10).addBox(8.481F, -8.3068F, 3.9063F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			backrightbackflowers.setTextureOffset(6, 9).addBox(9.3867F, -9.026F, 4.2265F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			backmiddleBackFlowers = new ModelRenderer(this);
			backmiddleBackFlowers.setRotationPoint(-0.237F, -19.3845F, -6.3391F);
			LowerBackFlowers.addChild(backmiddleBackFlowers);
			setRotationAngle(backmiddleBackFlowers, -1.0821F, 0.0F, 0.0F);
			backmiddleBackFlowers.setTextureOffset(30, 19).addBox(-0.5F, -7.4459F, 3.7278F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			backmiddleBackFlowers.setTextureOffset(8, 26).addBox(-0.5F, -8.2294F, 4.5974F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			backmiddleBackFlowers.setTextureOffset(8, 23).addBox(-0.5F, -9.2889F, 5.1607F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			backmiddleBackFlowers.setTextureOffset(2, 19).addBox(-0.5F, -10.3484F, 5.7241F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			backmiddleBackFlowers.setTextureOffset(10, 9).addBox(-0.5F, -11.5846F, 6.3814F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			backmiddleBackFlowers.setTextureOffset(0, 0).addBox(-0.5F, -12.4675F, 6.8508F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			UpperBackFlowers = new ModelRenderer(this);
			UpperBackFlowers.setRotationPoint(0.0F, 24.0F, 0.4F);
			frontmiddleBackFlowers = new ModelRenderer(this);
			frontmiddleBackFlowers.setRotationPoint(-0.237F, -19.3845F, -6.3391F);
			UpperBackFlowers.addChild(frontmiddleBackFlowers);
			setRotationAngle(frontmiddleBackFlowers, -1.0821F, 0.0F, 0.0F);
			frontmiddleBackFlowers.setTextureOffset(32, 24).addBox(-0.5F, -3.6527F, -0.2145F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			frontmiddleBackFlowers.setTextureOffset(9, 0).addBox(-0.5F, -2.5048F, -0.8248F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			frontmiddleBackFlowers.setTextureOffset(32, 19).addBox(-0.5F, -4.7122F, 0.3489F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			frontmiddleBackFlowers.setTextureOffset(30, 24).addBox(-0.5F, -5.7717F, 0.9122F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			frontmiddleBackFlowers.setTextureOffset(8, 9).addBox(-0.5F, -6.6547F, 1.3817F, 1.0F, 3.0F, 0.0F, 0.0F, false);
			frontrightbackflowers = new ModelRenderer(this);
			frontrightbackflowers.setRotationPoint(-0.437F, -18.9845F, -6.4391F);
			UpperBackFlowers.addChild(frontrightbackflowers);
			setRotationAngle(frontrightbackflowers, -1.1519F, -0.8552F, 0.0F);
			frontrightbackflowers.setTextureOffset(30, 43).addBox(0.7075F, -2.947F, -0.6693F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			frontrightbackflowers.setTextureOffset(6, 38).addBox(-0.1981F, -2.2278F, -0.9895F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			frontrightbackflowers.setTextureOffset(28, 43).addBox(1.6132F, -3.6662F, -0.3491F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			frontrightbackflowers.setTextureOffset(26, 43).addBox(2.5188F, -4.3854F, -0.0289F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			frontrightbackflowers.setTextureOffset(6, 23).addBox(3.5F, -5.1646F, 0.318F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			frontleftbackflowers = new ModelRenderer(this);
			frontleftbackflowers.setRotationPoint(0.963F, -21.6845F, -6.1391F);
			UpperBackFlowers.addChild(frontleftbackflowers);
			setRotationAngle(frontleftbackflowers, -1.1519F, 0.8552F, 0.0F);
			frontleftbackflowers.setTextureOffset(24, 43).addBox(-2.1406F, -1.1948F, 1.2945F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			frontleftbackflowers.setTextureOffset(0, 38).addBox(-1.3105F, -0.5355F, 1.001F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			frontleftbackflowers.setTextureOffset(22, 43).addBox(-3.0463F, -1.914F, 1.6147F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			frontleftbackflowers.setTextureOffset(16, 43).addBox(-4.0274F, -2.6931F, 1.9616F, 1.0F, 2.0F, 0.0F, 0.0F, false);
			frontleftbackflowers.setTextureOffset(0, 23).addBox(-4.9331F, -3.4123F, 2.2818F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			head.render(matrixStack, buffer, packedLight, packedOverlay);
			tail.render(matrixStack, buffer, packedLight, packedOverlay);
			bigbackflowerright.render(matrixStack, buffer, packedLight, packedOverlay);
			bigbackflowerleft.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			BackLeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
			BackRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
			FrontLeftLEg.render(matrixStack, buffer, packedLight, packedOverlay);
			FrontRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
			LowerBackFlowers.render(matrixStack, buffer, packedLight, packedOverlay);
			UpperBackFlowers.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.FrontRightLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.BackRightLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.BackLeftLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.tail.rotateAngleX = f3 / (180F / (float) Math.PI);
			this.FrontLeftLEg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		}
	}
}
