package nl.thedutchruben.pvpitems.modules.items.commands;

import nl.thedutchruben.pvpitems.PvpItems;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The main command of the plugin.
 * This command will give the item to the player
 */
public class PvpItemsCommand implements CommandExecutor , TabCompleter {

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args[0] == null){
                sender.sendMessage(ChatColor.DARK_RED + "No item speccefied /pvpitem <itemName>");
        }else{
            PvpItems.getInstance().getItemModule().getPvpItem(args[0].replace("_"," ")).ifPresentOrElse(pvpItem -> {
                if(sender instanceof Player){
                    ((Player) sender).getInventory().addItem(pvpItem.getItemStack());
                    sender.sendMessage(pvpItem.getName() + " given!");
                }else{
                    sender.sendMessage(ChatColor.RED + "Only Players can get items");
                }
            },() -> sender.sendMessage(ChatColor.DARK_RED + "Item not found"));
        }
        return false;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> names = new ArrayList<>();
        PvpItems.getInstance().getItemModule().getPvpItems().forEach(pvpItem -> names.add(pvpItem.getName().replace(" ", "_")));
        return names;
    }
}
