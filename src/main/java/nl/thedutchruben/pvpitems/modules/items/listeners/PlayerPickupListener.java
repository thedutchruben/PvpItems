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
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerPickupListener implements Listener {

    @EventHandler
    public void onJoin(EntityPickupItemEvent event){
        if(event.getItem().getScoreboardTags().contains("PICKUP-BLOCK")){
            if(!event.getItem().getScoreboardTags().contains("PICKUP-" + event.getEntity().getUniqueId())){
                event.setCancelled(true);
            }
        }
    }

}
