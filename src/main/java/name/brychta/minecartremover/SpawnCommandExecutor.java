package name.brychta.minecartremover;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
                        player.teleport(player.getWorld().getSpawnLocation());
                        player.sendMessage(ChatColor.GREEN + "Whoosh!");
                        return true;
                    } else {
                        cs.sendMessage(ChatColor.RED + "[Minecart Remover] NO arguments are allowed, usage: /spawn");
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