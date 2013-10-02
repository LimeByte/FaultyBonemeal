package me.limebyte.faultybonemeal;

import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dispenser;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableMap;

public class FaultyBoneMeal extends JavaPlugin implements Listener {

    private static final ItemStack BONE_MEAL = new ItemStack(Material.INK_SACK, 1, (short) 15);
    private static final Map<Material, Byte> BONE_MEALABLE = ImmutableMap.<Material, Byte> builder().put(Material.SAPLING, Byte.MAX_VALUE).put(Material.CROPS, (byte) 6).put(Material.CARROT, (byte) 6)
            .put(Material.POTATO, (byte) 6).put(Material.COCOA, (byte) 7).put(Material.MELON_STEM, (byte) 6).put(Material.PUMPKIN_STEM, (byte) 6).put(Material.BROWN_MUSHROOM, Byte.MAX_VALUE)
            .put(Material.RED_MUSHROOM, Byte.MAX_VALUE).put(Material.GRASS, Byte.MAX_VALUE).build();

    private Logger log;

    @Override
    public void onEnable() {
        this.log = this.getLogger();

        getServer().getPluginManager().registerEvents(this, this);
        log.info(getDescription().getFullName() + " enabled succesfully!");
    }

    @Override
    public void onDisable() {
        log.info(getDescription().getFullName() + " disabled.");
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockDispense(BlockDispenseEvent event) {
        if (!event.getItem().isSimilar(BONE_MEAL)) return;

        Block block = event.getBlock();
        if (block.getType() != Material.DISPENSER) return;

        BlockFace face = ((Dispenser) block.getState().getData()).getFacing();
        block = block.getRelative(face);

        if (BONE_MEALABLE.containsKey(block.getType())) {
            if (block.getData() <= BONE_MEALABLE.get(block.getType())) return;
        }

        event.setCancelled(true);
        return;
    }
}
