package com.jemmerl.rekindleprimitive.block.uniqueblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class DryBrickMold extends Block {
    public DryBrickMold(Properties builder) {
        super(builder);
    }

    private static final VoxelShape SHAPE = Block.makeCuboidShape(3, 0, 1, 13, 3, 15);

    // Properties
    @Override
    public VoxelShape getShape(BlockState stateIn, IBlockReader block, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isTransparent(BlockState stateIn) {
        return true;
    }

    // Behavior
    @Override
    public boolean isValidPosition(BlockState stateIn, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        return hasSolidSideOnTop((IBlockReader)worldIn, blockpos);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld InWorld, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.DOWN && !isValidPosition(stateIn, (IWorldReader)InWorld, currentPos)) {
            return Blocks.AIR.getDefaultState();
        }
        InWorld.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
        return super.updatePostPlacement(stateIn, facing, facingState, InWorld, currentPos, facingPos);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!worldIn.isAreaLoaded(pos, 1)) { return; }
        if (!state.isValidPosition((IWorldReader)worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }
}
