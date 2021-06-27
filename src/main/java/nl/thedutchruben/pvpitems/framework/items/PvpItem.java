package nl.thedutchruben.pvpitems.framework.items;

import org.bukkit.entity.Player;

public abstract class PvpItem {
    private final String name;
    private final PvpItemType pvpItemType;

    public PvpItem(String name, PvpItemType pvpItemType) {
        this.name = name;
        this.pvpItemType = pvpItemType;
    }

    public String getName() {
        return name;
    }

    public PvpItemType getPvpItemType() {
        return pvpItemType;
    }

    public abstract void execute(Player player);

    public abstract void executeOnTarget(Player player,Player target);
}
