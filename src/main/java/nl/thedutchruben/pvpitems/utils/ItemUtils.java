package nl.thedutchruben.pvpitems.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public static boolean has(Player player, ItemStack itemStack) {
        if (itemStack == null) {
            return player.getInventory().firstEmpty() != -1;
        }

        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if ((slot != null) && (slot.isSimilar(itemStack))) {
                amount += slot.getAmount();
            }
        }
        return amount >= itemStack.getAmount();
    }

    public static boolean hasName(Player player, String itemStack,int needed) {
        if (itemStack == null) {
            return player.getInventory().firstEmpty() != -1;
        }

        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if ((slot != null) && (slot.getItemMeta().getDisplayName().equalsIgnoreCase(itemStack))) {
                amount += slot.getAmount();
            }
        }
        return amount >= needed;
    }

    public static int amount(Player player, Material itemStack) {
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot != null) {
                if (slot.getType() == itemStack) {
                    amount = amount + slot.getAmount();
                }
            }
        }
        return amount;
    }

    public static boolean take(Player player, ItemStack itemStack) {
        if (!has(player, itemStack)) {
            return false;
        }

        int amount = itemStack.getAmount();
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot != null) {
                if (slot.isSimilar(itemStack)) {
                    if (amount - slot.getAmount() <= 0) {
                        slot.setAmount(slot.getAmount() - amount);
                        amount = 0;
                        player.getInventory().setItem(i, slot);
                    } else {
                        amount -= slot.getAmount();
                        player.getInventory().setItem(i, null);
                    }
                    player.updateInventory();
                }
            }
        }

        if (amount > 0) {
            ItemStack toGive = itemStack.clone();
            toGive.setAmount(toGive.getAmount() - amount);
            give(player, toGive);
            return false;
        }

        if (amount < 0) {
            ItemStack toGive = itemStack.clone();
            toGive.setAmount(amount * -1);
            give(player, toGive);
            return false;
        }
        return true;
    }

    public static boolean takeName(Player player, String itemStack,int amountt,ItemStack itemm) {
        if (!hasName(player, itemStack,amountt)) {
            return false;
        }

        int amount = amountt;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot != null) {
                if (slot.getType() == Material.PLAYER_HEAD) {
                    if (slot.getItemMeta().getDisplayName().equalsIgnoreCase(itemStack)) {
                        if (amount - slot.getAmount() <= 0) {
                            slot.setAmount(slot.getAmount() - amount);
                            amount = 0;
                            player.getInventory().setItem(i, slot);
                        } else {
                            amount -= slot.getAmount();
                            player.getInventory().setItem(i, null);
                        }
                        player.updateInventory();
                    }
                }
            }
        }

        if (amount > 0) {
            ItemStack toGive = itemm.clone();
            toGive.setAmount(toGive.getAmount() - amount);
            give(player, toGive);
            return false;
        }

        if (amount < 0) {
            ItemStack toGive = itemm.clone();
            toGive.setAmount(amount * -1);
            give(player, toGive);
            return false;
        }
        return true;
    }

    public static boolean take(Inventory inventory, ItemStack itemStack) {


        int amount = itemStack.getAmount();
        for (int i = 0; i < 36; i++) {
            ItemStack slot = inventory.getItem(i);
            if (slot != null) {
                if (slot.isSimilar(itemStack)) {
                    if (amount - slot.getAmount() <= 0) {
                        slot.setAmount(slot.getAmount() - amount);
                        amount = 0;
                        inventory.setItem(i, slot);
                    } else {
                        amount -= slot.getAmount();
                        inventory.setItem(i, null);
                    }
                }
            }
        }

        if (amount > 0) {
            ItemStack toGive = itemStack.clone();
            toGive.setAmount(toGive.getAmount() - amount);
//            give(player, toGive);
            return false;
        }

        if (amount < 0) {
            ItemStack toGive = itemStack.clone();
            toGive.setAmount(amount * -1);
//            give(player, toGive);
            return false;
        }
        return true;
    }


    public static boolean give(Player player, ItemStack itemStack) {
        if (!hasRoom(player, itemStack)) {
            return false;
        }

        if (itemStack.getAmount() == 1) {
            player.getInventory().addItem(itemStack);

        } else if (itemStack.getAmount() == 16) {
            if (itemStack.getMaxStackSize() == 1) {
                for (int i = 0; i < 16; i++) {
                    ItemStack item = itemStack.clone();
                    item.setAmount(1);
                    player.getInventory().addItem(item);
                }

            } else {
                player.getInventory().addItem(itemStack);
            }

        } else if (itemStack.getAmount() == 32) {
            if (itemStack.getMaxStackSize() == 1) {
                for (int i = 0; i < 32; i++) {
                    ItemStack item = itemStack.clone();
                    item.setAmount(1);
                    player.getInventory().addItem(item);
                }

            } else if (itemStack.getMaxStackSize() == 16) {
                ItemStack item = itemStack.clone();
                item.setAmount(16);
                player.getInventory().addItem(item);
                player.getInventory().addItem(item);

            } else {
                player.getInventory().addItem(itemStack);
            }

        } else if (itemStack.getAmount() == 48) {
            if (itemStack.getMaxStackSize() == 1) {
                for (int i = 0; i < 48; i++) {
                    ItemStack item = itemStack.clone();
                    item.setAmount(1);
                    player.getInventory().addItem(item);
                }

            } else if (itemStack.getMaxStackSize() == 16) {
                for (int i = 0; i < 3; i++) {
                    ItemStack item = itemStack.clone();
                    item.setAmount(16);
                    player.getInventory().addItem(item);
                }

            } else {
                player.getInventory().addItem(itemStack);
            }

        } else if (itemStack.getAmount() == 64) {
            if (itemStack.getMaxStackSize() == 1) {
                for (int i = 0; i < 64; i++) {
                    ItemStack item = itemStack.clone();
                    item.setAmount(1);
                    player.getInventory().addItem(item);
                }

            } else if (itemStack.getMaxStackSize() == 16) {
                for (int i = 0; i < 4; i++) {
                    ItemStack item = itemStack.clone();
                    item.setAmount(16);
                    player.getInventory().addItem(item);
                }

            } else {
                player.getInventory().addItem(itemStack);
            }
        }
        return true;
    }


    public static boolean hasRoom(Player player, ItemStack itemStack) {
        if (itemStack == null) {
            return player.getInventory().firstEmpty() != -1;
        }
        int freeAmount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot == null) {
                freeAmount += itemStack.getMaxStackSize();
            } else {
                int free = slot.getMaxStackSize() - slot.getAmount();
                if (slot.isSimilar(itemStack)) {
                    freeAmount += free;
                }
            }
        }
        return freeAmount >= itemStack.getAmount();
    }

    public static boolean hasItemInEitherHand(Player player, ItemStack itemStack) {
        if (player.getInventory().getItemInMainHand().equals(itemStack)) return true;

        if (player.getInventory().getItemInMainHand() == null) {
            return player.getInventory().getItemInOffHand().equals(itemStack);
        }

        return false;
    }

    public static boolean hasItemInEitherHand(Player player, Material material) {
        if (player.getInventory().getItemInMainHand() == null) {
            if (player.getInventory().getItemInOffHand() == null) return false;

            return player.getInventory().getItemInOffHand().getType().equals(material);
        } else {
            return player.getInventory().getItemInMainHand().getType().equals(material);
        }
    }

}
