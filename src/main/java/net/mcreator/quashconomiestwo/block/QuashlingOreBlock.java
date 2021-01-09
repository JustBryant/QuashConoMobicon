
package net.mcreator.quashconomiestwo.block;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.World;
import net.minecraft.world.ISeedReader;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.RegistryKey;
import net.minecraft.loot.LootContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import net.mcreator.quashconomiestwo.itemgroup.QuashconomiesItemGroup;
import net.mcreator.quashconomiestwo.QuashconomiestwoModElements;

import java.util.Random;
import java.util.List;
import java.util.Collections;

@QuashconomiestwoModElements.ModElement.Tag
public class QuashlingOreBlock extends QuashconomiestwoModElements.ModElement {
	@ObjectHolder("quashconomiestwo:quashling_ore")
	public static final Block block = null;
	public QuashlingOreBlock(QuashconomiestwoModElements instance) {
		super(instance, 8);
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
			super(Block.Properties.create(Material.ROCK).sound(SoundType.METAL).hardnessAndResistance(12f, 15f).setLightLevel(s -> 0).harvestLevel(2)
					.harvestTool(ToolType.PICKAXE));
			setRegistryName("quashling_ore");
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
		}.withConfiguration(new OreFeatureConfig(new BlockMatchRuleTest(Blocks.STONE.getDefaultState().getBlock()) {
			public boolean test(BlockState blockAt, Random random) {
				boolean blockCriteria = false;
				if (blockAt.getBlock() == Blocks.STONE.getDefaultState().getBlock())
					blockCriteria = true;
				if (blockAt.getBlock() == Blocks.DIRT.getDefaultState().getBlock())
					blockCriteria = true;
				if (blockAt.getBlock() == Blocks.GRANITE.getDefaultState().getBlock())
					blockCriteria = true;
				if (blockAt.getBlock() == Blocks.ANDESITE.getDefaultState().getBlock())
					blockCriteria = true;
				return blockCriteria;
			}

			protected IRuleTestType<?> getType() {
				return IRuleTestType.BLOCK_MATCH;
			}
		}, block.getDefaultState(), 8)).range(32).square().func_242731_b(10));
	}
}
