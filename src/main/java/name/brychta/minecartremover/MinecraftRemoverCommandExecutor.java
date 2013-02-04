package name.brychta.minecartremover;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

class MinecraftRemoverCommandExecutor implements CommandExecutor {

    private MinecartRemover plg;
    private int i = 0, j = 0, k = 0;

    public MinecraftRemoverCommandExecutor(MinecartRemover plg) {
        this.plg = plg;
    }

    public boolean onCommand(CommandSender cs, Command cmd, String str, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rmmc")) {
            if (cs instanceof Player) {
                Player player = (Player) cs;
                if (player.hasPermission("minecartremover.rmmc")) {
                    if (args.length == 1) {
                        int range = (int) Double.parseDouble(args[0]);
                        Location loc = player.getLocation();
                        for (Entity entity : loc.getWorld().getEntities()) {
                            if (isInRange(loc, entity.getLocation(), range)) {
                                Remove(entity, plg.getConfig().getString("remove"), plg.getConfig().getBoolean("empty_only"));
                            }
                        }
                        if (i == -1 && j == -1 && k == -1) {
                            cs.sendMessage(ChatColor.RED + "[Minecart Remover] Invalid selection, check your config.yml");
                        } else {
                            cs.sendMessage(ChatColor.GREEN + "[Minecart Remover] " + i + " Regular, " + k + " Storage and " + j + " Furnace Minecart(s) cleared.");
                        }
                        i = j = k = 0;
                        return true;
                    } else {
                        cs.sendMessage(ChatColor.RED + "[Minecart Remover] Wrong amount of arguments, usage: /rmmc [range]");
                    }
                } else {
                    cs.sendMessage(ChatColor.RED + "[Minecart Remover] You don't have necessary permission 'minecartremover.rmmc'");
                }
            } else {
                cs.sendMessage(ChatColor.RED + "[Minecart Remover] Not usable from console");
            }
        }
        return false;
    }

    public static boolean isInRange(Location center, Location border, int range) {
        int x = center.getBlockX(), z = center.getBlockZ();
        int x1 = border.getBlockX(), z1 = border.getBlockZ();
        if (x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range)) {
            return false;
        }
        return true;
    }

    public void Remove(Entity entity, String selection, boolean empty) {
        if (empty) {
            switch (selection) {
                case "regular":
                    if ((entity instanceof Minecart) && !(entity instanceof StorageMinecart || entity instanceof PoweredMinecart)) {
                        if (entity.isEmpty()) {
                            entity.remove();
                            i++;
                        }
                    }
                    break;
                case "furnace":
                    if (entity instanceof PoweredMinecart) {
                        if (entity.getVelocity().length() == 0) {
                            entity.remove();
                            j++;
                        }
                    } else if ((entity instanceof Minecart) && !(entity instanceof StorageMinecart)) {
                        if (entity.isEmpty()) {
                            entity.remove();
                            i++;
                        }
                    }
                    break;
                case "storage":
                    if (entity instanceof StorageMinecart) {
                        StorageMinecart stmc = (StorageMinecart) entity;
                        boolean emptycart = true;
                        ItemStack[] contents = stmc.getInventory().getContents();
                        for (ItemStack item : contents) {
                            if (item != null) {
                                emptycart = false;
                            }
                        }
                        if (emptycart) {
                            entity.remove();
                            k++;
                        }
                    } else if ((entity instanceof Minecart) && !(entity instanceof PoweredMinecart)) {
                        if (entity.isEmpty()) {
                            entity.remove();
                            i++;
                        }
                    }
                    break;
                case "all":
                    if (entity instanceof StorageMinecart) {
                        StorageMinecart stmc = (StorageMinecart) entity;
                        boolean emptycart = true;
                        ItemStack[] contents = stmc.getInventory().getContents();
                        for (ItemStack item : contents) {
                            if (item != null) {
                                emptycart = false;
                            }
                        }
                        if (emptycart) {
                            entity.remove();
                            k++;
                        }
                    } else if (entity instanceof PoweredMinecart) {
                        if (entity.getVelocity().length() == 0) {
                            entity.remove();
                            j++;
                        }
                    } else if (entity instanceof Minecart) {
                        if (entity.isEmpty()) {
                            entity.remove();
                            i++;
                        }
                    }
                    break;
                default:
                    i = j = k = -1;
            }
        } else {
            switch (selection) {
                case "regular":
                    if ((entity instanceof Minecart) && !(entity instanceof StorageMinecart || entity instanceof PoweredMinecart)) {
                        entity.remove();
                        i++;
                    }
                    break;
                case "furnace":
                    if (entity instanceof PoweredMinecart) {
                        entity.remove();
                        j++;
                    } else if ((entity instanceof Minecart) && !(entity instanceof StorageMinecart)) {
                        entity.remove();
                        i++;
                    }
                    break;
                case "storage":
                    if (entity instanceof StorageMinecart) {
                        entity.remove();
                        k++;
                    } else if ((entity instanceof Minecart) && !(entity instanceof PoweredMinecart)) {
                        entity.remove();
                        i++;
                    }
                    break;
                case "all":
                    if (entity instanceof StorageMinecart) {
                        entity.remove();
                        k++;
                    } else if (entity instanceof PoweredMinecart) {
                        entity.remove();
                        j++;
                    } else if (entity instanceof Minecart) {
                        entity.remove();
                        i++;
                    }
                    break;
                default:
                    i = j = k = -1;
            }
        }
    }
}