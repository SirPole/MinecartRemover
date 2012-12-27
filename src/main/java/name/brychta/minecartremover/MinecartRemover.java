package name.brychta.minecartremover;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.plugin.java.JavaPlugin;
public class MinecartRemover extends JavaPlugin{
    Logger logger=Logger.getLogger("Minecraft");
    public void onEnable(){       
        logger.info("[Minecart remover] Activated");
    }
    public void onDisable(){
        logger.info("[Minecart remover] Decativated");
    }
    public boolean onCommand(CommandSender cs, Command cmd, String str, String[] args){
        Player player=null;
        if(cs instanceof Player)
            player=(Player)cs;
        if(cmd.getName().equalsIgnoreCase("rmmc"))
            if(player!=null)
                if(args.length==1){
                    int range = (int)Double.parseDouble(args[0]);
                    Location loc = player.getLocation();
                    for(Entity entity : loc.getWorld().getEntities())
                        if (isInBorder(loc, entity.getLocation(), range))
                            if(entity instanceof Minecart && !(entity instanceof StorageMinecart) && !(entity instanceof PoweredMinecart))
                                if(entity.isEmpty())
                                    entity.remove();
                    cs.sendMessage(ChatColor.GREEN+"[Minecart Remover] Cleared");
                    return true;
                }else
                    cs.sendMessage(ChatColor.RED+"[Minecart Remover] Wrong amount of arguments, usage: /rmmc [range]");
            else
                cs.sendMessage(ChatColor.RED+"[Minecart Remover] Not usable from console");
        return false;
    }
    public static boolean isInBorder(Location center, Location notCenter, int range) {
        int x = center.getBlockX(), z = center.getBlockZ();
        int x1 = notCenter.getBlockX(), z1 = notCenter.getBlockZ();
        if (x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range))
            return false;
        return true;
    }
}