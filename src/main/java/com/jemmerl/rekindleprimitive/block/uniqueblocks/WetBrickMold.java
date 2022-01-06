package com.jemmerl.rekindleprimitive.block.uniqueblocks;

import com.jemmerl.rekindleprimitive.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.LightType;

import java.util.Random;

public class WetBrickMold extends Block {

    // Properties
    public static final IntegerProperty AGE_0_19 = IntegerProperty.create("dry_age", 0, 19); // Needs 20 random ticks to dry, only updates during day+skylight exposure, resets from rain

    private static final VoxelShape SHAPE = Block.makeCuboidShape(3, 0, 1, 13, 3, 15);

    public WetBrickMold(Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with((Property)AGE_0_19, Integer.valueOf(0)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(AGE_0_19);
    }

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
    public void randomTick(BlockState selfState, ServerWorld worldIn, BlockPos posIn, Random randomIn) {
        int i = (Integer)selfState.get((Property)AGE_0_19);
        int j = canDry(selfState, worldIn.getWorld(), posIn);
        if (j == 1) {
            if (i == 19) {
                worldIn.setBlockState(posIn, ModBlocks.DRY_BRICK_MOLD.get().getDefaultState(), 2);
            } else {
                worldIn.setBlockState(posIn, (BlockState) selfState.with((Property) AGE_0_19, Integer.valueOf(i + 1)), 4);
            }
        } else if (j == 2) {
            worldIn.setBlockState(posIn, (BlockState)selfState.with((Property)AGE_0_19, (Integer)0), 4);
        } else { return; }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld InWorld, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.DOWN && !isValidPosition(stateIn, (IWorldReader) InWorld, currentPos)) {
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

    /* 0 -> Nothing done (happens when no skylight or not raining + not sky light enough),
    2 -> reset age (soaked from rain), 1 -> update drying age or convert if aged */
    private int canDry(BlockState state, World world, BlockPos posIn) {
        if (world.getDimensionType().hasSkyLight()) {
            if (world.canBlockSeeSky(posIn)) {
                if (world.isRaining()) { return 2; }
                if (Math.max(((world.getLightFor(LightType.SKY, posIn)) - (world.getSkylightSubtracted())), 0) > 8) { return 1; }
            }
        }
        return 0;
    }
}
