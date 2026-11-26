package TheKingOfNerds.nerds_better_deserts;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Nerds_better_deserts.MODID)
public class Nerds_better_deserts
{
    public static final String MODID = "nerds_better_deserts";
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredBlock<Block> CACTUS_SMALL = BLOCKS.registerBlock("cactus_small", SmallCactusBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CACTUS));
    public static final DeferredBlock<Block> CACTUS_WITH_FLOWER = BLOCKS.registerBlock("cactus_with_flower", CactusWithFlowerBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CACTUS));
    public static final DeferredItem<BlockItem> CACTUS_SMALL_ITEM = ITEMS.registerSimpleBlockItem("cactus_small", CACTUS_SMALL);
    public static final DeferredItem<Item> CACTUS_FLOWER_ITEM = ITEMS.registerItem("cactus_flower", CactusFlowerItem::new);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DesertTab = CREATIVE_MODE_TABS.register("desert_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.nerds_better_deserts"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CACTUS_SMALL_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(CACTUS_SMALL_ITEM.get());
                output.accept(CACTUS_FLOWER_ITEM.get());
            }).build());

    public Nerds_better_deserts(IEventBus modEventBus, ModContainer modContainer)
    {

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS)
            event.accept(CACTUS_SMALL_ITEM);
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS)
            event.accept(CACTUS_FLOWER_ITEM);
    }
}
