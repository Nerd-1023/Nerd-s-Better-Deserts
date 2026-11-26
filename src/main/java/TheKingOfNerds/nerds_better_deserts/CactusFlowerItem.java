package TheKingOfNerds.nerds_better_deserts;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import static TheKingOfNerds.nerds_better_deserts.Nerds_better_deserts.CACTUS_SMALL;
import static TheKingOfNerds.nerds_better_deserts.Nerds_better_deserts.CACTUS_WITH_FLOWER;
import static net.minecraft.world.level.block.Blocks.CACTUS;

public class CactusFlowerItem extends Item{
    public CactusFlowerItem(Item.Properties p_40626_) {
        super(p_40626_);
    }
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        if(level.getBlockState(blockpos).is(CACTUS_SMALL)){
            if (level.setBlock(blockpos,CACTUS_SMALL.get().defaultBlockState().setValue(SmallCactusBlock.FLOWER,true), 3)) {
                if (!level.isClientSide) {
                    context.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                    context.getItemInHand().consume(1, context.getPlayer());
                    level.levelEvent(1505, blockpos, 15);
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        } else if(level.getBlockState(blockpos).is(CACTUS)){
            if (!level.getBlockState(blockpos.above()).is(CACTUS) && !level.getBlockState(blockpos.above()).is(CACTUS_WITH_FLOWER)){
                if (level.setBlock(blockpos,CACTUS_WITH_FLOWER.get().defaultBlockState(), 3)) {
                    if (!level.isClientSide) {
                        context.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                        level.levelEvent(1505, blockpos, 15);
                    }

                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
        return InteractionResult.FAIL;
    }
}
