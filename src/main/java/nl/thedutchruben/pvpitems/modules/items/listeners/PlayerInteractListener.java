package nl.thedutchruben.pvpitems.modules.items.listeners;

import nl.thedutchruben.pvpitems.PvpItems;
import nl.thedutchruben.pvpitems.framework.items.PvpItem;
import nl.thedutchruben.pvpitems.framework.items.PvpItemAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        for (PvpItem pvpItem : PvpItems.getInstance().getItemModule().getPvpItems()) {
            event.getPlayer().getInventory().addItem(pvpItem.getItemStack());
        }
    }

    @EventHandler
    public void onBuild(BlockPlaceEvent event){
        PvpItems.getInstance().getItemModule().getPvpItem(event.getItemInHand(), PvpItemAction.RIGHT_CLICK).ifPresent(pvpItem -> {
            pvpItem.execute(event.getPlayer());
            event.setCancelled(true);
        });
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        PvpItems.getInstance().getItemModule().getPvpItem(event.getPlayer().getInventory().getItemInMainHand(), PvpItemAction.LEFT_CLICK).ifPresent(pvpItem -> {
            pvpItem.execute(event.getPlayer());
            event.setCancelled(true);
        });
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getItem() != null){
            if(event.getAction() == Action.RIGHT_CLICK_AIR){
                PvpItems.getInstance().getItemModule().getPvpItem(event.getItem(), PvpItemAction.RIGHT_CLICK).ifPresent(pvpItem -> {
                    pvpItem.execute(event.getPlayer());
                    event.setCancelled(true);
                });
            }

            if(event.getAction() == Action.LEFT_CLICK_AIR){
                PvpItems.getInstance().getItemModule().getPvpItem(event.getItem(), PvpItemAction.LEFT_CLICK).ifPresent(pvpItem -> {
                    pvpItem.execute(event.getPlayer());
                    event.setCancelled(true);
                });
            }
        }

    }

    @EventHandler
    public void onClick(PlayerInteractEntityEvent event){
        if(event.getHand() == EquipmentSlot.HAND){
            if(event.getPlayer().getInventory().getItemInMainHand() != null){
                PvpItems.getInstance().getItemModule().getPvpItem(event.getPlayer().getInventory().getItemInMainHand(), PvpItemAction.RIGHT_CLICK_PLAYER).ifPresent(pvpItem -> {
                    pvpItem.executeOnTarget(event.getPlayer(),event.getRightClicked());
                });
            }
        }
    }

    @EventHandler
    public void onClick(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            if(((Player)event.getDamager()).getInventory().getItemInMainHand() != null){
                PvpItems.getInstance().getItemModule().getPvpItem(((Player)event.getDamager()).getInventory().getItemInMainHand(), PvpItemAction.LEFT_CLICK_PLAYER).ifPresent(pvpItem -> {
                    pvpItem.executeOnTarget(((Player)event.getDamager()),event.getEntity());
                });
            }

        }

    }
}
