package TheKingOfNerds.nerds_better_deserts;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class SandstoneBricksBlock extends Block {
    public static final IntegerProperty WATER =  IntegerProperty.create("water", 0, 3);

    public SandstoneBricksBlock(BlockBehaviour.Properties p_53247_) {
        super(p_53247_);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(WATER, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WATER});
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    protected void randomTick(BlockState p_221139_, ServerLevel p_221140_, BlockPos p_221141_, RandomSource p_221142_) {
        int i = (Integer)p_221139_.getValue(WATER);
        if (!p_221140_.getBlockState(p_221141_.east()).is(Blocks.WATER) && !p_221140_.getBlockState(p_221141_.west()).is(Blocks.WATER) && !p_221140_.getBlockState(p_221141_.north()).is(Blocks.WATER) && !p_221140_.getBlockState(p_221141_.south()).is(Blocks.WATER)) {
            if (i > 0) {
                p_221140_.setBlock(p_221141_, (BlockState)p_221139_.setValue(WATER, i - 1), 2);
            }
        } else if (i < 3) {
            p_221140_.setBlock(p_221141_, (BlockState)p_221139_.setValue(WATER, 3), 2);
        }

    }
}
