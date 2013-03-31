package name.brychta.minecartremover;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
//@author Martin Brychta [SirPole]
class RmmcCommandExecutor implements CommandExecutor {

    private MinecartRemover plg;
    private int i = 0; //using for counting Minecarts
    private String selection;
    private boolean emptiness;

    public RmmcCommandExecutor(MinecartRemover plg) {
        this.plg = plg;
        selection = plg.getConfig().getString("remove");
        emptiness = plg.getConfig().getBoolean("empty_only");
    }

    /*
     * Applies command /rmmc <range> [selection] [emptiness]
     * Removes minecarts in range
     * @param cs Command Sender, in this case must be player
     * @param cmd Command itself
     * @param str Command Alias, I'm not using that
     * @param args Arguments, 1. argument is required - range, 2.-3. arguments are optional, they serve as config override
     * @return true if successful
     */
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String str, String[] args) {
        int range;
        if (cmd.getName().equalsIgnoreCase("rmmc")) {
            if (cs instanceof Player) {
                Player player = (Player) cs;
                if (player.hasPermission("minecartremover.rmmc")) {
                    switch (args.length) {
                        case 0:
                            cs.sendMessage(ChatColor.RED + "[Minecart Remover] Not enough arguments - /rmmc <range> [regular | furnace | storage | hopper | explosive | spawner | all] [empty | NOTempty]");
                            return false;
                        case 1:
                            range = Integer.parseInt(args[0]);
                            break;
                        case 2:
                            range = Integer.parseInt(args[0]);
                            selection = args[1];
                            if ("all".equals(selection)) {
                                selection = "regular,furnace,storage,hopper,explosive,spawner";
                            }
                            break;
                        case 3:
                            range = Integer.parseInt(args[0]);
                            selection = args[1];
                            if ("all".equals(selection)) {
                                selection = "regular,furnace,storage,hopper,explosive,spawner";
                            }
                            if ("empty".equals(args[2])) {
                                emptiness = true;
                            } else if ("NOTempty".contains(args[2])) {
                                emptiness = false;
                            }
                            break;
                        default:
                            cs.sendMessage(ChatColor.RED + "[Minecart Remover] Too many arguments - /rmmc <range> [regular | furnace | storage | all] [empty | NOTempty]");
                            return false;
                    }
                    Location loc = player.getLocation();
                    for (Entity entity : loc.getWorld().getEntities()) {
                        if (isInRange(loc, entity.getLocation(), range)) {
                            if (entity instanceof Minecart) {
                                Remove(entity);
                            }
                        }
                    }
                    if (i == -1) {
                        cs.sendMessage(ChatColor.RED + "[Minecart Remover] Invalid selection, check your config.yml or parameters typed.");
                    } else {
                        cs.sendMessage(ChatColor.GREEN + "[Minecart Remover] " + i + " Minecart(s) cleared.");
                    }
                    i = 0;
                    return true;
                } else {
                    cs.sendMessage(ChatColor.RED + "[Minecart Remover] You don't have necessary permission 'minecartremover.rmmc'");
                }
            } else {
                cs.sendMessage(ChatColor.RED + "[Minecart Remover] Not usable from console");
            }
        }
        return false;
    }

    /*
     * Says if given entity is in range
     * @param center Location, where player is
     * @param border Location, where range is at top
     * @param range range
     * @return true if entity in range
     */
    public static boolean isInRange(Location center, Location border, int range) {
        int x = center.getBlockX(), z = center.getBlockZ();
        int x1 = border.getBlockX(), z1 = border.getBlockZ();
        if (x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range)) {
            return false;
        }
        return true;
    }

    public void removeRideable(Entity entity, boolean emptiness) {
        if (emptiness) {
            if (!entity.isEmpty()) {
                return;
            }
        }
        entity.remove();
        save(328);
        i++;
    }

    public void removeFurnace(Entity entity, boolean emptiness) {
        if (emptiness) {
            if (entity.getVelocity().length() != 0) {
                return;
            }
        }
        entity.remove();
        save(343);
        i++;
    }

    public void removeStorage(Entity entity, boolean emptiness) {
        if (emptiness) {
            StorageMinecart stmc = (StorageMinecart) entity;
            ItemStack[] contents = stmc.getInventory().getContents();
            for (ItemStack item : contents) {
                if (item != null) {
                    return;
                }
            }
        }
        entity.remove();
        save(342);
        i++;
    }

    public void removeExplosive(Entity entity) {
        entity.remove();
        save(407);
        i++;
    }

    public void removeHopper(Entity entity, boolean emptiness) {
        if (emptiness) {
            HopperMinecart homc = (HopperMinecart) entity;
            ItemStack[] contents = homc.getInventory().getContents();
            for (ItemStack item : contents) {
                if (item != null) {
                    return;
                }
            }
        }
        entity.remove();
        save(408);
        i++;
    }

    public void removeSpawner(Entity entity) {
        entity.remove();
        save(328);
        i++;
    }

    /*
     * Removes 1 minecart due to selecion
     * @param entity entity given by isInRange method
     * @param selection selection made, by config or overriden
     * @param emptiness if empty or not
     * @return nothing, but might return true, if successful
     */
    public void Remove(Entity entity) {
        String[] list = selection.split(",");
        for (String sel : list) {
            switch (sel) {
                case "rideable":
                    if (entity instanceof RideableMinecart) {
                        removeRideable(entity, emptiness);
                    }
                    break;
                case "furnace":
                    if (entity instanceof PoweredMinecart) {
                        removeFurnace(entity, emptiness);
                    }
                    break;
                case "storage":
                    if (entity instanceof StorageMinecart) {
                        removeStorage(entity, emptiness);
                    }
                    break;
                case "hopper":
                    if (entity instanceof HopperMinecart) {
                        removeHopper(entity, emptiness);
                    }
                    break;
                case "explosive":
                    if (entity instanceof ExplosiveMinecart) {
                        removeExplosive(entity);
                    }
                    break;
                case "spawner":
                    if (entity instanceof SpawnerMinecart) {
                        removeSpawner(entity);
                    }
                    break;
                default:
                    i = -1;
            }
        }
    }

    public void save(int id) {
        Block block = plg.getServer().getWorld(plg.getConfig().getString("chestworld")).getBlockAt(plg.getConfig().getInt("chestx"), plg.getConfig().getInt("chesty"), plg.getConfig().getInt("chestz"));
        if (block instanceof Chest) {
            Chest chest = (Chest) block;
            Inventory inv = chest.getInventory();
            ItemStack add = new ItemStack(id, 1);
            for (ItemStack item : inv.getContents()) {
                if (item.getTypeId() == id || item == null) {
                    inv.addItem(add);
                }
            }
        }
    }
}