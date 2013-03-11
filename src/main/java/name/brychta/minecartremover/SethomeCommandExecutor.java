package name.brychta.minecartremover;

import org.bukkit.ChatColor;
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
            if (cs instanceof Player) {
                Player player = (Player) cs;
                if (player.hasPermission("minecartremover.sethome")) {
                    if (args.length == 0) {
                        if (plg.setHome(player.getName(), player)) {
                            cs.sendMessage(ChatColor.GREEN + "[Minecart Remover] Your home has been set.");
                            return true;
                        } else {
                            cs.sendMessage(ChatColor.RED + "[Minecart Remover] Something went wrong, check log...");
                        }
                    } else if (args.length == 1) {
                        if (player.hasPermission("minecartremover.sethome.other")) {
                            if (plg.setHome(args[0], player)) {
                                cs.sendMessage(ChatColor.GREEN + "[Minecart Remover] " + args[0] + "'s home has been set.");
                                return true;
                            } else {
                                cs.sendMessage(ChatColor.RED + "[Minecart Remover] Something went wrong, check log...");
                            }
                        } else {
                            cs.sendMessage(ChatColor.RED + "[Minecart Remover] You don't have necessary permission 'minecartremover.sethome.other'");
                        }
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
        return false;
    }
}