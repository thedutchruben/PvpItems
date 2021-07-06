package nl.thedutchruben.pvpitems.framework.items.throw_items;

import nl.thedutchruben.pvpitems.PvpItems;
import nl.thedutchruben.pvpitems.framework.items.PvpItem;
import nl.thedutchruben.pvpitems.framework.items.PvpItemAction;
import nl.thedutchruben.pvpitems.framework.items.PvpItemType;
import nl.thedutchruben.pvpitems.utils.ItemBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Locale;

public class ThrowKnife extends PvpItem {
    private final int damage;
    /**
     * Default constructor for the {@link PvpItem}
     *
     * @param name        The name of the item;
     * @param material    The {@link Material} of the item;
     */
    public ThrowKnife(String name, Material material) {
        super(name, PvpItemType.THROW_ITEM);
        setPvpItemAction(PvpItemAction.RIGHT_CLICK);
        setItemStack(new ItemBuilder(material).setDisplayName(ChatColor.translateAlternateColorCodes('&',getConfig().get().getString("items.throw_knife_"+material.name().toLowerCase(Locale.ROOT).split("_")[0] +".name","&4Knive"))).build());
        damage = getConfig().get().getInt("items.throw_knife_"+material.name().toLowerCase(Locale.ROOT).split("_")[0] +".damage",1);
    }

    /**
     * Executes the {@link PvpItem}
     *
     * The sword get launched and the runnable will check if it hits an player
     *
     * @param player The {@link Player} that execute the {@link PvpItem}
     */
    @Override
    public void execute(Player player) {
        if(getConfig().get().getBoolean("items.throw_knife_"+getItemStack().getType().name().toLowerCase(Locale.ROOT).split("_")[0] +".decrease_durability",true)){
            ((Damageable)player.getInventory().getItemInMainHand().getItemMeta()).setDamage(((Damageable)player.getInventory().getItemInMainHand().getItemMeta()).getDamage() - 1);
        }

        Item throwItem = player.getWorld().dropItem(player.getLocation().add(0,1.5,0)
                , getItemStack());
        if(getConfig().get().getBoolean("items.throw_knife_"+getItemStack().getType().name().toLowerCase(Locale.ROOT).split("_")[0] +".thrower_only_pickup",true)){
            throwItem.getScoreboardTags().add("PICKUP-BLOCK");
            throwItem.getScoreboardTags().add("PICKUP-" + player.getUniqueId());
        }
        throwItem.setVelocity(player.getLocation().getDirection());
        throwItem.getWorld().playSound(throwItem.getLocation(), Sound.ITEM_TRIDENT_THROW,1,1);
        //Run a task to handle damage and particles
        Bukkit.getScheduler().runTaskTimer(PvpItems.getInstance(),(bukkitTask) -> {
            throwItem.getWorld().spawnParticle(Particle.SMALL_FLAME,throwItem.getLocation(),1,0,0,0);
            if(throwItem.isDead() || throwItem.isOnGround()){
                bukkitTask.cancel();
            }

            for (Entity nearbyEntity : throwItem.getNearbyEntities(0.2, 0.2, 0.2)) {
                if(nearbyEntity instanceof LivingEntity){
                    ((LivingEntity) nearbyEntity).damage(damage,player);
                }
            }

        },1,1);
        ItemStack toRemove = getItemStack().clone();
        toRemove.setAmount(1);
        player.getInventory().remove(toRemove);
    }

    /**
     * Executes the {@link PvpItem}
     *
     * @param player The {@link Player} that execute the {@link PvpItem}
     * @param target The target is a {@link Entity} that the player hits
     */
    @Override
    public void executeOnTarget(Player player, Entity target) {

    }

    public int getDamage() {
        return damage;
    }
}
