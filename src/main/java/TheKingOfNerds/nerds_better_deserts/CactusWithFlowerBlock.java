package TheKingOfNerds.nerds_better_deserts;

import com.mojang.serialization.MapCodec;
import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.TriState;

public class CactusWithFlowerBlock extends Block {
    public static final MapCodec<net.minecraft.world.level.block.CactusBlock> CODEC = simpleCodec(net.minecraft.world.level.block.CactusBlock::new);
    protected static final VoxelShape COLLISION_SHAPE;
    protected static final VoxelShape OUTLINE_SHAPE;

    public MapCodec<net.minecraft.world.level.block.CactusBlock> codec() {
        return CODEC;
    }

    public CactusWithFlowerBlock(BlockBehaviour.Properties p_51136_) {
        super(p_51136_);
    }

    protected void tick(BlockState p_220908_, ServerLevel p_220909_, BlockPos p_220910_, RandomSource p_220911_) {
        if (p_220909_.isAreaLoaded(p_220910_, 1)) {
            if (!p_220908_.canSurvive(p_220909_, p_220910_)) {
                p_220909_.destroyBlock(p_220910_, true);
            }

        }
    }

    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return OUTLINE_SHAPE;
    }

    protected BlockState updateShape(BlockState p_51157_, Direction p_51158_, BlockState p_51159_, LevelAccessor p_51160_, BlockPos p_51161_, BlockPos p_51162_) {
        if (!p_51157_.canSurvive(p_51160_, p_51161_)) {
            p_51160_.scheduleTick(p_51161_, this, 1);
        }

        return super.updateShape(p_51157_, p_51158_, p_51159_, p_51160_, p_51161_, p_51162_);
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Iterator var4 = Plane.HORIZONTAL.iterator();

        Direction direction;
        BlockState blockstate;
        do {
            if (!var4.hasNext()) {
                BlockState blockstate1 = level.getBlockState(pos.below());
                TriState soilDecision = blockstate1.canSustainPlant(level, pos.below(), Direction.UP, state);
                if (!soilDecision.isDefault()) {
                    return soilDecision.isTrue();
                }

                return (blockstate1.is(Blocks.CACTUS) || blockstate1.is(BlockTags.SAND)) && !level.getBlockState(pos.above()).liquid();
            }

            direction = (Direction)var4.next();
            blockstate = level.getBlockState(pos.relative(direction));
        } while(!blockstate.isSolid() && !level.getFluidState(pos.relative(direction)).is(FluidTags.LAVA));

        return false;
    }

    protected void entityInside(BlockState p_51148_, Level p_51149_, BlockPos p_51150_, Entity p_51151_) {
        p_51151_.hurt(p_51149_.damageSources().cactus(), 1.0F);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    }

    protected boolean isPathfindable(BlockState p_51143_, PathComputationType p_51146_) {
        return false;
    }

    static {
        COLLISION_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
        OUTLINE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    }
}
