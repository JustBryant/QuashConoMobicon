
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
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.World;
import net.minecraft.world.IWorldReader;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ActionResultType;
import net.minecraft.tags.FluidTags;
import net.minecraft.server.Main;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.network.IPacket;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import net.mcreator.quashconomiestwo.procedures.FluffalDolphinOnEntityTickUpdateProcedure;
import net.mcreator.quashconomiestwo.block.TeaBlockBlock;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

import java.util.Map;
import java.util.HashMap;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@QuashconomiestwoModElements.ModElement.Tag
public class FluffalDolphinEntity extends QuashconomiestwoModElements.ModElement {
	public static EntityType entity = null;
	public FluffalDolphinEntity(QuashconomiestwoModElements instance) {
		super(instance, 96);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.WATER_CREATURE)
				.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new)
				.size(1f, 1f)).build("fluffal_dolphin").setRegistryName("fluffal_dolphin");
		elements.entities.add(() -> entity);
		elements.items.add(
				() -> new SpawnEggItem(entity, -65281, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("fluffal_dolphin_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
		boolean biomeCriteria = false;
		if (new ResourceLocation("ocean").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("river").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("deep_ocean").equals(event.getName()))
			biomeCriteria = true;
		if (!biomeCriteria)
			return;
		event.getSpawns().getSpawner(EntityClassification.WATER_CREATURE).add(new MobSpawnInfo.Spawners(entity, 6, 2, 4));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(this::setupAttributes);
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				SquidEntity::func_223365_b);
	}
	private static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
				return new MobRenderer(renderManager, new ModelFluffal_Dolphin(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("quashconomiestwo:textures/fluffal_dolphin.png");
					}
				};
			});
		}
	}
	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 1);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 40);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 0);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 4);
		ammma = ammma.createMutableAttribute(ForgeMod.SWIM_SPEED.get(), 1);
		GlobalEntityTypeAttributes.put(entity, ammma.create());
	}
	public static class CustomEntity extends TameableEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 25;
			setNoAI(false);
			this.moveController = new MovementController(this) {
				@Override
				public void tick() {
					if (CustomEntity.this.areEyesInFluid(FluidTags.WATER))
						CustomEntity.this.setMotion(CustomEntity.this.getMotion().add(0, 0.005, 0));
					if (this.action == MovementController.Action.MOVE_TO && !CustomEntity.this.getNavigator().noPath()) {
						double dx = this.posX - CustomEntity.this.getPosX();
						double dy = this.posY - CustomEntity.this.getPosY();
						double dz = this.posZ - CustomEntity.this.getPosZ();
						dy = dy / (double) MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
						CustomEntity.this.rotationYaw = this.limitAngle(CustomEntity.this.rotationYaw,
								(float) (MathHelper.atan2(dz, dx) * (double) (180 / (float) Math.PI)) - 90, 90);
						CustomEntity.this.renderYawOffset = CustomEntity.this.rotationYaw;
						CustomEntity.this.setAIMoveSpeed(MathHelper.lerp(0.125f, CustomEntity.this.getAIMoveSpeed(),
								(float) (this.speed * CustomEntity.this.getAttributeValue(Attributes.MOVEMENT_SPEED))));
						CustomEntity.this.setMotion(CustomEntity.this.getMotion().add(0, CustomEntity.this.getAIMoveSpeed() * dy * 0.1, 0));
					} else {
						CustomEntity.this.setAIMoveSpeed(0);
					}
				}
			};
			this.navigator = new SwimmerPathNavigator(this, this.world);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1, (float) 10, (float) 2, false));
			this.goalSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
			this.goalSelector.addGoal(3, new OwnerHurtTargetGoal(this));
			this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 0.8));
			this.goalSelector.addGoal(5, new SwimGoal(this));
			this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1, 40));
			this.targetSelector.addGoal(7, new HurtByTargetGoal(this).setCallsForHelp(this.getClass()));
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
			if (source == DamageSource.DROWN)
				return false;
			return super.attackEntityFrom(source, amount);
		}

		@Override
		public ActionResultType func_230254_b_(PlayerEntity sourceentity, Hand hand) {
			ItemStack itemstack = sourceentity.getHeldItem(hand);
			ActionResultType retval = ActionResultType.func_233537_a_(this.world.isRemote());
			Item item = itemstack.getItem();
			if (itemstack.getItem() instanceof SpawnEggItem) {
				retval = super.func_230254_b_(sourceentity, hand);
			} else if (this.world.isRemote()) {
				retval = (this.isTamed() && this.isOwner(sourceentity) || this.isBreedingItem(itemstack))
						? ActionResultType.func_233537_a_(this.world.isRemote())
						: ActionResultType.PASS;
			} else {
				if (this.isTamed()) {
					if (this.isOwner(sourceentity)) {
						if (item.isFood() && this.isBreedingItem(itemstack) && this.getHealth() < this.getMaxHealth()) {
							this.consumeItemFromStack(sourceentity, itemstack);
							this.heal((float) item.getFood().getHealing());
							retval = ActionResultType.func_233537_a_(this.world.isRemote());
						} else if (this.isBreedingItem(itemstack) && this.getHealth() < this.getMaxHealth()) {
							this.consumeItemFromStack(sourceentity, itemstack);
							this.heal(4);
							retval = ActionResultType.func_233537_a_(this.world.isRemote());
						} else {
							retval = super.func_230254_b_(sourceentity, hand);
						}
					}
				} else if (this.isBreedingItem(itemstack)) {
					this.consumeItemFromStack(sourceentity, itemstack);
					if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, sourceentity)) {
						this.setTamedBy(sourceentity);
						this.world.setEntityState(this, (byte) 7);
					} else {
						this.world.setEntityState(this, (byte) 6);
					}
					this.enablePersistence();
					retval = ActionResultType.func_233537_a_(this.world.isRemote());
				} else {
					retval = super.func_230254_b_(sourceentity, hand);
					if (retval == ActionResultType.SUCCESS || retval == ActionResultType.CONSUME)
						this.enablePersistence();
				}
			}
			sourceentity.startRiding(this);
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			return retval;
		}

		@Override
		public void baseTick() {
			super.baseTick();
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("entity", entity);
				FluffalDolphinOnEntityTickUpdateProcedure.executeProcedure($_dependencies);
			}
		}

		@Override
		public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageable) {
			CustomEntity retval = (CustomEntity) entity.create(serverWorld);
			retval.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(new BlockPos(retval.getPosition())), SpawnReason.BREEDING,
					(ILivingEntityData) null, (CompoundNBT) null);
			return retval;
		}

		@Override
		public boolean isBreedingItem(ItemStack stack) {
			if (stack == null)
				return false;
			if (new ItemStack(TeaBlockBlock.block, (int) (1)).getItem() == stack.getItem())
				return true;
			return false;
		}

		@Override
		public boolean canBreatheUnderwater() {
			return true;
		}

		@Override
		public boolean isNotColliding(IWorldReader worldreader) {
			return worldreader.checkNoEntityCollision(this, VoxelShapes.create(this.getBoundingBox()));
		}

		@Override
		public boolean isPushedByWater() {
			return false;
		}

		@Override
		public void travel(Vector3d dir) {
			Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
			if (this.isBeingRidden()) {
				this.rotationYaw = entity.rotationYaw;
				this.prevRotationYaw = this.rotationYaw;
				this.rotationPitch = entity.rotationPitch * 0.5F;
				this.setRotation(this.rotationYaw, this.rotationPitch);
				this.jumpMovementFactor = this.getAIMoveSpeed() * 0.15F;
				this.renderYawOffset = entity.rotationYaw;
				this.rotationYawHead = entity.rotationYaw;
				this.stepHeight = 1.0F;
				if (entity instanceof LivingEntity) {
					this.setAIMoveSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
					float forward = ((LivingEntity) entity).moveForward;
					float strafe = ((LivingEntity) entity).moveStrafing;
					super.travel(new Vector3d(strafe, 0, forward));
				}
				this.prevLimbSwingAmount = this.limbSwingAmount;
				double d1 = this.getPosX() - this.prevPosX;
				double d0 = this.getPosZ() - this.prevPosZ;
				float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
				if (f1 > 1.0F)
					f1 = 1.0F;
				this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
				this.limbSwing += this.limbSwingAmount;
				return;
			}
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.travel(dir);
		}
	}

	// Made with Blockbench 3.7.5
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	public static class ModelFluffal_Dolphin extends EntityModel<Entity> {
		private final ModelRenderer Main;
		private final ModelRenderer Head2;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer cube_r3;
		private final ModelRenderer cube_r4;
		private final ModelRenderer cube_r5;
		private final ModelRenderer cube_r6;
		private final ModelRenderer cube_r7;
		private final ModelRenderer cube_r8;
		private final ModelRenderer cube_r9;
		private final ModelRenderer cube_r10;
		private final ModelRenderer cube_r11;
		private final ModelRenderer cube_r12;
		private final ModelRenderer cube_r13;
		private final ModelRenderer cube_r14;
		private final ModelRenderer cube_r15;
		private final ModelRenderer cube_r16;
		private final ModelRenderer cube_r17;
		private final ModelRenderer TailFin;
		private final ModelRenderer cube_r18;
		private final ModelRenderer cube_r19;
		private final ModelRenderer cube_r20;
		private final ModelRenderer cube_r21;
		private final ModelRenderer cube_r22;
		private final ModelRenderer cube_r23;
		private final ModelRenderer cube_r24;
		private final ModelRenderer cube_r25;
		private final ModelRenderer cube_r26;
		private final ModelRenderer cube_r27;
		private final ModelRenderer cube_r28;
		private final ModelRenderer cube_r29;
		private final ModelRenderer cube_r30;
		private final ModelRenderer cube_r31;
		private final ModelRenderer cube_r32;
		private final ModelRenderer cube_r33;
		private final ModelRenderer cube_r34;
		private final ModelRenderer cube_r35;
		private final ModelRenderer cube_r36;
		private final ModelRenderer cube_r37;
		private final ModelRenderer cube_r38;
		private final ModelRenderer cube_r39;
		private final ModelRenderer cube_r40;
		private final ModelRenderer cube_r41;
		private final ModelRenderer cube_r42;
		private final ModelRenderer cube_r43;
		private final ModelRenderer cube_r44;
		private final ModelRenderer cube_r45;
		private final ModelRenderer cube_r46;
		private final ModelRenderer cube_r47;
		private final ModelRenderer cube_r48;
		private final ModelRenderer cube_r49;
		private final ModelRenderer cube_r50;
		private final ModelRenderer cube_r51;
		private final ModelRenderer cube_r52;
		private final ModelRenderer cube_r53;
		private final ModelRenderer cube_r54;
		private final ModelRenderer cube_r55;
		private final ModelRenderer cube_r56;
		private final ModelRenderer cube_r57;
		private final ModelRenderer cube_r58;
		private final ModelRenderer cube_r59;
		private final ModelRenderer cube_r60;
		private final ModelRenderer cube_r61;
		private final ModelRenderer cube_r62;
		private final ModelRenderer cube_r63;
		private final ModelRenderer cube_r64;
		private final ModelRenderer cube_r65;
		private final ModelRenderer cube_r66;
		private final ModelRenderer cube_r67;
		private final ModelRenderer Tail;
		private final ModelRenderer Body;
		private final ModelRenderer Fins;
		private final ModelRenderer cube_r68;
		private final ModelRenderer cube_r69;
		private final ModelRenderer cube_r70;
		private final ModelRenderer cube_r71;
		private final ModelRenderer Floatie;
		public ModelFluffal_Dolphin() {
			textureWidth = 256;
			textureHeight = 256;
			Main = new ModelRenderer(this);
			Main.setRotationPoint(7.5F, 24.0F, -16.3F);
			Head2 = new ModelRenderer(this);
			Head2.setRotationPoint(-7.3876F, -10.7257F, 7.1092F);
			Main.addChild(Head2);
			Head2.setTextureOffset(135, 15).addBox(-0.6124F, 4.4257F, -18.7092F, 1.0F, 1.0F, 7.0F, 0.0F, false);
			Head2.setTextureOffset(61, 16).addBox(1.9876F, 1.2274F, -12.0531F, 2.0F, 1.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(61, 16).addBox(-4.2155F, 1.2118F, -11.9281F, 2.0F, 1.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(16, 105).addBox(1.9903F, 2.2257F, -12.6248F, 2.0F, 4.0F, 11.0F, 0.0F, false);
			Head2.setTextureOffset(37, 1).addBox(1.8876F, 5.4257F, -12.8092F, 1.0F, 1.0F, 23.0F, 0.0F, false);
			Head2.setTextureOffset(8, 216).addBox(-3.9124F, 5.5257F, -12.8092F, 7.0F, 1.0F, 23.0F, 0.0F, false);
			Head2.setTextureOffset(148, 89).addBox(1.5876F, 5.4257F, -13.5092F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(148, 97).addBox(1.3876F, 5.4257F, -13.9092F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(149, 122).addBox(1.1876F, 5.4257F, -14.6092F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(5, 164).addBox(0.9876F, 5.4257F, -15.2092F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(13, 164).addBox(0.7876F, 5.4257F, -15.7092F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(21, 164).addBox(0.5876F, 5.4257F, -16.2092F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(165, 43).addBox(0.3876F, 5.4257F, -16.7092F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(17, 24).addBox(0.3876F, 5.2257F, -17.4092F, 1.0F, 1.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(34, 4).addBox(-1.6061F, 5.0257F, -17.9092F, 3.0F, 1.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(113, 164).addBox(0.3876F, 4.7257F, -18.6092F, 1.0F, 1.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(87, 74).addBox(-1.6124F, 4.7257F, -18.6092F, 2.0F, 1.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(34, 1).addBox(-2.1124F, 5.2257F, -17.4092F, 3.0F, 1.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(164, 132).addBox(-2.3124F, 5.4257F, -16.7092F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(141, 153).addBox(-2.5124F, 5.4257F, -16.4092F, 4.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(115, 153).addBox(-2.9124F, 5.4257F, -15.7092F, 4.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(20, 137).addBox(-3.1124F, 5.4257F, -15.2092F, 4.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(64, 164).addBox(-3.6124F, 5.4257F, -13.9092F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(56, 164).addBox(-3.8124F, 5.4257F, -13.5092F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(87, 164).addBox(-3.3124F, 5.4257F, -14.6092F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(165, 17).addBox(-2.7124F, 5.4257F, -16.2092F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(0, 24).addBox(-4.2151F, 2.2257F, -12.6248F, 5.0F, 4.0F, 23.0F, 0.0F, false);
			Head2.setTextureOffset(62, 62).addBox(-4.1124F, 5.4257F, -12.8092F, 1.0F, 1.0F, 23.0F, 0.0F, false);
			Head2.setTextureOffset(72, 136).addBox(-4.5124F, -2.2132F, -10.4531F, 5.0F, 7.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(137, 108).addBox(-4.8124F, -2.4132F, -10.0531F, 5.0F, 7.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(171, 199).addBox(-4.8124F, -3.6132F, -8.4531F, 5.0F, 8.0F, 19.0F, 0.0F, false);
			Head2.setTextureOffset(0, 50).addBox(-4.5124F, -4.0132F, -8.3531F, 5.0F, 9.0F, 19.0F, 0.0F, false);
			Head2.setTextureOffset(0, 8).addBox(-4.5124F, -3.9132F, -8.9531F, 5.0F, 6.0F, 6.0F, 0.0F, false);
			Head2.setTextureOffset(57, 105).addBox(-4.5124F, -3.7132F, -9.6531F, 5.0F, 6.0F, 7.0F, 0.0F, false);
			Head2.setTextureOffset(56, 68).addBox(-4.5124F, -2.7132F, -10.2531F, 5.0F, 5.0F, 8.0F, 0.0F, false);
			Head2.setTextureOffset(0, 104).addBox(-1.0124F, -1.6132F, -11.0531F, 5.0F, 3.0F, 8.0F, 0.0F, false);
			Head2.setTextureOffset(0, 104).addBox(-4.2311F, -1.6132F, -11.0531F, 5.0F, 3.0F, 8.0F, 0.0F, false);
			Head2.setTextureOffset(38, 97).addBox(-0.8124F, -2.7132F, -10.2531F, 5.0F, 5.0F, 8.0F, 0.0F, false);
			Head2.setTextureOffset(40, 110).addBox(-0.8124F, -3.7132F, -9.6531F, 5.0F, 6.0F, 7.0F, 0.0F, false);
			Head2.setTextureOffset(71, 192).addBox(-0.8124F, -3.9132F, -8.9531F, 5.0F, 6.0F, 6.0F, 0.0F, false);
			Head2.setTextureOffset(216, 166).addBox(-0.8124F, -4.2132F, -8.3531F, 5.0F, 6.0F, 12.0F, 0.0F, false);
			Head2.setTextureOffset(137, 123).addBox(-0.6124F, -2.4132F, -10.0531F, 5.0F, 7.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(166, 211).addBox(-0.6124F, -3.6132F, -8.0531F, 5.0F, 8.0F, 19.0F, 0.0F, false);
			Head2.setTextureOffset(210, 189).addBox(-0.8124F, -4.8132F, -7.3531F, 5.0F, 7.0F, 18.0F, 0.0F, false);
			Head2.setTextureOffset(203, 99).addBox(-0.8124F, -4.0132F, -8.3531F, 5.0F, 9.0F, 19.0F, 0.0F, false);
			Head2.setTextureOffset(57, 86).addBox(-4.2124F, -5.0132F, -7.0531F, 8.0F, 2.0F, 18.0F, 0.0F, false);
			Head2.setTextureOffset(4, 189).addBox(-1.1155F, -5.4743F, -0.3092F, 2.0F, 1.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(0, 188).addBox(-0.9124F, -5.8743F, -0.1092F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(129, 0).addBox(-0.9124F, -6.6743F, 0.6908F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(168, 23).addBox(-0.9124F, -7.3743F, 1.3908F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(168, 9).addBox(-0.9124F, -6.9743F, 0.9908F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(168, 97).addBox(-0.8124F, -7.4743F, 1.2908F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(168, 113).addBox(-0.7124F, -7.1743F, 0.7908F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(168, 47).addBox(-0.8124F, -7.0743F, 0.8908F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(148, 54).addBox(-0.8124F, -6.7743F, 0.5908F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(148, 70).addBox(-0.7124F, -6.8743F, 0.4908F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(42, 138).addBox(-0.8124F, -6.3743F, 0.1908F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(148, 62).addBox(-0.7124F, -6.4743F, 0.0908F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(77, 81).addBox(-0.8124F, -5.9743F, -0.2092F, 1.0F, 1.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(23, 198).addBox(-0.7124F, -6.0743F, -0.3092F, 1.0F, 1.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(23, 198).addBox(-0.7124F, -5.6743F, -0.7092F, 1.0F, 1.0F, 4.0F, 0.0F, false);
			Head2.setTextureOffset(134, 160).addBox(-0.6124F, -5.2743F, -1.2092F, 1.0F, 2.0F, 5.0F, 0.0F, false);
			Head2.setTextureOffset(8, 164).addBox(-0.6124F, -5.7743F, -0.8092F, 1.0F, 3.0F, 4.0F, 0.0F, false);
			Head2.setTextureOffset(0, 164).addBox(-0.6124F, -5.8743F, -0.6092F, 1.0F, 3.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(163, 155).addBox(-0.6124F, -5.9743F, -0.5092F, 1.0F, 3.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(162, 91).addBox(-0.6124F, -6.1743F, -0.3092F, 1.0F, 3.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(162, 56).addBox(-0.6124F, -6.3743F, -0.1092F, 1.0F, 3.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(53, 7).addBox(-0.6124F, -7.2743F, 0.7908F, 1.0F, 4.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(133, 166).addBox(-0.6124F, -7.6743F, 1.1908F, 1.0F, 5.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(106, 166).addBox(-0.6124F, -7.9743F, 1.4908F, 1.0F, 5.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(168, 154).addBox(-0.7124F, -7.5743F, 1.1908F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(165, 104).addBox(-0.6124F, -8.0743F, 1.5908F, 1.0F, 5.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(125, 166).addBox(-0.6124F, -7.8743F, 1.3908F, 1.0F, 5.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(129, 166).addBox(-0.6124F, -7.7743F, 1.2908F, 1.0F, 5.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(115, 100).addBox(-0.6124F, -7.5743F, 1.0908F, 1.0F, 4.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(123, 55).addBox(-0.6124F, -7.4743F, 0.9908F, 1.0F, 4.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(124, 66).addBox(-0.6124F, -7.3743F, 0.8908F, 1.0F, 4.0F, 1.0F, 0.0F, false);
			Head2.setTextureOffset(62, 10).addBox(-0.6124F, -7.1743F, 0.6908F, 1.0F, 4.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(73, 0).addBox(-0.6124F, -7.0743F, 0.5908F, 1.0F, 4.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(73, 10).addBox(-0.6124F, -6.9743F, 0.4908F, 1.0F, 4.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(0, 76).addBox(-0.6124F, -6.8743F, 0.3908F, 1.0F, 4.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(11, 76).addBox(-0.6124F, -6.7743F, 0.2908F, 1.0F, 4.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(28, 76).addBox(-0.6124F, -6.6743F, 0.1908F, 1.0F, 4.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(0, 84).addBox(-0.6124F, -6.5743F, 0.0908F, 1.0F, 3.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(11, 84).addBox(-0.6124F, -6.4743F, -0.0092F, 1.0F, 3.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(162, 64).addBox(-0.6124F, -6.2743F, -0.2092F, 1.0F, 3.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(163, 163).addBox(-0.6124F, -6.0743F, -0.4092F, 1.0F, 3.0F, 3.0F, 0.0F, false);
			Head2.setTextureOffset(106, 160).addBox(-0.6124F, -5.4743F, -1.0092F, 1.0F, 2.0F, 4.0F, 0.0F, false);
			Head2.setTextureOffset(124, 160).addBox(-0.6124F, -5.3743F, -1.1092F, 1.0F, 2.0F, 4.0F, 0.0F, false);
			Head2.setTextureOffset(160, 148).addBox(-0.6124F, -5.1743F, -1.3092F, 1.0F, 2.0F, 4.0F, 0.0F, false);
			Head2.setTextureOffset(2, 2).addBox(-0.6124F, -5.5743F, -0.9092F, 1.0F, 1.0F, 4.0F, 0.0F, false);
			Head2.setTextureOffset(10, 124).addBox(-0.8124F, -6.2743F, 0.1908F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Head2.setTextureOffset(159, 115).addBox(-0.6124F, -5.6743F, -0.9092F, 1.0F, 2.0F, 4.0F, 0.0F, false);
			Head2.setTextureOffset(33, 8).addBox(2.8876F, 2.2868F, -7.3531F, 1.0F, 3.0F, 17.0F, 0.0F, false);
			Head2.setTextureOffset(0, 76).addBox(-4.5124F, -4.8132F, -7.4531F, 5.0F, 7.0F, 18.0F, 0.0F, false);
			Head2.setTextureOffset(87, 67).addBox(-4.5124F, -4.2132F, -8.4531F, 5.0F, 6.0F, 12.0F, 0.0F, false);
			cube_r1 = new ModelRenderer(this);
			cube_r1.setRotationPoint(-13.361F, -3.5743F, -8.8364F);
			Head2.addChild(cube_r1);
			setRotationAngle(cube_r1, 0.0F, -0.3927F, 0.0F);
			cube_r1.setTextureOffset(56, 93).addBox(7.0F, 7.0F, -13.6F, 3.0F, 2.0F, 6.0F, 0.0F, false);
			cube_r2 = new ModelRenderer(this);
			cube_r2.setRotationPoint(-13.361F, -4.5743F, -8.8364F);
			Head2.addChild(cube_r2);
			setRotationAngle(cube_r2, 0.0F, -0.3927F, 0.0F);
			cube_r2.setTextureOffset(134, 135).addBox(7.0F, 7.2F, -11.6F, 4.0F, 2.0F, 4.0F, 0.0F, false);
			cube_r3 = new ModelRenderer(this);
			cube_r3.setRotationPoint(-13.3227F, -5.3743F, -8.9288F);
			Head2.addChild(cube_r3);
			setRotationAngle(cube_r3, 0.0F, -0.3927F, 0.0F);
			cube_r3.setTextureOffset(192, 69).addBox(7.0F, 7.6F, -10.9F, 4.0F, 3.0F, 4.0F, 0.0F, false);
			cube_r4 = new ModelRenderer(this);
			cube_r4.setRotationPoint(-9.4124F, -8.3229F, -8.8364F);
			Head2.addChild(cube_r4);
			setRotationAngle(cube_r4, 0.3927F, 0.0F, 0.0F);
			cube_r4.setTextureOffset(43, 37).addBox(7.1F, 7.0F, -12.2F, 1.0F, 2.0F, 6.0F, 0.0F, false);
			cube_r4.setTextureOffset(94, 101).addBox(6.2F, 7.0F, -9.7F, 1.0F, 2.0F, 4.0F, 0.0F, false);
			cube_r4.setTextureOffset(15, 116).addBox(5.4F, 7.0F, -7.9F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r4.setTextureOffset(16, 119).addBox(12.2F, 7.0F, -7.9F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r4.setTextureOffset(30, 103).addBox(11.4F, 7.0F, -9.7F, 1.0F, 2.0F, 4.0F, 0.0F, false);
			cube_r4.setTextureOffset(10, 36).addBox(10.5F, 7.0F, -12.2F, 1.0F, 2.0F, 6.0F, 0.0F, false);
			cube_r4.setTextureOffset(0, 36).addBox(7.8F, 7.0F, -14.0F, 1.0F, 2.0F, 8.0F, 0.0F, false);
			cube_r4.setTextureOffset(37, 13).addBox(8.8F, 7.0F, -14.0F, 1.0F, 2.0F, 8.0F, 0.0F, false);
			cube_r4.setTextureOffset(33, 37).addBox(9.8F, 7.0F, -14.0F, 1.0F, 2.0F, 8.0F, 0.0F, false);
			cube_r5 = new ModelRenderer(this);
			cube_r5.setRotationPoint(-7.6124F, 7.421F, -19.9633F);
			Head2.addChild(cube_r5);
			setRotationAngle(cube_r5, -3.0892F, 0.0F, 0.0F);
			cube_r5.setTextureOffset(0, 137).addBox(3.4F, 9.7F, -13.7F, 8.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r6 = new ModelRenderer(this);
			cube_r6.setRotationPoint(-7.6124F, 13.837F, -11.2727F);
			Head2.addChild(cube_r6);
			setRotationAngle(cube_r6, -2.3911F, 0.0F, 0.0F);
			cube_r6.setTextureOffset(42, 154).addBox(3.4F, 9.7F, -14.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r7 = new ModelRenderer(this);
			cube_r7.setRotationPoint(-7.6124F, 12.259F, -14.0176F);
			Head2.addChild(cube_r7);
			setRotationAngle(cube_r7, -2.6093F, 0.0F, 0.0F);
			cube_r7.setTextureOffset(151, 129).addBox(3.4F, 9.7F, -14.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r8 = new ModelRenderer(this);
			cube_r8.setRotationPoint(-7.6124F, 12.4112F, -14.2761F);
			Head2.addChild(cube_r8);
			setRotationAngle(cube_r8, -2.6093F, 0.0F, 0.0F);
			cube_r8.setTextureOffset(151, 129).addBox(3.4F, 9.7F, -14.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r9 = new ModelRenderer(this);
			cube_r9.setRotationPoint(-7.6124F, 16.1717F, -8.7823F);
			Head2.addChild(cube_r9);
			setRotationAngle(cube_r9, -2.1293F, 0.0F, 0.0F);
			cube_r9.setTextureOffset(152, 10).addBox(3.4F, 8.7F, -14.7F, 8.0F, 3.0F, 1.0F, 0.0F, false);
			cube_r10 = new ModelRenderer(this);
			cube_r10.setRotationPoint(-7.6124F, 14.7236F, -12.2234F);
			Head2.addChild(cube_r10);
			setRotationAngle(cube_r10, -2.3911F, 0.0F, 0.0F);
			cube_r10.setTextureOffset(154, 14).addBox(3.4F, 9.7F, -14.7F, 8.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r11 = new ModelRenderer(this);
			cube_r11.setRotationPoint(-7.6124F, 16.4262F, -8.9413F);
			Head2.addChild(cube_r11);
			setRotationAngle(cube_r11, -2.1293F, 0.0F, 0.0F);
			cube_r11.setTextureOffset(152, 10).addBox(3.4F, 8.7F, -14.7F, 8.0F, 3.0F, 1.0F, 0.0F, false);
			cube_r12 = new ModelRenderer(this);
			cube_r12.setRotationPoint(-7.6124F, 13.4827F, -1.038F);
			Head2.addChild(cube_r12);
			setRotationAngle(cube_r12, -1.6493F, 0.0F, 0.0F);
			cube_r12.setTextureOffset(5, 76).addBox(3.4F, 8.7F, -14.0F, 8.0F, 3.0F, 2.0F, 0.0F, false);
			cube_r13 = new ModelRenderer(this);
			cube_r13.setRotationPoint(-0.2124F, 0.8231F, -11.2106F);
			Head2.addChild(cube_r13);
			setRotationAngle(cube_r13, 0.7854F, 0.0F, 0.0F);
			cube_r13.setTextureOffset(129, 45).addBox(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r14 = new ModelRenderer(this);
			cube_r14.setRotationPoint(-7.7124F, -9.2229F, -6.3364F);
			Head2.addChild(cube_r14);
			setRotationAngle(cube_r14, 0.3927F, 0.0F, 0.0F);
			cube_r14.setTextureOffset(0, 76).addBox(8.0F, 7.0F, -14.0F, 2.0F, 1.0F, 7.0F, 0.0F, false);
			cube_r15 = new ModelRenderer(this);
			cube_r15.setRotationPoint(-8.2124F, -8.4229F, -7.6364F);
			Head2.addChild(cube_r15);
			setRotationAngle(cube_r15, 0.3927F, 0.0F, 0.0F);
			cube_r15.setTextureOffset(28, 76).addBox(8.0F, 7.0F, -14.0F, 2.0F, 1.0F, 7.0F, 0.0F, false);
			cube_r16 = new ModelRenderer(this);
			cube_r16.setRotationPoint(-0.722F, -5.3743F, -3.0961F);
			Head2.addChild(cube_r16);
			setRotationAngle(cube_r16, 0.0F, 0.3927F, 0.0F);
			cube_r16.setTextureOffset(154, 158).addBox(5.0F, 7.6F, -10.8F, 3.0F, 3.0F, 4.0F, 0.0F, false);
			cube_r16.setTextureOffset(155, 18).addBox(5.0F, 8.0F, -11.8F, 3.0F, 2.0F, 5.0F, 0.0F, false);
			cube_r16.setTextureOffset(56, 136).addBox(6.0F, 8.8F, -13.5F, 2.0F, 2.0F, 6.0F, 0.0F, false);
			cube_r16.setTextureOffset(116, 106).addBox(6.0F, 9.8F, -14.0F, 2.0F, 1.0F, 7.0F, 0.0F, false);
			cube_r17 = new ModelRenderer(this);
			cube_r17.setRotationPoint(-13.361F, -2.5743F, -8.8364F);
			Head2.addChild(cube_r17);
			setRotationAngle(cube_r17, 0.0F, -0.3927F, 0.0F);
			cube_r17.setTextureOffset(81, 105).addBox(7.0F, 7.0F, -14.0F, 3.0F, 1.0F, 7.0F, 0.0F, false);
			TailFin = new ModelRenderer(this);
			TailFin.setRotationPoint(-7.55F, -9.55F, 42.3281F);
			Main.addChild(TailFin);
			TailFin.setTextureOffset(86, 136).addBox(-1.15F, -0.85F, -2.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			TailFin.setTextureOffset(66, 136).addBox(0.25F, -0.85F, -2.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			TailFin.setTextureOffset(137, 18).addBox(0.25F, -0.45F, 0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			TailFin.setTextureOffset(165, 115).addBox(-1.15F, -1.25F, 0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			TailFin.setTextureOffset(153, 33).addBox(0.25F, -1.25F, 0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			TailFin.setTextureOffset(167, 30).addBox(0.25F, -1.05F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			TailFin.setTextureOffset(145, 168).addBox(-1.15F, -1.05F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			TailFin.setTextureOffset(40, 167).addBox(0.25F, -0.65F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			TailFin.setTextureOffset(114, 168).addBox(-1.15F, -0.65F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			TailFin.setTextureOffset(158, 115).addBox(-1.15F, -0.45F, 0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r18 = new ModelRenderer(this);
			cube_r18.setRotationPoint(7.9842F, -4.85F, -6.6661F);
			TailFin.addChild(cube_r18);
			setRotationAngle(cube_r18, 0.0F, -0.8727F, 0.0F);
			cube_r18.setTextureOffset(60, 122).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r18.setTextureOffset(144, 15).addBox(8.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r19 = new ModelRenderer(this);
			cube_r19.setRotationPoint(8.9906F, -4.85F, -7.9943F);
			TailFin.addChild(cube_r19);
			setRotationAngle(cube_r19, 0.0F, -1.0472F, 0.0F);
			cube_r19.setTextureOffset(120, 32).addBox(8.1F, 4.4F, 7.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r19.setTextureOffset(117, 124).addBox(8.1F, 3.6F, 7.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r20 = new ModelRenderer(this);
			cube_r20.setRotationPoint(6.5414F, -4.85F, -8.8786F);
			TailFin.addChild(cube_r20);
			setRotationAngle(cube_r20, 0.0F, -0.8727F, 0.0F);
			cube_r20.setTextureOffset(125, 0).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r20.setTextureOffset(145, 105).addBox(8.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r21 = new ModelRenderer(this);
			cube_r21.setRotationPoint(3.9758F, -4.85F, -9.3242F);
			TailFin.addChild(cube_r21);
			setRotationAngle(cube_r21, 0.0F, -0.6981F, 0.0F);
			cube_r21.setTextureOffset(125, 4).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r21.setTextureOffset(142, 146).addBox(8.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r22 = new ModelRenderer(this);
			cube_r22.setRotationPoint(-1.1915F, -4.85F, -8.8587F);
			TailFin.addChild(cube_r22);
			setRotationAngle(cube_r22, 0.0F, -0.3491F, 0.0F);
			cube_r22.setTextureOffset(129, 114).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r22.setTextureOffset(132, 151).addBox(8.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r23 = new ModelRenderer(this);
			cube_r23.setRotationPoint(1.3718F, -4.85F, -9.3175F);
			TailFin.addChild(cube_r23);
			setRotationAngle(cube_r23, 0.0F, -0.5236F, 0.0F);
			cube_r23.setTextureOffset(128, 10).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r23.setTextureOffset(146, 28).addBox(8.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r24 = new ModelRenderer(this);
			cube_r24.setRotationPoint(-2.9107F, -4.85F, -8.6141F);
			TailFin.addChild(cube_r24);
			setRotationAngle(cube_r24, 0.0F, -0.2618F, 0.0F);
			cube_r24.setTextureOffset(130, 100).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r24.setTextureOffset(10, 152).addBox(8.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r25 = new ModelRenderer(this);
			cube_r25.setRotationPoint(-6.2527F, -4.85F, -7.6812F);
			TailFin.addChild(cube_r25);
			setRotationAngle(cube_r25, 0.0F, -0.0873F, 0.0F);
			cube_r25.setTextureOffset(134, 135).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r25.setTextureOffset(76, 152).addBox(8.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r26 = new ModelRenderer(this);
			cube_r26.setRotationPoint(-4.602F, -4.85F, -8.2206F);
			TailFin.addChild(cube_r26);
			setRotationAngle(cube_r26, 0.0F, -0.1745F, 0.0F);
			cube_r26.setTextureOffset(132, 26).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r26.setTextureOffset(33, 152).addBox(8.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r27 = new ModelRenderer(this);
			cube_r27.setRotationPoint(-11.8652F, -4.85F, -6.0912F);
			TailFin.addChild(cube_r27);
			setRotationAngle(cube_r27, 0.0F, 0.0873F, 0.0F);
			cube_r27.setTextureOffset(124, 158).addBox(9.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r27.setTextureOffset(165, 99).addBox(9.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r28 = new ModelRenderer(this);
			cube_r28.setRotationPoint(-13.3951F, -4.85F, -5.0451F);
			TailFin.addChild(cube_r28);
			setRotationAngle(cube_r28, 0.0F, 0.1745F, 0.0F);
			cube_r28.setTextureOffset(0, 158).addBox(9.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r28.setTextureOffset(72, 164).addBox(9.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r29 = new ModelRenderer(this);
			cube_r29.setRotationPoint(-14.828F, -4.85F, -3.8696F);
			TailFin.addChild(cube_r29);
			setRotationAngle(cube_r29, 0.0F, 0.2618F, 0.0F);
			cube_r29.setTextureOffset(157, 78).addBox(9.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r29.setTextureOffset(40, 161).addBox(9.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r30 = new ModelRenderer(this);
			cube_r30.setRotationPoint(-16.4203F, -4.85F, -1.5092F);
			TailFin.addChild(cube_r30);
			setRotationAngle(cube_r30, 0.0F, 0.4363F, 0.0F);
			cube_r30.setTextureOffset(157, 6).addBox(9.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r30.setTextureOffset(112, 160).addBox(9.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r31 = new ModelRenderer(this);
			cube_r31.setRotationPoint(-17.4483F, -4.85F, 3.2807F);
			TailFin.addChild(cube_r31);
			setRotationAngle(cube_r31, 0.0F, 0.7854F, 0.0F);
			cube_r31.setTextureOffset(155, 99).addBox(9.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r31.setTextureOffset(106, 160).addBox(9.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r32 = new ModelRenderer(this);
			cube_r32.setRotationPoint(-17.6471F, -4.85F, 6.121F);
			TailFin.addChild(cube_r32);
			setRotationAngle(cube_r32, 0.0F, 0.9599F, 0.0F);
			cube_r32.setTextureOffset(10, 129).addBox(8.1F, 4.4F, 7.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r32.setTextureOffset(129, 79).addBox(8.1F, 3.6F, 7.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r33 = new ModelRenderer(this);
			cube_r33.setRotationPoint(-19.0419F, -4.85F, 6.6346F);
			TailFin.addChild(cube_r33);
			setRotationAngle(cube_r33, 0.0F, 0.8727F, 0.0F);
			cube_r33.setTextureOffset(93, 154).addBox(9.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r33.setTextureOffset(73, 160).addBox(9.1F, 3.6F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r34 = new ModelRenderer(this);
			cube_r34.setRotationPoint(-17.9906F, -5.25F, 5.7673F);
			TailFin.addChild(cube_r34);
			setRotationAngle(cube_r34, 0.0F, 1.0472F, 0.0F);
			cube_r34.setTextureOffset(128, 22).addBox(8.1F, 4.4F, 6.5F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r35 = new ModelRenderer(this);
			cube_r35.setRotationPoint(-17.6471F, -5.05F, 5.121F);
			TailFin.addChild(cube_r35);
			setRotationAngle(cube_r35, 0.0F, 0.9599F, 0.0F);
			cube_r35.setTextureOffset(143, 132).addBox(8.1F, 4.4F, 7.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r35.setTextureOffset(92, 147).addBox(8.1F, 4.0F, 7.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r36 = new ModelRenderer(this);
			cube_r36.setRotationPoint(-19.0419F, -5.05F, 5.6346F);
			TailFin.addChild(cube_r36);
			setRotationAngle(cube_r36, 0.0F, 0.8727F, 0.0F);
			cube_r36.setTextureOffset(167, 54).addBox(9.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r36.setTextureOffset(167, 62).addBox(9.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r37 = new ModelRenderer(this);
			cube_r37.setRotationPoint(-17.4483F, -5.05F, 2.2807F);
			TailFin.addChild(cube_r37);
			setRotationAngle(cube_r37, 0.0F, 0.7854F, 0.0F);
			cube_r37.setTextureOffset(167, 78).addBox(9.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r37.setTextureOffset(167, 84).addBox(9.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r38 = new ModelRenderer(this);
			cube_r38.setRotationPoint(-16.4203F, -5.05F, -2.5092F);
			TailFin.addChild(cube_r38);
			setRotationAngle(cube_r38, 0.0F, 0.4363F, 0.0F);
			cube_r38.setTextureOffset(167, 110).addBox(9.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r38.setTextureOffset(167, 89).addBox(9.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r39 = new ModelRenderer(this);
			cube_r39.setRotationPoint(-14.828F, -5.05F, -4.8696F);
			TailFin.addChild(cube_r39);
			setRotationAngle(cube_r39, 0.0F, 0.2618F, 0.0F);
			cube_r39.setTextureOffset(44, 168).addBox(9.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r39.setTextureOffset(167, 128).addBox(9.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r40 = new ModelRenderer(this);
			cube_r40.setRotationPoint(-13.3951F, -5.05F, -6.0451F);
			TailFin.addChild(cube_r40);
			setRotationAngle(cube_r40, 0.0F, 0.1745F, 0.0F);
			cube_r40.setTextureOffset(48, 168).addBox(9.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r40.setTextureOffset(79, 168).addBox(9.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r41 = new ModelRenderer(this);
			cube_r41.setRotationPoint(-11.8652F, -5.05F, -7.0912F);
			TailFin.addChild(cube_r41);
			setRotationAngle(cube_r41, 0.0F, 0.0873F, 0.0F);
			cube_r41.setTextureOffset(101, 168).addBox(9.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r41.setTextureOffset(97, 168).addBox(9.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r42 = new ModelRenderer(this);
			cube_r42.setRotationPoint(-6.2527F, -5.05F, -8.6812F);
			TailFin.addChild(cube_r42);
			setRotationAngle(cube_r42, 0.0F, -0.0873F, 0.0F);
			cube_r42.setTextureOffset(166, 149).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r42.setTextureOffset(167, 27).addBox(8.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r43 = new ModelRenderer(this);
			cube_r43.setRotationPoint(-4.602F, -5.05F, -9.2206F);
			TailFin.addChild(cube_r43);
			setRotationAngle(cube_r43, 0.0F, -0.1745F, 0.0F);
			cube_r43.setTextureOffset(166, 146).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r43.setTextureOffset(167, 0).addBox(8.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r44 = new ModelRenderer(this);
			cube_r44.setRotationPoint(-2.9107F, -5.05F, -9.6141F);
			TailFin.addChild(cube_r44);
			setRotationAngle(cube_r44, 0.0F, -0.2618F, 0.0F);
			cube_r44.setTextureOffset(75, 167).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r44.setTextureOffset(40, 225).addBox(8.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r45 = new ModelRenderer(this);
			cube_r45.setRotationPoint(-1.1915F, -5.05F, -9.8587F);
			TailFin.addChild(cube_r45);
			setRotationAngle(cube_r45, 0.0F, -0.3491F, 0.0F);
			cube_r45.setTextureOffset(166, 135).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r45.setTextureOffset(155, 167).addBox(8.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r46 = new ModelRenderer(this);
			cube_r46.setRotationPoint(1.3718F, -5.05F, -10.3175F);
			TailFin.addChild(cube_r46);
			setRotationAngle(cube_r46, 0.0F, -0.5236F, 0.0F);
			cube_r46.setTextureOffset(166, 71).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r46.setTextureOffset(151, 167).addBox(8.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r47 = new ModelRenderer(this);
			cube_r47.setRotationPoint(3.9758F, -5.05F, -10.3242F);
			TailFin.addChild(cube_r47);
			setRotationAngle(cube_r47, 0.0F, -0.6981F, 0.0F);
			cube_r47.setTextureOffset(159, 166).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r47.setTextureOffset(141, 167).addBox(8.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r48 = new ModelRenderer(this);
			cube_r48.setRotationPoint(6.5414F, -5.05F, -9.8786F);
			TailFin.addChild(cube_r48);
			setRotationAngle(cube_r48, 0.0F, -0.8727F, 0.0F);
			cube_r48.setTextureOffset(137, 166).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r48.setTextureOffset(120, 167).addBox(8.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r49 = new ModelRenderer(this);
			cube_r49.setRotationPoint(8.9906F, -5.05F, -8.9943F);
			TailFin.addChild(cube_r49);
			setRotationAngle(cube_r49, 0.0F, -1.0472F, 0.0F);
			cube_r49.setTextureOffset(114, 132).addBox(8.1F, 4.4F, 7.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r49.setTextureOffset(132, 68).addBox(8.1F, 4.0F, 7.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r50 = new ModelRenderer(this);
			cube_r50.setRotationPoint(7.9842F, -5.05F, -7.6661F);
			TailFin.addChild(cube_r50);
			setRotationAngle(cube_r50, 0.0F, -0.8727F, 0.0F);
			cube_r50.setTextureOffset(165, 125).addBox(8.1F, 4.4F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r50.setTextureOffset(110, 167).addBox(8.1F, 4.0F, 7.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			cube_r51 = new ModelRenderer(this);
			cube_r51.setRotationPoint(7.9842F, -5.25F, -8.6661F);
			TailFin.addChild(cube_r51);
			setRotationAngle(cube_r51, 0.0F, -0.8727F, 0.0F);
			cube_r51.setTextureOffset(56, 94).addBox(8.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r52 = new ModelRenderer(this);
			cube_r52.setRotationPoint(8.9906F, -5.25F, -9.9943F);
			TailFin.addChild(cube_r52);
			setRotationAngle(cube_r52, 0.0F, -1.0472F, 0.0F);
			cube_r52.setTextureOffset(86, 128).addBox(8.1F, 4.4F, 6.5F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r53 = new ModelRenderer(this);
			cube_r53.setRotationPoint(6.5414F, -5.25F, -10.8786F);
			TailFin.addChild(cube_r53);
			setRotationAngle(cube_r53, 0.0F, -0.8727F, 0.0F);
			cube_r53.setTextureOffset(78, 120).addBox(8.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r54 = new ModelRenderer(this);
			cube_r54.setRotationPoint(3.9758F, -5.25F, -11.3242F);
			TailFin.addChild(cube_r54);
			setRotationAngle(cube_r54, 0.0F, -0.6981F, 0.0F);
			cube_r54.setTextureOffset(0, 121).addBox(8.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r55 = new ModelRenderer(this);
			cube_r55.setRotationPoint(1.3718F, -5.25F, -11.3175F);
			TailFin.addChild(cube_r55);
			setRotationAngle(cube_r55, 0.0F, -0.5236F, 0.0F);
			cube_r55.setTextureOffset(50, 122).addBox(8.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r56 = new ModelRenderer(this);
			cube_r56.setRotationPoint(-1.1915F, -5.25F, -10.8587F);
			TailFin.addChild(cube_r56);
			setRotationAngle(cube_r56, 0.0F, -0.3491F, 0.0F);
			cube_r56.setTextureOffset(98, 128).addBox(8.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r57 = new ModelRenderer(this);
			cube_r57.setRotationPoint(-2.9107F, -5.25F, -10.6141F);
			TailFin.addChild(cube_r57);
			setRotationAngle(cube_r57, 0.0F, -0.2618F, 0.0F);
			cube_r57.setTextureOffset(0, 129).addBox(8.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r58 = new ModelRenderer(this);
			cube_r58.setRotationPoint(-4.602F, -5.25F, -10.2206F);
			TailFin.addChild(cube_r58);
			setRotationAngle(cube_r58, 0.0F, -0.1745F, 0.0F);
			cube_r58.setTextureOffset(46, 130).addBox(8.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r59 = new ModelRenderer(this);
			cube_r59.setRotationPoint(-6.2527F, -5.25F, -9.6812F);
			TailFin.addChild(cube_r59);
			setRotationAngle(cube_r59, 0.0F, -0.0873F, 0.0F);
			cube_r59.setTextureOffset(131, 94).addBox(8.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r60 = new ModelRenderer(this);
			cube_r60.setRotationPoint(-11.7781F, -5.25F, -8.095F);
			TailFin.addChild(cube_r60);
			setRotationAngle(cube_r60, 0.0F, 0.0873F, 0.0F);
			cube_r60.setTextureOffset(56, 134).addBox(9.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r61 = new ModelRenderer(this);
			cube_r61.setRotationPoint(-13.2215F, -5.25F, -7.0602F);
			TailFin.addChild(cube_r61);
			setRotationAngle(cube_r61, 0.0F, 0.1745F, 0.0F);
			cube_r61.setTextureOffset(133, 0).addBox(9.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r62 = new ModelRenderer(this);
			cube_r62.setRotationPoint(-14.5692F, -5.25F, -5.9036F);
			TailFin.addChild(cube_r62);
			setRotationAngle(cube_r62, 0.0F, 0.2618F, 0.0F);
			cube_r62.setTextureOffset(132, 84).addBox(9.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r63 = new ModelRenderer(this);
			cube_r63.setRotationPoint(-17.0335F, -5.25F, -2.2175F);
			TailFin.addChild(cube_r63);
			setRotationAngle(cube_r63, 0.0F, 0.5236F, 0.0F);
			cube_r63.setTextureOffset(36, 128).addBox(9.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r64 = new ModelRenderer(this);
			cube_r64.setRotationPoint(-15.811F, -5.25F, -4.634F);
			TailFin.addChild(cube_r64);
			setRotationAngle(cube_r64, 0.0F, 0.3491F, 0.0F);
			cube_r64.setTextureOffset(132, 57).addBox(9.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r65 = new ModelRenderer(this);
			cube_r65.setRotationPoint(-17.8178F, -5.25F, 0.3745F);
			TailFin.addChild(cube_r65);
			setRotationAngle(cube_r65, 0.0F, 0.6981F, 0.0F);
			cube_r65.setTextureOffset(60, 126).addBox(9.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r66 = new ModelRenderer(this);
			cube_r66.setRotationPoint(-18.1401F, -5.25F, 3.0634F);
			TailFin.addChild(cube_r66);
			setRotationAngle(cube_r66, 0.0F, 0.8727F, 0.0F);
			cube_r66.setTextureOffset(68, 93).addBox(9.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r67 = new ModelRenderer(this);
			cube_r67.setRotationPoint(-19.583F, -5.25F, 5.2759F);
			TailFin.addChild(cube_r67);
			setRotationAngle(cube_r67, 0.0F, 0.8727F, 0.0F);
			cube_r67.setTextureOffset(101, 118).addBox(9.1F, 4.4F, 6.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Tail = new ModelRenderer(this);
			Tail.setRotationPoint(-7.6062F, -10.0689F, 28.2573F);
			Main.addChild(Tail);
			Tail.setTextureOffset(30, 224).addBox(-3.2938F, 4.6689F, -8.9573F, 5.0F, 1.0F, 5.0F, 0.0F, false);
			Tail.setTextureOffset(10, 46).addBox(-3.2938F, 1.43F, -8.0011F, 1.0F, 4.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(0, 32).addBox(-3.3938F, -4.77F, -8.0011F, 1.0F, 8.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(87, 62).addBox(-3.1938F, -3.97F, -4.0011F, 1.0F, 7.0F, 5.0F, 0.0F, false);
			Tail.setTextureOffset(10, 30).addBox(-3.0938F, -3.7311F, -0.9573F, 1.0F, 6.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(56, 75).addBox(-2.9938F, -2.9311F, 3.6427F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(112, 23).addBox(-2.2938F, -2.4311F, 4.4427F, 1.0F, 3.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(46, 87).addBox(-1.9938F, -2.4311F, 5.5427F, 1.0F, 3.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(111, 115).addBox(-2.0938F, 0.2689F, 4.4427F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(88, 94).addBox(-1.7938F, -0.7311F, 5.5427F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(53, 159).addBox(-1.5938F, 3.2689F, 7.7427F, 2.0F, 1.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(119, 90).addBox(-2.2938F, 0.4689F, 3.6427F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(20, 120).addBox(-2.4938F, 0.0689F, 2.6427F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(47, 12).addBox(-2.6938F, 0.7689F, 1.5427F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(47, 7).addBox(-2.6938F, -3.2311F, 2.6427F, 1.0F, 5.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(34, 94).addBox(-2.8938F, -3.2311F, 1.4427F, 1.0F, 5.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(108, 123).addBox(-2.8938F, 1.1689F, -0.9573F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(114, 79).addBox(-3.0938F, 4.4689F, -4.9573F, 5.0F, 1.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(20, 128).addBox(-2.9938F, 0.4689F, -4.9573F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(33, 33).addBox(2.4062F, -4.77F, -8.0011F, 1.0F, 8.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(74, 63).addBox(2.2062F, -4.07F, -4.0011F, 1.0F, 7.0F, 5.0F, 0.0F, false);
			Tail.setTextureOffset(214, 176).addBox(2.1062F, -3.7311F, -0.9573F, 1.0F, 6.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(85, 49).addBox(1.9062F, -3.2311F, 1.4427F, 1.0F, 5.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(68, 58).addBox(1.5062F, -2.9311F, 3.6427F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(70, 19).addBox(1.7062F, -3.2311F, 2.6427F, 1.0F, 5.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(208, 204).addBox(1.3062F, -2.4311F, 4.4427F, 1.0F, 3.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(68, 75).addBox(1.1062F, -2.4311F, 5.5427F, 1.0F, 3.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(37, 1).addBox(-0.3938F, -2.4811F, 5.7427F, 2.0F, 6.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(108, 68).addBox(0.9062F, -0.7311F, 5.5427F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(24, 115).addBox(1.1062F, -0.4311F, 4.4427F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(119, 57).addBox(1.3062F, -0.2311F, 3.6427F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(120, 70).addBox(1.5062F, 0.0689F, 2.6427F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(70, 122).addBox(1.7062F, 0.1689F, 1.5427F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(20, 123).addBox(1.9062F, 0.4689F, -0.9573F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(70, 125).addBox(2.0062F, 0.4689F, -4.9573F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			Tail.setTextureOffset(46, 48).addBox(2.2062F, 1.6689F, -7.9573F, 1.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(30, 224).addBox(-1.7938F, 4.6689F, -8.9573F, 5.0F, 1.0F, 5.0F, 0.0F, false);
			Tail.setTextureOffset(108, 55).addBox(-1.9938F, 4.4689F, -3.9573F, 5.0F, 1.0F, 5.0F, 0.0F, false);
			Tail.setTextureOffset(145, 78).addBox(-1.2938F, 4.2689F, 1.0427F, 4.0F, 1.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(157, 79).addBox(-0.4938F, 4.0689F, 3.5427F, 3.0F, 1.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(156, 135).addBox(-0.6938F, 3.8689F, 4.6427F, 3.0F, 1.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(155, 99).addBox(-0.8938F, 3.6689F, 5.6427F, 3.0F, 1.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(98, 159).addBox(-0.0938F, 3.4689F, 6.4427F, 2.0F, 1.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(65, 159).addBox(-0.2938F, 3.2689F, 7.7427F, 2.0F, 1.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(43, 157).addBox(-2.2938F, 3.8689F, 4.6427F, 3.0F, 1.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(155, 43).addBox(-2.0938F, 3.6689F, 5.6427F, 3.0F, 1.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(86, 159).addBox(-1.8938F, 3.4689F, 6.4427F, 2.0F, 1.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(157, 1).addBox(-2.4938F, 4.0689F, 3.5427F, 3.0F, 1.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(145, 5).addBox(-2.6938F, 4.2689F, 1.0427F, 4.0F, 1.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(74, 105).addBox(-1.0938F, -2.67F, 7.4989F, 3.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(12, 8).addBox(-1.8938F, -2.67F, 7.4989F, 3.0F, 1.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(128, 144).addBox(-2.1938F, -2.87F, 6.3989F, 3.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(144, 15).addBox(-0.7938F, -2.87F, 6.3989F, 3.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(74, 145).addBox(-0.5938F, -3.27F, 5.5989F, 3.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(60, 145).addBox(-2.3938F, -3.27F, 5.5989F, 3.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(145, 40).addBox(-0.3938F, -3.47F, 4.5989F, 3.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(98, 145).addBox(-2.5938F, -3.47F, 4.5989F, 3.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(146, 132).addBox(-0.1938F, -3.67F, 3.4989F, 3.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(142, 146).addBox(-2.7938F, -3.67F, 3.4989F, 3.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(108, 67).addBox(-2.9938F, -3.87F, 0.7989F, 6.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(97, 111).addBox(-2.9938F, -4.07F, -1.2011F, 6.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(112, 22).addBox(-2.9938F, -4.27F, -3.1011F, 6.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(77, 113).addBox(-2.9938F, -4.47F, -5.3011F, 6.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(224, 219).addBox(-2.9938F, -4.77F, -8.0011F, 6.0F, 3.0F, 4.0F, 0.0F, false);
			Tail.setTextureOffset(3, 185).addBox(-1.4938F, -2.4811F, 5.7427F, 2.0F, 6.0F, 6.0F, 0.0F, false);
			Body = new ModelRenderer(this);
			Body.setRotationPoint(-7.4219F, -10.2376F, 18.4249F);
			Main.addChild(Body);
			Body.setTextureOffset(0, 0).addBox(-4.1844F, -5.0014F, -7.4249F, 8.0F, 1.0F, 9.0F, 0.0F, false);
			Body.setTextureOffset(160, 210).addBox(-3.7781F, -4.8014F, -0.0688F, 7.0F, 1.0F, 2.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(2.6219F, -4.7014F, -1.8688F, 1.0F, 9.0F, 4.0F, 0.0F, false);
			Body.setTextureOffset(0, 147).addBox(1.9219F, -4.7014F, -1.8688F, 2.0F, 9.0F, 4.0F, 0.0F, false);
			Body.setTextureOffset(64, 117).addBox(-4.0781F, -4.7014F, -1.8688F, 4.0F, 9.0F, 4.0F, 0.0F, false);
			Body.setTextureOffset(0, 21).addBox(-3.8781F, 1.8986F, -2.5688F, 1.0F, 3.0F, 3.0F, 0.0F, false);
			Body.setTextureOffset(100, 98).addBox(-3.6781F, 2.1986F, -0.8688F, 1.0F, 3.0F, 3.0F, 0.0F, false);
			Body.setTextureOffset(24, 223).addBox(-3.6781F, 4.2126F, -7.0249F, 7.0F, 2.0F, 8.0F, 0.0F, false);
			Body.setTextureOffset(0, 8).addBox(2.7219F, 1.8986F, -2.5688F, 1.0F, 3.0F, 4.0F, 0.0F, false);
			Body.setTextureOffset(94, 72).addBox(2.3219F, 2.1986F, -0.8688F, 1.0F, 3.0F, 3.0F, 0.0F, false);
			Fins = new ModelRenderer(this);
			Fins.setRotationPoint(-7.7795F, -7.1466F, 6.9009F);
			Main.addChild(Fins);
			Fins.setTextureOffset(204, 177).addBox(-9.2183F, -1.0923F, 1.2743F, 3.0F, 3.0F, 1.0F, 0.0F, false);
			Fins.setTextureOffset(161, 6).addBox(-8.9205F, -1.0923F, 1.0552F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			Fins.setTextureOffset(66, 30).addBox(-6.3205F, -1.0923F, -3.7448F, 3.0F, 3.0F, 7.0F, 0.0F, false);
			Fins.setTextureOffset(122, 68).addBox(-6.8205F, -1.5923F, -2.7448F, 2.0F, 3.0F, 6.0F, 0.0F, false);
			Fins.setTextureOffset(213, 139).addBox(-6.1205F, -1.8923F, -2.7448F, 2.0F, 3.0F, 6.0F, 0.0F, false);
			Fins.setTextureOffset(78, 120).addBox(-6.1205F, -1.6923F, -3.1448F, 2.0F, 3.0F, 6.0F, 0.0F, false);
			Fins.setTextureOffset(108, 51).addBox(-6.2205F, 0.1466F, -3.3009F, 12.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(108, 18).addBox(-6.4205F, 0.1466F, -3.2009F, 13.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(28, 51).addBox(-6.1205F, 0.3466F, -3.1009F, 12.0F, 2.0F, 1.0F, 0.0F, false);
			Fins.setTextureOffset(66, 50).addBox(-6.6205F, 0.1466F, -3.0009F, 13.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(9, 114).addBox(-6.8205F, 0.1466F, -2.8009F, 14.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(88, 96).addBox(-7.0205F, 0.1466F, -2.6009F, 14.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(90, 12).addBox(-7.4205F, 0.1466F, -2.2009F, 15.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(90, 8).addBox(-7.6205F, 0.1466F, -2.0009F, 15.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(90, 4).addBox(-7.8205F, 0.1466F, -1.8009F, 16.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(90, 0).addBox(-8.0205F, 0.1466F, -1.6009F, 16.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(88, 88).addBox(-8.2205F, 0.1466F, -1.4009F, 17.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(33, 28).addBox(-8.4205F, 0.1466F, -0.8009F, 17.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(28, 64).addBox(-8.3205F, 0.1466F, -1.2009F, 17.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(33, 33).addBox(-8.5205F, 0.1466F, -0.2009F, 17.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(37, 0).addBox(-8.6205F, 0.1466F, 0.2991F, 17.0F, 2.0F, 1.0F, 0.0F, false);
			Fins.setTextureOffset(88, 86).addBox(-8.8205F, 0.1466F, 1.5991F, 18.0F, 2.0F, 1.0F, 0.0F, false);
			Fins.setTextureOffset(94, 39).addBox(-8.6205F, 0.3466F, 1.6991F, 17.0F, 2.0F, 1.0F, 0.0F, false);
			Fins.setTextureOffset(33, 32).addBox(-8.6205F, 0.1466F, 0.8991F, 17.0F, 2.0F, 1.0F, 0.0F, false);
			Fins.setTextureOffset(94, 37).addBox(-8.4205F, 0.3466F, 0.3991F, 17.0F, 2.0F, 1.0F, 0.0F, false);
			Fins.setTextureOffset(90, 16).addBox(-8.2205F, 0.3466F, -0.7009F, 17.0F, 2.0F, 1.0F, 0.0F, false);
			Fins.setTextureOffset(94, 41).addBox(-7.9205F, 0.3466F, -1.4009F, 16.0F, 2.0F, 1.0F, 0.0F, false);
			Fins.setTextureOffset(66, 54).addBox(-7.2205F, 0.3466F, -1.9009F, 15.0F, 2.0F, 1.0F, 0.0F, false);
			Fins.setTextureOffset(88, 92).addBox(-7.2205F, 0.1466F, -2.4009F, 15.0F, 2.0F, 3.0F, 0.0F, false);
			Fins.setTextureOffset(28, 50).addBox(-6.6205F, 0.3466F, -2.4009F, 13.0F, 2.0F, 1.0F, 0.0F, false);
			Fins.setTextureOffset(62, 1).addBox(4.3795F, -1.0923F, -3.7448F, 2.0F, 3.0F, 7.0F, 0.0F, false);
			Fins.setTextureOffset(214, 218).addBox(5.6501F, -1.0923F, 1.3054F, 4.0F, 3.0F, 2.0F, 0.0F, false);
			Fins.setTextureOffset(46, 132).addBox(3.3795F, -1.5923F, -3.3448F, 2.0F, 3.0F, 6.0F, 0.0F, false);
			Fins.setTextureOffset(129, 76).addBox(3.3795F, -1.5923F, -3.2448F, 3.0F, 3.0F, 6.0F, 0.0F, false);
			Fins.setTextureOffset(129, 100).addBox(3.3795F, -1.8923F, -2.7448F, 2.0F, 3.0F, 6.0F, 0.0F, false);
			Fins.setTextureOffset(130, 23).addBox(3.3795F, -1.6923F, -3.1448F, 2.0F, 3.0F, 6.0F, 0.0F, false);
			Fins.setTextureOffset(101, 120).addBox(-6.1205F, -1.5923F, -3.3448F, 2.0F, 2.0F, 6.0F, 0.0F, false);
			cube_r68 = new ModelRenderer(this);
			cube_r68.setRotationPoint(3.062F, -6.5923F, 9.9359F);
			Fins.addChild(cube_r68);
			setRotationAngle(cube_r68, 0.0F, 0.2182F, 0.0F);
			cube_r68.setTextureOffset(123, 10).addBox(5.3F, 5.5F, -10.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
			cube_r69 = new ModelRenderer(this);
			cube_r69.setRotationPoint(8.0816F, -6.5923F, 9.1953F);
			Fins.addChild(cube_r69);
			setRotationAngle(cube_r69, 0.0F, 0.7854F, 0.0F);
			cube_r69.setTextureOffset(16, 164).addBox(5.3F, 5.5F, -10.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
			cube_r70 = new ModelRenderer(this);
			cube_r70.setRotationPoint(-22.4425F, -6.5923F, -0.0678F);
			Fins.addChild(cube_r70);
			setRotationAngle(cube_r70, 0.0F, -0.7854F, 0.0F);
			cube_r70.setTextureOffset(51, 164).addBox(8.8F, 5.5F, -14.0F, 2.0F, 3.0F, 3.0F, 0.0F, false);
			cube_r71 = new ModelRenderer(this);
			cube_r71.setRotationPoint(-20.5654F, -6.5923F, 9.6219F);
			Fins.addChild(cube_r71);
			setRotationAngle(cube_r71, 0.0F, -0.2618F, 0.0F);
			cube_r71.setTextureOffset(162, 33).addBox(8.8F, 5.5F, -14.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
			Floatie = new ModelRenderer(this);
			Floatie.setRotationPoint(-9.8F, -16.3F, 23.4F);
			Main.addChild(Floatie);
			Floatie.setTextureOffset(231, 49).addBox(-1.2F, -0.7F, -8.0F, 7.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-1.2F, -1.1F, -8.5F, 7.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-1.2F, -1.3F, -8.9F, 7.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-1.2F, -3.0F, -9.5F, 7.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-1.2F, -3.0F, -8.5F, 7.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(77, 28).addBox(7.8F, 2.0F, -8.9F, 1.0F, 5.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 104).addBox(8.5F, 2.0F, -9.6F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(94, 24).addBox(8.5F, 2.0F, -8.3F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-5.7F, 2.0F, -8.5F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-5.7F, 2.0F, -9.4F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(7.8F, 7.0F, -8.9F, 1.0F, 4.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(8.5F, 7.0F, -9.6F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-5.7F, 7.0F, -9.4F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(8.5F, 7.0F, -8.3F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-5.7F, 7.0F, -8.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(0.0F, 12.5F, -8.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(0.0F, 13.0F, -8.5F, 4.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(0.0F, 13.2F, -8.9F, 4.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(0.0F, 13.7F, -8.5F, 4.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(0.0F, 13.7F, -9.4F, 4.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(7.4F, 11.0F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(101, 106).addBox(7.0F, 11.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(7.7F, 11.0F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(8.3F, 11.0F, -9.6F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(8.3F, 11.0F, -8.3F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-3.4F, 11.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-3.8F, 11.0F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-4.1F, 11.0F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-5.7F, 11.0F, -8.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-5.7F, 11.0F, -9.4F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(36, 165).addBox(7.0F, 2.0F, -8.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-3.4F, 2.0F, -8.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-3.7F, 2.0F, -8.5F, 1.0F, 5.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-4.1F, 2.0F, -8.9F, 1.0F, 5.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(18, 104).addBox(7.4F, 2.0F, -8.5F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-3.7F, 7.0F, -8.5F, 1.0F, 4.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(7.4F, 7.0F, -8.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(6.5F, 11.5F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-4.1F, 7.0F, -8.9F, 1.0F, 4.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(7.0F, 12.0F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(7.3F, 12.0F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(7.8F, 11.5F, -9.6F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(7.8F, 11.5F, -8.5F, 2.0F, 1.0F, 3.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(6.0F, 12.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(6.5F, 12.5F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(6.5F, 12.8F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(6.7F, 12.8F, -9.4F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(6.7F, 12.8F, -8.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(6.5F, 13.1F, -9.4F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(6.0F, 13.1F, -8.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(6.8F, 12.5F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(7.2F, 12.5F, -8.3F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(7.2F, 12.5F, -9.4F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(7.0F, 12.7F, -9.3F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(7.0F, 12.7F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(113, 113).addBox(-2.4F, 12.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-3.2F, 12.7F, -8.7F, 1.0F, 2.0F, 3.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-3.0F, 12.5F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-3.0F, 12.7F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.6F, 12.7F, -9.4F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.4F, 13.2F, -9.4F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.4F, 13.2F, -8.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-4.5F, 12.4F, -8.5F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-3.3F, 12.4F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-4.5F, 12.4F, -9.4F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-3.7F, 12.4F, -9.3F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.0F, 12.5F, -8.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.0F, 13.0F, -8.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.0F, 13.2F, -8.9F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.0F, 13.7F, -8.5F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.0F, 13.7F, -9.4F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(4.5F, 12.5F, -8.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(40, 84).addBox(4.5F, 13.0F, -8.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(4.5F, 13.7F, -8.5F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(4.5F, 13.2F, -8.9F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(4.5F, 13.7F, -9.4F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-2.9F, 11.5F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-3.4F, 12.0F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-3.6F, 12.0F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-5.1F, 11.5F, -8.7F, 2.0F, 1.0F, 3.0F, 0.0F, false);
			Floatie.setTextureOffset(231, 49).addBox(-5.1F, 11.5F, -9.4F, 2.0F, 1.0F, 3.0F, 0.0F, false);
			Floatie.setTextureOffset(128, 94).addBox(5.6F, -0.3F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(5.8F, -0.9F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 125).addBox(-2.5F, 0.1F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-3.0F, -0.5F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(5.8F, -1.1F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-3.0F, -0.8F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.5F, -2.5F, -9.4F, 1.0F, 2.0F, 3.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-2.5F, -1.2F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-2.5F, -1.2F, -9.4F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.5F, -2.5F, -8.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(111, 124).addBox(-2.9F, 0.5F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-3.5F, 0.0F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-4.8F, 0.1F, -7.7F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-3.8F, -0.4F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-4.8F, 0.1F, -9.4F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-3.7F, 0.0F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(-3.4F, -0.4F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.8F, -1.9F, -8.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.8F, -1.9F, -9.4F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(124, 94).addBox(-2.0F, -0.3F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(39, 3).addBox(-2.0F, -0.9F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(237, 76).addBox(-2.0F, -1.1F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(31, 33).addBox(-2.0F, -1.5F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(4, 6).addBox(5.6F, -1.5F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.0F, -2.9F, -8.7F, 1.0F, 2.0F, 3.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(5.6F, -2.9F, -8.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(3, 0).addBox(5.6F, -1.5F, -9.3F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(31, 28).addBox(-2.0F, -1.5F, -9.3F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-2.0F, -2.9F, -9.5F, 1.0F, 2.0F, 3.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(5.6F, -2.9F, -9.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(50, 126).addBox(6.0F, 0.1F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(6.0F, -0.5F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(6.6F, -0.7F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(6.0F, -2.5F, -8.4F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(6.6F, -1.1F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(6.0F, -2.5F, -9.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(6.3F, -2.2F, -8.4F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(237, 74).addBox(6.3F, -2.2F, -9.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(7.4F, 0.0F, -9.6F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(7.4F, 0.0F, -8.3F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(6.4F, -1.0F, -9.6F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(6.4F, -1.0F, -8.3F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-3.8F, -1.0F, -9.4F, 1.0F, 2.0F, 3.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-3.8F, -1.0F, -8.7F, 1.0F, 2.0F, 3.0F, 0.0F, false);
			Floatie.setTextureOffset(40, 126).addBox(6.5F, 0.5F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(6.6F, -1.1F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 0).addBox(7.0F, 0.0F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(0, 27).addBox(7.0F, 0.5F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(5.5F, 0.2F, -8.9F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(110, 82).addBox(6.5F, -1.8F, -9.6F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(6.5F, -1.8F, -8.3F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(7.5F, 0.2F, -9.6F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(7.5F, 0.2F, -8.3F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(238, 75).addBox(7.5F, 0.7F, -8.9F, 0.0F, 0.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(36, 126).addBox(7.0F, 1.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(238, 75).addBox(7.8F, 1.0F, -8.9F, 0.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(7.8F, 0.7F, -9.6F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(7.8F, 0.7F, -8.3F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(8.3F, 1.0F, -8.5F, 2.0F, 1.0F, 3.0F, 0.0F, false);
			Floatie.setTextureOffset(15, 44).addBox(-3.9F, 0.9F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 76).addBox(8.3F, 1.0F, -9.6F, 2.0F, 1.0F, 3.0F, 0.0F, false);
			Floatie.setTextureOffset(239, 74).addBox(-3.4F, 1.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(14, 53).addBox(-3.8F, 1.0F, -8.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-4.9F, 0.5F, -8.7F, 2.0F, 1.0F, 3.0F, 0.0F, false);
			Floatie.setTextureOffset(33, 22).addBox(-3.9F, 0.5F, -8.9F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-4.9F, 0.5F, -9.4F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-5.5F, 0.9F, -8.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-5.5F, 0.9F, -9.4F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(-3.4F, 7.0F, -8.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
			Floatie.setTextureOffset(236, 73).addBox(7.0F, 7.0F, -8.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			Main.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.TailFin.rotateAngleX = f3 / (180F / (float) Math.PI);
		}
	}
}
