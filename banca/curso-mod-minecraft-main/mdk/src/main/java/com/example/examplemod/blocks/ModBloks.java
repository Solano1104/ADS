package com.example.examplemod.blocks;

import com.example.examplemod.ArmoryOfTheFallenMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.rmi.registry.Registry;
import java.util.function.Supplier;

public class ModBloks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ArmoryOfTheFallenMod.MODID );

    public static final RegistryObject<Block> teste =
            BLOCKS.register("teste", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops()));

    public static RegistryObject<Block> registerblock(String name, Supplier<Block> supplier){
        RegistryObject<Block> result = BLOCKS.register(name, supplier);
        return result;
    }

    public  static RegistryObject<Item> registryBlockItem(String name, RegistryObject<Block>resgistry)

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);


    }
}




