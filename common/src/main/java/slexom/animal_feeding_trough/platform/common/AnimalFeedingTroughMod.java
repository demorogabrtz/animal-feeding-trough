package slexom.animal_feeding_trough.platform.common;

import com.google.common.base.Suppliers;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slexom.animal_feeding_trough.platform.common.block.FeedingTroughBlock;
import slexom.animal_feeding_trough.platform.common.block.entity.FeedingTroughBlockEntity;
import slexom.animal_feeding_trough.platform.common.screen.FeedingTroughScreen;
import slexom.animal_feeding_trough.platform.common.screen.FeedingTroughScreenHandler;

import java.util.Set;
import java.util.function.Supplier;

public class AnimalFeedingTroughMod {

    public static final Logger LOGGER = LogManager.getLogger("Animal Feeding Trough");
    public static final String MOD_ID = "animal_feeding_trough";

    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static final Registrar<Block> BLOCK_REGISTRAR = REGISTRIES.get().get(Registries.BLOCK);
    public static final Registrar<Item> ITEM_REGISTRAR = REGISTRIES.get().get(Registries.ITEM);
    public static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPE_REGISTRAR = REGISTRIES.get().get(Registries.BLOCK_ENTITY_TYPE);
    public static final Registrar<MenuType<?>> SCREEN_HANDLER_TYPE_REGISTRAR = REGISTRIES.get().get(Registries.MENU);

    public static final ResourceLocation REGISTRY_NAME = ResourceLocation.fromNamespaceAndPath(AnimalFeedingTroughMod.MOD_ID, "feeding_trough");
    public static RegistrySupplier<Block> FEEDING_TROUGH_BLOCK = BLOCK_REGISTRAR.register(REGISTRY_NAME, () -> new FeedingTroughBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(0.2f).sound(SoundType.WOOD).ignitedByLava().noOcclusion().setId(ResourceKey.create(Registries.BLOCK, REGISTRY_NAME))));
    public static RegistrySupplier<BlockItem> FEEDING_TROUGH_BLOCK_ITEM = ITEM_REGISTRAR.register(REGISTRY_NAME, () -> new BlockItem(FEEDING_TROUGH_BLOCK.get(), new Item.Properties().arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES).useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, REGISTRY_NAME))));

    public static void onInitialize() {
        LOGGER.info("[Animal Feeding Trough] Load Complete! Enjoy :D");
    }

    public static void onInitializeClient() {
        MenuRegistry.registerScreenFactory(FEEDING_TROUGH_SCREEN_HANDLER.get(), FeedingTroughScreen::new);
    }

    public static RegistrySupplier<MenuType<FeedingTroughScreenHandler>> FEEDING_TROUGH_SCREEN_HANDLER = SCREEN_HANDLER_TYPE_REGISTRAR.register(REGISTRY_NAME, () -> new MenuType<>(FeedingTroughScreenHandler::new, FeatureFlags.VANILLA_SET));
    public static RegistrySupplier<BlockEntityType<FeedingTroughBlockEntity>> FEEDING_TROUGH_BLOCK_ENTITY = BLOCK_ENTITY_TYPE_REGISTRAR.register(REGISTRY_NAME, () -> new BlockEntityType<>(FeedingTroughBlockEntity::new, Set.of(FEEDING_TROUGH_BLOCK.get())));

}
