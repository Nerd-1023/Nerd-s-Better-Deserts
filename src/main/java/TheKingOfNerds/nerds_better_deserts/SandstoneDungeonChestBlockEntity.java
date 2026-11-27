package TheKingOfNerds.nerds_better_deserts;

import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

import static TheKingOfNerds.nerds_better_deserts.Nerds_better_deserts.*;

public class SandstoneDungeonChestBlockEntity extends RandomizableContainerBlockEntity implements GameEventListener.Provider<SandstoneDungeonChestBlockEntity.DeathListener> {
    private NonNullList<ItemStack> items;
    public int KillsLeft = 25;
    private final ContainerOpenersCounter openersCounter;
    private final DeathListener  deathListener;
    public SandstoneDungeonChestBlockEntity(BlockPos pos, BlockState blockState) {
        super(SANDSTONE_DUNGEON_CHEST_BLOCK_ENTITY.get(), pos, blockState);
        this.deathListener = new DeathListener(blockState, new BlockPositionSource(pos));
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(Level p_155062_, BlockPos p_155063_, BlockState p_155064_) {

            }

            protected void onClose(Level p_155072_, BlockPos p_155073_, BlockState p_155074_) {

            }

            protected void openerCountChanged(Level p_155066_, BlockPos p_155067_, BlockState p_155068_, int p_155069_, int p_155070_) {
            }

            protected boolean isOwnContainer(Player p_155060_) {
                if (p_155060_.containerMenu instanceof ChestMenu) {
                    Container container = ((ChestMenu)p_155060_.containerMenu).getContainer();
                    return container == SandstoneDungeonChestBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    @Override
    public boolean canOpen(Player p_59643_) {
        if(KillsLeft <= 0){
        return super.canOpen(p_59643_) && (this.lootTable == null || !p_59643_.isSpectator());}
        else{return false;}
    }
    @Override
    protected void saveAdditional(CompoundTag p_187459_, HolderLookup.Provider p_323686_) {
        super.saveAdditional(p_187459_, p_323686_);
        p_187459_.putInt("kills", this.KillsLeft);
        if (!this.trySaveLootTable(p_187459_)) {
            ContainerHelper.saveAllItems(p_187459_, this.items, p_323686_);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag p_155055_, HolderLookup.Provider p_324230_) {
        super.loadAdditional(p_155055_, p_324230_);
        this.KillsLeft = p_155055_.getInt("kills");
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(p_155055_)) {
            ContainerHelper.loadAllItems(p_155055_, this.items, p_324230_);
        }

    }

    protected void setKillCount(int count) {
        KillsLeft = count;
        setChanged();
    }
    public int getKillCount(int count) {
        return KillsLeft;
    }

    public int getContainerSize() {
        return 27;
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    protected Component getDefaultName() {
        return Component.translatable("container.sandstone_dungeon_chest");
    }

    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return ChestMenu.threeRows(id, player, this);
    }

    public void startOpen(Player p_58616_) {
        if (!this.remove && !p_58616_.isSpectator()) {
            this.openersCounter.incrementOpeners(p_58616_, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public void stopOpen(Player p_58614_) {
        if (!this.remove && !p_58614_.isSpectator()) {
            this.openersCounter.decrementOpeners(p_58614_, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    void updateBlockState(BlockState state, boolean open) {
    }

    void playSound(BlockState state, SoundEvent sound) { }
    public DeathListener getListener() {
        return this.deathListener;
    }

    public class DeathListener implements GameEventListener {
        public static final int PULSE_TICKS = 8;
        private final BlockState blockState;
        private final PositionSource positionSource;

        public DeathListener(BlockState blockState, PositionSource positionSource) {
            this.blockState = blockState;
            this.positionSource = positionSource;
        }

        public PositionSource getListenerSource() {
            return this.positionSource;
        }

        public int getListenerRadius() {
            return 16;
        }

        public GameEventListener.DeliveryMode getDeliveryMode() {
            return DeliveryMode.BY_DISTANCE;
        }

        public boolean handleGameEvent(ServerLevel p_283470_, Holder<GameEvent> p_316661_, GameEvent.Context p_283014_, Vec3 p_282350_) {
            if (p_316661_.is(GameEvent.ENTITY_DIE)) {
                Entity var6 = p_283014_.sourceEntity();
                if (var6 instanceof LivingEntity) {
                    if(KillsLeft >= 1){KillsLeft -= 1;}
                    setChanged();
                    if(20 <= KillsLeft && KillsLeft <= 24){
                        p_283014_.sourceEntity().level().setBlock(BlockPos.containing(positionSource.getPosition(p_283014_.sourceEntity().level()).get().x,positionSource.getPosition(p_283014_.sourceEntity().level()).get().y,positionSource.getPosition(p_283014_.sourceEntity().level()).get().z),SANDSTONE_DUNGEON_CHEST.get().defaultBlockState().setValue(SandstoneDungeonChestBlock.LEVEL,1), 3);
                    }
                    if(15 <= KillsLeft && KillsLeft <= 19){
                        p_283014_.sourceEntity().level().setBlock(BlockPos.containing(positionSource.getPosition(p_283014_.sourceEntity().level()).get().x,positionSource.getPosition(p_283014_.sourceEntity().level()).get().y,positionSource.getPosition(p_283014_.sourceEntity().level()).get().z),SANDSTONE_DUNGEON_CHEST.get().defaultBlockState().setValue(SandstoneDungeonChestBlock.LEVEL,2), 3);
                    }

                    if(10 <= KillsLeft && KillsLeft <= 14){
                        p_283014_.sourceEntity().level().setBlock(BlockPos.containing(positionSource.getPosition(p_283014_.sourceEntity().level()).get().x,positionSource.getPosition(p_283014_.sourceEntity().level()).get().y,positionSource.getPosition(p_283014_.sourceEntity().level()).get().z),SANDSTONE_DUNGEON_CHEST.get().defaultBlockState().setValue(SandstoneDungeonChestBlock.LEVEL,3), 3);
                    }

                    if(5 <= KillsLeft && KillsLeft <= 9){
                        p_283014_.sourceEntity().level().setBlock(BlockPos.containing(positionSource.getPosition(p_283014_.sourceEntity().level()).get().x,positionSource.getPosition(p_283014_.sourceEntity().level()).get().y,positionSource.getPosition(p_283014_.sourceEntity().level()).get().z),SANDSTONE_DUNGEON_CHEST.get().defaultBlockState().setValue(SandstoneDungeonChestBlock.LEVEL,4), 3);
                    }
                    if(1 <= KillsLeft && KillsLeft <= 4){
                        p_283014_.sourceEntity().level().setBlock(BlockPos.containing(positionSource.getPosition(p_283014_.sourceEntity().level()).get().x,positionSource.getPosition(p_283014_.sourceEntity().level()).get().y,positionSource.getPosition(p_283014_.sourceEntity().level()).get().z),SANDSTONE_DUNGEON_CHEST.get().defaultBlockState().setValue(SandstoneDungeonChestBlock.LEVEL,5), 3);
                    }

                    if(25 == KillsLeft){
                        p_283014_.sourceEntity().level().setBlock(BlockPos.containing(positionSource.getPosition(p_283014_.sourceEntity().level()).get().x,positionSource.getPosition(p_283014_.sourceEntity().level()).get().y,positionSource.getPosition(p_283014_.sourceEntity().level()).get().z),SANDSTONE_DUNGEON_CHEST.get().defaultBlockState().setValue(SandstoneDungeonChestBlock.LEVEL,0), 3);
                    }
                    if(0 == KillsLeft){
                        p_283014_.sourceEntity().level().setBlock(BlockPos.containing(positionSource.getPosition(p_283014_.sourceEntity().level()).get().x,positionSource.getPosition(p_283014_.sourceEntity().level()).get().y,positionSource.getPosition(p_283014_.sourceEntity().level()).get().z),SANDSTONE_DUNGEON_CHEST.get().defaultBlockState().setValue(SandstoneDungeonChestBlock.LEVEL,6), 3);
                    }
                    return true;
                }
            }

            return false;
        }
    }
    }
