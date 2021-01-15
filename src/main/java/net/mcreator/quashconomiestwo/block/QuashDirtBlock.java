
package net.mcreator.quashconomiestwo.block;

import net.minecraft.block.material.Material;

@QuashconomiestwoModElements.ModElement.Tag
public class QuashDirtBlock extends QuashconomiestwoModElements.ModElement {

	@ObjectHolder("quashconomiestwo:quash_dirt")
	public static final Block block = null;

	public QuashDirtBlock(QuashconomiestwoModElements instance) {
		super(instance, 110);

		MinecraftForge.EVENT_BUS.register(this);

	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items
				.add(() -> new BlockItem(block, new Item.Properties().group(QuashconomiesItemGroup.tab)).setRegistryName(block.getRegistryName()));
	}

	public static class CustomBlock extends Block {

		public CustomBlock() {
			super(

					Block.Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(1f, 10f).setLightLevel(s -> 0)
							.harvestLevel(0).harvestTool(ToolType.SHOVEL));

			setRegistryName("quash_dirt");
		}

		@Override
		public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction direction, IPlantable plantable) {
			return true;
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(this, 1));
		}

	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
		event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> new OreFeature(OreFeatureConfig.CODEC) {
			@Override
			public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, OreFeatureConfig config) {
				RegistryKey<World> dimensionType = world.getWorld().getDimensionKey();
				boolean dimensionCriteria = false;

				if (dimensionType == World.OVERWORLD)
					dimensionCriteria = true;

				if (!dimensionCriteria)
					return false;

				return super.generate(world, generator, rand, pos, config);
			}
		}.withConfiguration(new OreFeatureConfig(new BlockMatchRuleTest(Blocks.BARRIER) {
			public boolean test(BlockState blockAt, Random random) {
				boolean blockCriteria = false;
				return blockCriteria;
			}

			protected IRuleTestType<?> getType() {
				return IRuleTestType.BLOCK_MATCH;
			}
		}, block.getDefaultState(), 16)).range(64).square().func_242731_b(10));
	}

}
