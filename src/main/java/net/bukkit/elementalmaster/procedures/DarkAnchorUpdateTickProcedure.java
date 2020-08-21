package net.bukkit.elementalmaster.procedures;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;

import net.bukkit.elementalmaster.block.DarkStoneBrickBlock;
import net.bukkit.elementalmaster.block.DarkPortalBlockBlock;
import net.bukkit.elementalmaster.ElementalmasterModElements;

import java.util.Map;

@ElementalmasterModElements.ModElement.Tag
public class DarkAnchorUpdateTickProcedure extends ElementalmasterModElements.ModElement {
	public DarkAnchorUpdateTickProcedure(ElementalmasterModElements instance) {
		super(instance, 716);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure DarkAnchorUpdateTick!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure DarkAnchorUpdateTick!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure DarkAnchorUpdateTick!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure DarkAnchorUpdateTick!");
			return;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if (((((((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z + 1)))).getBlock() == DarkStoneBrickBlock.block.getDefaultState()
				.getBlock())
				&& ((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z - 1)))).getBlock() == DarkStoneBrickBlock.block.getDefaultState()
						.getBlock()))
				&& (((world.getBlockState(new BlockPos((int) (x + 1), (int) y, (int) z))).getBlock() == DarkStoneBrickBlock.block.getDefaultState()
						.getBlock())
						&& ((world.getBlockState(new BlockPos((int) (x - 1), (int) y, (int) z))).getBlock() == DarkStoneBrickBlock.block
								.getDefaultState().getBlock())))
				&& ((((world.getBlockState(new BlockPos((int) (x + 1), (int) y, (int) (z + 1)))).getBlock() == DarkStoneBrickBlock.block
						.getDefaultState().getBlock())
						&& ((world.getBlockState(new BlockPos((int) (x - 1), (int) y, (int) (z - 1)))).getBlock() == DarkStoneBrickBlock.block
								.getDefaultState().getBlock()))
						&& (((world.getBlockState(new BlockPos((int) (x + 1), (int) y, (int) (z - 1)))).getBlock() == DarkStoneBrickBlock.block
								.getDefaultState().getBlock())
								&& ((world.getBlockState(new BlockPos((int) (x - 1), (int) y, (int) (z + 1)))).getBlock() == DarkStoneBrickBlock.block
										.getDefaultState().getBlock()))))
				&& (((((world.getBlockState(new BlockPos((int) x, (int) (y + 1), (int) (z + 2)))).getBlock() == DarkStoneBrickBlock.block
						.getDefaultState().getBlock())
						&& ((world.getBlockState(new BlockPos((int) x, (int) (y + 1), (int) (z - 2)))).getBlock() == DarkStoneBrickBlock.block
								.getDefaultState().getBlock()))
						&& (((world.getBlockState(new BlockPos((int) (x + 2), (int) (y + 1), (int) z))).getBlock() == DarkStoneBrickBlock.block
								.getDefaultState().getBlock())
								&& ((world.getBlockState(new BlockPos((int) (x - 2), (int) (y + 1), (int) z))).getBlock() == DarkStoneBrickBlock.block
										.getDefaultState().getBlock())))
						&& (((((world.getBlockState(new BlockPos((int) (x + 2), (int) (y + 1), (int) (z + 1))))
								.getBlock() == DarkStoneBrickBlock.block.getDefaultState().getBlock())
								&& ((world.getBlockState(new BlockPos((int) (x - 2), (int) (y + 1), (int) (z - 1))))
										.getBlock() == DarkStoneBrickBlock.block.getDefaultState().getBlock()))
								&& (((world.getBlockState(new BlockPos((int) (x + 2), (int) (y + 1), (int) (z - 1))))
										.getBlock() == DarkStoneBrickBlock.block.getDefaultState().getBlock())
										&& ((world.getBlockState(new BlockPos((int) (x - 2), (int) (y + 1), (int) (z + 1))))
												.getBlock() == DarkStoneBrickBlock.block.getDefaultState().getBlock())))
								&& ((((world.getBlockState(new BlockPos((int) (x + 1), (int) (y + 1), (int) (z + 2))))
										.getBlock() == DarkStoneBrickBlock.block.getDefaultState().getBlock())
										&& ((world.getBlockState(new BlockPos((int) (x - 1), (int) (y + 1), (int) (z - 2))))
												.getBlock() == DarkStoneBrickBlock.block.getDefaultState().getBlock()))
										&& (((world.getBlockState(new BlockPos((int) (x + 1), (int) (y + 1), (int) (z - 2))))
												.getBlock() == DarkStoneBrickBlock.block.getDefaultState().getBlock())
												&& ((world.getBlockState(new BlockPos((int) (x - 1), (int) (y + 1), (int) (z + 2))))
														.getBlock() == DarkStoneBrickBlock.block.getDefaultState().getBlock()))))))) {
			world.setBlockState(new BlockPos((int) x, (int) (y + 1), (int) z), DarkPortalBlockBlock.block.getDefaultState(), 3);
			world.setBlockState(new BlockPos((int) (x + 1), (int) (y + 1), (int) z), DarkPortalBlockBlock.block.getDefaultState(), 3);
			world.setBlockState(new BlockPos((int) (x - 1), (int) (y + 1), (int) z), DarkPortalBlockBlock.block.getDefaultState(), 3);
			world.setBlockState(new BlockPos((int) x, (int) (y + 1), (int) (z - 1)), DarkPortalBlockBlock.block.getDefaultState(), 3);
			world.setBlockState(new BlockPos((int) x, (int) (y + 1), (int) (z + 1)), DarkPortalBlockBlock.block.getDefaultState(), 3);
			world.setBlockState(new BlockPos((int) (x + 1), (int) (y + 1), (int) (z + 1)), DarkPortalBlockBlock.block.getDefaultState(), 3);
			world.setBlockState(new BlockPos((int) (x - 1), (int) (y + 1), (int) (z - 1)), DarkPortalBlockBlock.block.getDefaultState(), 3);
			world.setBlockState(new BlockPos((int) (x + 1), (int) (y + 1), (int) (z - 1)), DarkPortalBlockBlock.block.getDefaultState(), 3);
			world.setBlockState(new BlockPos((int) (x - 1), (int) (y + 1), (int) (z + 1)), DarkPortalBlockBlock.block.getDefaultState(), 3);
		}
	}
}
