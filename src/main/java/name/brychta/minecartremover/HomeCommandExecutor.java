package name.brychta.minecartremover;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
//@author Martin Brychta [SirPole]
public class HomeCommandExecutor implements CommandExecutor {

    private MinecartRemover plg;

    public HomeCommandExecutor(MinecartRemover plg) {
        this.plg = plg;
    }

    /*
     * Applies command /home
     * Teleports player to his home, previously created by sleeping in bed or using /sethome
     * @param cs Command Sender, in this case must be player
     * @param cmd Command itself
     * @param str Command Alias, I'm not using that
     * @param args Arguments, int his case no arguments are allowed
     * @return true if successful
     */
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String str, String[] args) {
        if (cmd.getName().equalsIgnoreCase("home")) {
            if (plg.getConfig().getBoolean("home")) {
                if (cs instanceof Player) {
                    Player player = (Player) cs;
                    if (player.hasPermission("minecartremover.home")) {
                        if (args.length == 0) {
                            player.teleport(player.getBedSpawnLocation());
                            cs.sendMessage(ChatColor.GREEN + "Whoosh!");
                            return true;
                        } else {
                            cs.sendMessage(ChatColor.RED + "[Minecart Remover] NO arguments are allowed, usage: /home");
                        }
                    } else {
                        cs.sendMessage(ChatColor.RED + "[Minecart Remover] You don't have necessary permission 'minecartremover.home'");
                    }
                } else {
                    cs.sendMessage(ChatColor.RED + "[Minecart Remover] Not usable from console");
                }
            }
        }
        return false;
    }
}