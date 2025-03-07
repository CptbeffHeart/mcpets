package fr.nocsy.mcpets.commands.tabcompleters;

import fr.nocsy.mcpets.PPermission;
import fr.nocsy.mcpets.data.Category;
import fr.nocsy.mcpets.data.Pet;
import fr.nocsy.mcpets.data.config.ItemsListConfig;
import fr.nocsy.mcpets.data.config.PetFoodConfig;
import fr.nocsy.mcpets.data.livingpets.PetFood;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MCPetsCommandTabCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender,
                                                @NotNull Command cmd,
                                                @NotNull String alias,
                                                @NotNull String[] args) {

        ArrayList<String> completed = new ArrayList<>();

        if (cmd.getName().equalsIgnoreCase("mcpets")) {

            if (commandSender instanceof Player) {

                Player p = ((Player)commandSender);
                if (!p.hasPermission(PPermission.ADMIN.getPermission()))
                    return completed;

                if (args.length == 1) {
                    completed.add("reload");
                    completed.add("editor");
                    completed.add("spawn");
                    completed.add("open");
                    completed.add("inventory");
                    completed.add("revoke");
                    completed.add("signalstick");
                    completed.add("item");
                    completed.add("clearStats");
                    completed.add("petFood");
                    completed.add("category");
                    completed.add("debug");
                }

                else if (args.length == 2) {
                    switch (args[0].toLowerCase()) {
                        case "spawn":
                            completed.addAll(Pet.getObjectPets().stream().map(Pet::getId).collect(Collectors.toList()));
                            break;
                        case "open": case "inventory":
                            completed.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
                            break;
                        case "signalstick":
                            completed.addAll(Pet.getObjectPets().stream().map(Pet::getId).collect(Collectors.toList()));
                            break;
                        case "item":
                            completed.addAll(ItemsListConfig.getInstance().listKeys());
                            completed.add("add");
                            completed.add("remove");
                            completed.add("give");
                            completed.add("list");
                            break;
                        case "clearstats":
                            completed.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
                            completed.addAll(Pet.getObjectPets().stream().map(Pet::getId).collect(Collectors.toList()));
                            break;
                        case "petfood":
                            completed.addAll(PetFoodConfig.getInstance().list().stream().map(PetFood::getId).collect(Collectors.toList()));
                            break;
                        case "category":
                            completed.addAll(Category.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
                            break;
                        case "revoke":
                            completed.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
                            break;
                        default: {}
                    }
                }

                else if (args.length == 3) {
                    switch (args[0].toLowerCase()) {
                        case "spawn":
                            completed.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
                            break;
                        case "signalstick":
                            completed.addAll(Pet.getObjectPets().stream().map(Pet::getId).collect(Collectors.toList()));
                            break;
                        case "inventory":
                            completed.addAll(Pet.getObjectPets().stream()
                                    .filter(pet -> pet.getInventorySize() > 0)
                                    .map(Pet::getId)
                                    .collect(Collectors.toList()));
                            break;
                        case "item":
                            if (args[1].equalsIgnoreCase("give") || args[1].equalsIgnoreCase("remove"))
                                completed.addAll(ItemsListConfig.getInstance().listKeys());
                            break;
                        case "clearstats":
                            completed.addAll(Pet.getObjectPets().stream().map(Pet::getId).collect(Collectors.toList()));
                            break;
                        case "category":
                            completed.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
                            break;
                        default: {}
                    }
                }

                else if (args.length == 4) {
                    if (args[0].equalsIgnoreCase("spawn")) {
                        completed.add("true");
                        completed.add("false");
                    }
                }

                else if (args.length == 5) {
                    if (args[0].equalsIgnoreCase("spawn")) {
                        completed.add("-s");
                    }
                }
            }
        }

        Collections.sort(completed);
        return completed;
    }
}
