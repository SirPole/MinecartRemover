package name.brychta.minecartremover;

import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
//@author Martin Brychta [SirPole]
public class SetspawnCommandExecutor implements CommandExecutor {

    private MinecartRemover plg;
    private static SQL sql;
    private Statement st;

    public SetspawnCommandExecutor(MinecartRemover plg) {
        this.plg = plg;
        sql = new SQL(plg);
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
            if (cs instanceof Player) {
                Player player = (Player) cs;
                if (player.hasPermission("minecartremover.setspawn")) {
                    if (args.length == 0) {
                        if (plg.setSpawn(player)) {
                            cs.sendMessage(ChatColor.GREEN + "[Minecart Remover] Global spawn has been set.");
                            return true;
                        } else {
                            cs.sendMessage(ChatColor.RED + "[Minecart Remover] Something went wrong, check log...");
                        }
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
        return false;
    }
}