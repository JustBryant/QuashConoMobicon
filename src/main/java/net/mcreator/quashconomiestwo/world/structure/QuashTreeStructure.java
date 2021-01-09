
package net.mcreator.quashconomiestwo.world.structure;

@QuashconomiestwoModElements.ModElement.Tag
public class QuashTreeStructure extends QuashconomiestwoModElements.ModElement {

	public QuashTreeStructure(QuashconomiestwoModElements instance) {
		super(instance, 84);

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
		boolean biomeCriteria = false;
		if (new ResourceLocation("quashconomiestwo:quashiome").equals(event.getName()))
			biomeCriteria = true;
		if (!biomeCriteria)
			return;

		Feature<NoFeatureConfig> feature = new Feature<NoFeatureConfig>(NoFeatureConfig.field_236558_a_) {
			@Override
			public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
				int ci = (pos.getX() >> 4) << 4;
				int ck = (pos.getZ() >> 4) << 4;

				RegistryKey<World> dimensionType = world.getWorld().getDimensionKey();
				boolean dimensionCriteria = false;

				if (dimensionType == World.OVERWORLD)
					dimensionCriteria = true;

				if (!dimensionCriteria)
					return false;

				if ((random.nextInt(1000000) + 1) <= 100000) {
					int count = random.nextInt(16) + 1;
					for (int a = 0; a < count; a++) {
						int i = ci + random.nextInt(16);
						int k = ck + random.nextInt(16);
						int j = world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, i, k);

						j -= 1;

						Rotation rotation = Rotation.values()[random.nextInt(3)];
						Mirror mirror = Mirror.values()[random.nextInt(2)];

						BlockPos spawnTo = new BlockPos(i + 0, j + 0, k + 0);

						int x = spawnTo.getX();
						int y = spawnTo.getY();
						int z = spawnTo.getZ();

						Template template = world.getWorld().getStructureTemplateManager()
								.getTemplateDefaulted(new ResourceLocation("quashconomiestwo", "tree"));

						if (template == null)
							return false;

						template.func_237144_a_(world, spawnTo,
								new PlacementSettings().setRotation(rotation).setRandom(random).setMirror(mirror)
										.addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK).setChunk(null).setIgnoreEntities(false),
								random);

					}
				}

				return true;
			}
		};

		event.getGeneration().getFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES).add(() -> feature
				.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
	}

}
