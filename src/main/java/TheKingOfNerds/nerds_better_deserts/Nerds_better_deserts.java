package TheKingOfNerds.nerds_better_deserts;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod(Nerds_better_deserts.MODID)
public class Nerds_better_deserts
{
    public static final String MODID = "nerds_better_deserts";
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final DeferredBlock<Block> SANDSTONE_BRICKS = BLOCKS.registerBlock("sandstone_bricks", SandstoneBricksBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_SANDSTONE).randomTicks());
    public static final DeferredBlock<Block> MOSSY_SANDSTONE_BRICKS = BLOCKS.registerBlock("mossy_sandstone_bricks", SandstoneBricksBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_SANDSTONE).randomTicks());
    public static final DeferredBlock<Block> SANDSTONE_DUNGEON_CHEST = BLOCKS.registerBlock("sandstone_dungeon_chest", SandstoneDungeonChestBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_SANDSTONE).strength(50.0F, 1200.0F));
    public static final DeferredBlock<Block> CACTUS_SMALL = BLOCKS.registerBlock("cactus_small", SmallCactusBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CACTUS));
    public static final DeferredBlock<Block> CACTUS_WITH_FLOWER = BLOCKS.registerBlock("cactus_with_flower", CactusWithFlowerBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CACTUS));
    public static final DeferredItem<BlockItem> CACTUS_SMALL_ITEM = ITEMS.registerSimpleBlockItem("cactus_small", CACTUS_SMALL);
    public static final DeferredItem<BlockItem> SANDSTONE_BRICKS_ITEM = ITEMS.registerSimpleBlockItem("sandstone_bricks", SANDSTONE_BRICKS);
    public static final DeferredItem<BlockItem> SANDSTONE_DUNGEON_CHEST_ITEM = ITEMS.registerSimpleBlockItem("sandstone_dungeon_chest", SANDSTONE_DUNGEON_CHEST);
    public static final DeferredItem<BlockItem> MOSSY_SANDSTONE_BRICKS_ITEM = ITEMS.registerSimpleBlockItem("mossy_sandstone_bricks", MOSSY_SANDSTONE_BRICKS);
    public static final DeferredItem<Item> CACTUS_FLOWER_ITEM = ITEMS.registerItem("cactus_flower", CactusFlowerItem::new);
public static final Supplier<BlockEntityType<SandstoneDungeonChestBlockEntity>> SANDSTONE_DUNGEON_CHEST_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
        "sandstone_dungeon_chest_block_entity",
        () -> BlockEntityType.Builder.of(
                        SandstoneDungeonChestBlockEntity::new,
                        SANDSTONE_DUNGEON_CHEST.get()
                )
                .build(null)
);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DesertTab = CREATIVE_MODE_TABS.register("desert_tab", () -> CreativeModeTab.builder()
        .title(Component.translatable("itemGroup.nerds_better_deserts"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CACTUS_SMALL_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(CACTUS_SMALL_ITEM.get());
                output.accept(CACTUS_FLOWER_ITEM.get());
                output.accept(SANDSTONE_BRICKS_ITEM.get());
                output.accept(MOSSY_SANDSTONE_BRICKS_ITEM.get());
                output.accept(SANDSTONE_DUNGEON_CHEST_ITEM.get());
            }).build());

    public Nerds_better_deserts(IEventBus modEventBus, ModContainer modContainer)
    {

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS)
            event.accept(CACTUS_SMALL_ITEM);
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS)
            event.accept(CACTUS_FLOWER_ITEM);
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(SANDSTONE_BRICKS_ITEM);
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(MOSSY_SANDSTONE_BRICKS_ITEM);

    }
}
