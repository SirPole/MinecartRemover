package name.brychta.minecartremover;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
//@author Martin Brychta [SirPole]
public class SetspawnCommandExecutor implements CommandExecutor {

    private MinecartRemover plg;

    public SetspawnCommandExecutor(MinecartRemover plg) {
        this.plg = plg;
    }

    /*
     * Applies command /setspawn
     * Sets world's global spawn to players current location
     * 
     * @param cs Command Sender, in this case must be player
     * @param cmd Command itself
     * @param str Command Alias, I'm not using that
     * @param args Arguments, in this case no arguments are allowed
     * @return true if successful 
     */
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String str, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setspawn")) {
            if (plg.getConfig().getBoolean("spawn")) {
                if (cs instanceof Player) {
                    Player player = (Player) cs;
                    if (player.hasPermission("minecartremover.setspawn")) {
                        if (args.length == 0) {
                            Location loc = player.getLocation();
                            int x = (int) loc.getX();
                            int y = (int) loc.getY();
                            int z = (int) loc.getZ();
                            loc.getWorld().setSpawnLocation(x, y, z);
                            cs.sendMessage(ChatColor.GREEN + "Global spawn has been set to X=" + x + ", Y=" + y + ", Z=" + z + ".");
                            return true;
                        } else {
                            cs.sendMessage(ChatColor.RED + "[Minecart Remover] NO arguments are allowed, usage: /setspawn");
                        }
                    } else {
                        cs.sendMessage(ChatColor.RED + "[Minecart Remover] You don't have necessary permission 'minecartremover.setspawn'");
                    }
                } else {
                    cs.sendMessage(ChatColor.RED + "[Minecart Remover] Not usable from console");
                }
            }
        }
        return false;
    }
}