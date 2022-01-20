package com.jemmerl.rekindleprimitive.inventory.container;

import com.jemmerl.rekindleprimitive.RekindlePrimitive;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {

    public static DeferredRegister<ContainerType<?>> CONTAINERS
            = DeferredRegister.create(ForgeRegistries.CONTAINERS, RekindlePrimitive.MOD_ID);

    public static final RegistryObject<ContainerType<BasicCrucibleContainer>> BASIC_CRUCIBLE_CONTAINER
                = CONTAINERS.register("basic_crucible_container",
                () -> IForgeContainerType.create(((windowId, inv, data) -> new BasicCrucibleContainer(windowId, inv, data))));

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }

}
