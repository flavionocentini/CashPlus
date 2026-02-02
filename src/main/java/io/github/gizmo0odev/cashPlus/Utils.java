package io.github.gizmo0odev.cashPlus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Utils {

    public static String prefix = "" + ChatColor.GRAY + ChatColor.BOLD + "[" + ChatColor.GREEN + ChatColor.BOLD + "Cash+" + ChatColor.GRAY + ChatColor.BOLD + "] ";

    public static Material cashMaterial = Material.RAW_GOLD;

    public static ChatColor success = ChatColor.GREEN;
    public static ChatColor error = ChatColor.RED;

    public static void playError(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
    }

    public static void playSuccess(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
    }

    public static int countCash(Player player) {
        int amount = 0;
        Inventory inv = player.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null) {
                continue;
            }
            if (item.getType() == cashMaterial && item.getItemMeta() != null && item.getItemMeta().hasEnchant(Enchantment.UNBREAKING)) {
                amount += item.getAmount();
            }
        }
        return amount;
    }

    public static void removeCash(Player player, int amountToRemove) {
        Inventory inv = player.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null) {
                continue;
            }
            if (item.getType() == cashMaterial && item.getItemMeta() != null && item.getItemMeta().hasEnchant(Enchantment.UNBREAKING)) {
                int itemAmount = item.getAmount();
                if (itemAmount <= amountToRemove) {
                    player.getInventory().remove(item);
                    amountToRemove = amountToRemove - itemAmount;
                } else {
                    item.setAmount(itemAmount - amountToRemove);
                    break;
                }
                if (amountToRemove <= 0) {
                    break;
                }
            }
        }
    }

}
