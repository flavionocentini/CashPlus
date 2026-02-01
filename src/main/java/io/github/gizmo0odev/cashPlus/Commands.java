package io.github.gizmo0odev.cashPlus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static org.bukkit.Bukkit.getLogger;

public class Commands implements CommandExecutor {

    int amount;
    double balance;

    String currencySymbol = "$";
    String currencyName = "Coin";
    ChatColor currencyColor = ChatColor.YELLOW;

    ChatColor success = Utils.success;
    ChatColor error = Utils.error;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("withdraw")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Inventory inv = player.getInventory();
                if (player.hasPermission("cashplus.withdraw")) {
                    if (args.length >= 1) {
                        if (args[0].matches("-?\\d+")) {
                            amount = Integer.parseInt(args[0]);
                            balance = CashPlus.getEconomy().getBalance(player);
                            if (CashPlus.getEconomy().has(player, amount)) {

                                CashPlus.getEconomy().withdrawPlayer(player, amount);
                                player.sendMessage(success + "You have withdrew " + currencySymbol + amount + "!");
                                Utils.playSuccess(player);

                                ItemStack cash = new ItemStack(Material.RAW_GOLD);
                                ItemMeta cashMeta = cash.getItemMeta();
                                cashMeta.setDisplayName(currencyColor + "" + ChatColor.BOLD + currencyName);
                                cashMeta.addEnchant(Enchantment.UNBREAKING, 1, true);
                                cashMeta.setLore(Arrays.asList(ChatColor.GRAY + "Deposit into your balance with /deposit"));
                                cashMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                cash.setItemMeta(cashMeta);
                                cash.setAmount(amount);

                                inv.addItem(cash);

                            } else {
                                player.sendMessage(error + "You don't have enough money!");
                                player.sendMessage(error + "Balance: " + currencySymbol + balance);
                                Utils.playError(player);
                            }
                        } else {
                            player.sendMessage(error + "The amount must be a number (no decimal allowed)!");
                            Utils.playError(player);
                        }
                    } else {
                        player.sendMessage(error + "Incorrect command usage:");
                        player.sendMessage(error + "/withdraw [amount]");
                        Utils.playError(player);
                    }
                }
            } else {
                getLogger().severe("[Cash+] This command can only be executed by a player!");
            }
        }
        return false;
    }

}
