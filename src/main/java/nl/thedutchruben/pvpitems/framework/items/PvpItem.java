package nl.thedutchruben.pvpitems.framework.items;

import nl.thedutchruben.pvpitems.PvpItems;
import nl.thedutchruben.pvpitems.utils.FileManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The PvpItem is the default class to make different type's of pvp items
 * @author Ruben de Roos
 * @since 1.0
 * @version 1.0
 */
public abstract class PvpItem {
    /**
     * Config to find all the settings of the item
     */
    private static final FileManager.Config config = PvpItems.getInstance().getFileManager().getConfig("items.yml");

    /**
     * The name of the item <br>
     * This is accessible with the {@link #getName()} function
     */
    private final String name;
    /**
     * The type of the item <br>
     * This is accessible with the {@link #getPvpItemType()} function
     */
    private final PvpItemType pvpItemType;

    /**
     *
     */
    private PvpItemAction pvpItemAction;
    /**
     * The item herzelf is an Bukkit {@link ItemStack} <br>
     * This is accessible with the {@link #getItemStack()} function <br>
     * This is settable with the {@link #setItemStack(ItemStack)} ()} function
     */
    private ItemStack itemStack;

    /**
     * Default constructor for the {@link PvpItem}
     *
     * @param name The name of the item;
     * @param pvpItemType the {@link PvpItemType} of the item;
     */
    public PvpItem(String name, PvpItemType pvpItemType) {
        this.name = name;
        this.pvpItemType = pvpItemType;
    }

    /**
     * Set's the item that will be used for the {@link PvpItem}
     * @param itemStack The bukkit {@link ItemStack} that the player will be used
     */
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Get the bucket item stack that will be used for the {@link PvpItem}
     * @return an bukkit {@link ItemStack}
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Get the name of the {@link PvpItem}
     * @return {@link String} of the {@link PvpItem}
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of the {@link PvpItem}
     * @return {@link PvpItemType} of the {@link PvpItem}
     */
    public PvpItemType getPvpItemType() {
        return pvpItemType;
    }

    public PvpItemAction getPvpItemAction() {
        return pvpItemAction;
    }

    public void setPvpItemAction(PvpItemAction pvpItemAction) {
        this.pvpItemAction = pvpItemAction;
    }

    /**
     * Executes the {@link PvpItem}
     * @param player The {@link Player} that execute the {@link PvpItem}
     */
    public abstract void execute(Player player);

    public static FileManager.Config getConfig() {
        return config;
    }

    /**
     * Executes the {@link PvpItem}
     * @param player The {@link Player} that execute the {@link PvpItem}
     * @param target The target is a {@link Entity} that the player hits
     */
    public abstract void executeOnTarget(Player player, Entity target);
}
