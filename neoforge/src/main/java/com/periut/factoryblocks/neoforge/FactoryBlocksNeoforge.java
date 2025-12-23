package com.periut.factoryblocks.neoforge;

import com.periut.factoryblocks.FactoryBlocksMod;
import com.periut.factoryblocks.platform.neoforge.RegistryHelperImpl;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;

@Mod(FactoryBlocksMod.MODID)
public class FactoryBlocksNeoforge
{
    public FactoryBlocksNeoforge(IEventBus modEventBus)
    {
        FactoryBlocksMod.init();

        // Register all DeferredRegisters
        for (DeferredRegister<?> register : RegistryHelperImpl.getRegisters()) {
            register.register(modEventBus);
        }

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        Map<RegistryKey<ItemGroup>, List<Item>> entries = RegistryHelperImpl.getItemGroupEntries();
        for (Map.Entry<RegistryKey<ItemGroup>, List<Item>> entry : entries.entrySet()) {
            if (event.getTabKey().equals(entry.getKey())) {
                for (Item item : entry.getValue()) {
                    event.add(item);
                }
            }
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FactoryBlocksMod.post(ModList.get().isLoaded("chisel"));
        });
    }
}