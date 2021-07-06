package nl.thedutchruben.pvpitems.modules.items;

import nl.thedutchruben.pvpitems.PvpItems;
import nl.thedutchruben.pvpitems.framework.items.PvpItem;
import nl.thedutchruben.pvpitems.framework.items.PvpItemAction;
import nl.thedutchruben.pvpitems.framework.items.throw_items.ThrowKnife;
import nl.thedutchruben.pvpitems.framework.items.throw_items.TntBomb;
import nl.thedutchruben.pvpitems.modules.items.commands.PvpItemsCommand;
import nl.thedutchruben.pvpitems.modules.items.listeners.PlayerInteractListener;
import nl.thedutchruben.pvpitems.modules.items.listeners.PlayerPickupListener;
import nl.thedutchruben.pvpitems.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class ItemModule {
    public List<PvpItem> pvpItems = new ArrayList<>();
    public FileManager.Config config = PvpItems.getInstance().getFileManager().getConfig("items.yml");
    public ItemModule(){
        setupConfig();

        if(config.get().getBoolean("items.tntbomb.enabled",true)) {
            pvpItems.add(new TntBomb());
        }

        //Register all swords as throwable knives
        for (Material material : Arrays.asList(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD)) {
            if(config.get().getBoolean("items.throw_knife_"+material.name().toLowerCase(Locale.ROOT).split("_")[0] +".enabled",true)) {
                pvpItems.add(new ThrowKnife(material.name().toLowerCase(Locale.ROOT).split("_")[0] + " Knive" ,material));
            }
        }

        PvpItems.getInstance().getCommand("pvpitems").setExecutor(new PvpItemsCommand());
        PvpItems.getInstance().getCommand("pvpitems").setTabCompleter(new PvpItemsCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(),PvpItems.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerPickupListener(),PvpItems.getInstance());

    }

    /**
     * Setup the config and migrations to newer versions of the config
     */
    public void setupConfig(){
        if(!new File(config.get().getCurrentPath()).exists()){
            config.saveDefaultConfig();
        }
    }

    /**
     * Get a {@link PvpItem} from a {@link ItemStack} and {@link PvpItemAction}
     * @param itemStack The {@link ItemStack} what we need to search
     * @param pvpItemAction
     * @return
     */
    public Optional<PvpItem> getPvpItem(ItemStack itemStack, PvpItemAction pvpItemAction){
        return pvpItems.stream().filter(pvpItem -> pvpItem.getPvpItemAction() == pvpItemAction).filter(pvpItem -> pvpItem.getItemStack().isSimilar(itemStack)).findFirst();
    }

    /**
     *
     */
    public Optional<PvpItem> getPvpItem(String name){
        return pvpItems.stream().filter(pvpItem -> pvpItem.getName().equalsIgnoreCase(name)).findFirst();
    }

    /**
     * Get all the pvp items registerd for use
     * @return {@link List} of {@link PvpItem}
     */
    public List<PvpItem> getPvpItems() {
        return pvpItems;
    }
}
