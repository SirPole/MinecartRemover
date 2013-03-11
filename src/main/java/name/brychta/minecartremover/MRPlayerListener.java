package name.brychta.minecartremover;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
//@author - Martin Brychta [SirPole]
public class MRPlayerListener implements Listener {

    private MinecartRemover plg;

    public MRPlayerListener(MinecartRemover plg) {
        this.plg = plg;
    }

    /*
     * Fires when player joins server
     * @param event - instance of PlayerJoinEvent
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            player.teleport(plg.getSpawn(player.getWorld().getName()));
        }
    }

    /*
     * Fires when player respawns (after dying)
     * @param event - instance of PlayerRespawnEvent
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (plg.getHome(event.getPlayer().getName()) != null) {
            event.setRespawnLocation(plg.getHome(event.getPlayer().getName()));
        } else {
            event.setRespawnLocation(plg.getSpawn(event.getPlayer().getWorld().getName()));
        }
    }

    /*
     * Fires when player enter bed
     * @param event - instance of PlayerBedEnterEvent
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        event.getPlayer().sendMessage(ChatColor.YELLOW + "[Minecart Remover] Your home wasn't set, use /setspawn to set it.");
    }
}