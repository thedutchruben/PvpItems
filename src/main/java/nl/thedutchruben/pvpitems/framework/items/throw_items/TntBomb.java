package nl.thedutchruben.pvpitems.framework.items.throw_items;

import nl.thedutchruben.pvpitems.PvpItems;
import nl.thedutchruben.pvpitems.framework.items.PvpItem;
import nl.thedutchruben.pvpitems.framework.items.PvpItemType;
import nl.thedutchruben.pvpitems.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

/**
 * This item is an throwable item that will explode after a few moments.
 * @author Ruben de Roos
 * @since 1.0
 * @version 1.0
 * @see nl.thedutchruben.pvpitems.framework.items.PvpItem
 */
public class TntBomb extends PvpItem {

    /**
     * Default constructor for the {@link PvpItem}
     *
     */
    public TntBomb() {
        super("TNT Bomb", PvpItemType.THROW_ITEM);
        setItemStack(new ItemBuilder(Material.TNT).setDisplayName("Tnt Bomb").setLore("Right click to trow the tnt").build());
    }

    /**
     * Executes the {@link PvpItem}
     *
     * @param player The {@link Player} that execute the {@link PvpItem}
     */
    @Override
    public void execute(Player player) {
        Item throwItem = player.getWorld().dropItem(player.getLocation()
                , player.getInventory().getItemInMainHand());
        throwItem.setPickupDelay(Integer.MAX_VALUE);
        throwItem.setVelocity(player.getLocation().getDirection());
        throwItem.getWorld().playSound(throwItem.getLocation(), Sound.ENTITY_TNT_PRIMED,1,1);
        Bukkit.getScheduler().runTaskLater(PvpItems.getInstance(),() -> {
            throwItem.getWorld().createExplosion(throwItem.getLocation(),getConfig().get().getInt("items.tntbomb.power",3), getConfig().get().getBoolean("items.tntbomb.fire",true),getConfig().get().getBoolean("items.tntbomb.breakblocks",true),player);
            throwItem.remove();
        },getConfig().get().getInt("items.tntbomb.explodetime",3)* 20L);
    }

    /**
     * Executes the {@link PvpItem}
     *
     * @param player The {@link Player} that execute the {@link PvpItem}
     * @param target The target is a {@link Player} that the player hits
     */
    @Override
    public void executeOnTarget(Player player, Player target) {

    }
}
