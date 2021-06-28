package nl.thedutchruben.pvpitems;

import de.jeff_media.updatechecker.UpdateChecker;
import de.jeff_media.updatechecker.UserAgentBuilder;
import nl.thedutchruben.pvpitems.framework.items.PvpItem;
import nl.thedutchruben.pvpitems.modules.items.ItemModule;
import nl.thedutchruben.pvpitems.utils.FileManager;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;

public final class PvpItems extends JavaPlugin {
    private static PvpItems instance;
    private ItemModule itemModule;
    private FileManager fileManager;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        fileManager = new FileManager(this);
        Metrics metrics = new Metrics(this, 11864);
        itemModule = new ItemModule();


        for (PvpItem pvpItem : itemModule.getPvpItems()) {
            System.out.println(pvpItem.getName());
            metrics.addCustomChart(new SimplePie("items", pvpItem::getName));
        }
        UpdateChecker.init(this, "https://thedutchruben.nl/api/projects/version/pvpitems") // A link to a URL that contains the latest version as String
                .setDownloadLink("https://www.spigotmc.org/resources/tdrplaytime-milestones-mysql.47894/") // You can either use a custom URL or the Spigot Resource ID
                .setDonationLink("https://www.paypal.com/paypalme/RGSYT")
                .setChangelogLink(47894) // Same as for the Download link: URL or Spigot Resource ID
                .setNotifyOpsOnJoin(true) // Notify OPs on Join when a new version is found (default)
                .setNotifyByPermissionOnJoin("thedutchruben.updatechecker") // Also notify people on join with this permission
                .setUserAgent(new UserAgentBuilder().addPluginNameAndVersion())
                .checkEveryXHours(0.5) // Check every 30 minutes
                .checkNow(); // And check right now
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public ItemModule getItemModule() {
        return itemModule;
    }

    public static PvpItems getInstance() {
        return instance;
    }
}
