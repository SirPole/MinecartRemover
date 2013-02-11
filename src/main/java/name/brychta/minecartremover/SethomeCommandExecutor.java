package name.brychta.minecartremover;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
//@author Martin Brychta [SirPole]
public class SethomeCommandExecutor implements CommandExecutor {

    private MinecartRemover plg;

    public SethomeCommandExecutor(MinecartRemover plg) {
        this.plg = plg;
    }

    /*
     * Applies command /sethome
     * Sets player's home to his current location
     * 
     * @param cs Command Sender, in this case must be player
     * @param cmd Command itself
     * @param str Command Alias, I'm not using that
     * @param args Arguments, in this case no arguments are allowed
     * @return true if successful 
     */
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String str, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sethome")) {
            if (plg.getConfig().getBoolean("home")) {
                if (cs instanceof Player) {
                    Player player = (Player) cs;
                    if (player.hasPermission("minecartremover.sethome")) {
                        if (args.length == 0) {
                            Location loc = player.getLocation();
                            int x = (int) loc.getX();
                            int y = (int) loc.getY();
                            int z = (int) loc.getZ();
                            player.setBedSpawnLocation(loc, true);
                            cs.sendMessage(ChatColor.GREEN + "Your home has been set to X=" + x + ", Y=" + y + ", Z=" + z + ".");
                            return true;
                        } else {
                            cs.sendMessage(ChatColor.RED + "[Minecart Remover] NO arguments are allowed, usage: /sethome");
                        }
                    } else {
                        cs.sendMessage(ChatColor.RED + "[Minecart Remover] You don't have necessary permission 'minecartremover.sethome'");
                    }
                } else {
                    cs.sendMessage(ChatColor.RED + "[Minecart Remover] Not usable from console");
                }
            }
        }
        return false;
    }
}