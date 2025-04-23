package me.antigeddon.snowlayers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.BlockFace;

public class sSnow implements Listener {

    public sSnow(sMain plugin) {
    }

    @EventHandler
    public void onSnowLayerInteract(PlayerInteractEvent event) {

        if (!event.getAction().toString().contains("RIGHT_CLICK")) return;

        Player player = event.getPlayer();
        Block clicked = event.getClickedBlock();
        ItemStack inHand = player.getItemInHand();


        if (clicked == null || clicked.getType() != Material.SNOW) return;
        if (inHand == null || inHand.getType() != Material.SNOW) return;


        if (!player.hasPermission("snowlayers.place")) {
            event.setCancelled(true);
            return;
        }

        if (event.getBlockFace() != BlockFace.UP) {
            event.setCancelled(true);
            return;
        }


        // Max Height
        if (clicked.getData() == 7 || clicked.getData() == 15) {
            event.setCancelled(true);
            return;
        }


        BlockPlaceEvent placeEvent = new BlockPlaceEvent(clicked, clicked.getState(), clicked.getRelative(BlockFace.SELF), inHand, player, true);
        Bukkit.getServer().getPluginManager().callEvent(placeEvent);
        if (placeEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }


        BlockBreakEvent breakEvent = new BlockBreakEvent(clicked, player);
        Bukkit.getServer().getPluginManager().callEvent(breakEvent);
        if (breakEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);

        byte newMeta = (byte) (clicked.getData() + 1);
        clicked.setType(Material.SNOW); // Safety
        clicked.setData(newMeta);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendBlockChange(clicked.getLocation(), Material.SNOW, newMeta);
        }

        int newAmount = inHand.getAmount() - 1;
        if (newAmount <= 0) {
            player.setItemInHand(null);
        } else {
            inHand.setAmount(newAmount);
            player.setItemInHand(inHand);
        }
    }
}
