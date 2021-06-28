package nl.thedutchruben.pvpitems.modules.items;

import nl.thedutchruben.pvpitems.PvpItems;
import nl.thedutchruben.pvpitems.framework.items.PvpItem;
import nl.thedutchruben.pvpitems.framework.items.throw_items.TntBomb;
import nl.thedutchruben.pvpitems.utils.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemModule {
    public List<PvpItem> pvpItems = new ArrayList<>();
    public FileManager.Config config = PvpItems.getInstance().getFileManager().getConfig("items.yml");
    public ItemModule(){
        if(config.get().getBoolean("items.tntbomb.enabled",true)){
            pvpItems.add(new TntBomb());
        }


        setupConfig();
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
     * Get all the pvp items registerd for use
     * @return {@link List} of {@link PvpItem}
     */
    public List<PvpItem> getPvpItems() {
        return pvpItems;
    }
}
