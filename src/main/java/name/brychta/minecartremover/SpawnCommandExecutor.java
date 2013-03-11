package name.brychta.minecartremover;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
//@author Martin Brychta [SirPole]
public class SpawnCommandExecutor implements CommandExecutor {

    private MinecartRemover plg;

    public SpawnCommandExecutor(MinecartRemover plg) {
        this.plg = plg;
    }

    /*
     * Applies command /spawn
     * Teleports player to world's spawn location.
     * @param cs Command Sender, in this case must be player
     * @param cmd Command itself
     * @param str Command Alias, I'm not using that
     * @param args Arguments, int his case no arguments are allowed
     * @return true if successful
     */
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String str, String[] args) {
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (cs instanceof Player) {
                Player player = (Player) cs;
                if (player.hasPermission("minecartremover.spawn")) {
                    if (args.length == 0) {
                        if (plg.getSpawn(player.getWorld().getName()) != null) {
                            player.teleport(plg.getSpawn(player.getWorld().getName()), PlayerTeleportEvent.TeleportCause.PLUGIN);
                            player.sendMessage(ChatColor.GREEN + "Whoosh!");
                            return true;
                        }
                    } else if (args.length == 1) {
                        if (player.hasPermission("minecartremover.spawn.other")) {
                            if (plg.getSpawn(args[0]) != null) {
                                player.teleport(plg.getSpawn(args[0]), PlayerTeleportEvent.TeleportCause.PLUGIN);
                                player.sendMessage(ChatColor.GREEN + "Whoosh!");
                                return true;
                            } else {
                                cs.sendMessage(ChatColor.RED + "[Minecart Remover] Oops, seems like the world doesn't exists...");
                            }
                        } else {
                            cs.sendMessage(ChatColor.RED + "[Minecart Remover] You don't have necessary permission 'minecartremover.spawn.other'");
                        }
                    } else {
                        cs.sendMessage(ChatColor.RED + "[Minecart Remover] NO arguments are allowed, usage: /spawn or /spawn [world]");
                    }
                } else {
                    cs.sendMessage(ChatColor.RED + "[Minecart Remover] You don't have necessary permission 'minecartremover.spawn'");
                }
            } else {
                cs.sendMessage(ChatColor.RED + "[Minecart Remover] Not usable from console");
            }
        }
        return false;
    }
}