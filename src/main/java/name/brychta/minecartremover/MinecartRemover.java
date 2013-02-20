package name.brychta.minecartremover;

import org.bukkit.plugin.java.JavaPlugin;
//@author Martin Brychta [SirPole]
public class MinecartRemover extends JavaPlugin {

    /*
     * Actions, that are done, when plugin is loaded (usually start)
     */
    public void onEnable() {
        this.saveDefaultConfig();
        getCommand("rmmc").setExecutor(new MinecraftRemoverCommandExecutor(this));
        if (this.getConfig().getBoolean("home")) {
            getCommand("home").setExecutor(new HomeCommandExecutor(this));
            getCommand("spawn").setExecutor(new SpawnCommandExecutor(this));
        }
        if (this.getConfig().getBoolean("spawn")) {
            getCommand("setspawn").setExecutor(new SetspawnCommandExecutor(this));
            getCommand("sethome").setExecutor(new SethomeCommandExecutor(this));
        }
    }

    /*
     * Actions, that are done, when plugin is unloaded (usually shutdown)
     */
    public void onDisable() {
    }
}