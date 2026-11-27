package TheKingOfNerds.nerds_better_deserts;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

public class SandstoneDungeonChestBlock extends Block implements EntityBlock {
    public static final IntegerProperty LEVEL =  IntegerProperty.create("level", 0, 6);

    public SandstoneDungeonChestBlock(BlockBehaviour.Properties p_53247_) {
        super(p_53247_);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(LEVEL, 0));
    }
    protected InteractionResult useWithoutItem(BlockState p_49069_, Level p_49070_, BlockPos p_49071_, Player p_49072_, BlockHitResult p_49074_) {
        if (p_49070_.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = p_49070_.getBlockEntity(p_49071_);
            if (blockentity instanceof SandstoneDungeonChestBlockEntity) {
                p_49072_.openMenu((SandstoneDungeonChestBlockEntity)blockentity);
            }

            return InteractionResult.CONSUME;
        }
    }
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SandstoneDungeonChestBlockEntity(pos, state);
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LEVEL});
    }
}
