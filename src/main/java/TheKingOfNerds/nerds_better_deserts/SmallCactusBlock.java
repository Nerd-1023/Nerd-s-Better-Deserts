package TheKingOfNerds.nerds_better_deserts;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.TriState;

public class SmallCactusBlock extends Block {

    protected static final VoxelShape COLLISION_SHAPE;
    protected static final VoxelShape OUTLINE_SHAPE;
    public static final BooleanProperty FLOWER = BooleanProperty.create("flower");
    public SmallCactusBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any()
                .setValue(FLOWER, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FLOWER);
    }
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return OUTLINE_SHAPE;
    }

    protected void tick(BlockState p_220908_, ServerLevel p_220909_, BlockPos p_220910_, RandomSource p_220911_) {
        if (p_220909_.isAreaLoaded(p_220910_, 1)) {
            if (!p_220908_.canSurvive(p_220909_, p_220910_)) {
                p_220909_.destroyBlock(p_220910_, true);
            }

        }
    }
    protected BlockState updateShape(BlockState p_51157_, Direction p_51158_, BlockState p_51159_, LevelAccessor p_51160_, BlockPos p_51161_, BlockPos p_51162_) {
        if (!p_51157_.canSurvive(p_51160_, p_51161_)) {
            p_51160_.scheduleTick(p_51161_, this, 1);
        }

        return super.updateShape(p_51157_, p_51158_, p_51159_, p_51160_, p_51161_, p_51162_);
    }

    static {
        COLLISION_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
        OUTLINE_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
    }
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockstate1 = level.getBlockState(pos.below());
        TriState soilDecision = blockstate1.canSustainPlant(level, pos.below(), Direction.UP, state);
        if (!soilDecision.isDefault()) {
            return soilDecision.isTrue();
        }

        return (blockstate1.is(BlockTags.SAND)) && !level.getBlockState(pos.above()).liquid();
    }
    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        double reach = COLLISION_SHAPE.bounds().getXsize() / 14.0;
        AABB hurtBox = COLLISION_SHAPE.bounds().move(pos.getX(), pos.getY(), pos.getZ()).inflate(reach);
        if (entity.getBoundingBox().intersects(hurtBox)) {
            entity.hurt(level.damageSources().cactus(), 1.0F);
        }
    }
}