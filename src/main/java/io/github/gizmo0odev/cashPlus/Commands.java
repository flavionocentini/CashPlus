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

    String prefix = CashPlus.prefix;
    Material cashMaterial = CashPlus.cashMaterial;
    String currencySymbol;
    String currencyName;
    String cashDescLine1;
    String cashDescLine2;

    public Commands() {
        currencySymbol = CashPlus.getPluginConfig().getString("currency-symbol");
        currencyName = CashPlus.getPluginConfig().getString("currency-name");
        cashDescLine1 = CashPlus.getPluginConfig().getString("cash-description-line-1");
        cashDescLine2 = CashPlus.getPluginConfig().getString("cash-description-line-2");
    }

    ChatColor success = Utils.success;
    ChatColor error = Utils.error;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // withdraw
        if (cmd.getName().equalsIgnoreCase("withdraw")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Inventory inv = player.getInventory();
                if (player.hasPermission("cashplus.withdraw")) {
                    if (args.length >= 1) {
                        if (args[0].matches("-?\\d+")) {
                            int amount = Integer.parseInt(args[0]);
                            double balance = CashPlus.getEconomy().getBalance(player);
                            if (CashPlus.getEconomy().has(player, amount)) {

                                CashPlus.getEconomy().withdrawPlayer(player, amount);
                                player.sendMessage(prefix + success + "You have withdrew " + currencySymbol + amount + "!");
                                Utils.playSuccess(player);

                                ItemStack cash = new ItemStack(cashMaterial);
                                ItemMeta cashMeta = cash.getItemMeta();
                                cashMeta.setDisplayName(currencyName);
                                cashMeta.addEnchant(Enchantment.UNBREAKING, 1, true);
                                cashMeta.setLore(Arrays.asList(cashDescLine1, cashDescLine2));
                                cashMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                cash.setItemMeta(cashMeta);
                                cash.setAmount(amount);

                                inv.addItem(cash);

                            } else {
                                player.sendMessage(prefix + error + "You don't have enough money!");
                                player.sendMessage(prefix + error + "Balance: " + currencySymbol + balance);
                                Utils.playError(player);
                            }
                        } else {
                            player.sendMessage(prefix + error + "The amount must be a number (no decimal allowed)!");
                            Utils.playError(player);
                        }
                    } else {
                        player.sendMessage(prefix + error + "Incorrect command usage:");
                        player.sendMessage(prefix + error + "/withdraw [amount]");
                        Utils.playError(player);
                    }
                } else {
                    player.sendMessage(prefix + error + "You don't have permission to use that command!");
                    Utils.playError(player);
                }
            } else {
                getLogger().severe("[Cash+] This command can only be executed by a player!");
            }
            // deposit
        } else if (cmd.getName().equalsIgnoreCase("deposit")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("cashplus.deposit")) {
                    if (args.length >= 1) {
                        if (args[0].matches("-?\\d+")) {
                            int amount = Integer.parseInt(args[0]);
                            if (Utils.countCash(player) >= amount) {

                                Utils.removeCash(player, amount);
                                CashPlus.getEconomy().depositPlayer(player, amount);
                                player.sendMessage(prefix + success + "You have deposited " + currencySymbol + amount + "!");
                                Utils.playSuccess(player);

                            } else {
                                player.sendMessage(prefix + error + "You don't have enough cash!");
                                Utils.playError(player);
                            }
                        } else if (args[0].equalsIgnoreCase("all")) {
                            int amount = Utils.countCash(player);

                            Utils.removeCash(player, amount);
                            CashPlus.getEconomy().depositPlayer(player, amount);
                            player.sendMessage(prefix + success + "You have deposited " + currencySymbol + amount + "!");
                            Utils.playSuccess(player);

                        } else {
                            player.sendMessage(prefix + error + "The amount must be a number (no decimal allowed)!");
                            Utils.playError(player);
                        }
                    } else {
                        player.sendMessage(prefix + error + "Incorrect command usage:");
                        player.sendMessage(prefix + error + "/withdraw [amount]");
                        Utils.playError(player);
                    }
                } else {
                    player.sendMessage(prefix + error + "You don't have permission to use that command!");
                    Utils.playError(player);
                }
            } else {
                getLogger().severe("[Cash+] This command can only be executed by a player!");
            }
            // cashplus admin command
        } else if (cmd.getName().equalsIgnoreCase("cashplus")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        if (player.hasPermission("cashplus.admin")) {
                            CashPlus.getInstance().reloadConfig();
                            player.sendMessage(prefix + success + "Config reloaded!");
                            Utils.playSuccess(player);
                        } else {
                            player.sendMessage(prefix + error + "You don't have permission to use that command!");
                            Utils.playError(player);
                        }
                    } else {
                        CashPlus.getInstance().reloadConfig();
                        getLogger().info("[Chat+] Config reloaded!");
                    }
                } else {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        if (player.hasPermission("cashplus.admin")) {
                            player.sendMessage(prefix + error + "Incorrect command usage!");
                            Utils.playError(player);
                        } else {
                            player.sendMessage(prefix + error + "You don't have permission to use that command!");
                            Utils.playError(player);
                        }
                    } else {
                        getLogger().severe("[Cash+] Incorrect command usage!");
                    }
                }
            } else {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("cashplus.admin")) {
                        player.sendMessage(prefix + error + "Incorrect command usage!");
                        Utils.playError(player);
                    } else {
                        player.sendMessage(prefix + error + "You don't have permission to use that command!");
                        Utils.playError(player);
                    }
                } else {
                    getLogger().severe("[Cash+] Incorrect command usage!");
                }
            }
        }
        return false;
    }

}
